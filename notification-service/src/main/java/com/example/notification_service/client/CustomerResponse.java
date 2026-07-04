package com.example.notification_service.client;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}