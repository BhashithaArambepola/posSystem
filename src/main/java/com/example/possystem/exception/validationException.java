package com.example.possystem.exception;

public class validationException extends RuntimeException{
    public validationException() {
        super();
    }

    public validationException(String message) {
        super(message);
    }

    public validationException(String message, Throwable cause) {
        super(message, cause);
    }

    public validationException(Throwable cause) {
        super(cause);
    }

    protected validationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
