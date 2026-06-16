package com.devops.pipelineservice.repository;

import com.devops.pipelineservice.model.PipelineDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PipelineRepository extends MongoRepository<PipelineDefinition, String> {
    List<PipelineDefinition> findByOwnerEmail(String ownerEmail);
    List<PipelineDefinition> findByProjectIdAndOwnerEmail(String projectId, String ownerEmail);
}
