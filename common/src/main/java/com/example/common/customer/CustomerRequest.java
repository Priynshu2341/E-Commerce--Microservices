package com.example.common.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,

        @NotBlank(message ="Customer first name cannot be null")
        String firstname,

        @NotBlank(message ="Customer last name cannot be null")
        String lastname,

        @NotBlank(message ="Customer email cannot be null")
        @Email(message ="Customer email is not valid")
        String email,

        Address address
) {
}
