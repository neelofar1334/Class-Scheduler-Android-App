package com.karimi.c196.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes",
        foreignKeys = @ForeignKey(entity = Courses.class,
                parentColumns = "courseId",
                childColumns = "courseId",
                onDelete = ForeignKey.CASCADE))
public class Notes {
    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private String noteTitle;
    private String text;
    private int courseId; //foreign key linked to Course entity

    public Notes(String noteTitle, String text, int courseId) {
        this.noteTitle = noteTitle;
        this.text = text;
        this.courseId = courseId;
    }

    public Notes() {

    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
