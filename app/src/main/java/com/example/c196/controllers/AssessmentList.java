package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;
import com.example.c196.viewmodel.AssessmentViewModel;
import com.example.c196.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

import View.AssessmentListAdapter;
import View.CourseListAdapter;

public class AssessmentList extends MenuActivity implements AssessmentListAdapter.onAssessmentListener {

    private AssessmentListAdapter adapter;
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        //initialize recyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.assessments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AssessmentListAdapter(this, new ArrayList<>(), this, true);
        recyclerView.setAdapter(adapter);

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this, assessments -> {
            if (assessments != null) {
                adapter.setAssessments(assessments);
            } else {
                Toast.makeText(this, "No assessments available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewClicked(int position) {
        if (adapter != null) {
            Assessments assessment = adapter.getAssessmentAtPosition(position);
            Intent intent = new Intent(AssessmentList.this, AssessmentDetail.class);
            intent.putExtra("assessmentId", assessment.getAssessmentId());
            startActivity(intent);
            Log.d("AssessmentList", "Viewing assessment: " + assessment.getAssessmentId());
        } else {
            Log.e("AssessmentList", "Adapter is not initialized.");
        }
    }

    @Override
    public void onEditClicked(int position) {
        if (adapter != null) {
            Assessments assessments = adapter.getAssessmentAtPosition(position);
            Intent intent = new Intent(AssessmentList.this, EditAssessment.class);
            intent.putExtra("assessmentId", assessments.getAssessmentId());
            startActivity(intent);
        } else {
            Log.e("AssessmentList", "Adapter is not initialized.");
        }
    }

    @Override
    public void onDeleteClicked(int position) {
        if (adapter != null) {
            Assessments assessments = adapter.getAssessmentAtPosition(position);
            assessmentViewModel.delete(assessments);
            adapter.notifyItemRemoved(position);
        } else {
            Log.e("AssessmentList", "Adapter is not initialized.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}