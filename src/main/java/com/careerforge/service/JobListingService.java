package com.careerforge.service;

import com.careerforge.dto.JobListingRequest;
import com.careerforge.dto.JobListingResponse;
import com.careerforge.entity.Company;
import com.careerforge.entity.JobListing;
import com.careerforge.entity.JobRole;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.CompanyRepository;
import com.careerforge.repository.JobListingRepository;
import com.careerforge.repository.JobRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobListingService {

    private final JobListingRepository jobListingRepository;
    private final CompanyRepository companyRepository;
    private final JobRoleRepository jobRoleRepository;

    public JobListingService(
            JobListingRepository jobListingRepository,
            CompanyRepository companyRepository,
            JobRoleRepository jobRoleRepository) {

        this.jobListingRepository = jobListingRepository;
        this.companyRepository = companyRepository;
        this.jobRoleRepository = jobRoleRepository;
    }

    public JobListingResponse createJobListing(
            JobListingRequest request) {

        Company company =
                companyRepository.findById(request.getCompanyId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"));

        JobRole jobRole =
                jobRoleRepository.findById(request.getJobRoleId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        JobListing jobListing = new JobListing();

        mapRequestToEntity(
                request,
                jobListing,
                company,
                jobRole
        );

        return mapToResponse(
                jobListingRepository.save(jobListing)
        );
    }

    public List<JobListingResponse> getAllJobListings() {

        return jobListingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JobListingResponse getJobListing(Long id) {

        JobListing jobListing =
                jobListingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job listing not found"));

        return mapToResponse(jobListing);
    }

    public JobListingResponse updateJobListing(
            Long id,
            JobListingRequest request) {

        JobListing jobListing =
                jobListingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job listing not found"));

        Company company =
                companyRepository.findById(request.getCompanyId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"));

        JobRole jobRole =
                jobRoleRepository.findById(request.getJobRoleId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        mapRequestToEntity(
                request,
                jobListing,
                company,
                jobRole
        );

        return mapToResponse(
                jobListingRepository.save(jobListing)
        );
    }

    public void deleteJobListing(Long id) {

        JobListing jobListing =
                jobListingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job listing not found"));

        jobListingRepository.delete(jobListing);
    }

    public List<JobListingResponse> getByJobRole(
            Long jobRoleId) {

        JobRole jobRole =
                jobRoleRepository.findById(jobRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        return jobListingRepository
                .findByJobRole(jobRole)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<JobListingResponse> getByLocation(
            String location) {

        return jobListingRepository
                .findByLocationIgnoreCase(location)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void mapRequestToEntity(
            JobListingRequest request,
            JobListing jobListing,
            Company company,
            JobRole jobRole) {

        jobListing.setTitle(request.getTitle().trim());
        jobListing.setLocation(request.getLocation().trim());
        jobListing.setSalaryRange(request.getSalaryRange());
        jobListing.setExperienceRequired(
                request.getExperienceRequired()
        );
        jobListing.setEmploymentType(
                request.getEmploymentType().trim()
        );
        jobListing.setDescription(request.getDescription());
        jobListing.setApplicationUrl(
                request.getApplicationUrl().trim()
        );
        jobListing.setCompany(company);
        jobListing.setJobRole(jobRole);
    }

    private JobListingResponse mapToResponse(
            JobListing jobListing) {

        return new JobListingResponse(
                jobListing.getId(),
                jobListing.getTitle(),
                jobListing.getLocation(),
                jobListing.getSalaryRange(),
                jobListing.getExperienceRequired(),
                jobListing.getEmploymentType(),
                jobListing.getDescription(),
                jobListing.getApplicationUrl(),
                jobListing.getCompany().getId(),
                jobListing.getCompany().getName(),
                jobListing.getJobRole().getId(),
                jobListing.getJobRole().getRoleName()
        );
    }
}