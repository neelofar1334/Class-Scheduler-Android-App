package com.example.c196.database.loginData.LModel;

import android.app.Application;

import com.example.c196.database.AppDatabase;

public class LoginRepository {

    private static volatile LoginRepository instance;
    private AppDatabase appDatabase;

    private LoginRepository(Application application) {
        appDatabase = AppDatabase.getDatabase(application);
    }

    public static LoginRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (LoginRepository.class) {
                if (instance == null) {
                    instance = new LoginRepository(application);
                }
            }
        }
        return instance;
    }

    public loginResult<LoggedInUser> login(String username, String password) {
        try {
            // TODO: Implement the logic to check login credentials against the database

            // Simulating successful login with a dummy user
            LoggedInUser newUser = new LoggedInUser(java.util.UUID.randomUUID().toString(), username);
            return new loginResult.Success<>(newUser);
        } catch (Exception e) {
            return new loginResult.Error(new Exception("Error logging in", e));
        }
    }
}
