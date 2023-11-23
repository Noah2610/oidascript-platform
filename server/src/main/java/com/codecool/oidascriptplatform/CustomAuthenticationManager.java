package com.codecool.oidascriptplatform;

import com.codecool.oidascriptplatform.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = userService.loadUserByUsername((String) authentication.getPrincipal());

        Object credentials = authentication.getCredentials();

        if (
                credentials != null &&
                passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())
        ) {
            return UsernamePasswordAuthenticationToken.authenticated(user.getUsername(), user.getPassword(), List.of());
        }

        System.out.println("------------------");
        System.out.println(" AuthenticatorManager.authenticate ");
        System.out.println("name: " + authentication.getName());
        System.out.println("principal: " + authentication.getPrincipal());
        System.out.println("credentials: " + authentication.getCredentials().toString());
        System.out.println("user name: " + user.getUsername());
        System.out.println("user pass: " + user.getPassword());
        System.out.println("matches: " + passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()));
        System.out.println("------------------");

        throw new BadCredentialsException("Invalid login");
    }
}
