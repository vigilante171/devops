package com.devops.notificationservice.service;

import com.devops.notificationservice.dto.request.CreateNotificationRequest;
import com.devops.notificationservice.dto.request.UpdateNotificationStatusRequest;
import com.devops.notificationservice.dto.response.NotificationResponse;
import com.devops.notificationservice.dto.response.NotificationSummaryResponse;
import com.devops.notificationservice.exception.ResourceNotFoundException;
import com.devops.notificationservice.model.Notification;
import com.devops.notificationservice.model.NotificationSeverity;
import com.devops.notificationservice.model.NotificationStatus;
import com.devops.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationResponse create(CreateNotificationRequest request, String authenticatedEmail) {
        Notification notification = new Notification();

        String recipient = request.getRecipientEmail();
        if (recipient == null || recipient.isBlank()) {
            recipient = authenticatedEmail;
        }

        notification.setRecipientEmail(recipient);
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setSeverity(request.getSeverity());
        notification.setChannel(request.getChannel());
        notification.setSourceService(request.getSourceService());
        notification.setProjectId(request.getProjectId());
        notification.setEntityId(request.getEntityId());
        notification.setActionUrl(request.getActionUrl());
        notification.setMetadata(request.getMetadata());
        notification.setStatus(NotificationStatus.UNREAD);

        return toResponse(notificationRepository.save(notification));
    }

    public List<NotificationResponse> findMine(String email, NotificationStatus status) {
        List<Notification> notifications = status == null
                ? notificationRepository.findByRecipientEmailOrderByCreatedAtDesc(email)
                : notificationRepository.findByRecipientEmailAndStatusOrderByCreatedAtDesc(email, status);

        return notifications.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<NotificationResponse> findByProject(String projectId) {
        return notificationRepository.findByProjectIdOrderByCreatedAtDesc(projectId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public NotificationResponse findById(String id) {
        return notificationRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
    }

    public NotificationResponse updateStatus(String id, UpdateNotificationStatusRequest request) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        NotificationStatus newStatus = request.getStatus() == null ? notification.getStatus() : request.getStatus();
        notification.setStatus(newStatus);

        if (newStatus == NotificationStatus.READ) {
            notification.setReadAt(LocalDateTime.now());
        }

        if (newStatus == NotificationStatus.ARCHIVED) {
            notification.setArchivedAt(LocalDateTime.now());
        }

        return toResponse(notificationRepository.save(notification));
    }

    public NotificationResponse markRead(String id) {
        UpdateNotificationStatusRequest request = new UpdateNotificationStatusRequest();
        request.setStatus(NotificationStatus.READ);
        return updateStatus(id, request);
    }

    public void markAllRead(String email) {
        List<Notification> unread = notificationRepository.findByRecipientEmailAndStatusOrderByCreatedAtDesc(email, NotificationStatus.UNREAD);

        unread.forEach(notification -> {
            notification.setStatus(NotificationStatus.READ);
            notification.setReadAt(LocalDateTime.now());
        });

        notificationRepository.saveAll(unread);
    }

    public NotificationSummaryResponse summary(String email) {
        long total = notificationRepository.findByRecipientEmailOrderByCreatedAtDesc(email).size();
        long unread = notificationRepository.countByRecipientEmailAndStatus(email, NotificationStatus.UNREAD);
        long criticalUnread = notificationRepository.countByRecipientEmailAndSeverityAndStatus(email, NotificationSeverity.CRITICAL, NotificationStatus.UNREAD);
        long warningUnread = notificationRepository.countByRecipientEmailAndSeverityAndStatus(email, NotificationSeverity.WARNING, NotificationStatus.UNREAD);

        return new NotificationSummaryResponse(total, unread, criticalUnread, warningUnread);
    }

    public void delete(String id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found");
        }

        notificationRepository.deleteById(id);
    }

    private NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getRecipientEmail(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getSeverity(),
                notification.getStatus(),
                notification.getChannel(),
                notification.getSourceService(),
                notification.getProjectId(),
                notification.getEntityId(),
                notification.getActionUrl(),
                notification.getMetadata(),
                notification.getReadAt(),
                notification.getArchivedAt(),
                notification.getCreatedAt()
        );
    }
}
