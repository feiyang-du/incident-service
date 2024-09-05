# Incident Management Service

Incident Management Service is a Spring Boot application designed to manage incidents with a RESTful API. It supports features such as CRUD operations, in-memory caching, pagination, and integrates OpenAPI for API documentation.

## Features

- Create, read, update, and delete incidents.
- In-memory caching with Caffeine for efficient data access.
- Pagination support for incident listings.
- Exception handling with a custom error response structure.
- OpenAPI/Swagger integration for API documentation.
- Docker support for containerized deployment.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- OpenAPI (Swagger)
- Caffeine Cache
- H2 In-memory Database
- Docker

## Prerequisites

- JDK 17 or higher
- Maven 3.x
- Docker (optional, for containerized deployment)

## Getting Started

### Clone the repository

```bash
git clone https://github.com/yourusername/incident-service.git
cd incident-service
```
