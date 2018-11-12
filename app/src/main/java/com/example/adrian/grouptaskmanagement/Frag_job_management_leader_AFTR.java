package com.example.adrian.grouptaskmanagement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_job_management_leader_AFTR extends Fragment {
    View view;
    Context context;
    Activity activity;
    String ID_Job, ID_Job2;
    ImageView finish, task, worker, cash_in;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity = getActivity();
        ID_Job = getTag();
        ID_Job2 = ID_Job;
        context = activity.getApplicationContext();
        view = inflater.inflate(R.layout.job_management_leader_aftr, container, false);

        Toast.makeText(getContext(), ID_Job2, Toast.LENGTH_SHORT).show();
        cash_in = (ImageView) view.findViewById(R.id.Cash_in);

        cash_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background background1 = new background(getContext());
                background1.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        DocumentReference choosenDoc = db.document("List_Job/"+ID_Job);
                        choosenDoc.delete();
                        getFragmentManager().popBackStack();
                    }
                });
                background1.execute("cashIn-"+ID_Job);
                getFragmentManager().popBackStack();
            }
        });


        return view;
    }
    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Offer_avaible_task) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
