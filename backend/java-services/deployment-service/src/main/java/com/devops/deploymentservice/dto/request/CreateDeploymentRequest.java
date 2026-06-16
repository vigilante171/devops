package com.devops.deploymentservice.dto.request;

import com.devops.deploymentservice.model.DeploymentEnvironment;
import com.devops.deploymentservice.model.DeploymentStrategy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateDeploymentRequest {

    @NotBlank(message = "Project id is required")
    private String projectId;

    private String pipelineId;
    private String pipelineRunId;

    @NotBlank(message = "Application name is required")
    private String applicationName;

    @NotBlank(message = "Version is required")
    private String version;

    private String artifactUrl;

    @NotNull(message = "Environment is required")
    private DeploymentEnvironment environment;

    private DeploymentStrategy strategy = DeploymentStrategy.STANDARD;
    private String notes;

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

    public String getNotes() {
        return notes;
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
        this.applicationName = applicationName;
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
        this.strategy = strategy;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
