package com.devops.monitoringservice.dto.response;

import com.devops.monitoringservice.model.ServiceStatus;

import java.time.LocalDateTime;

public record ServiceHealthResponse(
        String id,
        String serviceName,
        String serviceUrl,
        ServiceStatus status,
        long responseTimeMs,
        String version,
        String message,
        LocalDateTime lastCheckedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
