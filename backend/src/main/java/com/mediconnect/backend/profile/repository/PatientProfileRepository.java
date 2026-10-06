package com.mediconnect.backend.profile.repository;

import com.mediconnect.backend.profile.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, UUID> {

    @Query("SELECT p FROM PatientProfile p JOIN FETCH p.user u WHERE p.userId = :userId")
    Optional<PatientProfile> findByIdWithUser(@Param("userId") UUID userId);

    @Query("SELECT p FROM PatientProfile p JOIN FETCH p.user u WHERE u.email = :email")
    Optional<PatientProfile> findByUserEmail(@Param("email") String email);
}
