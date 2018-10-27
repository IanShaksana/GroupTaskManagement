package com.example.adrian.grouptaskmanagement;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 10/27/2018.
 */

public class a_firebase_image_show_act extends AppCompatActivity {
    private RecyclerView recyclerView;
    private a_firebase_image_adapter adapter;

    private ProgressBar mproProgressBar;
    private a_firebase_image_upload upload;

    private ImageView image_name;

    private StorageReference databaseReference;
    //private List<a_firebase_image_upload> uploads;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_firebase_image_show2);


        image_name = findViewById(R.id.patent);
        mproProgressBar = findViewById(R.id.prog_circle);
        //Glide.with(this).load("gs://grouptaskmanagement-60230.appspot.com/uploads/1540630813866.jpg").into(image_name);
        //mproProgressBar.setVisibility(View.INVISIBLE);

        databaseReference = FirebaseStorage.getInstance().getReference();
        databaseReference.child("uploads/ss-1540623869720.jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(a_firebase_image_show_act.this).load(uri).into(image_name);
                        mproProgressBar.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(a_firebase_image_show_act.this,"complete",Toast.LENGTH_SHORT).show();
                    }
                });




        /*
        recyclerView = findViewById(R.id.recyclerimage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mproProgressBar = findViewById(R.id.prog_circle);

        uploads = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    a_firebase_image_upload upload = postSnapshot.getValue(a_firebase_image_upload.class);
                    uploads.add(upload);
                }
                adapter = new a_firebase_image_adapter(a_firebase_image_show_act.this,uploads);
                recyclerView.setAdapter(adapter);
                mproProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(a_firebase_image_show_act.this,"no permit",Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
