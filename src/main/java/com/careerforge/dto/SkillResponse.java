package com.careerforge.dto;

import com.careerforge.entity.ProficiencyLevel;

public class SkillResponse {

    private Long id;
    private String skillName;
    private ProficiencyLevel proficiencyLevel;

    public SkillResponse() {
    }

    public SkillResponse(
            Long id,
            String skillName,
            ProficiencyLevel proficiencyLevel) {

        this.id = id;
        this.skillName = skillName;
        this.proficiencyLevel = proficiencyLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}