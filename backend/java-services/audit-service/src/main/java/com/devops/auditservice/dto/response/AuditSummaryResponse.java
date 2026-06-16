package com.devops.auditservice.dto.response;

public record AuditSummaryResponse(
        long totalEvents,
        long infoEvents,
        long lowEvents,
        long mediumEvents,
        long highEvents,
        long criticalEvents,
        long loginEvents,
        long deploymentEvents,
        long rollbackEvents,
        long pipelineRunEvents
) {}
