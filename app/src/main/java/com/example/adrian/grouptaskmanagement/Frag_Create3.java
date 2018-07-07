package com.example.adrian.grouptaskmanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Create3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        getActivity().setTitle("Create Job");
        return inflater.inflate(R.layout.create3,container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        */
        getActivity().setTitle("Create Job");
        View view = inflater.inflate(R.layout.create3,container, false);
        Button crt_job_btn = (Button) view.findViewById(R.id.crt_apply);
        final TextView textView = (TextView) view.findViewById(R.id.crt3);
        //Fragment nextfrag = new Frag_Create2();
        crt_job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("lol");
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentBottom, new Frag_Create2()).commit();
                //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom, new Frag_Create2()).commit();
            }
        });
        return view;
    }
}
