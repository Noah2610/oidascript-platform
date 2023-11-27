package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.controller.data.CreateSessionRequestBody;
import com.codecool.oidascriptplatform.controller.data.CreateSessionResponseBody;
import com.codecool.oidascriptplatform.model.User;
import com.codecool.oidascriptplatform.service.SessionService;
import com.codecool.oidascriptplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sessions")
public class SessionController {
    private final SessionService sessionService;
    private final UserService userService;
    private final Log log;

    public SessionController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.log = LogFactory.getLog(SessionController.class);
    }

    @GetMapping
    public User getSession(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Not authenticated");
        }
        return userService.findUserByUsername(authentication.getName());
    }

    @PostMapping
    public User createSession(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InsufficientAuthenticationException("Not authenticated");
        }
        return userService.findUserByUsername(authentication.getName());
    }
}
