package com.codecool.oidascriptplatform.exception;

public class RegisterUserException extends RuntimeException {
    public RegisterUserException(String msg) {
        super(String.format("Couldn't register user: %s", msg));
    }
    public RegisterUserException() {
        super("Couldn't register user: Unknown error");
    }
}
