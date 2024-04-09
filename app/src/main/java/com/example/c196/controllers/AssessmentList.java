package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import com.example.c196.R;
import com.example.c196.entities.Assessments;
import com.example.c196.viewmodel.AssessmentViewModel;
import com.example.c196.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

import View.AssessmentListAdapter;
import View.CourseListAdapter;

public class AssessmentList extends MenuActivity {

    private AssessmentListAdapter adapter;
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        RecyclerView recyclerView = findViewById(R.id.assessments_recycler_view);
        final AssessmentListAdapter adapter = new AssessmentListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize ViewModel
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        //update the UI
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<Assessments>>() {
            @Override
            public void onChanged(List<Assessments> assessments) {
                adapter.setAssessments(assessments);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}