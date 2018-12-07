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
 * Created by Adrian on 5/19/2018.
 * <p>
 * <p>
 * public class advancedcustomadapter_offer extends ArrayAdapter<String> {
 * public advancedcustomadapter_offer(@NonNull Context context, String[] resource) {
 * super(context,R.layout.advancelistview ,resource);
 * }
 *
 * @NonNull
 * @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
 * LayoutInflater inflater = LayoutInflater.from(getContext());
 * View cusView = inflater.inflate(R.layout.advancelistview,parent,false);
 * <p>
 * String singletask = getItem(position);
 * TextView textView = (TextView) cusView.findViewById(R.id.inputtext);
 * ImageView imageView = (ImageView) cusView.findViewById(R.id.imageView);
 * <p>
 * textView.setText(singletask);
 * imageView.setImageResource(R.drawable.image);
 * <p>
 * return cusView;
 * <p>
 * <p>
 * }
 * }
 */

public class advancedcustomadapter_list_worker extends ArrayAdapter<String> {
    public advancedcustomadapter_list_worker(@NonNull Context context, String[] resource) {
        super(context, R.layout.list, resource);
    }


    TextView Worker_name;
    TextView Worker_task;
    TextView date;
    TextView date2;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cusView = inflater.inflate(R.layout.worker_detail, parent, false);


        String singletask = getItem(position);
        String[] splitted_task = singletask.split("-");
        //String[] splitted_time = singletask.split(",");
        //String[] splitted_time2 = splitted_time[1].split(" ");

        Worker_name = (TextView) cusView.findViewById(R.id.worker_name_view);
        //Worker_task = (TextView) cusView.findViewById(R.id.Task_name);
        //date = (TextView) cusView.findViewById(R.id.date_input_view);
        //date2 = (TextView) cusView.findViewById(R.id.date_time_input_view);


        Worker_name.setText(splitted_task[0]);
        //description.setText(splitted_task[1]);
        //date.setText(splitted_time2[0]);
        //date2.setText(splitted_time2[1]);
        return cusView;

    }

}