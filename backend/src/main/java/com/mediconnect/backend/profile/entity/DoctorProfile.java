package com.mediconnect.backend.profile.entity;

import com.mediconnect.backend.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Extended profile for users with DOCTOR role.
 * Keyed one-to-one on the {@link User} PK so both share the same UUID.
 */
@Entity
@Table(name = "doctor_profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorProfile implements Persistable<UUID> {

    /** Shares its PK with the parent User row */
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String specialty;

    @Column(nullable = false, length = 150)
    private String clinic;

    @Column(nullable = false)
    private int experienceYears;

    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(nullable = false)
    private int reviewCount;

    @Column(nullable = false, length = 10)
    private String accent;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(nullable = false)
    @Builder.Default
    private boolean acceptingNewPatients = true;

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
