package com.karimi.c196.controllers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karimi.c196.R;
import com.karimi.c196.database.Repository;
import com.karimi.c196.entities.Terms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTerm extends MenuActivity {

    private Button startDatePickerButton, endDatePickerButton, submitButton, cancelButton;
    private EditText titleEditText;
    private String startDate, endDate;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        //initialize the repository
        repository = new Repository(getApplication());

        //initialize UI components
        startDatePickerButton = findViewById(R.id.startDatePicker);
        endDatePickerButton = findViewById(R.id.endDatePicker);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        titleEditText = findViewById(R.id.title);

        setupDatePickerButtons();
        setupSubmitButton();
        setupCancelButton();
    }

    //implement datePicker
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
            String formattedDate = format.format(date.getTime());
            if (isStart) {
                startDate = formattedDate;
                startDatePickerButton.setText(String.format(Locale.getDefault(), "Start Date: %s", startDate));
            } else {
                endDate = formattedDate;
                endDatePickerButton.setText(String.format(Locale.getDefault(), "End Date: %s", endDate));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> saveTerm());
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveTerm() {
        String title = titleEditText.getText().toString().trim();

        if (validateInputs(title, startDate, endDate)) {
            //create a new Term object
            Terms term = new Terms();
            term.setTitle(title);
            term.setStartDate(startDate);
            term.setEndDate(endDate);

            //use the repository to insert the new term
            repository.insert(term);

            Toast.makeText(this, "Term added successfully", Toast.LENGTH_SHORT).show();

            // Redirect to TermList activity
            Intent intent = new Intent(AddTerm.this, TermList.class);
            startActivity(intent);
            finish();
        }
    }

    //Validation
    private boolean validateInputs(String title, String startDate, String endDate) {
        if (title.isEmpty() || startDate == null || endDate == null) {
            Log.e("SaveTerm", "Failed to save: One or more fields are empty.");
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validateDates(startDate, endDate)) {
            Log.e("SaveTerm", "Failed to save: End date is before start date.");
            Toast.makeText(this, "End date must be after start date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateDates(String startDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date start = format.parse(startDate);
            Date end = format.parse(endDate);
            return start != null && end != null && end.after(start);
        } catch (ParseException e) {
            Log.e("ValidateDates", "Date parsing error", e);
            return false;
        }
    }
}
