package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.DAO.CoursesDAO;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.R;
import com.example.c196.database.AppDatabase;
import com.example.c196.database.Repository;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTerm extends MenuActivity {

    private Button startDatePickerButton, endDatePickerButton, submitButton, cancelButton;
    private EditText titleEditText;
    private String startDate, endDate;
    private Repository repository;
    private Terms terms;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        //initialize UI components
        startDatePickerButton = findViewById(R.id.startDatePicker);
        endDatePickerButton = findViewById(R.id.endDatePicker);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        titleEditText = findViewById(R.id.title);

        setupDatePickerButtons();
        setupSubmitButton();
        setupCancelButton();
        initializeRepository();
        loadData();
    }

    private void initializeRepository() {
        repository = new Repository(getApplication());
    }

    private void loadData() {
        termId = getIntent().getIntExtra("termId", -1);
        if (termId != -1) {
            repository.getTermById(termId).observe(this, terms -> {
                if (terms != null) {
                    this.terms = terms;
                    titleEditText.setText(terms.getTitle());
                    //dates
                    startDate = terms.getStartDate();
                    endDate = terms.getEndDate();
                    startDatePickerButton.setText("Start Date: " + startDate);
                    endDatePickerButton.setText("End Date: " + endDate);
                } else {
                    Toast.makeText(this, "Term not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Invalid Term ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //datePicker implementation
    private void setupDatePickerButtons() {
        startDatePickerButton.setOnClickListener(v -> showDatePickerDialog(true));
        endDatePickerButton.setOnClickListener(v -> showDatePickerDialog(false));
    }

    private void showDatePickerDialog(boolean isStart) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar date = Calendar.getInstance();
            date.set(year, month, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            if (isStart) {
                startDate = format.format(date.getTime());
                startDatePickerButton.setText(String.format(Locale.getDefault(), "Start Date: %s", startDate));
            } else {
                endDate = format.format(date.getTime());
                endDatePickerButton.setText(String.format(Locale.getDefault(), "End Date: %s", endDate));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //buttons
    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> saveTerm());
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveTerm() {
        if (terms != null) {
            terms.setTitle(titleEditText.getText().toString().trim());
            terms.setStartDate(startDate);
            terms.setEndDate(endDate);

            repository.update(terms);
            Toast.makeText(EditTerm.this, "Term updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Unable to save: No term data", Toast.LENGTH_SHORT).show();
        }
    }
}