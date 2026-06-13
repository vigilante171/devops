package com.devops.projectservice.dto.request;

import com.devops.projectservice.model.DevOpsEnvironment;
import com.devops.projectservice.model.RepositoryConfig;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class CreateProjectRequest {

    @NotBlank(message = "Project name is required")
    private String name;

    private String description;

    private RepositoryConfig repository;

    private List<DevOpsEnvironment> environments = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RepositoryConfig getRepository() {
        return repository;
    }

    public void setRepository(RepositoryConfig repository) {
        this.repository = repository;
    }

    public List<DevOpsEnvironment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<DevOpsEnvironment> environments) {
        this.environments = environments;
    }
}
