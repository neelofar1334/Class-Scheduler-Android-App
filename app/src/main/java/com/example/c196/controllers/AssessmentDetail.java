package com.example.c196.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.c196.R;
import com.example.c196.entities.Assessments;
import com.example.c196.viewmodel.AssessmentViewModel;

public class AssessmentDetail extends MenuActivity {
    private TextView assessmentTitleLabel, assessmentType, assessmentStartDate, assessmentEndDate;
    private ImageView notifyStart, notifyEnd;
    private AssessmentViewModel assessmentViewModel;
    private int assessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        initializeViews();
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        //retrieve assessment ID from existing assessment
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        if (assessmentId != -1) {
            loadAssessmentData(assessmentId);
        } else {
            Toast.makeText(this, "Invalid Assessment ID", Toast.LENGTH_LONG).show();
            finish();
        }

        setupNotificationIcons();
    }

    private void initializeViews() {
        assessmentTitleLabel = findViewById(R.id.assessmentTitleLabel);
        assessmentType = findViewById(R.id.assessmentType);
        assessmentStartDate = findViewById(R.id.assessmentStartDate);
        assessmentEndDate = findViewById(R.id.assessmentEndDate);

        notifyStart = findViewById(R.id.notifyStart);
        notifyEnd = findViewById(R.id.notifyEnd);
    }

    private void loadAssessmentData(int id) {
        assessmentViewModel.getAssessmentById(id).observe(this, assessment -> {
            if (assessment != null) {
                assessmentTitleLabel.setText(assessment.getTitle());
                assessmentType.setText(assessment.getType());
                assessmentStartDate.setText(assessment.getStartDate());
                assessmentEndDate.setText(assessment.getEndDate());
            } else {
                Toast.makeText(AssessmentDetail.this, "Assessment not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupNotificationIcons() {
        notifyStart.setOnClickListener(v -> {
            // Set or remove a notification for the assessment start date
            toggleNotification(assessmentStartDate.getText().toString(), "Assessment Start");
        });

        notifyEnd.setOnClickListener(v -> {
            // Set or remove a notification for the assessment end date
            toggleNotification(assessmentEndDate.getText().toString(), "Assessment End");
        });
    }

    private void toggleNotification(String date, String event) {
        // This method would ideally check if a notification is already set, and either set or remove it
        Toast.makeText(this, "Toggle notification for: " + event + " on " + date, Toast.LENGTH_SHORT).show();
    }
}
