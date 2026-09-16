package com.careerforge.dto;

import java.util.List;

public class JobMatchBreakdownResponse {

    private Long jobListingId;
    private String jobTitle;
    private String companyName;

    private double skillMatchPercentage;
    private double locationBonus;
    private double experienceBonus;
    private double finalMatchPercentage;

    private List<String> matchedSkills;
    private List<String> missingSkills;

    public JobMatchBreakdownResponse() {
    }

    public JobMatchBreakdownResponse(
            Long jobListingId,
            String jobTitle,
            String companyName,
            double skillMatchPercentage,
            double locationBonus,
            double experienceBonus,
            double finalMatchPercentage,
            List<String> matchedSkills,
            List<String> missingSkills) {

        this.jobListingId = jobListingId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.skillMatchPercentage = skillMatchPercentage;
        this.locationBonus = locationBonus;
        this.experienceBonus = experienceBonus;
        this.finalMatchPercentage = finalMatchPercentage;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
    }

    public Long getJobListingId() {
        return jobListingId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getSkillMatchPercentage() {
        return skillMatchPercentage;
    }

    public double getLocationBonus() {
        return locationBonus;
    }

    public double getExperienceBonus() {
        return experienceBonus;
    }

    public double getFinalMatchPercentage() {
        return finalMatchPercentage;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }
}