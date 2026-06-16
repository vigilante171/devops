package com.devops.auditservice.repository;

import com.devops.auditservice.model.AuditAction;
import com.devops.auditservice.model.AuditEntityType;
import com.devops.auditservice.model.AuditEvent;
import com.devops.auditservice.model.AuditSeverity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditEventRepository extends MongoRepository<AuditEvent, String> {
    List<AuditEvent> findByActorEmailOrderByCreatedAtDesc(String actorEmail);
    List<AuditEvent> findByActionOrderByCreatedAtDesc(AuditAction action);
    List<AuditEvent> findBySeverityOrderByCreatedAtDesc(AuditSeverity severity);
    List<AuditEvent> findByEntityTypeOrderByCreatedAtDesc(AuditEntityType entityType);
    List<AuditEvent> findByServiceNameOrderByCreatedAtDesc(String serviceName);

    long countBySeverity(AuditSeverity severity);
    long countByAction(AuditAction action);
}
