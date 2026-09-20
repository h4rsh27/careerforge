package com.careerforge.service;

import com.careerforge.dto.AIChatMessage;
import com.careerforge.dto.AIChatRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CareerAssistantService {

    private final RestClient openRouterClient;

    public CareerAssistantService(RestClient openRouterClient) {
        this.openRouterClient = openRouterClient;
    }

    public String chat(String email, AIChatRequest request) {

        String apiKey = System.getenv("OPENROUTER_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "OPENROUTER_API_KEY environment variable is not set"
            );
        }

        StringBuilder messages = new StringBuilder();

        messages.append("""
                {
                    "role": "system",
                    "content": "You are CareerForge AI, a professional career assistant inside the CareerForge career platform. Your purpose is to help students with career planning, Java and backend development, full-stack development, interview preparation, resume improvement, skill-gap understanding, learning roadmaps, job preparation, project guidance, and professional development. Give practical and actionable advice. Explain technical concepts clearly. Prefer structured answers. Do not invent CareerForge data. If information about the student's profile is unavailable, say so. Do not claim that you performed an action when you did not. Keep responses reasonably concise. When appropriate, give step-by-step recommendations."
                }
                """);

        if (request.getHistory() != null) {

            for (AIChatMessage message : request.getHistory()) {

                if (message == null ||
                        message.getContent() == null ||
                        message.getContent().isBlank()) {
                    continue;
                }

                String role = "assistant".equalsIgnoreCase(message.getRole())
                        ? "assistant"
                        : "user";

                String content = escapeJson(message.getContent());

                messages.append("""
                        ,
                        {
                            "role": "%s",
                            "content": "%s"
                        }
                        """.formatted(role, content));
            }
        }

        messages.append("""
                ,
                {
                    "role": "user",
                    "content": "%s"
                }
                """.formatted(
                escapeJson(request.getMessage())
        ));

        String requestBody = """
                {
                    "model": "openrouter/free",
                    "messages": [%s]
                }
                """.formatted(messages);

        JsonNode response = openRouterClient
                .post()
                .uri("/chat/completions")
                .header(
                        "Authorization",
                        "Bearer " + apiKey
                )
                .header(
                        "Content-Type",
                        "application/json"
                )
                .body(requestBody)
                .retrieve()
                .body(JsonNode.class);

        if (response == null) {
            return "I couldn't generate a response right now.";
        }

        JsonNode choices = response.path("choices");

        if (!choices.isArray() || choices.isEmpty()) {
            return "I couldn't generate a response right now.";
        }

        String reply = choices
                .get(0)
                .path("message")
                .path("content")
                .asText("");

        if (reply.isBlank()) {
            return "I couldn't generate a response right now.";
        }

        return reply;
    }

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}