package com.careerforge.service;

import com.careerforge.dto.JobRoleRequest;
import com.careerforge.dto.JobRoleResponse;
import com.careerforge.entity.JobRole;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.JobRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobRoleService {

    private final JobRoleRepository jobRoleRepository;

    public JobRoleService(
            JobRoleRepository jobRoleRepository) {

        this.jobRoleRepository = jobRoleRepository;
    }

    public JobRoleResponse createJobRole(
            JobRoleRequest request) {

        String roleName =
                request.getRoleName().trim();

        if (jobRoleRepository.existsByRoleNameIgnoreCase(
                roleName)) {

            throw new IllegalArgumentException(
                    "Job role already exists");
        }

        JobRole jobRole = new JobRole();

        jobRole.setRoleName(roleName);

        if (request.getDescription() != null) {
            jobRole.setDescription(
                    request.getDescription().trim()
            );
        }

        JobRole savedJobRole =
                jobRoleRepository.save(jobRole);

        return mapToResponse(savedJobRole);
    }

    public List<JobRoleResponse> getAllJobRoles() {

        return jobRoleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JobRoleResponse getJobRole(Long id) {

        JobRole jobRole =
                jobRoleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        return mapToResponse(jobRole);
    }

    public JobRoleResponse updateJobRole(
            Long id,
            JobRoleRequest request) {

        JobRole jobRole =
                jobRoleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        String roleName =
                request.getRoleName().trim();

        if (!jobRole.getRoleName()
                .equalsIgnoreCase(roleName)
                && jobRoleRepository
                .existsByRoleNameIgnoreCase(roleName)) {

            throw new IllegalArgumentException(
                    "Job role already exists");
        }

        jobRole.setRoleName(roleName);

        if (request.getDescription() != null) {
            jobRole.setDescription(
                    request.getDescription().trim()
            );
        } else {
            jobRole.setDescription(null);
        }

        JobRole updatedJobRole =
                jobRoleRepository.save(jobRole);

        return mapToResponse(updatedJobRole);
    }

    public void deleteJobRole(Long id) {

        JobRole jobRole =
                jobRoleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        jobRoleRepository.delete(jobRole);
    }

    private JobRoleResponse mapToResponse(
            JobRole jobRole) {

        return new JobRoleResponse(
                jobRole.getId(),
                jobRole.getRoleName(),
                jobRole.getDescription()
        );
    }
}