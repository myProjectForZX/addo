package com.szp.tinyhttpserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.nanohttpd.util.ServerRunner;

public class HttpService extends Service {
    public HttpService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ServerRunner.run(NanoHttpServer.class, getApplicationContext(), null);
        sendPostRequestToRrefreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendPostRequestToRrefreshData() {
        httpRequestRunnable httpRequest = new httpRequestRunnable(Utils.BASE_CONFIG_XML_URL);
        Thread thread = new Thread(httpRequest);
        thread.start();
    }

    private class httpRequestRunnable implements Runnable{
        private String fileName;

        public httpRequestRunnable(String fileName) {
            this.fileName = fileName;
        }
        @Override
        public void run() {
            HttpUtils.submitPostData(fileName, getApplicationContext(), null);
        }
    }
}
