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


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        ID_Job = getTag();
        //ID_Job2 = ID_Job;
        context = activity.getApplicationContext();
        view = inflater.inflate(R.layout.job_management_worker, container, false);
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");

        //Toast.makeText(getContext(), ID_Job, Toast.LENGTH_SHORT).show();

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
                        if(obj.contains("failed")){

                        }else {/*
                            String[] obj1 = obj.split("-LISTJOB-");
                            DocumentReference doc1 = db.document("List_Job/"+ID_Job);
                            doc1.update("slotnow",obj1[0]);

                            String[] obj2 = obj1[1].split("-");
                            for (int i = 0;i<obj2.length;i++){
                                Toast.makeText(getContext(), obj2[i], Toast.LENGTH_SHORT).show();
                                DocumentReference doc2 = db.document("List_Job/"+ID_Job+"/List_Task/"+obj2[i]);
                                doc2.update("worker","none");
                            }*/
                            getFragmentManager().popBackStack();
                        }
                    }
                });
                background1.execute("abandon_group-" + ID_Job + "-" + state);
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new list_task_worker2(), ID_Job).addToBackStack(null).commit();
            }
        });

        return view;
    }


}
