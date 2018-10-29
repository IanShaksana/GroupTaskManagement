package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class showprove_deletetask_show_upload extends Fragment {
    @Nullable
    View view;
    String state;
    private  static final int PICK_IMAGE_REQUEST =1;
    Button in, out, conf;
    private EditText name;
    private ImageView image;
    private ProgressBar progbar;

    private Uri mImageUri;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Inbox");
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        view = inflater.inflate(R.layout.upload_prove_image, container, false);
        image = view.findViewById(R.id.pickedimage);
        in = view.findViewById(R.id.click_me);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfile();
            }
        });
        return view;
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof showprove_deletetask_show_upload) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void openfile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            mImageUri = data.getData();
            //Picasso.with(this).load(mImageUri).into(image);
            Picasso.with(getContext()).load(mImageUri).into(image);
            image.setImageURI(mImageUri);
        }
    }
}
