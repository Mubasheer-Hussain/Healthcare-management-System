package com.mediconnect.backend.profile.service;

import com.mediconnect.backend.auth.entity.User;
import com.mediconnect.backend.auth.repository.UserRepository;
import com.mediconnect.backend.common.ResourceNotFoundException;
import com.mediconnect.backend.profile.dto.PatientProfileResponse;
import com.mediconnect.backend.profile.dto.PatientProfileUpdateRequest;
import com.mediconnect.backend.profile.entity.PatientProfile;
import com.mediconnect.backend.profile.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientProfileService {

    private final PatientProfileRepository patientProfileRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PatientProfileResponse getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        PatientProfile profile = patientProfileRepository.findById(user.getId())
                .orElseGet(() -> PatientProfile.builder().user(user).build());

        return mapToResponse(user, profile);
    }

    @Transactional(readOnly = true)
    public PatientProfileResponse getProfileById(UUID patientId) {
        PatientProfile profile = patientProfileRepository.findByIdWithUser(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found with id: " + patientId));

        return mapToResponse(profile.getUser(), profile);
    }

    @Transactional
    public PatientProfileResponse updateProfile(String email, PatientProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        PatientProfile profile = patientProfileRepository.findById(user.getId())
                .orElseGet(() -> {
                    PatientProfile newProfile = PatientProfile.builder()
                            .user(user)
                            .userId(user.getId())
                            .build();
                    return patientProfileRepository.save(newProfile);
                });

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName().trim());
            userRepository.save(user);
        }

        if (request.getDateOfBirth() != null) {
            profile.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            profile.setGender(request.getGender().trim());
        }
        if (request.getBloodGroup() != null) {
            profile.setBloodGroup(request.getBloodGroup().trim());
        }
        if (request.getEmergencyContactName() != null) {
            profile.setEmergencyContactName(request.getEmergencyContactName().trim());
        }
        if (request.getEmergencyContactPhone() != null) {
            profile.setEmergencyContactPhone(request.getEmergencyContactPhone().trim());
        }
        if (request.getAllergies() != null) {
            profile.setAllergies(request.getAllergies().trim());
        }

        PatientProfile saved = patientProfileRepository.save(profile);
        return mapToResponse(user, saved);
    }

    private PatientProfileResponse mapToResponse(User user, PatientProfile profile) {
        return PatientProfileResponse.builder()
                .userId(user.getId().toString())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .bloodGroup(profile.getBloodGroup())
                .emergencyContactName(profile.getEmergencyContactName())
                .emergencyContactPhone(profile.getEmergencyContactPhone())
                .allergies(profile.getAllergies())
                .build();
    }
}
