package com.codecool.oidascriptplatform.controller.dto;

public class CreateSessionResponseBody {
    private final String username;

    public CreateSessionResponseBody(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
