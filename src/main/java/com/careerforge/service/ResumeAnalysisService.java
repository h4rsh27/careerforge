package com.careerforge.service;

import com.careerforge.dto.ResumeAnalysisResponse;
import com.careerforge.entity.Resume;
import com.careerforge.entity.StudentProfile;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.ResumeRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ResumeAnalysisService {

    private final ResumeRepository resumeRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;
    private final RestClient openRouterClient;
    private final ObjectMapper objectMapper;

    public ResumeAnalysisService(
            ResumeRepository resumeRepository,
            StudentProfileRepository studentProfileRepository,
            UserRepository userRepository,
            RestClient openRouterClient,
            ObjectMapper objectMapper) {

        this.resumeRepository = resumeRepository;
        this.studentProfileRepository =
                studentProfileRepository;
        this.userRepository = userRepository;
        this.openRouterClient = openRouterClient;
        this.objectMapper = objectMapper;
    }

    public ResumeAnalysisResponse analyzeResume(
            String email) {

        StudentProfile profile =
                userRepository.findByEmail(email)
                        .flatMap(studentProfileRepository::findByUser)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student profile not found"));

        Resume resume =
                resumeRepository.findByProfile(profile)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Resume not found"));

        String resumeText =
                resume.getExtractedText();

        if (resumeText == null ||
                resumeText.isBlank()) {

            throw new ResourceNotFoundException(
                    "No extracted text found for resume");
        }

        String apiKey =
                System.getenv("OPENROUTER_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {

            throw new IllegalStateException(
                    "OPENROUTER_API_KEY environment variable is not set");
        }

        String prompt = buildPrompt(resumeText);

        String requestBody =
                """
                {
                    "model": "openrouter/free",
                    "messages": [
                        {
                            "role": "system",
                            "content": "You are CareerForge Resume AI. Analyze resumes professionally for students and entry-level software developers. Return ONLY valid JSON. Do not use markdown. Do not invent information that is not present in the resume. Scores must be integers from 0 to 100."
                        },
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ]
                }
                """.formatted(
                        escapeJson(prompt)
                );

        JsonNode response =
                openRouterClient
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
            throw new IllegalStateException(
                    "AI service returned an empty response");
        }

        JsonNode choices =
                response.path("choices");

        if (!choices.isArray() ||
                choices.isEmpty()) {

            throw new IllegalStateException(
                    "AI service returned no analysis");
        }

        String aiContent =
                choices
                        .get(0)
                        .path("message")
                        .path("content")
                        .asText("");

        if (aiContent.isBlank()) {
            throw new IllegalStateException(
                    "AI service returned an empty analysis");
        }

        aiContent = cleanJsonResponse(aiContent);

        try {

            JsonNode analysis =
                    objectMapper.readTree(aiContent);

            return mapResponse(
                    resume,
                    analysis
            );

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "AI returned an invalid resume analysis",
                    exception
            );
        }
    }

    private String buildPrompt(String resumeText) {

        return """
                Analyze the following resume for a student or
                entry-level software developer.

                Evaluate:

                1. Overall resume quality
                2. ATS compatibility
                3. Technical skills
                4. Missing or weak skills
                5. Projects
                6. Experience
                7. Education
                8. Resume keywords
                9. Strengths
                10. Weaknesses
                11. Specific improvements

                Return exactly this JSON structure:

                {
                  "overallScore": 0,
                  "atsScore": 0,
                  "summary": "",
                  "strengths": [],
                  "weaknesses": [],
                  "skills": [],
                  "missingSkills": [],
                  "projectFeedback": [],
                  "experienceFeedback": [],
                  "educationFeedback": [],
                  "keywordSuggestions": [],
                  "improvementSuggestions": []
                }

                Important rules:

                - Scores must be integers from 0 to 100.
                - Use only information supported by the resume.
                - Do not invent projects, jobs, skills, degrees,
                  certifications or achievements.
                - If a section is missing, mention that clearly.
                - Give practical suggestions.
                - Keep each list item concise.
                - Return ONLY JSON.

                RESUME:

                %s
                """.formatted(resumeText);
    }

    private ResumeAnalysisResponse mapResponse(
            Resume resume,
            JsonNode analysis) {

        ResumeAnalysisResponse response =
                new ResumeAnalysisResponse();

        response.setResumeId(resume.getId());
        response.setFileName(resume.getFileName());

        response.setOverallScore(
                analysis.path("overallScore").asInt(0)
        );

        response.setAtsScore(
                analysis.path("atsScore").asInt(0)
        );

        response.setSummary(
                analysis.path("summary").asText("")
        );

        response.setStrengths(
                objectMapper.convertValue(
                        analysis.path("strengths"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        response.setWeaknesses(
                objectMapper.convertValue(
                        analysis.path("weaknesses"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        response.setSkills(
                objectMapper.convertValue(
                        analysis.path("skills"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        response.setMissingSkills(
                objectMapper.convertValue(
                        analysis.path("missingSkills"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        response.setProjectFeedback(
                objectMapper.convertValue(
                        analysis.path("projectFeedback"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        response.setExperienceFeedback(
                objectMapper.convertValue(
                        analysis.path("experienceFeedback"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        response.setEducationFeedback(
                objectMapper.convertValue(
                        analysis.path("educationFeedback"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        response.setKeywordSuggestions(
                objectMapper.convertValue(
                        analysis.path("keywordSuggestions"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        response.setImprovementSuggestions(
                objectMapper.convertValue(
                        analysis.path("improvementSuggestions"),
                        objectMapper
                                .getTypeFactory()
                                .constructCollectionType(
                                        java.util.List.class,
                                        String.class
                                )
                )
        );

        return response;
    }

    private String cleanJsonResponse(String content) {

        String cleaned = content.trim();

        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }

        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(
                    0,
                    cleaned.length() - 3
            );
        }

        return cleaned.trim();
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