package com.codecool.oidascriptplatform.controller;

// TODO: refactor into generic `ControllerAdvice`

import com.codecool.oidascriptplatform.exception.NotAuthenticatedException;
import com.codecool.oidascriptplatform.exception.RegisterUserException;
import com.codecool.oidascriptplatform.exception.SaveUserException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RegisterUserException.class)
    public String registerUserExceptionHandler(RegisterUserException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SaveUserException.class)
    public String saveUserExceptionHandler(SaveUserException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public String insufficientAuthenticationExceptionHandler(InsufficientAuthenticationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        return String.format("Invalid request body: %s", ex.getMessage());
    }
}
