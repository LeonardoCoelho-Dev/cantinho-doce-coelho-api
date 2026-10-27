# Cantinho Doce Coelho API

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-migrations-CC0200?logo=flyway&logoColor=white)

REST API for **Cantinho Doce Coelho**, a homemade bakery. It manages the cake catalog (flavors, prices and availability) and protects its routes with JWT authentication.

The project follows a layered architecture inspired by **Domain-Driven Design**, with the business domain kept separate from the web and infrastructure layers.

## Features

- Cake catalog CRUD: create, list (paginated), detail, update and delete
- Soft delete: removed cakes are deactivated, not erased, keeping history intact
- Prices stored as `BigDecimal` / `decimal(10,2)`, with cents and no rounding errors
- Request validation with Bean Validation (required fields, positive prices)
- Global error handling with consistent JSON error responses
- Stateless authentication with **JWT** (HMAC256, 2-hour expiration)
- Passwords stored as **BCrypt** hashes
- Versioned database schema with **Flyway** migrations

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4.1 (Web MVC, Data JPA, Validation) |
| Security | Spring Security 7, Auth0 java-jwt |
| Database | MySQL 8 |
| Migrations | Flyway |
| Build | Maven (wrapper included) |
| Utilities | Lombok, Spring Boot DevTools |

## Project structure

```
src/main/java/com/CantinhoDoceCoelho/Project
├── controller/          # REST endpoints (CakeController, AuthenticationController)
├── domain/
│   ├── cake/            # Cake aggregate: entity, Flavor enum, repository, DTOs
│   ├── user/            # User aggregate: entity, repository
│   └── shared/          # Shared kernel (Price value object)
└── infra/
    ├── exception/       # Global error handler
    └── security/        # Spring Security config, JWT token service and filter
```

## Getting started

### Prerequisites

- Java 25
- MySQL 8 running locally

### 1. Create the database

```sql
create database cantinho_doce_coelho_api;
```

Tables are created automatically by Flyway when the application starts.

### 2. Configure local credentials

Credentials and secrets are **not** committed. Create the file `src/main/resources/application-local.properties` (it is already listed in `.gitignore`):

```properties
spring.datasource.username=your-db-user
spring.datasource.password=your-db-password

JWT_SECRET=a-long-random-secret
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd spring-boot:run`. The API starts at `http://localhost:8080`.

### 4. Create a user

There is no sign-up endpoint yet. Insert a user directly in the database, with the password as a **BCrypt hash**:

```sql
insert into users (login, password)
values ('admin@cantinhodoce.com', '<bcrypt-hash-of-your-password>');
```

## Authentication

1. Send your credentials to `POST /login`.
2. Copy the `token` from the response.
3. Send it in the `Authorization` header of every other request:

```
Authorization: Bearer <token>
```

Tokens expire after **2 hours**. Every route except `POST /login` requires a valid token.

## API reference

| Method | Endpoint | Description | Success |
|---|---|---|---|
| `POST` | `/login` | Authenticate and receive a JWT | `200` |
| `POST` | `/cakes` | Create a cake | `201` |
| `GET` | `/cakes` | List active cakes (paginated) | `200` |
| `GET` | `/cakes/{id}` | Get cake details | `200` |
| `PUT` | `/cakes` | Update flavor and/or price | `200` |
| `DELETE` | `/cakes/{id}` | Deactivate a cake (soft delete) | `204` |

### Examples

**Login**

```http
POST /login
Content-Type: application/json

{
  "login": "admin@cantinhodoce.com",
  "password": "your-password"
}
```

```json
{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }
```

**Create a cake**

```http
POST /cakes
Authorization: Bearer <token>
Content-Type: application/json

{
  "flavor": "CHOCOLATE",
  "price": { "price": 39.90 }
}
```

```json
{ "id": 1, "flavor": "CHOCOLATE", "price": { "price": 39.90 } }
```

Available flavors: `CHOCOLATE`, `CARROT`, `POWDERED_MILK`, `VANILLA`.

**List cakes**

`GET /cakes` returns 10 cakes per page, sorted by flavor. Pagination and sorting can be changed with query parameters:

```
GET /cakes?page=0&size=5&sort=price,desc
```

**Update a cake**

Only the fields sent are changed.

```http
PUT /cakes
Authorization: Bearer <token>
Content-Type: application/json

{
  "id": 1,
  "price": { "price": 42.50 }
}
```

### Error responses

| Status | When |
|---|---|
| `400` | Invalid body, failed validation or malformed JSON |
| `403` | Missing or invalid token, or wrong credentials |
| `404` | Cake not found |

Validation errors list each invalid field:

```json
[
  { "field": "flavor", "message": "must not be null" },
  { "field": "price.price", "message": "must be greater than 0" }
]
```

## Roadmap

- [ ] Return `401` for wrong credentials and invalid tokens
- [ ] Validate login request fields
- [ ] Unique constraint on user login
- [ ] Sign-up endpoint for new users
- [ ] Support for other products besides cakes
- [ ] Automated tests for endpoints and security

## Author

**Leonardo Coelho** — [GitHub](https://github.com/LeonardoCoelho-Dev)
