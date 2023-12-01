package com.codecool.oidascriptplatform.service;

import com.codecool.oidascriptplatform.AuthCookieManager;
import com.codecool.oidascriptplatform.UserDetailsImpl;
import com.codecool.oidascriptplatform.dto.CreateSessionRequestBody;
import com.codecool.oidascriptplatform.jwt.JwtEncoder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final AuthCookieManager cookieManager;

    public SessionService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, AuthCookieManager cookieManager) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.cookieManager = cookieManager;
    }

    public Authentication createSession(
            CreateSessionRequestBody login,
            HttpServletResponse res
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                        // TODO: authorities
                )
        );

        UserDetails user = new UserDetailsImpl(login.getUsername(), login.getPassword());
//        String token = jwtEncoder.encode(user);
        //String body = user.getUsername() + " " + token;
//        res.addCookie(cookieManager.newAuthCookie(token));

        return authentication;
    }
}
