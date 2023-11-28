package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.controller.dto.CreateScriptRequestBody;
import com.codecool.oidascriptplatform.controller.dto.GetUserScriptsResponseBody;
import com.codecool.oidascriptplatform.model.ScriptDetails;
import com.codecool.oidascriptplatform.service.ScriptService;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/scripts")
public class ScriptController {
    private final ScriptService scriptService;

    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @GetMapping
    public GetUserScriptsResponseBody getScripts(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Not authenticated");
        }

        String username = authentication.getName();
        List<ScriptDetails> scriptDetails = scriptService.getScriptDetailsForUsername(username);
        return new GetUserScriptsResponseBody(scriptDetails);
    }

    @GetMapping(value = "{id}")
    public ScriptDetails getScript(
            Authentication authentication,
            @PathVariable Long id
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Not authenticated");
        }

        String username = authentication.getName();
        return scriptService.getScriptForUsername(username, id);
    }

    @PostMapping
    public ScriptDetails createScript(
            Authentication authentication,
            @RequestBody CreateScriptRequestBody body
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Not authenticated");
        }

        String username = authentication.getName();
        return scriptService.createScriptForUsername(username, body);
    }

    @PatchMapping(value = "{id}")
    public ScriptDetails updateScript(
            Authentication authentication,
            @RequestBody CreateScriptRequestBody body,
            @PathVariable Long id
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Not authenticated");
        }

        String username = authentication.getName();
        return scriptService.updateScriptForUsername(username, id, body);
    }
}
