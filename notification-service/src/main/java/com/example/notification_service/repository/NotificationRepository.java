package com.example.notification_service.repository;


import com.example.notification_service.entity.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notifications,String> {
}
