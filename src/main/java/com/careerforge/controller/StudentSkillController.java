package com.careerforge.controller;

import com.careerforge.dto.SkillRequest;
import com.careerforge.dto.SkillResponse;
import com.careerforge.service.StudentSkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class StudentSkillController {

    private final StudentSkillService studentSkillService;

    public StudentSkillController(
            StudentSkillService studentSkillService) {

        this.studentSkillService = studentSkillService;
    }

    @PostMapping
    public SkillResponse createSkill(
            @Valid @RequestBody SkillRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return studentSkillService.createSkill(
                email,
                request
        );
    }

    @GetMapping
    public List<SkillResponse> getMySkills(
            Authentication authentication) {

        String email = authentication.getName();

        return studentSkillService.getMySkills(email);
    }

    @PutMapping("/{id}")
    public SkillResponse updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody SkillRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return studentSkillService.updateSkill(
                email,
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        studentSkillService.deleteSkill(
                email,
                id
        );
    }
}