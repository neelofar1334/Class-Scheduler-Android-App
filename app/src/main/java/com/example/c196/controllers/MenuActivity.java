package com.example.c196.controllers;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.c196.MainActivity;
import com.example.c196.R;
import com.example.c196.entities.Notes;

public class MenuActivity extends AppCompatActivity {
    // Common menu creation logic
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        // Configure SearchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        return true;
    }

    // Common menu item selection logic
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_home) {
            navigateToActivity(MainActivity.class);
        } else if (itemId == R.id.action_courseList) {
            navigateToActivity(CourseList.class);
        } else if (itemId == R.id.action_addCourse) {
            navigateToActivity(AddCourse.class);
        } else if (itemId == R.id.action_editCourse) {
            navigateToActivity(EditCourse.class);
        } else if (itemId == R.id.action_assessmentsList) {
            navigateToActivity(AssessmentList.class);
        } else if (itemId == R.id.action_addAssessment) {
            navigateToActivity(AddAssessment.class);
        } else if (itemId == R.id.action_editAssessment) {
            navigateToActivity(EditAssessment.class);
        } else if (itemId == R.id.action_termList) {
            navigateToActivity(TermList.class);
        } else if (itemId == R.id.action_addTerm) {
            navigateToActivity(AddTerm.class);
        } else if (itemId == R.id.action_editTerm) {
            navigateToActivity(EditTerm.class);
        } else if (itemId == R.id.action_notes) {
            navigateToActivity(Notes.class);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // Helper method for navigation
    protected void navigateToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
