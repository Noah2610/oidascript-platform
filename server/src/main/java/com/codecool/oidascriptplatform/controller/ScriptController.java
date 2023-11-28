package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.controller.dto.GetUserScriptsResponseBody;
import com.codecool.oidascriptplatform.model.ScriptDetails;
import com.codecool.oidascriptplatform.service.ScriptService;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/scripts")
public class ScriptController {
    private final ScriptService scriptService;

    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @GetMapping
    public GetUserScriptsResponseBody getUserScripts(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Not authenticated");
        }

        String username = authentication.getName();
        List<ScriptDetails> scriptDetails = scriptService.getScriptDetailsForUsername(username);
        return new GetUserScriptsResponseBody(scriptDetails);
    }
}
