package com.fc.service;

import com.fc.domain.CommentNotification;
import com.fc.domain.FollowNotification;
import com.fc.domain.LikeNotification;
import com.fc.service.converter.CommentUserNotificationConverter;
import com.fc.service.converter.FollowUserNotificationConverter;
import com.fc.service.converter.LikeUserNotificationConverter;
import com.fc.service.dto.ConvertedNotification;
import com.fc.service.dto.GetUserNotificationsByPivotResult;
import com.fc.service.dto.GetUserNotificationsResult;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class GetUserNotificationsService {

    private final NotificationListService listService;
    private final CommentUserNotificationConverter commentConverter;
    private final LikeUserNotificationConverter likeConverter;
    private final FollowUserNotificationConverter followConverter;

    public GetUserNotificationsService(NotificationListService listService,
                                       CommentUserNotificationConverter commentConverter,
                                       LikeUserNotificationConverter likeConverter, FollowUserNotificationConverter followConverter) {
        this.listService = listService;
        this.commentConverter = commentConverter;
        this.likeConverter = likeConverter;
        this.followConverter = followConverter;
    }

    public GetUserNotificationsResult getUserNotificationsByPivot(long userId, Instant pivot) {
        // 알림 목록 조회
        GetUserNotificationsByPivotResult result = listService.getUserNotificationsByPivot(userId, pivot);

        // MongoDB에 저장된 알림객체를 사용자에게 보여줄수 있는 형태로 변환
        List<ConvertedNotification> convertedNotifications = result.getNotifications().stream()
                .map(notification -> switch (notification.getType()) {
                    case COMMENT -> commentConverter.convert((CommentNotification) notification);
                    case LIKE -> likeConverter.convert((LikeNotification) notification);
                    case FOLLOW -> followConverter.convert((FollowNotification) notification);
                })
                .toList();

        return new GetUserNotificationsResult(
               convertedNotifications,
               result.isHasNext()
        );
    }
}
