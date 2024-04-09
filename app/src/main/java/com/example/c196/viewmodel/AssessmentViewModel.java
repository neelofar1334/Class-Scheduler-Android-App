package com.example.c196.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196.database.Repository;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    private LiveData<List<Assessments>> allAssessments;
    private Repository repository;

    public AssessmentViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allAssessments = repository.getAllAssessments();
    }

    public LiveData<List<Assessments>> getAllAssessments() {
        return allAssessments;
    }
}
