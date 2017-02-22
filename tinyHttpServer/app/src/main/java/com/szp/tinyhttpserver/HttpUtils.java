package com.szp.tinyhttpserver;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcily on 2017/1/23.
 */

public class HttpUtils {
    public static void submitPostData(String strUrlPath,Context context, Handler handler) {
        Map<String,String> params = new HashMap<String,String>();
        params.put("user", Utils.POST_PARAM_USERID);
        params.put("domain", Utils.POST_PARAM_DOMAIN);
        byte[] data = getRequestData(params, "utf-8").toString().getBytes();//获得请求体
        try {
            URL url = new URL(strUrlPath);

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();
            Message msg = new Message();
            msg.what = Utils.MESSAGE_DOWNLOAD_CONFIG_STATUS;
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                parseConfigXmlFromServer(context, inputStream);
                msg.arg1 = Utils.HTTP_OK;
                LogUtils.e("HttpUtils", "request http ok ");
            } else {
                msg.arg1 = Utils.HTTP_FAIL;
                LogUtils.e("HttpUtils", "request http fail ");
            }
            if(handler != null)
                handler.sendMessage(msg);
        } catch (IOException e) {
            //e.printStackTrace();
            LogUtils.e("httpUtils", "request http ioexception faild");
        }
    }

    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    private static void parseConfigXmlFromServer(Context context, InputStream inputStream){
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
                            SharePreferenceUtils.put(context, Utils.XML_ID, xmlPullParser.getAttributeValue(null, Utils.XML_ID));
                        } else if (tag.equalsIgnoreCase(Utils.TAG_PARAM)) {
                            if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_PASSWORD)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_PASSWORD, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_VMPASSWORD)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_VMPASSWORD, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            }
                        } else if (tag.equalsIgnoreCase(Utils.TAG_VARIABLE)) {
                            if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_TOLLALLOW)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_TOLLALLOW, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_ACCOUNTCODE)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_ACCOUNTCODE, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_USERCONTEXT)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_USERCONTEXT, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_EFFECTIVE_CALLER_ID_NAME)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_EFFECTIVE_CALLER_ID_NAME, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_EFFECTIVE_CALLER_ID_NUMBER)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_EFFECTIVE_CALLER_ID_NUMBER, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_OUTBOUND_CALLER_ID_NAME)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_OUTBOUND_CALLER_ID_NAME, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_OUTBOUND_CALLER_ID_NUMBER)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_OUTBOUND_CALLER_ID_NUMBER, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
                            } else if(xmlPullParser.getAttributeValue(null, Utils.XML_NAME).equalsIgnoreCase(Utils.XML_CALL_GROUP)) {
                                xmlPullParser.nextTag();
                                SharePreferenceUtils.put(context, Utils.XML_CALL_GROUP, xmlPullParser.getAttributeValue(null, Utils.XML_VALUE));
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

    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    public class httpRequestRunnable implements Runnable{
        private String fileName;

        public httpRequestRunnable(String fileName) {
            this.fileName = fileName;
        }
        @Override
        public void run() {
            LogUtils.e("main", "file : " + fileName);
            HttpUtils.submitPostData(fileName, null, null);
        }
    }
}
