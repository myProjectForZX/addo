package com.szp.tinyhttpserver;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import com.szp.tinyhttpserver.SharePreferenceUtils;

/**
 * Created by zcily on 2017/1/22.
 */

public class NanoHttpServer extends NanoHTTPD{
    private AssetManager asset_mgr;
    private Context mContext;
    private final int MAX_FILE_SIZE = 2 * 1024 * 1024; //2M;

    private final String[] configPara =  new String[]{ Utils.ADDO_USER_ID, Utils.ADDO_PASSWORD, Utils.ADDO_VM_PASSWORD, Utils.ADDO_TOLL_ALLOW,
            Utils.ADDO_ACCOUNTCODE, Utils.ADDO_USER_CONTEXT, Utils.ADDO_EFFECTIVE_CALLER_ID_NAME, Utils.ADDO_EFFECTIVE_CALLER_ID_NUMBER, Utils.ADDO_OUTBOUND_CALLER_ID_NAME,
            Utils.ADDO_OUTBOUND_CALLER_ID_NUMBER, Utils.ADDO_CALLGROUP};

    public NanoHttpServer() {
       super(Utils.HTTP_PORT);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        String fileName = uri.substring(1);
        Map<String, String> parms = session.getParms();

        String msg;
        byte[] buffer = new byte[MAX_FILE_SIZE];
        int len = 0;

        LogUtils.e("NanoHttpServer", "*******  uri : "  + fileName + "  user : " + parms.get(Utils.HTTP_ADDO_USER) + "  id : " + parms.get(Utils.HTTP_ADDO_USERID));

        if(fileName.isEmpty())
            return null;

        if((fileName.startsWith(Utils.HTTP_LOGIN)) && parms.get(Utils.HTTP_ADDO_USER) == null && parms.get(Utils.HTTP_ADDO_USERID) == null) {
            fileName = Utils.HTTP_LOGIN;
            GeneratorXmlFile(Utils.CONFIG_XML);
        } else if (fileName.startsWith(Utils.HTTP_LOGIN) && parms.get(Utils.HTTP_ADDO_USER) != null && parms.get(Utils.HTTP_ADDO_PASSWORD) != null
                && parms.get(Utils.HTTP_ADDO_USER).equals(Utils.UserName)
                && parms.get(Utils.HTTP_ADDO_PASSWORD).equals(Utils.UserPassword)) {
            fileName = Utils.HTTP_CONFIG;
        } else if (fileName.startsWith(Utils.HTTP_LOGIN)
                && parms.get(Utils.HTTP_ADDO_USER) == null
                && parms.get(Utils.HTTP_ADDO_USERID) != null){
            fileName = Utils.HTTP_LOGIN;
            saveParamter(parms);
        } else if (fileName.startsWith(Utils.HTTP_GET_XML_CONFIG)){
            return Response.newFixedLengthResponse(getConfigXmlMsg());
        } else {
            fileName = Utils.HTTP_LOGIN;
        }

        if(asset_mgr == null) {
            msg = "<html><body><h1>httpServer</h1>\n";
            msg += "<p>open file error 100</p>";
            msg += "</body></html>\n";
            return Response.newFixedLengthResponse(msg);
        }

        try {
            InputStream in = asset_mgr.open(fileName, AssetManager.ACCESS_BUFFER);

            int temp;
            while((temp=in.read())!=-1){
                buffer[len]=(byte)temp;
                len++;
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        msg = new String(buffer, 0, len);

        return Response.newFixedLengthResponse(msg);
    }

    public void setAssetMgr(AssetManager am) {
        asset_mgr = am;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    private void saveParamter(Map<String, String> parms) {
        //userid password vmpassword tollallow accountcode context
        // calleridname caleridnumber outboundname outboundnumber group
        for(String singleParam : configPara) {
            if(parms.get(singleParam) != null) {
                SharePreferenceUtils.put(mContext, singleParam, parms.get(singleParam));
            }
        }
    }

    private String getConfigXmlMsg() {
        FileInputStream fis = null;
        try {
            fis = mContext.openFileInput(Utils.CONFIG_XML);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        byte[] bytes = new byte[0];
        try {
            bytes = new byte[fis.available()];
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try{
            while (fis.read(bytes) != -1) {
                arrayOutputStream.write(bytes, 0, bytes.length);
            }
            fis.close();
            fis = null;
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String msg = new String(arrayOutputStream.toByteArray());

        if(arrayOutputStream != null) {
            try {
                arrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LogUtils.e("ZWY", "msg : " + msg);
        return msg;
    }

    private void GeneratorXmlFile(String FileName) {
        FileOutputStream fout = null;
        try {
            fout = mContext.openFileOutput(FileName, mContext.MODE_PRIVATE);

            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fout, "UTF-8");
            serializer.startDocument("UTF-8", null);

            //<include>
            serializer.startTag(null, "include");

            //<user>
            serializer.startTag(null, "user");
            serializer.attribute(null, "id", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_USER_ID, "1"));
            //<params>
            serializer.startTag(null, "params");
            //<params --> password>
            serializer.startTag(null, "param");
            serializer.attribute(null, "name", "password");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_PASSWORD, ""));
            serializer.endTag(null, "param");
            //end <params --> password>

            serializer.startTag(null, "param");
            serializer.attribute(null, "name", "vm-password");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_VM_PASSWORD, ""));
            serializer.endTag(null, "param");

            serializer.endTag(null, "params");
            //end <params>

            //<variables>
            serializer.startTag(null, "variables");

            //toll_allow
            serializer.startTag(null, "variable");
            serializer.attribute(null, "name", "toll_allow");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_TOLL_ALLOW, ""));
            serializer.endTag(null, "variable");
            //end toll_allow

            //accountcode
            serializer.startTag(null, "variable");
            serializer.attribute(null, "name", "accountcode");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_ACCOUNTCODE, ""));
            serializer.endTag(null, "variable");
            //end accountcode

