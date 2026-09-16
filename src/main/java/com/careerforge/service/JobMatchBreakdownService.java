package com.careerforge.service;

import com.careerforge.dto.JobMatchBreakdownResponse;
import com.careerforge.dto.SkillGapResponse;
import com.careerforge.dto.SkillGapSkillResponse;
import com.careerforge.entity.JobListing;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.JobListingRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobMatchBreakdownService {

    private final JobListingRepository jobListingRepository;
    private final SkillGapService skillGapService;
    private final com.careerforge.repository.StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public JobMatchBreakdownService(
            JobListingRepository jobListingRepository,
            SkillGapService skillGapService,
            StudentProfileRepository studentProfileRepository,
            UserRepository userRepository) {

        this.jobListingRepository = jobListingRepository;
        this.skillGapService = skillGapService;
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
    }

    public JobMatchBreakdownResponse getBreakdown(
            Long jobListingId,
            Authentication authentication) {

        // Get logged-in user
        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        // Get student's profile
        StudentProfile profile =
                studentProfileRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student profile not found"));

        // Get job listing
        JobListing jobListing =
                jobListingRepository
                        .findById(jobListingId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job listing not found"));

        /*
         * IMPORTANT:
         * SkillGapService expects:
         *
         * analyzeSkillGap(String email, Long jobRoleId)
         *
         * Therefore:
         * authentication.getName() -> String email
         * jobListing.getJobRole().getId() -> Long jobRoleId
         */
        SkillGapResponse skillGap =
                skillGapService.analyzeSkillGap(
                        authentication.getName(),
                        jobListing.getJobRole().getId()
                );

        // Skill match percentage
        double skillMatchPercentage =
                skillGap.getReadinessPercentage();

        // Location bonus
        double locationBonus = 0;

        if (profile.getLocation() != null
                && jobListing.getLocation() != null
                && profile.getLocation()
                .equalsIgnoreCase(
                        jobListing.getLocation())) {

            locationBonus = 5;
        }

        /*
         * Experience bonus is currently 0.
         *
         * Graduation year cannot reliably represent
         * professional work experience.
         */
        double experienceBonus = 0;

        // Final match percentage
        double finalMatchPercentage =
                Math.min(
                        100,
                        skillMatchPercentage
                                + locationBonus
                                + experienceBonus
                );

        // Matched skills
        List<String> matchedSkills =
                skillGap.getMatchedSkills()
                        .stream()
                        .map(SkillGapSkillResponse::getSkillName)
                        .toList();

        // Missing skills
        List<String> missingSkills =
                skillGap.getMissingSkills()
                        .stream()
                        .map(SkillGapSkillResponse::getSkillName)
                        .toList();

        // Return breakdown
        return new JobMatchBreakdownResponse(
                jobListing.getId(),
                jobListing.getTitle(),
                jobListing.getCompany().getName(),
                skillMatchPercentage,
                locationBonus,
                experienceBonus,
                Math.round(
                        finalMatchPercentage * 100.0
                ) / 100.0,
                matchedSkills,
                missingSkills
        );
    }
}