package com.careerforge.dto;

import jakarta.validation.constraints.NotBlank;

public class AnswerRequest {

    @NotBlank(message = "Answer is required")
    private String answer;

    public AnswerRequest() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}