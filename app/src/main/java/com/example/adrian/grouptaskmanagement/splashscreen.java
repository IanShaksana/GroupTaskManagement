package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splashscreen);
        startActivity(new Intent(splashscreen.this, LoginActivity.class));
        finish();
    }
}
