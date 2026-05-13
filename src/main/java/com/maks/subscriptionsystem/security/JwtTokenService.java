package com.maks.subscriptionsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenService {
    @Value("${jwt.access.secret}")
    private String accessSecret;
    @Value("${jwt.access.validity-time}")
    private Duration jwtAccessLifeTime;

    public String generateAccessToken(UserDetails userDetails) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtAccessLifeTime.toMillis());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(accessSecret.getBytes()))
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getAllClaimsFromAccessToken(token).getExpiration();
        return expiration.before(new Date());
    }

    public String getUsername(String token) {
        return getAllClaimsFromAccessToken(token).getSubject();
    }

    private Claims getAllClaimsFromAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
