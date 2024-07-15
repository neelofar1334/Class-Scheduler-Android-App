package com.example.c196.viewmodel;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import android.app.Application;
import android.util.Log;

import com.example.c196.Stubs.StubCoursesDAO;
import com.example.c196.Stubs.StubTermsDAO;
import com.example.c196.entities.Terms;
import com.example.c196.entities.Courses;
import com.example.c196.LiveDataTestUtil;

import java.util.List;
import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class TermViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private StubTermsDAO stubTermsDAO;
    private StubCoursesDAO stubCoursesDAO;
    private TermViewModel termViewModel;
    private static final String TAG = "TermViewModelTest";

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        stubTermsDAO = new StubTermsDAO();
        stubCoursesDAO = new StubCoursesDAO();
        termViewModel = new TermViewModel(application);
        termViewModel.setTermsDAO(stubTermsDAO);
    }

    @Test
    public void testAddAndDeleteTerm() throws InterruptedException, TimeoutException {
        Log.d(TAG, "Starting testAddAndDeleteTerm");

        // Reset DAO data
        stubTermsDAO.clearAll();
        stubCoursesDAO.clearAll();

        // Add a term
        Terms newTerm = new Terms();
        newTerm.setTermID(1);
        newTerm.setTitle("Spring 2024");
        newTerm.setStartDate("2024-01-01");
        newTerm.setEndDate("2024-06-01");
        stubTermsDAO.insert(newTerm);
        Log.d(TAG, "Inserted term: " + newTerm.getTitle());

        // Verify term was added
        List<Terms> termsAfterInsert = LiveDataTestUtil.getValue(termViewModel.getAllTerms());
        Log.d(TAG, "Observed terms after insert: " + termsAfterInsert.size());
        for (Terms term : termsAfterInsert) {
            Log.d(TAG, "Observed term: " + term.getTitle());
        }
        assertEquals(1, termsAfterInsert.size());
        assertEquals("Spring 2024", termsAfterInsert.get(0).getTitle());

        // Add and delete a course associated with the term
        Courses course = new Courses("Course 1", "2024-01-01", "2024-06-01", "Active", "Instructor Name", "Instructor Email", "Instructor Phone", newTerm.getTermID());
        course.setCourseId(1);
        stubCoursesDAO.insert(course);
        Log.d(TAG, "Inserted course: " + course.getTitle());

        // Verify course addition
        List<Courses> coursesAfterInsert = LiveDataTestUtil.getValue(stubCoursesDAO.getCoursesByTermId(newTerm.getTermID()));
        assertEquals(1, coursesAfterInsert.size());
        Log.d(TAG, "Verified inserted course: " + coursesAfterInsert.get(0).getTitle());

        // Now delete the course
        stubCoursesDAO.delete(course);
        Log.d(TAG, "Deleted course: " + course.getTitle());

        List<Courses> coursesAfterDelete = LiveDataTestUtil.getValue(stubCoursesDAO.getCoursesByTermId(newTerm.getTermID()));
        assertEquals(0, coursesAfterDelete.size());
        Log.d(TAG, "Verified course deletion, course list size: " + coursesAfterDelete.size());

        // Delete the term
        stubTermsDAO.delete(newTerm);

        // Check term deletion
        List<Terms> termsAfterDelete = LiveDataTestUtil.getValue(termViewModel.getAllTerms());
        Log.d(TAG, "Observed terms after delete: " + termsAfterDelete.size());
        assertEquals(0, termsAfterDelete.size());
    }
}
