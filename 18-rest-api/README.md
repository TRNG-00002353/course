# REST APIs with Spring Boot

## Overview
Build RESTful web services using Spring Boot with proper HTTP methods, status codes, and documentation.

## Learning Objectives
By the end of this module, you will understand and be able to apply the key concepts and practices of REST APIs with Spring Boot.

## Topics Covered

### 1. [REST Fundamentals](./topics/01-rest-fundamentals.md)
- Introduction to REST
- REST Principles (Statelessness, Client-Server, Cacheability, Uniform Interface)
- HTTP Methods (GET, POST, PUT, PATCH, DELETE)
- HTTP Status Codes (2xx, 3xx, 4xx, 5xx)
- RESTful URI Design Best Practices

### 2. [Spring Boot REST](./topics/02-spring-boot-rest.md)
- Spring Boot 3 Features for REST APIs
- Creating REST Controllers (@RestController, @RequestMapping)
- Request Handling (@PathVariable, @RequestParam, @RequestBody)
- ResponseEntity and Response Handling
- RESTful Resource Representation with JSON
- Jackson Serialization and Annotations
- Exception Handling (@ExceptionHandler, @ControllerAdvice)
- CORS Configuration

### 3. [DTOs and Mapping](./topics/03-dtos-and-mapping.md)
- Data Transfer Objects (DTOs)
- Why Use DTOs (Decoupling, Security, Customization)
- DTO Pattern (Request DTOs, Response DTOs, Nested DTOs)
- Java Records for DTOs
- Manual Mapping vs Automated Mapping
- ModelMapper Library
- MapStruct (Compile-time Code Generation)
- Mapping Best Practices

### 4. [CRUD Operations](./topics/04-crud-operations.md)
- Complete CRUD Implementation
- Create Resources (POST)
- Read Resources (GET)
- Update Resources (PUT/PATCH)
- Delete Resources (DELETE)
- Pagination and Sorting
- Filtering and Searching
- Input Validation
- Error Handling

### 5. [Testing REST APIs](./topics/05-testing-rest-apis.md)
- Testing Strategy and Testing Pyramid
- Spring Boot Test Dependencies
- MockMvc for Controller Testing
- @WebMvcTest for Unit Tests
- @SpringBootTest for Integration Tests
- Testing CRUD Operations
- Testing with Real Database (H2, TestContainers)
- Advanced Testing Techniques
- Testing Best Practices

### 6. [API Documentation](./topics/06-api-documentation.md)
- Importance of API Documentation
- OpenAPI Specification
- SpringDoc OpenAPI Integration
- Documenting with Annotations (@Operation, @ApiResponse, @Schema)
- Controller and Schema Documentation
- Security Documentation
- Swagger UI Customization
- Generating Client Code
- Documentation Best Practices

### 7. [API Security](./topics/07-api-security.md)
- Spring Security Fundamentals
- Authentication vs Authorization
- JWT (JSON Web Token) Authentication
- JWT Structure and Flow
- JwtService Implementation
- JWT Authentication Filter
- Security Configuration
- Method-Level Security (@PreAuthorize, @Secured)
- OAuth2 Integration
- OAuth2 Resource Server
- OAuth2 Client (Login with Google/GitHub)
- Password Security (BCrypt)
- Security Best Practices

## Key Concepts
For detailed explanations, code examples, and best practices, refer to the individual topic files in the [topics](./topics/) directory.

## Exercises
See the [exercises](./exercises/) directory for hands-on practice problems and solutions.

## Code Examples
Check the module materials and exercises for practical code examples.

## Additional Resources
- Official documentation
- Online tutorials and courses
- Community forums and discussions

## Assessment
Make sure you are comfortable with all topics listed above before proceeding to the next module.

## Next Steps
Continue to the next module in the curriculum sequence.

## Demo Projects

- [Spring Security JWT Demo](../resources/demo/spring/spring-security-jwt-demo/) - Complete JWT authentication implementation

---
**Time Estimate:** 5 days | **Difficulty:** Intermediate | **Prerequisites:** Previous modules
