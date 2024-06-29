package com.example.c196.entities;

import androidx.room.Entity;

@Entity(tableName = "student")
public class Student extends User {
    private String studentId;

    public Student(String id, String username, String password, String userType, String studentId) {
        super(id, username, password, userType);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
