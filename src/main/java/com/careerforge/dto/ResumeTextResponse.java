package com.careerforge.dto;

public class ResumeTextResponse {

    private Long resumeId;
    private String fileName;
    private String extractedText;

    public ResumeTextResponse() {
    }

    public ResumeTextResponse(
            Long resumeId,
            String fileName,
            String extractedText) {

        this.resumeId = resumeId;
        this.fileName = fileName;
        this.extractedText = extractedText;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getExtractedText() {
        return extractedText;
    }
}