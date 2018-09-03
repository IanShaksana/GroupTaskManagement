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

import com.baoyz.swipemenulistview.SwipeMenuListView;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_job_management extends Fragment {
    SwipeMenuListView listView;
    View view;
    Context context;
    Activity activity;
    String ID_Job,ID_Job2;
    ImageView finish,task,worker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity=getActivity();
        ID_Job = getTag();
        ID_Job2 = ID_Job;
        context=activity.getApplicationContext();
        view = inflater.inflate(R.layout.job_management,container, false);

        Toast.makeText(getContext(),ID_Job2,Toast.LENGTH_SHORT).show();

        finish = (ImageView) view.findViewById(R.id.Finish_Job);
        task = (ImageView) view.findViewById(R.id.View_Task);
        worker = (ImageView) view.findViewById(R.id.View_Worker);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new Frag_Create()).addToBackStack(null).commit();
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new list_task_leader(),ID_Job).addToBackStack(null).commit();
            }
        });

        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new Frag_worker_management(),ID_Job2).addToBackStack(null).commit();
            }
        });


        /*
        String[] split = {"User as Worker","User as Leader"};
        ListAdapter adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,split);
        listView = (SwipeMenuListView) view.findViewById(R.id.myjob_viw);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String choosen = String.valueOf(adapterView.getItemAtPosition(i));
            //Toast.makeText(getContext(),choosen,Toast.LENGTH_SHORT).show();
                if (choosen.equals("User as Worker")){
                    getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_myjob_worker()).addToBackStack(null).commit();
                }else if (choosen.equals("User as Leader")){
                    getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_myjob_leader()).addToBackStack(null).commit();
                }
            }
        });
        */
        return view;
    }


}
