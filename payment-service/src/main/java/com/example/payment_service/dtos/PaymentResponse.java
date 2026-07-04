package com.example.payment_service.dtos;



import com.example.payment_service.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentResponse(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId
) {
}
