package com.karimi.c196.Stubs;

import androidx.lifecycle.LiveData;

import com.karimi.c196.DAO.StudentDAO;
import com.karimi.c196.entities.Student;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StubStudentDAO implements StudentDAO {
    private final Map<String, Student> studentMap = new HashMap<>();

    @Override
    public void insert(Student student) {
        studentMap.put(student.getUsername(), student);
    }

    @Override
    public LiveData<Student> getStudentByUsername(String username) {
        return null;
    }

    @Override
    public LiveData<List<Student>> getAllStudents() {
        return null;
    }
}
