package com.careerforge.dto;

public class JobListingResponse {

    private Long id;
    private String title;
    private String location;
    private String salaryRange;
    private Integer experienceRequired;
    private String employmentType;
    private String description;
    private String applicationUrl;

    private Long companyId;
    private String companyName;

    private Long jobRoleId;
    private String jobRole;

    public JobListingResponse() {
    }

    public JobListingResponse(
            Long id,
            String title,
            String location,
            String salaryRange,
            Integer experienceRequired,
            String employmentType,
            String description,
            String applicationUrl,
            Long companyId,
            String companyName,
            Long jobRoleId,
            String jobRole) {

        this.id = id;
        this.title = title;
        this.location = location;
        this.salaryRange = salaryRange;
        this.experienceRequired = experienceRequired;
        this.employmentType = employmentType;
        this.description = description;
        this.applicationUrl = applicationUrl;
        this.companyId = companyId;
        this.companyName = companyName;
        this.jobRoleId = jobRoleId;
        this.jobRole = jobRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
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
}