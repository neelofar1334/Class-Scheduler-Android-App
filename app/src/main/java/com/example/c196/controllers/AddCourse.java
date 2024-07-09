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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCourse extends MenuActivity {
    private Button startDatePickerButton, endDatePickerButton, submitButton, cancelButton;
    private EditText titleEditText, instructorNameEditText, instructorEmailEditText, instructorPhoneEditText;
    private String startDate, endDate, courseStatus;
    private Spinner status;
    private Repository repository;
    private int termId; //for term+course associations
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

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
        status = findViewById(R.id.status);
        instructorNameEditText = findViewById(R.id.InstructorName);
        instructorEmailEditText = findViewById(R.id.InstructorEmail);
        instructorPhoneEditText = findViewById(R.id.InstructorPhone);

        setupSpinner();
        setupDatePickerButtons();
        setupSubmitButton();
        setupCancelButton();
    }

    //spinner implementation
    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.course_status_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    courseStatus = parentView.getItemAtPosition(position).toString();
                } else {
                    courseStatus = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                courseStatus = null;
            }
        });
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
        submitButton.setOnClickListener(v -> saveCourse());
    }

    private void setupCancelButton() {
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveCourse() {

        String title = titleEditText.getText().toString().trim();
        String instructorName = instructorNameEditText.getText().toString().trim();
        String instructorEmail = instructorEmailEditText.getText().toString().trim();
        String instructorPhone = instructorPhoneEditText.getText().toString().trim();

        //validation
        if (title.isEmpty() || courseStatus == null || instructorName.isEmpty() || instructorEmail.isEmpty() || instructorPhone.isEmpty() || startDate == null || endDate == null) {
            Log.e("SaveCourse", "Failed to save: One or more fields are empty.");
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(instructorEmail)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPhone(instructorPhone)) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateDates(startDate, endDate)) {
            Toast.makeText(this, "Invalid date range. End date must be after start date.", Toast.LENGTH_SHORT).show();
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean termExists = repository.termExists(termId); //term validation
            if (termExists) {
                Courses course = new Courses(title, startDate, endDate, courseStatus, instructorName, instructorEmail, instructorPhone, termId);
                repository.insert(course);
                runOnUiThread(() -> {
                    Toast.makeText(AddCourse.this, "Course added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(AddCourse.this, "Invalid term ID", Toast.LENGTH_SHORT).show());
            }
        });
    }

    //Validation
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean validateDates(String startDate, String endDate) {
        try {
            return DATE_FORMAT.parse(startDate).before(DATE_FORMAT.parse(endDate));
        } catch (ParseException e) {
            return false;
        }
    }
}
