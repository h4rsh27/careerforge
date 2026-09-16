package com.careerforge.repository;

import com.careerforge.entity.InterviewQuestion;
import com.careerforge.entity.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findBySessionOrderByQuestionOrder(
            InterviewSession session);
}