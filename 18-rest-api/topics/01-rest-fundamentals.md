# REST Fundamentals

## Overview

REST (Representational State Transfer) is an architectural style for designing networked applications. It provides a standardized approach to building web services that are scalable, maintainable, and easy to understand.

---

## Introduction to REST

### What is REST?

REST is an architectural style introduced by Roy Fielding in his 2000 doctoral dissertation. It defines a set of constraints and principles for creating web services that communicate over HTTP.

**Key Characteristics:**

- **Stateless**: Each request from client to server must contain all information needed to understand and process the request
- **Client-Server Architecture**: Separation of concerns between user interface and data storage
- **Cacheable**: Responses must define themselves as cacheable or non-cacheable
- **Uniform Interface**: Standardized way of communicating between client and server
- **Layered System**: Client cannot tell if it's connected directly to the end server or an intermediary

### RESTful Web Services

A RESTful web service is a web service implemented using HTTP and the principles of REST. It:

- Exposes resources through URIs (Uniform Resource Identifiers)
- Uses HTTP methods to perform operations on resources
- Returns data in standard formats (JSON, XML)
- Is stateless and scalable

**Example RESTful Resource:**
```
GET /api/users/123
```

This URI represents a specific user resource with ID 123.

---

## REST Principles

### 1. Client-Server Separation

The client and server are independent components that communicate over a network. This separation allows:

- **Independent Evolution**: Client and server can be developed and scaled independently
- **Improved Portability**: Client can work across different platforms
- **Scalability**: Servers can be replicated and load-balanced

### 2. Statelessness

The server does not store any client context between requests. Each request is self-contained.

**Benefits:**
- **Scalability**: No session state on the server means easier horizontal scaling
- **Reliability**: No risk of session data loss
- **Simplicity**: Each request can be handled independently

**Example:**
```http
# Bad - Stateful (relies on server remembering previous request)
GET /api/products/next

# Good - Stateless (all information in request)
GET /api/products?page=2&size=20
```

### 3. Cacheability

Responses must explicitly indicate whether they can be cached to improve performance.

**HTTP Cache Headers:**
```http
Cache-Control: max-age=3600
ETag: "33a64df551425fcc55e4d42a148795d9f25f89d4"
Last-Modified: Wed, 21 Oct 2023 07:28:00 GMT
```

### 4. Uniform Interface

REST uses a standardized interface based on HTTP methods and resource URIs.

**Core Constraints:**
- **Resource Identification**: Resources are identified by URIs
- **Resource Manipulation through Representations**: Clients manipulate resources through representations (JSON, XML)
- **Self-Descriptive Messages**: Each message includes enough information to describe how to process it
- **HATEOAS**: Hypermedia as the Engine of Application State (clients navigate through hyperlinks)

### 5. Layered System

The architecture can be composed of hierarchical layers, with each layer providing services to the layer above it.

**Benefits:**
- Load balancers can be added
- Caching layers can improve performance
- Security layers can be inserted

### 6. Code on Demand (Optional)

Servers can extend client functionality by transferring executable code (e.g., JavaScript).

---

## HTTP Methods

HTTP methods define the action to be performed on a resource. RESTful services use these methods to implement CRUD (Create, Read, Update, Delete) operations.

### GET - Retrieve Resources

Retrieves a representation of a resource without modifying it.

**Characteristics:**
- **Safe**: Does not modify the resource
- **Idempotent**: Multiple identical requests have the same effect as a single request
- **Cacheable**: Responses can be cached

**Examples:**
```http
# Get all users
GET /api/users

# Get a specific user
GET /api/users/123

# Get users with filtering
GET /api/users?role=admin&status=active

# Get nested resources
GET /api/users/123/orders
```

**Response Example:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 123,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "admin"
}
```

### POST - Create Resources

Creates a new resource on the server.

**Characteristics:**
- **Not Safe**: Creates new resources
- **Not Idempotent**: Multiple identical requests create multiple resources
- **Not Cacheable**: Typically not cached

**Example:**
```http
POST /api/users
Content-Type: application/json

