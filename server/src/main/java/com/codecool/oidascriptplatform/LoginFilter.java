package com.codecool.oidascriptplatform;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.codecool.oidascriptplatform.jwt.JwtEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtEncoder jwtEncoder;
    private final AuthCookieManager cookieManager;
    private final ObjectMapper objectMapper;

    protected LoginFilter(
            RequestMatcher requestMatcher,
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            AuthCookieManager cookieManager
        ) {
        super(requestMatcher, authenticationManager);
        this.jwtEncoder = jwtEncoder;
        this.cookieManager = cookieManager;

        // TODO: dependency
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        UserDetails user;

        try {
            user = objectMapper.readValue(request.getInputStream(), UserDetailsImpl.class);
        } catch (IOException ex) {
            // TODO?
            throw new InsufficientAuthenticationException(ex.getMessage());
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()

        );

        return this.getAuthenticationManager().authenticate(usernamePasswordAuthentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authentication);

        // TODO: JwtTokenService
        String jwt = jwtEncoder.encode(authentication.getName());
        response.addCookie(cookieManager.newAuthCookie(jwt));

        chain.doFilter(request, response);
    }
}
