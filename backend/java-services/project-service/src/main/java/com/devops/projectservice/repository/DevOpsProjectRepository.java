package com.devops.projectservice.repository;

import com.devops.projectservice.model.DevOpsProject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DevOpsProjectRepository extends MongoRepository<DevOpsProject, String> {
    List<DevOpsProject> findByOwnerEmail(String ownerEmail);
}
