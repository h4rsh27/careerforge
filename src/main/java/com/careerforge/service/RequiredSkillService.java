package com.careerforge.service;

import com.careerforge.dto.RequiredSkillRequest;
import com.careerforge.dto.RequiredSkillResponse;
import com.careerforge.entity.JobRole;
import com.careerforge.entity.RequiredSkill;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.JobRoleRepository;
import com.careerforge.repository.RequiredSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequiredSkillService {

    private final RequiredSkillRepository requiredSkillRepository;
    private final JobRoleRepository jobRoleRepository;

    public RequiredSkillService(
            RequiredSkillRepository requiredSkillRepository,
            JobRoleRepository jobRoleRepository) {

        this.requiredSkillRepository =
                requiredSkillRepository;

        this.jobRoleRepository =
                jobRoleRepository;
    }

    public RequiredSkillResponse addSkill(
            Long jobRoleId,
            RequiredSkillRequest request) {

        JobRole jobRole =
                jobRoleRepository.findById(jobRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        String skillName =
                request.getSkillName().trim();

        if (requiredSkillRepository
                .existsByJobRoleAndSkillNameIgnoreCase(
                        jobRole,
                        skillName)) {

            throw new IllegalArgumentException(
                    "Skill already exists for this job role");
        }

        RequiredSkill requiredSkill =
                new RequiredSkill();

        requiredSkill.setSkillName(skillName);
        requiredSkill.setImportance(
                request.getImportance()
        );
        requiredSkill.setJobRole(jobRole);

        RequiredSkill savedSkill =
                requiredSkillRepository.save(
                        requiredSkill
                );

        return mapToResponse(savedSkill);
    }

    public List<RequiredSkillResponse> getSkills(
            Long jobRoleId) {

        JobRole jobRole =
                jobRoleRepository.findById(jobRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        return requiredSkillRepository
                .findByJobRole(jobRole)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RequiredSkillResponse getSkill(
            Long jobRoleId,
            Long skillId) {

        JobRole jobRole =
                jobRoleRepository.findById(jobRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        RequiredSkill skill =
                requiredSkillRepository
                        .findById(skillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Required skill not found"));

        if (!skill.getJobRole()
                .getId()
                .equals(jobRole.getId())) {

            throw new ResourceNotFoundException(
                    "Required skill not found for this job role");
        }

        return mapToResponse(skill);
    }

    public RequiredSkillResponse updateSkill(
            Long jobRoleId,
            Long skillId,
            RequiredSkillRequest request) {

        JobRole jobRole =
                jobRoleRepository.findById(jobRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        RequiredSkill skill =
                requiredSkillRepository
                        .findById(skillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Required skill not found"));

        if (!skill.getJobRole()
                .getId()
                .equals(jobRole.getId())) {

            throw new ResourceNotFoundException(
                    "Required skill not found for this job role");
        }

        String skillName =
                request.getSkillName().trim();

        if (!skill.getSkillName()
                .equalsIgnoreCase(skillName)
                && requiredSkillRepository
                .existsByJobRoleAndSkillNameIgnoreCase(
                        jobRole,
                        skillName)) {

            throw new IllegalArgumentException(
                    "Skill already exists for this job role");
        }

        skill.setSkillName(skillName);
        skill.setImportance(
                request.getImportance()
        );

        RequiredSkill updatedSkill =
                requiredSkillRepository.save(skill);

        return mapToResponse(updatedSkill);
    }

    public void deleteSkill(
            Long jobRoleId,
            Long skillId) {

        JobRole jobRole =
                jobRoleRepository.findById(jobRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        RequiredSkill skill =
                requiredSkillRepository
                        .findById(skillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Required skill not found"));

        if (!skill.getJobRole()
                .getId()
                .equals(jobRole.getId())) {

            throw new ResourceNotFoundException(
                    "Required skill not found for this job role");
        }

        requiredSkillRepository.delete(skill);
    }

    private RequiredSkillResponse mapToResponse(
            RequiredSkill skill) {

        return new RequiredSkillResponse(
                skill.getId(),
                skill.getJobRole().getId(),
                skill.getSkillName(),
                skill.getImportance()
        );
    }
}