package com.example.ecommerce.dtos.requestdtos;

import com.example.ecommerce.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(

        Integer id,
        String reference,
        @Positive(message = "Order Amount Should Be Positive")
        BigDecimal amount,
        @NotNull(message = "Payment Method Cannot Be Null")
        PaymentMethod paymentMethod,
        @NotNull(message = "CustomerId Cannot be Null")
        String customerId,
        List<PurchaseRequest> products
) {
}
