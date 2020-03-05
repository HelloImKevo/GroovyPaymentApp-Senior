package com.imobile3.groovypayments.data;

import androidx.annotation.NonNull;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class Result<T> {

    // Hide the private constructor to limit subclass types (Success, Error)
    private Result() {
    }

    @NonNull
    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success)this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Result.Error error = (Result.Error)this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    public final static class Success<T> extends Result<T> {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    public final static class Error<T> extends Result<T> {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}
