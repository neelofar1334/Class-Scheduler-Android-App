package com.karimi.c196;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import com.karimi.c196.Utility.PDFGenerator;
import com.karimi.c196.controllers.AssessmentList;
import com.karimi.c196.controllers.CourseList;
import com.karimi.c196.controllers.GenerateSchedule;
import com.karimi.c196.controllers.MenuActivity;
import com.karimi.c196.controllers.TermList;
import com.karimi.c196.database.AppDatabase;
import com.karimi.c196.database.Repository;

public class MainActivity extends MenuActivity {

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
}
