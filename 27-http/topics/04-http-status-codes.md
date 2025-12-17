# HTTP Status Codes

## Overview

HTTP status codes are three-digit numbers returned by servers to indicate the result of a client's request. Understanding these codes is essential for proper error handling and API design.

---

## Status Code Categories

| Range | Category | Description |
|-------|----------|-------------|
| **1xx** | Informational | Request received, continuing process |
| **2xx** | Success | Request was successfully received and processed |
| **3xx** | Redirection | Further action needed to complete request |
| **4xx** | Client Error | Request contains bad syntax or cannot be fulfilled |
| **5xx** | Server Error | Server failed to fulfill a valid request |

```
Status Code Flow:
┌─────────────────────────────────────────────────────────┐
│                    Request Received                      │
│                          │                               │
│            ┌─────────────┼─────────────┐                │
│            ▼             ▼             ▼                │
│       ┌────────┐   ┌────────┐   ┌────────┐            │
│       │ Valid? │   │ Auth?  │   │ Found? │            │
│       └───┬────┘   └───┬────┘   └───┬────┘            │
│           │            │            │                   │
│      No   │       No   │       No   │                   │
│      ▼    │       ▼    │       ▼    │                   │
│    4xx    │     4xx    │     4xx    │                   │
│           │            │            │                   │
│      Yes  │       Yes  │       Yes  │                   │
│      ▼    ▼       ▼    ▼       ▼    ▼                   │
│       ┌──────────────────────────────┐                  │
│       │      Process Request         │                  │
│       └──────────────┬───────────────┘                  │
│                      │                                   │
│            ┌─────────┼─────────┐                        │
│            ▼         ▼         ▼                        │
│        Success    Redirect   Error                      │
│          2xx        3xx       5xx                       │
└─────────────────────────────────────────────────────────┘
```

---

## 1xx Informational Responses

Informational responses indicate that the request was received and is being processed.

| Code | Name | Description |
|------|------|-------------|
| 100 | Continue | Initial part of request received, continue sending |
| 101 | Switching Protocols | Server is switching protocols as requested |
| 102 | Processing | Server is processing (WebDAV) |
| 103 | Early Hints | Preload resources before final response |

### Common Usage

```http
# Client sends large request with Expect header
POST /upload HTTP/1.1
Host: example.com
Content-Length: 1048576
Expect: 100-continue

# Server responds to continue
HTTP/1.1 100 Continue

# Client then sends body
[Large request body...]

# Server sends final response
HTTP/1.1 200 OK
```

```http
# Upgrade to WebSocket
GET /chat HTTP/1.1
Host: example.com
Upgrade: websocket
Connection: Upgrade

HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
```

---

## 2xx Success Responses

Success responses indicate that the request was successfully received, understood, and processed.

| Code | Name | Description | Common Use |
|------|------|-------------|------------|
| **200** | OK | Standard success | GET, PUT, PATCH |
| **201** | Created | Resource created | POST |
| **202** | Accepted | Request accepted for processing | Async operations |
| **204** | No Content | Success with no body | DELETE |
| **206** | Partial Content | Partial resource returned | Range requests |

### 200 OK

```http
# GET request success
GET /api/users/123 HTTP/1.1
Host: api.example.com

HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 123,
    "name": "John Doe",
    "email": "john@example.com"
}
```

### 201 Created

```http
# POST creates new resource
POST /api/users HTTP/1.1
Host: api.example.com
Content-Type: application/json

{"name": "Jane Doe", "email": "jane@example.com"}

HTTP/1.1 201 Created
Location: /api/users/124
Content-Type: application/json

{
    "id": 124,
    "name": "Jane Doe",
    "email": "jane@example.com",
    "createdAt": "2024-10-21T10:30:00Z"
}
```

### 202 Accepted

```http
# Async operation accepted
POST /api/reports/generate HTTP/1.1
Host: api.example.com

HTTP/1.1 202 Accepted
Content-Type: application/json

{
    "message": "Report generation started",
    "jobId": "job-12345",
    "statusUrl": "/api/jobs/job-12345"
}
```

