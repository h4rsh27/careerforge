package com.careerforge.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class AIChatRequest {

    @NotBlank(message = "Message is required")
    private String message;

    private List<AIChatMessage> history = new ArrayList<>();

    public AIChatRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AIChatMessage> getHistory() {
        return history;
    }

    public void setHistory(List<AIChatMessage> history) {
        this.history = history;
    }
}