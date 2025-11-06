package com.example.stocks.dto;


import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "Stock id is Mandatory")
        Integer id,

        String name,
        String description,
        @NotNull(message = "Stock Quantity is required")
        double quantity
) {
}
