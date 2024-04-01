package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.appcompat.widget.SearchView;

import com.example.c196.controllers.AddAssessment;
import com.example.c196.controllers.AddCourse;
import com.example.c196.controllers.AddTerm;
import com.example.c196.controllers.AssessmentList;
import com.example.c196.controllers.CourseList;
import com.example.c196.controllers.EditAssessment;
import com.example.c196.controllers.EditCourse;
import com.example.c196.controllers.EditTerm;
import com.example.c196.controllers.MenuActivity;
import com.example.c196.controllers.TermList;
import com.example.c196.database.DatabaseBuilder;
import com.example.c196.database.Repository;
import com.example.c196.entities.Notes;

public class MainActivity extends MenuActivity {

    private Button coursesButton;
    private Button assessmentsButton;
    private Button termsButton;
    private CalendarView homeCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize database and repository
        DatabaseBuilder db = DatabaseBuilder.getDatabase(this);
        Repository repository = new Repository(getApplication());

        //make calendar show current date
        homeCalendar = findViewById(R.id.homeCalendar);
        long currentTime = System.currentTimeMillis();
        homeCalendar.setDate(currentTime, false, true);

        //initialize buttons
        coursesButton = findViewById(R.id.coursesButton);
        assessmentsButton = findViewById(R.id.assessmentsButton);
        termsButton = findViewById(R.id.termsButton);

        //set click listeners
        coursesButton.setOnClickListener(view -> navigateToActivity(CourseList.class));
        assessmentsButton.setOnClickListener(view -> navigateToActivity(AssessmentList.class));
        termsButton.setOnClickListener(view -> navigateToActivity(TermList.class));
    }

    //Menu functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        return true;
    }

}