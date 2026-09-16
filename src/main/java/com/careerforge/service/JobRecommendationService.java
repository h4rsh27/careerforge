package com.careerforge.service;

import com.careerforge.dto.JobListingRecommendationResponse;
import com.careerforge.dto.JobRecommendationResultResponse;
import com.careerforge.entity.JobListing;
import com.careerforge.entity.JobRole;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.repository.JobListingRepository;
import com.careerforge.repository.JobRoleRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class JobRecommendationService {

    private final JobListingRepository jobListingRepository;
    private final JobRoleRepository jobRoleRepository;
    private final SkillGapService skillGapService;
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;

    public JobRecommendationService(
            JobListingRepository jobListingRepository,
            JobRoleRepository jobRoleRepository,
            SkillGapService skillGapService,
            UserRepository userRepository,
            StudentProfileRepository studentProfileRepository) {

        this.jobListingRepository = jobListingRepository;
        this.jobRoleRepository = jobRoleRepository;
        this.skillGapService = skillGapService;
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    public JobRecommendationResultResponse getRecommendations(
            String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        StudentProfile profile =
                studentProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student profile not found"));

        List<JobListing> jobListings =
                jobListingRepository.findAll();

        List<JobListingRecommendationResponse> recommendations =
                new ArrayList<>();

        for (JobListing jobListing : jobListings) {

            JobRole jobRole = jobListing.getJobRole();

            try {

                var skillGap =
                        skillGapService.analyzeSkillGap(
                                email,
                                jobRole.getId()
                        );

                double skillMatch =
                        skillGap.getReadinessPercentage();

                double finalMatch =
                        calculateFinalMatch(
                                skillMatch,
                                profile,
                                jobListing
                        );

                String recommendationLevel =
                        determineRecommendationLevel(
                                finalMatch
                        );

                recommendations.add(
                        new JobListingRecommendationResponse(
                                jobListing.getId(),
                                jobListing.getTitle(),

                                jobListing.getCompany().getId(),
                                jobListing.getCompany().getName(),

                                jobListing.getLocation(),
                                jobListing.getSalaryRange(),
                                jobListing.getExperienceRequired(),
                                jobListing.getEmploymentType(),
                                jobListing.getApplicationUrl(),

                                jobRole.getId(),
                                jobRole.getRoleName(),

                                finalMatch,
                                recommendationLevel
                        )
                );

            } catch (Exception exception) {

                // Skip listings that cannot currently be analyzed.
            }
        }

        recommendations.sort(
                Comparator.comparing(
                        JobListingRecommendationResponse::
                                getMatchPercentage
                ).reversed()
        );

        return new JobRecommendationResultResponse(
                recommendations
        );
    }

    private double calculateFinalMatch(
            double skillMatch,
            StudentProfile profile,
            JobListing jobListing) {

        double score = skillMatch;

        if (profile.getLocation() != null
                && jobListing.getLocation() != null
                && profile.getLocation()
                .equalsIgnoreCase(
                        jobListing.getLocation())) {

            score += 5;
        }

        if (profile.getGraduationYear() != null
                && jobListing.getExperienceRequired() != null) {

            int currentYear =
                    java.time.Year.now().getValue();

            int experience =
                    Math.max(
                            0,
                            currentYear
                                    - profile.getGraduationYear()
                    );

            if (experience >=
                    jobListing.getExperienceRequired()) {

                score += 5;
            }
        }

        return Math.min(100, Math.round(score * 100.0) / 100.0);
    }

    private String determineRecommendationLevel(
            double matchPercentage) {

        if (matchPercentage >= 85) {
            return "HIGHLY_RECOMMENDED";
        }

        if (matchPercentage >= 70) {
            return "RECOMMENDED";
        }

        if (matchPercentage >= 50) {
            return "POSSIBLE";
        }

        return "LOW_MATCH";
    }
}