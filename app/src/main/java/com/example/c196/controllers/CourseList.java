package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.c196.R;

import java.util.ArrayList;

import View.CourseListAdapter;

import com.example.c196.entities.Courses;
import com.example.c196.viewmodel.CourseViewModel;

public class CourseList extends MenuActivity implements CourseListAdapter.OnCourseListener {

        private CourseListAdapter adapter;
        private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        //initialize recyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.courses_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseListAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        //initialize ViewModel
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        //observe LiveData from ViewModel
        courseViewModel.getAllCourses().observe(this, courses -> {
            //update the cached copy of the courses in the adapter.
            adapter.setCourses(courses);
        });
    }

    @Override
    public void onEditClicked(int position) {
        Courses course = adapter.getCourseAtPosition(position);
        Intent intent = new Intent(CourseList.this, EditCourse.class);
        intent.putExtra("courseId", course.getCourseID());
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(int position) {
        Courses course = adapter.getCourseAtPosition(position);
        courseViewModel.delete(course);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}

