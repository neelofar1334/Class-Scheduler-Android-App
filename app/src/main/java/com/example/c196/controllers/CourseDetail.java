package com.example.c196.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.c196.R;
import com.example.c196.entities.Courses;
import com.example.c196.viewmodel.AssessmentViewModel;
import com.example.c196.viewmodel.CourseViewModel;

import java.util.ArrayList;

import View.AssessmentListAdapter;
import View.CourseListAdapter;

public class CourseDetail extends MenuActivity {

    private TextView courseTitleLabel, courseStatus, courseStartDate, courseEndDate, instructorName, instructorEmail, instructorPhone;
    private RecyclerView assessmentsListRecyclerView;
    private Button addButton, shareButton, addAssessmentButton;
    private CourseViewModel courseViewModel;
    private int courseId;
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        initializeViews();
        courseId = getIntent().getIntExtra("courseId", -1);
        setupViewModels();
        if (courseId != -1) {
            setupRecyclerView();
            loadCourseData();
            setupButtonListeners();
        }
    }

    private void initializeViews() {
        courseTitleLabel = findViewById(R.id.courseTitleLabel);
        courseStatus = findViewById(R.id.courseStatus);
        courseStartDate = findViewById(R.id.courseStartDate);
        courseEndDate = findViewById(R.id.courseEndDate);
        instructorName = findViewById(R.id.instructorName);
        instructorEmail = findViewById(R.id.instructorEmail);
        instructorPhone = findViewById(R.id.instructorPhone);
        addAssessmentButton = findViewById(R.id.addAssessmentButton);
        assessmentsListRecyclerView = findViewById(R.id.assessmentsList_recycler_view);
        addButton = findViewById(R.id.addButton);
        shareButton = findViewById(R.id.shareButton);
    }

    private void setupViewModels() {
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
    }

    private void setupRecyclerView() {
        assessmentsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize the adapter with an empty list inside its constructor
        AssessmentListAdapter adapter = new AssessmentListAdapter(this, null, false); //false to hide buttons from recyclerView row

        assessmentsListRecyclerView.setAdapter(adapter);

        // Observe the LiveData from ViewModel and update adapter
        assessmentViewModel.getAssessmentsBycourseId(courseId).observe(this, assessments -> {
            if (assessments != null) {
                adapter.setAssessments(assessments);
            } else {
                Log.d("CourseDetail", "No assessments available for this course");
                Toast.makeText(this, "No assessments available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCourseData() {
        courseId = getIntent().getIntExtra("courseId", -1);
        if (courseId != -1) {
            courseViewModel.getCourseById(courseId).observe(this, this::populateCourseDetails);
        }
    }

    private void populateCourseDetails(Courses courses) {
        courseTitleLabel.setText(courses.getTitle());
        courseStatus.setText(courses.getStatus());
        courseStartDate.setText(courses.getStartDate());
        courseEndDate.setText(courses.getEndDate());
        instructorName.setText(courses.getInstructorName());
        instructorEmail.setText(courses.getInstructorEmail());
        instructorPhone.setText(courses.getInstructorPhone());
    }

    //notes buttons
    private void setupButtonListeners() {
        addButton.setOnClickListener(v -> onAddClicked());
        addAssessmentButton.setOnClickListener(v -> onAddAssessmentClicked());
        shareButton.setOnClickListener(v -> onShareClicked());
    }

    public void onAddAssessmentClicked() {
        Intent intent = new Intent(CourseDetail.this, AddAssessment.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void onAddClicked() {
        Intent intent = new Intent(CourseDetail.this, AddNotes.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void onShareClicked() {
//TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}
