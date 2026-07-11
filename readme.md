# 🛒 E-Commerce Microservices Platform

A production-style backend e-commerce platform built using **Spring Boot**, **Spring Cloud**, and **event-driven microservices architecture**. The system demonstrates scalable distributed system design with asynchronous communication, centralized configuration, service discovery, API Gateway routing, distributed tracing, and containerized deployment.

---

# 🚀 Features

### Core Business Features
- Customer management
- Product & stock management
- Category management
- Order creation and processing
- Payment processing
- Email notification service
- Product purchase workflow
- Stock validation before purchase
- Automatic stock deduction after successful orders

---

# 🏗️ Microservices

- Customer Service
- Stock Service
- Order Service
- Payment Service
- Notification Service
- API Gateway
- Eureka Discovery Server
- Spring Cloud Config Server

---

# ⚙️ Architecture

- Microservices Architecture
- Event-Driven Communication
- REST APIs
- Asynchronous Processing
- Centralized Configuration
- Service Discovery
- API Gateway Routing

---

# 🔄 Order Processing Flow

```
Client
   │
   ▼
API Gateway
   │
   ▼
Order Service
   │
   ▼
Stock Service
   │
   ▼
Payment Service
   │
   ▼
Kafka Event
   │
   ▼
Notification Service
   │
   ▼
Email Confirmation
```

---

# ✨ Technical Features

- Spring Cloud Gateway
- Eureka Service Discovery
- Spring Cloud Config Server
- Apache Kafka Messaging
- Distributed Tracing (Micrometer + Zipkin)
- Dockerized Microservices
- Health Monitoring using Spring Boot Actuator
- Bean Validation
- Global Exception Handling
- DTO Mapping
- Layered Architecture
- Repository Pattern

---

# 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot 4
- Spring Cloud

### Databases
- PostgreSQL
- MongoDB

### Messaging
- Apache Kafka

### Service Discovery
- Netflix Eureka

### API Gateway
- Spring Cloud Gateway

### Configuration
- Spring Cloud Config Server


### Monitoring
- Spring Boot Actuator
- Micrometer
- Zipkin

### Containerization
- Docker
- Docker Compose

### Testing
- JUnit 5
- Mockito
- MockMvc
- Testcontainers
- Repository Tests
- Integration Tests
- Controller Tests
- Service Tests

---

# 🗄️ Database Design

### PostgreSQL
- Orders
- Payments
- Stock
- Categories

### MongoDB
- Customers
- Notifications

---

# 📦 Infrastructure

The project includes Docker containers for:

- PostgreSQL
- MongoDB
- pgAdmin
- Mongo Express
- Apache Kafka
- ZooKeeper
- Zipkin
- MailDev
- Keycloak

---

# 📋 REST APIs

### Customer
- Create Customer
- Get Customer

### Stock
- Create Product
- Purchase Products
- Find Product
- Category Management

### Order
- Create Order

### Payment
- Process Payment

### Notification
- Consume Kafka Events
- Send Email Notifications

---

# 🧪 Testing

Implemented testing using:

- Repository Tests
- Controller Tests
- Integration Tests
- MockMvc
- Testcontainers
- Mockito

---

# 🚀 Running the Project

Clone the repository

```bash
git clone <repo-url>
```

Build the project

```bash
./mvnw clean package -DskipTests
```

Start the infrastructure

```bash
docker compose up -d
```

Access:

| Service | URL |
|----------|-----|
| Config Server | http://localhost:8888 |
| Eureka | http://localhost:8761 |
| Gateway | http://localhost:8222 |
| Zipkin | http://localhost:9411 |
| MailDev | http://localhost:1080 |
| pgAdmin | http://localhost:5050 |
| Mongo Express | http://localhost:8081 |
| Keycloak | http://localhost:8089 |

---

# 📂 Project Structure

```
config-server
eureka-discovery
gateway-service
customer-service
stock-service
order-service
payment-service
notification-service
docker-compose.yml
```

---

# 📌 Future Improvements

- JWT Authentication
- Role Based Authorization
- Redis Caching
- Circuit Breaker (Resilience4j)
- Distributed Transactions (Saga Pattern)
- Kubernetes Deployment
- CI/CD Pipeline (GitHub Actions)
- Prometheus & Grafana Monitoring