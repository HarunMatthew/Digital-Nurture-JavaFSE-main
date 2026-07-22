package com.cognizant.spring_learn.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Step 3: Generate token based on the user retrieved from the Authorization header.
 */
@Service
public class JwtService {

    // Demo secret key. In a real application this should come from a secure,
    // externally-configured source (e.g. environment variable / vault), not
    // hardcoded in source.
    private static final String SECRET_KEY = "ThisIsADemoSecretKeyForJWTSigningExerciseOnly123456";

    private static final long EXPIRATION_MS = 1000 * 60 * 20; // 20 minutes

    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
