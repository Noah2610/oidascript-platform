package com.codecool.oidascriptplatform.service;

import com.codecool.oidascriptplatform.UserDetailsImpl;
import com.codecool.oidascriptplatform.jwt.JwtAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final AuthenticationManager authenticationManager;

    public SessionService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication createSession(
            UserDetailsImpl login
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword(),
                        login.getAuthorities()
                )
        );

        // TODO

        return authentication;
    }
}
