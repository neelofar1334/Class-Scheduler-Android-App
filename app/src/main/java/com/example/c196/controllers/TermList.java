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
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;
import com.example.c196.viewmodel.CourseViewModel;
import com.example.c196.viewmodel.TermViewModel;

import java.util.ArrayList;
import java.util.List;

import View.CourseListAdapter;
import View.TermListAdapter;

public class TermList extends MenuActivity implements TermListAdapter.OnTermListener {

    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;
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

        //initialize viewModels
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        //ViewModel methods
        termViewModel.getAllTerms().observe(this, terms -> {
            adapter.setTerms(terms);
        });

    }

    @Override
    public void onViewClicked(int position) {
        Terms terms = adapter.getTermAtPosition(position);
        Intent intent = new Intent(TermList.this, TermDetail.class);
        intent.putExtra("termId", terms.getTermID());
        startActivity(intent);
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
        Terms term = adapter.getTermAtPosition(position);

        courseViewModel.getCoursesByTermId(term.getTermID()).observe(this, courses -> {
            if (courses.isEmpty()) {

                termViewModel.delete(term);
                Toast.makeText(this, "Term deleted successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cannot delete term, please delete associated courses first.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}
