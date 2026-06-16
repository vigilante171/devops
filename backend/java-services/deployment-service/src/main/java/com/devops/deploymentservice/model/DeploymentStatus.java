package com.devops.deploymentservice.model;

public enum DeploymentStatus {
    CREATED,
    QUEUED,
    RUNNING,
    SUCCESS,
    FAILED,
    ROLLED_BACK,
    CANCELED
}
