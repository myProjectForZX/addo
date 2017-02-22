package com.szp.tinyhttpserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBootReceiver extends BroadcastReceiver {
    public final static String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public MyBootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)) {
            Intent serIntent= new Intent(context, HttpService.class);
            serIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(serIntent);
        }
    }
}
