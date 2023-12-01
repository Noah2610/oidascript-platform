package com.codecool.oidascriptplatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateScriptRequestBody {
    @JsonProperty(required = true)
    private final String name;
    @JsonProperty(required = false)
    private final String description;
    @JsonProperty(required = true)
    private final String body;

    public CreateScriptRequestBody(String name, String description, String body) {
        this.name = name;
        this.description = description;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }
}
