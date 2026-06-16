package com.devops.notificationservice.repository;

import com.devops.notificationservice.model.Notification;
import com.devops.notificationservice.model.NotificationSeverity;
import com.devops.notificationservice.model.NotificationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByRecipientEmailOrderByCreatedAtDesc(String recipientEmail);
    List<Notification> findByRecipientEmailAndStatusOrderByCreatedAtDesc(String recipientEmail, NotificationStatus status);
    List<Notification> findByProjectIdOrderByCreatedAtDesc(String projectId);
    long countByRecipientEmailAndStatus(String recipientEmail, NotificationStatus status);
    long countByRecipientEmailAndSeverityAndStatus(String recipientEmail, NotificationSeverity severity, NotificationStatus status);
}
