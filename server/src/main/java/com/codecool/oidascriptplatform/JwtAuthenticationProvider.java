package com.codecool.oidascriptplatform;

import com.codecool.oidascriptplatform.jwt.JwtEncoder;
import com.codecool.oidascriptplatform.jwt.JwtTokenAuthentication;
import com.codecool.oidascriptplatform.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public JwtAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        String username = jwtEncoder.decode(token).getSubject();

        if (username != null) {
            Authentication authenticatedAuth = JwtTokenAuthentication.authenticated(token, username);
            return authenticatedAuth;
        }

        throw new BadCredentialsException("Invalid login");

//        Object credentials = authentication.getCredentials();

//        if (
//                credentials != null &&
//                passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())
//        ) {
//            return JwtTokenAuthentication.authenticated(jwtEncoder, (String) authentication.getCredentials());
//        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtTokenAuthentication.class);
    }
}
