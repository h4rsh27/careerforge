package com.careerforge.dto;

public class JobListingRecommendationResponse {

    private Long jobListingId;
    private String jobTitle;

    private Long companyId;
    private String companyName;

    private String location;
    private String salaryRange;
    private Integer experienceRequired;
    private String employmentType;
    private String applicationUrl;

    private Long jobRoleId;
    private String jobRole;

    private double matchPercentage;
    private String recommendationLevel;

    public JobListingRecommendationResponse() {
    }

    public JobListingRecommendationResponse(
            Long jobListingId,
            String jobTitle,
            Long companyId,
            String companyName,
            String location,
            String salaryRange,
            Integer experienceRequired,
            String employmentType,
            String applicationUrl,
            Long jobRoleId,
            String jobRole,
            double matchPercentage,
            String recommendationLevel) {

        this.jobListingId = jobListingId;
        this.jobTitle = jobTitle;
        this.companyId = companyId;
        this.companyName = companyName;
        this.location = location;
        this.salaryRange = salaryRange;
        this.experienceRequired = experienceRequired;
        this.employmentType = employmentType;
        this.applicationUrl = applicationUrl;
        this.jobRoleId = jobRoleId;
        this.jobRole = jobRole;
        this.matchPercentage = matchPercentage;
        this.recommendationLevel = recommendationLevel;
    }

    public Long getJobListingId() {
        return jobListingId;
    }

    public void setJobListingId(Long jobListingId) {
        this.jobListingId = jobListingId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public Integer getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(Integer experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public Long getJobRoleId() {
        return jobRoleId;
    }

    public void setJobRoleId(Long jobRoleId) {
        this.jobRoleId = jobRoleId;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public String getRecommendationLevel() {
        return recommendationLevel;
    }

    public void setRecommendationLevel(String recommendationLevel) {
        this.recommendationLevel = recommendationLevel;
    }
}