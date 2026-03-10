# Marketplace API

REST API developed with **Spring Boot** that simulates the backend of a marketplace platform.  
The system allows user management, product catalog management, and product categorization with secure authentication and role-based authorization.

This project was built focusing on **clean architecture, backend best practices, and real-world API design**.

---

# Technologies Used

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- Flyway (database migration)
- Maven
- Lombok
- Bean Validation (Jakarta Validation)

---

# Architecture

The project follows a layered architecture commonly used in enterprise backend systems.

Controller → Service → Repository → Database

Each layer has a clear responsibility.

**Controller**
Handles HTTP requests and responses.

**Service**
Contains business rules and application logic.

**Repository**
Handles database communication using Spring Data JPA.

**DTOs**
Used to transfer data between layers and avoid exposing entities.

**Mapper**
Converts Entities ↔ DTOs.

---

# Security

Authentication and authorization are implemented using **Spring Security + JWT**.

Features implemented:

- Stateless authentication
- JWT token generation
- Password encryption with BCrypt
- Role-based authorization
- Protected endpoints

Roles implemented:

- USER
- ADMIN

Example of protected routes:

ADMIN:
POST /api/products  
PATCH /api/products/{id}  
DELETE /api/products/{id}

USER / ADMIN:
GET /api/products  
GET /api/categories

---

# Authentication Flow

1. User logs in using:

POST /auth/login

2. API validates credentials.

3. A JWT token is generated.

4. The client must send the token in future requests using:

Authorization: Bearer <token>

---

# Modules

## User Module

Manages system users.

Features:

- Create user
- Update user
- Get user by ID
- List active users
- Soft delete users

Passwords are stored encrypted using **BCrypt**.

---

## Product Module

Manages marketplace products.

Features:

- Create product
- Update product
- Get product by ID
- List products
- Soft delete products

Each product contains:

- Name
- Description
- Price
- Stock
- Category
- Created date
- Updated date

---

## Category Module

Categories allow grouping products.

Features:

- Create category
- Update category
- List categories
- Get category by ID
- Soft delete categories
- Reactivate categories

Category names are **unique (case insensitive)**.

---

# Database

Database used: **PostgreSQL**

Tables:

- users
- products
- categories

Database versioning is handled with **Flyway migrations**.

Example migrations:

V1__create_users_table.sql  
V2__create_categories_table.sql  
V3__create_products_table.sql

---

# Soft Delete Strategy

Instead of removing records from the database, the system uses a boolean flag:

active = true / false

This allows:

- safer data management
- recovery of previously deleted records
- audit-friendly systems

---

# Exception Handling

A **GlobalExceptionHandler** standardizes API error responses.

Handled exceptions include:

- ResourceNotFoundException → 404
- BusinessException → 400
- ValidationException → 400
- Authentication errors → 401
- Authorization errors → 403

Example error response:

{
"timestamp": "2026-03-10T12:00:00",
"status": 404,
"error": "RESOURCE_NOT_FOUND",
"message": "Product with id 10 not found"
}

---

# API Endpoints

## Authentication

POST /auth/login

---

## Users

POST /api/users  
GET /api/users  
GET /api/users/{id}  
PATCH /api/users/{id}  
DELETE /api/users/{id}

---

## Products

POST /api/products  
GET /api/products  
GET /api/products/{id}  
PATCH /api/products/{id}  
DELETE /api/products/{id}

---

## Categories

POST /api/categories  
GET /api/categories  
GET /api/categories/{id}  
PATCH /api/categories/{id}  
DELETE /api/categories/{id}  
PATCH /api/categories/{id}/activate

---

# Running the Project

## Requirements

- Java 21
- PostgreSQL
- Maven

---

## Environment Variables

The application uses environment variables for database configuration:

DB_URL=jdbc:postgresql://localhost:5432/marketplace  
DB_USERNAME=postgres  
DB_PASSWORD=password

---

## Run the Application

mvn spring-boot:run

Server runs on:

http://localhost:8081

---

# Author

Kevin Rychlewski

Backend Developer focused on **Java + Spring Boot**.