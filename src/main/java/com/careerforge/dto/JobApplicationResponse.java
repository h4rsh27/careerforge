package com.careerforge.dto;

import com.careerforge.entity.ApplicationStatus;

import java.time.LocalDateTime;

public class JobApplicationResponse {

    private Long id;

    private Long jobListingId;
    private String jobTitle;

    private Long companyId;
    private String companyName;

    private String location;
    private String applicationUrl;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    public JobApplicationResponse() {
    }

    public JobApplicationResponse(
            Long id,
            Long jobListingId,
            String jobTitle,
            Long companyId,
            String companyName,
            String location,
            String applicationUrl,
            ApplicationStatus status,
            LocalDateTime appliedAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.jobListingId = jobListingId;
        this.jobTitle = jobTitle;
        this.companyId = companyId;
        this.companyName = companyName;
        this.location = location;
        this.applicationUrl = applicationUrl;
        this.status = status;
        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getJobListingId() {
        return jobListingId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}