package com.example.c196.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196.DAO.CoursesDAO;
import com.example.c196.database.Repository;
import com.example.c196.entities.Courses;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private LiveData<List<Courses>> allCourses;
    private Repository repository;
    private CoursesDAO coursesDAO;

    public CourseViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        coursesDAO = repository.getCoursesDAO();
        allCourses = coursesDAO.getAllCourses();
    }

    // Setter method for testing purposes
    public void setCoursesDAO(CoursesDAO coursesDAO) {
        this.coursesDAO = coursesDAO;
        allCourses = coursesDAO.getAllCourses();
    }

    public LiveData<List<Courses>> getAllCourses() {
        return allCourses;
    }

    public LiveData<Courses> getCourseById(int courseId) {
        return coursesDAO.getCourseById(courseId);
    }

    public LiveData<List<Courses>> getCoursesByTermId(int termId) {
        return coursesDAO.getCoursesByTermId(termId);
    }

    public void delete(Courses course) {
        coursesDAO.delete(course);
    }
}
