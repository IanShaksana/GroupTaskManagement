package com.example.adrian.grouptaskmanagement;

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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 6/3/2018.
 */

public class list_task_leader extends Fragment {
    @Nullable

    String unprocessed_task;
    ListView listView;
    String state;
    FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Avaible Task");
        final View view = inflater.inflate(R.layout.list_task_leader,container, false);
        String tempo = getTag();
        //clearBackStack();

        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State",MODE_PRIVATE);
        state = preferences.getString("Login_State","");
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.FAB);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_Create2(),getTag()).addToBackStack(null).commit();
            }
        });

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
                    listView = (ListView) view.findViewById(R.id.list_task_leader);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                            Toast.makeText(getContext(),choosen,Toast.LENGTH_SHORT).show();
                            //background background1 =new background(getContext());
                            //String[] selected_task = choosen.split("-");
                            //background1.execute("request_apply_task-"+state+"-"+selected_task[0]);
                            //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_List()).addToBackStack(null).commit();
                        }
                    });
                }

            }
        });
        background.execute("request_job_task-"+tempo);

        return view;

    }
    private void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}