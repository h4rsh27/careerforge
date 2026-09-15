package com.careerforge.dto;

public class RequiredSkillResponse {

    private Long id;
    private Long jobRoleId;
    private String skillName;
    private Integer importance;

    public RequiredSkillResponse() {
    }

    public RequiredSkillResponse(
            Long id,
            Long jobRoleId,
            String skillName,
            Integer importance) {

        this.id = id;
        this.jobRoleId = jobRoleId;
        this.skillName = skillName;
        this.importance = importance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobRoleId() {
        return jobRoleId;
    }

    public void setJobRoleId(Long jobRoleId) {
        this.jobRoleId = jobRoleId;
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
}