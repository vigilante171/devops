package com.devops.auditservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "audit_events")
public class AuditEvent {

    @Id
    private String id;

    private String actorEmail;
    private AuditAction action;
    private AuditSeverity severity = AuditSeverity.INFO;
    private AuditEntityType entityType;

    private String entityId;
    private String serviceName;
    private String message;

    private String ipAddress;
    private String userAgent;

    private Map<String, Object> metadata = new HashMap<>();

    @CreatedDate
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public AuditAction getAction() {
        return action;
    }

    public AuditSeverity getSeverity() {
        return severity;
    }

    public AuditEntityType getEntityType() {
        return entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMessage() {
        return message;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public void setSeverity(AuditSeverity severity) {
        this.severity = severity == null ? AuditSeverity.INFO : severity;
    }

    public void setEntityType(AuditEntityType entityType) {
        this.entityType = entityType;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata == null ? new HashMap<>() : metadata;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
