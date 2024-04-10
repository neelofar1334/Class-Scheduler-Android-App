package com.example.c196.controllers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.c196.R;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;
import com.example.c196.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

import View.CourseListAdapter;
import View.TermListAdapter;

public class TermList extends MenuActivity implements TermListAdapter.OnTermListener {

    private TermViewModel termViewModel;
    private TermListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        //initialize recyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.terms_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TermListAdapter(this, new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        //initialize TermViewModel
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        //observe LiveData from ViewModel
        termViewModel.getAllTerms().observe(this, terms -> {
            //update the cached copy of the terms in the adapter.
            adapter.setTerms(terms);
        });
    }
    @Override
    public void onEditClicked(int position) {
        Terms terms = adapter.getTermAtPosition(position);
        Intent intent = new Intent(TermList.this, EditTerm.class);
        intent.putExtra("termId", terms.getTermID());
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(int position) {
        Terms terms = adapter.getTermAtPosition(position);
        termViewModel.delete(terms);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}
