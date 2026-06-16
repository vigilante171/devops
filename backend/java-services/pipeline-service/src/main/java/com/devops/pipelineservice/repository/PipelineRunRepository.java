package com.devops.pipelineservice.repository;

import com.devops.pipelineservice.model.PipelineRun;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PipelineRunRepository extends MongoRepository<PipelineRun, String> {
    List<PipelineRun> findByPipelineIdOrderByCreatedAtDesc(String pipelineId);
    List<PipelineRun> findByTriggeredByOrderByCreatedAtDesc(String triggeredBy);
}
