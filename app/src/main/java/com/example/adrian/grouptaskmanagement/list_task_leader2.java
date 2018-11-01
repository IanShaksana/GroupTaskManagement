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
 * Created by Adrian on 6/3/2018.
 */

public class list_task_leader2 extends Fragment {
    @Nullable
    String state,IDJOB;
    FloatingActionButton floatingActionButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref;
    private list_task_leader2_recycler_adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Avaible Task");
        final View view = inflater.inflate(R.layout.list_task_leader2, container, false);
        IDJOB = getTag();
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.FAB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Create_Task2(), getTag()).addToBackStack(null).commit();
            }
        });
        ref = db.collection("List_Job/"+IDJOB+"/List_Task");
        setup(view);
        return view;
    }

    private void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof list_task_leader2) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void setup(View view){
        final View FinalView =view;
        Query query = ref.orderBy("title",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Frag_Offer_recycler_task> options = new FirestoreRecyclerOptions.Builder<Frag_Offer_recycler_task>().setQuery(query,Frag_Offer_recycler_task.class).build();
        adapter = new list_task_leader2_recycler_adapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.list_task_leader_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItem(new list_task_leader2_recycler_adapter .onitemclickListener() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                Frag_Offer_recycler_task note = documentSnapshot.toObject(Frag_Offer_recycler_task.class);
                String IDTASK = documentSnapshot.getId();
                String status = note.getStatus();
                Toast.makeText(getContext(), "ID_Job: "+IDJOB+" id: "+IDTASK, Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new showprove_deletetask(),IDTASK+"-"+IDJOB+"-"+status).addToBackStack(null).commit();
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