package com.devops.notificationservice.dto.response;

public record NotificationSummaryResponse(
        long total,
        long unread,
        long criticalUnread,
        long warningUnread
) {}
