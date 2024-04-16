package com.example.c196.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.c196.DAO.CoursesDAO;
import com.example.c196.database.Repository;
import com.example.c196.entities.Courses;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private final LiveData<List<Courses>> allCourses;
    private final Repository repository;

    public CourseViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allCourses = repository.getAllCourses();
    }

    public LiveData<List<Courses>> getAllCourses() {
        return allCourses;
    }

    public LiveData<Courses> getCourseById(int courseId) {
        return repository.getCourseById(courseId);
    }

    public LiveData<List<Courses>> getCoursesByTermId(int termId) {
        return repository.getCoursesByTermId(termId);
    }

    public void delete(Courses course) {
        repository.delete(course);
    }
}
