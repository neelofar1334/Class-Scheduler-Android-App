package com.example.c196.registration;

import androidx.annotation.Nullable;

public class UserRegistrationResult {
    @Nullable
    private RegisteredUserView success;
    @Nullable
    private Integer error;

    UserRegistrationResult(@Nullable RegisteredUserView success) {
        this.success = success;
        this.error = null;
    }

    UserRegistrationResult(@Nullable Integer error) {
        this.error = error;
        this.success = null;
    }

    @Nullable
    RegisteredUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
