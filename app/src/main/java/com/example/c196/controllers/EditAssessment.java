package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.R;
import com.example.c196.database.AppDatabase;
import com.example.c196.entities.Assessments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditAssessment extends MenuActivity {

    private Button startDatePickerButton, endDatePickerButton, submitButton, cancelButton;
    private String startDate, endDate;
    private EditText titleEditText;
    private Spinner typeSpinner;
    private Assessments assessment;

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

        //load data from existing assessment
        int assessmentId = getIntent().getIntExtra("assessmentId", -1);
        if (assessmentId != -1) {
            loadAssessmentData(assessmentId);
        } else {
            Toast.makeText(this, "Invalid Assessment ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
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

    private void loadAssessmentData(int assessmentId) {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        AssessmentsDAO assessmentsDAO = db.assessmentDao();
        assessmentsDAO.getAssessmentByID(assessmentId).observe(this, assessment -> {
            if (assessment != null) {
                titleEditText.setText(assessment.getTitle());

                // Safer casting approach
                SpinnerAdapter adapter = typeSpinner.getAdapter();
                if (adapter instanceof ArrayAdapter) {
                    ArrayAdapter<String> stringArrayAdapter = (ArrayAdapter<String>) adapter;
                    int spinnerPosition = stringArrayAdapter.getPosition(assessment.getType());
                    typeSpinner.setSelection(spinnerPosition);
                }

                startDatePickerButton.setText("Start Date: " + assessment.getStartDate());
                endDatePickerButton.setText("End Date: " + assessment.getEndDate());
            }
        });
    }


    private void saveAssessment() {
        String title = titleEditText.getText().toString().trim();
        String type = typeSpinner.getSelectedItem().toString();
        if (title.isEmpty() || type.equals(getResources().getStringArray(R.array.assessment_types)[0])) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        assessment.setTitle(title);
        assessment.setType(type);

        new Thread(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            db.assessmentDao().update(assessment);
            runOnUiThread(() -> {
                Toast.makeText(EditAssessment.this, "Assessment updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }


}