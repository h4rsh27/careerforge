package com.careerforge.dto;

public class JobRecommendationItemResponse {

    private Long jobRoleId;

    private String jobRole;

    private double matchPercentage;

    private String recommendationLevel;

    public JobRecommendationItemResponse() {
    }

    public JobRecommendationItemResponse(
            Long jobRoleId,
            String jobRole,
            double matchPercentage,
            String recommendationLevel) {

        this.jobRoleId = jobRoleId;
        this.jobRole = jobRole;
        this.matchPercentage = matchPercentage;
        this.recommendationLevel = recommendationLevel;
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

    public void setRecommendationLevel(
            String recommendationLevel) {

        this.recommendationLevel = recommendationLevel;
    }
}