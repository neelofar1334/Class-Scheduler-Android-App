package com.example.c196.database;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.c196.DAO.CoursesDAO;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.MainActivity;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;
import com.example.c196.viewmodel.CourseViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

public class Repository {

    //m is for instance variable, s is for static variable
    private CoursesDAO mCourseDAO;
    private TermsDAO mTermsDAO;
    private ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);
    private Context applicationContext;

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mCourseDAO = db.coursesDao();
        mTermsDAO = db.termsDAO();
        this.applicationContext = application.getApplicationContext();
    }

    //Courses
    //liveData for read operation
    public LiveData<List<Courses>> getAllCourses() {
        return mCourseDAO.getAllCourses();
    }

    //async execution for write operation
    public void insert(Courses courses) {
        databaseExecutor.execute(() -> mCourseDAO.insert(courses));
    }

    public void update(Courses courses) {
        databaseExecutor.execute(() -> mCourseDAO.update(courses));
    }

    public void delete(Courses courses) {
        databaseExecutor.execute(() -> mCourseDAO.delete(courses));
    }


    //Terms
    public LiveData<List<Terms>> getAllTerms() {
        return mTermsDAO.getAllTerms();
    }

    // Async execution for write operation
    public void insert(final Terms term) {
        databaseExecutor.execute(() -> {
            mTermsDAO.insert(term);
            // Handler to post success message on the main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(applicationContext, "Term saved successfully", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public void update(Terms terms) {
        databaseExecutor.execute(() -> mTermsDAO.update(terms));
    }

    public void delete(Terms terms) {
        databaseExecutor.execute(() -> mTermsDAO.delete(terms));
    }
}
