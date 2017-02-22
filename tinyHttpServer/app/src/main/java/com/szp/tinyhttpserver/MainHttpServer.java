package com.szp.tinyhttpserver;

import android.content.Context;
import android.content.res.AssetManager;
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
import android.widget.Toast;

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
import java.util.Map;

import static java.lang.System.in;

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

        //ServerRunner.exit();
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
                        Toast.makeText(mContext, getNanoHttpServiceAddress(), Toast.LENGTH_LONG).show();
                    } else if (msg.arg1 == Utils.HTTP_FAIL) {
                        Toast.makeText(mContext, "run http server failed", Toast.LENGTH_LONG).show();
                    }
                    break;
                case Utils.MESSAGE_REFRESH_DATA:
                    refreshData();
                    break;
                case Utils.MESSAGE_DOWNLOAD_CONFIG_STATUS:
                    if(msg.arg1 == Utils.HTTP_OK) {
                        refreshData();
                    } else if (msg.arg1 == Utils.HTTP_FAIL) {

                    }
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
                    httpRequestRunnable httpRequest = new httpRequestRunnable(Utils.BASE_CONFIG_XML_URL);
                    Thread thread = new Thread(httpRequest);
                    thread.start();
                    break;
                default:
                    break;
            }
        }
    }

    private class httpRequestRunnable implements Runnable{
        private String fileName;

        public httpRequestRunnable(String fileName) {
            this.fileName = fileName;
        }
        @Override
        public void run() {
            LogUtils.e("main", "file : " + fileName);
            HttpUtils.submitPostData(fileName, mContext, mHanlder);
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