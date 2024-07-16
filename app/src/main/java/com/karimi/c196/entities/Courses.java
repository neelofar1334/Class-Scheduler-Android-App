package com.karimi.c196.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Courses",
        foreignKeys = @ForeignKey(entity = Terms.class,
                parentColumns = "termID",
                childColumns = "termId",
                onDelete = ForeignKey.CASCADE))
public class Courses {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "courseId")
    private int courseId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "start")
    private String startDate;
    @ColumnInfo(name = "end")
    private String endDate;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "instructorName")
    private String instructorName;
    @ColumnInfo(name = "instructorEmail")
    private String instructorEmail;
    @ColumnInfo(name = "instructorPhone")
    private String instructorPhone;
    @ColumnInfo(name = "termId")
    private int termId; // foreign key linked to Term Entity

    public Courses(String title, String startDate, String endDate, String status, String instructorName, String instructorEmail, String instructorPhone, int termId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.termId = termId;
    }

    // No-args constructor for Room framework
    public Courses() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
