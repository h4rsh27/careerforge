package com.careerforge.controller;

import com.careerforge.dto.AIChatRequest;
import com.careerforge.dto.AIChatResponse;
import com.careerforge.service.CareerAssistantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/ai")
public class CareerAssistantController {

    private final CareerAssistantService careerAssistantService;

    public CareerAssistantController(
            CareerAssistantService careerAssistantService) {

        this.careerAssistantService =
                careerAssistantService;
    }

    @PostMapping("/chat")
    public AIChatResponse chat(
            @Valid @RequestBody AIChatRequest request,
            Authentication authentication) {

        String reply =
                careerAssistantService.chat(
                        authentication.getName(),
                        request
                );

        return new AIChatResponse(reply);
    }
}