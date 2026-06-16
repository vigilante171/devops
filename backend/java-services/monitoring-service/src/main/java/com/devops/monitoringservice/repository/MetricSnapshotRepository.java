package com.devops.monitoringservice.repository;

import com.devops.monitoringservice.model.MetricSnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MetricSnapshotRepository extends MongoRepository<MetricSnapshot, String> {
    List<MetricSnapshot> findTop20ByServiceNameOrderByCreatedAtDesc(String serviceName);
}
