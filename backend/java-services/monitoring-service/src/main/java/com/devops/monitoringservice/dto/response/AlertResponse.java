package com.devops.monitoringservice.dto.response;

import com.devops.monitoringservice.model.AlertSeverity;
import com.devops.monitoringservice.model.AlertStatus;

import java.time.LocalDateTime;

public record AlertResponse(
        String id,
        String projectId,
        String serviceName,
        String title,
        String description,
        AlertSeverity severity,
        AlertStatus status,
        String createdBy,
        String acknowledgedBy,
        String resolvedBy,
        LocalDateTime acknowledgedAt,
        LocalDateTime resolvedAt,
        LocalDateTime createdAt
) {}
