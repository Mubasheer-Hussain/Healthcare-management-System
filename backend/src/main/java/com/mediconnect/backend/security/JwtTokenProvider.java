package com.mediconnect.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Creates and validates JWT tokens using HMAC-SHA256.
 *
 * <p>Configuration (application.yml):
 * <pre>
 * app.jwt.secret          – HS256 signing secret (≥ 32 chars)
 * app.jwt.expiration-ms   – Token TTL in milliseconds (default 24 h)
 * </pre>
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // ── Token Generation ─────────────────────────────────────────────────

    /**
     * Generates a JWT token with the user's email as the subject and
     * their role as a custom claim — so the frontend can extract role
     * from the token payload without an extra API call.
     */
    public String generateToken(String email, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey)
                .compact();
    }

    // ── Token Parsing ─────────────────────────────────────────────────────

    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // ── Token Validation ──────────────────────────────────────────────────

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn("JWT validation failed: {}", ex.getMessage());
            return false;
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
