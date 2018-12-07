package com.example.adrian.grouptaskmanagement;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Adrian on 11/2/2018.
 */

public class zz_FirebaseService extends FirebaseMessagingService {
    private static final String TAG = "zz_FirebaseService";

    public zz_FirebaseService() {
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("msg", "onMessageReceived: " + remoteMessage.getData().get("message"));
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";

        //NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        //inboxStyle.setBigContentTitle("notif");
        //inboxStyle.addLine("no 1");
        //inboxStyle.addLine("no 2");
        //inboxStyle.setSummaryText(" summary ");

        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.image))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                //.setStyle(inboxStyle);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        String[] split = remoteMessage.getMessageId().split("%");
        String[] split1 = split[0].split(":");
        long split3 = Long.parseLong(split1[1])/100000;
        int id = (int)split3;
        manager.notify(id, builder.build());
    }

    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String title,String messageBody) {
        //Intent intent = new Intent(this,zz_chat_1.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
        //        PendingIntent.FLAG_ONE_SHOT);

        String channelId = "0";
        Notification notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody).build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());

        manager.notify(0 /* ID of notification */, notificationBuilder);
    }
}
