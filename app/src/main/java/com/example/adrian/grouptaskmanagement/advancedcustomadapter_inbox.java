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

public class advancedcustomadapter_inbox extends ArrayAdapter<String> {
    public advancedcustomadapter_inbox(@NonNull Context context, String[] resource) {
        super(context, R.layout.list, resource);
    }


    TextView Sender;
    TextView Recepient;
    TextView Message;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cusView = inflater.inflate(R.layout.inbox_detail, parent, false);

        //ian,kez,kez-apply-test1|153603447984
        //ian,ren,ren-invite-demolalala

        String bundle_msg = getItem(position);
        String[] splitted_msg = bundle_msg.split(",");
        String[] body_msg = splitted_msg[2].split("-");
        //String[] splitted_time2 = splitted_time[1].split(" ");

        Sender = (TextView) cusView.findViewById(R.id.sender);
        //Recepient = (TextView) cusView.findViewById(R.id.desc_input_view);
        Message = (TextView) cusView.findViewById(R.id.msg_name);

        Sender.setText(splitted_msg[0]);
        switch (body_msg[1]){
            case "apply":
                Message.setText(body_msg[0] + " wants to " + body_msg[1] + " " + body_msg[2]);
                break;
            case "invite":
                Message.setText(splitted_msg[0] + " wants to invite you to " + body_msg[2]);
                break;
            case "assign":
                Message.setText(splitted_msg[0] + " wants to assign you to " + body_msg[2]);
                break;
            case "vote":
                Message.setText("vote for "+body_msg[2]+" (Job : "+body_msg[3]+" )");
                break;
        }


        return cusView;

    }

}