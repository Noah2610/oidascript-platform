package com.codecool.oidascriptplatform.dto;

import com.codecool.oidascriptplatform.model.ScriptDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetUserScriptsResponseBody {
    @JsonProperty(value = "scriptDetails")
    private List<ScriptDetails> scriptDetails;

    public GetUserScriptsResponseBody(List<ScriptDetails> scriptDetails) {
        this.scriptDetails = scriptDetails;
    }
}
