package com.venom.ecommerce.dto;

import com.venom.ecommerce.model.Address;

public record CustomerResponse(
        String id,

        String firstname,

        String lastname,
        String email,

        Address address
) {
}
