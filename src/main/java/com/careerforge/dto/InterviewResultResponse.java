package com.careerforge.dto;

public class InterviewResultResponse {

    private Long questionId;

    private String question;

    private String answer;

    private Double score;

    private String feedback;

    public InterviewResultResponse() {
    }

    public InterviewResultResponse(
            Long questionId,
            String question,
            String answer,
            Double score,
            String feedback) {

        this.questionId = questionId;
        this.question = question;
        this.answer = answer;
        this.score = score;
        this.feedback = feedback;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}