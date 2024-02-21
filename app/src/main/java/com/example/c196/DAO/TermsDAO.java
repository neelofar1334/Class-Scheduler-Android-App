package com.example.c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.entities.Terms;
import java.util.List;

@Dao
public interface TermsDAO {
    //retrieves all terms
    @Query("SELECT * FROM terms")
    List<Terms> getAllTerms();

    //retrieves single term by ID
    @Query("SELECT * FROM terms WHERE termID = :termID ORDER BY termID ASC")
    Terms getTermById(int termID);

    //inserts new term
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Terms term);

    //updates existing term
    @Update
    void update(Terms term);

    //removes a term
    @Delete
    void delete(Terms term);
}

