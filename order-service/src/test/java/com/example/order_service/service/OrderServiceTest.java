package com.example.order_service.service;

import com.example.order_service.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.common.customer.Address;
import com.example.common.customer.CustomerResponse;
import com.example.common.order.*;
import com.example.common.payment.PaymentMethod;
import com.example.common.payment.PaymentRequest;
import com.example.order_service.entity.Orders;
import com.example.order_service.feignclient.CustomerClient;
import com.example.order_service.feignclient.PaymentClient;
import com.example.order_service.feignclient.ProductClient;
import com.example.order_service.kafka.OrderProducer;
import com.example.order_service.rep.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductClient productClient;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private OrderMapper mapper;

    @Mock
    private OrderLineService orderLineService;

    @Mock
    private OrderProducer orderProducer;

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private OrderService orderService;

    @Captor
    private ArgumentCaptor<OrderConfirmation> orderConfirmationCaptor;

    @Captor
    private ArgumentCaptor<PaymentRequest> paymentRequestCaptor;

    @Test
    void shouldCreateOrder() throws Exception {



        OrderRequest request = new OrderRequest(
                null,
                "ORD-001",
                BigDecimal.valueOf(50000),
                PaymentMethod.PAYPAL,
                "customer-1",
                List.of(
                        new PurchaseRequest(1,2),
                        new PurchaseRequest(2,5)
                )
        );

        CustomerResponse customer = new CustomerResponse(
                        "customer-1",
                        "Priyanshu",
                        "Kushwaha",
                        "abc@gmail.com",
                        mock(Address.class)
                );

        List<PurchaseResponse> products = List.of(
                        new PurchaseResponse(
                                1,
                                "Iqoo 12",
                                "Phone",
                                BigDecimal.valueOf(50000),
                                2
                        ),
                        new PurchaseResponse(
                                2,
                                "S24",
                                "Samsung",
                                BigDecimal.valueOf(60000),
                                5
                        )
                );

        Orders order = Orders.builder()
                        .id(100)
                        .reference("ORD-001")
                        .customerID("customer-1")
                        .paymentMethod(PaymentMethod.PAYPAL)
                        .totalAmount(BigDecimal.valueOf(50000))
                        .build();

        OrderResponse response = new OrderResponse(
                        100,
                        "ORD-001",
                        BigDecimal.valueOf(50000),
                        PaymentMethod.PAYPAL,
                        "customer-1"
                );

        when(customerClient.findCustomerByID("customer-1")).thenReturn(Optional.of(customer));
        when(productClient.purchaseResponses(request.products())).thenReturn(products);
        when(mapper.toOrder(request)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(mapper.toOrderResponse(request)).thenReturn(response);

        OrderResponse result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals(response, result);

        verify(customerClient).findCustomerByID("customer-1");
        verify(productClient).purchaseResponses(request.products());
        verify(orderRepository).save(order);
        verify(orderLineService, times(2)).saveOrderLine(any());
        verify(paymentClient).requestOrderPayment(paymentRequestCaptor.capture());
        verify(orderProducer).sendOrderConformation(orderConfirmationCaptor.capture());

        OrderConfirmation confirmation = orderConfirmationCaptor.getValue();

        assertEquals(request.reference(), confirmation.orderReference());
        assertEquals(request.amount(), confirmation.totalAmount());
        assertEquals(request.paymentMethod(), confirmation.paymentMethod());
        assertEquals(customer, confirmation.customer());
        assertEquals(products, confirmation.products());

        PaymentRequest payment = paymentRequestCaptor.getValue();

        assertEquals(request.amount(), payment.amount());
        assertEquals(request.reference(), payment.orderReference());
        assertEquals(order.getId(), payment.orderId());
        assertEquals(customer, payment.customer());
    }

    @Test
    void shouldThrowBusinessExceptionWhenCustomerDoesNotExist() throws JsonProcessingException {

        OrderRequest request = new OrderRequest(
                null,
                "ORD-001",
                BigDecimal.valueOf(50000),
                PaymentMethod.PAYPAL,
                "customer-1",
                List.of()
        );

        when(customerClient.findCustomerByID("customer-1"))
                .thenReturn(Optional.empty());

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> orderService.createOrder(request)
        );

        Assertions.assertEquals(
                "Cannot Create Customer:: No Customer Exist With IDcustomer-1",
                exception.getMessage()
        );

        verify(orderRepository, never()).save(any());
        verify(orderProducer, never()).sendOrderConformation(any());
        verify(paymentClient, never()).requestOrderPayment(any());
    }

    @Test
    void shouldFindAllOrders() {

        Orders order1 = Orders.builder()
                .id(1)
                .reference("ORD-001")
                .customerID("C1")
                .paymentMethod(PaymentMethod.PAYPAL)
                .totalAmount(BigDecimal.valueOf(100))
                .build();

        Orders order2 = Orders.builder()
                .id(2)
                .reference("ORD-002")
                .customerID("C2")
                .paymentMethod(PaymentMethod.PAYPAL)
                .totalAmount(BigDecimal.valueOf(200))
                .build();

        OrderResponse response1 =
                new OrderResponse(
                        1,
                        "ORD-001",
                        BigDecimal.valueOf(100),
                        PaymentMethod.PAYPAL,
                        "C1"
                );

        OrderResponse response2 =
                new OrderResponse(
                        2,
                        "ORD-002",
                        BigDecimal.valueOf(200),
                        PaymentMethod.PAYPAL,
                        "C2"
                );

        when(orderRepository.findAll())
                .thenReturn(List.of(order1, order2));

        when(mapper.fromOrder(order1))
                .thenReturn(response1);

        when(mapper.fromOrder(order2))
                .thenReturn(response2);

        List<OrderResponse> result = orderService.findAll();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(response1, result.get(0));
        Assertions.assertEquals(response2, result.get(1));

        verify(orderRepository).findAll();
    }

    @Test
    void shouldFindOrderById() {

        Orders order = Orders.builder()
                .id(1)
                .reference("ORD-001")
                .customerID("customer")
                .paymentMethod(PaymentMethod.PAYPAL)
                .totalAmount(BigDecimal.valueOf(100))
                .build();

        OrderResponse response =
                new OrderResponse(
                        1,
                        "ORD-001",
                        BigDecimal.valueOf(100),
                        PaymentMethod.PAYPAL,
                        "customer"
                );

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));

        when(mapper.fromOrder(order))
                .thenReturn(response);

        OrderResponse result = orderService.findById(1);

        Assertions.assertEquals(response, result);

        verify(orderRepository).findById(1);
    }

    @Test
    void shouldThrowWhenOrderDoesNotExist() {

        when(orderRepository.findById(1))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception =
                Assertions.assertThrows(
                        EntityNotFoundException.class,
                        () -> orderService.findById(1)
                );

        Assertions.assertEquals(
                "No Order Found with Provided Id 1",
                exception.getMessage()
        );

        verify(orderRepository).findById(1);
    }
}
