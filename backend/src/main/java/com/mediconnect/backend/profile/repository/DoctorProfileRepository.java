package com.mediconnect.backend.profile.repository;

import com.mediconnect.backend.profile.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, UUID> {

    @Query("SELECT DISTINCT d.specialty FROM DoctorProfile d ORDER BY d.specialty ASC")
    List<String> findDistinctSpecialties();

    @Query("SELECT d FROM DoctorProfile d JOIN FETCH d.user u " +
           "WHERE (:specialty IS NULL OR :specialty = '' OR :specialty = 'All' OR LOWER(d.specialty) = LOWER(:specialty)) " +
           "AND (:query IS NULL OR :query = '' OR " +
           "     LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "     LOWER(d.specialty) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "     LOWER(d.clinic) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<DoctorProfile> searchDoctors(@Param("query") String query, @Param("specialty") String specialty);
}
