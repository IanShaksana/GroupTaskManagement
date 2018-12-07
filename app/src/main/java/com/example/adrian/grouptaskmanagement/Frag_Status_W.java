package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Status_W extends Fragment {
    @Nullable
    String tag;
    TextView name;
    TextView description;
    TextView difficulty;
    TextView type;
    TextView date;
    TextView date2;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Home");
        View view = inflater.inflate(R.layout.compare, container, false);

        final TextView username = (TextView) view.findViewById(R.id.username);

        final TextView Str = (TextView) view.findViewById(R.id.str_qty);
        final TextView Agi = (TextView) view.findViewById(R.id.agi_qty);
        final TextView Intel = (TextView) view.findViewById(R.id.intel_qty);


        name = (TextView) view.findViewById(R.id.task_name_view);
        description = (TextView) view.findViewById(R.id.desc_input_view);
        difficulty = (TextView) view.findViewById(R.id.diff_input_view);
        type = (TextView) view.findViewById(R.id.type_input_view);
        date = (TextView) view.findViewById(R.id.date_input_view);
        date2 = (TextView) view.findViewById(R.id.date_time_input_view);

        //final TextView Exp_cur = (TextView) view.findViewById(R.id.exp_cur);
        //final TextView Exp_nex = (TextView) view.findViewById(R.id.exp_nex);

        tag = getTag();
        String[] state = tag.split("-");


        background background_compare1 = new background(getContext());
        background_compare1.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                String[] split = obj.split("-");
                username.setText(split[0]);
                Str.setText(split[2]);
                Agi.setText(split[3]);
                Intel.setText(split[4]);
                //Exp_cur.setText(split[1]);
                //Exp_nex.setText("~");
            }
        });
        background_compare1.execute("request_home-" + state[0]);

        background background_compare2 = new background(getContext());
        background_compare2.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                //String[] split = obj.split("-");
                //String singletask = getItem(position);
                String[] splitted_task = obj.split("-");
                String[] splitted_time = obj.split(",");
                String[] splitted_time2 = splitted_time[1].split(" ");
                name.setText(splitted_task[0]);
                description.setText(splitted_task[1]);
                difficulty.setText(splitted_task[3]);
                if(splitted_task[2].equals("Dex")){
                    type.setText("Crea");
                }else{
                    type.setText(splitted_task[2]);
                }
                date.setText(splitted_time2[0]);
                date2.setText(splitted_time2[1]);
            }
        });
        background_compare2.execute("request_detail_task-" + state[1]);

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
