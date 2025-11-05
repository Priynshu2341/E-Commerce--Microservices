package com.example.ecommerce.feignclient;

import com.example.ecommerce.dtos.requestdtos.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service",url = "${application.config.payment-url}")
public interface PaymentClient {

    @PostMapping("/create")
    Integer requestOrderPayment(@RequestBody PaymentRequest request);
}
