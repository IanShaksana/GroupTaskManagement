package com.example.adrian.grouptaskmanagement;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


/**
 * Created by Adrian on 10/4/2018.
 */

public class Frag_Offer_avaible_task_recycler_adapter extends FirestoreRecyclerAdapter<Frag_Offer_recycler_task,Frag_Offer_avaible_task_recycler_adapter.holder> {

    private onitemclickListener varlistener;

    public Frag_Offer_avaible_task_recycler_adapter(@NonNull FirestoreRecyclerOptions<Frag_Offer_recycler_task> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull Frag_Offer_recycler_task model) {
        holder.t1.setText(model.getTitle());
        holder.t2.setText(model.getDesc());
        holder.t3.setText(model.getDiff());
        holder.t4.setText(model.getType());
        holder.t5.setText(model.getTime());
        holder.t6.setText(model.getTime2());
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_task2,parent,false);
        return new holder(v);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        TextView t6;

        public holder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.task_name_view);
            t2 = itemView.findViewById(R.id.desc_input_view);
            t3 = itemView.findViewById(R.id.diff_input_view);
            t4 = itemView.findViewById(R.id.type_input_view);
            t5 = itemView.findViewById(R.id.date_input_view);
            t6 = itemView.findViewById(R.id.date_time_input_view);

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

    public void setOnItem(onitemclickListener listener){
        this.varlistener = listener;
    }

    public void delitem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

}
