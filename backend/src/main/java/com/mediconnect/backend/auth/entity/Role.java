package com.mediconnect.backend.auth.entity;

/**
 * Application-level roles stored in the USERS table.
 * Spring Security expects them prefixed with "ROLE_" but we strip that
 * prefix here for clarity; see CustomUserDetailsService for the mapping.
 */
public enum Role {
    PATIENT,
    DOCTOR,
    ADMIN
}
