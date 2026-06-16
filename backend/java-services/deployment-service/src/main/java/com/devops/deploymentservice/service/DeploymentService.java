package com.devops.deploymentservice.service;

import com.devops.deploymentservice.dto.request.CreateDeploymentRequest;
import com.devops.deploymentservice.dto.request.RollbackDeploymentRequest;
import com.devops.deploymentservice.dto.request.UpdateDeploymentStatusRequest;
import com.devops.deploymentservice.dto.response.DeploymentResponse;
import com.devops.deploymentservice.exception.ResourceNotFoundException;
import com.devops.deploymentservice.model.*;
import com.devops.deploymentservice.repository.DeploymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeploymentService {

    private final DeploymentRepository deploymentRepository;

    public DeploymentService(DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    public DeploymentResponse create(CreateDeploymentRequest request, String deployedBy) {
        Deployment deployment = new Deployment();
        deployment.setProjectId(request.getProjectId());
        deployment.setPipelineId(request.getPipelineId());
        deployment.setPipelineRunId(request.getPipelineRunId());
        deployment.setApplicationName(request.getApplicationName());
        deployment.setVersion(request.getVersion());
        deployment.setArtifactUrl(request.getArtifactUrl());
        deployment.setEnvironment(request.getEnvironment());
        deployment.setStrategy(request.getStrategy());
        deployment.setNotes(request.getNotes());
        deployment.setDeployedBy(deployedBy);
        deployment.setStatus(DeploymentStatus.RUNNING);
        deployment.setStartedAt(LocalDateTime.now());

        RollbackInfo rollbackInfo = new RollbackInfo();
        rollbackInfo.setRollbackAvailable(true);
        rollbackInfo.setRollbackTargetVersion("previous-stable");
        deployment.setRollbackInfo(rollbackInfo);

        return toResponse(deploymentRepository.save(deployment));
    }

    public List<DeploymentResponse> findMine(String deployedBy) {
        return deploymentRepository.findByDeployedByOrderByCreatedAtDesc(deployedBy)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<DeploymentResponse> findByProject(String projectId, String deployedBy) {
        return deploymentRepository.findByProjectIdAndDeployedByOrderByCreatedAtDesc(projectId, deployedBy)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<DeploymentResponse> findByEnvironment(DeploymentEnvironment environment) {
        return deploymentRepository.findByEnvironmentOrderByCreatedAtDesc(environment)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<DeploymentResponse> findByStatus(DeploymentStatus status) {
        return deploymentRepository.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DeploymentResponse findById(String id) {
        return deploymentRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Deployment not found"));
    }

    public DeploymentResponse updateStatus(String id, UpdateDeploymentStatusRequest request) {
        Deployment deployment = deploymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deployment not found"));

        DeploymentStatus newStatus = request.getStatus() == null ? deployment.getStatus() : request.getStatus();
        deployment.setStatus(newStatus);

        if (request.getNotes() != null && !request.getNotes().isBlank()) {
            deployment.setNotes(request.getNotes());
        }

        if (newStatus == DeploymentStatus.SUCCESS || newStatus == DeploymentStatus.FAILED || newStatus == DeploymentStatus.CANCELED) {
            deployment.setFinishedAt(LocalDateTime.now());
        }

        return toResponse(deploymentRepository.save(deployment));
    }

    public DeploymentResponse rollback(String id, RollbackDeploymentRequest request, String rolledBackBy) {
        Deployment deployment = deploymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deployment not found"));

        RollbackInfo rollbackInfo = deployment.getRollbackInfo() == null ? new RollbackInfo() : deployment.getRollbackInfo();
        rollbackInfo.setRollbackAvailable(false);
        rollbackInfo.setRollbackTargetVersion(request.getRollbackTargetVersion());
        rollbackInfo.setRollbackReason(request.getReason());
        rollbackInfo.setRolledBackBy(rolledBackBy);
        rollbackInfo.setRolledBackAt(LocalDateTime.now());

        deployment.setRollbackInfo(rollbackInfo);
        deployment.setStatus(DeploymentStatus.ROLLED_BACK);
        deployment.setFinishedAt(LocalDateTime.now());

        return toResponse(deploymentRepository.save(deployment));
    }

    public void delete(String id) {
        if (!deploymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Deployment not found");
        }

        deploymentRepository.deleteById(id);
    }

    private DeploymentResponse toResponse(Deployment deployment) {
        return new DeploymentResponse(
                deployment.getId(),
                deployment.getProjectId(),
                deployment.getPipelineId(),
                deployment.getPipelineRunId(),
                deployment.getApplicationName(),
                deployment.getVersion(),
                deployment.getArtifactUrl(),
                deployment.getEnvironment(),
                deployment.getStrategy(),
                deployment.getStatus(),
                deployment.getDeployedBy(),
                deployment.getNotes(),
                deployment.getStartedAt(),
                deployment.getFinishedAt(),
                deployment.getRollbackInfo(),
                deployment.getCreatedAt(),
                deployment.getUpdatedAt()
        );
    }
}
