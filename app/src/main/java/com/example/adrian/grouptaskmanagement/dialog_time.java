package com.example.adrian.grouptaskmanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Adrian on 5/31/2018.
 */

public class dialog_time extends AppCompatDialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int day = c.get(Calendar.SECOND);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getTargetFragment(), hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}
