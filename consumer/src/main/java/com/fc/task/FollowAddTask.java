package com.fc.task;

import com.fc.domain.FollowNotification;
import com.fc.event.FollowEvent;
import com.fc.service.NotificationSaveService;
import com.fc.utils.NotificationIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.fc.domain.NotificationType.FOLLOW;

@Slf4j
@Component
public class FollowAddTask {

    private final NotificationSaveService saveService;

    public FollowAddTask(NotificationSaveService saveService) {
        this.saveService = saveService;
    }

    public void processEvent(FollowEvent event) {
        if (event.getTargetUserId() == event.getUserId()) {
            log.error("targetUserId and userId cannot be the same");
            return;
        }

        saveService.insert(createFollowNotification(event));
    }

    private FollowNotification createFollowNotification(FollowEvent event) {
        Instant now = Instant.now();

        return new FollowNotification(
                NotificationIdGenerator.generate(),
                event.getTargetUserId(),
                FOLLOW,
                event.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                event.getUserId()
        );
    }
}
