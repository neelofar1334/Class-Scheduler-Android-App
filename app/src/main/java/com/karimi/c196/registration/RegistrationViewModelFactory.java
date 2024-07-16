package com.karimi.c196.registration;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

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
            return (T) new RegistrationViewModel(application);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
