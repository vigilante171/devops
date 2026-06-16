package com.devops.deploymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RollbackDeploymentRequest {

    @NotBlank(message = "Rollback target version is required")
    private String rollbackTargetVersion;

    private String reason;

    public String getRollbackTargetVersion() {
        return rollbackTargetVersion;
    }

    public String getReason() {
        return reason;
    }

    public void setRollbackTargetVersion(String rollbackTargetVersion) {
        this.rollbackTargetVersion = rollbackTargetVersion;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
