package com.karimi.c196.database.rModel;

import android.content.Context;

import com.karimi.c196.database.AppDatabase;
import com.karimi.c196.entities.User;

public class RegistrationDataSource {

    private final AppDatabase database;

    public RegistrationDataSource(Context context) {
        this.database = AppDatabase.getDatabase(context);
    }

    public GenericResult<RegisteredUser> register(String username, String password, String userType, String permissions) {
        try {
            // Check if the user already exists
            User existingUser = database.usersDAO().getUserByUsername(username);
            if (existingUser != null) {
                return new GenericResult.Error(new Exception("User already exists"));
            }

            // Create a new user
            User newUser = new User(username, password, userType, permissions);
            database.usersDAO().insert(newUser);

            RegisteredUser registeredUser = new RegisteredUser(String.valueOf(newUser.getId()), username);
            return new GenericResult.Success<>(registeredUser);
        } catch (Exception e) {
            return new GenericResult.Error(new Exception("Error registering", e));
        }
    }
}
