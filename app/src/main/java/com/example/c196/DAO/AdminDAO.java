package com.example.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.c196.entities.Admin;
import java.util.List;

@Dao
public interface AdminDAO {

    @Insert
    void insert(Admin admin);

    @Query("SELECT * FROM admin WHERE username = :username")
    LiveData<Admin> getAdminByUsername(String username);

    @Query("SELECT * FROM admin")
    LiveData<List<Admin>> getAllAdmins();
}
