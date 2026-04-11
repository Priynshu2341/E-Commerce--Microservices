# E-Commerce Microservices System

A scalable backend system built using Spring Boot and Spring Cloud microservices architecture.  
The system handles the complete order lifecycle using asynchronous communication and distributed services.

## 🚀 Key Features
- Order processing workflow (order → stock → payment → notification)
- Event-driven architecture using Kafka
- Service discovery with Eureka
- API Gateway for centralized routing
- Centralized configuration using Config Server
- Email notifications for order confirmation

## ⚙️ Tech Stack
- Java 17, Spring Boot 3
- Spring Cloud (Eureka, Gateway, Config Server)
- Apache Kafka (event-driven communication)
- PostgreSQL & MongoDB
- Docker & Docker Compose

## 🧩 Microservices
- Customer Service
- Order Service
- Payment Service
- Stock Service
- Notification Service

## 🗄️ Databases
- PostgreSQL → Orders, Payments, Stock
- MongoDB → Customers, Notifications

## 🧪 How to Run
```bash
docker-compose up -d
