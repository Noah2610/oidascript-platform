package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.UserDetailsImpl;
import com.codecool.oidascriptplatform.controller.data.CreateSessionRequestBody;
import com.codecool.oidascriptplatform.controller.data.CreateSessionResponseBody;
import com.codecool.oidascriptplatform.exception.NotAuthenticatedException;
import com.codecool.oidascriptplatform.service.SessionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public CreateSessionResponseBody createSession(
            @RequestBody
            UserDetailsImpl body
    ) {
        Authentication auth = sessionService.createSession(body);

        if (auth == null) {
            throw new NotAuthenticatedException("Invalid login");
        }
        return new CreateSessionResponseBody(auth.getName());
    }
}
