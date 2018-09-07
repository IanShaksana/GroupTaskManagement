package com.example.adrian.grouptaskmanagement;

import android.content.Context;
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
 * Created by Adrian on 6/3/2018.
 */

public class Frag_myjob_worker_old extends Fragment implements dialog_yes_no_complete_abandon.dialogListener_yes_no_complete_abandon/*dialog_yes_no_abandon.dialogListener_yes_no_abandon,dialog_yes_no_complete.dialogListener_yes_no_complete*/ {
    //SwipeMenuListView listView;
    ListView listView;
    String state;
    Context context;
    String IDTASK, IDJOB;
    Fragment current;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        getActivity().setTitle("As Worker");
        final View view = inflater.inflate(R.layout.myjob2, container, false);
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        background request_offer = new background(getContext());
        request_offer.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                String[] split = obj.split("-list-");
                ListAdapter adapter1 = new advancedcustomadapter_as_worker(getContext(), split);
                listView = (ListView) view.findViewById(R.id.myjob_viw);
                listView.setAdapter(adapter1);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                        Toast.makeText(getContext(), choosen, Toast.LENGTH_SHORT).show();
                        String[] splitIDTASK = choosen.split("-");
                        IDTASK = splitIDTASK[2];
                        IDJOB = splitIDTASK[1];
                        opendialog_yes_no();
                        //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_Offer_avaible_task()).addToBackStack(null).commit();
                    }
                });
            }
        });
        request_offer.execute("request_myjob_worker-" + state);
        return view;
    }

    private void opendialog_yes_no() {
        dialog_yes_no_complete_abandon dialogfragment = new dialog_yes_no_complete_abandon();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");

    }
    /*
    private void opendialog_yes_no_complete() {
        dialog_yes_no_complete dialogfragment = new dialog_yes_no_complete();
        dialogfragment.setTargetFragment(this,0);
        dialogfragment.show(getFragmentManager(),"exa");

    }
    */

    public void apply_abandoned(String wasd) {
        Toast.makeText(context, wasd, Toast.LENGTH_SHORT).show();
        background background = new background(context);
        background.execute("abandon_task-" + IDTASK + "-" + IDJOB + "-" + state);
        current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_myjob_worker_old) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }

    }

    @Override
    public void apply_complete(String wasd) {
        Toast.makeText(context, wasd, Toast.LENGTH_SHORT).show();
        background background = new background(context);
        background.execute("complete_task-" + IDTASK);
        //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_myjob_worker_old()).commit();
        current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_myjob_worker_old) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

}
