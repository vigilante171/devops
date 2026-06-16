package com.devops.pipelineservice.dto.response;

import com.devops.pipelineservice.model.PipelineStatus;
import com.devops.pipelineservice.model.PipelineStep;

import java.time.LocalDateTime;
import java.util.List;

public record PipelineResponse(
        String id,
        String projectId,
        String name,
        String description,
        String branch,
        String ownerEmail,
        PipelineStatus status,
        List<PipelineStep> steps,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
