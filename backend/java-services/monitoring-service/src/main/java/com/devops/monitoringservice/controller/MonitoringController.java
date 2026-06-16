package com.devops.monitoringservice.controller;

import com.devops.monitoringservice.dto.request.CreateAlertRequest;
import com.devops.monitoringservice.dto.request.CreateMetricSnapshotRequest;
import com.devops.monitoringservice.dto.request.UpsertServiceHealthRequest;
import com.devops.monitoringservice.dto.response.*;
import com.devops.monitoringservice.model.AlertStatus;
import com.devops.monitoringservice.model.ServiceStatus;
import com.devops.monitoringservice.service.MonitoringService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
                "service", "monitoring-service",
                "status", "running"
        );
    }

    @GetMapping("/summary")
    public MonitoringSummaryResponse summary() {
        return monitoringService.summary();
    }

    @PostMapping("/health")
    public ServiceHealthResponse upsertHealth(@Valid @RequestBody UpsertServiceHealthRequest request) {
        return monitoringService.upsertHealth(request);
    }

    @GetMapping("/health")
    public List<ServiceHealthResponse> health() {
        return monitoringService.findAllHealth();
    }

    @GetMapping("/health/status/{status}")
    public List<ServiceHealthResponse> healthByStatus(@PathVariable ServiceStatus status) {
        return monitoringService.findHealthByStatus(status);
    }

    @PostMapping("/alerts")
    public AlertResponse createAlert(
            @Valid @RequestBody CreateAlertRequest request,
            Authentication authentication
    ) {
        return monitoringService.createAlert(request, authentication.getName());
    }

    @GetMapping("/alerts")
    public List<AlertResponse> alerts(@RequestParam(required = false) AlertStatus status) {
        return monitoringService.findAlerts(status);
    }

    @GetMapping("/alerts/project/{projectId}")
    public List<AlertResponse> alertsByProject(@PathVariable String projectId) {
        return monitoringService.findAlertsByProject(projectId);
    }

    @PostMapping("/alerts/{id}/acknowledge")
    public AlertResponse acknowledge(
            @PathVariable String id,
            Authentication authentication
    ) {
        return monitoringService.acknowledgeAlert(id, authentication.getName());
    }

    @PostMapping("/alerts/{id}/resolve")
    public AlertResponse resolve(
            @PathVariable String id,
            Authentication authentication
    ) {
        return monitoringService.resolveAlert(id, authentication.getName());
    }

    @DeleteMapping("/alerts/{id}")
    public void deleteAlert(@PathVariable String id) {
        monitoringService.deleteAlert(id);
    }

    @PostMapping("/metrics")
    public MetricSnapshotResponse createMetric(@Valid @RequestBody CreateMetricSnapshotRequest request) {
        return monitoringService.createMetricSnapshot(request);
    }

    @GetMapping("/metrics/{serviceName}")
    public List<MetricSnapshotResponse> metrics(@PathVariable String serviceName) {
        return monitoringService.findMetrics(serviceName);
    }
}
