package com.example.order_service.dtos.responsedtos;



import com.example.order_service.entity.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerID
) {
}
