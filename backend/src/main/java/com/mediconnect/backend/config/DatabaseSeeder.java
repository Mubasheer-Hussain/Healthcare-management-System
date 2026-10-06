package com.mediconnect.backend.config;

import com.mediconnect.backend.auth.entity.Role;
import com.mediconnect.backend.auth.entity.User;
import com.mediconnect.backend.auth.repository.UserRepository;
import com.mediconnect.backend.profile.entity.DoctorProfile;
import com.mediconnect.backend.profile.entity.PatientProfile;
import com.mediconnect.backend.profile.repository.DoctorProfileRepository;
import com.mediconnect.backend.profile.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Seeds initial demo accounts and doctor profiles matching the frontend mock data.
 * Safe to run repeatedly; only runs if the database has no doctor profiles.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (doctorProfileRepository.count() > 0) {
            log.info("Database already seeded with doctors. Skipping seeder.");
            return;
        }

        log.info("Seeding initial doctor directory and demo patient accounts...");
        String defaultPasswordHash = passwordEncoder.encode("password123");

        // ── Seed Doctors ───────────────────────────────────────────────────
        seedDoctor(
                "Dr. Sarah Johnson",
                "sarah.johnson@mediconnect.com",
                defaultPasswordHash,
                "Cardiology",
                "NYC Medical Center",
                15,
                new BigDecimal("4.9"),
                230,
                "teal",
                "Board-certified cardiologist specializing in preventive cardiology and heart rhythm disorders.",
                new BigDecimal("150.00")
        );

        seedDoctor(
                "Dr. Mark Lee",
                "mark.lee@mediconnect.com",
                defaultPasswordHash,
                "Cardiology",
                "NYC Medical Center",
                11,
                new BigDecimal("4.9"),
                230,
                "sky",
                "Expert in interventional cardiology, cardiovascular imaging, and comprehensive cardiac rehabilitation.",
                new BigDecimal("140.00")
        );

        seedDoctor(
                "Dr. Priya Nair",
                "priya.nair@mediconnect.com",
                defaultPasswordHash,
                "Pediatrics",
                "Riverside Clinic",
                9,
                new BigDecimal("4.8"),
                142,
                "coral",
                "Compassionate pediatrician committed to whole-child wellness from infancy through adolescence.",
                new BigDecimal("110.00")
        );

        seedDoctor(
                "Dr. Elena Ruiz",
                "elena.ruiz@mediconnect.com",
                defaultPasswordHash,
                "Dermatology",
                "Westside Health",
                7,
                new BigDecimal("4.7"),
                98,
                "amber",
                "Specialist in clinical and cosmetic dermatology, skin cancer screenings, and laser therapies.",
                new BigDecimal("125.00")
        );

        seedDoctor(
                "Dr. Tom Whitfield",
                "tom.whitfield@mediconnect.com",
                defaultPasswordHash,
                "Orthopedics",
                "NYC Medical Center",
                18,
                new BigDecimal("4.9"),
                310,
                "sky",
                "Orthopedic surgeon specializing in sports injuries, arthroscopy, and minimally invasive joint replacement.",
                new BigDecimal("175.00")
        );

        seedDoctor(
                "Dr. Amara Obi",
                "amara.obi@mediconnect.com",
                defaultPasswordHash,
                "Neurology",
                "Riverside Clinic",
                13,
                new BigDecimal("4.8"),
                176,
                "teal",
                "Consultant neurologist specializing in headache disorders, epilepsy, and neurodegenerative care.",
                new BigDecimal("160.00")
        );

        // ── Seed Demo Patient ──────────────────────────────────────────────
        seedPatient(
                "Alex Rivera",
                "alex.rivera@mediconnect.com",
                defaultPasswordHash,
                LocalDate.of(1994, 5, 15),
                "Male",
                "O+",
                "Maria Rivera",
                "+1-555-0199",
                "Penicillin, Peanuts"
        );

        log.info("Database seeding completed successfully. 6 doctors and 1 patient seeded.");
    }

    private void seedDoctor(
            String name,
            String email,
            String passwordHash,
            String specialty,
            String clinic,
            int experienceYears,
            BigDecimal rating,
            int reviewCount,
            String accent,
            String bio,
            BigDecimal consultationFee) {

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = userRepository.saveAndFlush(User.builder()
                    .fullName(name)
                    .email(email)
                    .passwordHash(passwordHash)
                    .role(Role.DOCTOR)
                    .build());
        }

        DoctorProfile profile = DoctorProfile.builder()
                .user(user)
                .userId(user.getId())
                .isNewEntity(true)
                .specialty(specialty)
                .clinic(clinic)
                .experienceYears(experienceYears)
                .rating(rating)
                .reviewCount(reviewCount)
                .accent(accent)
                .bio(bio)
                .consultationFee(consultationFee)
                .acceptingNewPatients(true)
                .build();

        doctorProfileRepository.save(profile);
    }

    private void seedPatient(
            String name,
            String email,
            String passwordHash,
            LocalDate dob,
            String gender,
            String bloodGroup,
            String emergencyContactName,
            String emergencyContactPhone,
            String allergies) {

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = userRepository.saveAndFlush(User.builder()
                    .fullName(name)
                    .email(email)
                    .passwordHash(passwordHash)
                    .role(Role.PATIENT)
                    .build());
        }

        PatientProfile profile = PatientProfile.builder()
                .user(user)
                .userId(user.getId())
                .isNewEntity(true)
                .dateOfBirth(dob)
                .gender(gender)
                .bloodGroup(bloodGroup)
                .emergencyContactName(emergencyContactName)
                .emergencyContactPhone(emergencyContactPhone)
                .allergies(allergies)
                .build();

        patientProfileRepository.save(profile);
    }
}
