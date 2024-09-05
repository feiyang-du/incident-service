# Incident Service

Incident Service is a Spring Boot application designed to manage incidents with a RESTful API. It supports features such as CRUD operations, in-memory caching, pagination, and integrates OpenAPI for API documentation.

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

git clone https://github.com/yourusername/incident-service.git
cd incident-service

### Build the project

Use Maven to build the project:

mvn clean install

### Running the application

You can run the application using Maven:

mvn spring-boot:run

Alternatively, you can run the JAR file directly:

java -jar target/incident-service.jar

### Docker Deployment

1. Build the Docker image:

docker build -t incident-service .

2. Run the Docker container:

docker run -p 8080:8080 incident-service

### Accessing the API

Once the application is running, you can access the API documentation via Swagger UI:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### API Endpoints

| Method | Endpoint               | Description                    |
|--------|------------------------|--------------------------------|
| GET    | /api/incidents/{id}     | Retrieve an incident by ID     |
| POST   | /api/incidents          | Create a new incident          |
| PUT    | /api/incidents/{id}     | Update an existing incident    |
| DELETE | /api/incidents/{id}     | Delete an incident             |
| GET    | /api/incidents          | List incidents with pagination |

## Project Structure


## Development

### Running Tests

You can run all unit tests with the following Maven command:

mvn test

### Key Maven Dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```
Provides the core features of Spring Boot, including auto-configuration and common Spring modules such as dependency injection, resource management, and event handling.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
Enables the creation of RESTful APIs using Spring MVC, handling HTTP requests, routing, and JSON serialization/deserialization.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
Provides integration with Java Persistence API (JPA) for interacting with relational databases. It simplifies database operations such as CRUD operations through Spring Data repositories.

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```
Provides an in-memory database for development and testing. H2 is lightweight, requires no external configuration, and is ideal for quick testing during development.

```xml
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
</dependency>
```
Implements an in-memory cache to improve performance by reducing database lookups. Caffeine allows for customizable caching strategies to optimize data access.

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
```
Automatically generates OpenAPI 3.0-compliant documentation for your API and integrates with Swagger UI, enabling users to explore and test your API endpoints.

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```
Reduces boilerplate code by generating common methods like getters, setters, toString, equals, and hashCode automatically through annotations. This makes the codebase more concise and readable.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```
Provides testing support with JUnit 5 and Mockito, enabling both unit and integration testing. It includes a wide array of testing tools to mock dependencies and verify business logic.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
Enables Aspect-Oriented Programming (AOP) support in Spring Boot, useful for logging, transaction management, and other cross-cutting concerns.



