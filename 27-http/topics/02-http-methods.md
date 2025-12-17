# HTTP Methods

## Overview

HTTP methods (also called HTTP verbs) indicate the desired action to be performed on a resource. Each method has specific semantics that define whether it's safe, idempotent, and cacheable.

---

## HTTP Methods Summary

| Method | Purpose | Request Body | Response Body | Safe | Idempotent | Cacheable |
|--------|---------|--------------|---------------|------|------------|-----------|
| GET | Retrieve resource | No | Yes | Yes | Yes | Yes |
| POST | Create resource | Yes | Yes | No | No | Rarely |
| PUT | Replace resource | Yes | Optional | No | Yes | No |
| PATCH | Update resource | Yes | Yes | No | No | No |
| DELETE | Remove resource | Optional | Optional | No | Yes | No |
| HEAD | Get headers only | No | No | Yes | Yes | Yes |
| OPTIONS | Get capabilities | No | Yes | Yes | Yes | No |
| TRACE | Debug request | No | Yes | Yes | Yes | No |
| CONNECT | Create tunnel | No | Yes | No | No | No |

---

## GET Method

**Purpose:** Retrieve a representation of a resource

### Characteristics
- **Safe**: Does not modify server state
- **Idempotent**: Same result for multiple identical requests
- **Cacheable**: Responses can be cached
- **No Body**: Request should not have a body

### Examples

```http
# Get all users
GET /api/users HTTP/1.1
Host: api.example.com
Accept: application/json

# Response
HTTP/1.1 200 OK
Content-Type: application/json

[
    {"id": 1, "name": "John"},
    {"id": 2, "name": "Jane"}
]
```

```http
# Get specific user
GET /api/users/123 HTTP/1.1
Host: api.example.com

# Response
HTTP/1.1 200 OK
Content-Type: application/json

{"id": 123, "name": "John", "email": "john@example.com"}
```

```http
# Get with query parameters
GET /api/users?page=2&limit=10&sort=name HTTP/1.1
Host: api.example.com
```

### Best Practices

```
DO:
✓ Use for reading/fetching data
✓ Include query parameters for filtering/sorting
✓ Return 200 OK with data, or 404 if not found
✓ Enable caching with appropriate headers

DON'T:
✗ Modify data with GET requests
✗ Include sensitive data in URL (passwords)
✗ Send request body (technically allowed but discouraged)
```

---

## POST Method

**Purpose:** Submit data to create a new resource

### Characteristics
- **Not Safe**: Modifies server state
- **Not Idempotent**: Multiple requests may create multiple resources
- **Not Cacheable** (usually): Responses typically not cached
- **Has Body**: Request body contains data to submit

### Examples

```http
# Create new user
POST /api/users HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "secret123"
}

# Response - 201 Created
HTTP/1.1 201 Created
Location: /api/users/124
Content-Type: application/json

{
    "id": 124,
    "name": "John Doe",
    "email": "john@example.com",
    "createdAt": "2024-10-21T10:30:00Z"
}
```

```http
# Submit form data
POST /api/contact HTTP/1.1
Host: api.example.com
Content-Type: application/x-www-form-urlencoded

name=John&email=john@example.com&message=Hello
```

```http
# Upload file
POST /api/upload HTTP/1.1
Host: api.example.com
Content-Type: multipart/form-data; boundary=----FormBoundary

------FormBoundary
Content-Disposition: form-data; name="file"; filename="photo.jpg"
Content-Type: image/jpeg

[binary data]
------FormBoundary--
```

### Best Practices

```
DO:
✓ Use for creating new resources
✓ Return 201 Created with Location header
✓ Include the created resource in response body
✓ Validate input data

DON'T:
✗ Use for retrieving data
✗ Expect idempotent behavior
✗ Forget to handle duplicate submissions
```

---

## PUT Method

**Purpose:** Replace an existing resource entirely

