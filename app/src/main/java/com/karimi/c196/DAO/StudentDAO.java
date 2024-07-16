package com.karimi.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.karimi.c196.entities.Student;
import java.util.List;

@Dao
public interface StudentDAO {

    @Insert
    void insert(Student student);

    @Query("SELECT * FROM student WHERE username = :username")
    LiveData<Student> getStudentByUsername(String username);

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAllStudents();
}
