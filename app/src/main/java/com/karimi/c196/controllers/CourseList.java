package com.karimi.c196.controllers;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.karimi.c196.R;
import com.karimi.c196.View.CourseListAdapter;

import com.karimi.c196.entities.Courses;
import com.karimi.c196.viewmodel.CourseViewModel;

public class CourseList extends MenuActivity implements CourseListAdapter.OnCourseListener {

    private static final String TAG = "CourseList";
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

        new AlertDialog.Builder(this)
                .setTitle("Delete Course")
                .setMessage("Are you sure you want to delete this course?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    Log.d(TAG, "Deleting course: " + course.getCourseId());

                    //Ensure delete operation is performed on a background thread
                    new Thread(() -> {
                        courseViewModel.delete(course);
                        new Handler(Looper.getMainLooper()).post(() -> {
                            adapter.notifyItemRemoved(position);
                            Toast.makeText(this, "Course deleted", Toast.LENGTH_SHORT).show();
                        });
                    }).start();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
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
