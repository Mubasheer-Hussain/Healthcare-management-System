package com.mediconnect.backend.auth.service;

import com.mediconnect.backend.auth.dto.AuthResponse;
import com.mediconnect.backend.auth.dto.LoginRequest;
import com.mediconnect.backend.auth.dto.RegisterRequest;
import com.mediconnect.backend.auth.entity.Role;
import com.mediconnect.backend.auth.entity.User;
import com.mediconnect.backend.auth.repository.UserRepository;
import com.mediconnect.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles user registration and login business logic.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final com.mediconnect.backend.profile.repository.DoctorProfileRepository doctorProfileRepository;
    private final com.mediconnect.backend.profile.repository.PatientProfileRepository patientProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    // ── Register ──────────────────────────────────────────────────────────

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "An account with email '" + request.getEmail() + "' already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))   // "PATIENT" or "DOCTOR"
                .build();

        userRepository.saveAndFlush(user);

        if (user.getRole() == Role.DOCTOR) {
            doctorProfileRepository.save(com.mediconnect.backend.profile.entity.DoctorProfile.builder()
                    .user(user)
                    .userId(user.getId())
                    .isNewEntity(true)
                    .specialty("General Medicine")
                    .clinic("General Medical Clinic")
                    .experienceYears(1)
                    .rating(new java.math.BigDecimal("5.0"))
                    .reviewCount(0)
                    .accent("teal")
                    .acceptingNewPatients(true)
                    .build());
        } else if (user.getRole() == Role.PATIENT) {
            patientProfileRepository.save(com.mediconnect.backend.profile.entity.PatientProfile.builder()
                    .user(user)
                    .userId(user.getId())
                    .isNewEntity(true)
                    .build());
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());

        return buildAuthResponse(user, token);
    }

    // ── Login ─────────────────────────────────────────────────────────────

    public AuthResponse login(LoginRequest request) {
        // AuthenticationManager validates credentials using CustomUserDetailsService
        // and throws BadCredentialsException on failure (handled by GlobalExceptionHandler)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User disappeared after authentication"));

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());

        return buildAuthResponse(user, token);
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private AuthResponse buildAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .token(token)
                .userId(user.getId().toString())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
