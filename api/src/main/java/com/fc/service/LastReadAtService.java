package com.fc.service;

import com.fc.repository.NotificationReadRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class LastReadAtService {

    private final NotificationReadRepository repository;

    public LastReadAtService(NotificationReadRepository repository) {
        this.repository = repository;
    }

    public Instant setLastReadAt(long userId) {
        return repository.setLastReadAt(userId);
    }

    public Instant getLastReadAt(long userId) {
        return repository.getLastReadAt(userId);
    }
}
