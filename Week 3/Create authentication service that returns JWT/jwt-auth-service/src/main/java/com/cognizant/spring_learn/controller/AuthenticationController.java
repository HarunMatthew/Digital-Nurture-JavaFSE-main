package com.cognizant.spring_learn.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.spring_learn.service.JwtService;

/**
 * Step 1: Authentication controller, exposed at GET /authenticate (permitted
 * in SecurityConfig so this method sees the raw Authorization header).
 */
@RestController
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    // Demo credentials matching the curl example: curl -s -u user:pwd ...
    // In a real application, validate against a UserDetailsService/database instead.
    private static final String VALID_USERNAME = "user";
    private static final String VALID_PASSWORD = "pwd";

    private final JwtService jwtService;

    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // GET http://localhost:8090/authenticate
    // curl -s -u user:pwd http://localhost:8090/authenticate
    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        logger.info("authenticate() - start");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            logger.info("authenticate() - end, missing or malformed Authorization header");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Missing or invalid Authorization header"));
        }

        // Step 2: Read Authorization header and decode the username and password
        String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
        String decoded = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

        int separatorIndex = decoded.indexOf(':');
        String username = separatorIndex >= 0 ? decoded.substring(0, separatorIndex) : decoded;
        String password = separatorIndex >= 0 ? decoded.substring(separatorIndex + 1) : "";

        if (!VALID_USERNAME.equals(username) || !VALID_PASSWORD.equals(password)) {
            logger.info("authenticate() - end, invalid credentials for user={}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        // Step 3: Generate token based on the user retrieved above
        String token = jwtService.generateToken(username);

        logger.info("authenticate() - end, token issued for user={}", username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
