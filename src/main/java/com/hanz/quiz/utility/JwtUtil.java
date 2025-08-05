package com.hanz.quiz.utility;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    // Ensure this is a secure, long key (256 bits)
    private static final String SECRET_KEY_STRING = "hanz#1123122@@@323242323121qsadsdsd";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    public String generateToken(String username, List<String> roles) {
        String token = Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SECRET_KEY , SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Generated Token: " + token); // Log the generated token
        return token;
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token); return true;
        } catch (Exception e) { return false; }
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractClaims(token);
        return claims.get("roles", List.class);
    }
}