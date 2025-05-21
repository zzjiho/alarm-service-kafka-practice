package com.fc.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;

@Getter
@TypeAlias("FollowNotification")
public class FollowNotification extends Notification {
    private final long followerId;

    public FollowNotification(String id, long userId, NotificationType type, Instant occurredAt, Instant createdAt,
                              Instant lastUpdatedAt, Instant deletedAt, long followerId) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.followerId = followerId;
    }
}
