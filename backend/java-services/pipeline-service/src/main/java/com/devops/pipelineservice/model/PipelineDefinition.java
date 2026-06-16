package com.devops.pipelineservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "pipelines")
public class PipelineDefinition {

    @Id
    private String id;

    private String projectId;
    private String name;
    private String description;
    private String branch;
    private String ownerEmail;
    private PipelineStatus status = PipelineStatus.ACTIVE;
    private List<PipelineStep> steps = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBranch() {
        return branch;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public PipelineStatus getStatus() {
        return status;
    }

    public List<PipelineStep> getSteps() {
        return steps;
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

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBranch(String branch) {
        this.branch = branch == null || branch.isBlank() ? "main" : branch.trim();
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setStatus(PipelineStatus status) {
        this.status = status;
    }

    public void setSteps(List<PipelineStep> steps) {
        this.steps = steps == null ? new ArrayList<>() : steps;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
