package com.fc.task;

import com.fc.domain.NotificationType;
import com.fc.event.FollowEvent;
import com.fc.service.NotificationGetService;
import com.fc.service.NotificationRemoveService;
import org.springframework.stereotype.Component;

@Component
public class FollowRemoveTask {

    private final NotificationGetService getService;

    private final NotificationRemoveService removeService;

    public FollowRemoveTask(NotificationGetService getService, NotificationRemoveService removeService) {
        this.getService = getService;
        this.removeService = removeService;
    }

    public void processEvent(FollowEvent event) {
        getService.getNotificationByTypeAndUserIdAndFollowerId(NotificationType.FOLLOW, event.getTargetUserId(),
                        event.getUserId())
                .ifPresent(
                        notification -> removeService.deleteById(notification.getId())
                );
    }
}
