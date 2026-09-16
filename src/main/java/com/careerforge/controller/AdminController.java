package com.careerforge.controller;

import com.careerforge.dto.CompanyResponse;
import com.careerforge.dto.JobListingResponse;
import com.careerforge.dto.JobRoleResponse;
import com.careerforge.service.CompanyService;
import com.careerforge.service.JobListingService;
import com.careerforge.service.JobRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CompanyService companyService;
    private final JobRoleService jobRoleService;
    private final JobListingService jobListingService;

    public AdminController(
            CompanyService companyService,
            JobRoleService jobRoleService,
            JobListingService jobListingService) {

        this.companyService = companyService;
        this.jobRoleService = jobRoleService;
        this.jobListingService = jobListingService;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyResponse>> getCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/job-roles")
    public ResponseEntity<List<JobRoleResponse>> getJobRoles() {
        return ResponseEntity.ok(jobRoleService.getAllJobRoles());
    }

    @GetMapping("/job-listings")
    public ResponseEntity<List<JobListingResponse>> getJobListings() {
        return ResponseEntity.ok(jobListingService.getAllJobListings());
    }
}