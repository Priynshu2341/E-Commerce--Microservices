package com.example.order_service.integration;

import com.example.common.payment.PaymentMethod;
import com.example.order_service.entity.Orders;
import com.example.order_service.rep.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17.6");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void clearDatabase() {
        orderRepository.deleteAll();
    }

    @Test
    void shouldFindAllOrders() throws Exception {

        orderRepository.save(
                Orders.builder()
                        .reference("ORD-001")
                        .customerID("customer-1")
                        .paymentMethod(PaymentMethod.PAYPAL)
                        .totalAmount(BigDecimal.valueOf(100))
                        .build()
        );

        orderRepository.save(
                Orders.builder()
                        .reference("ORD-002")
                        .customerID("customer-2")
                        .paymentMethod(PaymentMethod.VISA)
                        .totalAmount(BigDecimal.valueOf(200))
                        .build()
        );

        mockMvc.perform(get("/api/v1/orders/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].reference").value("ORD-001"))
                .andExpect(jsonPath("$[1].reference").value("ORD-002"));
    }

    @Test
    void shouldFindOrderById() throws Exception {

        Orders saved = orderRepository.save(
                Orders.builder()
                        .reference("ORD-001")
                        .customerID("customer-1")
                        .paymentMethod(PaymentMethod.PAYPAL)
                        .totalAmount(BigDecimal.valueOf(100))
                        .build()
        );

        mockMvc.perform(get("/api/v1/orders/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.reference").value("ORD-001"))
                .andExpect(jsonPath("$.customerID").value("customer-1"))
                .andExpect(jsonPath("$.paymentMethod").value("PAYPAL"));
    }

    @Test
    void shouldReturnBadRequestWhenOrderDoesNotExist() throws Exception {

        mockMvc.perform(get("/api/v1/orders/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No Order Found with Provided Id 999"));
    }
}