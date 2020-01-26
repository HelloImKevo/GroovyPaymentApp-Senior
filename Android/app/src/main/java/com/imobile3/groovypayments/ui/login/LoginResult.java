package com.imobile3.groovypayments.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result: success (user details) or error message.
 */
class LoginResult {

    @Nullable
    private Integer error;
    @Nullable
    private LoggedInUserView success;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
