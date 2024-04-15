package com.example.c196.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.c196.R;
import com.example.c196.entities.Notes;
import com.example.c196.viewmodel.NotesViewModel;

//FEATURE DISABLED
public class EditNotes extends MenuActivity {
    private EditText noteTitleEditText, noteTextEditText;
    private Button saveButton, cancelButton;
    private NotesViewModel notesViewModel;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        noteTitleEditText = findViewById(R.id.noteTitle);
        noteTextEditText = findViewById(R.id.noteEditText);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize ViewModel
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        // Retrieve noteId from Intent extras
        noteId = getIntent().getIntExtra("noteId", -1);

        // Load existing note if a valid noteId is provided
        if (noteId != -1) {
            loadNote();
        } else {
            Toast.makeText(this, "Invalid Note ID", Toast.LENGTH_LONG).show();
            finish();
        }

        saveButton.setOnClickListener(v -> {
            String noteTitle = noteTitleEditText.getText().toString().trim();
            String noteText = noteTextEditText.getText().toString().trim();
            if (!noteTitle.isEmpty() && !noteText.isEmpty()) {
                updateNote(noteTitle, noteText);
            } else {
                Toast.makeText(this, "Please enter both title and text", Toast.LENGTH_SHORT).show();
            }
        });

        setupCancelButton();
        setupSaveButton();
    }

    private void loadNote() {
        notesViewModel.getNoteById(noteId).observe(this, note -> {
            if (note != null) {
                noteTitleEditText.setText(note.getNoteTitle());
                noteTextEditText.setText(note.getText());
            } else {
                Toast.makeText(this, "Note not found", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> {
            String noteTitle = noteTitleEditText.getText().toString().trim();
            String noteText = noteTextEditText.getText().toString().trim();
            if (validateInput(noteTitle, noteText)) {
                updateNote(noteTitle, noteText);
            } else {
                Toast.makeText(this, "Both title and text must be provided", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput(String title, String text) {
        return !title.isEmpty() && !text.isEmpty();
    }

    private void updateNote(String noteTitle, String text) {
        if (noteId != -1) {
            Notes updatedNote = new Notes(noteTitle, text, noteId);
            notesViewModel.update(updatedNote);

            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity
        } else {
            Toast.makeText(this, "Error: Note ID is invalid", Toast.LENGTH_SHORT).show();
        }
    }

}
