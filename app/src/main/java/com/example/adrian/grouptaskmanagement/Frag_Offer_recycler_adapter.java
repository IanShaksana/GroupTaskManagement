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

public class Frag_Offer_recycler_adapter extends FirestoreRecyclerAdapter<Frag_Offer_recycler_job,Frag_Offer_recycler_adapter.holder> {

    private onitemclickListener varlistener;

    public Frag_Offer_recycler_adapter(@NonNull FirestoreRecyclerOptions<Frag_Offer_recycler_job> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull Frag_Offer_recycler_job model) {
        holder.t1.setText(model.getTitle());
        holder.t2.setText(model.getDesc());
        holder.t3.setText(model.getTime());
        holder.t4.setText(model.getSlotmax());
        holder.t5.setText(model.getSlotnow());
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_avaible_job2_item,parent,false);
        return new holder(v);
    }

    class holder extends RecyclerView.ViewHolder{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;

        public holder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.title);
            t2 = itemView.findViewById(R.id.desc);
            t3 = itemView.findViewById(R.id.time1);
            t4 = itemView.findViewById(R.id.slot1);
            t5 = itemView.findViewById(R.id.slot2);

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
