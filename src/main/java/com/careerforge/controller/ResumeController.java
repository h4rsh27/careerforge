package com.careerforge.controller;

import com.careerforge.dto.ExtractedSkillResponse;
import com.careerforge.dto.ResumeResponse;
import com.careerforge.dto.ResumeAnalysisResponse;
import com.careerforge.service.ResumeAnalysisService;
import com.careerforge.dto.ResumeTextResponse;
import com.careerforge.service.ResumeService;
import com.careerforge.service.ResumeSkillExtractionService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeSkillExtractionService resumeSkillExtractionService;
    private final ResumeAnalysisService resumeAnalysisService;
    public ResumeController(
            ResumeService resumeService,
            ResumeSkillExtractionService resumeSkillExtractionService, ResumeAnalysisService resumeAnalysisService) {

        this.resumeService = resumeService;
        this.resumeSkillExtractionService =
                resumeSkillExtractionService;
        this.resumeAnalysisService = resumeAnalysisService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResumeResponse uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        String email = authentication.getName();

        return resumeService.uploadResume(
                email,
                file
        );
    }
    @PostMapping("/analyze")
    public ResumeAnalysisResponse analyzeResume(
            Authentication authentication) {

        return resumeAnalysisService.analyzeResume(
                authentication.getName()
        );
    }
    @GetMapping
    public ResumeResponse getResume(
            Authentication authentication) {

        String email = authentication.getName();

        return resumeService.getResume(email);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadResume(
            Authentication authentication) {

        String email = authentication.getName();

        Resource resource =
                resumeService.downloadResume(email);

        String fileName =
                resumeService.getResumeFileName(email);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                fileName +
                                "\""
                )
                .body(resource);
    }

    @GetMapping("/skills")
    public ExtractedSkillResponse extractSkills(
            Authentication authentication) {

        String email = authentication.getName();

        return resumeSkillExtractionService.extractSkills(
                email
        );
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResume(
            Authentication authentication) {

        String email = authentication.getName();

        resumeService.deleteResume(email);
    }
    @GetMapping("/text")
    public ResponseEntity<ResumeTextResponse> getResumeText(
            Authentication authentication) {

        return ResponseEntity.ok(
                resumeService.getResumeText(
                        authentication.getName()
                )
        );
    }
}