package com.mediconnect.backend.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Representation of a Doctor returned to frontend clients.
 * Matches the frontend Doctor interface in src/types/index.ts:
 * id, name, specialty, clinic, experienceYears, rating, reviewCount, nextSlot, initials, accent.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    private String id;
    private String name;
    private String email;
    private String specialty;
    private String clinic;
    private int experienceYears;
    private BigDecimal rating;
    private int reviewCount;
    private String nextSlot;
    private String initials;
    private String accent;
    private String bio;
    private BigDecimal consultationFee;
    private boolean acceptingNewPatients;
}
