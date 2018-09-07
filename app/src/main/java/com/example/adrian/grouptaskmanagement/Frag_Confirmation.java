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

public class Frag_Confirmation extends Fragment {
    @Nullable
    View view;
    String state, unprocessed_msg;
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Inbox");
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        view = inflater.inflate(R.layout.inbox, container, false);

        final background background = new background(getContext());
        background.getListener(new background.OnUpdateListener() {
            @Override
            public void onUpdate(String obj) {
                unprocessed_msg = obj;
                String[] processed_msg = unprocessed_msg.split("-msg-");
                ListAdapter adapter = new advancedcustomadapter_inbox(getContext(), processed_msg);
                listView = (ListView) view.findViewById(R.id.list_message);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                        Toast.makeText(getContext(), choosen, Toast.LENGTH_SHORT).show();

                        String[] choosensplit1 = choosen.split(",");
                        final String ID_User_W = choosensplit1[0];
                        final String ID_User_W2 = choosensplit1[1];
                        String[] choosensplit2 = choosen.split("-");
                        final String ID_Task_W = choosensplit2[2];

                        PopupMenu pop1 = new PopupMenu(getContext(), view, Gravity.CENTER);
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
                        pop1.getMenuInflater().inflate(R.menu.popupreply1, pop1.getMenu());
                    }
                });
            }
        });
        background.execute("get_message_conf-" + state);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Confirmation) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }
}
