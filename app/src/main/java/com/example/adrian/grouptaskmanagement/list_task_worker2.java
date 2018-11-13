package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 6/3/2018.
 */

public class list_task_worker2 extends Fragment implements dialog_yes_no_complete_abandon.dialogListener_yes_no_complete_abandon {
    @Nullable

    String unprocessed_task;
    ListView listView;
    String state;
    Context context;
    String ID_job, IDTASK;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Avaible Task");
        final View view = inflater.inflate(R.layout.myjob2, container, false);
        ID_job = getTag();
        context = getContext();
        //clearBackStack();

        Toast.makeText(getContext(), ID_job, Toast.LENGTH_SHORT).show();

        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");


        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                unprocessed_task = obj;
                if (obj.equals("No Task")||obj.contains("failed")) {
                    Toast.makeText(getContext(), "No Task Avaible", Toast.LENGTH_SHORT).show();
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
        background.execute("request_job_task3-" + ID_job + "-" + state);
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
}