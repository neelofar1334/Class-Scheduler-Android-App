package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.DAO.CoursesDAO;
import com.example.c196.R;
import com.example.c196.database.AppDatabase;
import com.example.c196.entities.Courses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditCourse extends MenuActivity {

    private Button startDatePickerButton, endDatePickerButton, submitButton, cancelButton;
    private EditText titleEditText, statusEditText, instructorNameEditText, instructorEmailEditText, instructorPhoneEditText;
    private String startDate, endDate;
    private Courses course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

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

        //load data from existing course
        int courseId = getIntent().getIntExtra("courseId", -1);
        if (courseId != -1) {
            loadCourseData(courseId);
        } else {
            Toast.makeText(this, "Invalid Course ID", Toast.LENGTH_SHORT).show();
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
        submitButton.setOnClickListener(v -> saveCourse());
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadCourseData(int courseId) {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        CoursesDAO coursesDAO = db.coursesDao();
        coursesDAO.getCourseById(courseId).observe(this, courses -> {
            if (course != null) {
                titleEditText.setText(course.getTitle());
                statusEditText.setText(course.getStatus());
                instructorNameEditText.setText(course.getInstructorName());
                instructorEmailEditText.setText(course.getInstructorEmail());
                instructorPhoneEditText.setText(course.getInstructorPhone());
            }
        });
    }

    private void saveCourse() {
        String title = titleEditText.getText().toString().trim();
        String status = statusEditText.getText().toString().trim();
        String instructorName = instructorNameEditText.getText().toString().trim();
        String instructorEmail = instructorEmailEditText.getText().toString().trim();
        String instructorPhone = instructorPhoneEditText.getText().toString().trim();

        if (title.isEmpty() || status.isEmpty() || instructorName.isEmpty() || instructorEmail.isEmpty() || instructorPhone.isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        course.setTitle(title);
        course.setStatus(status);
        course.setInstructorName(instructorName);
        course.setInstructorEmail(instructorEmail);
        course.setInstructorPhone(instructorPhone);

        new Thread(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            CoursesDAO dao = db.coursesDao();
            dao.update(course);
            runOnUiThread(() -> {
                Toast.makeText(EditCourse.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}