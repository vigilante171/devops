package com.devops.notificationservice.dto.response;

import com.devops.notificationservice.model.NotificationChannel;
import com.devops.notificationservice.model.NotificationSeverity;
import com.devops.notificationservice.model.NotificationStatus;
import com.devops.notificationservice.model.NotificationType;

import java.time.LocalDateTime;
import java.util.Map;

public record NotificationResponse(
        String id,
        String recipientEmail,
        String title,
        String message,
        NotificationType type,
        NotificationSeverity severity,
        NotificationStatus status,
        NotificationChannel channel,
        String sourceService,
        String projectId,
        String entityId,
        String actionUrl,
        Map<String, Object> metadata,
        LocalDateTime readAt,
        LocalDateTime archivedAt,
        LocalDateTime createdAt
) {}
