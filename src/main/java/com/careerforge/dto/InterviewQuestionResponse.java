package com.careerforge.dto;

public class InterviewQuestionResponse {

    private Long questionId;

    private String question;

    private String category;

    private Integer questionOrder;

    public InterviewQuestionResponse() {
    }

    public InterviewQuestionResponse(
            Long questionId,
            String question,
            String category,
            Integer questionOrder) {

        this.questionId = questionId;
        this.question = question;
        this.category = category;
        this.questionOrder = questionOrder;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }
}