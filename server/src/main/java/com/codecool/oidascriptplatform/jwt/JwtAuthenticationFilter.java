package com.codecool.oidascriptplatform.jwt;

import com.codecool.oidascriptplatform.AuthCookieManager;
import com.codecool.oidascriptplatform.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

// TODO: Refactor authentication logic (create token, validate, store in context) into AuthenticationService?

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final AuthCookieManager cookieManager;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            AuthCookieManager authCookieManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.cookieManager = authCookieManager;

        // TODO: do we need this?
        // setFilterProcessesUrl("/sessions");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws AuthenticationException {
        UserDetails user;

        try {
            user = new ObjectMapper()
                .readValue(req.getInputStream(), UserDetailsImpl.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth
    ) throws IOException {
        System.out.println("-------------------------");
        System.out.println("SUCCESSFUL AUTHENTICATION");
        System.out.println("-------------------------");

        String username = (String) auth.getPrincipal();
        String password = (String) auth.getCredentials();
        UserDetails user = new UserDetailsImpl(username, password);
        String token = jwtEncoder.encode(user);

        res.addCookie(cookieManager.newAuthCookie(token));

        String body = user.getUsername() + " " + token;
        res.getWriter().write(body);
        res.getWriter().flush();
    }
}
