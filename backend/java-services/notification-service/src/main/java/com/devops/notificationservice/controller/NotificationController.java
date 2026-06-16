package com.devops.notificationservice.controller;

import com.devops.notificationservice.dto.request.CreateNotificationRequest;
import com.devops.notificationservice.dto.request.UpdateNotificationStatusRequest;
import com.devops.notificationservice.dto.response.NotificationResponse;
import com.devops.notificationservice.dto.response.NotificationSummaryResponse;
import com.devops.notificationservice.model.NotificationStatus;
import com.devops.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of(
                "service", "notification-service",
                "status", "running"
        );
    }

    @PostMapping
    public NotificationResponse create(
            @Valid @RequestBody CreateNotificationRequest request,
            Authentication authentication
    ) {
        return notificationService.create(request, authentication.getName());
    }

    @GetMapping
    public List<NotificationResponse> mine(
            @RequestParam(required = false) NotificationStatus status,
            Authentication authentication
    ) {
        return notificationService.findMine(authentication.getName(), status);
    }

    @GetMapping("/summary")
    public NotificationSummaryResponse summary(Authentication authentication) {
        return notificationService.summary(authentication.getName());
    }

    @GetMapping("/project/{projectId}")
    public List<NotificationResponse> byProject(@PathVariable String projectId) {
        return notificationService.findByProject(projectId);
    }

    @GetMapping("/{id}")
    public NotificationResponse get(@PathVariable String id) {
        return notificationService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public NotificationResponse updateStatus(
            @PathVariable String id,
            @RequestBody UpdateNotificationStatusRequest request
    ) {
        return notificationService.updateStatus(id, request);
    }

    @PostMapping("/{id}/read")
    public NotificationResponse markRead(@PathVariable String id) {
        return notificationService.markRead(id);
    }

    @PostMapping("/read-all")
    public void markAllRead(Authentication authentication) {
        notificationService.markAllRead(authentication.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        notificationService.delete(id);
    }
}
