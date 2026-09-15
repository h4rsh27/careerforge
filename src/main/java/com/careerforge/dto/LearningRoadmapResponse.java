package com.careerforge.dto;

import java.util.List;

public class LearningRoadmapResponse {

    private Long jobRoleId;

    private String jobRole;

    private List<LearningRoadmapItemResponse> roadmap;

    public LearningRoadmapResponse() {
    }

    public LearningRoadmapResponse(
            Long jobRoleId,
            String jobRole,
            List<LearningRoadmapItemResponse> roadmap) {

        this.jobRoleId = jobRoleId;
        this.jobRole = jobRole;
        this.roadmap = roadmap;
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

    public List<LearningRoadmapItemResponse> getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(
            List<LearningRoadmapItemResponse> roadmap) {

        this.roadmap = roadmap;
    }
}