package com.fc.service;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CheckNewNotificationService {

    private final NotificationGetService notificationGetService;
    private final LastReadAtService lastReadAtService;

    public CheckNewNotificationService(NotificationGetService notificationGetService, LastReadAtService lastReadAtService) {
        this.notificationGetService = notificationGetService;
        this.lastReadAtService = lastReadAtService;
    }

    public boolean checkNewNotification(long userId) {
        Instant latestUpdatedAt = notificationGetService.getLatestUpdatedAt(userId);
        if (latestUpdatedAt == null) {
            return false;
        }

        Instant lastReadAt = lastReadAtService.getLastReadAt(userId);
        if (lastReadAt == null) {
            return true;
        }

        return latestUpdatedAt.isAfter(lastReadAt);
    }
}
