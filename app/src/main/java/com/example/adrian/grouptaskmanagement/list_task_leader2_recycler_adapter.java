package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 6/3/2018.
 */

public class list_task_leader2_recycler_adapter extends FirestoreRecyclerAdapter<Frag_Offer_recycler_task,list_task_leader2_recycler_adapter.holder> {
    private list_task_leader2_recycler_adapter .onitemclickListener varlistener;



    public list_task_leader2_recycler_adapter(@NonNull FirestoreRecyclerOptions<Frag_Offer_recycler_task> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull list_task_leader2_recycler_adapter.holder holder, int position, @NonNull Frag_Offer_recycler_task model) {
        holder.jobname.setText(model.getTitle());
        holder.description.setText(model.getDesc());
        holder.difficulty.setText(model.getDiff());
        holder.worker.setText(model.getWorker());
        holder.type.setText(model.getType());
        holder.completion.setText(model.getStatus());
        holder.date.setText(model.getTime());
        holder.date2.setText(model.getTime2());

    }

    @NonNull
    @Override
    public list_task_leader2_recycler_adapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytask_leader,parent,false);
        return new list_task_leader2_recycler_adapter.holder(v);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView jobname;
        TextView jobID;
        TextView name;
        TextView description;
        TextView difficulty;
        TextView type;
        TextView date;
        TextView date2;
        TextView worker;
        TextView completion;

        public holder(View itemView) {
            super(itemView);
            jobname = (TextView) itemView.findViewById(R.id.worker_job_name);
            jobID = (TextView) itemView.findViewById(R.id.job_ID);
            name = (TextView) itemView.findViewById(R.id.task_name_view);
            description = (TextView) itemView.findViewById(R.id.desc_input_view);
            difficulty = (TextView) itemView.findViewById(R.id.diff_input_view);
            type = (TextView) itemView.findViewById(R.id.type_input_view);
            completion = (TextView) itemView.findViewById(R.id.completed_view);
            worker = (TextView) itemView.findViewById(R.id.user_worker_view);
            date = (TextView) itemView.findViewById(R.id.date_input_view);
            date2 = (TextView) itemView.findViewById(R.id.date_time_input_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && varlistener!=null){
                        varlistener.onitemclick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface onitemclickListener{
        void onitemclick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItem(list_task_leader2_recycler_adapter .onitemclickListener listener){
        this.varlistener = listener;
    }

    public void delitem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
}