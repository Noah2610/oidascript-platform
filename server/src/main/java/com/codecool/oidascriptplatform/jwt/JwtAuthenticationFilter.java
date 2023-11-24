package com.codecool.oidascriptplatform.jwt;

import com.codecool.oidascriptplatform.AuthCookieManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

// TODO: Refactor authentication logic (create token, validate, store in context) into AuthenticationService?

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthCookieManager cookieManager;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(
            AuthCookieManager cookieManager,
            AuthenticationManager authenticationManager
    ) {
        super();
        this.cookieManager = cookieManager;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        Optional<String> cookieOpt = cookieManager.getAuthCookie(request.getCookies());
        if (cookieOpt.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        String cookie = cookieOpt.get();
        Authentication auth = JwtTokenAuthentication.unauthenticated(cookie);

        Authentication authenticatedAuth;

        try {
            authenticatedAuth = authenticationManager.authenticate(auth);
        } catch (AuthenticationException ex) {
            response.addCookie(cookieManager.clearAuthCookie());
            throw ex;
        }

        response.addCookie(
                cookieManager.newAuthCookie((String) authenticatedAuth.getCredentials())
        );

        // TODO: manually put authentication into securityContext?
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticatedAuth);

        chain.doFilter(request, response);
    }
}
