package com.karimi.c196.database.rModel;

/**
 * A generic class that holds a result success with data or an error exception.
 */
public class GenericResult<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private GenericResult() {}

    @Override
    public String toString() {
        if (this instanceof GenericResult.Success) {
            GenericResult.Success success = (GenericResult.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof GenericResult.Error) {
            GenericResult.Error error = (GenericResult.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public static final class Success<T> extends GenericResult {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public static final class Error extends GenericResult {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}
