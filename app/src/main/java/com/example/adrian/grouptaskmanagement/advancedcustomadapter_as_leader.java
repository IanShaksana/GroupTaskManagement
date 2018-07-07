package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Adrian on 6/4/2018.
 */

public class advancedcustomadapter_as_leader extends ArrayAdapter<String> {
    public advancedcustomadapter_as_leader(@NonNull Context context, String[] resource) {
        super(context,R.layout.list_task_leader ,resource);
    }


    TextView jobname;
    TextView jobID;
    TextView taskID;
    TextView name;
    TextView description ;
    TextView difficulty;
    TextView type ;
    TextView date;
    TextView date2;
    TextView approved;
    TextView completion;
    Button button;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cusView = inflater.inflate(R.layout.mytask_worker,parent,false);


        String singletask = getItem(position);
        String[] splitted_task = singletask.split("-");
        String[] splitted_time = singletask.split(",");
        String[] splitted_time2 = splitted_time[1].split(" ");

        jobname = (TextView) cusView.findViewById(R.id.worker_job_name);
        name = (TextView) cusView.findViewById(R.id.task_name_view);
        description = (TextView) cusView.findViewById(R.id.desc_input_view);
        difficulty = (TextView) cusView.findViewById(R.id.diff_input_view);
        type = (TextView) cusView.findViewById(R.id.type_input_view);
        date = (TextView) cusView.findViewById(R.id.date_input_view);
        date2 = (TextView) cusView.findViewById(R.id.date_time_input_view);


        name.setText(splitted_task[0]);
        description.setText(splitted_task[1]);
        difficulty.setText(splitted_task[3]);
        type.setText(splitted_task[2]);
        date.setText(splitted_time2[0]);
        date2.setText(splitted_time2[1]);
        return cusView;

    }
}
