package com.example.c196.registration;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import com.example.c196.database.rModel.RegistrationRepository;

public class RegistrationViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public RegistrationViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) new RegistrationViewModel(new RegistrationRepository(application));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
