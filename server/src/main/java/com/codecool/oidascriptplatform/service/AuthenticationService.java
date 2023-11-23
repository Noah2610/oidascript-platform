package com.codecool.oidascriptplatform.service;

import com.codecool.oidascriptplatform.jwt.JwtEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

// TODO

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    public Authentication attemptAuthentication(Authentication authentication) {
        Authentication auth;

        try {
            auth = authenticationManager.authenticate(authentication);
        } catch (AuthenticationException ex) {
            return authentication;
        }

        return auth;
    }

//    private successfulAuthentication() {
//
//    }
}
