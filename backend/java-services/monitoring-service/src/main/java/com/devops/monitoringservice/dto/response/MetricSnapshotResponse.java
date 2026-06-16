package com.devops.monitoringservice.dto.response;

import java.time.LocalDateTime;

public record MetricSnapshotResponse(
        String id,
        String serviceName,
        double cpuUsage,
        double memoryUsage,
        double diskUsage,
        long requestCount,
        long errorCount,
        double averageResponseTimeMs,
        LocalDateTime createdAt
) {}
