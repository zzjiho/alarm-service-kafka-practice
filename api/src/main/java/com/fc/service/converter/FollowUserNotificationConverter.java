package com.fc.service.converter;

import com.fc.client.UserClient;
import com.fc.domain.FollowNotification;
import com.fc.domain.User;
import com.fc.service.dto.ConvertedFollowNotification;
import org.springframework.stereotype.Service;

@Service
public class FollowUserNotificationConverter {

    private final UserClient userClient;

    public FollowUserNotificationConverter(UserClient userClient) {
        this.userClient = userClient;
    }

    public ConvertedFollowNotification convert(FollowNotification notification) {
        User user = userClient.getUser(notification.getFollowerId());
        boolean isFollowing = userClient.getIsFollowing(notification.getUserId(), notification.getFollowerId());

        return new ConvertedFollowNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                isFollowing
        );
    }
}
