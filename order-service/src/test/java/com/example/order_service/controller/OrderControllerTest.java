package com.example.order_service.controller;

import com.example.common.order.OrderRequest;
import com.example.common.order.OrderResponse;
import com.example.common.payment.PaymentMethod;
import com.example.order_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOrder() throws Exception {

        OrderRequest request = new OrderRequest(
                null,
                "ORD-001",
                BigDecimal.valueOf(50000),
                PaymentMethod.PAYPAL,
                "customer-1",
                List.of()
        );

        OrderResponse response = new OrderResponse(
                1,
                "ORD-001",
                BigDecimal.valueOf(50000),
                PaymentMethod.PAYPAL,
                "customer-1"
        );

        when(service.createOrder(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/orders/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Order Placed Successfully"))
                .andExpect(jsonPath("$.data.id")
                        .value(1))
                .andExpect(jsonPath("$.data.reference")
                        .value("ORD-001"))
                .andExpect(jsonPath("$.data.customerID")
                        .value("customer-1"));
    }

    @Test
    void shouldFindAllOrders() throws Exception {

        List<OrderResponse> responses = List.of(
                new OrderResponse(
                        1,
                        "ORD-001",
                        BigDecimal.valueOf(100),
                        PaymentMethod.PAYPAL,
                        "customer-1"
                ),
                new OrderResponse(
                        2,
                        "ORD-002",
                        BigDecimal.valueOf(200),
                        PaymentMethod.PAYPAL,
                        "customer-2"
                )
        );

        when(service.findAll()).thenReturn(responses);

        mockMvc.perform(get("/api/v1/orders/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].reference").value("ORD-001"))
                .andExpect(jsonPath("$[1].reference").value("ORD-002"));
    }

    @Test
    void shouldFindOrderById() throws Exception {

        OrderResponse response = new OrderResponse(
                1,
                "ORD-001",
                BigDecimal.valueOf(50000),
                PaymentMethod.PAYPAL,
                "customer-1"
        );

        when(service.findById(1)).thenReturn(response);

        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reference").value("ORD-001"))
                .andExpect(jsonPath("$.customerID").value("customer-1"));
    }
}