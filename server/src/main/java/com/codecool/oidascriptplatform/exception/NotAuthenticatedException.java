package com.codecool.oidascriptplatform.exception;

// TODO: remove, use InsufficientAuthenticationException instead

public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException(String message) {
        super(message);
    }
}
