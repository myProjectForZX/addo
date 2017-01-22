package com.szp.tinyhttpserver;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import org.nanohttpd.util.ServerRunner;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainHttpServer extends AppCompatActivity {
    private Handler mHanlder = new myHandler();
    private Context mContext;
    private TextView showInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_http_server);
        mContext = getApplicationContext();
        showInformation = (TextView)findViewById(R.id.show_information);

        ServerRunner.run(NanoHttpServer.class, mContext, mHanlder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ServerRunner.exit();
    }

    private class myHandler extends  Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Utils.MESSAGE_HTTP_STATUS:
                    if(msg.arg1 == Utils.HTTP_OK) {
                        showInformation.setText(String.format(getResources().getString(R.string.show_information), getHostIP()));
                    } else if (msg.arg1 == Utils.HTTP_FAIL) {
                        showInformation.setText(getResources().getString(R.string.start_http_fail));
                    }
                    break;
            }
        }
    }

    public static String getHostIP() {

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