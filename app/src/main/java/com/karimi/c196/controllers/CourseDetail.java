package com.karimi.c196.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.karimi.c196.R;
import com.karimi.c196.Utility.AlarmHelper;
import com.karimi.c196.entities.Courses;
import com.karimi.c196.entities.Notes;
import com.karimi.c196.viewmodel.AssessmentViewModel;
import com.karimi.c196.viewmodel.CourseViewModel;
import com.karimi.c196.viewmodel.NotesViewModel;

import java.util.ArrayList;

import com.karimi.c196.View.AssessmentListAdapter;
import com.karimi.c196.View.NotesListAdapter;

public class CourseDetail extends MenuActivity implements NotesListAdapter.NoteActionsListener {

    private TextView courseTitleLabel, courseStatus, courseStartDate, courseEndDate, instructorName, instructorEmail, instructorPhone;
    private RecyclerView assessmentsListRecyclerView, notesListrecyclerview;
    private Button addButton, addAssessmentButton;
    private ImageView notifyStart, notifyEnd;
    private CourseViewModel courseViewModel;
    private NotesViewModel notesViewModel;
    private int courseId;
    private String courseTitle; //for notification details
    private AssessmentViewModel assessmentViewModel;
    private NotesListAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        //retrieve course ID from existing course
        initializeViews();
        courseId = getIntent().getIntExtra("courseId", -1);
        setupViewModels();
        if (courseId != -1) {
            setupRecyclerView();
            loadCourseData();
            setupButtonListeners();
            setupNotificationIcons();
        }
    }

    private void initializeViews() {
        courseTitleLabel = findViewById(R.id.courseTitleLabel);
        courseStatus = findViewById(R.id.courseStatus);
        courseStartDate = findViewById(R.id.courseStartDate);
        courseEndDate = findViewById(R.id.courseEndDate);
        instructorName = findViewById(R.id.instructorName);
        instructorEmail = findViewById(R.id.instructorEmail);
        instructorPhone = findViewById(R.id.instructorPhone);
        addAssessmentButton = findViewById(R.id.addAssessmentButton);
        assessmentsListRecyclerView = findViewById(R.id.assessmentsList_recycler_view);
        notesListrecyclerview = findViewById(R.id.notesList_recycler_view);
        addButton = findViewById(R.id.addButton);
        notifyStart = findViewById(R.id.notifyStart);
        notifyEnd = findViewById(R.id.notifyEnd);
        if (notifyEnd == null) {
            Log.d("CourseDetail", "notifyEnd is null");
        } else {
            Log.d("CourseDetail", "notifyEnd is properly initialized");
        }
    }

    private void loadCourseData() {
        courseId = getIntent().getIntExtra("courseId", -1);
        if (courseId != -1) {
            courseViewModel.getCourseById(courseId).observe(this, this::populateCourseDetails);
            setupNotificationIcons(); //notification details
        }
    }

    private void populateCourseDetails(Courses courses) {
        courseTitle = courses.getTitle(); //for notification details
        courseTitleLabel.setText(courses.getTitle());
        courseStatus.setText(courses.getStatus());
        courseStartDate.setText(courses.getStartDate());
        courseEndDate.setText(courses.getEndDate());
        instructorName.setText(courses.getInstructorName());
        instructorEmail.setText(courses.getInstructorEmail());
        instructorPhone.setText(courses.getInstructorPhone());
    }

    private void setupViewModels() {
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
    }

    private void setupRecyclerView() {
        assessmentsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesListrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        //initialize assessment adapter with an empty list and no listener
        AssessmentListAdapter assessmentAdapter = new AssessmentListAdapter(this, new ArrayList<>(), null, false);
        assessmentsListRecyclerView.setAdapter(assessmentAdapter);

        //initialize notes adapter with show buttons
        notesAdapter = new NotesListAdapter(this, new ArrayList<>(), this, true);
        notesListrecyclerview.setAdapter(notesAdapter);

        //setup LiveData
        setupObservers(assessmentAdapter, notesAdapter);
    }


    private void setupObservers(AssessmentListAdapter assessmentAdapter, NotesListAdapter notesAdapter) {
        assessmentViewModel.getAssessmentsBycourseId(courseId).observe(this, assessments -> {
            if (assessments != null) {
                assessmentAdapter.setAssessments(assessments);
            } else {
                Toast.makeText(this, "No assessments available.", Toast.LENGTH_SHORT).show();
            }
        });

        notesViewModel.getNotesByCourseId(courseId).observe(this, notes -> {
            if (notes != null) {
                notesAdapter.setNotes(notes);
            } else {
                Toast.makeText(this, "No notes available.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //buttons for notes recycler view row
    @Override
    public void onShareClicked(int position) {
        Notes note = notesAdapter.getNoteAt(position);
        if (note != null) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Note Title: " + note.getNoteTitle() + "\nContent: " + note.getText());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Share Note via:");
            startActivity(shareIntent);
        } else {
            Toast.makeText(this, "Note not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClicked(int position) {
        Notes noteToDelete = notesAdapter.getNoteAt(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this note?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            notesViewModel.delete(noteToDelete);
            notesAdapter.notifyItemRemoved(position);
            notesAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onViewClicked(int position) {
        Notes noteToView = notesAdapter.getNoteAt(position);
        showNoteDetailsDialog(noteToView);
    }

    //show note details for view button
    private void showNoteDetailsDialog(Notes note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(note.getNoteTitle());
        builder.setMessage(note.getText());
        builder.setPositiveButton("Close", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //add note and add assessment buttons
    private void setupButtonListeners() {
        addButton.setOnClickListener(v -> onAddClicked());
        addAssessmentButton.setOnClickListener(v -> onAddAssessmentClicked());
    }

    public void onAddAssessmentClicked() {
        Intent intent = new Intent(CourseDetail.this, AddAssessment.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void onAddClicked() {
        Intent intent = new Intent(CourseDetail.this, AddNotes.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    //show app menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    //notifications
    private void setupNotificationIcons() {
        notifyStart.setOnClickListener(v -> {
            String startDate = courseStartDate.getText().toString();
            String startNotificationTitle = "Course ";
            String startNotificationText = courseTitle + " starts on " + startDate;
            AlarmHelper.setNotification(this, startDate, startNotificationTitle, startNotificationText, courseId);
        });

        notifyEnd.setOnClickListener(v -> {
            String endDate = courseEndDate.getText().toString();
            String endNotificationTitle = "Course ";
            String endNotificationText = courseTitle + " ends on " + endDate;
            AlarmHelper.setNotification(this, endDate, endNotificationTitle, endNotificationText, courseId + 1);
        });
    }
}
