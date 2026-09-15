package com.careerforge.dto;

public class LearningRoadmapItemResponse {

    private String skillName;

    private Integer importance;

    private String priority;

    private Integer recommendedOrder;

    public LearningRoadmapItemResponse() {
    }

    public LearningRoadmapItemResponse(
            String skillName,
            Integer importance,
            String priority,
            Integer recommendedOrder) {

        this.skillName = skillName;
        this.importance = importance;
        this.priority = priority;
        this.recommendedOrder = recommendedOrder;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getRecommendedOrder() {
        return recommendedOrder;
    }

    public void setRecommendedOrder(Integer recommendedOrder) {
        this.recommendedOrder = recommendedOrder;
    }
}