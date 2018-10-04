package com.example.adrian.grouptaskmanagement;

import android.app.DatePickerDialog;
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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Create_Job3 extends Fragment implements dialog_worker_slot.dialogListener_worker, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    @Nullable

    EditText desc;
    TextView worker;
    TextView date;
    TextView time;
    EditText JobName;
    String current_date;
    String current_time;
    String state;
    private z_recycler_adapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_job, container, false);

        Button crt_job_btn = (Button) view.findViewById(R.id.crt_job);
        desc = (EditText) view.findViewById(R.id.desc_input_job);
        worker = (TextView) view.findViewById(R.id.slot_input_job);
        date = (TextView) view.findViewById(R.id.date_input_job);
        time = (TextView) view.findViewById(R.id.date_time_input_job);
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
                opendialog_date();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog_time();
            }
        });


        crt_job_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background background = new background(getContext());
                //worker minimal
                String send = "create_job-" + JobName.getText().toString() + "-" + desc.getText().toString() + "-" + state + "-" + "," + current_date + "," + current_time;
                background.getListener(new background.OnUpdateListener() {
                    @Override
                    public void onUpdate(String obj) {
                        Toast.makeText(getContext(), obj, Toast.LENGTH_SHORT).show();
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
        String title = JobName.getText().toString();
        String description = desc.getText().toString();
        String priority = state;

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        /*CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("test");
        notebookRef.add(new z_recycler_note(title, description, priority));*/

        DocumentReference notebookRef1 = FirebaseFirestore.getInstance()
                .document("List_Job/"+obj);
        notebookRef1.set(new com.example.adrian.grouptaskmanagement.z_recycler_note(title, description, priority));

        Toast.makeText(getContext(), "Note added", Toast.LENGTH_SHORT).show();
    }

    private void update() {
        desc.setText("");
        JobName.setText("");
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Create_Job3) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void opendialog_worker() {
        dialog_worker_slot dialogfragment = new dialog_worker_slot();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getFragmentManager(), "exa");

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


    public void apply_worker(String wasd) {
        worker.setText(wasd);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat format_1 = new SimpleDateFormat("yyyy-MM-dd");
        current_date = format_1.format(c.getTime());
        //String current_display = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        date.setText(current_date);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        //String current_display = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        SimpleDateFormat format_1 = new SimpleDateFormat("hh:mm");
        current_time = format_1.format(c.getTime());
        //-->current_time = (hour+":"+minute+":00");
        //-->time.setText(hour+":"+minute);
        time.setText(current_time);
    }

}
