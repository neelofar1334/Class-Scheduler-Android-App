package com.example.c196.registration;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.c196.R;
import com.example.c196.database.rModel.RegistrationRepository;
import com.example.c196.database.rModel.RegisteredUser;
import com.example.c196.database.rModel.GenericResult;

public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<UserRegistrationResult> registerResult = new MutableLiveData<>();
    private RegistrationRepository registrationRepository;

    public RegistrationViewModel(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public LiveData<UserRegistrationResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String password, String userType, String permissions) {
        registrationRepository.register(username, password, userType, permissions, new RegistrationRepository.RegistrationCallback() {
            @Override
            public void onSuccess(RegisteredUser user) {
                registerResult.setValue(new UserRegistrationResult(new RegisteredUserView(user.getDisplayName())));
            }

            @Override
            public void onFailure(Exception e) {
                registerResult.setValue(new UserRegistrationResult(R.string.register_failed));
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
