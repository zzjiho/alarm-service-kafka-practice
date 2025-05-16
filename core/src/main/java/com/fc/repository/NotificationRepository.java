package com.fc.repository;


import com.fc.Notification;
import com.fc.NotificationType;
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

    Slice<Notification> findAllByUserIdOrderByOccurredAtDesc(long userId, Pageable page);

    Slice<Notification> findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(long userId, Instant occurredAt, Pageable pageable);

    Optional<Notification> findFirstByUserIdOrderByLastUpdatedAtDesc(long userId);
}