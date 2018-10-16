package com.example.adrian.grouptaskmanagement;

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
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Offer_avaible_task2 extends Fragment implements dialog_yes_no_apply_task.dialogListener_yes_no_apply_task {
    @Nullable

    //String unprocessed_task;
    ListView listView;
    String state;
    String ID_Task, ID_Job;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref ;
    private Frag_Offer_avaible_task_recycler_adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ID_Job = getTag();
        ref = db.collection("List_Job/"+ID_Job+"/List_Task");
        final View view = inflater.inflate(R.layout.offer_tab_avaible_tab_avaibletask2, container, false);
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        setup(view);
        /*final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                unprocessed_task = obj;
                String[] processed_task = unprocessed_task.split("-LIST-");
                ListAdapter adapter = new advancedcustomadapter_offer(getContext(), processed_task);
                listView = (ListView) view.findViewById(R.id.list_detail_task);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                        Toast.makeText(getContext(), choosen, Toast.LENGTH_SHORT).show();
                        String[] selected_task = choosen.split("-");
                        ID_Task = selected_task[6];
                        ID_Job = selected_task[4];
                        opendialog_yes_no();
                        //background background1 =new background(getContext());
                        //String[] selected_task = choosen.split("-");
                        //background1.execute("request_apply_task-"+state+"-"+selected_task[0]);
                        //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_List()).addToBackStack(null).commit();
                    }
                });

            }
        });
        background.execute("request_job_task2-" + idJob);*/
        return view;
    }

    private void opendialog_yes_no() {
        dialog_yes_no_apply_task dialogfragment = new dialog_yes_no_apply_task();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");
    }

    @Override
    public void apply_apply_task(String wasd) {
        if (wasd.equals("Apply")) {
            background background1 = new background(getContext());
            background1.getListener(new background.OnUpdateListener() {
                @Override
                public void onUpdate(String obj) {
                    //ada message
                    CollectionReference notebookRef1 = FirebaseFirestore.getInstance()
                            .collection("Message/"+obj+"/"+"inbox/");
                    notebookRef1.add(new Frag_Inbox_recycler(state,state+" want to apply "+ID_Task,"send","apply-task"));
                    update();
                }
            });
            //background1.execute("request_apply_task-"+state+"-"+ID_Task+"-"+ID_Job);
            background1.execute("apply_req-" + state + "-" + ID_Task + "-" + ID_Job);
            //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_List()).addToBackStack(null).commit();
        }
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Offer_avaible_task2) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void setup(View view){
        Query query = ref.whereEqualTo("worker","none");
        FirestoreRecyclerOptions<Frag_Offer_recycler_task> options = new FirestoreRecyclerOptions.Builder<Frag_Offer_recycler_task>().setQuery(query,Frag_Offer_recycler_task.class).build();
        adapter = new Frag_Offer_avaible_task_recycler_adapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.list_detail_task);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItem(new Frag_Offer_avaible_task_recycler_adapter.onitemclickListener() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                z_recycler_note note = documentSnapshot.toObject(z_recycler_note.class);
                String id = documentSnapshot.getId();
                ID_Task = id;
                Toast.makeText(getContext(), "ID_Job: "+ID_Job+" id: "+ID_Task, Toast.LENGTH_SHORT).show();
                //opendialog_yes_no();
                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Offer_avaible_task(), id).addToBackStack(null).commit();
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
