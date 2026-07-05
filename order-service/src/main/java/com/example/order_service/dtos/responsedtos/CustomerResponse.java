package com.example.order_service.dtos.responsedtos;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        AddressResponse address
) {
}
