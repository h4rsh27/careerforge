package com.careerforge.service;

import com.careerforge.dto.CareerReadinessResponse;
import com.careerforge.dto.SkillGapResponse;
import com.careerforge.dto.SkillGapSkillResponse;
import com.careerforge.entity.ReadinessLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareerReadinessService {

    private final SkillGapService skillGapService;

    public CareerReadinessService(
            SkillGapService skillGapService) {

        this.skillGapService = skillGapService;
    }

    public CareerReadinessResponse calculateReadiness(
            String email,
            Long jobRoleId) {

        SkillGapResponse skillGap =
                skillGapService.analyzeSkillGap(
                        email,
                        jobRoleId
                );

        double score =
                skillGap.getReadinessPercentage();

        ReadinessLevel level =
                determineLevel(score);

        String message =
                generateMessage(level);

        List<String> strengths =
                new ArrayList<>();

        for (SkillGapSkillResponse skill :
                skillGap.getMatchedSkills()) {

            strengths.add(
                    skill.getSkillName()
            );
        }

        List<String> criticalGaps =
                new ArrayList<>();

        for (SkillGapSkillResponse skill :
                skillGap.getMissingSkills()) {

            if (skill.getImportance() >= 8) {

                criticalGaps.add(
                        skill.getSkillName()
                );
            }
        }

        return new CareerReadinessResponse(
                skillGap.getJobRoleId(),
                skillGap.getJobRole(),
                score,
                level,
                message,
                strengths,
                criticalGaps
        );
    }

    private ReadinessLevel determineLevel(
            double score) {

        if (score < 40) {

            return ReadinessLevel.NOT_READY;
        }

        if (score < 70) {

            return ReadinessLevel.DEVELOPING;
        }

        if (score < 85) {

            return ReadinessLevel.ALMOST_READY;
        }

        return ReadinessLevel.JOB_READY;
    }

    private String generateMessage(
            ReadinessLevel level) {

        return switch (level) {

            case NOT_READY ->
                    "You need to develop several important skills before becoming job ready.";

            case DEVELOPING ->
                    "You are making progress, but several important skills still need improvement.";

            case ALMOST_READY ->
                    "You are close to being job ready. Focus on your remaining skill gaps.";

            case JOB_READY ->
                    "You have strong alignment with the required skills for this role.";
        };
    }
}