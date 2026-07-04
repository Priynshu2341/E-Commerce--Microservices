package com.example.order_service.kafka;



import com.example.order_service.dtos.responsedtos.CustomerResponse;
import com.example.order_service.dtos.responsedtos.PurchaseResponse;
import com.example.order_service.entity.PaymentMethod;

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
