package com.codecool.oidascriptplatform.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtEncoderImpl implements JwtEncoder {
    private final String signSecret;
    private final int expirationTimeMs;

    public JwtEncoderImpl(
            @Value("${auth.token.sign_secret}") String signSecret,
            @Value("${auth.token.expiration_time_ms}") int expirationTimeMs
    ) {
        this.signSecret = signSecret;
        this.expirationTimeMs = expirationTimeMs;
    }

    @Override
    public String encode(UserDetails user) {
        return JWT.create()
                .withSubject(user.getUsername())
                // TODO: is this bad?
                .withClaim("credentials", user.getPassword())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTimeMs))
                .sign(Algorithm.HMAC512(signSecret));
    }

    @Override
    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC512(signSecret))
                .build()
                .verify(token);
    }
}
