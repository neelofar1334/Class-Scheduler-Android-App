package com.example.c196.registration;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196.DAO.AdminDAO;
import com.example.c196.DAO.StudentDAO;
import com.example.c196.DAO.UsersDAO;
import com.example.c196.R;
import com.example.c196.database.AppDatabase;
import com.example.c196.database.rModel.RegisteredUser;
import com.example.c196.database.rModel.RegistrationRepository;
import com.example.c196.entities.Admin;
import com.example.c196.entities.Student;
import com.example.c196.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrationViewModel extends AndroidViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<UserRegistrationResult> registerResult = new MutableLiveData<>();
    private final RegistrationRepository registrationRepository;
    private final UsersDAO usersDAO;
    private final AdminDAO adminDAO;
    private final StudentDAO studentDAO;
    private final ExecutorService executorService;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        usersDAO = db.usersDAO();
        adminDAO = db.adminDAO();
        studentDAO = db.studentDAO();
        registrationRepository = new RegistrationRepository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public LiveData<UserRegistrationResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String password, String userType, String permissions, RegistrationRepository.RegistrationCallback callback) {
        executorService.execute(() -> {
            try {
                // Check if the user already exists
                User existingUser = usersDAO.getUserByUsername(username);
                if (existingUser != null) {
                    registerResult.postValue(new UserRegistrationResult(R.string.user_exists));
                    return;
                }

                // Register new user
                User newUser = new User(java.util.UUID.randomUUID().toString(), username, password, userType);
                usersDAO.insert(newUser); // Insert into User table

                if ("admin".equalsIgnoreCase(userType)) {
                    Admin admin = new Admin(newUser.getId(), username, password, userType, permissions);
                    adminDAO.insert(admin);
                } else if ("student".equalsIgnoreCase(userType)) {
                    String studentId = java.util.UUID.randomUUID().toString();
                    Student student = new Student(newUser.getId(), username, password, userType, studentId);
                    studentDAO.insert(student);
                }

                // Convert User to RegisteredUser and call onSuccess
                RegisteredUser registeredUser = new RegisteredUser(newUser.getId(), newUser.getUsername());
                callback.onSuccess(registeredUser);

                registerResult.postValue(new UserRegistrationResult(new RegisteredUserView(newUser.getUsername())));
                Log.d("RegistrationViewModel", "User registered successfully");

            } catch (Exception e) {
                registerResult.postValue(new UserRegistrationResult(R.string.register_failed));
                callback.onFailure(new Exception("Error registering", e));
                Log.e("RegistrationViewModel", "Error during registration", e);
            }
        });
    }

    public void registerDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
