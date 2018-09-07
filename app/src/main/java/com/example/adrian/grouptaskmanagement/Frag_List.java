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

import com.baoyz.swipemenulistview.SwipeMenuListView;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_List extends Fragment {
    SwipeMenuListView listView;
    View view;
    Context context;
    Activity activity;
    ImageView create, task, group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity = getActivity();
        context = activity.getApplicationContext();
        view = inflater.inflate(R.layout.myjob, container, false);

        create = (ImageView) view.findViewById(R.id.Create_Job);
        task = (ImageView) view.findViewById(R.id.My_Task);
        group = (ImageView) view.findViewById(R.id.My_Job);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Create_Job()).addToBackStack(null).commit();
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_myjob_worker()).addToBackStack(null).commit();
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_myjob_leader()).addToBackStack(null).commit();
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
                    getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_myjob_worker_old()).addToBackStack(null).commit();
                }else if (choosen.equals("User as Leader")){
                    getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_myjob_leader()).addToBackStack(null).commit();
                }
            }
        });
        */
        return view;
    }


}