### Characteristics
- **Not Safe**: Modifies server state
- **Idempotent**: Same result for multiple identical requests
- **Not Cacheable**: Responses not cached
- **Has Body**: Request body contains complete resource representation

### Examples

```http
# Replace entire user resource
PUT /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
    "id": 123,
    "name": "John Updated",
    "email": "john.updated@example.com",
    "phone": "555-1234"
}

# Response
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 123,
    "name": "John Updated",
    "email": "john.updated@example.com",
    "phone": "555-1234"
}
```

```http
# Create or replace (upsert)
PUT /api/config/settings HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
    "theme": "dark",
    "language": "en",
    "notifications": true
}

# Response - 201 if created, 200 if replaced
HTTP/1.1 200 OK
```

### PUT vs POST

```
POST /api/users           # Create new user, server assigns ID
PUT /api/users/123        # Create or replace user with ID 123

POST - "Create something new"
PUT  - "Store this exactly here"
```

### Best Practices

```
DO:
✓ Send complete resource representation
✓ Use when client knows the resource URI
✓ Return 200 OK or 204 No Content
✓ Consider returning 201 Created if resource is new

DON'T:
✗ Use for partial updates (use PATCH instead)
✗ Omit required fields (they may be nullified)
```

---

## PATCH Method

**Purpose:** Apply partial modifications to a resource

### Characteristics
- **Not Safe**: Modifies server state
- **Not Idempotent** (usually): Depends on patch format
- **Not Cacheable**: Responses not cached
- **Has Body**: Contains only the changes

### Examples

```http
# Update only the email
PATCH /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
    "email": "new.email@example.com"
}

# Response
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 123,
    "name": "John",
    "email": "new.email@example.com",
    "phone": "555-1234"
}
```

```http
# JSON Patch format (RFC 6902)
PATCH /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json-patch+json

[
    {"op": "replace", "path": "/email", "value": "new@example.com"},
    {"op": "add", "path": "/nickname", "value": "Johnny"},
    {"op": "remove", "path": "/phone"}
]
```

### PATCH vs PUT

```
Resource: {"id": 1, "name": "John", "email": "john@example.com", "phone": "555-1234"}

# PATCH - Update email only
PATCH /users/1
{"email": "new@example.com"}
Result: {"id": 1, "name": "John", "email": "new@example.com", "phone": "555-1234"}

# PUT - Replace entire resource
PUT /users/1
{"email": "new@example.com"}
Result: {"id": 1, "email": "new@example.com"}  // Other fields may be lost!
```

### Best Practices

```
DO:
✓ Send only the fields to update
✓ Document which fields are patchable
✓ Return updated resource in response
✓ Handle null values explicitly

DON'T:
✗ Require complete resource representation
✗ Assume all PATCH operations are idempotent
```

---

## DELETE Method

**Purpose:** Remove a resource

### Characteristics
- **Not Safe**: Modifies server state
- **Idempotent**: Deleting already deleted resource still succeeds
- **Not Cacheable**: Responses not cached
- **Optional Body**: Usually no body needed

### Examples

```http
# Delete a user
DELETE /api/users/123 HTTP/1.1
Host: api.example.com
Authorization: Bearer token123

# Response - Success
HTTP/1.1 204 No Content
```

```http
# Response if not found (still idempotent)
HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "error": "User not found"
}
```

```http
# Soft delete (mark as deleted)
DELETE /api/users/123 HTTP/1.1
Host: api.example.com

# Response
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 123,
    "deleted": true,
    "deletedAt": "2024-10-21T10:30:00Z"
}
```

### Best Practices

```
DO:
✓ Return 204 No Content on success
✓ Consider soft delete for audit trails
✓ Handle cascading deletes appropriately
✓ Return 404 or 200 for already deleted resources

DON'T:
✗ Return errors for already deleted resources (breaks idempotency)
✗ Forget to handle related resources
```

---

## HEAD Method

**Purpose:** Identical to GET but returns only headers (no body)

