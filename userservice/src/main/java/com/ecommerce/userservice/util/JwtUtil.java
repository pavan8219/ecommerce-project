package com.ecommerce.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {
    @Value("${app.jwt.secret.key}")
    private String jwtSecretKey;
    @Value("${app.jwt.expiration-ms}")
    private long expiration;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId,String email,String role){
        long now=System.currentTimeMillis();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email",email)
                .claim("role",role)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims=Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }
}
