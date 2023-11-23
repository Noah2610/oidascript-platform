package com.codecool.oidascriptplatform.jwt;

import com.codecool.oidascriptplatform.AuthCookieManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.server.Cookie;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;
import java.io.IOException;

// TODO: Refactor authentication logic (create token, validate, store in context) into AuthenticationService?

//@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthCookieManager cookieManager;
    private final ProviderManager providerManager;

    public JwtAuthenticationFilter(AuthCookieManager cookieManager, ProviderManager providerManager) {
        super();
        this.cookieManager = cookieManager;
        this.providerManager = providerManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Optional<String> cookieOpt = cookieManager.getAuthCookie(request.getCookies());
        if (cookieOpt.isEmpty()) {
            return;
        }

        String cookie = cookieOpt.get();

        Authentication auth = new JwtTokenAuthentication(cookie);




    }


}
