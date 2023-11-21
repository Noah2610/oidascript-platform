package com.codecool.oidascriptplatform.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_PREFIX = "Bearer ";

    private final JwtEncoder jwtEncoder;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder
    ) {
        super(authenticationManager);
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        Optional<String> headerOpt = getAuthHeader(req);
        if (headerOpt.isEmpty()) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken token = getToken(req);

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(req, res);
    }

    @Nullable
    private UsernamePasswordAuthenticationToken getToken(HttpServletRequest req) {
        Optional<String> headerOpt = getAuthHeader(req);
        if (headerOpt.isEmpty()) {
            return null;
        }

        String header = headerOpt.get();

        String user = jwtEncoder
                .decode(header.replace(AUTH_PREFIX, ""))
                .getSubject();

        if (user == null) {
            return null;
        }

        // TODO
        return new UsernamePasswordAuthenticationToken(user, null, List.of());
    }

    private Optional<String> getAuthHeader(HttpServletRequest req) {
        String header = req.getHeader(AUTH_HEADER);
        if (header == null || !header.startsWith(AUTH_PREFIX)) {
            return Optional.empty();
        }
        return Optional.of(header);
    }
}
