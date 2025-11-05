package com.example.ecommerce.dtos.requestdtos;

public record OrderLineRequest(
        Integer id,
        Integer orderID,
        Integer productID,
        double quantity
) {

}


