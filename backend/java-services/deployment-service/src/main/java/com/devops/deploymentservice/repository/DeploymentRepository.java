package com.devops.deploymentservice.repository;

import com.devops.deploymentservice.model.Deployment;
import com.devops.deploymentservice.model.DeploymentEnvironment;
import com.devops.deploymentservice.model.DeploymentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeploymentRepository extends MongoRepository<Deployment, String> {
    List<Deployment> findByDeployedByOrderByCreatedAtDesc(String deployedBy);
    List<Deployment> findByProjectIdAndDeployedByOrderByCreatedAtDesc(String projectId, String deployedBy);
    List<Deployment> findByEnvironmentOrderByCreatedAtDesc(DeploymentEnvironment environment);
    List<Deployment> findByStatusOrderByCreatedAtDesc(DeploymentStatus status);
}
