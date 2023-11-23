package com.codecool.oidascriptplatform.jwt;

import com.codecool.oidascriptplatform.AuthCookieManager;
import com.codecool.oidascriptplatform.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

// TODO: Refactor authentication logic (create token, validate, store in context) into AuthenticationService?

//@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter /*UsernamePasswordAuthenticationFilter*/ {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final AuthCookieManager cookieManager;

    public JwtAuthenticationFilter(
            RequestMatcher requestMatcher,
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            AuthCookieManager authCookieManager,
            RememberMeServices rememberMe
    ) {
        super(requestMatcher, authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.cookieManager = authCookieManager;
        super.setRememberMeServices(rememberMe);

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
    ) throws IOException, ServletException {
        System.out.println("------------------");
        System.out.println(" AUTHENTICATOR success ");
        System.out.println("------------------");

//        String username = (String) auth.getPrincipal();
//        String password = (String) auth.getCredentials();
//        UserDetails user = new UserDetailsImpl(username, password);
//        String token = jwtEncoder.encode(user);

        // TODO: we might not need to set the cookie here, because the JwtRememberMeServices will do this
        //res.addCookie(cookieManager.newAuthCookie(token));

        //String body = user.getUsername() + " " + token;
        //PrintWriter writer = res.getWriter();
        //writer.write(body);
        //writer.flush();

        super.successfulAuthentication(req, res, chain, auth);
        chain.doFilter(req, res);
    }
}
