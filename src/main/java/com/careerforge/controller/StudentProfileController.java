package com.careerforge.controller;

import com.careerforge.dto.StudentProfileRequest;
import com.careerforge.dto.StudentProfileResponse;
import com.careerforge.service.StudentProfileService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(
            StudentProfileService studentProfileService) {

        this.studentProfileService = studentProfileService;
    }

    // =========================
    // CREATE STUDENT PROFILE
    // =========================

    @PostMapping
    public StudentProfileResponse createProfile(
            @Valid @RequestBody StudentProfileRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return studentProfileService.createProfile(
                email,
                request
        );
    }
}