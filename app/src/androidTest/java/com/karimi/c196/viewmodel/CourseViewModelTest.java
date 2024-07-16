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

import com.karimi.c196.Stubs.StubCoursesDAO;
import com.karimi.c196.entities.Courses;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CourseViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private StubCoursesDAO stubCoursesDAO;
    private CourseViewModel courseViewModel;

    @Before
    public void setUp() {
        Application application = ApplicationProvider.getApplicationContext();
        stubCoursesDAO = new StubCoursesDAO();
        courseViewModel = new CourseViewModel(application);
        courseViewModel.setCoursesDAO(stubCoursesDAO);
    }

    @Test
    public void testGetAllCourses() {
        // Add a course before observing
        Courses newCourse = new Courses();
        newCourse.setCourseId(1);
        newCourse.setTitle("Math 101");
        newCourse.setTermId(1);
        stubCoursesDAO.insert(newCourse);

        // Observe the courses
        courseViewModel.getAllCourses().observeForever(new Observer<List<Courses>>() {
            @Override
            public void onChanged(List<Courses> courses) {
                assertNotNull(courses);
                assertEquals(1, courses.size()); // Should be 1 after adding the course
                assertEquals("Math 101", courses.get(0).getTitle());
            }
        });
    }

    @Test
    public void testGetCourseById() {
        Courses newCourse = new Courses();
        newCourse.setCourseId(1);
        newCourse.setTitle("Math 101");
        newCourse.setTermId(1);
        stubCoursesDAO.insert(newCourse);

        courseViewModel.getCourseById(1).observeForever(course -> {
            assertNotNull(course);
            assertEquals("Math 101", course.getTitle());
        });
    }

    @Test
    public void testGetCoursesByTermId() {
        Courses course1 = new Courses();
        course1.setCourseId(1);
        course1.setTitle("Math 101");
        course1.setTermId(1);
        stubCoursesDAO.insert(course1);

        Courses course2 = new Courses();
        course2.setCourseId(2);
        course2.setTitle("History 101");
        course2.setTermId(1);
        stubCoursesDAO.insert(course2);

        Courses course3 = new Courses();
        course3.setCourseId(3);
        course3.setTitle("Physics 101");
        course3.setTermId(2);
        stubCoursesDAO.insert(course3);

        courseViewModel.getCoursesByTermId(1).observeForever(courses -> {
            assertNotNull(courses);
            assertEquals(2, courses.size()); // Term 1 should have 2 courses
            assertEquals("Math 101", courses.get(0).getTitle());
            assertEquals("History 101", courses.get(1).getTitle());
        });

        courseViewModel.getCoursesByTermId(2).observeForever(courses -> {
            assertNotNull(courses);
            assertEquals(1, courses.size()); // Term 2 should have 1 course
            assertEquals("Physics 101", courses.get(0).getTitle());
        });
    }

    @Test
    public void testDeleteCourse() {
        Courses newCourse = new Courses();
        newCourse.setCourseId(1);
        newCourse.setTitle("Math 101");
        newCourse.setTermId(1);
        stubCoursesDAO.insert(newCourse);

        courseViewModel.delete(newCourse);

        courseViewModel.getAllCourses().observeForever(courses -> {
            assertNotNull(courses);
            assertEquals(0, courses.size());
        });
    }
}
