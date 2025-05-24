package com.fc.response;

import com.fc.domain.NotificationType;
import com.fc.service.dto.ConvertedCommentNotification;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CommentUserNotificationResponse extends UserNotificationResponse {

    private final String userName;

    private final String userProfileImage;

    private final String comment;

    private final String postImageUrl;

    public CommentUserNotificationResponse(String id, NotificationType type, Instant occurredAt, String userName,
                                           String userProfileImage, String comment, String postImageUrl) {
        super(id, type, occurredAt);
        this.userName = userName;
        this.userProfileImage = userProfileImage;
        this.comment = comment;
        this.postImageUrl = postImageUrl;
    }

    public static CommentUserNotificationResponse of(ConvertedCommentNotification notification) {
        return new CommentUserNotificationResponse(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getUserName(),
                notification.getUserProfileImageUrl(),
                notification.getComment(),
                notification.getPostImageUrl()
        );
    }
}
