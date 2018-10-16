package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Inbox2 extends Fragment {
    @Nullable
    View view;
    String state, unprocessed_msg;
    ListView listView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref;
    private Frag_Inbox_adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        ref = db.collection("Message/"+state+"/inbox");
        view = inflater.inflate(R.layout.inbox2, container, false);
        setup(view);

        return view;
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Inbox2) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void setup(View view){
        Query query = ref.orderBy("from",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Frag_Inbox_recycler> options = new FirestoreRecyclerOptions.Builder<Frag_Inbox_recycler>().setQuery(query,Frag_Inbox_recycler.class).build();
        adapter = new Frag_Inbox_adapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.list_message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.delitem(viewHolder.getAdapterPosition());
                //ada warning
                //ada delete query
            }
        }).attachToRecyclerView(recyclerView);*/

        adapter.setOnItem(new Frag_Inbox_adapter.onitemclickListener() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                z_recycler_note note = documentSnapshot.toObject(z_recycler_note.class);
                String id = documentSnapshot.getId();
                Toast.makeText(getContext(), "pos: "+position+" id: "+id, Toast.LENGTH_SHORT).show();
                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Offer_avaible_task2(), id).addToBackStack(null).commit();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
