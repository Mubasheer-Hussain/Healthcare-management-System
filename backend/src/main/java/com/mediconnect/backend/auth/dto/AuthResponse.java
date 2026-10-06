package com.mediconnect.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Returned by both /register and /login endpoints.
 * The frontend should store the {@code token} in localStorage
 * and attach it as "Authorization: Bearer <token>" on subsequent requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;

    /** User metadata so the frontend can route without an extra /me call */
    private String userId;
    private String fullName;
    private String email;
    private String role;    // "PATIENT" or "DOCTOR"
}
