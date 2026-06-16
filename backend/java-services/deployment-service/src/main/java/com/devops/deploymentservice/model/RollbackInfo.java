package com.devops.deploymentservice.model;

import java.time.LocalDateTime;

public class RollbackInfo {
    private boolean rollbackAvailable;
    private String rollbackTargetVersion;
    private String rollbackReason;
    private String rolledBackBy;
    private LocalDateTime rolledBackAt;

    public boolean isRollbackAvailable() {
        return rollbackAvailable;
    }

    public String getRollbackTargetVersion() {
        return rollbackTargetVersion;
    }

    public String getRollbackReason() {
        return rollbackReason;
    }

    public String getRolledBackBy() {
        return rolledBackBy;
    }

    public LocalDateTime getRolledBackAt() {
        return rolledBackAt;
    }

    public void setRollbackAvailable(boolean rollbackAvailable) {
        this.rollbackAvailable = rollbackAvailable;
    }

    public void setRollbackTargetVersion(String rollbackTargetVersion) {
        this.rollbackTargetVersion = rollbackTargetVersion;
    }

    public void setRollbackReason(String rollbackReason) {
        this.rollbackReason = rollbackReason;
    }

    public void setRolledBackBy(String rolledBackBy) {
        this.rolledBackBy = rolledBackBy;
    }

    public void setRolledBackAt(LocalDateTime rolledBackAt) {
        this.rolledBackAt = rolledBackAt;
    }
}
