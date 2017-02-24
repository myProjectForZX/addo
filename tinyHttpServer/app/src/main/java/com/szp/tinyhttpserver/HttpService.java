package com.szp.tinyhttpserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import org.nanohttpd.util.ServerRunner;

public class HttpService extends Service {
    private httpServieHandler serviceHander;

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
        HandlerThread thread = new HandlerThread("httpservice");
        thread.start();

        serviceHander = new httpServieHandler(thread.getLooper());
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

        serviceHander.postAtTime(httpRequest, 1000* 3);
    }

    private class httpRequestRunnable implements Runnable{
        private String fileName;

        public httpRequestRunnable(String fileName) {
            this.fileName = fileName;
        }
        @Override
        public void run() {
            HttpUtils.submitPostData(fileName, getApplicationContext(), serviceHander);
        }
    }

    private class httpServieHandler extends Handler{
        public httpServieHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Utils.MESSAGE_DOWNLOAD_CONFIG_STATUS:
                    if(msg.arg1 == Utils.HTTP_OK) {
                    } else if (msg.arg1 == Utils.HTTP_FAIL) {
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
