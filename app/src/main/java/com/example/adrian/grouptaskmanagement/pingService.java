package com.example.adrian.grouptaskmanagement;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class pingService extends IntentService {

    public pingService() {
        super("pingService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        //Toast.makeText(getApplicationContext(),"receiving",Toast.LENGTH_SHORT).show();
        //Intent broadcastIntent = new Intent();
        //broadcastIntent.setAction("restartservice");
        //broadcastIntent.setClass(this, pingReceiver.class);
        //this.sendBroadcast(broadcastIntent);
        while (true){
            try {
                TCP ping = new TCP();
                ping.setupCon("ping");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }*/
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
