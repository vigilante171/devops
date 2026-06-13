package com.devops.projectservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "projects")
public class DevOpsProject {

    @Id
    private String id;

    @Indexed
    private String name;

    private String description;
    private String ownerEmail;

    private ProjectStatus status = ProjectStatus.ACTIVE;

    private RepositoryConfig repository;

    private List<DevOpsEnvironment> environments = new ArrayList<>();

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = Instant.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
        this.updatedAt = Instant.now();
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    public RepositoryConfig getRepository() {
        return repository;
    }

    public void setRepository(RepositoryConfig repository) {
        this.repository = repository;
        this.updatedAt = Instant.now();
    }

    public List<DevOpsEnvironment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<DevOpsEnvironment> environments) {
        this.environments = environments;
        this.updatedAt = Instant.now();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
