package com.careerforge.service;

import com.careerforge.entity.InterviewQuestion;

public interface AiInterviewService {

    String generateQuestion(
            String jobRole,
            String skillName
    );

    AiEvaluationResult evaluateAnswer(
            InterviewQuestion question,
            String answer
    );

    record AiEvaluationResult(
            double score,
            String feedback
    ) {
    }
}