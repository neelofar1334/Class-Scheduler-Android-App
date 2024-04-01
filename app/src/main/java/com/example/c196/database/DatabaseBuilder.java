package com.example.c196.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196.DAO.CoursesDAO;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.entities.*;

@Database(entities = {Courses.class, Terms.class}, version = 2, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract CoursesDAO coursesDao();
    public abstract TermsDAO termsDAO();
    private static volatile DatabaseBuilder INSTANCE;

    //asynchronous: will send multiple requests to a server
    public static DatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (DatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),DatabaseBuilder.class, "MyDatabase.db")
                    .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
