package com.fc.service.converter;

import com.fc.client.PostClient;
import com.fc.client.UserClient;
import com.fc.domain.CommentNotification;
import com.fc.domain.Post;
import com.fc.domain.User;
import com.fc.service.dto.ConvertedCommentNotification;
import org.springframework.stereotype.Service;

@Service
public class CommentUserNotificationConverter {

    private final UserClient userClient;
    private final PostClient postClient;

    public CommentUserNotificationConverter(UserClient userClient, PostClient postClient) {
        this.userClient = userClient;
        this.postClient = postClient;
    }

    public ConvertedCommentNotification convert(CommentNotification notification) {
        User user = userClient.getUser(notification.getWriterId());
        Post post = postClient.getPost(notification.getPostId());

        return new ConvertedCommentNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                notification.getComment(),
                post.getImageUrl()
        );
    }
}
