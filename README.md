# 🛒 E-Commerce Microservices Project

> ⚡ **Note:**  
> This is the full and clean working project of the E-Commerce Microservices application.  
> This repository may not contain many commits because it was built cleanly from scratch.  
> The original development version can be found here → [Old Repository](https://github.com/Priynshu2341/e-commerce-Microservices/tree/microservices)

---

A backend **E-Commerce application** built using **Spring Boot** and **Spring Cloud Microservices**.  
It handles the complete workflow — from placing an order to processing payments and sending email notifications — using **Kafka**, **PostgreSQL**, **MongoDB**, and **Docker**.

> 🧱 Built with **Gradle**, but can easily be switched to **Maven** if preferred.

---

## 🚀 What This Project Does

This project simulates a small online store where:

1. A **customer** places an order.
2. The **Stocks Service** checks product availability.
3. The **Payment Service** processes the payment.
4. The **Order Service** confirms and saves the order.
5. The **Notification Service** sends an email confirmation.
6. All services communicate asynchronously using **Kafka** and are registered via **Eureka Discovery**.

---

## ⚙️ Main Technologies

- **Java 17** & **Spring Boot 3**
- **Spring Cloud** (Eureka, Config Server, Gateway)
- **Apache Kafka** (event-driven communication)
- **PostgreSQL** & **MongoDB**
- **Docker & Docker Compose**
- **Thymeleaf** (for email templates)
- **Lombok**, **Spring Validation**, **JPA / Hibernate**

---

## 🧩 Microservices Included

| Service | Description |
|----------|--------------|
| **Eureka Server** | Service discovery for all microservices |
| **Config Server** | Centralized configuration management |
| **API Gateway** | Routes all API requests |
| **Customer Service** | Manages customer data |
| **Order Service** | Handles order creation and management |
| **Payment Service** | Processes customer payments |
| **Stock Service** | Manages product inventory and stock levels |
| **Notification Service** | Sends order and payment confirmation emails |

---

## 🗄️ Databases Used

| Service | Database |
|----------|-----------|
| Customer, Order, Payment, Stock | PostgreSQL |
| Notification | MongoDB |

---

## 🧰 How to Run

### 1️⃣ Start Infrastructure with Docker

Run the command below to start all supporting services(PostgreSQL, MongoDB, Kafka, Zookeeper, Zipkin, and MailDev):

```bash
docker-compose up -d
