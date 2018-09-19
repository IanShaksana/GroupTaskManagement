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

public class dialog_yes_no_assign extends AppCompatDialogFragment {
    private dialog_yes_no_assign.dialogListener_yes_no_assign listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_assign, null);
        builder.setView(view).setTitle("Warning").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.apply_assign("yes");
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (dialog_yes_no_assign.dialogListener_yes_no_assign) getTargetFragment();
    }

    public interface dialogListener_yes_no_assign {
        void apply_assign(String wasd);
    }

}
