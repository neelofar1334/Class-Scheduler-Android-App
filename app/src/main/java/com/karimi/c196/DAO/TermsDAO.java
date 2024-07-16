package com.karimi.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.karimi.c196.entities.Courses;
import com.karimi.c196.entities.Terms;
import java.util.List;

@Dao
public interface TermsDAO {

    //retrieves all terms
    @Query("SELECT * FROM terms")
    LiveData<List<Terms>> getAllTerms();

    @Query("SELECT * FROM terms WHERE termID = :termID LIMIT 1")
    LiveData<Terms> getTermById(int termID);

    //term validation
    @Query("SELECT EXISTS(SELECT 1 FROM Terms WHERE termID = :termId)")
    boolean termExists(int termId);

    //inserts new term
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Terms term);

    //updates existing term
    @Update
    void update(Terms term);

    //removes a term
    @Delete
    void delete(Terms term);

    @Query("SELECT * FROM courses WHERE termId = :termId")
    LiveData<List<Courses>> getCoursesForTerm(int termId);
}

