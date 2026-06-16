package com.devops.deploymentservice.dto.response;

import com.devops.deploymentservice.model.DeploymentEnvironment;
import com.devops.deploymentservice.model.DeploymentStatus;
import com.devops.deploymentservice.model.DeploymentStrategy;
import com.devops.deploymentservice.model.RollbackInfo;

import java.time.LocalDateTime;

public record DeploymentResponse(
        String id,
        String projectId,
        String pipelineId,
        String pipelineRunId,
        String applicationName,
        String version,
        String artifactUrl,
        DeploymentEnvironment environment,
        DeploymentStrategy strategy,
        DeploymentStatus status,
        String deployedBy,
        String notes,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        RollbackInfo rollbackInfo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
