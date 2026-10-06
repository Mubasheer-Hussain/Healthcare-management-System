package com.mediconnect.backend.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Core user account. Patients and doctors both share this table;
 * the {@link Role} field determines which portal they access.
 *
 * <p>Full name, email, hashed password, and role are the only fields
 * stored here. Specialty, clinic, profile photo, etc. live in their
 * respective profile tables (Doctor, PatientProfile) keyed by this entity's id.</p>
 */
@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    /** BCrypt hash — never expose in responses. */
    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
