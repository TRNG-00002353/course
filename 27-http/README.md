# HTTP (Hypertext Transfer Protocol)

## Overview
Understand the foundation of web communication - HTTP protocol, its methods, status codes, headers, cookies, and the complete request-response lifecycle.

## Learning Objectives
By the end of this module, you will understand and be able to apply the key concepts and practices of HTTP protocol in web development.

## Topics Covered

### 1. [Introduction to HTTP](./topics/01-introduction-to-http.md)
- What is HTTP?
- History and Evolution (HTTP/1.0, HTTP/1.1, HTTP/2, HTTP/3)
- HTTP vs HTTPS
- Client-Server Architecture
- Request-Response Model
- URLs and URIs

### 2. [HTTP Methods](./topics/02-http-methods.md)
- GET - Retrieve resources
- POST - Create resources
- PUT - Update/Replace resources
- PATCH - Partial updates
- DELETE - Remove resources
- HEAD - Get headers only
- OPTIONS - Get allowed methods
- Safe and Idempotent Methods

### 3. [HTTP Lifecycle](./topics/03-http-lifecycle.md)
- DNS Resolution
- TCP Connection (Three-way Handshake)
- TLS Handshake (for HTTPS)
- HTTP Request
- Server Processing
- HTTP Response
- Connection Close/Keep-Alive

### 4. [HTTP Status Codes](./topics/04-http-status-codes.md)
- 1xx - Informational Responses
- 2xx - Success (200, 201, 204)
- 3xx - Redirection (301, 302, 304)
- 4xx - Client Errors (400, 401, 403, 404)
- 5xx - Server Errors (500, 502, 503)

### 5. [HTTP Headers](./topics/05-http-headers.md)
- Request Headers (Accept, Authorization, Content-Type, User-Agent)
- Response Headers (Content-Type, Cache-Control, Set-Cookie)
- Custom Headers
- CORS Headers
- Security Headers

### 6. [HTTP Cookies](./topics/06-http-cookies.md)
- What are Cookies?
- Cookie Attributes (Domain, Path, Expires, Max-Age)
- Secure and HttpOnly Flags
- SameSite Attribute
- Session vs Persistent Cookies
- Cookie Security Best Practices

## Key Concepts
For detailed explanations, code examples, and best practices, refer to the individual topic files in the [topics](./topics/) directory.

## Exercises
See the [exercises](./exercises/) directory for hands-on practice problems including a Node.js server implementation.

## Code Examples
Check the module materials and exercises for practical code examples demonstrating HTTP concepts.

## Additional Resources
- MDN Web Docs - HTTP
- RFC 7230-7235 (HTTP/1.1 Specifications)
- RFC 7540 (HTTP/2)
- OWASP Security Guidelines

## Assessment
Make sure you are comfortable with all topics listed above before proceeding to the next module.

## Next Steps
Continue to the next module in the curriculum sequence.

---
**Time Estimate:** 2 days | **Difficulty:** Beginner-Intermediate | **Prerequisites:** Basic networking concepts
