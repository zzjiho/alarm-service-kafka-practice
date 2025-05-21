package com.fc.task;

import com.fc.client.CommentClient;
import com.fc.client.PostClient;
import com.fc.domain.*;
import com.fc.event.CommentEvent;
import com.fc.service.NotificationSaveService;
import com.fc.utils.NotificationIdGenerator;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
public class CommentAddTask {

    private final PostClient postClient;

    private final CommentClient commentClient;

    private final NotificationSaveService saveService;

    public CommentAddTask(PostClient postClient, CommentClient commentClient, NotificationSaveService saveService) {
        this.postClient = postClient;
        this.commentClient = commentClient;
        this.saveService = saveService;
    }

    public void processEvent(CommentEvent event) {
        Post post = postClient.getPost(event.getPostId());

        // 내가작성한 댓글은 알림 X
        if (Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        Comment comment = commentClient.getComment(event.getCommentId());

        // 알림생성
        Notification notification = createNotification(post, comment);

        // 저장
        saveService.insert(notification);
    }

    private Notification createNotification(Post post, Comment comment) {
        Instant now = Instant.now();

        return new CommentNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                NotificationType.COMMENT,
                comment.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                post.getId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getId()
        );
    }
}
