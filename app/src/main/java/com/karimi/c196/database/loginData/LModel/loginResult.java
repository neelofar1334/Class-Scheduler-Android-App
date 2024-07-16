package com.karimi.c196.database.loginData.LModel;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class loginResult<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private loginResult() {
    }

    @Override
    public String toString() {
        if (this instanceof loginResult.Success) {
            loginResult.Success success = (loginResult.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof loginResult.Error) {
            loginResult.Error error = (loginResult.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends loginResult {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends loginResult {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}