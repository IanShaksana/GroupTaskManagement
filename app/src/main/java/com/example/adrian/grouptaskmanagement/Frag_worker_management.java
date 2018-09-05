package com.example.adrian.grouptaskmanagement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_worker_management extends Fragment implements dialog_worker_invite.dialogListener_worker {
    ListView listView;
    View view;
    Context context;
    Activity activity;
    String ID_Job;
    FloatingActionButton FAB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Job");
        activity=getActivity();
        ID_Job =getTag();
        context=activity.getApplicationContext();
        view = inflater.inflate(R.layout.worker_management2,container, false);

        FAB = (FloatingActionButton) view.findViewById(R.id.FAB);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog_worker();
            }
        });

        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                //Toast.makeText(getContext(),obj,Toast.LENGTH_SHORT).show();
                String[] processed_task = obj.split("-");
                ListAdapter adapter = new advancedcustomadapter_list_worker(getContext(), processed_task);
                listView = (ListView) view.findViewById(R.id.list_worker);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                        Toast.makeText(getContext(),choosen,Toast.LENGTH_SHORT).show();
                        PopupMenu pop = new PopupMenu(getContext(),view,  Gravity.CENTER);
                        try {
                            Field[] fields = pop.getClass().getDeclaredFields();
                            for (Field field : fields) {
                                if ("mPopup".equals(field.getName())) {
                                    field.setAccessible(true);
                                    Object menuPopupHelper = field.get(pop);
                                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                                    setForceIcons.invoke(menuPopupHelper, true);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pop.getMenuInflater().inflate(R.menu.popupworker,pop.getMenu());

                        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getTitle().toString()){
                                    case "Assign":
                                        Toast.makeText(getContext(), "Assign",Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_assign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                        break;
                                    case "Re Assign":
                                        Toast.makeText(getContext(), "Re Assign",Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_reassign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                        break;
                                    case "Remove Worker":
                                        Toast.makeText(getContext(), "Remove Worker",Toast.LENGTH_SHORT).show();
                                        background background1 =new background(getContext());
                                        background1.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                update();
                                            }
                                        });
                                        background1.execute("remove_worker-"+choosen+"-"+ID_Job);
                                        break;
                                }/*
                                if(menuItem.getTitle().toString().equals("Assign")){
                                    Toast.makeText(getContext(), "Remove Worker",Toast.LENGTH_SHORT).show();
                                    //background background1 = new background(getContext());
                                    //background1.execute("approve_yes-"+p1[6]);

                                }else {
                                    Toast.makeText(getContext(), "Delete Task",Toast.LENGTH_SHORT).show();
                                    //background background1 = new background(getContext());
                                    //background1.execute("Delete_task-"+p1[6]);
                                }*/
                                return true;
                            }
                        });
                        pop.show();
                    }
                });
            }
        });
        background.execute("request_worker-"+ID_Job);

        return view;
    }
    private void update(){
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_worker_management){
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void opendialog_worker() {
        dialog_worker_invite dialogfragment = new dialog_worker_invite();
        dialogfragment.setTargetFragment(this,0);
        dialogfragment.show(getFragmentManager(),"exa");

    }

    @Override
    public void apply_worker(String wasd) {
        //Toast.makeText(getContext(),"halo",Toast.LENGTH_SHORT).show();
        background background =new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                update();
            }
        });
        background.execute("invite_worker-"+wasd+"-"+ID_Job);
    }
}
