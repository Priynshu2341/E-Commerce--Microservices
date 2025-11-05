package com.example.ecommerce.client;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}