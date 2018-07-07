package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Home extends Fragment {
    @Nullable
    String state;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Home");
        View view = inflater.inflate(R.layout.home,container, false);

        final TextView username = (TextView) view.findViewById(R.id.username);

        final TextView Str = (TextView) view.findViewById(R.id.str_qty);
        final TextView Agi = (TextView) view.findViewById(R.id.agi_qty);
        final TextView Intel = (TextView) view.findViewById(R.id.intel_qty);

        final TextView Exp_cur = (TextView) view.findViewById(R.id.exp_cur);
        final TextView Exp_nex = (TextView) view.findViewById(R.id.exp_nex);

        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State",MODE_PRIVATE);
        state = preferences.getString("Login_State","");


        background background_home = new background(getContext());
        background_home.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                String[] split = obj.split("-");
                username.setText(split[0]);
                Str.setText(split[2]);
                Agi.setText(split[3]);
                Intel.setText(split[4]);
                Exp_cur.setText(split[1]);
                Exp_nex.setText("~");
            }
        });
        background_home.execute("request_home-"+state);
        Button button = (Button) view.findViewById(R.id.btn_board);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_Board()).addToBackStack(null).commit();

            }
        });

        Button button2 = (Button) view.findViewById(R.id.LogOut);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();
            }
        });
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
