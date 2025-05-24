package com.fc.controller;

import com.fc.response.UserNotificationListResponse;
import com.fc.service.GetUserNotificationsService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/v1/user-notifications")
public class UserNotificationListController implements UserNotificationListControllerSpec {

    private final GetUserNotificationsService getUserNotificationsService;

    public UserNotificationListController(GetUserNotificationsService getUserNotificationsService) {
        this.getUserNotificationsService = getUserNotificationsService;
    }

    @Override
    @GetMapping("/{userId}")
    public UserNotificationListResponse getNotifications(
            @PathVariable(value = "userId") long userId,
            @RequestParam(value = "pivot", required = false) Instant pivot
    ) {
        return UserNotificationListResponse.of(
                getUserNotificationsService.getUserNotificationsByPivot(userId, pivot)
        );
    }
}
