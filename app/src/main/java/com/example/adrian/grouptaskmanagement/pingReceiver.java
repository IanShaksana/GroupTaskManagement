package com.example.adrian.grouptaskmanagement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class pingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context,"receiving",Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, pingService.class));
    }
}
