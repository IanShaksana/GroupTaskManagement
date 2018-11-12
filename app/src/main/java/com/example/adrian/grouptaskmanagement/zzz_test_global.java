package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.onesignal.OneSignal;

/**
 * Created by Adrian on 11/5/2018.
 */

public class zzz_test_global extends AppCompatActivity {
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    TextView txt1;
    EditText edx1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zzz_global);
        btn1 = findViewById(R.id.Show_Time);
        btn2 = findViewById(R.id.Show_IP);
        btn3 = findViewById(R.id.PING);
        btn4 = findViewById(R.id.sendNotif);
        btn5 = findViewById(R.id.unsub);
        btn6 = findViewById(R.id.test);
        btn7 = findViewById(R.id.test1);
        txt1 = findViewById(R.id.Result);
        edx1 = findViewById(R.id.topic);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        sendBroadcast(new Intent(this,MyWakefulReceiver.class));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edx1.getText().toString().trim().equals("")){
                    Toast.makeText(zzz_test_global.this, "no topic", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseMessaging.getInstance().subscribeToTopic(edx1.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
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
                            btn4.setEnabled(true);
                            btn5.setEnabled(true);
                            txt1.setText(obj);
                        }
                    });
                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                    btn5.setEnabled(false);
                    b1.execute("showtime");
                }

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
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        txt1.setText(obj);
                    }
                });
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
                btn4.setEnabled(false);
                btn5.setEnabled(false);
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
                        btn4.setEnabled(true);
                        btn5.setEnabled(true);
                        txt1.setText(obj);
                    }
                });
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
                btn4.setEnabled(false);
                btn5.setEnabled(false);
                b3.execute("PING");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edx1.getText().toString().trim().equals("")){
                    Toast.makeText(zzz_test_global.this,"no topics",Toast.LENGTH_SHORT).show();

                }else {
                    background_http b4 = new background_http(zzz_test_global.this);
                    b4.getListener(new background_http.OnUpdateListener() {
                        @Override
                        public void onUpdate(String obj) {
                            btn1.setEnabled(true);
                            btn2.setEnabled(true);
                            btn3.setEnabled(true);
                            btn4.setEnabled(true);
                            btn5.setEnabled(true);
                            txt1.setText(obj);
                        }
                    });
                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                    btn5.setEnabled(false);
                    b4.execute(edx1.getText().toString());
                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edx1.getText().toString().trim().equals("")){
                }else {
                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                    btn5.setEnabled(false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(edx1.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(zzz_test_global.this,"Unsubscribe",Toast.LENGTH_SHORT).show();
                            btn1.setEnabled(true);
                            btn2.setEnabled(true);
                            btn3.setEnabled(true);
                            btn4.setEnabled(true);
                            btn5.setEnabled(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(zzz_test_global.this,"Failed Unsubscribe",Toast.LENGTH_SHORT).show();
                            btn1.setEnabled(true);
                            btn2.setEnabled(true);
                            btn3.setEnabled(true);
                            btn4.setEnabled(true);
                            btn5.setEnabled(true);
                        }
                    });


                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background_global b3 = new background_global(zzz_test_global.this);
                b3.getListener(new background_global.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        txt1.setText(obj);
                    }
                });
                b3.execute("ping");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background b3 = new background(zzz_test_global.this);
                b3.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        txt1.setText(obj);
                    }
                });
                b3.execute("test2");
            }
        });

    }
}
