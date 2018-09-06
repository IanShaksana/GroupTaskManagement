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


public class advancedcustomadapter_offer extends ArrayAdapter<String> {
    public advancedcustomadapter_offer(@NonNull Context context, String[] resource) {
        super(context,R.layout.advancelistview ,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cusView = inflater.inflate(R.layout.advancelistview,parent,false);

        String singletask = getItem(position);
        TextView textView = (TextView) cusView.findViewById(R.id.inputtext);
        ImageView imageView = (ImageView) cusView.findViewById(R.id.imageView);

        textView.setText(singletask);
        imageView.setImageResource(R.drawable.image);

        return cusView;


    }
}
 */

public class advancedcustomadapter_offer extends ArrayAdapter<String> {
    public advancedcustomadapter_offer(@NonNull Context context, String[] resource) {
        super(context,R.layout.list ,resource);
    }


    TextView name;
    TextView description ;
    TextView difficulty;
    TextView type ;
    TextView date;
    TextView date2;
    Button button;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cusView = inflater.inflate(R.layout.detail_task,parent,false);


        String singletask = getItem(position);
        String[] splitted_task = singletask.split("-");
        String[] splitted_time = singletask.split(",");
        String[] splitted_time2 = splitted_time[1].split(" ");

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