package com.mediconnect.backend.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientProfileResponse {
    private String userId;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String allergies;
}
