package com.careerforge.dto;

import java.util.List;

public class ExtractedSkillResponse {

    private Long resumeId;
    private String fileName;
    private List<String> skills;

    public ExtractedSkillResponse() {
    }

    public ExtractedSkillResponse(
            Long resumeId,
            String fileName,
            List<String> skills) {

        this.resumeId = resumeId;
        this.fileName = fileName;
        this.skills = skills;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}