package com.example.stocks.dto;

import java.math.BigDecimal;

public record StocksPurchaseResponse(

        Integer id,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
