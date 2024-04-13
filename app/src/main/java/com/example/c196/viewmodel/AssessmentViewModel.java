package com.example.c196.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.database.Repository;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    private LiveData<List<Assessments>> allAssessments;
    private Repository repository;
    private AssessmentsDAO assessmentsDAO;

    public AssessmentViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allAssessments = repository.getAllAssessments();
        assessmentsDAO = repository.getAssessmentsDAO();
    }

    public LiveData<List<Assessments>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<Assessments>> getAssessmentsBycourseId(int courseId) {
        return assessmentsDAO.getAssessmentsBycourseId(courseId);
    }

    public void delete(Assessments assessments) {
        repository.delete(assessments);
    }
}
