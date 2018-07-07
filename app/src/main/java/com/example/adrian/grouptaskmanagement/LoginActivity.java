package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Adrian on 5/18/2018.
 */

public class LoginActivity extends AppCompatActivity {
    public static final int LOGIN_REQUEST = 100;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText username = (EditText) findViewById(R.id.userInput);
        final EditText pass = (EditText) findViewById(R.id.passInput);


        final Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent()
                if (username.getText().toString().equals("")|| pass.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"Fill the form",Toast.LENGTH_SHORT).show();
                }else{
                    final String sendUsrPass = "login-"+username.getText().toString()+"-"+pass.getText().toString();
                    background loginBackground = new background(LoginActivity.this);
                    loginBackground.getListener(new background.OnUpdateListener() {
                        @Override
                        public void onUpdate(String obj) {
                                Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = getSharedPreferences("State",MODE_PRIVATE);
                                String login_state = username.getText().toString();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Login_State",login_state);
                                editor.commit();
                                LoginActivity.this.startActivityForResult(new Intent(LoginActivity.this,MainActivity.class),LOGIN_REQUEST);
                                finish();
                        }
                    });
                    loginBackground.execute(sendUsrPass);
                }

            }
        });

    }

}
