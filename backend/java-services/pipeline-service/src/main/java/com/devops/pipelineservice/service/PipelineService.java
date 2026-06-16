package com.devops.pipelineservice.service;

import com.devops.pipelineservice.dto.request.CreatePipelineRequest;
import com.devops.pipelineservice.dto.request.RunPipelineRequest;
import com.devops.pipelineservice.dto.response.PipelineResponse;
import com.devops.pipelineservice.dto.response.PipelineRunResponse;
import com.devops.pipelineservice.exception.ResourceNotFoundException;
import com.devops.pipelineservice.model.*;
import com.devops.pipelineservice.repository.PipelineRepository;
import com.devops.pipelineservice.repository.PipelineRunRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PipelineService {

    private final PipelineRepository pipelineRepository;
    private final PipelineRunRepository runRepository;

    public PipelineService(PipelineRepository pipelineRepository, PipelineRunRepository runRepository) {
        this.pipelineRepository = pipelineRepository;
        this.runRepository = runRepository;
    }

    public PipelineResponse create(CreatePipelineRequest request, String ownerEmail) {
        PipelineDefinition pipeline = new PipelineDefinition();
        pipeline.setProjectId(request.getProjectId());
        pipeline.setName(request.getName());
        pipeline.setDescription(request.getDescription());
        pipeline.setBranch(request.getBranch());
        pipeline.setOwnerEmail(ownerEmail);
        pipeline.setSteps(normalizeSteps(request.getSteps()));

        return toResponse(pipelineRepository.save(pipeline));
    }

    public List<PipelineResponse> findMine(String ownerEmail) {
        return pipelineRepository.findByOwnerEmail(ownerEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PipelineResponse> findByProject(String projectId, String ownerEmail) {
        return pipelineRepository.findByProjectIdAndOwnerEmail(projectId, ownerEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PipelineResponse findById(String id) {
        return pipelineRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline not found"));
    }

    public PipelineResponse updateStatus(String id, PipelineStatus status) {
        PipelineDefinition pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline not found"));

        pipeline.setStatus(status == null ? PipelineStatus.ACTIVE : status);
        return toResponse(pipelineRepository.save(pipeline));
    }

    public void delete(String id) {
        if (!pipelineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pipeline not found");
        }

        pipelineRepository.deleteById(id);
    }

    public PipelineRunResponse run(String pipelineId, RunPipelineRequest request, String triggeredBy) {
        PipelineDefinition pipeline = pipelineRepository.findById(pipelineId)
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline not found"));

        PipelineRun run = new PipelineRun();
        run.setPipelineId(pipeline.getId());
        run.setProjectId(pipeline.getProjectId());
        run.setPipelineName(pipeline.getName());
        run.setBranch(request.getBranch() == null || request.getBranch().isBlank() ? pipeline.getBranch() : request.getBranch());
        run.setCommitSha(request.getCommitSha());
        run.setTriggeredBy(triggeredBy);
        run.setStatus(PipelineRunStatus.RUNNING);
        run.setStartedAt(LocalDateTime.now());
        run.setStages(
                pipeline.getSteps()
                        .stream()
                        .sorted(Comparator.comparingInt(PipelineStep::getOrderIndex))
                        .map(step -> {
                            PipelineRunStage stage = new PipelineRunStage();
                            stage.setName(step.getName());
                            stage.setCommand(step.getCommand());
                            stage.setStatus(StageStatus.PENDING);
                            return stage;
                        })
                        .toList()
        );

        return toRunResponse(runRepository.save(run));
    }

    public List<PipelineRunResponse> findRuns(String pipelineId) {
        return runRepository.findByPipelineIdOrderByCreatedAtDesc(pipelineId)
                .stream()
                .map(this::toRunResponse)
                .toList();
    }

    public List<PipelineRunResponse> findMyRuns(String ownerEmail) {
        return runRepository.findByTriggeredByOrderByCreatedAtDesc(ownerEmail)
                .stream()
                .map(this::toRunResponse)
                .toList();
    }

    public PipelineRunResponse findRunById(String runId) {
        return runRepository.findById(runId)
                .map(this::toRunResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline run not found"));
    }

    public PipelineRunResponse completeRun(String runId, boolean success) {
        PipelineRun run = runRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline run not found"));

        run.setStatus(success ? PipelineRunStatus.SUCCESS : PipelineRunStatus.FAILED);
        run.setFinishedAt(LocalDateTime.now());

        run.getStages().forEach(stage -> {
            if (stage.getStatus() == StageStatus.PENDING || stage.getStatus() == StageStatus.RUNNING) {
                stage.setStatus(success ? StageStatus.SUCCESS : StageStatus.FAILED);
                stage.setFinishedAt(LocalDateTime.now());
            }
        });

        return toRunResponse(runRepository.save(run));
    }

    private List<PipelineStep> normalizeSteps(List<PipelineStep> steps) {
        if (steps == null || steps.isEmpty()) {
            return List.of(
                    new PipelineStep("Checkout", "git checkout", 1),
                    new PipelineStep("Build", "mvn clean package", 2),
                    new PipelineStep("Test", "mvn test", 3),
                    new PipelineStep("Security Scan", "dependency-check", 4),
                    new PipelineStep("Deploy", "deploy artifact", 5)
            );
        }

        return steps;
    }

    private PipelineResponse toResponse(PipelineDefinition pipeline) {
        return new PipelineResponse(
                pipeline.getId(),
                pipeline.getProjectId(),
                pipeline.getName(),
                pipeline.getDescription(),
                pipeline.getBranch(),
                pipeline.getOwnerEmail(),
                pipeline.getStatus(),
                pipeline.getSteps(),
                pipeline.getCreatedAt(),
                pipeline.getUpdatedAt()
        );
    }

    private PipelineRunResponse toRunResponse(PipelineRun run) {
        return new PipelineRunResponse(
                run.getId(),
                run.getPipelineId(),
                run.getProjectId(),
                run.getPipelineName(),
                run.getBranch(),
                run.getCommitSha(),
                run.getTriggeredBy(),
                run.getStatus(),
                run.getStages(),
                run.getCreatedAt(),
                run.getStartedAt(),
                run.getFinishedAt()
        );
    }
}
