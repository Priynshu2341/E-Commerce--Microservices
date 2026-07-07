package com.example.order_service.service;



import com.example.common.order.OrderRequest;
import com.example.common.order.OrderResponse;
import com.example.order_service.entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMapper {


    public Orders toOrder(OrderRequest request) {
        return Orders
                .builder()
                .id(request.id())
                .customerID(request.customerId())
                .reference(request.reference())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderResponse fromOrder(Orders orders) {
        return new OrderResponse(
                orders.getId(),
                orders.getReference(),
                orders.getTotalAmount(),
                orders.getPaymentMethod(),
                orders.getCustomerID()
        );
    }

    public OrderResponse toOrderResponse(OrderRequest request) {
        return new OrderResponse(
                request.id(),
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                request.customerId()
        );
    }
}
