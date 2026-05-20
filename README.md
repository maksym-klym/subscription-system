# Subscription Billing System

Backend SaaS billing system built with Spring Boot.

The project simulates a real-world subscription platform with user management, subscription lifecycle handling, invoice generation, payment processing, JWT authentication, and scheduler-based billing automation.

---

# Project Highlights

* JWT-based authentication with Spring Security
* Invoice-driven subscription activation flow
* Subscription lifecycle state management
* Scheduler-based expiration handling
* Global exception handling with unified API responses
* RESTful API design with DTO separation
* Swagger/OpenAPI documentation
* Validation using Bean Validation
* Transactional business operations with Spring Data JPA

---

# Tech Stack

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Spring Security (JWT)
- Swagger / OpenAPI
- Maven
- Lombok

---

# Architecture Overview

The project follows a layered Spring Boot architecture:

* **Controller layer** - REST endpoints and request handling
* **Service layer** - business logic and transactional operations
* **Repository layer** - database access using Spring Data JPA
* **DTO layer** - request/response models
* **Mapper layer** - entity/DTO conversion
* **Exception layer** - centralized exception handling

## Main Modules

* User Management
* Subscription Management
* Invoice/Billing Management
* Payment Processing
* Authentication & Authorization
* Scheduler-based lifecycle automation

---

# Database Schema Overview

## Entities

### User

Represents platform customers who can purchase subscription plans.

### Plan

Represents available subscription plans with pricing and duration information.

### Subscription

Represents user subscriptions and manages lifecycle states.

### Invoice

Represents billing invoices generated for subscriptions.

### Payment

Represents payment transactions associated with invoices.

---

## Relationships

* User -> Subscription (1:N)
* Subscription -> Invoice (1:N)
* Invoice -> Payment (1:1)

---

# Authentication

The application uses JWT-based authentication with Spring Security.

## Login Flow

1. User sends credentials to `/api/auth/login`
2. Server validates credentials
3. JWT token is generated and returned
4. Token must be included in protected requests

Example:

```http
Authorization: Bearer <JWT_TOKEN>
```

Protected endpoints require a valid JWT token in the Authorization header.

---

# API Overview

## Authentication

| Method | Endpoint |
| ------ | -------- |
| POST   | /api/auth/login |

---

## Subscriptions

| Method | Endpoint |
| ------ | -------- |
| POST   | /subscriptions |
| GET    | /subscriptions |
| GET    | /subscriptions/{id} |
| PUT    | /subscriptions/{id}/cancel |
| GET    | /subscriptions/{id}/invoices |

---

## Users

| Method | Endpoint |
| ------ | -------- |
| POST   | /users |
| GET    | /users |
| GET    | /users/{id} |

---

## Plans

| Method | Endpoint |
| ------ | -------- |
| GET    | /plans |
| GET    | /plans/{id} |

---

## Invoices

| Method | Endpoint |
| ------ | -------- |
| POST   | /invoices/{invoiceId}/pay |
| GET    | /invoices |
| GET    | /invoices/{id} |

---

## Payments

| Method | Endpoint |
| ------ | -------- |
| GET    | /payments |
| GET    | /payments/{id} |

---

# Example Subscription Flow

1. Create a user (`POST /users`)
2. Authenticate and receive JWT token (`POST /api/auth/login`)
3. Fetch available subscription plans (`GET /plans`)
4. Create subscription (`POST /subscriptions`)
   - Subscription is created in PENDING_PAYMENT state
   - Invoice is automatically generated
5. Retrieve generated invoice (`GET /subscriptions/{id}/invoices` or `GET /invoices`)
6. Pay invoice (`POST /invoices/{invoiceId}/pay`)
   - Payment is validated (only PENDING invoices allowed)
   - Invoice is marked as PAID
   - Subscription is activated
7. Subscription becomes ACTIVE and lifecycle management is handled by schedulers

---

# API Examples

## Create User

```http
POST /users
Content-Type: application/json
```

```json
{
  "name": "John Doe",
  "email": "john@example.com"
}
```

---

## Login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

---

## Create Subscription

```http
POST /subscriptions
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

```json
{
  "userId": 1,
  "planId": 2
}
```

---

## Pay Invoice

```http
POST /payments/5
Authorization: Bearer <JWT_TOKEN>
```

---

# Business Logic

## Subscription Lifecycle

```text
PENDING_PAYMENT → ACTIVE → EXPIRED / CANCELED
```

### Lifecycle Rules

* subscriptions are initially created in `PENDING_PAYMENT`
* invoice generation happens automatically during subscription creation
* subscription becomes `ACTIVE` only after successful invoice payment
* subscriptions are marked as `EXPIRED` by schedulers after the end date

---

## Invoice Rules

* invoices are generated automatically for new subscriptions
* only `PENDING` invoices can be paid
* overdue invoices become `FAILED`
* invoice payment activates subscription

---

## Payment Processing Rules

* payments are validated transactionally
* duplicate payments are prevented
* payment updates invoice and subscription state atomically

---

## Scheduler Automation

Schedulers automatically:

* mark overdue invoices as `FAILED`
* cancel unpaid subscriptions
* expire subscriptions after `endDate`

---

# Validation & Error Handling

The application uses:

* Bean Validation (`@Valid`)
* centralized exception handling
* consistent API error responses

## Validation Features

* request DTO validation
* field-level validation error mapping
* global validation exception handling

## Error Response Model

Unified `ErrorDto` structure:

```json
{
  "timestamp": "2026-05-20T10:15:30",
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "email": "must be a valid email"
  }
}
```

## Handled Errors

| Status | Description             |
| ------ | ----------------------- |
| 400    | Validation errors       |
| 404    | Resource not found      |
| 409    | Business rule conflicts |

---

# Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

# How to Run

## 1. Create PostgreSQL Database

Example database name:

```text
subscription_system
```

---

## 2. Configure application.properties

Update database configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/subscription_system
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Configure JWT secret:

```properties
jwt.secret=YOUR_SECRET_KEY
```

---

## 3. Run Application

```bash
./mvnw spring-boot:run
```

Application will start on:

```text
http://localhost:8080
```
