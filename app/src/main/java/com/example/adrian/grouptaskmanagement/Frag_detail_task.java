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

public class Frag_detail_task extends Fragment {
    @Nullable
    String state;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_task,container, false);
        String ID_Task = getTag();

        final TextView name = (TextView) view.findViewById(R.id.task_name_view);
        final TextView description = (TextView) view.findViewById(R.id.desc_input_view);
        final TextView difficulty = (TextView) view.findViewById(R.id.diff_input_view);
        final TextView type = (TextView) view.findViewById(R.id.type_input_view);
        final TextView date = (TextView) view.findViewById(R.id.date_input_view);
        final TextView date2 = (TextView) view.findViewById(R.id.date_time_input_view);

        background background_home = new background(getContext());
        background_home.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                //String[] split = obj.split("-");
                String[] splitted_task = obj.split("-");
                String[] splitted_time = obj.split(",");
                String[] splitted_time2 = splitted_time[1].split(" ");

                name.setText(splitted_task[0]);
                description.setText(splitted_task[1]);
                difficulty.setText(splitted_task[3]);
                type.setText(splitted_task[2]);
                date.setText(splitted_time2[0]);
                date2.setText(splitted_time2[1]);
            }
        });
        background_home.execute("request_detail_task-"+ID_Task);

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
