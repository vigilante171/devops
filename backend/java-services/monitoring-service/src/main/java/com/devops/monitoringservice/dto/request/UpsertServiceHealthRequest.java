package com.devops.monitoringservice.dto.request;

import com.devops.monitoringservice.model.ServiceStatus;
import jakarta.validation.constraints.NotBlank;

public class UpsertServiceHealthRequest {

    @NotBlank(message = "Service name is required")
    private String serviceName;

    private String serviceUrl;
    private ServiceStatus status = ServiceStatus.UNKNOWN;
    private long responseTimeMs;
    private String version;
    private String message;

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

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
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
}
