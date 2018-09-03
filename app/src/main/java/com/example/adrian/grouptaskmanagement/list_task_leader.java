package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
 * Created by Adrian on 6/3/2018.
 */

public class list_task_leader extends Fragment {
    @Nullable

    String unprocessed_task;
    ListView listView;
    String state;
    FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Avaible Task");
        final View view = inflater.inflate(R.layout.list_task_leader,container, false);
        String ID_job= getTag();
        //clearBackStack();

        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State",MODE_PRIVATE);
        state = preferences.getString("Login_State","");
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.FAB);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new Frag_Create2(),getTag()).addToBackStack(null).commit();
            }
        });

        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                unprocessed_task =obj;
                if(obj.equals("No Task")){
                    Toast.makeText(getContext(),"No Task Avaible",Toast.LENGTH_SHORT).show();
                }else {
                    String[] processed_task = unprocessed_task.split("-LIST-");
                    ListAdapter adapter = new advancedcustomadapter_as_leader(getContext(), processed_task);
                    listView = (ListView) view.findViewById(R.id.list_task_leader);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                            //Toast.makeText(getContext(),choosen,Toast.LENGTH_SHORT).show();

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
                            pop1.getMenuInflater().inflate(R.menu.popup1,pop1.getMenu());


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
                            pop2.getMenuInflater().inflate(R.menu.popup,pop2.getMenu());

                            final String[] p1 = choosen.split("-");
                            if(p1[5].equals("no")){
                                Toast.makeText(getContext(),"Not Completed Yet",Toast.LENGTH_SHORT).show();
                                pop1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        //ini jangan disini
                                        if(menuItem.getTitle().toString().equals("Remove Worker")){
                                            Toast.makeText(getContext(), "Remove Worker",Toast.LENGTH_SHORT).show();
                                            //background background1 = new background(getContext());
                                            //background1.execute("approve_yes-"+p1[6]);

                                        }else {
                                            Toast.makeText(getContext(), "Delete Task",Toast.LENGTH_SHORT).show();
                                            background background1 = new background(getContext());
                                            background1.execute("Delete_task-"+p1[6]);
                                        }
                                        return true;
                                    }
                                });
                                pop1.show();

                            }else {
                                pop2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {

                                        if(menuItem.getTitle().toString().equals("Approve")){
                                            Toast.makeText(getContext(), "Approve",Toast.LENGTH_SHORT).show();
                                            background background1 = new background(getContext());
                                            background1.execute("approve_yes-"+p1[6]);

                                        }else if(menuItem.getTitle().toString().equals("Disapprove")) {
                                            Toast.makeText(getContext(), "Disapprove",Toast.LENGTH_SHORT).show();
                                            background background1 = new background(getContext());
                                            background1.getListener(new background.OnUpdateListener() {
                                                @Override
                                                public void onUpdate(String obj) {
                                                    update();
                                                }
                                            });
                                            background1.execute("approve_no-"+p1[6]);
                                        }else{
                                            Toast.makeText(getContext(), "Delete Task",Toast.LENGTH_SHORT).show();
                                            background background1 = new background(getContext());
                                            background1.execute("Delete_task-"+p1[6]);
                                        }
                                        return true;
                                    }
                                });
                                pop2.show();
                            }

                            //background background1 =new background(getContext());
                            //String[] selected_task = choosen.split("-");
                            //background1.execute("request_apply_task-"+state+"-"+selected_task[0]);
                            //getFragmentManager().beginTransaction().replace(R.id.fragmentBottom,new Frag_List()).addToBackStack(null).commit();
                        }
                    });
                }

            }
        });
        background.execute("request_job_task-"+ID_job);

        return view;

    }
    private void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void update(){
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof list_task_leader){
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}