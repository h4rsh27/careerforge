package com.careerforge.dto;

public class SkillGapSkillResponse {

    private String skillName;
    private Integer importance;
    private String status;

    public SkillGapSkillResponse() {
    }

    public SkillGapSkillResponse(
            String skillName,
            Integer importance,
            String status) {

        this.skillName = skillName;
        this.importance = importance;
        this.status = status;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}