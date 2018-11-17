package com.example.adrian.grouptaskmanagement;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Inbox2 extends Fragment {
    @Nullable
    View view;
    String state, unprocessed_msg;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref;
    private Frag_Inbox_adapter adapter;
    String ID_Job_vote;
    String ID_Job;
    String ID_Task;
    String choosen;
    String ID_User_Worker;
    String docID;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        ref = db.collection("Message/"+state+"/inbox");
        view = inflater.inflate(R.layout.inbox2, container, false);
        setup(view);

        return view;
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Inbox2) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void setup(final View view){
        final View view1=view;
        Query query = ref.orderBy("from",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Frag_Inbox_recycler> options = new FirestoreRecyclerOptions.Builder<Frag_Inbox_recycler>().setQuery(query,Frag_Inbox_recycler.class).build();
        adapter = new Frag_Inbox_adapter(options);

        final RecyclerView recyclerView = view.findViewById(R.id.list_message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItem(new Frag_Inbox_adapter.onitemclickListener() {
            @Override
            public void onitemclick(final DocumentSnapshot documentSnapshot, final int position) {
                final Frag_Inbox_recycler note = documentSnapshot.toObject(Frag_Inbox_recycler.class);
                docID = documentSnapshot.getId();
                choosen = note.getMssgCode();
                String[] mssgCodesplit = choosen.split("-");
                ID_Task = mssgCodesplit[2];
                ID_Job = mssgCodesplit[2];
                if(mssgCodesplit.length>3){
                    ID_Job_vote = mssgCodesplit[3];
                }
                ID_User_Worker =note.getFrom() ;

                String type = note.getType();
                //Toast.makeText(getContext(), "type: "+type+" id: "+id, Toast.LENGTH_SHORT).show();


                switch (type) {
                    case "applyTask":
                        String[] optApply = {"Compare", "Accept", "Reject"};
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setTitle("Options");
                        builder1.setItems(optApply, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // the user clicked on colors[which]
                                switch (which) {
                                    case 0:
                                        //Toast.makeText(getContext(), "View Worker", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Status_W(), ID_User_Worker+"-"+ID_Task).addToBackStack(null).commit();
                                        break;
                                    case 1:
                                        //Toast.makeText(getContext(), "Accept", Toast.LENGTH_SHORT).show();
                                        background background2 = new background(getContext());
                                        background2.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                if (obj.contains("failed")){

                                                }else{/*
                                                    String[] objsplit =obj.split("-");
                                                    DocumentReference doc1 = db.document("List_Job/"+objsplit[1]);
                                                    DocumentReference doc2 = db.document("List_Job/"+objsplit[1]+"/List_Task/"+ID_Task);
                                                    doc1.update("slotnow",objsplit[2]);
                                                    doc2.update("worker",ID_User_Worker);
                                                    adapter.delitem(position);*/
                                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                        background2.execute("reply_message-apply_yes-" + ID_Task + "-" + ID_User_Worker + "-" + choosen + "-" +docID);
                                        break;
                                    case 2:
                                        //Toast.makeText(getContext(), "Reject", Toast.LENGTH_SHORT).show();
                                        background background3 = new background(getContext());
                                        background3.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                if (obj.contains("failed")){

                                                }else{
                                                    //adapter.delitem(position);
                                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        background3.execute("reply_message-apply_reject-" + ID_Task + "-" + ID_User_Worker + "-" + choosen + "-" +docID);
                                        break;
                                }
                            }
                        });
                        builder1.show();
                        break;
                    case "assignTask":
                        String[] optAssg = {"View Task", "Accept Task", "Reject Task"};

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                        builder2.setTitle("Options");
                        builder2.setItems(optAssg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // the user clicked on colors[which]
                                switch (which) {
                                    case 0:
                                        //Toast.makeText(getContext(), "View Task", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_detail_task(), ID_Task).addToBackStack(null).commit();
                                        break;
                                    case 1:
                                        //Toast.makeText(getContext(), "Accept Task", Toast.LENGTH_SHORT).show();
                                        background background1 = new background(getContext());
                                        background1.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                if (obj.contains("failed")){

                                                }else {/*
                                                    String[] objsplit = obj.split("-");
                                                    DocumentReference doc2 = db.document("List_Job/"+objsplit[0]+"/List_Task/"+ID_Task);
                                                    doc2.update("worker",objsplit[1]);
                                                    adapter.delitem(position);*/
                                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        background1.execute("reply_message-assign_yes-" + ID_Task + "-" +  "-" + choosen+"-"+docID);
                                        break;
                                    case 2:
                                        //Toast.makeText(getContext(), "Reject Task", Toast.LENGTH_SHORT).show();
                                        background background2 = new background(getContext());
                                        background2.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                if (obj.contains("failed")){

                                                }else {
                                                    //adapter.delitem(position);
                                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        background2.execute("reply_message-assign_reject-" + ID_Task + "-"  + "-" + choosen+"-"+docID);
                                        break;
                                }
                            }
                        });
                        builder2.show();
                        break;

                    case "inviteJob":
                        String[] optInvt = {"View Job", "View Task", "Accept Job", "Reject Job"};

                        AlertDialog.Builder builder3 = new AlertDialog.Builder(getContext());
                        builder3.setTitle("Options");
                        builder3.setItems(optInvt, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Toast.makeText(getContext(), "View Job", Toast.LENGTH_SHORT).show();
                                        //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_assign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                        break;
                                    case 1:
                                        Toast.makeText(getContext(), "View Task", Toast.LENGTH_SHORT).show();
                                        //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_reassign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                        break;
                                    case 2:
                                        //Toast.makeText(getContext(), "Accept job", Toast.LENGTH_SHORT).show();
                                        background background1 = new background(getContext());
                                        background1.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                if(obj.contains("failed")){

                                                }else {/*
                                                    String[] objsplit = obj.split("-");
                                                    DocumentReference doc2 = db.document("List_Job/"+objsplit[0]);
                                                    doc2.update("slotnow",objsplit[1]);
                                                    adapter.delitem(position);*/
                                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        background1.execute("reply_message-invite_yes-" + ID_Job + "-"  + "-" + choosen + "-" +docID);
                                        break;
                                    case 3:
                                        Toast.makeText(getContext(), ID_Job, Toast.LENGTH_SHORT).show();
                                        background background2 = new background(getContext());
                                        background2.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                if(obj.contains("failed")){

                                                }else {;
                                                    //adapter.delitem(position);
                                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        background2.execute("reply_message-invite_reject-" + ID_Job + "-" + "-" + choosen + "-" +docID);
                                        break;
                                }
                            }
                        });
                        builder3.show();
                        break;
                    case "vote":
                        String[] optVote = {"Up Vote", "Down Vote"};

                        AlertDialog.Builder builder4 = new AlertDialog.Builder(getContext());
                        builder4.setTitle("Options");
                        builder4.setItems(optVote, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // the user clicked on colors[which]
                                switch (which) {
                                    case 0:
                                        //Toast.makeText(getContext(), state + " Up Vote " + ID_Job_vote, Toast.LENGTH_SHORT).show();
                                        background background1 = new background(getContext());
                                        background1.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                if(obj.contains("failed")){

                                                }else{
                                                    //adapter.delitem(position);
                                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        background1.execute("reply_message-UpVote-" +ID_Job_vote+"-"+state+ "-" +docID);
                                        break;
                                    case 1:
                                        //Toast.makeText(getContext(), "Down Vote", Toast.LENGTH_SHORT).show();
                                        background background2 = new background(getContext());
                                        background2.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                if(obj.contains("failed")){

                                                }else{
                                                    //adapter.delitem(position);
                                                    Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        background2.execute("reply_message-DownVote-" + ID_Job_vote+"-"+state+ "-" +docID);
                                        break;
                                }
                            }
                        });
                        builder4.show();
                        break;
                }
                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Offer_avaible_task2(), id).addToBackStack(null).commit();
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
