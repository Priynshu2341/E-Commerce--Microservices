package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
