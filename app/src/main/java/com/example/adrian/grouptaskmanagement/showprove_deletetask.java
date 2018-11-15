package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class showprove_deletetask extends Fragment {
    @Nullable
    View view;
    String state, status;
    ListView listView;
    Button show, del;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Inbox");
        final String[] TAGSPLIT = getTag().split("-");
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        view = inflater.inflate(R.layout.showprove_deletetask, container, false);

        show =  view.findViewById(R.id.button1);
        del = view.findViewById(R.id.button2);

        final DocumentReference doc1 = db.document("List_Job/"+TAGSPLIT[1]+"/List_Task/"+TAGSPLIT[0]);
        doc1.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(getContext(),"something when wrong",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(documentSnapshot.exists()){

                    Frag_Offer_recycler_task note = documentSnapshot.toObject( Frag_Offer_recycler_task.class);
                    status = note.getStatus();
                    if(status.equals("approved")){
                        show.setEnabled(false);
                        del.setEnabled(false);
                        Toast.makeText(getContext(),"Task Has been Approved",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new showprove_deletetask_show_prove(),getTag()).addToBackStack(null).commit();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setEnabled(false);
                del.setEnabled(false);
                background background1 = new background(getContext());
                background1.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        if(obj.contains("failed")){
                            show.setEnabled(true);
                            del.setEnabled(true);
                        }else{
                            doc1.delete();
                            getFragmentManager().popBackStack();
                        }
                    }
                });
                background1.execute("Delete_task-" + TAGSPLIT[0]);
            }
        });

        return view;
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof showprove_deletetask) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
