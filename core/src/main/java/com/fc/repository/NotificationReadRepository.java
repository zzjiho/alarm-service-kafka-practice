package com.fc.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Repository
public class NotificationReadRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public NotificationReadRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 유저의 읽은 시간을 기록
    // 기록해야 어디까지 읽었는지 알 수 있다.
    // occurredAt 시간과 비교를 해서 LastReadAt이 이전이다 라고 하면 안읽은 알림 처리
    // 새 알림 표시 할때도 알림들의 가장 최신 업데이트시간과 읽은시간을 비교해서 읽은시간이 더 이전이면 빨간점 뜨게 ㅇㅇㅇ
    public Instant setLastReadAt(long userId) {
        long lastReadAt = Instant.now().toEpochMilli();
        String key = getKey(userId);
        redisTemplate.opsForValue().set(key, String.valueOf(lastReadAt));
        redisTemplate.expire(key, 90, TimeUnit.DAYS); // TTL
        return Instant.ofEpochMilli(lastReadAt);
    }

    public Instant getLastReadAt(long userId) {
        String key = getKey(userId);
        String lastReadAtStr = redisTemplate.opsForValue().get(key);
        if (lastReadAtStr == null) {
            return null;
        }

        long lastReadAtLong = Long.parseLong(lastReadAtStr);
        return Instant.ofEpochMilli(lastReadAtLong);
    }

    private String getKey(long userId) {
        return userId + ":lastReadAt"; // redis에서 키에 여러 값 조합할때 : 이거 많이씀.
    }
}
