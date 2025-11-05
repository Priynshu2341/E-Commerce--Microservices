package com.example.ecommerce.dtos.responsedtos;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
