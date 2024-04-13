package com.example.c196.controllers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.DAO.CoursesDAO;
import com.example.c196.R;
import com.example.c196.database.AppDatabase;
import com.example.c196.database.Repository;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCourse extends MenuActivity {
    private Button startDatePickerButton, endDatePickerButton, submitButton, cancelButton;
    private EditText titleEditText, statusEditText, instructorNameEditText, instructorEmailEditText, instructorPhoneEditText;
    private String startDate, endDate;
    private Repository repository;
    private int termId; //for term+course associations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //retrieve termId passed from TermDetail
        termId = getIntent().getIntExtra("termId", -1);

        //initialize the repository
        repository = new Repository(getApplication());

        //initialize UI components
        startDatePickerButton = findViewById(R.id.startDatePicker);
        endDatePickerButton = findViewById(R.id.endDatePicker);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        titleEditText = findViewById(R.id.title);
        statusEditText = findViewById(R.id.status);
        instructorNameEditText = findViewById(R.id.InstructorName);
        instructorEmailEditText = findViewById(R.id.InstructorEmail);
        instructorPhoneEditText = findViewById(R.id.InstructorPhone);

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
        submitButton.setOnClickListener(v -> saveCourse());
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveCourse() {

        String title = titleEditText.getText().toString().trim();
        String status = statusEditText.getText().toString().trim();
        String instructorName = instructorNameEditText.getText().toString().trim();
        String instructorEmail = instructorEmailEditText.getText().toString().trim();
        String instructorPhone = instructorPhoneEditText.getText().toString().trim();

        //validation
        if (title.isEmpty() || status.isEmpty() || instructorName.isEmpty() || instructorEmail.isEmpty() || instructorPhone.isEmpty() || startDate == null || endDate == null) {
            Log.e("SaveCourse", "Failed to save: One or more fields are empty.");
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            Courses course = new Courses(title, startDate, endDate, status, instructorName, instructorEmail, instructorPhone, termId);
            repository.insert(course);
            Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show();
            finish();;
        }
    }
}
