package com.example.adrian.grouptaskmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 9/4/2018.
 */

public class leader_assign2 extends Fragment implements  dialog_yes_no_assign.dialogListener_yes_no_assign {

    View view;
    String Tag;
    String ID_Task;
    String ID_User;
    String ID_Job;
    String state;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref ;
    private Frag_Offer_avaible_task_recycler_adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        Tag = getTag();
        String[] Tagsplit = Tag.split("-");
        ID_User = Tagsplit[1];
        ID_Job = Tagsplit[0];
        ref = db.collection("List_Job/"+Tagsplit[0]+"/List_Task");
        view = inflater.inflate(R.layout.list_assign_task_leader2, container, false);
        setup(view);
        return view;
    }

    private void opendialog() {
        dialog_yes_no_assign dialogfragment = new dialog_yes_no_assign();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");

    }

    @Override
    public void apply_assign(String wasd) {
        background background1 = new background(getContext());
        background1.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                if(obj.contains("failed")){

                }else {
                    getFragmentManager().popBackStack();
                }
                /*
                CollectionReference notebookRef1 = FirebaseFirestore.getInstance()
                        .collection("Message/"+ID_User+"/"+"inbox/");
                notebookRef1.add(new Frag_Inbox_recycler(state,"you got invitation to this task: "+ID_Task,"send","assign-task",obj));
                Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();*/
            }
        });
        background1.execute("msg_assign-" + ID_User + "-" + ID_Task + "-" + ID_Job);
    }

    private void setup(View view){
        Query query = ref.whereEqualTo("worker","none");
        FirestoreRecyclerOptions<Frag_Offer_recycler_task> options = new FirestoreRecyclerOptions.Builder<Frag_Offer_recycler_task>().setQuery(query,Frag_Offer_recycler_task.class).build();
        adapter = new Frag_Offer_avaible_task_recycler_adapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.list_assign_task_leader);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.setOnItem(new Frag_Offer_avaible_task_recycler_adapter.onitemclickListener() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                String id = documentSnapshot.getId();
                ID_Task = id;
                Toast.makeText(getContext(), "ID_Job: "+ID_Job+" ID_Task: "+ID_Task, Toast.LENGTH_SHORT).show();
                opendialog();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
