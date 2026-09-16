package com.careerforge.controller;

import com.careerforge.dto.AnswerRequest;
import com.careerforge.dto.InterviewQuestionResponse;
import com.careerforge.dto.InterviewResultResponse;
import com.careerforge.dto.InterviewSessionResponse;
import com.careerforge.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
            @PathVariable Long jobRoleId) {

        return interviewService.startInterview(
                jobRoleId
        );
    }

    @GetMapping("/{sessionId}/questions")
    public List<InterviewQuestionResponse> getQuestions(
            @PathVariable Long sessionId) {

        return interviewService.getQuestions(
                sessionId
        );
    }

    @PostMapping("/questions/{questionId}/answer")
    public InterviewResultResponse submitAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerRequest request) {

        return interviewService.submitAnswer(
                questionId,
                request
        );
    }
}