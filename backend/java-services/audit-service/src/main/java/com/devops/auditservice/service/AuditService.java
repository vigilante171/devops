package com.devops.auditservice.service;

import com.devops.auditservice.dto.request.CreateAuditEventRequest;
import com.devops.auditservice.dto.response.AuditEventResponse;
import com.devops.auditservice.dto.response.AuditSummaryResponse;
import com.devops.auditservice.exception.ResourceNotFoundException;
import com.devops.auditservice.model.*;
import com.devops.auditservice.repository.AuditEventRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditService {

    private final AuditEventRepository auditEventRepository;
    private final MongoTemplate mongoTemplate;

    public AuditService(AuditEventRepository auditEventRepository, MongoTemplate mongoTemplate) {
        this.auditEventRepository = auditEventRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public AuditEventResponse create(CreateAuditEventRequest request, String authenticatedEmail) {
        AuditEvent event = new AuditEvent();

        String actorEmail = request.getActorEmail();
        if (actorEmail == null || actorEmail.isBlank()) {
            actorEmail = authenticatedEmail;
        }

        event.setActorEmail(actorEmail);
        event.setAction(request.getAction());
        event.setSeverity(request.getSeverity());
        event.setEntityType(request.getEntityType());
        event.setEntityId(request.getEntityId());
        event.setServiceName(request.getServiceName());
        event.setMessage(request.getMessage());
        event.setIpAddress(request.getIpAddress());
        event.setUserAgent(request.getUserAgent());
        event.setMetadata(request.getMetadata());

        return toResponse(auditEventRepository.save(event));
    }

    public List<AuditEventResponse> search(
            String actorEmail,
            AuditAction action,
            AuditSeverity severity,
            AuditEntityType entityType,
            String serviceName,
            String entityId
    ) {
        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();

        if (actorEmail != null && !actorEmail.isBlank()) {
            criteria.add(Criteria.where("actorEmail").is(actorEmail));
        }

        if (action != null) {
            criteria.add(Criteria.where("action").is(action));
        }

        if (severity != null) {
            criteria.add(Criteria.where("severity").is(severity));
        }

        if (entityType != null) {
            criteria.add(Criteria.where("entityType").is(entityType));
        }

        if (serviceName != null && !serviceName.isBlank()) {
            criteria.add(Criteria.where("serviceName").is(serviceName));
        }

        if (entityId != null && !entityId.isBlank()) {
            criteria.add(Criteria.where("entityId").is(entityId));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        query.limit(200);

        return mongoTemplate.find(query, AuditEvent.class)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AuditEventResponse> mine(String actorEmail) {
        return auditEventRepository.findByActorEmailOrderByCreatedAtDesc(actorEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AuditEventResponse findById(String id) {
        return auditEventRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Audit event not found"));
    }

    public AuditSummaryResponse summary() {
        return new AuditSummaryResponse(
                auditEventRepository.count(),
                auditEventRepository.countBySeverity(AuditSeverity.INFO),
                auditEventRepository.countBySeverity(AuditSeverity.LOW),
                auditEventRepository.countBySeverity(AuditSeverity.MEDIUM),
                auditEventRepository.countBySeverity(AuditSeverity.HIGH),
                auditEventRepository.countBySeverity(AuditSeverity.CRITICAL),
                auditEventRepository.countByAction(AuditAction.LOGIN),
                auditEventRepository.countByAction(AuditAction.DEPLOY),
                auditEventRepository.countByAction(AuditAction.ROLLBACK),
                auditEventRepository.countByAction(AuditAction.RUN_PIPELINE)
        );
    }

    public void delete(String id) {
        if (!auditEventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Audit event not found");
        }

        auditEventRepository.deleteById(id);
    }

    private AuditEventResponse toResponse(AuditEvent event) {
        return new AuditEventResponse(
                event.getId(),
                event.getActorEmail(),
                event.getAction(),
                event.getSeverity(),
                event.getEntityType(),
                event.getEntityId(),
                event.getServiceName(),
                event.getMessage(),
                event.getIpAddress(),
                event.getUserAgent(),
                event.getMetadata(),
                event.getCreatedAt()
        );
    }
}
