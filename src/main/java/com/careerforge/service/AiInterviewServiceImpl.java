package com.careerforge.service;

import com.careerforge.entity.InterviewQuestion;
import org.springframework.stereotype.Service;

@Service
public class AiInterviewServiceImpl
        implements AiInterviewService {

    @Override
    public String generateQuestion(
            String jobRole,
            String skillName) {

        return "You are interviewing for the "
                + jobRole
                + " role. Explain "
                + skillName
                + " and describe how you would use it "
                + "in a real-world project.";
    }

    @Override
    public AiEvaluationResult evaluateAnswer(
            InterviewQuestion question,
            String answer) {

        /*
         * Temporary development implementation.
         *
         * Real AI evaluation will replace this logic.
         */

        if (answer == null ||
                answer.isBlank()) {

            return new AiEvaluationResult(
                    0,
                    "No answer was provided."
            );
        }

        return new AiEvaluationResult(
                5,
                "Answer received successfully. "
                        + "Detailed AI evaluation will be "
                        + "implemented with the AI provider."
        );
    }
}