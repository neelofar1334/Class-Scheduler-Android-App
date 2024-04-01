package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.database.Repository;

public class AddCourse extends MenuActivity {

    int courseID;
    int termID;
    String title;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    DatePicker datePicker;
    EditText editTitle;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        datePicker = findViewById(R.id.datePicker);
        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "Day " + datePicker.getDayOfMonth();
                String month = "Month " + datePicker.getMonth();
                String year = "Year " + datePicker.getYear();

                Toast.makeText(AddCourse.this, day + " " + month + " " + year, Toast.LENGTH_LONG).show();
            }
        });

    }
}