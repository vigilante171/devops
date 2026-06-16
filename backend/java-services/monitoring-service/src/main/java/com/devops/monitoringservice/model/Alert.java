package com.devops.monitoringservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "alerts")
public class Alert {

    @Id
    private String id;

    private String projectId;
    private String serviceName;
    private String title;
    private String description;
    private AlertSeverity severity = AlertSeverity.INFO;
    private AlertStatus status = AlertStatus.OPEN;

    private String createdBy;
    private String acknowledgedBy;
    private String resolvedBy;

    private LocalDateTime acknowledgedAt;
    private LocalDateTime resolvedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getAcknowledgedBy() {
        return acknowledgedBy;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public LocalDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSeverity(AlertSeverity severity) {
        this.severity = severity == null ? AlertSeverity.INFO : severity;
    }

    public void setStatus(AlertStatus status) {
        this.status = status == null ? AlertStatus.OPEN : status;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setAcknowledgedBy(String acknowledgedBy) {
        this.acknowledgedBy = acknowledgedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
