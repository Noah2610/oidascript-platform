package com.codecool.oidascriptplatform.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

public interface JwtEncoder {
    String encode(String username);
    DecodedJWT decode(String token);
}
