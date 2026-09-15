package com.careerforge.controller;

import com.careerforge.dto.LearningRoadmapResponse;
import com.careerforge.service.LearningRoadmapService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learning-roadmap")
public class LearningRoadmapController {

    private final LearningRoadmapService learningRoadmapService;

    public LearningRoadmapController(
            LearningRoadmapService learningRoadmapService) {

        this.learningRoadmapService =
                learningRoadmapService;
    }

    @GetMapping("/{jobRoleId}")
    public LearningRoadmapResponse generateRoadmap(
            @PathVariable Long jobRoleId,
            Authentication authentication) {

        String email =
                authentication.getName();

        return learningRoadmapService.generateRoadmap(
                email,
                jobRoleId
        );
    }
}