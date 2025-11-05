package com.example.ecommerce.dtos.responsedtos;

import java.math.BigDecimal;

public record PurchaseResponse(

        Integer id,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
