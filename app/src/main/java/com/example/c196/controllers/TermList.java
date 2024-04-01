package com.example.c196.controllers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import com.example.c196.R;
import com.example.c196.entities.Terms;
import com.example.c196.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

import View.TermListAdapter;

public class TermList extends MenuActivity {

    private TermViewModel termViewModel;
    private TermListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        // Setup RecyclerView and Adapter
        RecyclerView recyclerView = findViewById(R.id.terms_recycler_view);
        adapter = new TermListAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Initialize TermViewModel
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        // Observe LiveData from ViewModel
        termViewModel.getAllTerms().observe(this, terms -> {
            // Update the cached copy of the terms in the adapter.
            adapter.setTerms(terms);
        });
    }

    // Menu setup if needed
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}
