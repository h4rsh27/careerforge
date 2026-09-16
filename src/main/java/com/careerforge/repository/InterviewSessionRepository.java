package com.careerforge.repository;

import com.careerforge.entity.InterviewSession;
import com.careerforge.entity.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewSessionRepository
        extends JpaRepository<InterviewSession, Long> {

    List<InterviewSession> findByJobRole(JobRole jobRole);
}