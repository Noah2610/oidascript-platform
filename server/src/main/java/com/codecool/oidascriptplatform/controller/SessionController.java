package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.AuthCookieManager;
import com.codecool.oidascriptplatform.model.User;
import com.codecool.oidascriptplatform.service.SessionService;
import com.codecool.oidascriptplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
    private final AuthCookieManager cookieManager;

    public SessionController(SessionService sessionService, UserService userService, AuthCookieManager cookieManager) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.cookieManager = cookieManager;
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

    @DeleteMapping
    public ResponseEntity<String> deleteSession(Authentication authentication, HttpServletResponse response) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity("Not logged in", HttpStatus.OK);
        }

        response.addCookie(cookieManager.clearAuthCookie());

        return new ResponseEntity("Logged out", HttpStatus.OK);
    }
}
