package com.codecool.oidascriptplatform.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.codecool.oidascriptplatform.AuthCookieManager;
import com.codecool.oidascriptplatform.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Primary
public class JwtRememberMeServices implements RememberMeServices {
//    private static final String AUTH_HEADER = "Authorization";
//    private static final String AUTH_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final AuthCookieManager cookieManager;

    public JwtRememberMeServices(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            AuthCookieManager authCookieManager
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.cookieManager = authCookieManager;
    }

    @Override
    @Nullable
    public Authentication autoLogin(HttpServletRequest req, HttpServletResponse res) {
        Optional<UsernamePasswordAuthenticationToken> tokenOpt = getToken(req);
        if (tokenOpt.isEmpty()) {
            return null;
        }

        UsernamePasswordAuthenticationToken token = tokenOpt.get();

        Authentication auth;

        try {
            auth = authenticationManager.authenticate(token);
            if (!auth.isAuthenticated()) {
                throw new InsufficientAuthenticationException("Invalid authentication");
            }
        } catch (AuthenticationException ex) {
            System.out.println("------------------");
            System.out.println(" AUTHORATOR fail autoLogin ");
            System.out.println("------------------");

            throw ex;

//            invalidateCookie(res);
//            return null;
        }

        validateCookie(res, auth);
        return auth;
    }

    @Override
    public void loginFail(HttpServletRequest req, HttpServletResponse res) {
        invalidateCookie(res);
    }

    @Override
    public void loginSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        validateCookie(res, auth);
    }

    private void validateCookie(HttpServletResponse res, Authentication auth) {
        UserDetails user = new UserDetailsImpl((String) auth.getPrincipal(), (String) auth.getCredentials());
        String token = jwtEncoder.encode(user);
        Cookie cookie = cookieManager.newAuthCookie(token);
        res.addCookie(cookie);
    }

    private void invalidateCookie(HttpServletResponse res) {
        res.addCookie(cookieManager.clearAuthCookie());
    }

//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest req,
//            HttpServletResponse res,
//            FilterChain chain
//    ) throws IOException, ServletException {
//        Optional<UsernamePasswordAuthenticationToken> tokenOpt = getToken(req);
//        if (tokenOpt.isEmpty()) {
//            chain.doFilter(req, res);
//            return;
//        }
//
//        UsernamePasswordAuthenticationToken token = tokenOpt.get();
//
//        SecurityContextHolder.getContext().setAuthentication(token);
//        chain.doFilter(req, res);
//    }

    private Optional<UsernamePasswordAuthenticationToken> getToken(HttpServletRequest req) {
        Optional<String> tokenOpt = getAuthCookie(req);
        if (tokenOpt.isEmpty()) {
            return Optional.empty();
        }

        String token = tokenOpt.get();

        DecodedJWT decoded = jwtEncoder
//                .decode(header.replace(AUTH_PREFIX, ""))
                .decode(token);

        String username = decoded.getSubject();
        String password = decoded.getClaim("credentials").asString();

        if (username == null || password == null) {
            return Optional.empty();
        }

        return Optional.of(
                // TODO
                UsernamePasswordAuthenticationToken.unauthenticated(username, password)
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
