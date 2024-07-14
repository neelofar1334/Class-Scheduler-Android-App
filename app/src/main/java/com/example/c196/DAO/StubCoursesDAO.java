package com.example.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196.entities.Courses;

import java.util.ArrayList;
import java.util.List;

public class StubCoursesDAO implements CoursesDAO {
    private final List<Courses> courseList = new ArrayList<>();
    private final MutableLiveData<List<Courses>> liveDataCourses = new MutableLiveData<>(new ArrayList<>());

    @Override
    public LiveData<Courses> getCourseById(int courseId) {
        for (Courses course : courseList) {
            if (course.getCourseId() == courseId) {
                MutableLiveData<Courses> liveData = new MutableLiveData<>();
                liveData.setValue(course);
                return liveData;
            }
        }
        return null;
    }

    @Override
    public LiveData<List<Courses>> getAllCourses() {
        liveDataCourses.setValue(courseList);
        return liveDataCourses;
    }

    @Override
    public LiveData<List<Courses>> getCoursesByTermId(int termId) {
        List<Courses> coursesForTerm = new ArrayList<>();
        for (Courses course : courseList) {
            if (course.getTermId() == termId) {
                coursesForTerm.add(course);
            }
        }
        MutableLiveData<List<Courses>> liveData = new MutableLiveData<>();
        liveData.setValue(coursesForTerm);
        return liveData;
    }

    @Override
    public LiveData<List<Courses>> getCoursesForTerm(int termId) {
        return null;
    }

    @Override
    public void insert(Courses course) {
        courseList.add(course);
        liveDataCourses.setValue(new ArrayList<>(courseList));
    }

    @Override
    public void update(Courses course) {
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseId() == course.getCourseId()) {
                courseList.set(i, course);
                liveDataCourses.setValue(new ArrayList<>(courseList));
                return;
            }
        }
    }

    @Override
    public void delete(Courses course) {
        courseList.remove(course);
        liveDataCourses.setValue(new ArrayList<>(courseList));
    }

    @Override
    public boolean courseExists(int courseId) {
        for (Courses course : courseList) {
            if (course.getCourseId() == courseId) {
                return true;
            }
        }
        return false;
    }

}
