package com.example.c196.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.c196.database.Repository;
import com.example.c196.entities.Courses;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private LiveData<List<Courses>> allCourses;
    private Repository repository;

    public CourseViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allCourses = repository.getAllCourses();
    }

    public LiveData<List<Courses>> getAllCourses() {
        return allCourses;
    }
}
