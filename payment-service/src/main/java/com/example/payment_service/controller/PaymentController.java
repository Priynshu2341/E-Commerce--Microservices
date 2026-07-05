package com.example.payment_service.controller;


import com.example.payment_service.dtos.PaymentRequest;
import com.example.payment_service.dtos.PaymentResponse;
import com.example.payment_service.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<PaymentResponse>> findAll(){
        return ResponseEntity.ok(paymentService.findAll());
    }
}
