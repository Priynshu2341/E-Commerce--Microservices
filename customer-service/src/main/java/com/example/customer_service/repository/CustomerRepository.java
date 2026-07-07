package com.example.customer_service.repository;

import com.example.customer_service.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String> {

    Optional<Customer> findById(String id);
}
