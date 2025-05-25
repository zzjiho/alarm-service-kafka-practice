package com.fc.controller;

import com.fc.response.CheckNewNotificationResponse;
import com.fc.service.CheckNewNotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-notifications")
public class CheckNewNotificationController implements CheckNewNotificationControllerSpec {

    private final CheckNewNotificationService service;

    public CheckNewNotificationController(CheckNewNotificationService service) {
        this.service = service;
    }

    /**
     * 1. 사용자의 가장 최근 알림 업데이트 시간을 MongoDB에서 조회
     * 2. 사용자의 마지막 읽은 시간을 Redis에서 조회
     * 3. 두 시간을 비교하여 새 알림 여부 판단
     */
    @Override
    @GetMapping("/{userId}/new")
    public CheckNewNotificationResponse checkNew(
            @PathVariable(value = "userId") long userId
    ) {
        boolean hasNew = service.checkNewNotification(userId);
        return new CheckNewNotificationResponse(hasNew);
    }
}
