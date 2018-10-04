package com.example.adrian.grouptaskmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Adrian on 10/4/2018.
 */

public class z_new_recycler extends AppCompatActivity{
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_new_recycler);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //setTitle("Add Note");

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        Button button = findViewById(R.id.Abtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

    }



    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String priority = String.valueOf(numberPickerPriority.getValue());

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        /*CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("test");
        notebookRef.add(new z_recycler_note(title, description, priority));*/

        DocumentReference notebookRef1 = FirebaseFirestore.getInstance()
                .document("test/wasd");
        notebookRef1.set(new z_recycler_note(title, description, priority));

        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        finish();
    }
}
