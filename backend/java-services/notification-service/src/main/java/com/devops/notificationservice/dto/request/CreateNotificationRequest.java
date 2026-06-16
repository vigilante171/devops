package com.devops.notificationservice.dto.request;

import com.devops.notificationservice.model.NotificationChannel;
import com.devops.notificationservice.model.NotificationSeverity;
import com.devops.notificationservice.model.NotificationType;
import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;
import java.util.Map;

public class CreateNotificationRequest {

    private String recipientEmail;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Message is required")
    private String message;

    private NotificationType type = NotificationType.SYSTEM;
    private NotificationSeverity severity = NotificationSeverity.INFO;
    private NotificationChannel channel = NotificationChannel.IN_APP;

    private String sourceService;
    private String projectId;
    private String entityId;
    private String actionUrl;
    private Map<String, Object> metadata = new HashMap<>();

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        return type;
    }

    public NotificationSeverity getSeverity() {
        return severity;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public String getSourceService() {
        return sourceService;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setSeverity(NotificationSeverity severity) {
        this.severity = severity;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public void setSourceService(String sourceService) {
        this.sourceService = sourceService;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
