package com.example.common.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product is Mandatory")
        Integer id,

        @Positive(message = "Quantity is Mandatory")
        double quantity
) {
}
