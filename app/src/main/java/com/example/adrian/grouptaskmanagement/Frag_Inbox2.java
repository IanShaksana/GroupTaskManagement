package com.example.adrian.grouptaskmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Adrian on 5/18/2018.
 */

public class Frag_Inbox2 extends Fragment {
    @Nullable
    View view;
    String state, unprocessed_msg;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref;
    private Frag_Inbox_adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final SharedPreferences preferences = this.getActivity().getSharedPreferences("State", MODE_PRIVATE);
        state = preferences.getString("Login_State", "");
        ref = db.collection("Message/"+state+"/inbox");
        view = inflater.inflate(R.layout.inbox2, container, false);
        setup(view);

        return view;
    }

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.fragmentBottom);//getActivity().getFragmentManager().findFragmentById(R.id.fragmentBottom);
        if (current instanceof Frag_Inbox2) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void setup(View view){
        final View view1=view;
        Query query = ref.orderBy("from",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Frag_Inbox_recycler> options = new FirestoreRecyclerOptions.Builder<Frag_Inbox_recycler>().setQuery(query,Frag_Inbox_recycler.class).build();
        adapter = new Frag_Inbox_adapter(options);

        final RecyclerView recyclerView = view.findViewById(R.id.list_message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItem(new Frag_Inbox_adapter.onitemclickListener() {
            @Override
            public void onitemclick(final DocumentSnapshot documentSnapshot, final int position) {
                final Frag_Inbox_recycler note = documentSnapshot.toObject(Frag_Inbox_recycler.class);
                String id = documentSnapshot.getId();
                final String choosen = note.getMssgCode();
                String[] mssgCodesplit = choosen.split("-");
                final String ID_Task = mssgCodesplit[2];
                final String ID_Job = mssgCodesplit[2];
                final String ID_Job_vote = mssgCodesplit[2];
                final String ID_User_Worker =note.getFrom() ;

                String type = note.getType();
                Toast.makeText(getContext(), "type: "+type+" id: "+id, Toast.LENGTH_SHORT).show();

                PopupMenu pop1 = new PopupMenu(getContext(), recyclerView, Gravity.CENTER);
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

                PopupMenu pop2 = new PopupMenu(getContext(), view1, Gravity.CENTER);
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
                pop2.getMenuInflater().inflate(R.menu.popupreply2, pop2.getMenu());

                PopupMenu pop3 = new PopupMenu(getContext(), view1, Gravity.CENTER);
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
                pop3.getMenuInflater().inflate(R.menu.popupreply3, pop3.getMenu());

                PopupMenu pop4 = new PopupMenu(getContext(), view1, Gravity.CENTER);
                try {
                    Field[] fields = pop4.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(pop4);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pop4.getMenuInflater().inflate(R.menu.popupreply4, pop4.getMenu());

                switch (type) {
                    case "apply-task":
                        pop1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getTitle().toString()) {
                                    case "Compare":
                                        Toast.makeText(getContext(), "View Worker", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Status_W(), ID_User_Worker+"-"+ID_Task).addToBackStack(null).commit();
                                        break;
                                    case "Accept":
                                        Toast.makeText(getContext(), "Accept", Toast.LENGTH_SHORT).show();
                                        background background2 = new background(getContext());
                                        background2.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                String[] objsplit =obj.split("-");
                                                DocumentReference doc1 = db.document("List_Job/"+objsplit[1]);
                                                DocumentReference doc2 = db.document("List_Job/"+objsplit[1]+"/List_Task/"+ID_Task);
                                                doc1.update("slotnow",objsplit[2]);
                                                doc2.update("worker",ID_User_Worker);
                                                adapter.delitem(position);
                                            }
                                        });
                                        background2.execute("reply_message-apply_yes-" + ID_Task + "-" + ID_User_Worker + "-" + choosen);
                                        break;
                                    case "Reject":
                                        Toast.makeText(getContext(), "Reject", Toast.LENGTH_SHORT).show();
                                        background background3 = new background(getContext());
                                        background3.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                adapter.delitem(position);
                                            }
                                        });
                                        background3.execute("reply_message-apply_reject-" + ID_Task + "-" + ID_User_Worker + "-" + choosen);
                                        break;
                                }
                                return true;
                            }
                        });
                        pop1.show();
                        break;

                    case "assign-task":
                        pop2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getTitle().toString()) {
                                    case "View Task":
                                        Toast.makeText(getContext(), "View Task", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_detail_task(), ID_Task).addToBackStack(null).commit();
                                        break;
                                    case "Accept Task":
                                        Toast.makeText(getContext(), "Accept Task", Toast.LENGTH_SHORT).show();
                                        background background1 = new background(getContext());
                                        background1.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                String[] objsplit = obj.split("-");
                                                DocumentReference doc2 = db.document("List_Job/"+objsplit[0]+"/List_Task/"+ID_Task);
                                                doc2.update("worker",objsplit[1]);
                                                adapter.delitem(position);
                                            }
                                        });
                                        background1.execute("reply_message-assign_yes-" + ID_Task + "-" +  "-" + choosen);
                                        break;
                                    case "Reject Task":
                                        Toast.makeText(getContext(), "Reject Task", Toast.LENGTH_SHORT).show();
                                        background background2 = new background(getContext());
                                        background2.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                adapter.delitem(position);
                                                update();
                                            }
                                        });
                                        background2.execute("reply_message-assign_reject-" + ID_Task + "-"  + "-" + choosen);
                                        break;
                                }
                                return true;
                            }
                        });
                        pop2.show();
                        break;

                    case "invite-job":
                        pop3.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getTitle().toString()) {
                                    case "View Job":
                                        Toast.makeText(getContext(), "View Job", Toast.LENGTH_SHORT).show();
                                        //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_assign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                        break;
                                    case "View Task":
                                        Toast.makeText(getContext(), "View Task", Toast.LENGTH_SHORT).show();
                                        //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1,R.anim.ani2,R.animator.popenter,R.animator.popexit).replace(R.id.fragmentBottom,new leader_reassign(),ID_Job+"-"+choosen).addToBackStack(null).commit();
                                        break;
                                    case "Accept Job":
                                        Toast.makeText(getContext(), "Accept job", Toast.LENGTH_SHORT).show();
                                        background background1 = new background(getContext());
                                        background1.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                String[] objsplit = obj.split("-");
                                                DocumentReference doc2 = db.document("List_Job/"+objsplit[0]);
                                                doc2.update("slotnow",objsplit[1]);
                                                adapter.delitem(position);
                                            }
                                        });
                                        background1.execute("reply_message-invite_yes-" + ID_Job + "-"  + "-" + choosen);
                                        break;
                                    case "Reject Job":
                                        Toast.makeText(getContext(), ID_Job, Toast.LENGTH_SHORT).show();
                                        background background2 = new background(getContext());
                                        background2.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                adapter.delitem(position);
                                            }
                                        });
                                        background2.execute("reply_message-invite_reject-" + ID_Job + "-" + "-" + choosen);
                                        break;
                                }
                                return true;
                            }
                        });
                        pop3.show();
                        break;
                    case "vote":
                        pop4.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                //final String Custom_ID_Job = choosensplit2[3];
                                switch (menuItem.getTitle().toString()) {
                                    case "Up Vote":
                                        Toast.makeText(getContext(), state + " Up Vote " + ID_Job_vote, Toast.LENGTH_SHORT).show();
                                        background background1 = new background(getContext());
                                        background1.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                adapter.delitem(position);
                                            }
                                        });
                                        background1.execute("reply_message-UpVote-" +ID_Job_vote+"-"+state);
                                        break;
                                    case "Down Vote":
                                        Toast.makeText(getContext(), "Down Vote", Toast.LENGTH_SHORT).show();
                                        background background2 = new background(getContext());
                                        background2.getListener(new background.OnUpdateListener() {
                                            @Override
                                            public void onUpdate(String obj) {
                                                adapter.delitem(position);
                                                update();
                                            }
                                        });
                                        //background2.execute("reply_message-DownVote-" + Custom_ID_Job+"-"+ID_User_W2);
                                        break;
                                }
                                return true;
                            }
                        });
                        pop4.show();
                        break;
                }
                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, new Frag_Offer_avaible_task2(), id).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
