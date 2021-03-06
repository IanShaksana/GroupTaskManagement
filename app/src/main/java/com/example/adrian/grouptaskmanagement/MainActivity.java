package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences preferences = this.getSharedPreferences("State", MODE_PRIVATE);
        String state = preferences.getString("Login_State", "");
        FirebaseMessaging.getInstance().subscribeToTopic(state);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentBottom, new Frag_Home2()).commit();
        Intent mServiceIntent = new Intent(this, pingService.class);
        startService(mServiceIntent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_Home:
                    selectedFragment = new Frag_Home2();
                    clearBackStack();
                    break;
                case R.id.nav_Offer:
                    selectedFragment = new Frag_Offer2();
                    clearBackStack();
                    break;
                    /*
                case R.id.nav_Create:
                    selectedFragment = new Frag_Create_Job();
                    clearBackStack();
                    break;
                    */
                case R.id.nav_List:
                    selectedFragment = new Frag_List();
                    clearBackStack();
                    break;
                case R.id.nav_Inbox:
                    selectedFragment = new Frag_Inbox2();
                    clearBackStack();
                    break;
            }
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.ani1, R.anim.ani2, R.animator.popenter, R.animator.popexit).replace(R.id.fragmentBottom, selectedFragment).commit();
            return true;
        }
    };


    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.ani1, R.anim.ani2);
    }
}
