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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 6/3/2018.
 */

public class Frag_myjob_leader extends Fragment {
    ListView listView;
    String state;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("As Leader");
        final View view = inflater.inflate(R.layout.myjob2, container, false);
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        background request_offer = new background(getContext());
        request_offer.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                if(obj.contains("failed")){

                }else {
                    String[] split = obj.split("-");
                    ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, split);
                    listView = (ListView) view.findViewById(R.id.myjob_viw);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                            final String[] choosensplit = choosen.split(",");
                            background background1 = new background(getContext());
                            background1.getListener(new background.OnUpdateListener() {
                                @Override
                                public void onUpdate(String obj) {
                                    if(obj.equals("finished")){
                                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_job_management_leader_AFTR(), choosensplit[1]).addToBackStack(null).commit();
                                    }else {
                                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_job_management_leader(), choosensplit[1]).addToBackStack(null).commit();
                                    }
                                }
                            });
                            background1.execute("cek_finished-"+choosensplit[1]);
                            //Toast.makeText(getContext(), choosensplit[1], Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
        request_offer.execute("request_myjob_leader-" + state);

        return view;
    }

}
