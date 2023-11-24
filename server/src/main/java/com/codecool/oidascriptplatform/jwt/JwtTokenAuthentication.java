package com.codecool.oidascriptplatform.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtTokenAuthentication implements Authentication {
    private final String token;
    @Nullable
    private final String username;

    private boolean isAuthenticated;

    private JwtTokenAuthentication(String token, String username, boolean isAuthenticated) {
        this.token = token;
        this.username = username;
        this.isAuthenticated = isAuthenticated;
    }
    public JwtTokenAuthentication(String token, String username) {
        this(token, username, false);
    }
    public JwtTokenAuthentication(String token) {
        this(token, null, false);
    }

    public static JwtTokenAuthentication authenticated(String token, String username) {
        return new JwtTokenAuthentication(token, username, true);
    }
    public static JwtTokenAuthentication unauthenticated(String token) {
        return new JwtTokenAuthentication(token, null, false);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    @Nullable
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return (String) this.getPrincipal();
    }
}
