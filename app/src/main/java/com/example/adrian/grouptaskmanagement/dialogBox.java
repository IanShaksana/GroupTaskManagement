package com.example.adrian.grouptaskmanagement;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Adrian on 5/30/2018.
 */



public class dialogBox extends AppCompatActivity implements dialogfragment.dialogListener {
    TextView lulz;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        Button button = (Button) findViewById(R.id.dial);
        lulz =(TextView) findViewById(R.id.thisis);
        lulz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(dialogBox.this,"clicked",Toast.LENGTH_SHORT).show();
                opendialog();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
    }

    private void opendialog() {
        dialogfragment dialogfragment = new dialogfragment();
        dialogfragment.show(getSupportFragmentManager(),"exa");
    }

    @Override
    public void apply(String wasd) {
        lulz.setText(wasd);
    }
}