package com.devops.projectservice.dto.response;

import com.devops.projectservice.model.DevOpsEnvironment;
import com.devops.projectservice.model.ProjectStatus;
import com.devops.projectservice.model.RepositoryConfig;

import java.time.Instant;
import java.util.List;

public class ProjectResponse {

    private String id;
    private String name;
    private String description;
    private String ownerEmail;
    private ProjectStatus status;
    private RepositoryConfig repository;
    private List<DevOpsEnvironment> environments;
    private Instant createdAt;
    private Instant updatedAt;

    public ProjectResponse(
            String id,
            String name,
            String description,
            String ownerEmail,
            ProjectStatus status,
            RepositoryConfig repository,
            List<DevOpsEnvironment> environments,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerEmail = ownerEmail;
        this.status = status;
        this.repository = repository;
        this.environments = environments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public RepositoryConfig getRepository() {
        return repository;
    }

    public List<DevOpsEnvironment> getEnvironments() {
        return environments;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
