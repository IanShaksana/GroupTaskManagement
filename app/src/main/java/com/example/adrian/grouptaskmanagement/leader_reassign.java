package com.example.adrian.grouptaskmanagement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Adrian on 9/4/2018.
 */

public class leader_reassign extends Fragment implements  dialog_yes_no_reassign.dialogListener_yes_no_reassign {

    View view;
    Context context;
    Activity activity;
    ListView listView;
    String Tag;
    String choosen;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity = getActivity();
        context = activity.getApplicationContext();
        view = inflater.inflate(R.layout.list_reassign_task_leader, container, false);
        Tag = getTag();
        String[] Tagsplit = Tag.split("-");
        //Toast.makeText(getContext(), Tag, Toast.LENGTH_SHORT).show();

        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                String[] split = obj.split("-list-");
                ListAdapter adapter1 = new advancedcustomadapter_as_worker2(getContext(), split);
                listView = (ListView) view.findViewById(R.id.list_reassign_task_leader);
                listView.setAdapter(adapter1);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        choosen = String.valueOf(adapterView.getItemAtPosition(i));
                        String[] status = choosen.split("-");
                        if(status[7].equals("no")){
                            opendialog_worker();
                        }else {
                            Toast.makeText(getContext(),"task has been completed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        background.execute("request_worker_task-" + Tagsplit[1] + "-" +Tagsplit[0]);

        return view;
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof leader_reassign) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void opendialog_worker() {
        dialog_yes_no_reassign dialogfragment = new dialog_yes_no_reassign();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");
    }

    @Override
    public void apply_reassign(String wasd) {
        final String[] choosensplit = choosen.split("-");
        background background1 = new background(getContext());
        background1.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                DocumentReference doc2 = db.document("List_Job/"+choosensplit[1]+"/List_Task/"+choosensplit[2]);
                doc2.update("worker","none");
                update();
            }
        });
        background1.execute("abandon_task-" + choosensplit[2]);
    }
}
