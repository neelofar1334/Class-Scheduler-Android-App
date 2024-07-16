package com.karimi.c196.viewmodel;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import android.app.Application;

import com.karimi.c196.Stubs.StubAssessmentsDAO;
import com.karimi.c196.entities.Assessments;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AssessmentViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private StubAssessmentsDAO stubAssessmentsDAO;
    private AssessmentViewModel assessmentViewModel;

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        stubAssessmentsDAO = new StubAssessmentsDAO();
        assessmentViewModel = new AssessmentViewModel(application);
        assessmentViewModel.setAssessmentsDAO(stubAssessmentsDAO);
    }

    @Test
    public void testGetAllAssessments() {
        // Add an assessment before observing
        Assessments newAssessment = new Assessments();
        newAssessment.setAssessmentId(1);
        newAssessment.setTitle("Assessment 1");
        newAssessment.setCourseId(1);
        stubAssessmentsDAO.insert(newAssessment);

        // Observe the assessments
        assessmentViewModel.getAllAssessments().observeForever(new Observer<List<Assessments>>() {
            @Override
            public void onChanged(List<Assessments> assessments) {
                assertNotNull(assessments);
                assertEquals(1, assessments.size()); // Should be 1 after adding the assessment
                assertEquals("Assessment 1", assessments.get(0).getTitle());
            }
        });
    }

    @Test
    public void testGetAssessmentById() {
        Assessments newAssessment = new Assessments();
        newAssessment.setAssessmentId(1);
        newAssessment.setTitle("Assessment 1");
        newAssessment.setCourseId(1);
        stubAssessmentsDAO.insert(newAssessment);

        assessmentViewModel.getAssessmentById(1).observeForever(assessment -> {
            assertNotNull(assessment);
            assertEquals("Assessment 1", assessment.getTitle());
        });
    }

    @Test
    public void testGetAssessmentsByCourseId() {
        Assessments assessment1 = new Assessments();
        assessment1.setAssessmentId(1);
        assessment1.setTitle("Assessment 1");
        assessment1.setCourseId(1);
        stubAssessmentsDAO.insert(assessment1);

        Assessments assessment2 = new Assessments();
        assessment2.setAssessmentId(2);
        assessment2.setTitle("Assessment 2");
        assessment2.setCourseId(1);
        stubAssessmentsDAO.insert(assessment2);

        Assessments assessment3 = new Assessments();
        assessment3.setAssessmentId(3);
        assessment3.setTitle("Assessment 3");
        assessment3.setCourseId(2);
        stubAssessmentsDAO.insert(assessment3);

        assessmentViewModel.getAssessmentsBycourseId(1).observeForever(assessments -> {
            assertNotNull(assessments);
            assertEquals(2, assessments.size()); // Course 1 should have 2 assessments
            assertEquals("Assessment 1", assessments.get(0).getTitle());
            assertEquals("Assessment 2", assessments.get(1).getTitle());
        });

        assessmentViewModel.getAssessmentsBycourseId(2).observeForever(assessments -> {
            assertNotNull(assessments);
            assertEquals(1, assessments.size()); // Course 2 should have 1 assessment
            assertEquals("Assessment 3", assessments.get(0).getTitle());
        });
    }

    @Test
    public void testDeleteAssessment() {
        Assessments newAssessment = new Assessments();
        newAssessment.setAssessmentId(1);
        newAssessment.setTitle("Assessment 1");
        newAssessment.setCourseId(1);
        stubAssessmentsDAO.insert(newAssessment);

        assessmentViewModel.delete(newAssessment);

        assessmentViewModel.getAllAssessments().observeForever(assessments -> {
            assertNotNull(assessments);
            assertEquals(0, assessments.size());
        });
    }
}
