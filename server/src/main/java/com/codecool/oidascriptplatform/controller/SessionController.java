package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.controller.data.CreateSessionResponseBody;
import com.codecool.oidascriptplatform.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sessions")
public class SessionController {
    private final UserService userService;

    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public CreateSessionResponseBody createSession(Authentication authentication) {
        return new CreateSessionResponseBody(authentication.getName());
    }
}
