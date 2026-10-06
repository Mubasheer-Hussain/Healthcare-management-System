package com.mediconnect.backend.profile.service;

import com.mediconnect.backend.auth.entity.User;
import com.mediconnect.backend.auth.repository.UserRepository;
import com.mediconnect.backend.common.ResourceNotFoundException;
import com.mediconnect.backend.profile.dto.DoctorProfileUpdateRequest;
import com.mediconnect.backend.profile.dto.DoctorResponse;
import com.mediconnect.backend.profile.entity.DoctorProfile;
import com.mediconnect.backend.profile.repository.DoctorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<DoctorResponse> searchDoctors(String query, String specialty) {
        String cleanQuery = (query != null && !query.isBlank()) ? query.trim() : null;
        String cleanSpecialty = (specialty != null && !specialty.isBlank() && !specialty.equalsIgnoreCase("All")) 
                ? specialty.trim() 
                : null;

        return doctorProfileRepository.searchDoctors(cleanQuery, cleanSpecialty)
                .stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(UUID doctorId) {
        DoctorProfile profile = doctorProfileRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));
        return mapToDoctorResponse(profile);
    }

    @Transactional(readOnly = true)
    public DoctorResponse getDoctorByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        DoctorProfile profile = doctorProfileRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found for user: " + email));

        return mapToDoctorResponse(profile);
    }

    @Transactional(readOnly = true)
    public List<String> getAllSpecialties() {
        List<String> distinct = doctorProfileRepository.findDistinctSpecialties();
        List<String> result = new ArrayList<>();
        result.add("All");
        for (String s : distinct) {
            if (s != null && !s.equalsIgnoreCase("All") && !result.contains(s)) {
                result.add(s);
            }
        }
        return result;
    }

    @Transactional
    public DoctorResponse updateDoctorProfile(String email, DoctorProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        DoctorProfile profile = doctorProfileRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found for user: " + email));

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName().trim());
            userRepository.save(user);
        }

        if (request.getSpecialty() != null && !request.getSpecialty().isBlank()) {
            profile.setSpecialty(request.getSpecialty().trim());
        }
        if (request.getClinic() != null && !request.getClinic().isBlank()) {
            profile.setClinic(request.getClinic().trim());
        }
        if (request.getExperienceYears() != null) {
            profile.setExperienceYears(request.getExperienceYears());
        }
        if (request.getAccent() != null && !request.getAccent().isBlank()) {
            profile.setAccent(request.getAccent().trim());
        }
        if (request.getBio() != null) {
            profile.setBio(request.getBio().trim());
        }
        if (request.getConsultationFee() != null) {
            profile.setConsultationFee(request.getConsultationFee());
        }
        if (request.getAcceptingNewPatients() != null) {
            profile.setAcceptingNewPatients(request.getAcceptingNewPatients());
        }

        DoctorProfile saved = doctorProfileRepository.save(profile);
        return mapToDoctorResponse(saved);
    }

    // ── Mapping & Formatting ───────────────────────────────────────────────

    public DoctorResponse mapToDoctorResponse(DoctorProfile profile) {
        User user = profile.getUser();
        String fullName = user != null ? user.getFullName() : "Doctor";
        String email = user != null ? user.getEmail() : "";
        UUID userId = profile.getUserId() != null ? profile.getUserId() : (user != null ? user.getId() : null);

        return DoctorResponse.builder()
                .id(userId != null ? userId.toString() : "")
                .name(fullName)
                .email(email)
                .specialty(profile.getSpecialty())
                .clinic(profile.getClinic())
                .experienceYears(profile.getExperienceYears())
                .rating(profile.getRating())
                .reviewCount(profile.getReviewCount())
                .nextSlot("Today, 2:30 PM") // Default availability slot
                .initials(computeInitials(fullName))
                .accent(profile.getAccent())
                .bio(profile.getBio())
                .consultationFee(profile.getConsultationFee())
                .acceptingNewPatients(profile.isAcceptingNewPatients())
                .build();
    }

    private String computeInitials(String fullName) {
        if (fullName == null || fullName.isBlank()) return "DR";
        String cleaned = fullName.replace("Dr.", "").replace("Dr ", "").trim();
        String[] parts = cleaned.split("\\s+");
        if (parts.length >= 2) {
            return ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase();
        } else if (parts.length == 1 && !parts[0].isEmpty()) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        }
        return "DR";
    }
}
