package com.example.c196.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.util.Date;

@Entity(tableName = "terms")
public class Terms {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int termID;
    @ColumnInfo(name = "Term Name")
    private String name;
    @ColumnInfo(name = "Term Start")
    private String startDate;
    @ColumnInfo(name = "Term End")
    private String endDate;
    @ColumnInfo(name = "courseID")
    private int courseID;

    public Terms(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //no args constructor for room framework
    public Terms() {
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}

