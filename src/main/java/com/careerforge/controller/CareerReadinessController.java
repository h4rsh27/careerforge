package com.careerforge.controller;

import com.careerforge.dto.CareerReadinessResponse;
import com.careerforge.service.CareerReadinessService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/career-readiness")
public class CareerReadinessController {

    private final CareerReadinessService careerReadinessService;

    public CareerReadinessController(
            CareerReadinessService careerReadinessService) {

        this.careerReadinessService =
                careerReadinessService;
    }

    @GetMapping("/{jobRoleId}")
    public CareerReadinessResponse calculateReadiness(
            @PathVariable Long jobRoleId,
            Authentication authentication) {

        String email =
                authentication.getName();

        return careerReadinessService.calculateReadiness(
                email,
                jobRoleId
        );
    }
}