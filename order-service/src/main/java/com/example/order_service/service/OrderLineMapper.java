package com.example.order_service.service;

import com.example.order_service.dtos.requestdtos.OrderLineRequest;
import com.example.order_service.dtos.responsedtos.OrderLineResponse;
import com.example.order_service.entity.OrderLine;
import com.example.order_service.entity.Orders;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {


    public OrderLine toOrder(OrderLineRequest orderLineRequest) {
        return OrderLine
                .builder()
                .id(orderLineRequest.id())
                .stockId(orderLineRequest.productID())
                .order(Orders.builder().id(orderLineRequest.orderID()).build())
                .quantity(orderLineRequest.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
