package com.careerforge.dto;

import java.util.List;

public class JobRecommendationResponse {

    private List<JobRecommendationItemResponse> recommendations;

    public JobRecommendationResponse() {
    }

    public JobRecommendationResponse(
            List<JobRecommendationItemResponse> recommendations) {

        this.recommendations = recommendations;
    }

    public List<JobRecommendationItemResponse> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(
            List<JobRecommendationItemResponse> recommendations) {

        this.recommendations = recommendations;
    }
}