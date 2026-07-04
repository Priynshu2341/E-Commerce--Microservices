package com.example.order_service.dtos.requestdtos;



import com.example.order_service.dtos.responsedtos.CustomerResponse;
import com.example.order_service.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer

) {
}
