package com.example.c196.controllers;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.example.c196.R;
import com.example.c196.entities.Assessments;
import com.example.c196.viewmodel.AssessmentViewModel;

import java.util.ArrayList;
import View.AssessmentListAdapter;

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
            new AlertDialog.Builder(this)
                    .setTitle("Delete Assessment")
                    .setMessage("Are you sure you want to delete this assessment?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        assessmentViewModel.delete(assessments);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(this, "Assessment deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        } else {
            Log.e("AssessmentList", "Adapter is not initialized.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}
