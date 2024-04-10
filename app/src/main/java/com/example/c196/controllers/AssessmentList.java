package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

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
        adapter = new AssessmentListAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        //initialize ViewModel
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        //observe LiveData from ViewModel
        assessmentViewModel.getAllAssessments().observe(this, assessments -> {
            //update the cached copy of the assessments in the adapter.
            adapter.setAssessments(assessments);
        });
    }

    @Override
    public void onEditClicked(int position) {
        Assessments assessments = adapter.getAssessmentAtPosition(position);
        Intent intent = new Intent(AssessmentList.this, EditAssessment.class);
        intent.putExtra("assessmentId", assessments.getAssessmentId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(int position) {
        Assessments assessments = adapter.getAssessmentAtPosition(position);
        assessmentViewModel.delete(assessments);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}