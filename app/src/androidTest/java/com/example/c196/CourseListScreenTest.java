package com.example.c196;

import static android.content.ContentValues.TAG;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.example.c196.DAO.CoursesDAO;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.controllers.CourseList;
import com.example.c196.database.AppDatabase;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.util.Log;

@RunWith(AndroidJUnit4.class)
public class CourseListScreenTest {

    private AppDatabase db;
    private static final String TAG = CourseListScreenTest.class.getSimpleName();

    @Before
    public void populateDb() {
        Log.d(TAG, "populating test database");
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        //insert a Term
        Terms terms = new Terms("Term", "2020-09-01", "2021-01-01");
        db.termsDAO().insert(terms);
        Log.d(TAG, "Inserted test term");

        //insert a Course that references this Term
        Courses courses = new Courses("Course", "2020-09-01", "2020-12-15", "In Progress", "test name", "test email", "test phone", "Term", 1);
        db.coursesDao().insert(courses);
        Log.d(TAG, "Inserted test course");


        //retrieve the course by title
        Courses insertedCourse = db.coursesDao().getCourseByTitle("Course");
        if (insertedCourse != null) {
            Log.d(TAG, "Retrieved course: " + insertedCourse.getTitle() + ", Term: " + insertedCourse.getTermName());
        } else {
            Log.d(TAG, "No course found with title: Course");
        }

    }

    @Rule
    public ActivityScenarioRule<CourseList> activityRule =
            new ActivityScenarioRule<>(CourseList.class);

    @Test
    public void courseListDisplaysCourses() {
        Log.d(TAG, "Starting test: courseListDisplaysCourses");

        onView(withId(R.id.courses_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(0));

        onView(withId(R.id.courses_recycler_view))
                .check(matches(hasDescendant(withText("Course"))));

    }

    @After
    public void closeDb() {
        db.close();
    }

}
