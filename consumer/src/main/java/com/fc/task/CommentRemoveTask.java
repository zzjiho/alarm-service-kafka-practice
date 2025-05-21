package com.fc.task;

import com.fc.client.PostClient;
import com.fc.domain.Post;
import com.fc.event.CommentEvent;
import com.fc.service.NotificationGetService;
import com.fc.service.NotificationRemoveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.fc.domain.NotificationType.COMMENT;

@Slf4j
@Component
public class CommentRemoveTask {

    private final PostClient postClient;

    private final NotificationGetService getService;

    private final NotificationRemoveService removeService;

    public CommentRemoveTask(PostClient postClient, NotificationGetService getService, NotificationRemoveService removeService) {
        this.postClient = postClient;
        this.getService = getService;
        this.removeService = removeService;
    }

    public void processEvent(CommentEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if (Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        getService.getNotificationByTypeAndCommentId(COMMENT, event.getCommentId())
                .ifPresent(
                        notification -> removeService.deleteById(notification.getId())
                );
    }
}
