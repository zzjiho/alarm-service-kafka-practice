package com.fc;

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
@Document("notifications")
public class Notification {
    @Field(targetType = FieldType.STRING)   // ObjectId('123') -> "123"
    private String id;
    private long userId;
    private NotificationType type;
    private Instant occurredAt; // 알림 대상인 실제 이벤트가 발생한 시간
    private Instant createdAt;
    private Instant lastUpdatedAt;
    private Instant deletedAt;  // 알림이 삭제될 시간
}