### Characteristics
- **Safe**: Does not modify server state
- **Idempotent**: Same result for multiple requests
- **Cacheable**: Headers can be cached

### Use Cases

```http
# Check if resource exists
HEAD /api/users/123 HTTP/1.1
Host: api.example.com

# Response - 200 OK (exists)
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 245
Last-Modified: Wed, 21 Oct 2024 07:28:00 GMT
ETag: "abc123"

# Response - 404 Not Found (doesn't exist)
HTTP/1.1 404 Not Found
```

```http
# Check file size before download
HEAD /downloads/large-file.zip HTTP/1.1
Host: example.com

# Response
HTTP/1.1 200 OK
Content-Length: 104857600
Accept-Ranges: bytes
```

---

## OPTIONS Method

**Purpose:** Describe communication options for a resource

### Characteristics
- **Safe**: Does not modify server state
- **Idempotent**: Same result for multiple requests
- **Used for CORS**: Preflight requests

### Examples

```http
# Check allowed methods
OPTIONS /api/users HTTP/1.1
Host: api.example.com

# Response
HTTP/1.1 204 No Content
Allow: GET, POST, HEAD, OPTIONS
```

```http
# CORS Preflight request
OPTIONS /api/users HTTP/1.1
Host: api.example.com
Origin: https://frontend.com
Access-Control-Request-Method: POST
Access-Control-Request-Headers: Content-Type, Authorization

# Response
HTTP/1.1 204 No Content
Access-Control-Allow-Origin: https://frontend.com
Access-Control-Allow-Methods: GET, POST, PUT, DELETE
Access-Control-Allow-Headers: Content-Type, Authorization
Access-Control-Max-Age: 86400
```

---

## Safe and Idempotent Methods

### Safe Methods

Safe methods do not modify server state. They are read-only operations.

```
Safe Methods:    GET, HEAD, OPTIONS, TRACE
Unsafe Methods:  POST, PUT, PATCH, DELETE
```

### Idempotent Methods

Idempotent methods produce the same result when called multiple times.

```
Idempotent:     GET, HEAD, OPTIONS, PUT, DELETE
Non-Idempotent: POST, PATCH
```

### Visual Comparison

```
                 Safe?    Idempotent?   Example
            ┌────────────────────────────────────┐
GET         │    Yes         Yes        Read user
HEAD        │    Yes         Yes        Check existence
OPTIONS     │    Yes         Yes        Get capabilities
            ├────────────────────────────────────┤
PUT         │    No          Yes        Replace user
DELETE      │    No          Yes        Remove user
            ├────────────────────────────────────┤
POST        │    No          No         Create user
PATCH       │    No          No         Update field
            └────────────────────────────────────┘
```

---

## Methods in REST APIs

```
HTTP Method    CRUD Operation    Collection (/users)    Item (/users/123)
────────────────────────────────────────────────────────────────────────
GET            Read              List all users         Get one user
POST           Create            Create new user        -
PUT            Update/Replace    Replace all users*     Replace user
PATCH          Update            -                      Partial update
DELETE         Delete            Delete all users*      Delete user

* Bulk operations on collections are less common
```

### Example REST API

```http
# List users
GET /api/users

# Create user
POST /api/users

# Get user
GET /api/users/123

# Update user
PUT /api/users/123

# Partial update
PATCH /api/users/123

# Delete user
DELETE /api/users/123
```

---

## Key Takeaways

1. **GET for reading** - Never modify data with GET
2. **POST for creating** - Server assigns resource ID
3. **PUT for replacing** - Send complete representation
4. **PATCH for updating** - Send only changed fields
5. **DELETE for removing** - Should be idempotent
6. **Safe methods** - Don't modify state (GET, HEAD, OPTIONS)
7. **Idempotent methods** - Same result on repeat (GET, PUT, DELETE)

---

## Next Topic

Continue to [HTTP Lifecycle](./03-http-lifecycle.md) to understand the complete journey of an HTTP request.
