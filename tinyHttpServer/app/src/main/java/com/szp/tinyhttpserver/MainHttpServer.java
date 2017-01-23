package com.szp.tinyhttpserver;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.nanohttpd.util.ServerRunner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class MainHttpServer extends AppCompatActivity {
    private Handler mHanlder = new myHandler();
    private Context mContext;

    private Button downloadXmlButton;
    private ListView showConfigListView;
    private ArrayList<HashMap<String, String>> mUserConfigList = null;
    private UserInfoAdapter mUserInfoAdapter;
    private View.OnClickListener myEventListent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_http_server);
        mContext = getApplicationContext();
        initView();
        refreshData();

        ServerRunner.run(NanoHttpServer.class, mContext, mHanlder);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ServerRunner.exit();
    }

    private void initView() {
        myEventListent = new myClickEventListener();
        downloadXmlButton = (Button)findViewById(R.id.download_button);
        showConfigListView = (ListView)findViewById(R.id.config_list_view);

        downloadXmlButton.setOnClickListener(myEventListent);
    }

    private void refreshData() {
        if(mUserConfigList == null)
            mUserConfigList = new ArrayList<HashMap<String, String>>();

        mUserConfigList.clear();

        for(String[] config : Utils.configPara) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put(Utils.ITEM_NAME, config[0]);
            temp.put(Utils.ITEM_VALUE, (String)SharePreferenceUtils.get(mContext, config[1], config[2]));
            mUserConfigList.add(temp);
        }

        mUserInfoAdapter = new UserInfoAdapter(mContext, mUserConfigList);
        showConfigListView.setAdapter(mUserInfoAdapter);
        mUserInfoAdapter.notifyDataSetChanged();
    }

    private class myHandler extends  Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Utils.MESSAGE_HTTP_STATUS:
                    if(msg.arg1 == Utils.HTTP_OK) {
                    } else if (msg.arg1 == Utils.HTTP_FAIL) {
                    }
                    break;
                case Utils.MESSAGE_REFRESH_DATA:
                    refreshData();
                    break;
                default:
                    break;
            }
        }
    }


    private class myClickEventListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.download_button:
                    break;
                default:
                    break;
            }
        }
    }

    private void parseConfigXmlFromServer(InputStream inputStream){
        XmlPullParser xmlPullParser = Xml.newPullParser();

        try {
            xmlPullParser.setInput(inputStream, "utf-8");
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            int eventType = xmlPullParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlPullParser.getName();
                        if(tag.equalsIgnoreCase(Utils.TAG_USER)) {
                            SharePreferenceUtils.put(mContext, Utils.XML_ID, xmlPullParser.getAttributeValue(null, Utils.XML_ID));
                        } else if (tag.equalsIgnoreCase(Utils.TAG_PARAM)) {
                            if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_PASSWORD)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_PASSWORD, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_VMPASSWORD)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_VMPASSWORD, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            }
                        } else if (tag.equalsIgnoreCase(Utils.TAG_VARIABLE)) {
                            if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_TOLLALLOW)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_TOLLALLOW, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_ACCOUNTCODE)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_ACCOUNTCODE, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_USERCONTEXT)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_USERCONTEXT, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_EFFECTIVE_CALLER_ID_NAME)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_EFFECTIVE_CALLER_ID_NAME, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_EFFECTIVE_CALLER_ID_NUMBER)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_EFFECTIVE_CALLER_ID_NUMBER, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_OUTBOUND_CALLER_ID_NAME)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_OUTBOUND_CALLER_ID_NAME, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_OUTBOUND_CALLER_ID_NUMBER)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_OUTBOUND_CALLER_ID_NUMBER, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_CALL_GROUP)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(mContext, Utils.XML_CALL_GROUP, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.nextTag();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getNanoHttpServiceAddress() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            LogUtils.e("MainHttpServer", "SocketException");
            e.printStackTrace();
        }
        return "http://" + hostIp + ":" + Utils.HTTP_PORT + "/" + Utils.HTTP_LOGIN;
    }
}