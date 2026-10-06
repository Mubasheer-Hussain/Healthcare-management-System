package com.mediconnect.backend.auth.controller;

import com.mediconnect.backend.auth.dto.AuthResponse;
import com.mediconnect.backend.auth.dto.LoginRequest;
import com.mediconnect.backend.auth.dto.RegisterRequest;
import com.mediconnect.backend.auth.entity.User;
import com.mediconnect.backend.auth.repository.UserRepository;
import com.mediconnect.backend.common.ApiResponse;
import com.mediconnect.backend.common.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.mediconnect.backend.auth.service.AuthService;

import java.util.Map;

/**
 * Authentication endpoints — all publicly accessible (no JWT required).
 *
 * POST /api/v1/auth/register  – Create account, get JWT
 * POST /api/v1/auth/login     – Authenticate, get JWT
 * GET  /api/v1/auth/me        – Get current user profile (JWT required)
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login and user profile endpoints")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    // ── Register ──────────────────────────────────────────────────────────

    @PostMapping("/register")
    @Operation(summary = "Create a new PATIENT or DOCTOR account")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        AuthResponse authResponse = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Account created successfully", authResponse));
    }

    // ── Login ─────────────────────────────────────────────────────────────

    @PostMapping("/login")
    @Operation(summary = "Authenticate with email and password, receive JWT token")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse authResponse = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.ok("Login successful", authResponse));
    }

    // ── Me ────────────────────────────────────────────────────────────────

    @GetMapping("/me")
    @Operation(summary = "Get currently authenticated user profile (requires Bearer token)")
    public ResponseEntity<ApiResponse<Map<String, String>>> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userDetails.getUsername()));

        Map<String, String> profile = Map.of(
                "userId",   user.getId().toString(),
                "fullName", user.getFullName(),
                "email",    user.getEmail(),
                "role",     user.getRole().name()
        );

        return ResponseEntity.ok(ApiResponse.ok("Profile retrieved", profile));
    }
}
