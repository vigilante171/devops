package com.devops.auditservice.dto.response;

import com.devops.auditservice.model.AuditAction;
import com.devops.auditservice.model.AuditEntityType;
import com.devops.auditservice.model.AuditSeverity;

import java.time.LocalDateTime;
import java.util.Map;

public record AuditEventResponse(
        String id,
        String actorEmail,
        AuditAction action,
        AuditSeverity severity,
        AuditEntityType entityType,
        String entityId,
        String serviceName,
        String message,
        String ipAddress,
        String userAgent,
        Map<String, Object> metadata,
        LocalDateTime createdAt
) {}
