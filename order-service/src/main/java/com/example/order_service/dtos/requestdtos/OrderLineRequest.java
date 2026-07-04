package com.example.order_service.dtos.requestdtos;

public record OrderLineRequest(
        Integer id,
        Integer orderID,
        Integer productID,
        double quantity
) {

}