### 204 No Content

```http
# DELETE success (no body returned)
DELETE /api/users/123 HTTP/1.1
Host: api.example.com

HTTP/1.1 204 No Content
```

### 206 Partial Content

```http
# Range request for large file
GET /downloads/video.mp4 HTTP/1.1
Host: example.com
Range: bytes=0-1023

HTTP/1.1 206 Partial Content
Content-Range: bytes 0-1023/1048576
Content-Length: 1024
Content-Type: video/mp4

[First 1024 bytes of video...]
```

---

## 3xx Redirection Responses

Redirection responses indicate that further action needs to be taken to complete the request.

| Code | Name | Description | Method Changes? |
|------|------|-------------|-----------------|
| **301** | Moved Permanently | URL changed forever | May change to GET |
| **302** | Found | Temporary redirect | May change to GET |
| **303** | See Other | Redirect to GET | Always GET |
| **304** | Not Modified | Use cached version | N/A |
| **307** | Temporary Redirect | Temporary, keep method | No |
| **308** | Permanent Redirect | Permanent, keep method | No |

### 301 Moved Permanently

```http
# Old URL redirects to new URL (cached by browsers)
GET /old-page HTTP/1.1
Host: example.com

HTTP/1.1 301 Moved Permanently
Location: https://example.com/new-page

# SEO: Search engines update their index
```

### 302 Found (Temporary Redirect)

```http
# Temporary redirect (don't cache)
GET /promo HTTP/1.1
Host: example.com

HTTP/1.1 302 Found
Location: https://example.com/current-promo
```

### 304 Not Modified

```http
# Conditional request - resource not changed
GET /api/users/123 HTTP/1.1
Host: api.example.com
If-None-Match: "abc123"

HTTP/1.1 304 Not Modified
ETag: "abc123"

# Client uses cached version
```

### 307 vs 302

```http
# 302 may change POST to GET (historical behavior)
POST /form HTTP/1.1
→ 302 Found
→ Browser might GET the redirect URL

# 307 preserves the HTTP method
POST /form HTTP/1.1
→ 307 Temporary Redirect
→ Browser will POST to the redirect URL
```

---

## 4xx Client Error Responses

Client error responses indicate that the client's request contains bad syntax or cannot be fulfilled.

### Most Common 4xx Codes

| Code | Name | When to Use |
|------|------|-------------|
| **400** | Bad Request | Malformed request syntax, invalid data |
| **401** | Unauthorized | Authentication required or failed |
| **403** | Forbidden | Authenticated but not authorized |
| **404** | Not Found | Resource doesn't exist |
| **405** | Method Not Allowed | HTTP method not supported |
| **409** | Conflict | Resource state conflict |
| **422** | Unprocessable Entity | Validation failed |
| **429** | Too Many Requests | Rate limit exceeded |

### 400 Bad Request

```http
# Invalid JSON
POST /api/users HTTP/1.1
Content-Type: application/json

{invalid json}

HTTP/1.1 400 Bad Request
Content-Type: application/json

{
    "error": "Bad Request",
    "message": "Invalid JSON syntax"
}
```

### 401 Unauthorized

```http
# No authentication provided
GET /api/protected HTTP/1.1
Host: api.example.com

HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer realm="api"

{
    "error": "Unauthorized",
    "message": "Authentication required"
}
```

### 403 Forbidden

```http
# Authenticated but not allowed
GET /api/admin/users HTTP/1.1
Authorization: Bearer user-token

HTTP/1.1 403 Forbidden

{
    "error": "Forbidden",
    "message": "Admin access required"
}
```

### 401 vs 403

```
401 Unauthorized:
├── "Who are you?"
├── Not authenticated
├── Can retry with credentials
└── Include WWW-Authenticate header

403 Forbidden:
├── "I know who you are, but NO"
├── Authenticated but not authorized
├── Don't retry (won't help)
└── Access denied permanently
```

### 404 Not Found

