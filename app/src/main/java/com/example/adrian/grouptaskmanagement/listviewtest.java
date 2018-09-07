package com.example.adrian.grouptaskmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Adrian on 5/19/2018.
 */

public class listviewtest extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlistview);

        String[] task = {"sing", "swim", "eat", "chore", "sing", "swim", "eat", "chore", "sing", "swim", "eat", "chore", "sing", "swim", "eat", "chore"};
        //ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,task);
        ListAdapter adapter = new advancedcustomadapter_offer(this, task);

        ListView listView = (ListView) findViewById(R.id.mylist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String choosen = String.valueOf(adapterView.getItemAtPosition(i));
                Toast.makeText(listviewtest.this, choosen, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
