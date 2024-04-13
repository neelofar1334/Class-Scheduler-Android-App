package com.example.c196.entities;

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
    private String content;
    private int courseId; // Linked to Course entity
}
