package com.fc.event;

import lombok.Data;

import java.time.Instant;

@Data
public class FollowEvent {
    private FollowEventType type;
    private long userId;
    private long targetUserId; // 누굴 팔로우햇냐 뭐 이런거
    private Instant createdAt;
}
