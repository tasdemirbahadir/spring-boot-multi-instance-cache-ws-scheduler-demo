package com.millimetric.demo.redis.model;

public record MemberPushNotification(
        String title,
        String message,
        String date
) {
}
