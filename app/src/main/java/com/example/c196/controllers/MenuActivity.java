package com.example.c196.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.c196.MainActivity;
import com.example.c196.R;
import com.example.c196.Utility.SearchResult;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Terms;
import com.example.c196.viewmodel.CourseViewModel;
import com.example.c196.viewmodel.AssessmentViewModel;
import com.example.c196.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewModel;
    private TermViewModel termViewModel;
    private List<Courses> allCourses = new ArrayList<>();
    private List<Assessments> allAssessments = new ArrayList<>();
    private List<Terms> allTerms = new ArrayList<>();
    private List<SearchResult> searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Initialize ViewModels
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        // Observe data
        courseViewModel.getAllCourses().observe(this, courses -> {
            allCourses.clear();
            allCourses.addAll(courses);
            Log.d("MenuActivity", "Courses observed: " + allCourses.size());
        });

        assessmentViewModel.getAllAssessments().observe(this, assessments -> {
            allAssessments.clear();
            allAssessments.addAll(assessments);
            Log.d("MenuActivity", "Assessments observed: " + allAssessments.size());
        });

        termViewModel.getAllTerms().observe(this, terms -> {
            allTerms.clear();
            allTerms.addAll(terms);
            Log.d("MenuActivity", "Terms observed: " + allTerms.size());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        // Search function for action bar
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Search", "Query submitted: " + query);
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Search", "Query text changed: " + newText);
                // Perform live search here if needed
                return false;
            }
        });

        return true;
    }

    private void performSearch(String query) {
        Log.d("Search", "Performing search for: " + query);
        searchResults.clear();
        String lowerCaseQuery = query.toLowerCase();

        for (Courses course : allCourses) {
            if (course.getTitle().toLowerCase().contains(lowerCaseQuery)) {
                searchResults.add(new SearchResult("Course", course.getTitle(), course.getCourseId()));
                Log.d("MenuActivity", "Course matched: " + course.getTitle());
            }
        }

        for (Assessments assessment : allAssessments) {
            if (assessment.getTitle().toLowerCase().contains(lowerCaseQuery)) {
                searchResults.add(new SearchResult("Assessment", assessment.getTitle(), assessment.getAssessmentId()));
                Log.d("MenuActivity", "Assessment matched: " + assessment.getTitle());
            }
        }

        for (Terms term : allTerms) {
            if (term.getTitle().toLowerCase().contains(lowerCaseQuery)) {
                searchResults.add(new SearchResult("Term", term.getTitle(), term.getTermID()));
                Log.d("MenuActivity", "Term matched: " + term.getTitle());
            }
        }

        Log.d("MenuActivity", "Total search results: " + searchResults.size());

        //Pass searchResults to the SearchResultsActivity
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchResults", (ArrayList<SearchResult>) searchResults);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // Handle the up button
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (itemId == R.id.action_home) {
            navigateToActivity(MainActivity.class);
        } else if (itemId == R.id.action_courseList) {
            navigateToActivity(CourseList.class);
        }  else if (itemId == R.id.action_assessmentsList) {
            navigateToActivity(AssessmentList.class);
        }  else if (itemId == R.id.action_termList) {
            navigateToActivity(TermList.class);
        } else if (itemId == R.id.action_addTerm) {
            navigateToActivity(AddTerm.class);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected void navigateToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
