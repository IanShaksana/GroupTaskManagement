package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.os.AsyncTask;
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

public class updateable_listview extends Fragment {
    @Nullable

    private SectionPageAdapter msectionadapter;
    private ViewPager mviewpager;
    ListView listView;
    Context context;
    String[] split;
    ListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        final View view = inflater.inflate(R.layout.offer_tab_avaible, container, false);
        bck_update request_offer = new bck_update(getContext());
        request_offer.getListener(new bck_update.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                split = obj.split("-");
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, split);
                listView = (ListView) view.findViewById(R.id.avaible_task);
                listView.setAdapter(adapter);
            }
        });
        request_offer.execute("request_user");
        return view;
    }


    public static class bck_update extends AsyncTask<String, Void, Void> {
        Context currentAct;
        String dataset;

        public bck_update(Context currentAct) {
            this.currentAct = currentAct;
        }

        public interface OnUpdateListener {
            public void onUpdate(String obj);
        }

        OnUpdateListener listener;

        public void getListener(OnUpdateListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(String... strings) {
            while (true){
                TCP tcp = new TCP(currentAct);
                dataset =tcp.setupCon(strings[0]);
                listener.onUpdate(dataset);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
