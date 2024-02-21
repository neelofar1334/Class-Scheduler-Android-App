package com.example.c196.entities;
import java.util.Date;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "courses")
public class Courses {

    @PrimaryKey(autoGenerate = true)
    public int courseID;
    public String title;
    public Date startDate;
    public Date endDate;

    public String status; // "In Progress", "Completed", "Dropped", "Plan to take"

    public Courses(String title, Date startDate, Date endDate, String status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return courseID;
    }

    public void setId(int id) {
        this.courseID = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

