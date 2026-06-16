package com.devops.auditservice.controller;

import com.devops.auditservice.dto.request.CreateAuditEventRequest;
import com.devops.auditservice.dto.response.AuditEventResponse;
import com.devops.auditservice.dto.response.AuditSummaryResponse;
import com.devops.auditservice.model.AuditAction;
import com.devops.auditservice.model.AuditEntityType;
import com.devops.auditservice.model.AuditSeverity;
import com.devops.auditservice.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
                "service", "audit-service",
                "status", "running"
        );
    }

    @PostMapping("/events")
    public AuditEventResponse create(
            @Valid @RequestBody CreateAuditEventRequest request,
            Authentication authentication
    ) {
        return auditService.create(request, authentication.getName());
    }

    @GetMapping("/events")
    public List<AuditEventResponse> events(
            @RequestParam(required = false) String actorEmail,
            @RequestParam(required = false) AuditAction action,
            @RequestParam(required = false) AuditSeverity severity,
            @RequestParam(required = false) AuditEntityType entityType,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false) String entityId
    ) {
        return auditService.search(actorEmail, action, severity, entityType, serviceName, entityId);
    }

    @GetMapping("/events/me")
    public List<AuditEventResponse> mine(Authentication authentication) {
        return auditService.mine(authentication.getName());
    }

    @GetMapping("/events/{id}")
    public AuditEventResponse get(@PathVariable String id) {
        return auditService.findById(id);
    }

    @GetMapping("/summary")
    public AuditSummaryResponse summary() {
        return auditService.summary();
    }

    @DeleteMapping("/events/{id}")
    public void delete(@PathVariable String id) {
        auditService.delete(id);
    }
}
