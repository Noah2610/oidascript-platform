package com.codecool.oidascriptplatform;

import com.codecool.oidascriptplatform.model.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final String username;
    @JsonAlias({ "password" })
    private final String passwordHash;

    public UserDetailsImpl(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }
    public UserDetailsImpl(User user) {
        this.username = user.getUsername();
        this.passwordHash = user.getPasswordHash();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
