package com.example.adrian.grouptaskmanagement;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class z_firebase extends AppCompatActivity {

    private  static final  String tag = "MainFirebase";

    private  static final  String key_title = "name";
    private  static final  String key_desc = "desc";

    private EditText name,desc,numb;
    private Button button1,button2,button3,button4,button5,button6,button7;
    private TextView loadtext,updttext,data;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("listuser/ian");
    private DocumentReference noteRef2 = db.document("listuser/ren");
    private CollectionReference colRef = db.collection("listuser");

    //private ListenerRegistration fireListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_firebase);

        desc = (EditText) findViewById(R.id.desc);
        name = (EditText) findViewById(R.id.name);
        numb = (EditText) findViewById(R.id.numb);
        button1 = (Button) findViewById(R.id.button1);
        loadtext = (TextView) findViewById(R.id.loadtext);
        button2 = (Button) findViewById(R.id.button2);
        //updttext = (TextView) findViewById(R.id.updttext);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        data = (TextView) findViewById(R.id.data);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser(view);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noteRef = db.document("listuser/"+name.getText().toString());
                loadUser(view);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDesc();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDesc();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDoc();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loads();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*noteRef.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(z_firebase.this,"something when wrong",Toast.LENGTH_SHORT).show();
                    Log.d(tag,e.toString());
                    return;
                }

                if(documentSnapshot.exists()){
                    Toast.makeText(z_firebase.this,"exist",Toast.LENGTH_SHORT).show();
                    z_firebase1 note = documentSnapshot.toObject(z_firebase1.class);


                    *//*String name = documentSnapshot.getString(key_title);
                    String desc = documentSnapshot.getString(key_desc);*//*

                    String name = note.getName();
                    String desc = note.getDesc();
                    updttext.setText(name +" "+desc);
                }else {
                    updttext.setText("");
                }

            }
        });*/
        colRef.orderBy("numb", Query.Direction.ASCENDING).addSnapshotListener(this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(z_firebase.this,"something when wrong",Toast.LENGTH_SHORT).show();
                    Log.d(tag,e.toString());
                    return;
                }
                String datas="";
                for (QueryDocumentSnapshot documentSnapshot1: documentSnapshot){
                    z_firebase1 note = documentSnapshot1.toObject(z_firebase1.class);
                    note.setID(documentSnapshot1.getId());

                    String documentID = note.getID();
                    String titles = note.getName();
                    String desc = note.getDesc();
                    String numb = note.getNumb();
                    datas += documentID+" "+titles+" "+desc+" "+numb+"\n\n";
                }
                data.setText(datas);
            }
        });

    }

    public void saveUser (View v){
        String userName = name.getText().toString();
        String userDesc = desc.getText().toString();

        /*Map<String,Object> note = new HashMap<>();
        note.put(key_title,userName);
        note.put(key_desc,userDesc);*/

        z_firebase1 note = new z_firebase1(userDesc,userName);

        db.collection("listuser").document(userName).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(z_firebase.this,"sukses",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(z_firebase.this,"error",Toast.LENGTH_SHORT).show();
                Log.d(tag,e.toString());
            }
        });

    }

    public void loadUser (View v){
        //noteRef.set(note)
        noteRef2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Toast.makeText(z_firebase.this,"exist",Toast.LENGTH_SHORT).show();
                    z_firebase1 note = documentSnapshot.toObject(z_firebase1.class);


                    /*String name = documentSnapshot.getString(key_title);
                    String desc = documentSnapshot.getString(key_desc);*/

                    String name = note.getName();
                    String desc = note.getDesc();

                    loadtext.setText(name +" "+desc);

                }else {
                    Toast.makeText(z_firebase.this,"does not exist",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(z_firebase.this,"error",Toast.LENGTH_SHORT).show();
                Log.d(tag,e.toString());
            }
        });
    }

    public void updateDesc(){
        String userDesc = desc.getText().toString();
        noteRef.update(key_desc,userDesc);
    }

    public void deleteDesc(){
        //String userDesc = desc.getText().toString();
        noteRef.update(key_desc, FieldValue.delete());
    }

    public void deleteDoc(){
        String userName = name.getText().toString();
        DocumentReference choosenDoc = db.document("listuser/"+userName);
        noteRef.delete();
    }

    public void add(){
        String userName = name.getText().toString();
        String userDesc = desc.getText().toString();
        String userNumb = numb.getText().toString();

        z_firebase1 note = new z_firebase1(userDesc,userName,userNumb);

        //colRef.add(note);
        colRef.document(userName).set(note);
    }

    public void loads(){
        colRef.orderBy("numb", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String datas="";
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    z_firebase1 note = documentSnapshot.toObject(z_firebase1.class);
                    note.setID(documentSnapshot.getId());

                    String documentID = note.getID();
                    String titles = note.getName();
                    String desc = note.getDesc();
                    String numb = note.getNumb();
                    datas += documentID+" "+titles+" "+desc+" "+numb+"\n\n";
                }
                data.setText(datas);
            }
        });
    }
}
