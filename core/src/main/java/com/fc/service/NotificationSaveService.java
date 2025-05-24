package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationSaveService {

    private final NotificationRepository repository;

    public NotificationSaveService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void insert(Notification notification) { // insert는 기존 동일한 아이디가 있으면 예외 발생
        Notification result = repository.insert(notification);
        log.info("inserted: {}", result);
    }

    public void upsert(Notification notification) {
        Notification result = repository.save(notification);
        log.info("upserted: {}", result);
    }
}