```http
GET /api/users/999 HTTP/1.1
Host: api.example.com

HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "error": "Not Found",
    "message": "User with ID 999 not found"
}
```

### 405 Method Not Allowed

```http
DELETE /api/users HTTP/1.1  # Collection doesn't support DELETE
Host: api.example.com

HTTP/1.1 405 Method Not Allowed
Allow: GET, POST

{
    "error": "Method Not Allowed",
    "message": "DELETE not supported on this resource",
    "allowedMethods": ["GET", "POST"]
}
```

### 409 Conflict

```http
# Creating user with existing email
POST /api/users HTTP/1.1
Content-Type: application/json

{"email": "existing@example.com"}

HTTP/1.1 409 Conflict

{
    "error": "Conflict",
    "message": "Email already registered"
}
```

### 422 Unprocessable Entity

```http
# Validation failed
POST /api/users HTTP/1.1
Content-Type: application/json

{"name": "", "email": "invalid-email", "age": -5}

HTTP/1.1 422 Unprocessable Entity

{
    "error": "Validation Error",
    "errors": [
        {"field": "name", "message": "Name is required"},
        {"field": "email", "message": "Invalid email format"},
        {"field": "age", "message": "Age must be positive"}
    ]
}
```

### 429 Too Many Requests

```http
GET /api/users HTTP/1.1
Host: api.example.com

HTTP/1.1 429 Too Many Requests
Retry-After: 60
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 0
X-RateLimit-Reset: 1634812860

{
    "error": "Too Many Requests",
    "message": "Rate limit exceeded. Try again in 60 seconds"
}
```

### Complete 4xx Reference

| Code | Name | Description |
|------|------|-------------|
| 400 | Bad Request | Malformed request |
| 401 | Unauthorized | Authentication required |
| 402 | Payment Required | Reserved for future use |
| 403 | Forbidden | Access denied |
| 404 | Not Found | Resource not found |
| 405 | Method Not Allowed | HTTP method not supported |
| 406 | Not Acceptable | Cannot generate acceptable response |
| 407 | Proxy Authentication Required | Proxy auth needed |
| 408 | Request Timeout | Server timed out waiting |
| 409 | Conflict | Resource conflict |
| 410 | Gone | Resource permanently deleted |
| 411 | Length Required | Content-Length required |
| 412 | Precondition Failed | Conditional request failed |
| 413 | Payload Too Large | Request body too large |
| 414 | URI Too Long | URL too long |
| 415 | Unsupported Media Type | Content-Type not supported |
| 416 | Range Not Satisfiable | Invalid range request |
| 417 | Expectation Failed | Expect header not met |
| 418 | I'm a Teapot | April Fools' joke (RFC 2324) |
| 422 | Unprocessable Entity | Validation error |
| 423 | Locked | Resource is locked |
| 424 | Failed Dependency | Dependency failed |
| 426 | Upgrade Required | Protocol upgrade needed |
| 428 | Precondition Required | Conditional request required |
| 429 | Too Many Requests | Rate limited |
| 431 | Request Header Fields Too Large | Headers too large |
| 451 | Unavailable For Legal Reasons | Censored/blocked |

---

## 5xx Server Error Responses

Server error responses indicate that the server failed to fulfill a valid request.

| Code | Name | When to Use |
|------|------|-------------|
| **500** | Internal Server Error | Generic server error |
| **501** | Not Implemented | Feature not supported |
| **502** | Bad Gateway | Invalid upstream response |
| **503** | Service Unavailable | Server overloaded/maintenance |
| **504** | Gateway Timeout | Upstream timeout |

### 500 Internal Server Error

```http
GET /api/users HTTP/1.1
Host: api.example.com

HTTP/1.1 500 Internal Server Error

{
    "error": "Internal Server Error",
    "message": "An unexpected error occurred",
    "requestId": "req-12345"  // For debugging
}

# Don't expose stack traces to clients!
```

### 502 Bad Gateway

