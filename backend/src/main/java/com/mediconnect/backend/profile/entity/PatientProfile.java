package com.mediconnect.backend.profile.entity;

import com.mediconnect.backend.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Extended profile for users with PATIENT role.
 * One-to-one with the {@link User} entity, sharing the same UUID PK.
 */
@Entity
@Table(name = "patient_profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientProfile implements Persistable<UUID> {

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate dateOfBirth;

    @Column(length = 10)
    private String gender;

    /** e.g., "A+", "O-" */
    @Column(length = 5)
    private String bloodGroup;

    /** Emergency contact full name */
    @Column(length = 120)
    private String emergencyContactName;

    /** Emergency contact phone number */
    @Column(length = 20)
    private String emergencyContactPhone;

    /** Short list of known allergies, free-text */
    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Transient
    @Builder.Default
    private boolean isNewEntity = true;

    @Override
    public UUID getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return isNewEntity;
    }

    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNewEntity = false;
    }
}
