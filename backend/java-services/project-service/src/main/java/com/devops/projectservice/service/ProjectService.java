package com.devops.projectservice.service;

import com.devops.projectservice.dto.request.CreateProjectRequest;
import com.devops.projectservice.dto.response.ProjectResponse;
import com.devops.projectservice.exception.ResourceNotFoundException;
import com.devops.projectservice.model.DevOpsProject;
import com.devops.projectservice.repository.DevOpsProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final DevOpsProjectRepository projectRepository;

    public ProjectService(DevOpsProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse create(CreateProjectRequest request, String ownerEmail) {
        DevOpsProject project = new DevOpsProject();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwnerEmail(ownerEmail);
        project.setRepository(request.getRepository());
        project.setEnvironments(request.getEnvironments());

        return toResponse(projectRepository.save(project));
    }

    public List<ProjectResponse> findMyProjects(String ownerEmail) {
        return projectRepository.findByOwnerEmail(ownerEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProjectResponse findById(String id) {
        return projectRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    public void delete(String id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found");
        }

        projectRepository.deleteById(id);
    }

    private ProjectResponse toResponse(DevOpsProject project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwnerEmail(),
                project.getStatus(),
                project.getRepository(),
                project.getEnvironments(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
