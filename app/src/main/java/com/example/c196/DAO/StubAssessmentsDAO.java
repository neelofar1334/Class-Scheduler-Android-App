package com.example.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196.entities.Assessments;

import java.util.ArrayList;
import java.util.List;

public class StubAssessmentsDAO implements AssessmentsDAO {
    private final List<Assessments> assessmentList = new ArrayList<>();
    private final MutableLiveData<List<Assessments>> liveDataAssessments = new MutableLiveData<>(new ArrayList<>());

    @Override
    public LiveData<Assessments> getAssessmentByID(int assessmentId) {
        for (Assessments assessment : assessmentList) {
            if (assessment.getAssessmentId() == assessmentId) {
                MutableLiveData<Assessments> liveData = new MutableLiveData<>();
                liveData.setValue(assessment);
                return liveData;
            }
        }
        return null;
    }

    @Override
    public LiveData<List<Assessments>> getAllAssessments() {
        liveDataAssessments.setValue(assessmentList);
        return liveDataAssessments;
    }

    @Override
    public LiveData<List<Assessments>> getAssessmentsBycourseId(int courseId) {
        List<Assessments> assessmentsForCourse = new ArrayList<>();
        for (Assessments assessment : assessmentList) {
            if (assessment.getCourseId() == courseId) {
                assessmentsForCourse.add(assessment);
            }
        }
        MutableLiveData<List<Assessments>> liveData = new MutableLiveData<>();
        liveData.setValue(assessmentsForCourse);
        return liveData;
    }

    @Override
    public LiveData<List<Assessments>> getAssessmentsForCourse(int courseId) {
        return null;
    }

    @Override
    public void insert(Assessments assessment) {
        assessmentList.add(assessment);
        liveDataAssessments.setValue(new ArrayList<>(assessmentList));
    }

    @Override
    public void update(Assessments assessment) {
        for (int i = 0; i < assessmentList.size(); i++) {
            if (assessmentList.get(i).getAssessmentId() == assessment.getAssessmentId()) {
                assessmentList.set(i, assessment);
                liveDataAssessments.setValue(new ArrayList<>(assessmentList));
                return;
            }
        }
    }

    @Override
    public void delete(Assessments assessment) {
        assessmentList.remove(assessment);
        liveDataAssessments.setValue(new ArrayList<>(assessmentList));
    }

}