```http
# Reverse proxy received invalid response from upstream
GET /api/users HTTP/1.1
Host: api.example.com

HTTP/1.1 502 Bad Gateway

{
    "error": "Bad Gateway",
    "message": "Unable to reach upstream server"
}
```

### 503 Service Unavailable

```http
# Server is overloaded or under maintenance
GET /api/users HTTP/1.1
Host: api.example.com

HTTP/1.1 503 Service Unavailable
Retry-After: 300

{
    "error": "Service Unavailable",
    "message": "Server is temporarily unavailable. Please try again later."
}
```

### 504 Gateway Timeout

```http
# Upstream server didn't respond in time
GET /api/slow-operation HTTP/1.1
Host: api.example.com

HTTP/1.1 504 Gateway Timeout

{
    "error": "Gateway Timeout",
    "message": "Upstream server timed out"
}
```

---

## Best Practices for API Design

### Error Response Format

```json
{
    "error": {
        "code": "VALIDATION_ERROR",
        "status": 422,
        "message": "Validation failed",
        "details": [
            {
                "field": "email",
                "message": "Invalid email format"
            }
        ],
        "timestamp": "2024-10-21T10:30:00Z",
        "requestId": "req-abc123"
    }
}
```

### Status Code Selection Guide

```
Creating a resource?
├── Success → 201 Created
└── Async → 202 Accepted

Updating a resource?
├── Success with data → 200 OK
└── Success without data → 204 No Content

Deleting a resource?
├── Success → 204 No Content
└── Already deleted → 404 or 204 (depends on preference)

Client made a mistake?
├── Bad syntax → 400 Bad Request
├── Not logged in → 401 Unauthorized
├── No permission → 403 Forbidden
├── Wrong URL → 404 Not Found
├── Wrong method → 405 Method Not Allowed
├── Data conflict → 409 Conflict
└── Invalid data → 422 Unprocessable Entity

Server made a mistake?
├── Unknown error → 500 Internal Server Error
├── Not supported → 501 Not Implemented
├── Upstream error → 502 Bad Gateway
├── Overloaded → 503 Service Unavailable
└── Upstream timeout → 504 Gateway Timeout
```

---

## Quick Reference Chart

```
┌─────┬────────────────────────┬─────────────────────────────────┐
│Code │ Name                   │ Quick Description               │
├─────┼────────────────────────┼─────────────────────────────────┤
│ 200 │ OK                     │ Success                         │
│ 201 │ Created                │ Resource created                │
│ 204 │ No Content             │ Success, no body                │
│ 301 │ Moved Permanently      │ URL changed forever             │
│ 302 │ Found                  │ Temporary redirect              │
│ 304 │ Not Modified           │ Use cache                       │
│ 400 │ Bad Request            │ Invalid syntax                  │
│ 401 │ Unauthorized           │ Login required                  │
│ 403 │ Forbidden              │ No permission                   │
│ 404 │ Not Found              │ Doesn't exist                   │
│ 405 │ Method Not Allowed     │ Wrong HTTP method               │
│ 409 │ Conflict               │ State conflict                  │
│ 422 │ Unprocessable Entity   │ Validation failed               │
│ 429 │ Too Many Requests      │ Rate limited                    │
│ 500 │ Internal Server Error  │ Server broke                    │
│ 502 │ Bad Gateway            │ Upstream error                  │
│ 503 │ Service Unavailable    │ Server busy                     │
│ 504 │ Gateway Timeout        │ Upstream timeout                │
└─────┴────────────────────────┴─────────────────────────────────┘
```

---

## Key Takeaways

1. **2xx = Success** - Request worked as expected
2. **3xx = Redirect** - Go somewhere else
3. **4xx = Client Error** - You (client) made a mistake
4. **5xx = Server Error** - We (server) made a mistake
5. **Be specific** - Use appropriate codes, not just 200/400/500
6. **Include details** - Helpful error messages aid debugging
7. **401 vs 403** - Unauthenticated vs unauthorized

---

## Next Topic

Continue to [HTTP Headers](./05-http-headers.md) to learn about request and response metadata.
