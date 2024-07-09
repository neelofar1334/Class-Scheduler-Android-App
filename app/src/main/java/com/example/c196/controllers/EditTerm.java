package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.database.Repository;
import com.example.c196.entities.Terms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    //buttons
    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> saveTerm());
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveTerm() {
        if (terms != null) {
            String title = titleEditText.getText().toString().trim();
            if (!validateInputs(title, startDate, endDate)) {
                return;
            }

            terms.setTitle(title);
            terms.setStartDate(startDate);
            terms.setEndDate(endDate);

            repository.update(terms);
            Toast.makeText(EditTerm.this, "Term updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Unable to save: No term data", Toast.LENGTH_SHORT).show();
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
