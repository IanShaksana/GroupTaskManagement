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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Create_Task2 extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
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
        View view = inflater.inflate(R.layout.create_task, container, false);

        Button crt_job_btn = (Button) view.findViewById(R.id.crt_task2);

        taskname = (EditText) view.findViewById(R.id.task_name2);
        taskdesc = (EditText) view.findViewById(R.id.desc_input_task2);
        taskdiff = (TextView) view.findViewById(R.id.diff_input_task2);
        tasktype = (TextView) view.findViewById(R.id.type_input_task2);
        taskdate = (TextView) view.findViewById(R.id.date_input_task2);
        tasktime = (TextView) view.findViewById(R.id.date_time_input_task2);



        taskdiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskdiff.getText().toString().equals("")) {
                    taskdiff.setText("1");
                } else if (taskdiff.getText().toString().equals("1")) {
                    taskdiff.setText("2");
                } else if (taskdiff.getText().toString().equals("2")) {
                    taskdiff.setText("3");
                } else if (taskdiff.getText().toString().equals("3")) {
                    taskdiff.setText("1");
                }

            }
        });
        tasktype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tasktype.getText().toString().equals("")) {
                    tasktype.setText("Str");
                } else if (tasktype.getText().toString().equals("Str")) {
                    tasktype.setText("Dex");
                } else if (tasktype.getText().toString().equals("Dex")) {
                    tasktype.setText("Int");
                } else if (tasktype.getText().toString().equals("Int")) {
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
                background background = new background(getContext());
                String send = "create_task-" + taskname.getText().toString() + "-" + taskdesc.getText().toString() + "-" + tasktype.getText().toString() + "-" + taskdiff.getText().toString() + "-" + getTag() + "-," + current_date + "," + current_time;
                background.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        Toast.makeText(getContext(), "Create Task Success", Toast.LENGTH_SHORT).show();
                        saveNote(obj);
                        update();
                    }
                });
                background.execute(send);
            }
        });
        return view;
    }

    private void saveNote(String obj) {
        String title = taskname.getText().toString();
        String desc = taskdesc.getText().toString();
        String diff = taskdiff.getText().toString();
        String type = tasktype.getText().toString();
        String time = current_date;
        String time2 = current_time;

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        /*CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("test");
        notebookRef.add(new z_recycler_note(title, description, priority));*/

        DocumentReference notebookRef1 = FirebaseFirestore.getInstance()
                .document("List_Job/"+getTag()+"/"+"List_Task/"+obj);
        notebookRef1.set(new com.example.adrian.grouptaskmanagement.Frag_Offer_recycler_task(title, desc, diff,type,time,time2,"none"));
        Toast.makeText(getContext(), "Note added", Toast.LENGTH_SHORT).show();
    }


    private void opendialog_date() {
        dialog_date dialogfragment = new dialog_date();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");

    }

    private void opendialog_time() {
        dialog_time dialogfragment = new dialog_time();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat format_1 = new SimpleDateFormat("yyyy-MM-dd");
        current_date = format_1.format(c.getTime());
        taskdate.setText(current_date);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        SimpleDateFormat format_1 = new SimpleDateFormat("hh:mm");
        current_time = format_1.format(c.getTime());
        tasktime.setText(current_time);
    }

    private void clearBackStack() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void update() {
        taskname.setText("");
        taskdesc.setText("");
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Create_Task2) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
