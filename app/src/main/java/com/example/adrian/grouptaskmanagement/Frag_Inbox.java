package com.example.adrian.grouptaskmanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Inbox extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Inbox");
        return inflater.inflate(R.layout.inbox,container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
