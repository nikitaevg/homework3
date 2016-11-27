package com.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenOnReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent i = new Intent(context, LoadService.class);
        context.startService(i);
    }
}
