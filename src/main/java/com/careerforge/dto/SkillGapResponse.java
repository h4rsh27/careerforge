package com.careerforge.dto;

import java.util.List;

public class SkillGapResponse {

    private Long jobRoleId;
    private String jobRole;
    private List<SkillGapSkillResponse> matchedSkills;
    private List<SkillGapSkillResponse> missingSkills;
    private double readinessPercentage;

    public SkillGapResponse() {
    }

    public SkillGapResponse(
            Long jobRoleId,
            String jobRole,
            List<SkillGapSkillResponse> matchedSkills,
            List<SkillGapSkillResponse> missingSkills,
            double readinessPercentage) {

        this.jobRoleId = jobRoleId;
        this.jobRole = jobRole;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.readinessPercentage = readinessPercentage;
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

    public List<SkillGapSkillResponse> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(
            List<SkillGapSkillResponse> matchedSkills) {

        this.matchedSkills = matchedSkills;
    }

    public List<SkillGapSkillResponse> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(
            List<SkillGapSkillResponse> missingSkills) {

        this.missingSkills = missingSkills;
    }

    public double getReadinessPercentage() {
        return readinessPercentage;
    }

    public void setReadinessPercentage(
            double readinessPercentage) {

        this.readinessPercentage = readinessPercentage;
    }
}