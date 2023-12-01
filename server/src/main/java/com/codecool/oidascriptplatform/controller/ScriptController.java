package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.dto.CreateScriptRequestBody;
import com.codecool.oidascriptplatform.dto.GetUserScriptsResponseBody;
import com.codecool.oidascriptplatform.dto.ScriptDetailsWithBody;
import com.codecool.oidascriptplatform.model.ScriptDetails;
import com.codecool.oidascriptplatform.service.ScriptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ScriptDetailsWithBody getScript(
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
    public ScriptDetailsWithBody createScript(
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
    public ScriptDetailsWithBody updateScript(
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

    @DeleteMapping(value = "{id}")
    public String deleteScript(
            Authentication authentication,
            @PathVariable Long id
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Not authenticated");
        }

        String username = authentication.getName();
        scriptService.deleteScriptForUsername(username, id);
        return "Deleted script";
    }
}
