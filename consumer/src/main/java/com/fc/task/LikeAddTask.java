package com.fc.task;

import com.fc.client.PostClient;
import com.fc.domain.LikeNotification;
import com.fc.domain.Notification;
import com.fc.domain.Post;
import com.fc.event.LikeEvent;
import com.fc.service.NotificationGetService;
import com.fc.service.NotificationSaveService;
import com.fc.utils.NotificationIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.fc.domain.NotificationType.LIKE;

/**
 * 좋아요 이벤트를 처리하고 알림을 생성/관리하는 태스크
 */
@Slf4j
@Component
public class LikeAddTask {

    private final PostClient postClient;

    private final NotificationGetService getService;

    private final NotificationSaveService saveService;

    /**
     * LikeAddTask 생성자
     */
    public LikeAddTask(PostClient postClient, NotificationGetService getService, NotificationSaveService saveService) {
        this.postClient = postClient;
        this.getService = getService;
        this.saveService = saveService;
    }

    /**
     * 좋아요 이벤트를 처리하는 메소드
     * 1. 게시물 정보 조회
     * 2. 자신의 게시물 좋아요 확인 (자신의 게시물 좋아요는 알림 생성 안함)
     * 3. 알림 생성 또는 업데이트
     */
    public void processEvent(LikeEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if (post == null) {
            log.error("Post is null with postId:{}", event.getPostId());
            return;
        }

        if (post.getUserId() == event.getUserId()) {
            return;
        }

        saveService.upsert(createOrUpdateNotification(post, event)); // update + insert
    }

    /**
     * 알림을 생성하거나 업데이트하는 메소드
     * 1. 해당 게시물과 타입에 대한 알림이 이미 존재하는지 확인
     * 2. 존재하면 업데이트, 없으면 새로 생성
     */
    private Notification createOrUpdateNotification(Post post, LikeEvent event) {
        Optional<Notification> optionalNotification = getService.getNotificationByTypeAndPostId(LIKE, post.getId());

        Instant now = Instant.now();
        Instant retention = now.plus(90, ChronoUnit.DAYS);

        if (optionalNotification.isPresent()) {
            return updateNotification((LikeNotification) optionalNotification.get(), event, now, retention);
        } else {
            return createNotification(post, event, now, retention);
        }
    }

    /**
     * 기존 알림을 업데이트하는 메소드
     * 하나의 게시물에 여러 사람이 좋아요를 누르면, 매번 새 알림을 생성하는 대신 기존 알림에 좋아요 누른 사람(liker)을 추가
     */
    private Notification updateNotification(LikeNotification notification, LikeEvent event, Instant now, Instant retention) {
        notification.addLiker(event.getUserId(), event.getCreatedAt(), now, retention);
        return notification;
    }

    /**
     * 새로운 좋아요 알림을 생성하는 메소드
     */
    private Notification createNotification(Post post, LikeEvent event, Instant now, Instant retention) {
        return new LikeNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                LIKE,
                event.getCreatedAt(),
                now,
                now,
                retention,
                post.getId(),
                List.of(event.getUserId())
        );
    }
}
