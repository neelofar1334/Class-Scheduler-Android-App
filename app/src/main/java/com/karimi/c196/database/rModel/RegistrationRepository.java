package com.karimi.c196.database.rModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.karimi.c196.DAO.AdminDAO;
import com.karimi.c196.DAO.StudentDAO;
import com.karimi.c196.DAO.UsersDAO;
import com.karimi.c196.database.AppDatabase;
import com.karimi.c196.entities.Admin;
import com.karimi.c196.entities.Student;
import com.karimi.c196.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrationRepository {

    private final UsersDAO usersDAO;
    private final AdminDAO adminDAO;
    private final StudentDAO studentDAO;
    private final ExecutorService executorService;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public RegistrationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        usersDAO = db.usersDAO();
        adminDAO = db.adminDAO();
        studentDAO = db.studentDAO();
        executorService = Executors.newSingleThreadExecutor();

    }

    public void register(String username, String password, String userType, String permissions, RegistrationCallback callback) {
        executorService.execute(() -> {
            try {
                //Check if the user already exists
                User existingUser = usersDAO.getUserByUsername(username);
                if (existingUser != null) {
                    mainHandler.post(() -> callback.onFailure(new Exception("User already exists")));
                    return;
                }

                //Register new user
                User newUser;
                if ("admin".equalsIgnoreCase(userType)) {
                    newUser = new Admin(java.util.UUID.randomUUID().toString(), username, password, userType, permissions);
                    adminDAO.insert((Admin) newUser);
                } else if ("student".equalsIgnoreCase(userType)) {
                    String studentId = java.util.UUID.randomUUID().toString();
                    newUser = new Student(java.util.UUID.randomUUID().toString(), username, password, userType, studentId);
                    studentDAO.insert((Student) newUser);
                } else {
                    newUser = new User(java.util.UUID.randomUUID().toString(), username, password, userType);
                    usersDAO.insert(newUser);
                }

                Log.d("RegistrationRepository", "User registered successfully");
                mainHandler.post(() -> callback.onSuccess(new RegisteredUser(newUser.getId(), newUser.getUsername())));
            } catch (Exception e) {
                Log.e("RegistrationRepository", "Error during registration", e);
                mainHandler.post(() -> callback.onFailure(new Exception("Error registering", e)));
            }
        });
    }

    public interface RegistrationCallback {
        void onSuccess(RegisteredUser user);

        void onFailure(Exception e);
    }
}
