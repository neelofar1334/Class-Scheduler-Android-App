package com.example.c196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.c196.entities.User;
import java.util.List;

@Dao
public interface UsersDAO {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM user WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();
}
