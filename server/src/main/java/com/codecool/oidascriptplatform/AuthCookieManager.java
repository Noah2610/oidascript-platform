package com.codecool.oidascriptplatform;

import jakarta.servlet.http.Cookie;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthCookieManager {
    private static final String COOKIE_NAME = "auth";

    public Optional<String> getAuthCookie(@Nullable Cookie[] cookies) {
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .map(cookie -> cookie.getValue())
                .findFirst();
    }

    public Cookie newAuthCookie(String value) {
        return new Cookie(COOKIE_NAME, value);
    }
}
