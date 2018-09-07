package com.example.adrian.grouptaskmanagement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_worker_management_job extends Fragment {
    ListView listView;
    View view;
    Context context;
    Activity activity;
    String ID_Job;
    ImageView finish, task, worker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity = getActivity();
        ID_Job = getTag();
        context = activity.getApplicationContext();
        view = inflater.inflate(R.layout.worker_management2, container, false);

        background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                String[] processed_task = obj.split("-");
                ListAdapter adapter = new advancedcustomadapter_list_worker(getContext(), processed_task);
                listView = (ListView) view.findViewById(R.id.list_worker);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }
        });
        background.execute("request_worker-" + ID_Job);

        return view;
    }
}
