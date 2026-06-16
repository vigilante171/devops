package com.devops.monitoringservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateMetricSnapshotRequest {

    @NotBlank(message = "Service name is required")
    private String serviceName;

    private double cpuUsage;
    private double memoryUsage;
    private double diskUsage;
    private long requestCount;
    private long errorCount;
    private double averageResponseTimeMs;

    public String getServiceName() {
        return serviceName;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public double getDiskUsage() {
        return diskUsage;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public double getAverageResponseTimeMs() {
        return averageResponseTimeMs;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public void setDiskUsage(double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public void setRequestCount(long requestCount) {
        this.requestCount = requestCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    public void setAverageResponseTimeMs(double averageResponseTimeMs) {
        this.averageResponseTimeMs = averageResponseTimeMs;
    }
}
