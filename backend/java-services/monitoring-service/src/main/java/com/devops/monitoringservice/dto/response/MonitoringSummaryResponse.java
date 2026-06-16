package com.devops.monitoringservice.dto.response;

public record MonitoringSummaryResponse(
        long totalServices,
        long healthyServices,
        long degradedServices,
        long downServices,
        long openAlerts,
        long criticalOpenAlerts
) {}
