package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 6/3/2018.
 */

public class list_task_worker2 extends Fragment implements dialog_yes_no_complete_abandon.dialogListener_yes_no_complete_abandon {
    @Nullable
    ListView listView;
    TextView title;
    String state;
    Context context;
    String ID_job, IDTASK;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference ref ;
    list_task_leader2_recycler_adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Avaible Task");
        final View view = inflater.inflate(R.layout.myjob3, container, false);
        ID_job = getTag();
        context = getContext();
        title = view.findViewById(R.id.title);
        title.setText("~ Task List ~");
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        Toast.makeText(getContext(), ID_job +" "+state, Toast.LENGTH_SHORT).show();
        ref = db.collection("List_Job/"+ID_job+"/List_Task");
        setup(view);

        /*
        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                unprocessed_task = obj;
                if (obj.equals("No Task")||obj.contains("failed")) {
                    //Toast.makeText(getContext(), "No Task Avaible", Toast.LENGTH_SHORT).show();
                } else {
                    String[] processed_task = unprocessed_task.split("-LIST-");
                    ListAdapter adapter = new advancedcustomadapter_as_worker(getContext(), processed_task);
                    listView = (ListView) view.findViewById(R.id.myjob_viw);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                            final String[] p1 = choosen.split("-");
                            IDTASK = p1[6];
                            String status = p1[5];
                            if (status.equals("yes")){
                                Toast.makeText(getContext(), "waiting for review", Toast.LENGTH_SHORT).show();
                            }else {
                                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new showprove_deletetask_show_upload(),IDTASK+"-"+ID_job).addToBackStack(null).commit();
                            }
                            //opendialog_yes_no();
                        }
                    });
                }
            }
        });
        background.execute("request_job_task3-" + ID_job + "-" + state);*/
        return view;

    }

    private void opendialog_yes_no() {
        dialog_yes_no_complete_abandon dialogfragment = new dialog_yes_no_complete_abandon();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");
    }

    private void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof list_task_worker2) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    public void apply_abandoned(String wasd) {
        //Toast.makeText(context, wasd, Toast.LENGTH_SHORT).show();
        background background = new background(context);
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                DocumentReference doc2 = db.document("List_Job/"+ID_job+"/List_Task/"+IDTASK);
                doc2.update("worker","none");
            }
        });
        background.execute("abandon_task-" + IDTASK + "-" + ID_job + "-" + state);
        update();
    }

    @Override
    public void apply_complete(String wasd) {
        Toast.makeText(context, wasd, Toast.LENGTH_SHORT).show();
        background background = new background(context);
        background.execute("complete_task-" + IDTASK);
        update();
    }

    private void setup(View view){
        Query query = ref.whereEqualTo("worker",state);
        FirestoreRecyclerOptions<Frag_Offer_recycler_task> options = new FirestoreRecyclerOptions.Builder<Frag_Offer_recycler_task>().setQuery(query,Frag_Offer_recycler_task.class).build();
        adapter = new list_task_leader2_recycler_adapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.myjob_viw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItem(new list_task_leader2_recycler_adapter.onitemclickListener() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                Frag_Offer_recycler_task note = documentSnapshot.toObject(Frag_Offer_recycler_task.class);
                String id = documentSnapshot.getId();
                String status = note.getStatus();
                switch (status){
                    case "on":
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new showprove_deletetask_show_upload(),id+"-"+ID_job).addToBackStack(null).commit();
                        break;
                    case "Waiting for review":
                        Toast.makeText(getContext(), "Waiting for review", Toast.LENGTH_SHORT).show();
                        break;
                    case "approved" :
                        Toast.makeText(getContext(), "Approved", Toast.LENGTH_SHORT).show();
                        break;
                }
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