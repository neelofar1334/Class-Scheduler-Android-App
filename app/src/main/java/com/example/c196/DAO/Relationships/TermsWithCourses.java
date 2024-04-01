package com.example.c196.DAO.Relationships;
import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;

public class TermsWithCourses {
    @Embedded
    public Terms term;

    @Relation(
            parentColumn = "termID",
            entityColumn = "termId"
    )
    public List<Courses> courses;
}
