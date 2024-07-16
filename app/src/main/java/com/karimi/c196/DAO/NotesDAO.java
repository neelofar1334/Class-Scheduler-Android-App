package com.karimi.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.karimi.c196.entities.Notes;

import java.util.List;

@Dao
public interface NotesDAO {

    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    LiveData<Notes> getNoteById(int noteId);

    @Query("SELECT * FROM Notes WHERE courseId = :courseId")
    LiveData<List<Notes>> getNotesByCourseId(int courseId);

    @Insert
    void insert(Notes notes);

    @Update
    void update(Notes notes);

    @Delete
    void delete(Notes notes);
}
