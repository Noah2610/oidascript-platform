package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.exception.RegisterUserException;
import org.springframework.http.HttpStatus;
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
}
