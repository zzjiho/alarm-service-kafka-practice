package com.fc.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;
import java.util.List;

@Getter
@TypeAlias("LikeNotification")
public class LikeNotification extends Notification {
    private final long postId;
    private final List<Long> likerIds;

    public LikeNotification(String id, long userId, NotificationType type, Instant occurredAt, Instant createdAt,
                            Instant lastUpdatedAt, Instant deletedAt, long postId, List<Long> likerIds) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.likerIds = likerIds;
    }

    // 리치모델관련: ddd책 읽기
    public void addLiker(long likerId, Instant occurredAt, Instant now, Instant retention) {
        this.likerIds.add(likerId);
        this.setOccurredAt(occurredAt); // 좋아요 눌렀을때 occuredAt을 갱신해줘야 알림 리스트에서 맨 처음에 노출시킴
        this.setLastUpdatedAt(now);
        this.setDeletedAt(retention);
    }

    public void removeLiker(long userId, Instant now) {
        this.likerIds.remove(userId);
        this.setLastUpdatedAt(now); // 좋아요 취소시에는 최신으로 노출될 필요가 없어서, 이 필드만 갱신
    }
}
