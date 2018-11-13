package com.example.adrian.grouptaskmanagement;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class showprove_deletetask_show_upload extends Fragment {
    @Nullable
    View view;
    String state,TAG,IDTASK,IDJOB;
    private  static final int PICK_IMAGE_REQUEST =1;
    Button in, out;
    private ImageView image;
    private ProgressBar progbar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDataRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageTask mUploadTask;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Inbox");
        String[] TAGSPLIT =getTag().split("-");
        IDJOB = TAGSPLIT[1];
        IDTASK = TAGSPLIT[0];
        TAG = IDTASK+"_Prove_"+System.currentTimeMillis();

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDataRef = FirebaseDatabase.getInstance().getReference("uploads");
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
        out = view.findViewById(R.id.upload);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask!=null && mUploadTask.isInProgress()){
                    Toast.makeText(getContext(),"Uploading",Toast.LENGTH_SHORT).show();
                }else {
                    String extension = uploadfile();
                    if(extension.equals("nofile")){

                    }else {
                        background upload_img_nm = new background(getContext());
                        upload_img_nm.getListener(new background.OnUpdateListener() {
                            @Override
                            public void onUpdate(String obj) {
                                if(obj.contains("failed")){
                                    update();
                                }else {
                                    DocumentReference doc1 = db.document("List_Job/"+IDJOB+"/List_Task/"+IDTASK);
                                    doc1.update("status","Waiting for review");
                                }
                            }
                        });
                        upload_img_nm.execute("upload_img_nm-"+IDTASK+"-"+TAG+"."+extension);
                    }
                }

            }
        });
        progbar = view.findViewById(R.id.progbar);
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
            Picasso.with(getContext()).load(mImageUri).into(image);
            image.setImageURI(mImageUri);
        }
    }

    private String getfileextension (Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private String uploadfile(){
        if (mImageUri!= null){
            final StorageReference fileRef = mStorageRef.child(TAG+"."+getfileextension(mImageUri));
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
                    Toast.makeText(getContext(),"complete",Toast.LENGTH_SHORT).show();
                    a_firebase_image_upload upload1 = new a_firebase_image_upload(TAG,fileRef.getDownloadUrl().toString());
                    String uploadid = mDataRef.push().getKey();
                    mDataRef.child(uploadid).setValue(upload1);
                    getFragmentManager().popBackStack();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),"no file",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progbar.setProgress((int) progress);
                }
            });
            return getfileextension(mImageUri);
        }else {
            Toast.makeText(getContext(),"no file",Toast.LENGTH_SHORT).show();
            return "nofile";
        }

    }
}
