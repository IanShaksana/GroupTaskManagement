package com.example.adrian.grouptaskmanagement;

import android.app.DatePickerDialog;
import android.app.IntentService;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Create_Job2 extends Fragment implements dialog_worker_slot.dialogListener_worker, dialog_worker_time.dialogListener_time {
    @Nullable

    EditText desc;
    TextView worker;
    TextView date;
    //TextView time;
    EditText JobName;
    String current_date;
    String current_time;
    String state;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Create Job");
        View view = inflater.inflate(R.layout.create_job2, container, false);


        Button crt_job_btn = (Button) view.findViewById(R.id.crt_job);
        desc = (EditText) view.findViewById(R.id.desc_input_job);
        worker = (TextView) view.findViewById(R.id.slot_input_job);
        date = (TextView) view.findViewById(R.id.date_input_job);
        //time = (TextView) view.findViewById(R.id.date_time_input_job);
        JobName = (EditText) view.findViewById(R.id.job_name);



        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");

        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog_worker();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog_time();
            }
        });


        crt_job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(worker.getText().toString())>10){
                    Toast.makeText(getContext(),"Maximum worker is 10",Toast.LENGTH_SHORT).show();
                }else {
                    background background = new background(getContext());
                    //worker minimal
                    String send = "create_job-" + JobName.getText().toString() + "-" + desc.getText().toString() + "-" + state + "-" +date.getText().toString()+ "-" +worker.getText().toString();
                    background.getListener(new background.OnUpdateListener() {
                        @Override
                        public void onUpdate(String obj) {
                            Toast.makeText(getContext(), "Create Job Success", Toast.LENGTH_SHORT).show();
                            update();
                            //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_Offer()).commit();
                            //Toast.makeText(getContext(), obj, Toast.LENGTH_SHORT).show();
                        }
                    });
                    background.execute(send);
                }

            }
        });
        return view;

    }

    private void update() {
        desc.setText("");
        JobName.setText("");
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Create_Job2) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void opendialog_worker() {
        dialog_worker_slot dialogfragment = new dialog_worker_slot();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");

    }

    private void opendialog_time() {
        dialog_worker_time dialogfragment = new dialog_worker_time();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");

    }


    public void apply_worker(String wasd) {
        worker.setText(wasd);
    }

    @Override
    public void apply_time(String wasd) {
        date.setText(wasd);
    }
}
