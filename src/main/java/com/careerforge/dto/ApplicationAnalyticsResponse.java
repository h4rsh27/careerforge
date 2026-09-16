package com.careerforge.dto;

public class ApplicationAnalyticsResponse {

    private long totalApplications;
    private long savedApplications;
    private long appliedApplications;
    private long interviewApplications;
    private long offerApplications;
    private long rejectedApplications;

    public ApplicationAnalyticsResponse() {
    }

    public ApplicationAnalyticsResponse(
            long totalApplications,
            long savedApplications,
            long appliedApplications,
            long interviewApplications,
            long offerApplications,
            long rejectedApplications) {

        this.totalApplications = totalApplications;
        this.savedApplications = savedApplications;
        this.appliedApplications = appliedApplications;
        this.interviewApplications = interviewApplications;
        this.offerApplications = offerApplications;
        this.rejectedApplications = rejectedApplications;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public long getSavedApplications() {
        return savedApplications;
    }

    public long getAppliedApplications() {
        return appliedApplications;
    }

    public long getInterviewApplications() {
        return interviewApplications;
    }

    public long getOfferApplications() {
        return offerApplications;
    }

    public long getRejectedApplications() {
        return rejectedApplications;
    }
}