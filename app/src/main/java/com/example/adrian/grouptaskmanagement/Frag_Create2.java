package com.example.adrian.grouptaskmanagement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Create2 extends Fragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    @Nullable
    EditText taskname;
    EditText taskdesc;
    TextView taskdiff;
    TextView tasktype;
    TextView taskdate;
    TextView tasktime;

    String current_date;
    String current_time;
    String state;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getTag());
        View view = inflater.inflate(R.layout.create_task2,container, false);

        Button crt_job_btn = (Button) view.findViewById(R.id.crt_task2);

        taskname =(EditText) view.findViewById(R.id.task_name2);
        taskdesc =(EditText) view.findViewById(R.id.desc_input_task2);
        taskdiff =(TextView) view.findViewById(R.id.diff_input_task2);
        tasktype =(TextView) view.findViewById(R.id.type_input_task2);
        taskdate =(TextView) view.findViewById(R.id.date_input_task2);
        tasktime =(TextView) view.findViewById(R.id.date_time_input_task2);

        taskdiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog_diff();

                if(taskdiff.getText().toString().equals("")){
                    taskdiff.setText("1");
                }else if (taskdiff.getText().toString().equals("1")){
                    taskdiff.setText("2");
                }else if(taskdiff.getText().toString().equals("2")){
                    taskdiff.setText("3");
                }else if(taskdiff.getText().toString().equals("3")){
                    taskdiff.setText("1");
                }

            }
        });
        tasktype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog_type();

                if(tasktype.getText().toString().equals("")){
                    tasktype.setText("Str");
                }else if (tasktype.getText().toString().equals("Str")){
                    tasktype.setText("Dex");
                }else if(tasktype.getText().toString().equals("Dex")){
                    tasktype.setText("Int");
                }else if(tasktype.getText().toString().equals("Int")){
                    tasktype.setText("Str");
                }
            }
        });
        taskdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog_date();
            }
        });
        tasktime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog_time();
            }
        });




        crt_job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //textView.setText("lol");
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentBottom, new Frag_Create2()).commit();
                background background = new background(getContext());
                String send ="create_task-"+taskname.getText().toString()+"-"+taskdesc.getText().toString()+"-"+tasktype.getText().toString()+"-"+taskdiff.getText().toString()+"-"+getTag()+"-,"+current_date+","+current_time;
                background.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        Toast.makeText(getContext(),"Create Task Success",Toast.LENGTH_SHORT).show();
                        clearBackStack();
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new list_task_leader(),getTag()).addToBackStack(null).commit();

                    }
                });
                background.execute(send);
                //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom, new Frag_Create3()).addToBackStack(null).commit();
            }
        });
        return view;
    }


    private void opendialog_type() {
        //dialog_worker_slot dialogfragment = new dialog_worker_slot();
        //dialogfragment.setTargetFragment(this,0);
        //dialogfragment.show(getFragmentManager(),"exa");

    }

    private void opendialog_diff() {
        //dialog_worker_slot dialogfragment = new dialog_worker_slot();
        //dialogfragment.setTargetFragment(this,0);
        //dialogfragment.show(getFragmentManager(),"exa");

    }


    private void opendialog_date() {
        dialog_date dialogfragment = new dialog_date();
        dialogfragment.setTargetFragment(this,0);
        dialogfragment.show(getFragmentManager(),"exa");

    }

    private void opendialog_time() {
        dialog_time dialogfragment = new dialog_time();
        dialogfragment.setTargetFragment(this,0);
        dialogfragment.show(getFragmentManager(),"exa");

    }



    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat format_1 = new SimpleDateFormat("yyyy-MM-dd");
        current_date = format_1.format(c.getTime());
        String current_display = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        taskdate.setText(current_display);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        //String current_display = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        current_time = (hour+":"+minute+":00");
        tasktime.setText(hour+":"+minute);
    }

    private void clearBackStack() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
