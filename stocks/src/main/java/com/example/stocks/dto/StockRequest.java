package com.example.stocks.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record StockRequest(

        Integer id,

        @NotNull(message = "Stock name is Required")
        String name,

        @NotNull(message = "Stock description is Required")
        String description,

        @Positive(message = "Available Quantity must be positive")
        double availableQuantity,
        @Positive(message = "Available Price must be positive")
        BigDecimal price,
        @NotNull(message = "Category id is required")
        Integer categoryId
) {
}
