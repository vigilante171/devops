package com.devops.monitoringservice.service;

import com.devops.monitoringservice.dto.request.CreateAlertRequest;
import com.devops.monitoringservice.dto.request.CreateMetricSnapshotRequest;
import com.devops.monitoringservice.dto.request.UpsertServiceHealthRequest;
import com.devops.monitoringservice.dto.response.*;
import com.devops.monitoringservice.exception.ResourceNotFoundException;
import com.devops.monitoringservice.model.*;
import com.devops.monitoringservice.repository.AlertRepository;
import com.devops.monitoringservice.repository.MetricSnapshotRepository;
import com.devops.monitoringservice.repository.ServiceHealthRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitoringService {

    private final ServiceHealthRepository healthRepository;
    private final AlertRepository alertRepository;
    private final MetricSnapshotRepository metricRepository;

    public MonitoringService(
            ServiceHealthRepository healthRepository,
            AlertRepository alertRepository,
            MetricSnapshotRepository metricRepository
    ) {
        this.healthRepository = healthRepository;
        this.alertRepository = alertRepository;
        this.metricRepository = metricRepository;
    }

    public ServiceHealthResponse upsertHealth(UpsertServiceHealthRequest request) {
        ServiceHealth health = healthRepository.findByServiceName(request.getServiceName())
                .orElseGet(ServiceHealth::new);

        health.setServiceName(request.getServiceName());
        health.setServiceUrl(request.getServiceUrl());
        health.setStatus(request.getStatus());
        health.setResponseTimeMs(request.getResponseTimeMs());
        health.setVersion(request.getVersion());
        health.setMessage(request.getMessage());
        health.setLastCheckedAt(LocalDateTime.now());

        return toHealthResponse(healthRepository.save(health));
    }

    public List<ServiceHealthResponse> findAllHealth() {
        return healthRepository.findAll()
                .stream()
                .map(this::toHealthResponse)
                .toList();
    }

    public List<ServiceHealthResponse> findHealthByStatus(ServiceStatus status) {
        return healthRepository.findByStatus(status)
                .stream()
                .map(this::toHealthResponse)
                .toList();
    }

    public AlertResponse createAlert(CreateAlertRequest request, String createdBy) {
        Alert alert = new Alert();
        alert.setProjectId(request.getProjectId());
        alert.setServiceName(request.getServiceName());
        alert.setTitle(request.getTitle());
        alert.setDescription(request.getDescription());
        alert.setSeverity(request.getSeverity());
        alert.setStatus(AlertStatus.OPEN);
        alert.setCreatedBy(createdBy);

        return toAlertResponse(alertRepository.save(alert));
    }

    public List<AlertResponse> findAlerts(AlertStatus status) {
        List<Alert> alerts = status == null
                ? alertRepository.findAll()
                : alertRepository.findByStatusOrderByCreatedAtDesc(status);

        return alerts.stream()
                .map(this::toAlertResponse)
                .toList();
    }

    public List<AlertResponse> findAlertsByProject(String projectId) {
        return alertRepository.findByProjectIdOrderByCreatedAtDesc(projectId)
                .stream()
                .map(this::toAlertResponse)
                .toList();
    }

    public AlertResponse acknowledgeAlert(String alertId, String userEmail) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found"));

        alert.setStatus(AlertStatus.ACKNOWLEDGED);
        alert.setAcknowledgedBy(userEmail);
        alert.setAcknowledgedAt(LocalDateTime.now());

        return toAlertResponse(alertRepository.save(alert));
    }

    public AlertResponse resolveAlert(String alertId, String userEmail) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found"));

        alert.setStatus(AlertStatus.RESOLVED);
        alert.setResolvedBy(userEmail);
        alert.setResolvedAt(LocalDateTime.now());

        return toAlertResponse(alertRepository.save(alert));
    }

    public MetricSnapshotResponse createMetricSnapshot(CreateMetricSnapshotRequest request) {
        MetricSnapshot metric = new MetricSnapshot();
        metric.setServiceName(request.getServiceName());
        metric.setCpuUsage(request.getCpuUsage());
        metric.setMemoryUsage(request.getMemoryUsage());
        metric.setDiskUsage(request.getDiskUsage());
        metric.setRequestCount(request.getRequestCount());
        metric.setErrorCount(request.getErrorCount());
        metric.setAverageResponseTimeMs(request.getAverageResponseTimeMs());

        return toMetricResponse(metricRepository.save(metric));
    }

    public List<MetricSnapshotResponse> findMetrics(String serviceName) {
        return metricRepository.findTop20ByServiceNameOrderByCreatedAtDesc(serviceName)
                .stream()
                .map(this::toMetricResponse)
                .toList();
    }

    public MonitoringSummaryResponse summary() {
        long totalServices = healthRepository.count();
        long healthy = healthRepository.findByStatus(ServiceStatus.HEALTHY).size();
        long degraded = healthRepository.findByStatus(ServiceStatus.DEGRADED).size();
        long down = healthRepository.findByStatus(ServiceStatus.DOWN).size();
        long openAlerts = alertRepository.countByStatus(AlertStatus.OPEN);
        long criticalOpenAlerts = alertRepository.countBySeverityAndStatus(AlertSeverity.CRITICAL, AlertStatus.OPEN);

        return new MonitoringSummaryResponse(
                totalServices,
                healthy,
                degraded,
                down,
                openAlerts,
                criticalOpenAlerts
        );
    }

    public void deleteAlert(String id) {
        if (!alertRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alert not found");
        }

        alertRepository.deleteById(id);
    }

    private ServiceHealthResponse toHealthResponse(ServiceHealth health) {
        return new ServiceHealthResponse(
                health.getId(),
                health.getServiceName(),
                health.getServiceUrl(),
                health.getStatus(),
                health.getResponseTimeMs(),
                health.getVersion(),
                health.getMessage(),
                health.getLastCheckedAt(),
                health.getCreatedAt(),
                health.getUpdatedAt()
        );
    }

    private AlertResponse toAlertResponse(Alert alert) {
        return new AlertResponse(
                alert.getId(),
                alert.getProjectId(),
                alert.getServiceName(),
                alert.getTitle(),
                alert.getDescription(),
                alert.getSeverity(),
                alert.getStatus(),
                alert.getCreatedBy(),
                alert.getAcknowledgedBy(),
                alert.getResolvedBy(),
                alert.getAcknowledgedAt(),
                alert.getResolvedAt(),
                alert.getCreatedAt()
        );
    }

    private MetricSnapshotResponse toMetricResponse(MetricSnapshot metric) {
        return new MetricSnapshotResponse(
                metric.getId(),
                metric.getServiceName(),
                metric.getCpuUsage(),
                metric.getMemoryUsage(),
                metric.getDiskUsage(),
                metric.getRequestCount(),
                metric.getErrorCount(),
                metric.getAverageResponseTimeMs(),
                metric.getCreatedAt()
        );
    }
}
