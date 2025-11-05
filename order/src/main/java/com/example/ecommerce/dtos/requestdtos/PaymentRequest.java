package com.example.ecommerce.dtos.requestdtos;

import com.example.ecommerce.dtos.responsedtos.CustomerResponse;
import com.example.ecommerce.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer

) {
}
