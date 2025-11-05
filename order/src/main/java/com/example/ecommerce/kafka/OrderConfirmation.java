package com.example.ecommerce.kafka;

import com.example.ecommerce.dtos.responsedtos.CustomerResponse;
import com.example.ecommerce.dtos.responsedtos.PurchaseResponse;
import com.example.ecommerce.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
