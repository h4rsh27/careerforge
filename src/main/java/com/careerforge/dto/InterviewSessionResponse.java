package com.careerforge.dto;

import java.time.LocalDateTime;

public class InterviewSessionResponse {

    private Long sessionId;

    private Long jobRoleId;

    private String jobRole;

    private String status;

    private LocalDateTime startedAt;

    public InterviewSessionResponse() {
    }

    public InterviewSessionResponse(
            Long sessionId,
            Long jobRoleId,
            String jobRole,
            String status,
            LocalDateTime startedAt) {

        this.sessionId = sessionId;
        this.jobRoleId = jobRoleId;
        this.jobRole = jobRole;
        this.status = status;
        this.startedAt = startedAt;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
}