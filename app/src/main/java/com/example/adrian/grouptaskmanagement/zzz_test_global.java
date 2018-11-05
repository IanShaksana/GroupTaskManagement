package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OneSignal;

/**
 * Created by Adrian on 11/5/2018.
 */

public class zzz_test_global extends AppCompatActivity {
    Button btn1,btn2,btn3;
    TextView txt1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zzz_global);
        btn1 = findViewById(R.id.Show_Time);
        btn2 = findViewById(R.id.Show_IP);
        btn3 = findViewById(R.id.PING);
        txt1 = findViewById(R.id.Result);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        sendBroadcast(new Intent(this,MyWakefulReceiver.class));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "subsribed";
                        if (!task.isSuccessful()) {
                            msg = "notsubsribed";
                        }
                        Toast.makeText(zzz_test_global.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
                background_global b1 = new background_global(zzz_test_global.this);
                b1.getListener(new background_global.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        btn1.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        txt1.setText(obj);
                    }
                });
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
                b1.execute("showtime");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background_global b2 = new background_global(zzz_test_global.this);
                b2.getListener(new background_global.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        btn1.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        txt1.setText(obj);
                    }
                });
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
                b2.execute("showIP");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background_global b3 = new background_global(zzz_test_global.this);
                b3.getListener(new background_global.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        btn1.setEnabled(true);
                        btn2.setEnabled(true);
                        btn3.setEnabled(true);
                        txt1.setText(obj);
                    }
                });
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
                b3.execute("PING");
            }
        });
    }
}
