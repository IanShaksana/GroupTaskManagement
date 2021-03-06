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

        final SharedPreferences preferences = this.getSharedPreferences("State", MODE_PRIVATE);
        String state = preferences.getString("Login_State", "");
        if (state.length() > 0) {
            LoginActivity.this.startActivityForResult(new Intent(LoginActivity.this, MainActivity.class), LOGIN_REQUEST);
            finish();
        }

        final Button login = (Button) findViewById(R.id.loginButton);
        final Button reg = (Button) findViewById(R.id.RegButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent()
                if (username.getText().toString().trim().equals("") || pass.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "Fill the form", Toast.LENGTH_SHORT).show();
                } else {
                    final String sendUsrPass = "login-" + username.getText().toString() + "-" + pass.getText().toString();
                    background loginBackground = new background(LoginActivity.this);
                    loginBackground.getListener(new background.OnUpdateListener() {
                        @Override
                        public void onUpdate(String obj) {
                            if (obj.contains("failed")) {
                                login.setEnabled(true);
                                reg.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = getSharedPreferences("State", MODE_PRIVATE);
                                String login_state = username.getText().toString();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Login_State", login_state);
                                editor.commit();
                                LoginActivity.this.startActivityForResult(new Intent(LoginActivity.this, MainActivity.class), LOGIN_REQUEST);
                                finish();
                            }
                        }
                    });
                    login.setEnabled(false);
                    reg.setEnabled(false);
                    loginBackground.execute(sendUsrPass);
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().equals("") || pass.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "Fill the form", Toast.LENGTH_SHORT).show();
                } else {
                    final String sendUsrPass = "register-" + username.getText().toString() + "-" + pass.getText().toString();
                    background loginBackground = new background(LoginActivity.this);
                    loginBackground.getListener(new background.OnUpdateListener() {
                        @Override
                        public void onUpdate(String obj) {
                            if (obj.contains("failed")) {
                                login.setEnabled(true);
                                reg.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Name already used", Toast.LENGTH_SHORT).show();
                            } else {
                                login.setEnabled(true);
                                reg.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "Try Login Now", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    login.setEnabled(false);
                    reg.setEnabled(false);
                    loginBackground.execute(sendUsrPass);
                }
            }
        });
    }
}
