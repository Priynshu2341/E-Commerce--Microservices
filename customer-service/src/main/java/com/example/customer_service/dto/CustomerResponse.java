package com.example.customer_service.dto;


import com.example.customer_service.model.Address;

public record CustomerResponse(
        String id,

        String firstname,

        String lastname,
        String email,

        Address address
) {
}
