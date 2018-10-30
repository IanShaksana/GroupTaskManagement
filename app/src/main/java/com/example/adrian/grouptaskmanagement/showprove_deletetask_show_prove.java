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
    Button in, out, conf;
    private StorageReference databaseReference;

    private ProgressBar mproProgressBar;

    private ImageView image_name;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Inbox");
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        view = inflater.inflate(R.layout.show_prove_image, container, false);
        mproProgressBar = view.findViewById(R.id.prog_circle);
        image_name = view.findViewById(R.id.pickedimage);

        background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                Toast.makeText(getContext(),"uploads/"+obj+".jpg",Toast.LENGTH_SHORT).show();
                String url ="uploads/"+obj+".jpg";


                databaseReference = FirebaseStorage.getInstance().getReference();
                databaseReference.child(url).getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getContext()).load(uri).into(image_name);
                                //mproProgressBar.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"complete",Toast.LENGTH_SHORT).show();
                            }
                        });

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
