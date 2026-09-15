package com.careerforge.controller;

import com.careerforge.dto.JobRoleRequest;
import com.careerforge.dto.JobRoleResponse;
import com.careerforge.service.JobRoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-roles")
public class JobRoleController {

    private final JobRoleService jobRoleService;

    public JobRoleController(
            JobRoleService jobRoleService) {

        this.jobRoleService = jobRoleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobRoleResponse createJobRole(
            @Valid @RequestBody JobRoleRequest request) {

        return jobRoleService.createJobRole(request);
    }

    @GetMapping
    public List<JobRoleResponse> getAllJobRoles() {

        return jobRoleService.getAllJobRoles();
    }

    @GetMapping("/{id}")
    public JobRoleResponse getJobRole(
            @PathVariable Long id) {

        return jobRoleService.getJobRole(id);
    }

    @PutMapping("/{id}")
    public JobRoleResponse updateJobRole(
            @PathVariable Long id,
            @Valid @RequestBody JobRoleRequest request) {

        return jobRoleService.updateJobRole(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobRole(
            @PathVariable Long id) {

        jobRoleService.deleteJobRole(id);
    }
}