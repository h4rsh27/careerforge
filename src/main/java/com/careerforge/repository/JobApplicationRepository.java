package com.careerforge.repository;

import com.careerforge.entity.ApplicationStatus;
import com.careerforge.entity.JobApplication;
import com.careerforge.entity.JobListing;
import com.careerforge.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByProfile(StudentProfile profile);

    Optional<JobApplication> findByIdAndProfile(
            Long id,
            StudentProfile profile
    );

    boolean existsByProfileAndJobListing(
            StudentProfile profile,
            JobListing jobListing
    );

    long countByStatus(ApplicationStatus status);

    long countByProfile(StudentProfile profile);

    long countByProfileAndStatus(
            StudentProfile profile,
            ApplicationStatus status
    );
}