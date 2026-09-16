package com.careerforge.dto;

import java.util.List;

public class JobRecommendationResultResponse {

    private List<JobListingRecommendationResponse> recommendations;

    public JobRecommendationResultResponse() {
    }

    public JobRecommendationResultResponse(
            List<JobListingRecommendationResponse> recommendations) {

        this.recommendations = recommendations;
    }

    public List<JobListingRecommendationResponse> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(
            List<JobListingRecommendationResponse> recommendations) {

        this.recommendations = recommendations;
    }
}