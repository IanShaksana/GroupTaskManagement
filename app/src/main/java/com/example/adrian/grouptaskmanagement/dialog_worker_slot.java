package com.example.adrian.grouptaskmanagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Adrian on 5/30/2018.
 */

public class dialog_worker_slot extends AppCompatDialogFragment {

    private dialogListener_worker listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_worker_slot, null);
        final EditText editText = (EditText) view.findViewById(R.id.dialog_input_worker);
        //editText.setRawInputType(Configuration.KEYBOARD_12KEY);
        builder.setView(view).setTitle("Worker Slot").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String dialog = editText.getText().toString();
                listener.apply_worker(dialog);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (dialogListener_worker) getTargetFragment();
    }

    public interface dialogListener_worker {
        void apply_worker(String wasd);
    }


}
