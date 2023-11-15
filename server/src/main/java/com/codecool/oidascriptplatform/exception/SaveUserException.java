package com.codecool.oidascriptplatform.exception;

public class SaveUserException extends RuntimeException {
    public SaveUserException() {
        super("Failed to save user to database");
    }
}
