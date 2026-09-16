package com.careerforge.service;

import com.careerforge.dto.AdminDashboardResponse;
import com.careerforge.entity.ApplicationStatus;
import com.careerforge.repository.CompanyRepository;
import com.careerforge.repository.JobApplicationRepository;
import com.careerforge.repository.JobListingRepository;
import com.careerforge.repository.JobRoleRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JobRoleRepository jobRoleRepository;
    private final JobListingRepository jobListingRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public AdminDashboardService(
            UserRepository userRepository,
            CompanyRepository companyRepository,
            JobRoleRepository jobRoleRepository,
            JobListingRepository jobListingRepository,
            JobApplicationRepository jobApplicationRepository) {

        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.jobRoleRepository = jobRoleRepository;
        this.jobListingRepository = jobListingRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public AdminDashboardResponse getDashboard() {

        long totalStudents =
                userRepository.countByRoleIgnoreCase("STUDENT");

        long totalCompanies =
                companyRepository.count();

        long totalJobRoles =
                jobRoleRepository.count();

        long totalJobListings =
                jobListingRepository.count();

        long totalApplications =
                jobApplicationRepository.count();

        long savedApplications =
                jobApplicationRepository
                        .countByStatus(ApplicationStatus.SAVED);

        long appliedApplications =
                jobApplicationRepository
                        .countByStatus(ApplicationStatus.APPLIED);

        long interviews =
                jobApplicationRepository
                        .countByStatus(ApplicationStatus.INTERVIEW);

        long offers =
                jobApplicationRepository
                        .countByStatus(ApplicationStatus.OFFER);

        long rejectedApplications =
                jobApplicationRepository
                        .countByStatus(ApplicationStatus.REJECTED);

        return new AdminDashboardResponse(
                totalStudents,
                totalCompanies,
                totalJobRoles,
                totalJobListings,
                totalApplications,
                savedApplications,
                appliedApplications,
                interviews,
                offers,
                rejectedApplications
        );
    }
}