package com.example.ecommerce.dtos;

import com.example.ecommerce.entity.Customer;
import com.example.ecommerce.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