{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "role": "user"
}
```

**Response:**
```http
HTTP/1.1 201 Created
Location: /api/users/124
Content-Type: application/json

{
  "id": 124,
  "name": "Jane Smith",
  "email": "jane@example.com",
  "role": "user",
  "createdAt": "2023-12-12T10:30:00Z"
}
```

### PUT - Update/Replace Resources

Replaces an existing resource entirely with the provided representation.

**Characteristics:**
- **Not Safe**: Modifies resources
- **Idempotent**: Multiple identical requests have the same effect
- **Not Cacheable**: Typically not cached

**Example:**
```http
PUT /api/users/123
Content-Type: application/json

{
  "id": 123,
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "role": "admin"
}
```

**Response:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 123,
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "role": "admin",
  "updatedAt": "2023-12-12T10:35:00Z"
}
```

### PATCH - Partial Update

Updates only specific fields of a resource without replacing the entire resource.

**Characteristics:**
- **Not Safe**: Modifies resources
- **Not Necessarily Idempotent**: Depends on implementation
- **Not Cacheable**: Typically not cached

**Example:**
```http
PATCH /api/users/123
Content-Type: application/json

{
  "email": "john.newemail@example.com"
}
```

**Response:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 123,
  "name": "John Doe",
  "email": "john.newemail@example.com",
  "role": "admin"
}
```

### DELETE - Remove Resources

Removes a resource from the server.

**Characteristics:**
- **Not Safe**: Removes resources
- **Idempotent**: Deleting multiple times has the same effect
- **Not Cacheable**: Typically not cached

**Example:**
```http
DELETE /api/users/123
```

**Response:**
```http
HTTP/1.1 204 No Content
```

Or with a response body:
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "message": "User deleted successfully",
  "id": 123
}
```

### Method Comparison Table

| Method | CRUD | Safe | Idempotent | Request Body | Response Body |
|--------|------|------|------------|--------------|---------------|
| GET | Read | Yes | Yes | No | Yes |
| POST | Create | No | No | Yes | Yes |
| PUT | Update/Replace | No | Yes | Yes | Yes |
| PATCH | Partial Update | No | No* | Yes | Yes |
| DELETE | Delete | No | Yes | No | Optional |

*PATCH can be designed to be idempotent, but it's not guaranteed

---

## HTTP Status Codes

Status codes indicate the result of an HTTP request. They are grouped into five categories.

### 1xx: Informational

Rarely used in REST APIs. Indicates the request was received and processing continues.

| Code | Meaning | Usage |
|------|---------|-------|
| 100 | Continue | Client should continue with request |
| 101 | Switching Protocols | Server switching to different protocol |

### 2xx: Success

The request was successfully received, understood, and accepted.

| Code | Meaning | Usage |
|------|---------|-------|
| **200** | OK | Standard successful response (GET, PUT, PATCH) |
| **201** | Created | Resource successfully created (POST) |
| **202** | Accepted | Request accepted for processing (async operations) |
| **204** | No Content | Success but no response body (DELETE) |

**Examples:**

```http
# 200 OK - Successful GET
GET /api/users/123
HTTP/1.1 200 OK
Content-Type: application/json
{ "id": 123, "name": "John" }

# 201 Created - Successful POST
POST /api/users
HTTP/1.1 201 Created
Location: /api/users/124
{ "id": 124, "name": "Jane" }

# 204 No Content - Successful DELETE
DELETE /api/users/123
HTTP/1.1 204 No Content
```

### 3xx: Redirection

Further action needs to be taken to complete the request.

| Code | Meaning | Usage |
|------|---------|-------|
| 301 | Moved Permanently | Resource permanently moved to new URI |
| 302 | Found | Temporary redirect |
| 304 | Not Modified | Resource hasn't changed (caching) |

**Example:**
```http
GET /api/users/123
If-None-Match: "33a64df551425fcc55e4d42a148795d9f25f89d4"

HTTP/1.1 304 Not Modified
```

### 4xx: Client Error

