package com.careerforge.controller;

import com.careerforge.dto.SkillGapResponse;
import com.careerforge.service.SkillGapService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skill-gap")
public class SkillGapController {

    private final SkillGapService skillGapService;

    public SkillGapController(
            SkillGapService skillGapService) {

        this.skillGapService = skillGapService;
    }

    @GetMapping("/{jobRoleId}")
    public SkillGapResponse analyzeSkillGap(
            @PathVariable Long jobRoleId,
            Authentication authentication) {

        String email = authentication.getName();

        return skillGapService.analyzeSkillGap(
                email,
                jobRoleId
        );
    }
}