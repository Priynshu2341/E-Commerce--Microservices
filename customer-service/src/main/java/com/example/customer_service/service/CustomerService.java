package com.example.customer_service.service;


import com.example.common.customer.CustomerRequest;
import com.example.common.customer.CustomerResponse;
import com.example.customer_service.exception.CustomerNotFoundException;
import com.example.customer_service.model.Customer;
import com.example.customer_service.repository.CustomerRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;


    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }


    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Cannot Update customer :: No Customer FOund with ID :: %s", request.id())));
        mergeCustomer(customer, request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if (StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }


    public List<CustomerResponse> findAllCustomer() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .toList();
    }

    public Boolean existByID(String id) {
        return repository.findById(id).isPresent();
    }

    public CustomerResponse findById(String id) {
        return repository.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("Invalid Customer id" + id));

    }

    public void deleteCustomer(String id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Invalid Customer id" + id));
        repository.deleteById(id);

    }
}
