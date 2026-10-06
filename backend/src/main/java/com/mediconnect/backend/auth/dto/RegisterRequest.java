package com.mediconnect.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request body for POST /api/v1/auth/register
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    /**
     * Role must be exactly "PATIENT" or "DOCTOR".
     * Matches the role selector in the frontend Signup.tsx.
     */
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "PATIENT|DOCTOR", message = "Role must be PATIENT or DOCTOR")
    private String role;
}
