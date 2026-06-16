package com.devops.monitoringservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "service_health")
public class ServiceHealth {

    @Id
    private String id;

    private String serviceName;
    private String serviceUrl;
    private ServiceStatus status = ServiceStatus.UNKNOWN;
    private long responseTimeMs;
    private String version;
    private String message;

    private LocalDateTime lastCheckedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public String getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public long getResponseTimeMs() {
        return responseTimeMs;
    }

    public String getVersion() {
        return version;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getLastCheckedAt() {
        return lastCheckedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status == null ? ServiceStatus.UNKNOWN : status;
    }

    public void setResponseTimeMs(long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLastCheckedAt(LocalDateTime lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
