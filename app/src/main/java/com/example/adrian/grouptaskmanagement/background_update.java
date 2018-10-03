package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Adrian on 5/21/2018.
 */

public class background_update extends AsyncTask<String, Void, Void> {
    Context currentAct;
    String dataset;

    public background_update(Context currentAct) {
        this.currentAct = currentAct;
    }

    public interface OnUpdateListener {
        public void onUpdate(String obj);
    }

    OnUpdateListener listener;

    public void getListener(OnUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... strings) {
        while (true){
            TCP tcp = new TCP(currentAct);
            dataset =tcp.setupCon(strings[0]);
            listener.onUpdate(dataset);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
