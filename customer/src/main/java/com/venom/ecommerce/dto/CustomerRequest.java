package com.venom.ecommerce.dto;

import com.venom.ecommerce.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,

        @NotNull(message = "Customer first name cannot be null ")
        String firstname,

        @NotNull(message = "Customer last name cannot be null ")
        String lastname,

        @NotNull(message = "Customer email cannot be null ")
        @Email(message = "Customer email is not valid ")
        String email,

        Address address
) {
}
