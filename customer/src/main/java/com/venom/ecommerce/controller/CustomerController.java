package com.venom.ecommerce.controller;

import com.venom.ecommerce.dto.CustomerRequest;
import com.venom.ecommerce.dto.CustomerResponse;
import com.venom.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService service;

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequest request) {
        return ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request) {
        service.updateCustomer(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCustomer());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existById(@PathVariable("customer-id") String id) {
        return ResponseEntity.ok(service.existByID(id));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id") String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/delete/{customer-id}")
    public ResponseEntity<Void> deleteById(@PathVariable ("customer-id") String id){
        service.deleteCustomer(id);
        return ResponseEntity.accepted().build();
    }

}
