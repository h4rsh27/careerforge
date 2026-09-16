package com.careerforge.dto;

public class AdminDashboardResponse {

    private long totalStudents;
    private long totalCompanies;
    private long totalJobRoles;
    private long totalJobListings;
    private long totalApplications;

    private long savedApplications;
    private long appliedApplications;
    private long interviews;
    private long offers;
    private long rejectedApplications;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(
            long totalStudents,
            long totalCompanies,
            long totalJobRoles,
            long totalJobListings,
            long totalApplications,
            long savedApplications,
            long appliedApplications,
            long interviews,
            long offers,
            long rejectedApplications) {

        this.totalStudents = totalStudents;
        this.totalCompanies = totalCompanies;
        this.totalJobRoles = totalJobRoles;
        this.totalJobListings = totalJobListings;
        this.totalApplications = totalApplications;
        this.savedApplications = savedApplications;
        this.appliedApplications = appliedApplications;
        this.interviews = interviews;
        this.offers = offers;
        this.rejectedApplications = rejectedApplications;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public long getTotalCompanies() {
        return totalCompanies;
    }

    public long getTotalJobRoles() {
        return totalJobRoles;
    }

    public long getTotalJobListings() {
        return totalJobListings;
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

    public long getInterviews() {
        return interviews;
    }

    public long getOffers() {
        return offers;
    }

    public long getRejectedApplications() {
        return rejectedApplications;
    }
}