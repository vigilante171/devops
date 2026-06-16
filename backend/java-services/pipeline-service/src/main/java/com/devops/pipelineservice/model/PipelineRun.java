package com.devops.pipelineservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "pipeline_runs")
public class PipelineRun {

    @Id
    private String id;

    private String pipelineId;
    private String projectId;
    private String pipelineName;
    private String branch;
    private String commitSha;
    private String triggeredBy;
    private PipelineRunStatus status = PipelineRunStatus.CREATED;
    private List<PipelineRunStage> stages = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

    public String getId() {
        return id;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public String getBranch() {
        return branch;
    }

    public String getCommitSha() {
        return commitSha;
    }

    public String getTriggeredBy() {
        return triggeredBy;
    }

    public PipelineRunStatus getStatus() {
        return status;
    }

    public List<PipelineRunStage> getStages() {
        return stages;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCommitSha(String commitSha) {
        this.commitSha = commitSha;
    }

    public void setTriggeredBy(String triggeredBy) {
        this.triggeredBy = triggeredBy;
    }

    public void setStatus(PipelineRunStatus status) {
        this.status = status;
    }

    public void setStages(List<PipelineRunStage> stages) {
        this.stages = stages;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
}