            //user_context
            serializer.startTag(null, "variable");
            serializer.attribute(null, "name", "user_context");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_USER_CONTEXT, ""));
            serializer.endTag(null, "variable");
            //end user_context

            //effective_caller_id_name
            serializer.startTag(null, "variable");
            serializer.attribute(null, "name", "effective_caller_id_name");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_EFFECTIVE_CALLER_ID_NAME, ""));
            serializer.endTag(null, "variable");
            //end effective_caller_id_name

            //effective_caller_id_number
            serializer.startTag(null, "variable");
            serializer.attribute(null, "name", "effective_caller_id_number");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_EFFECTIVE_CALLER_ID_NUMBER, ""));
            serializer.endTag(null, "variable");
            //end effective_caller_id_number

            //outbound_caller_id_name
            serializer.startTag(null, "variable");
            serializer.attribute(null, "name", "outbound_caller_id_name");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_OUTBOUND_CALLER_ID_NAME, ""));
            serializer.endTag(null, "variable");
            //end outbound_caller_id_name

            //outbound_caller_id_number
            serializer.startTag(null, "variable");
            serializer.attribute(null, "name", "outbound_caller_id_number");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_OUTBOUND_CALLER_ID_NUMBER, ""));
            serializer.endTag(null, "variable");
            //end outbound_caller_id_number

            //callgroup
            serializer.startTag(null, "variable");
            serializer.attribute(null, "name", "callgroup");
            serializer.attribute(null, "value", (String)SharePreferenceUtils.get(mContext, Utils.ADDO_CALLGROUP, ""));
            serializer.endTag(null, "variable");
            //end callgroup

            serializer.endTag(null, "variables");
            //end <variables>

            serializer.endTag(null, "user");
            serializer.endTag(null, "include");
            serializer.endDocument();
            serializer.flush();

            fout.close();
            fout = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException error) {
            error.printStackTrace();
        } finally{
            try {
                if (fout != null)
                    fout.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
