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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_job_management_leader extends Fragment {
    View view;
    Context context;
    Activity activity;
    String ID_Job, ID_Job2;
    ImageView finish, task, worker, play;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView jobname, taskfinished, taskall, jobdate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ID_Job = getTag();
        ID_Job2 = ID_Job;
        view = inflater.inflate(R.layout.job_management_leader, container, false);
        jobname = (TextView) view.findViewById(R.id.textView1);
        taskfinished = (TextView) view.findViewById(R.id.textView3);
        taskall = (TextView) view.findViewById(R.id.textView4);
        jobdate = (TextView) view.findViewById(R.id.Job_date);

        background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                if(obj.contains("failed")){

                }else{
                    String[] objsplit = obj.split("-list-");
                    jobname.setText(objsplit[1]);
                    taskfinished.setText(objsplit[2]);
                    taskall.setText(objsplit[3]);
                    jobdate.setText(objsplit[0]);
                }
            }
        });
        background.execute("get_job_detail-"+ID_Job);

        //Toast.makeText(getContext(), ID_Job2, Toast.LENGTH_SHORT).show();

        finish = (ImageView) view.findViewById(R.id.Finish_Job);
        task = (ImageView) view.findViewById(R.id.View_Task);
        worker = (ImageView) view.findViewById(R.id.View_Worker);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                background background1 = new background(getContext());
                background1.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        //Toast.makeText(getContext(), obj, Toast.LENGTH_SHORT).show();

                        if(obj.contains("failed")){
                        }else{
                            String[] objsplit = obj.split("-");
                            DocumentReference doc2 = db.document("List_Job/"+ID_Job);
                            doc2.update("status","off");
                            for (int i =2; i<objsplit.length;i++){
                                CollectionReference notebookRef1 = db
                                        .collection("Message/"+objsplit[i]+"/"+"inbox/");
                                notebookRef1.add(new Frag_Inbox_recycler("System","Time to vote "+ID_Job,"send","vote",objsplit[i]+"-vote-"+objsplit[0]+"-"+objsplit[1]));
                            }
                            getFragmentManager().popBackStack();
                        }
                        /*
                        for (int i =2; i<objsplit.length;i++){
                            Toast.makeText(getContext(), objsplit[i], Toast.LENGTH_SHORT).show();
                        }
                        */
                    }
                });
                background1.execute("finish_job-"+ID_Job);
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

        return view;
    }
    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Offer_avaible_task) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
