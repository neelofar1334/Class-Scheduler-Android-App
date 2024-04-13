package com.example.c196.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

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

        RecyclerView recyclerView = findViewById(R.id.courses_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CourseListAdapter(this, this, true); //true to show edit, view, delete buttons
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, courses -> {
            if (courses != null) {
                adapter.setCourses(courses);
            } else {
                Toast.makeText(this, "No courses available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEditClicked(int position) {
        Courses course = adapter.getCourseAtPosition(position);
        Intent intent = new Intent(this, EditCourse.class);
        intent.putExtra("courseId", course.getCourseId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(int position) {
        Courses course = adapter.getCourseAtPosition(position);
        courseViewModel.delete(course);
        Toast.makeText(this, "Course deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewClicked(int position) {
        Courses course = adapter.getCourseAtPosition(position);
        Intent intent = new Intent(this, CourseDetail.class);
        intent.putExtra("courseId", course.getCourseId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}

