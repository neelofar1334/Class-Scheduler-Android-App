package com.karimi.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.karimi.c196.entities.Courses;

import java.util.List;

@Dao
public interface CoursesDAO {

    @Query("SELECT * FROM courses WHERE courseId = :courseId")
    LiveData<Courses> getCourseById(int courseId);

    @Query("SELECT * FROM courses")
    LiveData<List<Courses>> getAllCourses();

    @Query("SELECT * FROM Courses WHERE termId = :termId")
    LiveData<List<Courses>> getCoursesByTermId(int termId);

    @Query("SELECT * FROM courses WHERE termId = :termId")
    LiveData<List<Courses>> getCoursesForTerm(int termId);

    //course validation
    @Query("SELECT EXISTS(SELECT 1 FROM Courses WHERE courseId = :courseId)")
    boolean courseExists(int courseId);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Courses courses);

    @Update
    void update(Courses courses);

    @Delete
    void delete(Courses courses);
}
