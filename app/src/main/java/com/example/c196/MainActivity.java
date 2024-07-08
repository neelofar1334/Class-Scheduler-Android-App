package com.example.c196;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.c196.controllers.AssessmentList;
import com.example.c196.controllers.CourseList;
import com.example.c196.controllers.GenerateSchedule;
import com.example.c196.controllers.MenuActivity;
import com.example.c196.controllers.TermList;
import com.example.c196.database.AppDatabase;
import com.example.c196.database.Repository;
import com.example.c196.Utility.PDFGenerator;

import java.util.List;

public class MainActivity extends MenuActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private Button coursesButton;
    private Button assessmentsButton;
    private Button termsButton;
    private Button scheduleButton;
    private GenerateSchedule generateSchedule;
    private CalendarView homeCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize database and repository
        AppDatabase db = AppDatabase.getDatabase(this);
        Repository repository = new Repository(getApplication());

        // Make calendar show current date
        homeCalendar = findViewById(R.id.homeCalendar);
        long currentTime = System.currentTimeMillis();
        homeCalendar.setDate(currentTime, false, true);

        // Initialize buttons
        coursesButton = findViewById(R.id.coursesButton);
        assessmentsButton = findViewById(R.id.assessmentsButton);
        termsButton = findViewById(R.id.termsButton);
        scheduleButton = findViewById(R.id.scheduleButton);

        // Set click listeners
        coursesButton.setOnClickListener(view -> navigateToActivity(CourseList.class));
        assessmentsButton.setOnClickListener(view -> navigateToActivity(AssessmentList.class));
        termsButton.setOnClickListener(view -> navigateToActivity(TermList.class));

        // Generate schedule button and handle permissions for it
        generateSchedule = new ViewModelProvider(this).get(GenerateSchedule.class);

        scheduleButton.setOnClickListener(v -> {
            generateSchedule.generateSchedule().observe(this, scheduleItems -> {
                PDFGenerator.generateSchedulePDF(this, scheduleItems);
                Toast.makeText(MainActivity.this, "Schedule generated", Toast.LENGTH_SHORT).show();
            });
        });
    }

    // Menu functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}
