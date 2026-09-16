package com.careerforge.controller;

import com.careerforge.dto.JobApplicationRequest;
import com.careerforge.dto.JobApplicationResponse;
import com.careerforge.entity.ApplicationStatus;
import com.careerforge.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService applicationService;

    public JobApplicationController(
            JobApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponse> createApplication(
            @Valid @RequestBody JobApplicationRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.createApplication(
                        request,
                        authentication
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationResponse>> getMyApplications(
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.getMyApplications(authentication)
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<JobApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status,
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.updateStatus(
                        id,
                        status,
                        authentication
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id,
            Authentication authentication) {

        applicationService.deleteApplication(
                id,
                authentication
        );

        return ResponseEntity.noContent().build();
    }
}