The request contains bad syntax or cannot be fulfilled.

| Code | Meaning | Usage |
|------|---------|-------|
| **400** | Bad Request | Invalid request syntax or parameters |
| **401** | Unauthorized | Authentication required or failed |
| **403** | Forbidden | Authenticated but not authorized |
| **404** | Not Found | Resource doesn't exist |
| **405** | Method Not Allowed | HTTP method not supported for resource |
| **409** | Conflict | Request conflicts with current state (duplicate resource) |
| **422** | Unprocessable Entity | Validation failed |
| **429** | Too Many Requests | Rate limit exceeded |

**Examples:**

```http
# 400 Bad Request
POST /api/users
{ "email": "invalid-email" }

HTTP/1.1 400 Bad Request
Content-Type: application/json
{
  "error": "Bad Request",
  "message": "Invalid email format",
  "field": "email"
}

# 401 Unauthorized
GET /api/users/123

HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer
{
  "error": "Unauthorized",
  "message": "Missing or invalid authentication token"
}

# 403 Forbidden
DELETE /api/users/123

HTTP/1.1 403 Forbidden
{
  "error": "Forbidden",
  "message": "You don't have permission to delete users"
}

# 404 Not Found
GET /api/users/999

HTTP/1.1 404 Not Found
{
  "error": "Not Found",
  "message": "User with id 999 not found"
}

# 409 Conflict
POST /api/users
{ "email": "existing@example.com" }

HTTP/1.1 409 Conflict
{
  "error": "Conflict",
  "message": "User with this email already exists"
}

# 422 Unprocessable Entity
POST /api/users
{ "name": "", "email": "valid@example.com" }

HTTP/1.1 422 Unprocessable Entity
{
  "error": "Validation Failed",
  "errors": [
    {
      "field": "name",
      "message": "Name cannot be empty"
    }
  ]
}
```

### 5xx: Server Error

The server failed to fulfill a valid request.

| Code | Meaning | Usage |
|------|---------|-------|
| **500** | Internal Server Error | Generic server error |
| **501** | Not Implemented | Server doesn't support the functionality |
| **502** | Bad Gateway | Invalid response from upstream server |
| **503** | Service Unavailable | Server temporarily unavailable |
| **504** | Gateway Timeout | Upstream server timeout |

**Examples:**

```http
# 500 Internal Server Error
GET /api/users/123

HTTP/1.1 500 Internal Server Error
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}

# 503 Service Unavailable
GET /api/users

HTTP/1.1 503 Service Unavailable
Retry-After: 3600
{
  "error": "Service Unavailable",
  "message": "Service is temporarily down for maintenance"
}
```

### Status Code Selection Guide

**For Successful Operations:**
- Use `200 OK` for successful GET, PUT, or PATCH
- Use `201 Created` for successful POST with resource creation
- Use `204 No Content` for successful DELETE or operations with no response body

**For Client Errors:**
- Use `400 Bad Request` for invalid request format
- Use `401 Unauthorized` for missing/invalid authentication
- Use `403 Forbidden` for insufficient permissions
- Use `404 Not Found` for missing resources
- Use `409 Conflict` for duplicate or conflicting resources
- Use `422 Unprocessable Entity` for validation errors

**For Server Errors:**
- Use `500 Internal Server Error` for unexpected server failures
- Use `503 Service Unavailable` for temporary unavailability

---

## RESTful URI Design Best Practices

### 1. Use Nouns, Not Verbs

URIs should represent resources (nouns), not actions (verbs). The HTTP method indicates the action.

**Good:**
```
GET /api/users
POST /api/users
GET /api/users/123
PUT /api/users/123
DELETE /api/users/123
```

**Bad:**
```
GET /api/getAllUsers
POST /api/createUser
GET /api/getUserById/123
PUT /api/updateUser/123
DELETE /api/deleteUser/123
```

### 2. Use Plural Nouns for Collections

```
GET /api/users          # Collection
GET /api/users/123      # Specific resource
GET /api/products       # Collection
GET /api/products/456   # Specific resource
```

