package com.example.c196.ui.login;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.c196.R;
import com.example.c196.DAO.UsersDAO;
import com.example.c196.database.AppDatabase;
import com.example.c196.database.loginData.LModel.LoginRepository;
import com.example.c196.entities.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private final ExecutorService executorService;
    private UsersDAO usersDAO;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        loginRepository = LoginRepository.getInstance(application);
        executorService = Executors.newSingleThreadExecutor();
        usersDAO = db.usersDAO();
    }

    //Methods for testing purposes
    public void setUsersDAO(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        executorService.execute(() -> {
            User user = usersDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                // Successful login
                loginResult.postValue(new LoginResult(new LoggedInUserView(user.getUsername())));
            } else {
                // Login failed
                if (user == null) {
                    postToast("User not found");
                } else {
                    postToast("Invalid password");
                }
                loginResult.postValue(new LoginResult(R.string.login_failed));
            }
        });
    }

    private void postToast(String message) {
        new android.os.Handler(android.os.Looper.getMainLooper()).post(() ->
                Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show());
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}

