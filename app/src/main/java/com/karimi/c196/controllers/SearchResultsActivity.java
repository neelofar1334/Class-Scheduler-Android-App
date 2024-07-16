package com.karimi.c196.controllers;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karimi.c196.R;
import com.karimi.c196.Utility.SearchResult;
import com.karimi.c196.Utility.SearchResultsAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class SearchResultsActivity extends MenuActivity {

    private RecyclerView recyclerView;
    private SearchResultsAdapter adapter;
    private ArrayList<SearchResult> searchResults;
    private TextView noResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Enable the back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        noResultsTextView = findViewById(R.id.no_results_text);

        // RecyclerView setup
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
