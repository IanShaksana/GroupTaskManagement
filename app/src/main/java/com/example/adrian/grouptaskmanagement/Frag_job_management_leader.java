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
import android.widget.ImageView;
import android.widget.Toast;


/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_job_management_leader extends Fragment {
    View view;
    Context context;
    Activity activity;
    String ID_Job, ID_Job2;
    ImageView finish, task, worker, play;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity = getActivity();
        ID_Job = getTag();
        ID_Job2 = ID_Job;
        context = activity.getApplicationContext();
        view = inflater.inflate(R.layout.job_management_leader, container, false);

        Toast.makeText(getContext(), ID_Job2, Toast.LENGTH_SHORT).show();

        finish = (ImageView) view.findViewById(R.id.Finish_Job);
        task = (ImageView) view.findViewById(R.id.View_Task);
        worker = (ImageView) view.findViewById(R.id.View_Worker);
        play = (ImageView) view.findViewById(R.id.Offer_Not);


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background background1 = new background(getContext());
                background1.execute("finish_job-"+ID_Job);
                getFragmentManager().popBackStack();
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new list_task_leader2(), ID_Job).addToBackStack(null).commit();
            }
        });

        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_worker_management(), ID_Job2).addToBackStack(null).commit();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background background = new background(getContext());
                background.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        Toast.makeText(getContext(), obj, Toast.LENGTH_SHORT).show();
                    }
                });
                //background.execute("postornot-"+ID_Job);
            }
        });



        return view;
    }
    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Offer_avaible_task) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
