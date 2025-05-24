package com.fc.repository;


import com.fc.domain.Notification;
import com.fc.domain.NotificationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    Optional<Notification> findById(String id);

    Notification save(Notification notification);

    void deleteById(String id);

    @Query("{ 'type': ?0, 'commentId': ?1 }")
    Optional<Notification> findByTypeAndCommentId(NotificationType type, long commentId);

    @Query("{ 'type': ?0, 'postId': ?1 }")
    Optional<Notification> findByTypeAndPostId(NotificationType type, long postId);

    @Query("{ 'type': ?0, 'userId': ?1, 'followerId': ?2 }")
    Optional<Notification> findByTypeAndUserIdAndFollowerId(NotificationType type, long userId, long followerId);

    // Slice는 다음 페이지 여부를 알 수 있다.
    // 사용자의 모든 알림을 발생 시간 내림차순으로 페이징 조회
    Slice<Notification> findAllByUserIdOrderByOccurredAtDesc(long userId, Pageable page);

    // 사용자의 특정 시간 이전 알림을 발생 시간 내림차순으로 조회
    Slice<Notification> findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(long userId, Instant occurredAt, Pageable pageable);

    // 사용자의 가장 최근에 업데이트된 알림 조회
    Optional<Notification> findFirstByUserIdOrderByLastUpdatedAtDesc(long userId);
}