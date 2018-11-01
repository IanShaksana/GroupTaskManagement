package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class showprove_deletetask_show_prove extends Fragment {
    @Nullable
    View view;
    String state, unprocessed_msg;
    ListView listView;
    Button app, diss;
    private StorageReference databaseReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref;

    private ProgressBar mproProgressBar;

    private ImageView image_name;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        view = inflater.inflate(R.layout.show_prove_image, container, false);

        String[] TAGSPLIT = getTag().split("-");
        final String IDTASK = TAGSPLIT[0];
        final String IDJOB = TAGSPLIT[1];

        mproProgressBar = view.findViewById(R.id.progbar);
        image_name = view.findViewById(R.id.pickedimage);
        app = view.findViewById(R.id.APP);
        diss = view.findViewById(R.id.DISS);

        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background background1 = new background(getContext());
                background1.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        DocumentReference doc1 = db.document("List_Job/"+IDJOB+"/List_Task/"+IDTASK);
                        doc1.update("status","approved");
                        getFragmentManager().popBackStack();
                    }
                });
                background1.execute("approve_yes-" + IDTASK + "-" + IDTASK);
            }
        });

        diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background background1 = new background(getContext());
                background1.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        DocumentReference doc1 = db.document("List_Job/"+IDJOB+"/List_Task/"+IDTASK);
                        doc1.update("status","no");
                        update();
                    }
                });
                background1.execute("approve_no-" +IDTASK);
            }
        });

        background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                if (obj.equals("no prove")){
                    mproProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(),obj,Toast.LENGTH_SHORT).show();
                    app.setEnabled(false);
                    diss.setEnabled(false);
                }else{
                    Toast.makeText(getContext(),"uploads/"+obj+".jpg",Toast.LENGTH_SHORT).show();
                    String url ="uploads/"+obj+".jpg";


                    databaseReference = FirebaseStorage.getInstance().getReference();
                    databaseReference.child(url).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(getContext()).load(uri).into(image_name);
                                    mproProgressBar.setVisibility(View.INVISIBLE);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"failed loading",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        background.execute("req_prove-"+getTag());


        return view;
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof showprove_deletetask_show_prove) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
