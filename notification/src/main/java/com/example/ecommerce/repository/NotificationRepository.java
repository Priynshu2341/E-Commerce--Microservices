package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notifications,String> {
}
