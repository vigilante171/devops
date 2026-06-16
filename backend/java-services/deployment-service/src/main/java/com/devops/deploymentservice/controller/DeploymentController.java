package com.devops.deploymentservice.controller;

import com.devops.deploymentservice.dto.request.CreateDeploymentRequest;
import com.devops.deploymentservice.dto.request.RollbackDeploymentRequest;
import com.devops.deploymentservice.dto.request.UpdateDeploymentStatusRequest;
import com.devops.deploymentservice.dto.response.DeploymentResponse;
import com.devops.deploymentservice.model.DeploymentEnvironment;
import com.devops.deploymentservice.model.DeploymentStatus;
import com.devops.deploymentservice.service.DeploymentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deployments")
public class DeploymentController {

    private final DeploymentService deploymentService;

    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
                "service", "deployment-service",
                "status", "running"
        );
    }

    @PostMapping
    public DeploymentResponse create(
            @Valid @RequestBody CreateDeploymentRequest request,
            Authentication authentication
    ) {
        return deploymentService.create(request, authentication.getName());
    }

    @GetMapping
    public List<DeploymentResponse> mine(Authentication authentication) {
        return deploymentService.findMine(authentication.getName());
    }

    @GetMapping("/project/{projectId}")
    public List<DeploymentResponse> byProject(
            @PathVariable String projectId,
            Authentication authentication
    ) {
        return deploymentService.findByProject(projectId, authentication.getName());
    }

    @GetMapping("/environment/{environment}")
    public List<DeploymentResponse> byEnvironment(@PathVariable DeploymentEnvironment environment) {
        return deploymentService.findByEnvironment(environment);
    }

    @GetMapping("/status/{status}")
    public List<DeploymentResponse> byStatus(@PathVariable DeploymentStatus status) {
        return deploymentService.findByStatus(status);
    }

    @GetMapping("/{id}")
    public DeploymentResponse get(@PathVariable String id) {
        return deploymentService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public DeploymentResponse updateStatus(
            @PathVariable String id,
            @RequestBody UpdateDeploymentStatusRequest request
    ) {
        return deploymentService.updateStatus(id, request);
    }

    @PostMapping("/{id}/rollback")
    public DeploymentResponse rollback(
            @PathVariable String id,
            @Valid @RequestBody RollbackDeploymentRequest request,
            Authentication authentication
    ) {
        return deploymentService.rollback(id, request, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        deploymentService.delete(id);
    }
}
