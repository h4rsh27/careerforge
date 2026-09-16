package com.careerforge.controller;

import com.careerforge.dto.JobListingRequest;
import com.careerforge.dto.JobListingResponse;
import com.careerforge.service.JobListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-listings")
public class JobListingController {

    private final JobListingService jobListingService;

    public JobListingController(
            JobListingService jobListingService) {

        this.jobListingService = jobListingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobListingResponse createJobListing(
            @Valid @RequestBody JobListingRequest request) {

        return jobListingService.createJobListing(request);
    }

    @GetMapping
    public List<JobListingResponse> getAllJobListings() {

        return jobListingService.getAllJobListings();
    }

    @GetMapping("/{id}")
    public JobListingResponse getJobListing(
            @PathVariable Long id) {

        return jobListingService.getJobListing(id);
    }

    @PutMapping("/{id}")
    public JobListingResponse updateJobListing(
            @PathVariable Long id,
            @Valid @RequestBody JobListingRequest request) {

        return jobListingService.updateJobListing(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobListing(
            @PathVariable Long id) {

        jobListingService.deleteJobListing(id);
    }

    @GetMapping("/role/{jobRoleId}")
    public List<JobListingResponse> getByJobRole(
            @PathVariable Long jobRoleId) {

        return jobListingService.getByJobRole(jobRoleId);
    }

    @GetMapping("/location/{location}")
    public List<JobListingResponse> getByLocation(
            @PathVariable String location) {

        return jobListingService.getByLocation(location);
    }
}