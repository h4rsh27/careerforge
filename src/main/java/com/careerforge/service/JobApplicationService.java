package com.careerforge.service;

import com.careerforge.dto.JobApplicationRequest;
import com.careerforge.dto.JobApplicationResponse;
import com.careerforge.entity.ApplicationStatus;
import com.careerforge.entity.JobApplication;
import com.careerforge.entity.JobListing;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.repository.JobApplicationRepository;
import com.careerforge.repository.JobListingRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobListingRepository jobListingRepository;
    private final StudentProfileRepository profileRepository;
    private final UserRepository userRepository;

    public JobApplicationService(
            JobApplicationRepository applicationRepository,
            JobListingRepository jobListingRepository,
            StudentProfileRepository profileRepository,
            UserRepository userRepository) {

        this.applicationRepository = applicationRepository;
        this.jobListingRepository = jobListingRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public JobApplicationResponse createApplication(
            JobApplicationRequest request,
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        StudentProfile profile = profileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        JobListing jobListing = jobListingRepository
                .findById(request.getJobListingId())
                .orElseThrow(() ->
                        new RuntimeException("Job listing not found"));

        if (applicationRepository.existsByProfileAndJobListing(
                profile,
                jobListing)) {

            throw new RuntimeException(
                    "You have already saved this job");
        }

        JobApplication application = new JobApplication();

        application.setProfile(profile);
        application.setJobListing(jobListing);

        application.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : ApplicationStatus.SAVED
        );

        application.setAppliedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                applicationRepository.save(application)
        );
    }

    public List<JobApplicationResponse> getMyApplications(
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        StudentProfile profile = profileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        return applicationRepository
                .findByProfile(profile)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JobApplicationResponse updateStatus(
            Long applicationId,
            ApplicationStatus status,
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        StudentProfile profile = profileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        JobApplication application = applicationRepository
                .findByIdAndProfile(applicationId, profile)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        application.setStatus(status);
        application.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(
                applicationRepository.save(application)
        );
    }

    public void deleteApplication(
            Long applicationId,
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        StudentProfile profile = profileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        JobApplication application = applicationRepository
                .findByIdAndProfile(applicationId, profile)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        applicationRepository.delete(application);
    }

    private User getCurrentUser(Authentication authentication) {

        return userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private JobApplicationResponse mapToResponse(
            JobApplication application) {

        JobListing job = application.getJobListing();

        return new JobApplicationResponse(
                application.getId(),
                job.getId(),
                job.getTitle(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                job.getLocation(),
                job.getApplicationUrl(),
                application.getStatus(),
                application.getAppliedAt(),
                application.getUpdatedAt()
        );
    }
}