package com.karimi.c196.controllers;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karimi.c196.database.AppDatabase;
import com.karimi.c196.entities.Assessments;
import com.karimi.c196.entities.Courses;
import com.karimi.c196.entities.Terms;

import java.util.ArrayList;
import java.util.List;

public class GenerateSchedule extends AndroidViewModel {

    private final AppDatabase appDatabase;

    public GenerateSchedule(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
    }

    public LiveData<List<String>> generateSchedule() {
        MutableLiveData<List<String>> scheduleLiveData = new MutableLiveData<>();
        List<String> scheduleItems = new ArrayList<>();

        appDatabase.termsDAO().getAllTerms().observeForever(terms -> {
            for (Terms term : terms) {
                scheduleItems.add("Term: " + term.getTitle() + " (" + term.getStartDate() + " - " + term.getEndDate() + ")");
                appDatabase.coursesDao().getCoursesForTerm(term.getTermID()).observeForever(courses -> {
                    for (Courses course : courses) {
                        scheduleItems.add("  Course: " + course.getTitle() + " (" + course.getStartDate() + " - " + course.getEndDate() + ")");
                        appDatabase.assessmentDao().getAssessmentsForCourse(course.getCourseId()).observeForever(assessments -> {
                            for (Assessments assessment : assessments) {
                                scheduleItems.add("    Assessment: " + assessment.getTitle() + " (" + assessment.getStartDate() + " - " + assessment.getEndDate() + ")");
                            }
                            // Update LiveData once all data is collected
                            scheduleLiveData.setValue(scheduleItems);
                        });
                    }
                });
            }
        });

        return scheduleLiveData;
    }
}
