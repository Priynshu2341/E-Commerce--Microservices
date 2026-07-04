package com.example.payment_service.dtos;



import com.example.payment_service.entity.Customer;
import com.example.payment_service.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
