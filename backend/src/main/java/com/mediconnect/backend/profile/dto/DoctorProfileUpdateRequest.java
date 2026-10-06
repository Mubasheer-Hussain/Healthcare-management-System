package com.mediconnect.backend.profile.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorProfileUpdateRequest {

    private String fullName;

    private String specialty;

    private String clinic;

    @Min(value = 0, message = "Experience years cannot be negative")
    private Integer experienceYears;

    @Pattern(regexp = "^(teal|sky|coral|amber)$", message = "Accent must be one of: teal, sky, coral, amber")
    private String accent;

    private String bio;

    private BigDecimal consultationFee;

    private Boolean acceptingNewPatients;
}
