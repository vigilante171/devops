package com.devops.pipelineservice.dto.request;

import com.devops.pipelineservice.model.PipelineStep;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class CreatePipelineRequest {

    @NotBlank(message = "Project id is required")
    private String projectId;

    @NotBlank(message = "Pipeline name is required")
    private String name;

    private String description;
    private String branch = "main";
    private List<PipelineStep> steps = new ArrayList<>();

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

    public List<PipelineStep> getSteps() {
        return steps;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSteps(List<PipelineStep> steps) {
        this.steps = steps;
    }
}
