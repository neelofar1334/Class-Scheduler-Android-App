package com.karimi.c196.controllers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.karimi.c196.R;
import com.karimi.c196.database.Repository;
import com.karimi.c196.entities.Assessments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditAssessment extends MenuActivity {

    private Button startDatePickerButton, endDatePickerButton, submitButton, cancelButton;
    private String startDate, endDate;
    private EditText titleEditText;
    private Spinner typeSpinner;
    private Assessments assessment;
    private Repository repository;
    private int assessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        //initialize UI components
        startDatePickerButton = findViewById(R.id.startDatePicker);
        endDatePickerButton = findViewById(R.id.endDatePicker);
        typeSpinner = findViewById(R.id.typeSpinner);
        titleEditText = findViewById(R.id.title);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        setupSpinner();
        setupDatePickerButtons();
        setupSubmitButton();
        setupCancelButton();
        initializeRepository();
        loadData();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    private void initializeRepository() {
        repository = new Repository(getApplication());
    }

    private void loadData() {
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        if (assessmentId != -1) {
            repository.getAssessmentById(assessmentId).observe(this, assessment -> {
                if (assessment != null) {
                    this.assessment = assessment;
                    titleEditText.setText(assessment.getTitle());
                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) typeSpinner.getAdapter();
                    int spinnerPosition = adapter.getPosition(assessment.getType());
                    typeSpinner.setSelection(spinnerPosition);
                    //dates
                    startDate = assessment.getStartDate();
                    endDate = assessment.getEndDate();
                    startDatePickerButton.setText("Start Date: " + startDate);
                    endDatePickerButton.setText("End Date: " + endDate);
                } else {
                    Toast.makeText(this, "Assessment not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Invalid Assessment ID", Toast.LENGTH_SHORT).show();
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


    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> saveAssessment());
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveAssessment() {
        if (assessment != null) {
            String title = titleEditText.getText().toString().trim();
            String type = typeSpinner.getSelectedItem().toString();

            if (!validateInputs(title, startDate, endDate)) {
                return;
            }

            assessment.setTitle(title);
            assessment.setType(type);
            assessment.setStartDate(startDate);
            assessment.setEndDate(endDate);

            repository.update(assessment);
            Toast.makeText(EditAssessment.this, "Assessment updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Unable to save: No assessment data", Toast.LENGTH_SHORT).show();
        }
    }

    //Validation
    private boolean validateInputs(String title, String startDate, String endDate) {
        if (title.isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
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
