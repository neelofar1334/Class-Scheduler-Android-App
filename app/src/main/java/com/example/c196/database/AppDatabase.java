package com.example.c196.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196.DAO.AdminDAO;
import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.DAO.CoursesDAO;
import com.example.c196.DAO.NotesDAO;
import com.example.c196.DAO.StudentDAO;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.DAO.UsersDAO;
import com.example.c196.entities.Admin;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Notes;
import com.example.c196.entities.Student;
import com.example.c196.entities.Terms;
import com.example.c196.entities.User;

@Database(entities = {Assessments.class, Courses.class, Terms.class, Notes.class, User.class, Admin.class, Student.class}, version = 13)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AssessmentsDAO assessmentDao();
    public abstract CoursesDAO coursesDao();
    public abstract TermsDAO termsDAO();
    public abstract NotesDAO notesDAO();
    public abstract UsersDAO usersDAO();
    public abstract AdminDAO adminDAO();
    public abstract StudentDAO studentDAO();

    private static final String DATABASE_NAME = "my_database.db";
    private static volatile AppDatabase INSTANCE; //singleton instance

    //get instance of the database
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


