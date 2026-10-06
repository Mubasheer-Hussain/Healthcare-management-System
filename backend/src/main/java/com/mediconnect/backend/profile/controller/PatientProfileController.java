package com.mediconnect.backend.profile.controller;

import com.mediconnect.backend.common.ApiResponse;
import com.mediconnect.backend.profile.dto.PatientProfileResponse;
import com.mediconnect.backend.profile.dto.PatientProfileUpdateRequest;
import com.mediconnect.backend.profile.service.PatientProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Patient profile management")
@SecurityRequirement(name = "BearerAuth")
public class PatientProfileController {

    private final PatientProfileService patientProfileService;

    @Operation(summary = "Get current patient profile", description = "Requires PATIENT role")
    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponse<PatientProfileResponse>> getMyProfile(Principal principal) {
        PatientProfileResponse profile = patientProfileService.getProfileByEmail(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok("Profile retrieved successfully", profile));
    }

    @Operation(summary = "Update current patient profile", description = "Requires PATIENT role")
    @PutMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<ApiResponse<PatientProfileResponse>> updateMyProfile(
            @Valid @RequestBody PatientProfileUpdateRequest request,
            Principal principal) {
        PatientProfileResponse updated = patientProfileService.updateProfile(principal.getName(), request);
        return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully", updated));
    }

    @Operation(summary = "Get patient profile by ID", description = "Accessible by DOCTOR and ADMIN")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<PatientProfileResponse>> getPatientById(@PathVariable UUID id) {
        PatientProfileResponse profile = patientProfileService.getProfileById(id);
        return ResponseEntity.ok(ApiResponse.ok("Patient profile retrieved successfully", profile));
    }
}
