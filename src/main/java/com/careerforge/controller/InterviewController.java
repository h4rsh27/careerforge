package com.careerforge.controller;

import com.careerforge.dto.AnswerRequest;
import com.careerforge.dto.InterviewQuestionResponse;
import com.careerforge.dto.InterviewResultResponse;
import com.careerforge.dto.InterviewSessionResponse;
import com.careerforge.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(
            InterviewService interviewService) {

        this.interviewService =
                interviewService;
    }

    @PostMapping("/start/{jobRoleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewSessionResponse startInterview(
            @PathVariable Long jobRoleId,
            Authentication authentication) {

        return interviewService.startInterview(
                authentication.getName(),
                jobRoleId
        );
    }

    @GetMapping("/{sessionId}/questions")
    public List<InterviewQuestionResponse> getQuestions(
            @PathVariable Long sessionId,
            Authentication authentication) {

        return interviewService.getQuestions(
                sessionId,
                authentication.getName()
        );
    }

    @PostMapping("/questions/{questionId}/answer")
    public InterviewResultResponse submitAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerRequest request,
            Authentication authentication) {

        return interviewService.submitAnswer(
                questionId,
                request,
                authentication.getName()
        );
    }

    @PutMapping("/{sessionId}/complete")
    public InterviewSessionResponse completeInterview(
            @PathVariable Long sessionId,
            Authentication authentication) {

        return interviewService.completeInterview(
                sessionId,
                authentication.getName()
        );
    }
}