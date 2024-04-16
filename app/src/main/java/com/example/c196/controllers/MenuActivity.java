package com.example.c196.controllers;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.c196.MainActivity;
import com.example.c196.R;
import com.example.c196.entities.Notes;

public class MenuActivity extends AppCompatActivity {

    //For the back button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        //search function for action bar
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        //handle the up button
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