### 3. Use Hierarchical Relationships

Represent relationships through URI hierarchy.

```
GET /api/users/123/orders           # All orders for user 123
GET /api/users/123/orders/789       # Specific order for user 123
GET /api/categories/5/products      # All products in category 5
```

### 4. Use Query Parameters for Filtering, Sorting, and Pagination

```
# Filtering
GET /api/users?role=admin
GET /api/products?category=electronics&inStock=true

# Sorting
GET /api/users?sort=name
GET /api/products?sort=-price  # Descending order

# Pagination
GET /api/users?page=2&size=20
GET /api/products?offset=40&limit=20

# Searching
GET /api/users?search=john
GET /api/products?q=laptop
```

### 5. Version Your API

```
# URI Versioning
GET /api/v1/users
GET /api/v2/users

# Header Versioning
GET /api/users
Accept: application/vnd.myapi.v1+json
```

### 6. Use Kebab-Case or Snake_Case Consistently

Choose one convention and stick with it.

```
# Kebab-case (recommended for URIs)
GET /api/user-profiles
GET /api/order-history

# Snake_case (common in JSON)
{
  "user_id": 123,
  "first_name": "John"
}
```

### 7. Keep URIs Lowercase

```
# Good
GET /api/users/123

# Bad
GET /api/Users/123
GET /api/USERS/123
```

---

## Content Negotiation

Content negotiation allows clients and servers to agree on the format of data exchanged.

### How It Works

The client specifies preferred formats using HTTP headers, and the server responds in the best matching format.

**Key Headers:**

| Header | Purpose | Example |
|--------|---------|---------|
| `Accept` | Client's preferred response format | `application/json` |
| `Content-Type` | Format of request body | `application/json` |

### Basic Example

```http
# Client requests JSON
GET /api/users/123
Accept: application/json

# Server responds with JSON
HTTP/1.1 200 OK
Content-Type: application/json
{"id": 123, "name": "John"}
```

### Spring Boot Implementation

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Default: JSON (most common)
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Explicit format specification
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserJson(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Accept specific content type
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }
}
```

**Note:** JSON is the standard for REST APIs. XML support is rarely needed in modern applications.

---

## HATEOAS (Brief Overview)

HATEOAS (Hypermedia as the Engine of Application State) is a REST constraint where responses include links to related resources and available actions.

### Why HATEOAS?

- Clients discover available actions dynamically
- API becomes self-documenting
- Reduces coupling between client and server

### Example Response

```json
{
  "id": 123,
  "name": "John Doe",
  "email": "john@example.com",
  "_links": {
    "self": { "href": "/api/users/123" },
    "orders": { "href": "/api/users/123/orders" },
    "delete": { "href": "/api/users/123", "method": "DELETE" }
  }
}
```

### Spring HATEOAS (Basic)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

```java
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@GetMapping("/{id}")
public EntityModel<User> getUser(@PathVariable Long id) {
    User user = userService.findById(id);

    return EntityModel.of(user,
        linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
        linkTo(methodOn(UserController.class).getAllUsers()).withRel("users")
    );
}
```

**Note:** HATEOAS adds complexity. Use it when clients need dynamic API discovery. For most REST APIs, good documentation (Swagger/OpenAPI) is sufficient.

---

## Summary

REST is a powerful architectural style that provides:

- **Standardization**: Consistent use of HTTP methods and status codes
- **Scalability**: Stateless design enables easy horizontal scaling
- **Simplicity**: Intuitive resource-based URIs
- **Flexibility**: Platform-independent communication
- **Cacheability**: Built-in support for caching

**Key Takeaways:**

1. REST uses HTTP methods to perform operations on resources
2. Resources are identified by URIs
3. Status codes communicate the result of operations
4. Stateless design improves scalability
5. Proper URI design makes APIs intuitive and maintainable

**Next Steps:**

Understanding REST fundamentals is essential for building modern web services. In the next topic, we'll explore how to implement RESTful APIs using Spring Boot 3.
