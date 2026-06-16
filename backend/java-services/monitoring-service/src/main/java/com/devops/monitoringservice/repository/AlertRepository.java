package com.devops.monitoringservice.repository;

import com.devops.monitoringservice.model.Alert;
import com.devops.monitoringservice.model.AlertSeverity;
import com.devops.monitoringservice.model.AlertStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {
    List<Alert> findByStatusOrderByCreatedAtDesc(AlertStatus status);
    List<Alert> findBySeverityOrderByCreatedAtDesc(AlertSeverity severity);
    List<Alert> findByProjectIdOrderByCreatedAtDesc(String projectId);
    long countByStatus(AlertStatus status);
    long countBySeverityAndStatus(AlertSeverity severity, AlertStatus status);
}
