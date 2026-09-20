package com.careerforge.dto;

import java.util.List;

public class ResumeAnalysisResponse {

    private Long resumeId;
    private String fileName;
    private Integer overallScore;
    private Integer atsScore;
    private String summary;

    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> skills;
    private List<String> missingSkills;

    private List<String> projectFeedback;
    private List<String> experienceFeedback;
    private List<String> educationFeedback;

    private List<String> keywordSuggestions;
    private List<String> improvementSuggestions;

    public ResumeAnalysisResponse() {
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

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public Integer getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(Integer atsScore) {
        this.atsScore = atsScore;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getProjectFeedback() {
        return projectFeedback;
    }

    public void setProjectFeedback(List<String> projectFeedback) {
        this.projectFeedback = projectFeedback;
    }

    public List<String> getExperienceFeedback() {
        return experienceFeedback;
    }

    public void setExperienceFeedback(List<String> experienceFeedback) {
        this.experienceFeedback = experienceFeedback;
    }

    public List<String> getEducationFeedback() {
        return educationFeedback;
    }

    public void setEducationFeedback(List<String> educationFeedback) {
        this.educationFeedback = educationFeedback;
    }

    public List<String> getKeywordSuggestions() {
        return keywordSuggestions;
    }

    public void setKeywordSuggestions(List<String> keywordSuggestions) {
        this.keywordSuggestions = keywordSuggestions;
    }

    public List<String> getImprovementSuggestions() {
        return improvementSuggestions;
    }

    public void setImprovementSuggestions(
            List<String> improvementSuggestions) {

        this.improvementSuggestions = improvementSuggestions;
    }
}