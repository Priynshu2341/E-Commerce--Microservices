package com.example.ecommerce.dtos;

import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentResponse(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId
) {
}
