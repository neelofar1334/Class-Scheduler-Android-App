package com.example.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;

import java.util.List;

@Dao
public interface AssessmentsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessments assessment);

    @Update
    void update(Assessments assessment);

    //retrieves single assessment by title
    @Query("SELECT * FROM assessments WHERE title = :title LIMIT 1")
    Assessments getAssessmentByTitle(String title);

    @Query("SELECT * FROM assessments WHERE assessmentId = :assessmentId LIMIT 1")
    LiveData<Assessments> getAssessmentByID(int assessmentId);

    @Query("SELECT * FROM assessments")
    LiveData<List<Assessments>> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE assessmentId=:assessment ORDER BY assessmentId ASC")
    LiveData<List<Assessments>> getAssociatedAssessments(int assessment);

    @Delete
    void delete(Assessments assessments);
}
