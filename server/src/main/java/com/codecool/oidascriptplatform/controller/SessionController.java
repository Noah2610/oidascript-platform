package com.codecool.oidascriptplatform.controller;

import com.codecool.oidascriptplatform.UserDetailsImpl;
import com.codecool.oidascriptplatform.controller.data.CreateSessionRequestBody;
import com.codecool.oidascriptplatform.controller.data.CreateSessionResponseBody;
import com.codecool.oidascriptplatform.exception.NotAuthenticatedException;
import com.codecool.oidascriptplatform.service.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<String> getSession(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity("Not authenticated", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(authentication.getPrincipal(), HttpStatus.OK);
    }

    @PostMapping
    public CreateSessionResponseBody createSession(
            HttpServletResponse res,
            @RequestBody
            CreateSessionRequestBody body
    ) {
        Authentication auth = sessionService.createSession(body, res);

        if (auth == null) {
            throw new NotAuthenticatedException("Invalid login");
        }
        return new CreateSessionResponseBody(auth.getName());
    }
}
