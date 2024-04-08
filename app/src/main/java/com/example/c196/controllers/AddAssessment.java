package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.R;
import com.example.c196.database.AppDatabase;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Terms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddAssessment extends MenuActivity {

    private Button startDatePickerButton, endDatePickerButton, submitButton, cancelButton;
    private EditText titleEditText;
    private Spinner typeSpinner;
    private String startDate, endDate, assessmentType;
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private Terms terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        //initialize UI components
        startDatePickerButton = findViewById(R.id.startDatePicker);
        endDatePickerButton = findViewById(R.id.endDatePicker);
        titleEditText = findViewById(R.id.title);
        typeSpinner = findViewById(R.id.typeSpinner);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        setupSpinner();
        setupDatePickerButtons();
        setupSubmitButton();
        setupCancelButton();

    }

       //spinner implementation
       private void setupSpinner() {
           ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                   R.array.assessment_types, android.R.layout.simple_spinner_item);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           typeSpinner.setAdapter(adapter);
           typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                   if (position > 0) { // Excluding the hint
                       assessmentType = parentView.getItemAtPosition(position).toString();
                   } else {
                       assessmentType = null;
                   }
               }

               @Override
               public void onNothingSelected(AdapterView<?> parentView) {
                   assessmentType = null;
               }
           });
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

    /**
     * Submit button function
     */
    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> saveAssessment());
    }

    /**
     * Cancel button function
     */
    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish()); //close the activity
    }

    /**
     * Save assessment function
     */
    private void saveAssessment() {
        String title = titleEditText.getText().toString().trim();
        if (title.isEmpty() || assessmentType == null || startDate == null || endDate == null) {
            Log.e("SaveAssessment", "Failed to save: One or more fields are empty.");
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        databaseExecutor.execute(() -> {
            Assessments assessment = new Assessments();
            assessment.setTitle(title);
            assessment.setType(assessmentType);
            assessment.setStartDate(startDate);
            assessment.setEndDate(endDate);

            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            AssessmentsDAO dao = db.assessmentDao();
            Log.d("SaveAssessment", "Attempting to save assessment: " + title);
            dao.insert(assessment);
            Log.d("SaveAssessment", "Assessment saved successfully.");

            runOnUiThread(() -> {
                Toast.makeText(AddAssessment.this, "Assessment saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

}
