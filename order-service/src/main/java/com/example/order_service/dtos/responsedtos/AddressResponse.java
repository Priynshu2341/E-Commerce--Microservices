package com.example.order_service.dtos.responsedtos;

public record AddressResponse(
        String street,
        String houseNumber,
        String zipCode
) {}