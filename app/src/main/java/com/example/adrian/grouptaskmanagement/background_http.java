package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Adrian on 5/21/2018.
 */

public class background_http extends AsyncTask<String, Void, String> {
    Context currentAct;

    public background_http(Context currentAct) {
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
        try {
            return postData(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equals("failed")){
            listener.onUpdate("failed");
        }else {
            listener.onUpdate(s);
        }

    }

    private String postData(String topic) throws IOException {

        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            //Create data to send to server
            JSONObject dataToSend = new JSONObject();
            JSONObject dataToSend2 = new JSONObject();
            dataToSend2.put("body","This is a Firebase Cloud Messaging Topic Message!");
            dataToSend2.put("title","This is a Firebase Cloud Messaging Topic Message!");
            dataToSend.put("notification", dataToSend2);
            dataToSend.put("to", "/topics/"+topic);

            //Initialize and config request, then connect to server
            String urlpath = "https://fcm.googleapis.com/fcm/send";
            URL url = new URL(urlpath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // enable output (body data)
            urlConnection.setRequestProperty("Content-Type", "application/json"); // set header
            urlConnection.setRequestProperty("Authorization", "key=AAAAQ2NNxbo:APA91bFyfuWOeFmqz2GgvsIWJ6ZGO9rRt3y_ZvgIgU3qYy4idI08McSF7qcAKaxDJUc8xU1SrWWzQijADNQwwD3b8-JdcPjqQp33vvG14eq-kkvagesmbizf-JNI6I4uDrQa7JvZZFqU"); // set header
            urlConnection.connect();

            //Write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(dataToSend.toString());
            bufferedWriter.flush();

            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
        return result.toString();
    }


}
