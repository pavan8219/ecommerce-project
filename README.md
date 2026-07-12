# 🛒 E-Commerce Microservices Platform

A **production-ready E-Commerce backend** built using **Spring Boot Microservices**, following scalable and event-driven architecture principles. The application demonstrates secure authentication, asynchronous communication, payment gateway integration, inventory management, and distributed system design using modern backend technologies.

---

## 🚀 Features

- 🔐 JWT Authentication & Authorization
- 🌐 API Gateway with Centralized Security
- 🔍 Eureka Service Discovery
- 👤 User Management
- 📦 Product & Category Management
- 🎯 Product Variant Management with SKU Generation
- 📊 Inventory Management
- 🛍️ Order Management
- 💳 Razorpay Payment Gateway Integration
- 🔒 Secure Webhook Signature Verification
- 📩 Event-Driven Communication using Apache Kafka
- 🔄 Payment Retry Support
- 🖼️ Product Image Upload
- 🔗 OpenFeign based Inter-Service Communication
- 🐳 Dockerized Infrastructure
- 📄 RESTful APIs with Validation & Global Exception Handling

---

# 🏗️ Architecture

```text
                           +------------------+
                           |      Client      |
                           +--------+---------+
                                    |
                                    |
                           +--------v---------+
                           |    API Gateway   |
                           +--------+---------+
                                    |
       ---------------------------------------------------------------
      |            |             |             |                      |
      |            |             |             |                      |
+-----v-----+ +----v-----+ +-----v-----+ +-----v------+ +-------------v---------+
| User      | | Product  | | Order     | | Payment    | | Inventory             |
| Service   | | Service  | | Service   | | Service    | | Service               |
+-----------+ +----------+ +-----------+ +------------+ +-----------------------+
      \______________________________________________________________/
                              |
                       Apache Kafka
                              |
                   Event-Driven Communication
```

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Microservices
- Spring Cloud Gateway
- Netflix Eureka
- OpenFeign
- Apache Kafka

### Database
- MySQL

### Payment
- Razorpay Payment Gateway
- Razorpay Webhooks

### DevOps
- Docker
- Docker Compose

### Other
- JWT Authentication
- Swagger / OpenAPI
- Maven

---

# 📦 Microservices

| Service | Responsibility |
|----------|----------------|
| User Service | User Registration, Login & JWT Authentication |
| Product Service | Products, Categories & Product Variants |
| Inventory Service | Inventory Tracking & Stock Management |
| Order Service | Order Placement & Order Status Management |
| Payment Service | Razorpay Integration, Payment Processing & Webhooks |
| API Gateway | Routing, Authentication & User Context Propagation |
| Eureka Server | Service Discovery |

---

# 🔄 Order Processing Flow

```text
Customer
    │
    ▼
Order Service
    │
Create Order
(Status = PENDING_PAYMENT)
    │
    ▼
Payment Service
    │
Create Razorpay Order
    │
    ▼
Customer Completes Payment
    │
    ▼
Razorpay Webhook
    │
Verify Signature
    │
Update Payment Status
    │
Publish PaymentCompletedEvent
    │
    ├─────────────────────────────┐
    ▼                             ▼
Inventory Service           Order Service
Reduce Stock                Update Order Status
                             → CONFIRMED
```

---

# 🔐 Authentication Flow

```text
Client
   │
Login
   │
JWT Token
   │
API Gateway
   │
Validate JWT
   │
Add Headers
 ├── X-User-Id
 └── X-User-Role
   │
Forward Request
   │
Microservices
```

---

# 💳 Payment Workflow

1. Customer places an order.
2. Order is created with **PENDING_PAYMENT** status.
3. Payment Service creates a Razorpay Order.
4. Razorpay Checkout is opened.
5. Customer completes the payment.
6. Razorpay sends a secure webhook.
7. Payment Service verifies the webhook signature.
8. Payment status is updated.
9. `PaymentCompletedEvent` is published to Kafka.
10. Inventory Service reduces stock.
11. Order Service updates the order status to **CONFIRMED**.

---

# 📡 Event-Driven Architecture

### Producer

- Payment Service

### Consumers

- Order Service
- Inventory Service

### Kafka Topic

```
payment-events
```

### Published Event

```
PaymentCompletedEvent
```

This asynchronous architecture ensures loose coupling, scalability, and resilience across microservices.

---

# 🔒 Security

- JWT Authentication
- API Gateway Authorization
- User Context Propagation
- Razorpay Webhook Signature Verification
- Role-Based Access Support

---

# 📂 Project Structure

```text
ecommerce-project
│
├── api-gateway
├── eureka-server
├── user-service
├── product-service
├── inventory-service
├── order-service
├── payment-service
├── docker-compose.yml
└── README.md
```

---

# ⚙️ Getting Started

## Clone Repository

```bash
git clone https://github.com/pavan8219/ecommerce-project.git
cd ecommerce-project
```

## Start Infrastructure

```bash
docker compose up -d
```

## Start Services

Run the services in the following order:

1. Eureka Server
2. API Gateway
3. User Service
4. Product Service
5. Inventory Service
6. Order Service
7. Payment Service

---

# 📖 API Documentation

Each microservice exposes Swagger UI.

Example:

```
http://localhost:8081/swagger-ui/index.html
```

Update the port based on the respective service.

---

# ✨ Highlights

- Production-Ready Microservices Architecture
- Event-Driven Order Processing using Kafka
- Real Razorpay Payment Gateway Integration
- Secure Webhook Verification
- JWT Authentication & API Gateway Security
- Service Discovery using Eureka
- OpenFeign for Inter-Service Communication
- Dockerized Deployment
- Scalable REST APIs
- Payment Retry Mechanism
- SKU-Based Inventory Management

---

# 🚀 Future Enhancements

- Notification Service (Email/SMS)
- Redis Caching
- Inventory Reservation
- Transactional Outbox Pattern
- Distributed Tracing (Zipkin)
- Elasticsearch for Product Search
- Prometheus & Grafana Monitoring
- CI/CD using GitHub Actions
- Kubernetes Deployment

---

## 👨‍💻 Author

**Pavan Porika**

- Java Backend Developer
- Spring Boot • Microservices • Kafka • System Design • Angular

---

## ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub!
