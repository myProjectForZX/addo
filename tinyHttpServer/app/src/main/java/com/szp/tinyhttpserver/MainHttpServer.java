package com.szp.tinyhttpserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.nanohttpd.util.ServerRunner;

public class MainHttpServer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_http_server);

        ServerRunner.run(NanoHttpServer.class, getAssets(), getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ServerRunner.exit();
    }
}