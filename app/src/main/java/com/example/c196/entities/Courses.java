package com.example.c196.entities;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "courses",
        foreignKeys = @ForeignKey(entity = Terms.class,
                parentColumns = "termID",
                childColumns = "termId",
                onDelete = ForeignKey.CASCADE))
public class Courses {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "courseID")
    private int courseID;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "start")
    private String startDate;
    @ColumnInfo(name = "end")
    private String endDate;
    @ColumnInfo(name = "status")
    private String status; // "In Progress", "Completed", "Dropped", "Plan to take"
    @ColumnInfo(name = "Term Name")
    private String termName;
    @ColumnInfo(name = "termId")
    private int termId; //foreign key

    public Courses(String title, String startDate, String endDate, String status, String termName, int termId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termName = termName;
        this.termId = termId;
    }

    //no args constructor for room framework
    public Courses() {
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int id) {
        this.courseID = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public int getTermId() { return termId; }

    public void setTermId(int termId) { this.termId = termId; }
}

