package com.example.c196.Stubs;

import androidx.lifecycle.LiveData;

import com.example.c196.DAO.UsersDAO;
import com.example.c196.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StubUsersDAO implements UsersDAO {
    private final Map<String, User> userMap = new HashMap<>();

    public void addUser(User user) {
        userMap.put(user.getUsername(), user);
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public User getUserByUsername(String username) {
        return userMap.get(username);
    }

    @Override
    public LiveData<List<User>> getAllUsers() {
        return null;
    }

}
