package com.codecool.oidascriptplatform.jwt;

import com.codecool.oidascriptplatform.AuthCookieManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//    private static final String AUTH_HEADER = "Authorization";
//    private static final String AUTH_PREFIX = "Bearer ";

    private final JwtEncoder jwtEncoder;
    private final AuthCookieManager cookieManager;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            AuthCookieManager authCookieManager) {
        super(authenticationManager);
        this.jwtEncoder = jwtEncoder;
        this.cookieManager = authCookieManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        Optional<UsernamePasswordAuthenticationToken> tokenOpt = getToken(req);
        if (tokenOpt.isEmpty()) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken token = tokenOpt.get();

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(req, res);
    }

    private Optional<UsernamePasswordAuthenticationToken> getToken(HttpServletRequest req) {
        Optional<String> tokenOpt = getAuthCookie(req);
        if (tokenOpt.isEmpty()) {
            return Optional.empty();
        }

        String token = tokenOpt.get();

        String user = jwtEncoder
//                .decode(header.replace(AUTH_PREFIX, ""))
                .decode(token)
                .getSubject();

        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(
                // TODO
                UsernamePasswordAuthenticationToken.unauthenticated(user, null)
        );
    }

    private Optional<String> getAuthCookie(HttpServletRequest req) {
        return cookieManager.getAuthCookie(req.getCookies());
    }

    // TODO: deprecated
//    private Optional<String> getAuthHeader(HttpServletRequest req) {
//        String header = req.getHeader(AUTH_HEADER);
//        if (header == null || !header.startsWith(AUTH_PREFIX)) {
//            return Optional.empty();
//        }
//        return Optional.of(header);
//    }
}
