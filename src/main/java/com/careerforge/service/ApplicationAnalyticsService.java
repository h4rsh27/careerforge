package com.careerforge.service;

import com.careerforge.dto.ApplicationAnalyticsResponse;
import com.careerforge.entity.ApplicationStatus;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.JobApplicationRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAnalyticsService {

    private final JobApplicationRepository applicationRepository;
    private final StudentProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ApplicationAnalyticsService(
            JobApplicationRepository applicationRepository,
            StudentProfileRepository profileRepository,
            UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ApplicationAnalyticsResponse getAnalytics(
            Authentication authentication) {

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        StudentProfile profile = profileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student profile not found"));

        long saved =
                applicationRepository
                        .countByProfileAndStatus(
                                profile,
                                ApplicationStatus.SAVED
                        );

        long applied =
                applicationRepository
                        .countByProfileAndStatus(
                                profile,
                                ApplicationStatus.APPLIED
                        );

        long interviews =
                applicationRepository
                        .countByProfileAndStatus(
                                profile,
                                ApplicationStatus.INTERVIEW
                        );

        long offers =
                applicationRepository
                        .countByProfileAndStatus(
                                profile,
                                ApplicationStatus.OFFER
                        );

        long rejected =
                applicationRepository
                        .countByProfileAndStatus(
                                profile,
                                ApplicationStatus.REJECTED
                        );

        long total =
                applicationRepository.countByProfile(profile);

        return new ApplicationAnalyticsResponse(
                total,
                saved,
                applied,
                interviews,
                offers,
                rejected
        );
    }
}