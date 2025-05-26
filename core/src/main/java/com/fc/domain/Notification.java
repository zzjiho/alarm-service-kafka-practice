package com.fc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@Document("notifications") // 몽고db 어떤 컬렉션에 넣을거냐는 의미
public abstract class Notification {
    @Field(targetType = FieldType.STRING)   // ObjectId('123') -> "123"
    private String id;
    private long userId;
    private NotificationType type;

    // 알림 대상인 실제 이벤트가 발생한 시간 (사용자의 실제 액션(좋아요, 댓글 작성, 팔로우 등)이 발생한 시간)
    // 왜 createdAt으로 못쓰냐면 실제 이벤트 발생 시간과 알림 생성 시간 사이에 지연이 있을 수 있음
    // 시스템 지연으로 인해 알림이 5분 후에 생성되었다고 해도, 사용자는 실제 좋아요가 눌린 시간을 알고 싶겠지 ㅇㅇ?
    private Instant occurredAt;

    private Instant createdAt; // 알림 자체가 생성된 시간
    private Instant lastUpdatedAt;
    private Instant deletedAt;  // 알림이 삭제될 시간
}
