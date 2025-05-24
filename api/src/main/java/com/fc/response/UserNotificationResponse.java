package com.fc.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fc.domain.NotificationType;
import com.fc.service.dto.ConvertedCommentNotification;
import com.fc.service.dto.ConvertedFollowNotification;
import com.fc.service.dto.ConvertedLikeNotification;
import com.fc.service.dto.ConvertedNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommentUserNotificationResponse.class),
        @JsonSubTypes.Type(value = LikeUserNotificationResponse.class),
        @JsonSubTypes.Type(value = FollowUserNotificationResponse.class)
})
public abstract class UserNotificationResponse {

    private String id;

    private NotificationType type;

    private Instant occurredAt;

    public static UserNotificationResponse of(ConvertedNotification notification) {
        switch (notification.getType()) {
            case COMMENT -> {
                return CommentUserNotificationResponse.of((ConvertedCommentNotification) notification);
            }
            case LIKE -> {
                return LikeUserNotificationResponse.of((ConvertedLikeNotification) notification);
            }
            case FOLLOW -> {
                return FollowUserNotificationResponse.of((ConvertedFollowNotification) notification);
            }
            default -> throw new IllegalArgumentException("Unsupported notification type: " + notification.getType());
        }
    }
}
