package com.example.adrian.grouptaskmanagement;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 10/27/2018.
 */

public class a_firebase_image_show_act extends AppCompatActivity {
    private RecyclerView recyclerView;
    private a_firebase_image_adapter adapter;

    private ProgressBar mproProgressBar;

    private DatabaseReference databaseReference;
    private List<a_firebase_image_upload> uploads;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_firebase_image_show2);

        ImageView image_name;
        image_name = findViewById(R.id.patent);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/grouptaskmanagement-60230.appspot.com/o/uploads%2F1540630813866.jpg?alt=media&token=61aeeddf-1b3d-427c-bf0b-4a3ab3808bfc").into(image_name);
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
