package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Adrian on 5/21/2018.
 */

public class background_global extends AsyncTask<String, Void, String> {
    Context currentAct;

    public background_global(Context currentAct) {
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
    protected String doInBackground(String... strings) {
        TCP_global tcp = new TCP_global(currentAct);
        return tcp.setupCon(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {

        if (listener != null && !s.contains("failed")) {
            listener.onUpdate(s);
        } else {
            Toast.makeText(currentAct, s + " from background", Toast.LENGTH_SHORT).show();
            listener.onUpdate("failed");
        }
    }
}
