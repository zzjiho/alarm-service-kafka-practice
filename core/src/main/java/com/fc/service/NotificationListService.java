package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import com.fc.service.dto.GetUserNotificationsByPivotResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationListService {

    private final NotificationRepository repository;

    public NotificationListService(NotificationRepository repository) {
        this.repository = repository;
    }


    /**
     * 목록조회: Pivot 방식, Paging 방식이 있음
     * Pivot은 기준점을 갖는다. 특정시간을 기준점으로 잡고 그 이후에 데이터를 몇개 가져오는 방식
     * Paging은 새로운 데이터가 들어오게되면 순서 보장이 안된다. 그래서 실시간성으로 데이터 많이 바뀌는곳에선 안씀
     */
    public GetUserNotificationsByPivotResult getUserNotificationsByPivot(long userId, Instant occurredAt) {
        Slice<Notification> result;

        if (occurredAt == null) {
            result = repository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0, PAGE_SIZE));
        } else {
            // occurredAt 기준점부터 항상 20개씩만 가져온다.
            result = repository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }

        return new GetUserNotificationsByPivotResult(
                result.toList(),
                result.hasNext()
        );
    }

    private static final int PAGE_SIZE = 20;
}
