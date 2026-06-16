package com.devops.notificationservice.dto.request;

import com.devops.notificationservice.model.NotificationStatus;

public class UpdateNotificationStatusRequest {
    private NotificationStatus status;

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }
}
