package com.example.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.entities.Assessments;

import java.util.List;

@Dao
public interface AssessmentsDAO {

    @Query("SELECT * FROM assessments WHERE assessmentId = :assessmentId LIMIT 1")
    LiveData<Assessments> getAssessmentByID(int assessmentId);

    @Query("SELECT * FROM assessments")
    LiveData<List<Assessments>> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseId = :courseId")
    LiveData<List<Assessments>> getAssessmentsBycourseId(int courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessments assessment);

    @Update
    void update(Assessments assessment);

    @Delete
    void delete(Assessments assessments);
}
