package com.ecommerce.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component

public class JwtUtil {
    @Value("${app.jwt.expiration-ms}")
    private Long tokenExpiration;

    @Value("${app.jwt.secret.key}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getClaims(String token){
        return Jwts.parser().verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token){
        try {
            Claims claims=getClaims(token);
            return true;
        }catch (Exception r){
            r.printStackTrace();
            return false;
        }
    }

    public String getUserIdFromToken(String token){
        return getClaims(token).getSubject();
    }
    public String getRole(String token){
        return getClaims(token).get("role", String.class);
    }
}
