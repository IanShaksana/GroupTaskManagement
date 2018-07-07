package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class camera_test extends AppCompatActivity {
ImageView IMG;
Button buttonC,buttonU;
Bitmap imageBitmap;
static final  int Request=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        IMG =(ImageView) findViewById(R.id.IMG);
        buttonC = (Button) findViewById(R.id.takepicture);
        buttonU = (Button) findViewById(R.id.upload);

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        buttonU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background background1 = new background(camera_test.this);
                background1.execute("halo");

                //backgroundC background = new backgroundC(getApplicationContext());
                //background.execute(imageBitmap);

            }
        });



    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Request);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            IMG.setImageBitmap(imageBitmap);
        }
    }
}
