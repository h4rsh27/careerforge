package com.careerforge.repository;

import com.careerforge.entity.InterviewSession;
import com.careerforge.entity.JobRole;
import com.careerforge.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewSessionRepository
        extends JpaRepository<InterviewSession, Long> {

    List<InterviewSession> findByJobRole(
            JobRole jobRole);

    Optional<InterviewSession> findByIdAndProfile(
            Long id,
            StudentProfile profile);
}