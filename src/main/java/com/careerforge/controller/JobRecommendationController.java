package com.careerforge.controller;

import com.careerforge.dto.JobMatchBreakdownResponse;
import com.careerforge.dto.JobRecommendationResultResponse;
import com.careerforge.service.JobMatchBreakdownService;
import com.careerforge.service.JobRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-recommendations")
public class JobRecommendationController {

    private final JobRecommendationService jobRecommendationService;
    private final JobMatchBreakdownService jobMatchBreakdownService;

    public JobRecommendationController(
            JobRecommendationService jobRecommendationService,
            JobMatchBreakdownService jobMatchBreakdownService) {

        this.jobRecommendationService =
                jobRecommendationService;

        this.jobMatchBreakdownService =
                jobMatchBreakdownService;
    }

    // =========================
    // GET JOB RECOMMENDATIONS
    // =========================

    @GetMapping
    public ResponseEntity<JobRecommendationResultResponse>
    getRecommendations(Authentication authentication) {

        return ResponseEntity.ok(
                jobRecommendationService
                        .getRecommendations(
                                authentication.getName()
                        )
        );
    }

    // =========================
    // GET JOB MATCH BREAKDOWN
    // =========================

    @GetMapping("/{jobListingId}/breakdown")
    public ResponseEntity<JobMatchBreakdownResponse>
    getMatchBreakdown(
            @PathVariable Long jobListingId,
            Authentication authentication) {

        return ResponseEntity.ok(
                jobMatchBreakdownService.getBreakdown(
                        jobListingId,
                        authentication
                )
        );
    }
}