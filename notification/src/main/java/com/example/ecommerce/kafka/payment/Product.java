package com.example.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record Product(
        Integer productID,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
