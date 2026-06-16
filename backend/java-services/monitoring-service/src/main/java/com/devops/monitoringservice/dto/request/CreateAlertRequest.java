package com.devops.monitoringservice.dto.request;

import com.devops.monitoringservice.model.AlertSeverity;
import jakarta.validation.constraints.NotBlank;

public class CreateAlertRequest {

    private String projectId;

    @NotBlank(message = "Service name is required")
    private String serviceName;

    @NotBlank(message = "Alert title is required")
    private String title;

    private String description;
    private AlertSeverity severity = AlertSeverity.INFO;

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

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSeverity(AlertSeverity severity) {
        this.severity = severity;
    }
}
