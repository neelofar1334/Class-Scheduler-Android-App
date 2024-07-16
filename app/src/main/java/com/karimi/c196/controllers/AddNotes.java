package com.karimi.c196.controllers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karimi.c196.R;
import com.karimi.c196.database.Repository;
import com.karimi.c196.entities.Notes;

public class AddNotes extends MenuActivity {
    private EditText noteTitleEditText, noteTextEditText;
    private Button saveButton, cancelButton;
    private Repository repository;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        //initialize repository and UI components
        repository = new Repository(getApplication());
        noteTitleEditText = findViewById(R.id.noteTitle);
        noteTextEditText = findViewById(R.id.noteEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        //retrieve courseId from Intent extras
        courseId = getIntent().getIntExtra("courseId", -1);
        if (courseId == -1) {
            Toast.makeText(this, "Invalid Course ID", Toast.LENGTH_LONG).show();
            finish();
        }

        saveButton.setOnClickListener(v -> saveNote());

        setupCancelButton();
    }

    private void saveNote() {
        String title = noteTitleEditText.getText().toString().trim();
        String text = noteTextEditText.getText().toString().trim();

        if (validateInputs(title, text)) {
            Notes note = new Notes(title, text, courseId);
            repository.insert(note);
            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //Validation
    private boolean validateInputs(String title, String text) {
        if (title.isEmpty() || text.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }
}
