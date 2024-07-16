package com.karimi.c196.controllers;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karimi.c196.R;
import com.karimi.c196.entities.Terms;
import com.karimi.c196.viewmodel.CourseViewModel;
import com.karimi.c196.viewmodel.TermViewModel;

import com.karimi.c196.View.CourseListAdapter;

public class TermDetail extends MenuActivity {

    private TextView termTitleLabel, termStartDate, termEndDate;
    private RecyclerView courseListRecyclerView;
    private Button addCourseButton;
    private CourseViewModel courseViewModel;
    private int termId;
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        initializeViews();
        termId = getIntent().getIntExtra("termId", -1);
        setupViewModels();
        if (termId != -1) {
            setupRecyclerView();
            loadTermData();
            setupButtonListeners();
        }
    }

    private void initializeViews() {
        termTitleLabel = findViewById(R.id.termTitleLabel);
        termStartDate = findViewById(R.id.termStartDate);
        termEndDate = findViewById(R.id.termEndDate);
        addCourseButton = findViewById(R.id.addCourseButton);
        courseListRecyclerView = findViewById(R.id.courseList_recycler_view);
    }

    private void setupViewModels() {
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
    }

    private void setupRecyclerView() {
        courseListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize the adapter with an empty list inside its constructor
        CourseListAdapter adapter = new CourseListAdapter(this, null, false); //false to hide buttons from recyclerView row

        courseListRecyclerView.setAdapter(adapter);

        //observe LiveData from ViewModel and update adapter
        courseViewModel.getCoursesByTermId(termId).observe(this, courses -> {
            if (courses != null) {
                adapter.setCourses(courses);
            } else {
                Log.d("TermDetail", "No courses available for this term");
                Toast.makeText(this, "No courses available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTermData() {
        termId = getIntent().getIntExtra("termId", -1);
        if (termId != -1) {
            termViewModel.getTermById(termId).observe(this, this::populateTermDetails);
        }
    }

    private void populateTermDetails(Terms terms) {
        termTitleLabel.setText(terms.getTitle());
        termStartDate.setText(terms.getStartDate());
        termEndDate.setText(terms.getEndDate());
    }


    private void setupButtonListeners() {
        addCourseButton.setOnClickListener(v -> onAddClicked());
    }

    public void onAddClicked() {
        Intent intent = new Intent(TermDetail.this, AddCourse.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}