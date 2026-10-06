package com.mediconnect.backend.profile.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientProfileUpdateRequest {

    private String fullName;

    private LocalDate dateOfBirth;

    @Size(max = 10, message = "Gender must be at most 10 characters")
    private String gender;

    @Size(max = 5, message = "Blood group must be at most 5 characters")
    private String bloodGroup;

    @Size(max = 120, message = "Emergency contact name must be at most 120 characters")
    private String emergencyContactName;

    @Size(max = 20, message = "Emergency contact phone must be at most 20 characters")
    private String emergencyContactPhone;

    private String allergies;
}
