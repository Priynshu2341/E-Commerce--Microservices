package com.example.ecommerce.controller;

import com.example.ecommerce.dtos.PaymentRequest;
import com.example.ecommerce.dtos.PaymentResponse;
import com.example.ecommerce.service.PaymentService;
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
    public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentRequest request){
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<PaymentResponse>> findAll(){
        return ResponseEntity.ok(paymentService.findAll());
    }
}
