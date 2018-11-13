package com.example.adrian.grouptaskmanagement;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
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

public class Frag_Inbox_adapter extends FirestoreRecyclerAdapter<Frag_Inbox_recycler,Frag_Inbox_adapter.holder> {

    private onitemclickListener varlistener;

    public Frag_Inbox_adapter(@NonNull FirestoreRecyclerOptions<Frag_Inbox_recycler> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull Frag_Inbox_recycler model) {
        holder.t1.setText(model.getFrom());
        holder.t2.setText(model.getMssg());
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_detail2,parent,false);
        return new holder(v);
    }

    class holder extends RecyclerView.ViewHolder /*implements View.OnCreateContextMenuListener*/{
        TextView t1;
        TextView t2;

        public holder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.sender);
            t2 = itemView.findViewById(R.id.msg_name);
            /*itemView.setOnCreateContextMenuListener(this);*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && varlistener!=null){
                        varlistener.onitemclick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }/*

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select The Action");
            contextMenu.add(0, view.getId(), 0, "Call");//groupId, itemId, order, title
            contextMenu.add(0, view.getId(), 0, "SMS");

        }*/
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
