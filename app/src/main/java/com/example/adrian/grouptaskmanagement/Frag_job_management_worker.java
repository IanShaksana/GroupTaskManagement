package com.example.adrian.grouptaskmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_job_management_worker extends Fragment {
    View view;
    Context context;
    Activity activity;
    String ID_Job, state;
    ImageView abandon, task, worker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity = getActivity();
        ID_Job = getTag();
        //ID_Job2 = ID_Job;
        context = activity.getApplicationContext();
        view = inflater.inflate(R.layout.job_management_worker, container, false);
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");

        Toast.makeText(getContext(), ID_Job, Toast.LENGTH_SHORT).show();

        abandon = (ImageView) view.findViewById(R.id.exit);
        task = (ImageView) view.findViewById(R.id.View_Task);
        //worker = (ImageView) view.findViewById(R.id.View_Worker);

        abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background background1 = new background(getContext());
                background1.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        getFragmentManager().popBackStack();
                    }
                });
                background1.execute("abandon_group-" + ID_Job + "-" + state);
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new list_task_worker(), ID_Job).addToBackStack(null).commit();
            }
        });

        return view;
    }


}
