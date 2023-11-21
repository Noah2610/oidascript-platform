package com.codecool.oidascriptplatform.controller.data;

public class CreateSessionRequestBody {
    private final String username;
    private final String password;

    public CreateSessionRequestBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
