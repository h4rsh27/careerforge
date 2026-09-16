package com.careerforge.controller;

import com.careerforge.dto.ApplicationAnalyticsResponse;
import com.careerforge.service.ApplicationAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications/analytics")
public class ApplicationAnalyticsController {

    private final ApplicationAnalyticsService analyticsService;

    public ApplicationAnalyticsController(
            ApplicationAnalyticsService analyticsService) {

        this.analyticsService = analyticsService;
    }

    @GetMapping
    public ResponseEntity<ApplicationAnalyticsResponse> getAnalytics(
            Authentication authentication) {

        return ResponseEntity.ok(
                analyticsService.getAnalytics(authentication)
        );
    }
}