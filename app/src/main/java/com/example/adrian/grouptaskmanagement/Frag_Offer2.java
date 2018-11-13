package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



public class Frag_Offer2 extends Fragment {
    @Nullable
    Context context;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref = db.collection("List_Job");
    private Frag_Offer_recycler_adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.offer_tab_avaible2, container, false);
        setup(view);
        return view;
    }

    private void setup(View view){
        Query query = ref.whereEqualTo("status","on");
        FirestoreRecyclerOptions<Frag_Offer_recycler_job> options = new FirestoreRecyclerOptions.Builder<Frag_Offer_recycler_job>().setQuery(query,Frag_Offer_recycler_job.class).build();
        adapter = new Frag_Offer_recycler_adapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.setOnItem(new Frag_Offer_recycler_adapter.onitemclickListener() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                z_recycler_note note = documentSnapshot.toObject(z_recycler_note.class);
                String id = documentSnapshot.getId();
                //Toast.makeText(getContext(), "pos: "+position+" id: "+id, Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Offer_avaible_task2(), id).addToBackStack(null).commit();
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
