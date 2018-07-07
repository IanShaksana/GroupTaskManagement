package com.example.adrian.grouptaskmanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_List2 extends Fragment {
    @Nullable

    private SectionPageAdapter msectionadapter;
    private ViewPager mviewpager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        //View view = inflater.inflate(R.layout.list2,container, false);

        msectionadapter = new SectionPageAdapter(getChildFragmentManager());

        //mviewpager = (ViewPager) view.findViewById(R.id.container);
        //setupViewPager(mviewpager);

        //TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout1);
        //tabLayout.setupWithViewPager(mviewpager);

        //return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
        adapter.addFragment(new list_tab_task(),"Task");
        adapter.addFragment(new list_tab_worker(),"Worker Slot");
        viewPager.setAdapter(adapter);
    }
}
