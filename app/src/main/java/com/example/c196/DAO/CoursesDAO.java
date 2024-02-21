package com.example.c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.entities.Courses;
import java.util.List;

@Dao
public interface CoursesDAO {
    @Query("SELECT * FROM courses")
    List<Courses> getAllCourses();

    @Query("SELECT * FROM courses WHERE courseID=:course ORDER BY courseID ASC")
    List<Courses> getAssociatedCourses(int course);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Courses courses);

    @Update
    void update(Courses courses);

    @Delete
    void delete(Courses courses);
}
