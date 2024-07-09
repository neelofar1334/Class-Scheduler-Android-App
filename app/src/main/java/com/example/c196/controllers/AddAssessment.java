package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.c196.R;
import com.example.c196.entities.Assessments;
import com.example.c196.viewmodel.AssessmentViewModel;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196.database.Repository;

import java.text.ParseException;
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
    private Repository repository;
    private int courseId;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        //Retrieve courseId passed from the previous activity
        courseId = getIntent().getIntExtra("courseId", -1);

        //Initialize the repository
        repository = new Repository(getApplication());

        //Initialize UI components
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

    //Spinner implementation
    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.assessment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
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

    //DatePicker implementation
    private void setupDatePickerButtons() {
        startDatePickerButton.setOnClickListener(v -> showDatePickerDialog(true));
        endDatePickerButton.setOnClickListener(v -> showDatePickerDialog(false));
    }

    private void showDatePickerDialog(boolean isStart) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar date = Calendar.getInstance();
            date.set(year, month, dayOfMonth);
            String formattedDate = DATE_FORMAT.format(date.getTime());
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
        submitButton.setOnClickListener(v -> saveAssessment());
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish()); //Close the activity
    }

    private void saveAssessment() {
        String title = titleEditText.getText().toString().trim();
        if (title.isEmpty() || assessmentType == null || startDate == null || endDate == null) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //Date Validation
        if (!validateDates(startDate, endDate)) {
            Toast.makeText(this, "Invalid date range. End date must be after start date.", Toast.LENGTH_SHORT).show();
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean courseExists = repository.courseExists(courseId); //Course validation
            if (courseExists) {
                Assessments assessment = new Assessments(courseId, title, assessmentType, startDate, endDate);
                repository.insert(assessment);
                runOnUiThread(() -> {
                    Toast.makeText(AddAssessment.this, "Assessment saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(AddAssessment.this, "Invalid course ID", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private boolean validateDates(String startDate, String endDate) {
        try {
            return DATE_FORMAT.parse(startDate).before(DATE_FORMAT.parse(endDate));
        } catch (ParseException e) {
            return false;
        }
    }
}
