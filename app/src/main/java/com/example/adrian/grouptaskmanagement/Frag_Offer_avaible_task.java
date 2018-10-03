package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Offer_avaible_task extends Fragment implements dialog_yes_no_apply_task.dialogListener_yes_no_apply_task {
    @Nullable

    String unprocessed_task;
    ListView listView;
    String state;
    String ID_Task, ID_Job;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Avaible Task");
        String idJob = getTag();
        final View view = inflater.inflate(R.layout.offer_tab_avaible_tab_avaibletask, container, false);
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                unprocessed_task = obj;
                String[] processed_task = unprocessed_task.split("-LIST-");
                ListAdapter adapter = new advancedcustomadapter_offer(getContext(), processed_task);
                listView = (ListView) view.findViewById(R.id.list_detail_task);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                        Toast.makeText(getContext(), choosen, Toast.LENGTH_SHORT).show();
                        String[] selected_task = choosen.split("-");
                        ID_Task = selected_task[6];
                        ID_Job = selected_task[4];
                        opendialog_yes_no();
                        //background background1 =new background(getContext());
                        //String[] selected_task = choosen.split("-");
                        //background1.execute("request_apply_task-"+state+"-"+selected_task[0]);
                        //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_List()).addToBackStack(null).commit();
                    }
                });

            }
        });
        background.execute("request_job_task2-" + idJob);
        return view;
    }

    private void opendialog_yes_no() {
        dialog_yes_no_apply_task dialogfragment = new dialog_yes_no_apply_task();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");

    }

    @Override
    public void apply_apply_task(String wasd) {
        if (wasd.equals("Apply")) {
            background background1 = new background(getContext());
            background1.getListener(new background.OnUpdateListener() {
                @Override
                public void onUpdate(String obj) {
                    update();
                }
            });
            //background1.execute("request_apply_task-"+state+"-"+ID_Task+"-"+ID_Job);
            background1.execute("apply_req-" + state + "-" + ID_Task + "-" + ID_Job);
            //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_List()).addToBackStack(null).commit();
        }
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Offer_avaible_task) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
