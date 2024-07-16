package com.karimi.c196.Stubs;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.karimi.c196.DAO.CoursesDAO;
import com.karimi.c196.entities.Courses;
import java.util.ArrayList;
import java.util.List;

public class StubCoursesDAO implements CoursesDAO {

    private static final String TAG = "StubCoursesDAO";
    private final MutableLiveData<List<Courses>> coursesLiveData = new MutableLiveData<>(new ArrayList<>());
    private final List<Courses> coursesList = new ArrayList<>();

    @Override
    public LiveData<Courses> getCourseById(int courseId) {
        for (Courses course : coursesList) {
            if (course.getCourseId() == courseId) {
                MutableLiveData<Courses> courseLiveData = new MutableLiveData<>();
                courseLiveData.setValue(course);
                return courseLiveData;
            }
        }
        return new MutableLiveData<>(null);
    }

    @Override
    public LiveData<List<Courses>> getAllCourses() {
        coursesLiveData.setValue(new ArrayList<>(coursesList)); // Ensure a fresh copy is set
        return coursesLiveData;
    }

    @Override
    public LiveData<List<Courses>> getCoursesByTermId(int termId) {
        List<Courses> filteredCourses = new ArrayList<>();
        for (Courses course : coursesList) {
            if (course.getTermId() == termId) {
                filteredCourses.add(course);
            }
        }
        MutableLiveData<List<Courses>> filteredCoursesLiveData = new MutableLiveData<>(filteredCourses);
        return filteredCoursesLiveData;
    }

    @Override
    public LiveData<List<Courses>> getCoursesForTerm(int termId) {
        return null;
    }

    @Override
    public boolean courseExists(int courseId) {
        for (Courses course : coursesList) {
            if (course.getCourseId() == courseId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void insert(Courses courses) {
        coursesList.add(courses);
        coursesLiveData.setValue(new ArrayList<>(coursesList)); // Ensure a fresh copy is set
        Log.d(TAG, "Inserted course: " + courses.getTitle());
    }

    @Override
    public void update(Courses courses) {
        for (int i = 0; i < coursesList.size(); i++) {
            if (coursesList.get(i).getCourseId() == courses.getCourseId()) {
                coursesList.set(i, courses);
                break;
            }
        }
        coursesLiveData.setValue(new ArrayList<>(coursesList)); // Ensure a fresh copy is set
        Log.d(TAG, "Updated course: " + courses.getTitle());
    }

    @Override
    public void delete(Courses courses) {
        coursesList.remove(courses);
        coursesLiveData.setValue(new ArrayList<>(coursesList)); // Ensure a fresh copy is set
        Log.d(TAG, "Deleted course: " + courses.getTitle());
    }

    public void clearAll() {
        coursesList.clear();
        coursesLiveData.setValue(new ArrayList<>(coursesList)); // Ensure a fresh copy is set
    }
}
