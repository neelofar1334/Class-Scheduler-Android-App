package com.karimi.c196.DAO.Relationships;
import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;
import com.karimi.c196.entities.Courses;
import com.karimi.c196.entities.Terms;

public class TermsWithCourses {
    @Embedded
    public Terms term;

    @Relation(
            parentColumn = "termID",
            entityColumn = "termId"
    )
    public List<Courses> courses;
}
