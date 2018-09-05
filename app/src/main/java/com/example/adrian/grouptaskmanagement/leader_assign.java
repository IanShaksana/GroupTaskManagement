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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Adrian on 9/4/2018.
 */

public class leader_assign extends Fragment {

    View view;
    Context context;
    Activity activity;
    String unprocessed_task;
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity=getActivity();
        context=activity.getApplicationContext();
        view = inflater.inflate(R.layout.list_assign_task_leader,container, false);
        String Tag= getTag();
        final String[] Tagsplit= Tag.split("-");

        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                unprocessed_task =obj;
                if(obj.equals("No Task")){
                    Toast.makeText(getContext(),"No Task Avaible",Toast.LENGTH_SHORT).show();
                }else {
                    String[] processed_task = unprocessed_task.split("-LIST-");
                    ListAdapter adapter = new advancedcustomadapter_as_leader(getContext(), processed_task);
                    listView = (ListView) view.findViewById(R.id.list_assign_task_leader);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                            Toast.makeText(getContext(),choosen,Toast.LENGTH_SHORT).show();
                            String[] choosensplit = choosen.split("-");


                            background background1 = new background(getContext());
                            background1.getListener(new background.OnUpdateListener() {
                                @Override
                                public void onUpdate(String obj) {
                                    Toast.makeText(getContext(),"hai",Toast.LENGTH_SHORT).show();
                                    update();
                                }
                            });
                            //tambahi dialog aja
                            background1.execute("request_apply_task-"+Tagsplit[1]+"-"+choosensplit[6]+"-"+Tagsplit[0]);
                        }
                    });
                }
            }
        });
        background.execute("request_job_task2-"+Tagsplit[0]);

        return view;
    }

    private void update(){
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof leader_assign){
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
