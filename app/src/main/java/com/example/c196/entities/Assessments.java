package com.example.c196.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Assessments",
        foreignKeys = @ForeignKey(entity = Courses.class,
                parentColumns = "courseId",
                childColumns = "courseId",
                onDelete = ForeignKey.CASCADE))
public class Assessments {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessmentID")
    private int assessmentId;
    @ColumnInfo(name = "courseId")
    private int courseId; //foreign key linked to course entity
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "startDate")
    private String startDate;
    @ColumnInfo(name = "endDate")
    private String endDate;

    public Assessments(int courseId, String title, String type, String startDate, String endDate) {
        this.courseId = courseId;
        this.title = title;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //no args constructor
    public Assessments() {
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
