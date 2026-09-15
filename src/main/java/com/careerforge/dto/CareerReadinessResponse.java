package com.careerforge.dto;

import com.careerforge.entity.ReadinessLevel;

import java.util.List;

public class CareerReadinessResponse {

    private Long jobRoleId;

    private String jobRole;

    private double readinessScore;

    private ReadinessLevel readinessLevel;

    private String message;

    private List<String> strengths;

    private List<String> criticalGaps;

    public CareerReadinessResponse() {
    }

    public CareerReadinessResponse(
            Long jobRoleId,
            String jobRole,
            double readinessScore,
            ReadinessLevel readinessLevel,
            String message,
            List<String> strengths,
            List<String> criticalGaps) {

        this.jobRoleId = jobRoleId;
        this.jobRole = jobRole;
        this.readinessScore = readinessScore;
        this.readinessLevel = readinessLevel;
        this.message = message;
        this.strengths = strengths;
        this.criticalGaps = criticalGaps;
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

    public double getReadinessScore() {
        return readinessScore;
    }

    public void setReadinessScore(double readinessScore) {
        this.readinessScore = readinessScore;
    }

    public ReadinessLevel getReadinessLevel() {
        return readinessLevel;
    }

    public void setReadinessLevel(ReadinessLevel readinessLevel) {
        this.readinessLevel = readinessLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getCriticalGaps() {
        return criticalGaps;
    }

    public void setCriticalGaps(List<String> criticalGaps) {
        this.criticalGaps = criticalGaps;
    }
}