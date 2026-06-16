package com.devops.pipelineservice.controller;

import com.devops.pipelineservice.dto.request.CreatePipelineRequest;
import com.devops.pipelineservice.dto.request.RunPipelineRequest;
import com.devops.pipelineservice.dto.request.UpdatePipelineStatusRequest;
import com.devops.pipelineservice.dto.response.PipelineResponse;
import com.devops.pipelineservice.dto.response.PipelineRunResponse;
import com.devops.pipelineservice.service.PipelineService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pipelines")
public class PipelineController {

    private final PipelineService pipelineService;

    public PipelineController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
                "service", "pipeline-service",
                "status", "running"
        );
    }

    @PostMapping
    public PipelineResponse create(
            @Valid @RequestBody CreatePipelineRequest request,
            Authentication authentication
    ) {
        return pipelineService.create(request, authentication.getName());
    }

    @GetMapping
    public List<PipelineResponse> mine(Authentication authentication) {
        return pipelineService.findMine(authentication.getName());
    }

    @GetMapping("/project/{projectId}")
    public List<PipelineResponse> byProject(
            @PathVariable String projectId,
            Authentication authentication
    ) {
        return pipelineService.findByProject(projectId, authentication.getName());
    }

    @GetMapping("/{id}")
    public PipelineResponse get(@PathVariable String id) {
        return pipelineService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public PipelineResponse updateStatus(
            @PathVariable String id,
            @RequestBody UpdatePipelineStatusRequest request
    ) {
        return pipelineService.updateStatus(id, request.getStatus());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        pipelineService.delete(id);
    }

    @PostMapping("/{id}/runs")
    public PipelineRunResponse run(
            @PathVariable String id,
            @RequestBody RunPipelineRequest request,
            Authentication authentication
    ) {
        return pipelineService.run(id, request == null ? new RunPipelineRequest() : request, authentication.getName());
    }

    @GetMapping("/{id}/runs")
    public List<PipelineRunResponse> runs(@PathVariable String id) {
        return pipelineService.findRuns(id);
    }

    @GetMapping("/runs")
    public List<PipelineRunResponse> myRuns(Authentication authentication) {
        return pipelineService.findMyRuns(authentication.getName());
    }

    @GetMapping("/runs/{runId}")
    public PipelineRunResponse runById(@PathVariable String runId) {
        return pipelineService.findRunById(runId);
    }

    @PostMapping("/runs/{runId}/complete")
    public PipelineRunResponse completeRun(
            @PathVariable String runId,
            @RequestParam(defaultValue = "true") boolean success
    ) {
        return pipelineService.completeRun(runId, success);
    }
}
