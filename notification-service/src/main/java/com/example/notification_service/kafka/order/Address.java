package com.example.notification_service.kafka.order;

public record Address(
        String street,
        String houseNumber,
        String zipCode
) {
}
