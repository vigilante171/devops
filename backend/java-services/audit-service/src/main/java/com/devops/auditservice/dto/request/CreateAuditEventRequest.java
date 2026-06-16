package com.devops.auditservice.dto.request;

import com.devops.auditservice.model.AuditAction;
import com.devops.auditservice.model.AuditEntityType;
import com.devops.auditservice.model.AuditSeverity;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CreateAuditEventRequest {

    private String actorEmail;

    @NotNull(message = "Action is required")
    private AuditAction action;

    private AuditSeverity severity = AuditSeverity.INFO;

    @NotNull(message = "Entity type is required")
    private AuditEntityType entityType;

    private String entityId;
    private String serviceName;
    private String message;
    private String ipAddress;
    private String userAgent;
    private Map<String, Object> metadata = new HashMap<>();

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

    public void setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public void setSeverity(AuditSeverity severity) {
        this.severity = severity;
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
        this.metadata = metadata;
    }
}
