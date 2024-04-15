package com.example.c196.controllers;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import com.example.c196.R;
import com.example.c196.viewmodel.AssessmentViewModel;
import com.example.c196.Utility.AlarmHelper;

public class AssessmentDetail extends MenuActivity {
    private TextView assessmentTitleLabel, assessmentType, assessmentStartDate, assessmentEndDate;
    private ImageView notifyStart, notifyEnd;
    private AssessmentViewModel assessmentViewModel;
    private int assessmentId;
    private String assessmentTitle; //for notification details

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
                assessmentTitle = assessment.getTitle(); //for notification details
                assessmentTitleLabel.setText(assessment.getTitle());
                assessmentType.setText(assessment.getType());
                assessmentStartDate.setText(assessment.getStartDate());
                assessmentEndDate.setText(assessment.getEndDate());

                setupNotificationIcons(); //notification details
            } else {
                Toast.makeText(AssessmentDetail.this, "Assessment not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupNotificationIcons() {
        notifyStart.setOnClickListener(v -> {
            String startDate = assessmentStartDate.getText().toString();
            String startNotificationTitle = "Start of Assessment";
            String startNotificationText = assessmentTitle + " starts on " + startDate;
            AlarmHelper.setNotification(this, startDate, startNotificationTitle, startNotificationText, assessmentId);
        });

        notifyEnd.setOnClickListener(v -> {
            String endDate = assessmentEndDate.getText().toString();
            String endNotificationTitle = "End of Assessment";
            String endNotificationText = assessmentTitle + " ends on " + endDate;
            AlarmHelper.setNotification(this, endDate, endNotificationTitle, endNotificationText, assessmentId + 1);
        });
    }
}
