package com.devops.notificationservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    private String recipientEmail;
    private String title;
    private String message;

    private NotificationType type = NotificationType.SYSTEM;
    private NotificationSeverity severity = NotificationSeverity.INFO;
    private NotificationStatus status = NotificationStatus.UNREAD;
    private NotificationChannel channel = NotificationChannel.IN_APP;

    private String sourceService;
    private String projectId;
    private String entityId;
    private String actionUrl;

    private Map<String, Object> metadata = new HashMap<>();

    private LocalDateTime readAt;
    private LocalDateTime archivedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

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

    public NotificationStatus getStatus() {
        return status;
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

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public LocalDateTime getArchivedAt() {
        return archivedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(NotificationType type) {
        this.type = type == null ? NotificationType.SYSTEM : type;
    }

    public void setSeverity(NotificationSeverity severity) {
        this.severity = severity == null ? NotificationSeverity.INFO : severity;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status == null ? NotificationStatus.UNREAD : status;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel == null ? NotificationChannel.IN_APP : channel;
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
        this.metadata = metadata == null ? new HashMap<>() : metadata;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public void setArchivedAt(LocalDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
