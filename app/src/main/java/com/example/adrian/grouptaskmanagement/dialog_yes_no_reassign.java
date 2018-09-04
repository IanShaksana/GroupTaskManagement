package com.example.adrian.grouptaskmanagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Adrian on 6/8/2018.
 */

public class dialog_yes_no_reassign extends AppCompatDialogFragment {
    private dialog_yes_no_reassign.dialogListener_yes_no_abandon listener;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_yes_no_abandon,null);
        builder.setView(view).setTitle("Warning").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.apply_abandoned("Abandoned");
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (dialog_yes_no_reassign.dialogListener_yes_no_abandon)getTargetFragment();
    }
    public interface dialogListener_yes_no_abandon{
        void apply_abandoned(String wasd);
    }

}
