package com.devops.pipelineservice.dto.response;

import com.devops.pipelineservice.model.PipelineRunStage;
import com.devops.pipelineservice.model.PipelineRunStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PipelineRunResponse(
        String id,
        String pipelineId,
        String projectId,
        String pipelineName,
        String branch,
        String commitSha,
        String triggeredBy,
        PipelineRunStatus status,
        List<PipelineRunStage> stages,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime finishedAt
) {}
