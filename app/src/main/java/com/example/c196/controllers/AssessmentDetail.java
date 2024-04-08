package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196.R;

public class AssessmentDetail extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
    }

    /*
    //TODO pass info from existing assessment for edit button
    Intent intent = new Intent(AssessmentDetail.this, EditAssessment.class);
intent.putExtra("ASSESSMENT_ID", assessmentId); // 'assessmentId' is the ID of the assessment to edit
    startActivity(intent);

     */

}