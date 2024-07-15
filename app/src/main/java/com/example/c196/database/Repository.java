package com.example.c196.database;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.DAO.CoursesDAO;
import com.example.c196.DAO.NotesDAO;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Notes;
import com.example.c196.entities.Terms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

public class Repository {

    //m is for instance variable, s is for static variable
    private final CoursesDAO mCourseDAO;
    private final TermsDAO mTermsDAO;
    private final AssessmentsDAO mAssessmentsDAO;
    private final NotesDAO mNotesDAO;
    private final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);
    private final Context applicationContext;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mCourseDAO = db.coursesDao();
        mTermsDAO = db.termsDAO();
        mAssessmentsDAO = db.assessmentDao();
        mNotesDAO = db.notesDAO();
        this.applicationContext = application.getApplicationContext();
    }

    //Courses
    public LiveData<List<Courses>> getAllCourses() {
        return mCourseDAO.getAllCourses();
    }

    public void insert(Courses courses) {
        databaseExecutor.execute(() -> mCourseDAO.insert(courses));
    }

    public void update(Courses courses) {
        databaseExecutor.execute(() -> mCourseDAO.update(courses));
    }

    public void delete(Courses courses) {
        databaseExecutor.execute(() -> mCourseDAO.delete(courses));
    }

    public LiveData<Courses> getCourseById(int courseId) {
        return mCourseDAO.getCourseById(courseId);
    }

    public LiveData<List<Courses>> getCoursesByTermId(int termId) {
        return mCourseDAO.getCoursesByTermId(termId);
    }

    public CoursesDAO getCoursesDAO() {
        return mCourseDAO;
    }

    public boolean courseExists(int courseId) {
        return mCourseDAO.courseExists(courseId);
    }

    //Terms
    public LiveData<List<Terms>> getAllTerms() {
        return mTermsDAO.getAllTerms();
    }

    public void insert(Terms term) {
        databaseExecutor.execute(() -> {
            mTermsDAO.insert(term);
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(applicationContext, "Term saved successfully", Toast.LENGTH_SHORT).show()
            );
        });
    }

    public void update(Terms terms) {
        databaseExecutor.execute(() -> mTermsDAO.update(terms));
    }

    // Repository.java
    public void delete(Terms terms) {
        databaseExecutor.execute(() -> {
            mTermsDAO.delete(terms);
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(applicationContext, "Term deleted successfully", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public LiveData<Terms> getTermById(int termId) {
        return mTermsDAO.getTermById(termId);
    }

    public TermsDAO getTermsDAO() {
        return mTermsDAO;
    }

    public boolean termExists(int termId) {
        return mTermsDAO.termExists(termId);
    }

    //Assessments
    public LiveData<List<Assessments>> getAllAssessments() {
        return mAssessmentsDAO.getAllAssessments();
    }

    public void insert(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.insert(assessments);
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(applicationContext, "Assessment saved successfully", Toast.LENGTH_SHORT).show()
            );
        });
    }

    public void update(Assessments assessments) {
        databaseExecutor.execute(() -> mAssessmentsDAO.update(assessments));
    }

    public void delete(Assessments assessments) {
        databaseExecutor.execute(() -> mAssessmentsDAO.delete(assessments));
    }

    public LiveData<Assessments> getAssessmentById(int assessmentId) {
        return mAssessmentsDAO.getAssessmentByID(assessmentId);
    }

    public AssessmentsDAO getAssessmentsDAO() {
        return mAssessmentsDAO;
    }

    //Notes
    public void insert(Notes notes) {
        databaseExecutor.execute(() -> mNotesDAO.insert(notes));
    }

    public void update(Notes notes) {
        databaseExecutor.execute(() -> mNotesDAO.update(notes));
    }

    public void delete(Notes notes) {
        databaseExecutor.execute(() -> mNotesDAO.delete(notes));
    }

    public LiveData<Notes> getNoteById(int noteId) {
        return mNotesDAO.getNoteById(noteId);
    }

    public LiveData<List<Notes>> getNotesByCourseId(int courseId) {
        return mNotesDAO.getNotesByCourseId(courseId);
    }

    //Generate Schedule
    public LiveData<List<Courses>> getCoursesForTerm(int termId) {
        return mCourseDAO.getCoursesForTerm(termId);
    }

    public LiveData<List<Assessments>> getAssessmentsForCourse(int courseId) {
        return mAssessmentsDAO.getAssessmentsForCourse(courseId);
    }
}
