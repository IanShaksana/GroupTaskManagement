package com.example.adrian.grouptaskmanagement;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * Created by Adrian on 10/19/2018.
 */

public class a_firebase_image extends AppCompatActivity {

    private  static final int PICK_IMAGE_REQUEST =1;

    private Button choose,upload,show;
    private EditText name;
    private ImageView image;
    private ProgressBar progbar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDataRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_firebase_image);

        choose = findViewById(R.id.click_me);
        upload = findViewById(R.id.upload);
        show = findViewById(R.id.shupload);
        name = findViewById(R.id.image_name);
        image = findViewById(R.id.pickedimage);
        progbar = findViewById(R.id.progbar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDataRef = FirebaseDatabase.getInstance().getReference("uploads");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfile();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask!=null && mUploadTask.isInProgress()){
                    Toast.makeText(a_firebase_image.this,"something uploaded",Toast.LENGTH_SHORT).show();
                }else {
                    uploadfile();
                }

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openimage();
            }
        });

    }
    private void openfile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            mImageUri = data.getData();
            //Picasso.with(this).load(mImageUri).into(image);
            Picasso.with(this).load(mImageUri).into(image);
            image.setImageURI(mImageUri);
        }
    }

    private String getfileextension (Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadfile(){
        if (mImageUri!= null){
            final StorageReference fileRef = mStorageRef.child(name.getText().toString()+System.currentTimeMillis()+"."+getfileextension(mImageUri));
            mUploadTask=fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //progbar.setProgress(0);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progbar.setProgress(0);
                        }
                    },500);
                    Toast.makeText(a_firebase_image.this,"complete",Toast.LENGTH_SHORT).show();
                    //
                    a_firebase_image_upload upload = new a_firebase_image_upload(name.getText().toString().trim(),taskSnapshot.getUploadSessionUri().toString());
                    //
                    a_firebase_image_upload upload1 = new a_firebase_image_upload(name.getText().toString().trim(),fileRef.getDownloadUrl().toString());

                    String uploadid = mDataRef.push().getKey();
                    mDataRef.child(uploadid).setValue(upload1);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(a_firebase_image.this,"no file",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progbar.setProgress((int) progress);
                }
            });
        }else {
            Toast.makeText(this,"no file",Toast.LENGTH_SHORT).show();
        }

    }

    private void openimage(){
        Intent intent = new Intent(this,a_firebase_image_show_act.class);
        startActivity(intent);
    }


}
