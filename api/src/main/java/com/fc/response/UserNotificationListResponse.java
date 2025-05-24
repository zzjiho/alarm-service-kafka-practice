package com.fc.response;

import com.fc.service.dto.GetUserNotificationsResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserNotificationListResponse {
    private List<UserNotificationResponse> notifications;

    private boolean hasNext;

    private Instant pivot;

    public static UserNotificationListResponse of(GetUserNotificationsResult result) {
        List<UserNotificationResponse> notifications = result.getNotifications().stream()
                .map(UserNotificationResponse::of)
                .toList();

        Instant pivot = (result.isHasNext() && !notifications.isEmpty()) // 알림 없으면 pivot도 없음
                ? notifications.getLast().getOccurredAt() : null;

        return new UserNotificationListResponse(
                notifications,
                result.isHasNext(),
                pivot
        );
    }
}
