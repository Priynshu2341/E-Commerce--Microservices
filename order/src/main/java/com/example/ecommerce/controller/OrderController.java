package com.example.ecommerce.controller;

import com.example.ecommerce.dtos.requestdtos.OrderRequest;
import com.example.ecommerce.dtos.responsedtos.OrderCreationResponse;
import com.example.ecommerce.dtos.responsedtos.OrderResponse;
import com.example.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest request) {
        OrderResponse data = service.createOrder(request);
        var response = new OrderCreationResponse<OrderResponse>("Order Placed Successfully",data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("order-id") Integer id){
      return ResponseEntity.ok(service.findById(id));
    }

}
