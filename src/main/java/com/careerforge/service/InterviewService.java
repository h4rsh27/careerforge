package com.careerforge.service;

import com.careerforge.dto.AnswerRequest;
import com.careerforge.dto.InterviewQuestionResponse;
import com.careerforge.dto.InterviewResultResponse;
import com.careerforge.dto.InterviewSessionResponse;
import com.careerforge.entity.InterviewAnswer;
import com.careerforge.entity.InterviewQuestion;
import com.careerforge.entity.InterviewSession;
import com.careerforge.entity.JobRole;
import com.careerforge.entity.RequiredSkill;
import com.careerforge.entity.StudentProfile;
import com.careerforge.entity.User;
import com.careerforge.exception.ResourceNotFoundException;
import com.careerforge.repository.InterviewAnswerRepository;
import com.careerforge.repository.InterviewQuestionRepository;
import com.careerforge.repository.InterviewSessionRepository;
import com.careerforge.repository.JobRoleRepository;
import com.careerforge.repository.RequiredSkillRepository;
import com.careerforge.repository.StudentProfileRepository;
import com.careerforge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewService {

    private final JobRoleRepository jobRoleRepository;

    private final RequiredSkillRepository requiredSkillRepository;

    private final InterviewSessionRepository interviewSessionRepository;

    private final InterviewQuestionRepository interviewQuestionRepository;

    private final InterviewAnswerRepository interviewAnswerRepository;

    private final UserRepository userRepository;

    private final StudentProfileRepository studentProfileRepository;

    private final AiInterviewService aiInterviewService;

    public InterviewService(
            JobRoleRepository jobRoleRepository,
            RequiredSkillRepository requiredSkillRepository,
            InterviewSessionRepository interviewSessionRepository,
            InterviewQuestionRepository interviewQuestionRepository,
            InterviewAnswerRepository interviewAnswerRepository,
            UserRepository userRepository,
            StudentProfileRepository studentProfileRepository,
            AiInterviewService aiInterviewService) {

        this.jobRoleRepository =
                jobRoleRepository;

        this.requiredSkillRepository =
                requiredSkillRepository;

        this.interviewSessionRepository =
                interviewSessionRepository;

        this.interviewQuestionRepository =
                interviewQuestionRepository;

        this.interviewAnswerRepository =
                interviewAnswerRepository;

        this.userRepository =
                userRepository;

        this.studentProfileRepository =
                studentProfileRepository;

        this.aiInterviewService =
                aiInterviewService;
    }

    public InterviewSessionResponse startInterview(
            String email,
            Long jobRoleId) {

        StudentProfile profile =
                getStudentProfile(email);

        JobRole jobRole =
                jobRoleRepository.findById(jobRoleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Job role not found"));

        List<RequiredSkill> requiredSkills =
                requiredSkillRepository.findByJobRole(jobRole);

        if (requiredSkills.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No required skills found for this job role");
        }

        InterviewSession session =
                new InterviewSession();

        session.setProfile(profile);

        session.setJobRole(jobRole);

        session.setStatus(
                "IN_PROGRESS"
        );

        session.setStartedAt(
                LocalDateTime.now()
        );

        InterviewSession savedSession =
                interviewSessionRepository.save(session);

        List<InterviewQuestion> questions =
                new ArrayList<>();

        int order = 1;

        for (RequiredSkill requiredSkill :
                requiredSkills) {

            InterviewQuestion question =
                    new InterviewQuestion();

            String generatedQuestion =
                    aiInterviewService.generateQuestion(
                            jobRole.getRoleName(),
                            requiredSkill.getSkillName()
                    );

            question.setQuestion(
                    generatedQuestion
            );

            question.setCategory(
                    requiredSkill.getSkillName()
            );

            question.setQuestionOrder(
                    order
            );

            question.setSession(
                    savedSession
            );

            questions.add(question);

            order++;
        }

        interviewQuestionRepository.saveAll(
                questions
        );

        return mapToSessionResponse(
                savedSession
        );
    }

    public List<InterviewQuestionResponse> getQuestions(
            Long sessionId,
            String email) {

        StudentProfile profile =
                getStudentProfile(email);

        InterviewSession session =
                interviewSessionRepository
                        .findByIdAndProfile(
                                sessionId,
                                profile
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview session not found"));

        return interviewQuestionRepository
                .findBySessionOrderByQuestionOrder(session)
                .stream()
                .map(question ->
                        new InterviewQuestionResponse(
                                question.getId(),
                                question.getQuestion(),
                                question.getCategory(),
                                question.getQuestionOrder()
                        )
                )
                .toList();
    }

    public InterviewResultResponse submitAnswer(
            Long questionId,
            AnswerRequest request,
            String email) {

        StudentProfile profile =
                getStudentProfile(email);

        InterviewQuestion question =
                interviewQuestionRepository
                        .findById(questionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview question not found"));

        InterviewSession session =
                question.getSession();

        if (!session.getProfile()
                .getId()
                .equals(profile.getId())) {

            throw new ResourceNotFoundException(
                    "Interview question not found");
        }

        if ("COMPLETED".equals(session.getStatus())) {

            throw new IllegalStateException(
                    "Interview has already been completed");
        }

        InterviewAnswer answer =
                interviewAnswerRepository
                        .findByQuestion(question)
                        .orElse(new InterviewAnswer());

        answer.setQuestion(question);

        answer.setAnswer(
                request.getAnswer().trim()
        );

        answer.setSubmittedAt(
                LocalDateTime.now()
        );

        AiInterviewService.AiEvaluationResult evaluation =
                aiInterviewService.evaluateAnswer(
                        question,
                        request.getAnswer()
                );

        answer.setScore(
                evaluation.score()
        );

        answer.setFeedback(
                evaluation.feedback()
        );

        InterviewAnswer savedAnswer =
                interviewAnswerRepository.save(answer);

        return new InterviewResultResponse(
                question.getId(),
                question.getQuestion(),
                savedAnswer.getAnswer(),
                savedAnswer.getScore(),
                savedAnswer.getFeedback()
        );
    }

    public InterviewSessionResponse completeInterview(
            Long sessionId,
            String email) {

        StudentProfile profile =
                getStudentProfile(email);

        InterviewSession session =
                interviewSessionRepository
                        .findByIdAndProfile(
                                sessionId,
                                profile
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview session not found"));

        if ("COMPLETED".equals(session.getStatus())) {

            throw new IllegalStateException(
                    "Interview has already been completed");
        }

        session.setStatus(
                "COMPLETED"
        );

        session.setCompletedAt(
                LocalDateTime.now()
        );

        InterviewSession savedSession =
                interviewSessionRepository.save(session);

        return mapToSessionResponse(
                savedSession
        );
    }

    private StudentProfile getStudentProfile(
            String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"));

        return studentProfileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student profile not found"));
    }

    private InterviewSessionResponse mapToSessionResponse(
            InterviewSession session) {

        return new InterviewSessionResponse(
                session.getId(),
                session.getJobRole().getId(),
                session.getJobRole().getRoleName(),
                session.getStatus(),
                session.getStartedAt(),
                session.getCompletedAt()
        );
    }
}