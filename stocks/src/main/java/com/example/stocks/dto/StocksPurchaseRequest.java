package com.example.stocks.dto;

import jakarta.validation.constraints.NotNull;

public record StocksPurchaseRequest(
        @NotNull(message = "Stock id is Mandatory")
        Integer id,

        @NotNull(message = "Stock Quantity is required")
        double quantity
) {
}
