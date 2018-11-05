package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Home2 extends Fragment {
    @Nullable
    String state;
    private RadarChart chart;
    private static final float MAX =12, MIN = 1f;
    private static final int random =5;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home2, container, false);
        //Snackbar.make(container,"hai from snackbar",10000).show();

        final TextView username = (TextView) view.findViewById(R.id.username);
        final TextView Exp_cur = (TextView) view.findViewById(R.id.exp_cur);
        final TextView Exp_nex = (TextView) view.findViewById(R.id.exp_nex);
        final ProgressBar exp = view.findViewById(R.id.exp_bar);
        chart = view.findViewById(R.id.radarchart);

        //chart.setBackgroundColor(Color.rgb(60,65,82));
        chart.setClickable(false);
        chart.setWebLineWidth(1f);
        chart.setWebLineWidthInner(1f);
        chart.setWebColor(Color.BLACK);
        chart.setWebColorInner(Color.BLACK);
        chart.setWebAlpha(100);
        chart.animateXY(1400,1400, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(13f);

        YAxis yAxis = chart.getYAxis();
        yAxis.setDrawLabels(false);
        chart.setDescription("");

        Legend l = chart.getLegend();
        l.setEnabled(false);

        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        username.setText(state);

        background background_home = new background(getContext());
        background_home.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                String[] split = obj.split("-");

                Exp_cur.setText(split[1]);
                Exp_nex.setText(split[5]);

                setdata(Integer.parseInt(split[2]),Integer.parseInt(split[3]),Integer.parseInt(split[4]));

                int set1 = Integer.parseInt(split[1]);
                int set2 = Integer.parseInt(split[5]);
                int set3 = 100* set1/set2;
                exp.setProgress(set3);
            }
        });
        background_home.execute("request_home-" + state);

        Button button = (Button) view.findViewById(R.id.btn_board);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Board()).addToBackStack(null).commit();
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
    }

    public void setdata(int str, int agi, int intel) {
        ArrayList<Entry> player1 = new ArrayList<>();
        player1.add(new Entry(agi,3));
        player1.add(new Entry(str,2));
        player1.add(new Entry(intel,1));

        RadarDataSet set1 = new RadarDataSet(player1,"player1");

        set1.setColor(Color.RED);
        set1.setFillColor(Color.RED);
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);

        ArrayList<RadarDataSet> sets = new ArrayList<>();
        sets.add(set1);

        ArrayList<String> qualities = new ArrayList<String>();
        qualities.add("AGL");
        qualities.add("STR");
        qualities.add("INT");

        RadarData data = new RadarData(qualities,sets);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        data.setDrawValues(true);

        chart.setData(data);
        chart.invalidate();
    }

}
