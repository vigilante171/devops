package com.devops.deploymentservice.dto.request;

import com.devops.deploymentservice.model.DeploymentStatus;

public class UpdateDeploymentStatusRequest {
    private DeploymentStatus status;
    private String notes;

    public DeploymentStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public void setStatus(DeploymentStatus status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
