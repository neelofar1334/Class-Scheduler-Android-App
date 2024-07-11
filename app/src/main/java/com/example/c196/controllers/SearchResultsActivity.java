package com.example.c196.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.Utility.SearchResult;
import com.example.c196.Utility.SearchResultsAdapter;

import java.util.ArrayList;

public class SearchResultsActivity extends MenuActivity implements SearchResultsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private SearchResultsAdapter adapter;
    private ArrayList<SearchResult> searchResults;
    private TextView noResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        noResultsTextView = findViewById(R.id.no_results_text);

        //RecyclerView setup
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent() != null && getIntent().hasExtra("searchResults")) {
            searchResults = (ArrayList<SearchResult>) getIntent().getSerializableExtra("searchResults");
        } else {
            searchResults = new ArrayList<>();
            Toast.makeText(this, "No search results found.", Toast.LENGTH_SHORT).show();
        }

        adapter = new SearchResultsAdapter(searchResults, this);
        recyclerView.setAdapter(adapter);

        // Show or hide the no results text view
        if (searchResults.isEmpty()) {
            noResultsTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noResultsTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(SearchResult searchResult) {
        // Handle navigation based on the search result type
        Intent intent;
        switch (searchResult.getType()) {
            case "Course":
                intent = new Intent(this, CourseDetail.class);
                intent.putExtra("courseId", searchResult.getId());
                break;
            case "Assessment":
                intent = new Intent(this, AssessmentDetail.class);
                intent.putExtra("assessmentId", searchResult.getId());
                break;
            case "Term":
                intent = new Intent(this, TermDetail.class);
                intent.putExtra("termId", searchResult.getId());
                break;
            default:
                Toast.makeText(this, "Unknown type: " + searchResult.getType(), Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
