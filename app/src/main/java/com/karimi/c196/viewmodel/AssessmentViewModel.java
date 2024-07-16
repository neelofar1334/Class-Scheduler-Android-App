package com.karimi.c196.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.karimi.c196.DAO.AssessmentsDAO;
import com.karimi.c196.database.Repository;
import com.karimi.c196.entities.Assessments;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    private LiveData<List<Assessments>> allAssessments;
    private Repository repository;
    private AssessmentsDAO assessmentsDAO;

    public AssessmentViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        assessmentsDAO = repository.getAssessmentsDAO();
        allAssessments = assessmentsDAO.getAllAssessments();
    }

    // Setter method for testing purposes
    public void setAssessmentsDAO(AssessmentsDAO assessmentsDAO) {
        this.assessmentsDAO = assessmentsDAO;
        allAssessments = assessmentsDAO.getAllAssessments();
    }

    public LiveData<List<Assessments>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<Assessments>> getAssessmentsBycourseId(int courseId) {
        return assessmentsDAO.getAssessmentsBycourseId(courseId);
    }

    public LiveData<Assessments> getAssessmentById(int assessmentId) {
        return assessmentsDAO.getAssessmentByID(assessmentId);
    }

    public void delete(Assessments assessments) {
        assessmentsDAO.delete(assessments);
    }
}
