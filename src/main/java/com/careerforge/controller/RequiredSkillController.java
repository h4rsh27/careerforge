package com.careerforge.controller;

import com.careerforge.dto.RequiredSkillRequest;
import com.careerforge.dto.RequiredSkillResponse;
import com.careerforge.service.RequiredSkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-roles/{jobRoleId}/skills")
public class RequiredSkillController {

    private final RequiredSkillService requiredSkillService;

    public RequiredSkillController(
            RequiredSkillService requiredSkillService) {

        this.requiredSkillService =
                requiredSkillService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequiredSkillResponse addSkill(
            @PathVariable Long jobRoleId,
            @Valid @RequestBody RequiredSkillRequest request) {

        return requiredSkillService.addSkill(
                jobRoleId,
                request
        );
    }

    @GetMapping
    public List<RequiredSkillResponse> getSkills(
            @PathVariable Long jobRoleId) {

        return requiredSkillService.getSkills(
                jobRoleId
        );
    }

    @GetMapping("/{skillId}")
    public RequiredSkillResponse getSkill(
            @PathVariable Long jobRoleId,
            @PathVariable Long skillId) {

        return requiredSkillService.getSkill(
                jobRoleId,
                skillId
        );
    }

    @PutMapping("/{skillId}")
    public RequiredSkillResponse updateSkill(
            @PathVariable Long jobRoleId,
            @PathVariable Long skillId,
            @Valid @RequestBody RequiredSkillRequest request) {

        return requiredSkillService.updateSkill(
                jobRoleId,
                skillId,
                request
        );
    }

    @DeleteMapping("/{skillId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(
            @PathVariable Long jobRoleId,
            @PathVariable Long skillId) {

        requiredSkillService.deleteSkill(
                jobRoleId,
                skillId
        );
    }
}