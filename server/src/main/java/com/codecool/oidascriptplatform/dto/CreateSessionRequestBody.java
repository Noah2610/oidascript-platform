package com.codecool.oidascriptplatform.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateSessionRequestBody {
    private final String username;
    private final String password;

    @JsonCreator
    public CreateSessionRequestBody(
            @JsonProperty(value = "username", required = true)
            String username,
            @JsonProperty(value = "password", required = true)
            String password
    ) {
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
