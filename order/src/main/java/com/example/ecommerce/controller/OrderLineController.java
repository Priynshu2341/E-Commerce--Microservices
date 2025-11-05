package com.example.ecommerce.controller;

import com.example.ecommerce.dtos.responsedtos.OrderLineResponse;
import com.example.ecommerce.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-lines")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService orderLineService;

    @GetMapping("/order/{order-id}")
    public ResponseEntity<List<OrderLineResponse>> findByOrderID(@PathVariable("/order-id") Integer id){
        return ResponseEntity.ok(orderLineService.findByOrderId(id));
    }
}
