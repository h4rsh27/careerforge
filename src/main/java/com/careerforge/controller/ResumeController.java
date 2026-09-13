package com.careerforge.controller;

import com.careerforge.dto.ResumeResponse;
import com.careerforge.service.ResumeService;
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

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // =========================
    // UPLOAD / REPLACE RESUME
    // =========================

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

    // =========================
    // GET RESUME METADATA
    // =========================

    @GetMapping
    public ResumeResponse getResume(
            Authentication authentication) {

        String email = authentication.getName();

        return resumeService.getResume(email);
    }

    // =========================
    // DOWNLOAD RESUME
    // =========================

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadResume(
            Authentication authentication) {

        String email = authentication.getName();

        Resource resource =
                resumeService.downloadResume(email);

        String fileName =
                resumeService.getResumeFileName(email);

        return ResponseEntity.ok()
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                fileName +
                                "\""
                )
                .body(resource);
    }

    // =========================
    // DELETE RESUME
    // =========================

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResume(
            Authentication authentication) {

        String email = authentication.getName();

        resumeService.deleteResume(email);
    }
}