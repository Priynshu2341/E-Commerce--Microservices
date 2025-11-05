package com.example.stocks.dto;

import java.math.BigDecimal;

public record StockResponse(

         Integer id,
         String name,
         String description,
         double availableQuantity,
         BigDecimal price,
         Integer categoryId,
         String categoryName,
         String categoryDescription
) {
}
