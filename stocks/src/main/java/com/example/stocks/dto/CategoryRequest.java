package com.example.stocks.dto;


import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        String name,
        String description,
        @NotNull(message = "Stock Quantity is required")
        double quantity
) {
}
