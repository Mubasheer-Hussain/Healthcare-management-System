package com.mediconnect.backend.profile.controller;

import com.mediconnect.backend.common.ApiResponse;
import com.mediconnect.backend.profile.dto.DoctorProfileUpdateRequest;
import com.mediconnect.backend.profile.dto.DoctorResponse;
import com.mediconnect.backend.profile.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "Doctor discovery, directory search, and profile management")
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "Search and filter doctors", description = "Filter by specialty or search by name, specialty, or clinic")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> searchDoctors(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String specialty) {
        List<DoctorResponse> doctors = doctorService.searchDoctors(query, specialty);
        return ResponseEntity.ok(ApiResponse.ok("Doctors retrieved successfully", doctors));
    }

    @Operation(summary = "Get list of available specialties")
    @GetMapping("/specialties")
    public ResponseEntity<ApiResponse<List<String>>> getSpecialties() {
        List<String> specialties = doctorService.getAllSpecialties();
        return ResponseEntity.ok(ApiResponse.ok("Specialties retrieved successfully", specialties));
    }

    @Operation(summary = "Get doctor details by UUID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(@PathVariable UUID id) {
        DoctorResponse doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(ApiResponse.ok("Doctor retrieved successfully", doctor));
    }

    @Operation(summary = "Update current doctor profile", description = "Requires DOCTOR role")
    @SecurityRequirement(name = "BearerAuth")
    @PutMapping("/me")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateMyProfile(
            @Valid @RequestBody DoctorProfileUpdateRequest request,
            Principal principal) {
        DoctorResponse updated = doctorService.updateDoctorProfile(principal.getName(), request);
        return ResponseEntity.ok(ApiResponse.ok("Doctor profile updated successfully", updated));
    }
}
