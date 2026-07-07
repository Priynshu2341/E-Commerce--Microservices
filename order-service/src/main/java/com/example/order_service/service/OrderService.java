package com.example.order_service.service;


import com.example.common.order.OrderConfirmation;
import com.example.common.order.OrderRequest;
import com.example.common.order.OrderResponse;
import com.example.common.order.PurchaseRequest;
import com.example.common.payment.PaymentRequest;
import com.example.order_service.dtos.requestdtos.OrderLineRequest;
import com.example.order_service.exception.BusinessException;
import com.example.order_service.feignclient.CustomerClient;
import com.example.order_service.feignclient.PaymentClient;
import com.example.order_service.feignclient.ProductClient;
import com.example.order_service.kafka.OrderProducer;
import com.example.order_service.rep.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final CustomerClient customerClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public OrderResponse createOrder(OrderRequest request) throws JsonProcessingException {
        var customer = customerClient.findCustomerByID(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot Create Customer:: No Customer Exist With ID" + request.customerId()));

        log.info("found customer {}",customer);
        var purchasedProducts = this.productClient.purchaseResponses(request.products());
        var order = orderRepository.save(mapper.toOrder(request));

        log.info("found purchasedProducts {}",purchasedProducts);

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.id(),
                            purchaseRequest.quantity()
                    )
            );
        }
        log.info("saved order {}",order);

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                request.reference(),
                customer
        );
        log.info("sending paymentRequest {}",paymentRequest);

        paymentClient.requestOrderPayment(paymentRequest);

        log.info("order confirmation");
        orderProducer.sendOrderConformation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts

                )
        );
        log.info("order success");
        return mapper.toOrderResponse(request);

    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();

    }

    public OrderResponse findById(Integer id) {
        return orderRepository.findById(id)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("No Order Found with Provided Id " + id));
    }
}
