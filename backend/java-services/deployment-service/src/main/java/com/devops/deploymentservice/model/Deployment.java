package com.devops.deploymentservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "deployments")
public class Deployment {

    @Id
    private String id;

    private String projectId;
    private String pipelineId;
    private String pipelineRunId;

    private String applicationName;
    private String version;
    private String artifactUrl;

    private DeploymentEnvironment environment;
    private DeploymentStrategy strategy = DeploymentStrategy.STANDARD;
    private DeploymentStatus status = DeploymentStatus.CREATED;

    private String deployedBy;
    private String notes;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

    private RollbackInfo rollbackInfo = new RollbackInfo();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public String getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public String getPipelineRunId() {
        return pipelineRunId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getVersion() {
        return version;
    }

    public String getArtifactUrl() {
        return artifactUrl;
    }

    public DeploymentEnvironment getEnvironment() {
        return environment;
    }

    public DeploymentStrategy getStrategy() {
        return strategy;
    }

    public DeploymentStatus getStatus() {
        return status;
    }

    public String getDeployedBy() {
        return deployedBy;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public RollbackInfo getRollbackInfo() {
        return rollbackInfo;
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

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public void setPipelineRunId(String pipelineRunId) {
        this.pipelineRunId = pipelineRunId;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName == null ? null : applicationName.trim();
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setArtifactUrl(String artifactUrl) {
        this.artifactUrl = artifactUrl;
    }

    public void setEnvironment(DeploymentEnvironment environment) {
        this.environment = environment;
    }

    public void setStrategy(DeploymentStrategy strategy) {
        this.strategy = strategy == null ? DeploymentStrategy.STANDARD : strategy;
    }

    public void setStatus(DeploymentStatus status) {
        this.status = status;
    }

    public void setDeployedBy(String deployedBy) {
        this.deployedBy = deployedBy;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void setRollbackInfo(RollbackInfo rollbackInfo) {
        this.rollbackInfo = rollbackInfo;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
