package com.careerforge.dto;

import com.careerforge.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class JobApplicationRequest {

    @NotNull
    private Long jobListingId;

    private ApplicationStatus status;

    public Long getJobListingId() {
        return jobListingId;
    }

    public void setJobListingId(Long jobListingId) {
        this.jobListingId = jobListingId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}