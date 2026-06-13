package com.devops.projectservice.controller;

import com.devops.projectservice.dto.request.CreateProjectRequest;
import com.devops.projectservice.dto.response.ProjectResponse;
import com.devops.projectservice.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
                "service", "project-service",
                "status", "running"
        );
    }

    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request,
            Authentication authentication
    ) {
        return projectService.create(request, authentication.getName());
    }

    @GetMapping
    public List<ProjectResponse> myProjects(Authentication authentication) {
        return projectService.findMyProjects(authentication.getName());
    }

    @GetMapping("/{id}")
    public ProjectResponse getProject(@PathVariable String id) {
        return projectService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.delete(id);
    }
}
