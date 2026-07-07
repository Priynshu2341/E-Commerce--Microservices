package com.example.common.payment;

import java.math.BigDecimal;

public record PaymentResponse(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId
) {
}
