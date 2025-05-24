package com.fc.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;

@Getter
@TypeAlias("CommentNotification") // 몽고 DB에 저장하고 다시 자바로 역직렬화할때 어떤 클래스 사용해서 매핑할건지 지정
public class CommentNotification extends Notification {
    private final long postId;
    private final long writerId;
    private final String comment;
    private final long commentId;

    public CommentNotification(String id, long userId, NotificationType type, Instant occurredAt, Instant createdAt,
                               Instant lastUpdatedAt, Instant deletedAt, long postId, long writerId, String comment,
                               long commentId) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.writerId = writerId;
        this.comment = comment;
        this.commentId = commentId;
    }
}
