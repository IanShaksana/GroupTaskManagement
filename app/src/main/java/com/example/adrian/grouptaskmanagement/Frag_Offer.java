package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Offer extends Fragment {
    @Nullable

    private SectionPageAdapter msectionadapter;
    private ViewPager mviewpager;
    ListView listView;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job Offer");
        context=getContext();
        final View view = inflater.inflate(R.layout.offer_tab_avaible,container, false);
        background request_offer = new background(getContext());
        request_offer.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                String[] split = obj.split("-");
                ListAdapter adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,split);
                listView = (ListView) view.findViewById(R.id.avaible_task);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new Frag_Offer_avaible_task(),choosen).addToBackStack(null).commit();
                        }
                    });
            }
        });
        request_offer.execute("request_job_offer");
        return view;
    }
    /*
    public void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
        adapter.addFragment(new offer_tab_avaible(),"Avaible");
        adapter.addFragment(new offer_tab_pending(),"Pending");
        viewPager.setAdapter(adapter);
    }
    */

}
