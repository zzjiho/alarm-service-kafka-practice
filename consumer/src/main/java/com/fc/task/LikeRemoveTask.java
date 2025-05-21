package com.fc.task;

import com.fc.domain.LikeNotification;
import com.fc.domain.Notification;
import com.fc.event.LikeEvent;
import com.fc.service.NotificationGetService;
import com.fc.service.NotificationRemoveService;
import com.fc.service.NotificationSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

import static com.fc.domain.NotificationType.LIKE;

@Slf4j
@Component
public class LikeRemoveTask {

    private final NotificationGetService getService;

    private final NotificationRemoveService removeService;

    private final NotificationSaveService saveService;

    public LikeRemoveTask(NotificationGetService getService, NotificationRemoveService removeService, NotificationSaveService saveService) {
        this.getService = getService;
        this.removeService = removeService;
        this.saveService = saveService;
    }

    public void processEvent(LikeEvent event) {
        Optional<Notification> optionalNotification = getService.getNotificationByTypeAndPostId(LIKE, event.getPostId());
        if (optionalNotification.isEmpty()) {
            log.error("No notification with postId: {}", event.getPostId());
            return;
        }

        LikeNotification notification = (LikeNotification) optionalNotification.get();
        removeLikerAndUpdateNotification(notification, event);
    }

    private void removeLikerAndUpdateNotification(LikeNotification notification, LikeEvent event) {
        notification.removeLiker(event.getUserId(), Instant.now());

        if (notification.getLikerIds().isEmpty()) {
            removeService.deleteById(notification.getId());
        } else {
            saveService.upsert(notification);
        }
    }
}
