package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Adrian on 10/27/2018.
 */

public class a_firebase_image_adapter extends RecyclerView.Adapter<a_firebase_image_adapter.image_holder> {
    private Context context;
    private List<a_firebase_image_upload> uploads;


    public a_firebase_image_adapter(Context context, List<a_firebase_image_upload> uploads) {
        this.context = context;
        this.uploads = uploads;
    }

    @NonNull
    @Override
    public image_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.a_firebase_image_show_card,parent,false);
        return new image_holder(v);
    }

    public void onBindViewHolder(image_holder holder, int position) {
        a_firebase_image_upload uploadCurrent = uploads.get(position);
        holder.text_name.setText(uploadCurrent.getmName());
        Glide.with(context).load(uploadCurrent.getmImageUrl()).into(holder.image_name);
        //Picasso.with(context).load(uploadCurrent.getmImageUrl()).resize(50, 50).centerCrop().into(holder.image_name);
        //Picasso.get().load("gs://grouptaskmanagement-60230.appspot.com/uploads/ss-1540623869720.jpg").fit().centerCrop().into(holder.image_name);
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class image_holder extends RecyclerView.ViewHolder {
        TextView text_name;
        ImageView image_name;

        public image_holder(View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.img_show_name);
            image_name = itemView.findViewById(R.id.img_show_img);
        }
    }


}
