package com.careerforge.service;

import com.careerforge.dto.LearningRoadmapItemResponse;
import com.careerforge.dto.LearningRoadmapResponse;
import com.careerforge.dto.SkillGapResponse;
import com.careerforge.dto.SkillGapSkillResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LearningRoadmapService {

    private final SkillGapService skillGapService;

    public LearningRoadmapService(
            SkillGapService skillGapService) {

        this.skillGapService = skillGapService;
    }

    public LearningRoadmapResponse generateRoadmap(
            String email,
            Long jobRoleId) {

        SkillGapResponse skillGap =
                skillGapService.analyzeSkillGap(
                        email,
                        jobRoleId
                );

        List<SkillGapSkillResponse> missingSkills =
                new ArrayList<>(
                        skillGap.getMissingSkills()
                );

        missingSkills.sort(
                Comparator.comparing(
                        SkillGapSkillResponse::getImportance
                ).reversed()
        );

        List<LearningRoadmapItemResponse> roadmap =
                new ArrayList<>();

        int order = 1;

        for (SkillGapSkillResponse skill :
                missingSkills) {

            String priority =
                    determinePriority(
                            skill.getImportance()
                    );

            roadmap.add(
                    new LearningRoadmapItemResponse(
                            skill.getSkillName(),
                            skill.getImportance(),
                            priority,
                            order
                    )
            );

            order++;
        }

        return new LearningRoadmapResponse(
                skillGap.getJobRoleId(),
                skillGap.getJobRole(),
                roadmap
        );
    }

    private String determinePriority(
            Integer importance) {

        if (importance >= 8) {
            return "HIGH";
        }

        if (importance >= 5) {
            return "MEDIUM";
        }

        return "LOW";
    }
}