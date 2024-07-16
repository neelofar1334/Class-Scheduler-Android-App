package com.karimi.c196.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "Terms")
public class Terms {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int termID;
    @ColumnInfo(name = "termTitle")
    private String title;
    @ColumnInfo(name = "termStart")
    private String startDate;
    @ColumnInfo(name = "termEnd")
    private String endDate;
    @ColumnInfo(name = "courseId")
    private int courseId;

    public Terms(String title, String startDate, String endDate) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
