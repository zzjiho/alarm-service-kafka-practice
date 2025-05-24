package com.fc.repository;

import com.fc.IntegrationTest;
import com.fc.domain.CommentNotification;
import com.fc.domain.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.Instant;
import java.util.Optional;

import static com.fc.domain.NotificationType.COMMENT;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

class NotificationRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private NotificationRepository sut;

    private final long userId = 2L;
    private final long postId = 3L;
    private final long writerId = 4L;
    private final long commentId = 5L;
    private final String comment = "comment";
    private final Instant now = Instant.now();
    private final Instant ninetyDaysAfter = Instant.now().plus(90, DAYS);

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 5; i++) {
            Instant occurredAt = now.minus(i, DAYS);
            sut.save(new CommentNotification("id-" + i, userId, COMMENT, occurredAt, now, now, ninetyDaysAfter,
                    postId, writerId, comment, commentId));
        }
    }

    @AfterEach
    void tearDown() {
        sut.deleteAll();
    }

    @Test
    void testSave() {
        String id = "1";
        sut.save(createCommentNotification(id));
        Optional<Notification> optionalNotification = sut.findById(id);

        assertTrue(optionalNotification.isPresent());
    }

    @Test
    void testFindById() {
        String id = "2";

        sut.save(createCommentNotification(id));
        Optional<Notification> optionalNotification = sut.findById(id);

        CommentNotification notification = (CommentNotification) optionalNotification.orElseThrow();
        assertEquals(notification.getId(), id);
        assertEquals(notification.getUserId(), userId);
        assertEquals(notification.getOccurredAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getCreatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getLastUpdatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getDeletedAt().getEpochSecond(), ninetyDaysAfter.getEpochSecond());
        assertEquals(notification.getPostId(), postId);
        assertEquals(notification.getWriterId(), writerId);
        assertEquals(notification.getComment(), comment);
        assertEquals(notification.getCommentId(), commentId);
    }

    @Test
    void testDeleteById() {
        String id = "3";

        sut.save(createCommentNotification(id));
        sut.deleteById(id);
        Optional<Notification> optionalNotification = sut.findById(id);

        assertFalse(optionalNotification.isPresent());
    }

    @DisplayName("사용자별 알림 목록 조회 테스트 (시간순 내림차순 정렬)")
    @Test
    void testFindAllByUserIdOrderByOccurredAtDesc() {

        // Given: 페이징 설정 (0페이지, 크기3)
        Pageable pageable = PageRequest.of(0, 3);

        // When: 최신순 알림 조회
        Slice<Notification> result = sut.findAllByUserIdOrderByOccurredAtDesc(userId, pageable);

        // Then: 결과 검증
        assertEquals(3, result.getContent().size()); // 요청한 크기 반환
        assertTrue(result.hasNext()); // 다음 페이지 존재 확인

        // 시간순 내림차순 정렬 검증 (최신->과거)
        Notification first = result.getContent().get(0);
        Notification second = result.getContent().get(1);
        Notification third = result.getContent().get(2);

        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
        assertTrue(second.getOccurredAt().isAfter(third.getOccurredAt()));
    }

    @DisplayName("특정 시점 이전의 알림들을 조회하는 기능 검증" +
            "현재 시점(now) 이전의 알림들이 올바르게 조회되고 정렬되는지 확인")
    @Test
    void testFindAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDescFirstQuery() {
        Pageable pageable = PageRequest.of(0, 3);

        Slice<Notification> result = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, now, pageable);

        assertEquals(3, result.getContent().size());
        assertTrue(result.hasNext());

        Notification first = result.getContent().get(0);
        Notification second = result.getContent().get(1);
        Notification third = result.getContent().get(2);

        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
        assertTrue(second.getOccurredAt().isAfter(third.getOccurredAt()));
    }

    @DisplayName("피벗 시점 활용하여 다음 페이지 조회 기능 검증")
    @Test
    void testFindAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDescSecondQueryWithPivot() {
        // given
        Pageable pageable = PageRequest.of(0, 3);
        Slice<Notification> firstResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, now, pageable);

        // 첫번째 결과의 마지막 항목 시간을 피벗으로 설정
        Notification last = firstResult.getContent().get(2);
        Instant pivot = last.getOccurredAt();

        // when: 피벗 시점 이전 알림 조회
        Slice<Notification> secondResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, pivot, pageable);

        // then
        assertEquals(2, secondResult.getContent().size());
        assertFalse(secondResult.hasNext());

        // 두번째 페이지도 시간순으로 정렬 잘 되어있는지 ㅇㅇ?
        Notification first = secondResult.getContent().get(0);
        Notification second = secondResult.getContent().get(1);

        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
    }

    private CommentNotification createCommentNotification(String id) {
        return new CommentNotification(id, userId, COMMENT, now, now, now, ninetyDaysAfter, postId, writerId, comment,
                commentId);
    }
}