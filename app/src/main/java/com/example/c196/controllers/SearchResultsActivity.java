package com.example.c196.controllers;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.Utility.SearchResult;
import com.example.c196.Utility.SearchResultsAdapter;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchResultsAdapter adapter;
    private ArrayList<SearchResult> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent() != null && getIntent().hasExtra("searchResults")) {
            searchResults = (ArrayList<SearchResult>) getIntent().getSerializableExtra("searchResults");
        } else {
            searchResults = new ArrayList<>();
            Toast.makeText(this, "No search results found.", Toast.LENGTH_SHORT).show();
        }

        adapter = new SearchResultsAdapter(searchResults);
        recyclerView.setAdapter(adapter);
    }
}
