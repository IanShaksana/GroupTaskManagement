package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Inbox extends Fragment{
    @Nullable
    View view;
    String state,unprocessed_msg;
    ListView listView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Inbox");
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State",MODE_PRIVATE);
        state = preferences.getString("Login_State","");
        view = inflater.inflate(R.layout.inbox,container, false);

        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                unprocessed_msg =obj;
                String[] processed_msg = unprocessed_msg.split("-msg-");
                ListAdapter adapter = new advancedcustomadapter_inbox(getContext(), processed_msg);
                listView = (ListView) view.findViewById(R.id.list_message);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                        Toast.makeText(getContext(),choosen,Toast.LENGTH_SHORT).show();

                        PopupMenu pop1 = new PopupMenu(getContext(),view,  Gravity.CENTER);
                        try {
                            Field[] fields = pop1.getClass().getDeclaredFields();
                            for (Field field : fields) {
                                if ("mPopup".equals(field.getName())) {
                                    field.setAccessible(true);
                                    Object menuPopupHelper = field.get(pop1);
                                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                                    setForceIcons.invoke(menuPopupHelper, true);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pop1.getMenuInflater().inflate(R.menu.popupreply1,pop1.getMenu());

                        PopupMenu pop2 = new PopupMenu(getContext(),view,  Gravity.CENTER);
                        try {
                            Field[] fields = pop2.getClass().getDeclaredFields();
                            for (Field field : fields) {
                                if ("mPopup".equals(field.getName())) {
                                    field.setAccessible(true);
                                    Object menuPopupHelper = field.get(pop2);
                                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                                    setForceIcons.invoke(menuPopupHelper, true);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pop2.getMenuInflater().inflate(R.menu.popupreply2,pop2.getMenu());

                        PopupMenu pop3 = new PopupMenu(getContext(),view,  Gravity.CENTER);
                        try {
                            Field[] fields = pop3.getClass().getDeclaredFields();
                            for (Field field : fields) {
                                if ("mPopup".equals(field.getName())) {
                                    field.setAccessible(true);
                                    Object menuPopupHelper = field.get(pop3);
                                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                                    setForceIcons.invoke(menuPopupHelper, true);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pop3.getMenuInflater().inflate(R.menu.popupreply3,pop3.getMenu());

                        final String[] p1 = choosen.split("-");
                        switch (p1[1]){
                            case "apply":
                                pop1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getTitle().toString()){
                                            case "View Worker":
                                                Toast.makeText(getContext(), "View Worker",Toast.LENGTH_SHORT).show();
                                                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_assign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                                break;
                                            case "Accept":
                                                Toast.makeText(getContext(), "Accept",Toast.LENGTH_SHORT).show();
                                                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_reassign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                                break;
                                            case "Reject":
                                                Toast.makeText(getContext(), "Reject",Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                        return true;
                                    }
                                });
                                pop1.show();
                                break;
                            case "assign":
                                pop2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getTitle().toString()){
                                            case "View Task":
                                                Toast.makeText(getContext(), "View Task",Toast.LENGTH_SHORT).show();
                                                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_assign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                                break;
                                            case "Accept Task":
                                                Toast.makeText(getContext(), "Accept Task",Toast.LENGTH_SHORT).show();
                                                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_reassign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                                break;
                                            case "Reject Task":
                                                Toast.makeText(getContext(), "Reject Task",Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                        return true;
                                    }
                                });
                                pop2.show();
                                break;
                            case "invite":
                                pop3.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getTitle().toString()){
                                            case "View Job":
                                                Toast.makeText(getContext(), "View Job",Toast.LENGTH_SHORT).show();
                                                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_assign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                                break;
                                            case "View Task":
                                                Toast.makeText(getContext(), "View Task",Toast.LENGTH_SHORT).show();
                                                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_reassign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                                break;
                                            case "Accept Job":
                                                Toast.makeText(getContext(), "Accept job",Toast.LENGTH_SHORT).show();
                                                break;
                                            case "Reject Job":
                                                Toast.makeText(getContext(), "Accept job",Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                        return true;
                                    }
                                });
                                pop3.show();
                                break;
                        }


                    }
                });
            }
        });
        background.execute("get_message-"+state);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void update(){
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_worker_management){
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
