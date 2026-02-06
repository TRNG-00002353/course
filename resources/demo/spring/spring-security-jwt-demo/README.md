# Spring Security JWT Demo

A complete Spring Security implementation with JWT (JSON Web Token) authentication for REST APIs.

## Overview

This project demonstrates:

- **JWT Authentication**: Stateless token-based authentication
- **Role-Based Access Control**: USER and ADMIN roles
- **Method-Level Security**: Using `@PreAuthorize` annotations
- **Password Encoding**: BCrypt for secure password storage
- **OpenAPI Documentation**: Swagger UI with security schemes

## How JWT Security Works

### Complete Authentication Flow

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                           JWT AUTHENTICATION FLOW                              ║
╠═══════════════════════════════════════════════════════════════════════════════╣
║                                                                               ║
║   ┌──────────┐                                              ┌──────────────┐  ║
║   │  Client  │                                              │    Server    │  ║
║   └────┬─────┘                                              └───────┬──────┘  ║
║        │                                                            │         ║
║        │  ════════════════ PHASE 1: LOGIN ══════════════════       │         ║
║        │                                                            │         ║
║        │  1. POST /api/auth/login                                  │         ║
║        │     ┌─────────────────────────────┐                       │         ║
║        │────▶│ { "email": "user@test.com", │──────────────────────▶│         ║
║        │     │   "password": "password123" }                       │         ║
║        │     └─────────────────────────────┘                       │         ║
║        │                                                            │         ║
║        │                                    2. Validate credentials │         ║
║        │                                       ┌────────────────┐   │         ║
║        │                                       │ Load user from │   │         ║
║        │                                       │ database       │   │         ║
║        │                                       │ Check password │   │         ║
║        │                                       │ (BCrypt)       │   │         ║
║        │                                       └────────────────┘   │         ║
║        │                                                            │         ║
║        │                                    3. Generate JWT token   │         ║
║        │                                       ┌────────────────┐   │         ║
║        │                                       │ Create payload │   │         ║
║        │                                       │ Sign with      │   │         ║
║        │                                       │ secret key     │   │         ║
║        │                                       └────────────────┘   │         ║
║        │                                                            │         ║
║        │  4. Return JWT token                                      │         ║
║        │     ┌─────────────────────────────────────────────────┐   │         ║
║        │◀────│ { "token": "eyJhbGciOiJIUzI1NiJ9.eyJz...",      │◀──│         ║
║        │     │   "type": "Bearer",                              │   │         ║
║        │     │   "expiresIn": 86400000 }                        │   │         ║
║        │     └─────────────────────────────────────────────────┘   │         ║
║        │                                                            │         ║
║   ┌────┴─────┐                                                      │         ║
║   │  Client  │  5. Store token (localStorage, memory, etc.)        │         ║
║   └────┬─────┘                                                      │         ║
║        │                                                            │         ║
║        │  ═══════════ PHASE 2: ACCESS PROTECTED RESOURCE ══════════ │         ║
║        │                                                            │         ║
║        │  6. GET /api/users/me                                     │         ║
║        │     ┌─────────────────────────────────────────────────┐   │         ║
║        │────▶│ Headers:                                         │──▶│         ║
║        │     │   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9... │   │         ║
║        │     └─────────────────────────────────────────────────┘   │         ║
║        │                                                            │         ║
║        │                                    7. JwtAuthFilter       │         ║
║        │                                       validates token      │         ║
║        │                                                            │         ║
║        │                                    8. Load user, check    │         ║
║        │                                       authorization        │         ║
║        │                                                            │         ║
║        │  9. Return protected data                                 │         ║
║        │     ┌─────────────────────────────────────────────────┐   │         ║
║        │◀────│ { "id": 1, "name": "User", "role": "USER" }     │◀──│         ║
║        │     └─────────────────────────────────────────────────┘   │         ║
║        │                                                            │         ║
╚═══════════════════════════════════════════════════════════════════════════════╝
```

---

### JWT Token Structure

```
╔══════════════════════════════════════════════════════════════════════════════╗
║                              JWT TOKEN ANATOMY                                ║
╠══════════════════════════════════════════════════════════════════════════════╣
║                                                                              ║
║  A JWT consists of three parts separated by dots (.)                         ║
║                                                                              ║
║  eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIi...signature        ║
║  ├────────────────────┤├─────────────────────────────────────┤├───────────┤  ║
║         HEADER                      PAYLOAD                    SIGNATURE     ║
║                                                                              ║
║  ┌─────────────────────────────────────────────────────────────────────────┐ ║
║  │                            1. HEADER (Red)                              │ ║
║  │  Base64 encoded JSON describing the token                               │ ║
║  │                                                                         │ ║
║  │  {                                                                      │ ║
║  │    "alg": "HS256",    ◄── Algorithm used for signing                   │ ║
║  │    "typ": "JWT"        ◄── Token type                                   │ ║
║  │  }                                                                      │ ║
║  └─────────────────────────────────────────────────────────────────────────┘ ║
║                                                                              ║
║  ┌─────────────────────────────────────────────────────────────────────────┐ ║
║  │                           2. PAYLOAD (Purple)                           │ ║
║  │  Base64 encoded JSON containing claims (user data)                      │ ║
║  │                                                                         │ ║
║  │  {                                                                      │ ║
║  │    "sub": "user@example.com",  ◄── Subject (username/email)            │ ║
║  │    "iat": 1707218000,          ◄── Issued At (Unix timestamp)          │ ║
║  │    "exp": 1707304400           ◄── Expiration (Unix timestamp)         │ ║
║  │  }                                                                      │ ║
║  │                                                                         │ ║
║  │  ⚠️  Payload is NOT encrypted, only encoded!                           │ ║
║  │     Anyone can decode and read it. Never put secrets here.             │ ║
║  └─────────────────────────────────────────────────────────────────────────┘ ║
║                                                                              ║
║  ┌─────────────────────────────────────────────────────────────────────────┐ ║
║  │                          3. SIGNATURE (Blue)                            │ ║
║  │  Cryptographic signature to verify authenticity                         │ ║
║  │                                                                         │ ║
║  │  HMACSHA256(                                                            │ ║
║  │    base64UrlEncode(header) + "." + base64UrlEncode(payload),           │ ║
║  │    secret_key   ◄── Only the server knows this!                        │ ║
║  │  )                                                                      │ ║
║  │                                                                         │ ║
║  │  ✓ If anyone modifies header or payload, signature becomes invalid     │ ║
║  │  ✓ Server can verify token wasn't tampered with                        │ ║
║  └─────────────────────────────────────────────────────────────────────────┘ ║
║                                                                              ║
╚══════════════════════════════════════════════════════════════════════════════╝
```

---

### Request Flow Through Security Filter Chain

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                    SECURITY FILTER CHAIN - REQUEST LIFECYCLE                   ║
╠═══════════════════════════════════════════════════════════════════════════════╣
║                                                                               ║
║   Incoming Request: GET /api/users/me                                         ║
║   Headers: Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...                      ║
║                                                                               ║
║   ┌─────────────────────────────────────────────────────────────────────┐     ║
║   │                                                                     │     ║
║   ▼                                                                     │     ║
║ ┌───────────────────────────────────────────────────────────────────┐   │     ║
║ │                    JwtAuthenticationFilter                         │   │     ║
║ │  ┌─────────────────────────────────────────────────────────────┐  │   │     ║
║ │  │ 1. Extract Authorization header                              │  │   │     ║
║ │  │    authHeader = request.getHeader("Authorization")           │  │   │     ║
║ │  │                                                              │  │   │     ║
║ │  │ 2. Check if starts with "Bearer "                           │  │   │     ║
║ │  │    if (authHeader == null || !authHeader.startsWith("Bearer ")) │  │     ║
║ │  │        → continue filter chain (no auth attempt)             │  │   │     ║
║ │  │                                                              │  │   │     ║
║ │  │ 3. Extract token (remove "Bearer " prefix)                  │  │   │     ║
║ │  │    jwt = authHeader.substring(7)                            │  │   │     ║
║ │  │                                                              │  │   │     ║
║ │  │ 4. Extract username from token                              │  │   │     ║
║ │  │    username = jwtService.extractUsername(jwt)               │  │   │     ║
║ │  │                                                              │  │   │     ║
║ │  │ 5. Load user from database                                  │  │   │     ║
║ │  │    userDetails = userDetailsService.loadUserByUsername()    │  │   │     ║
║ │  │                                                              │  │   │     ║
║ │  │ 6. Validate token                                           │  │   │     ║
║ │  │    if (jwtService.isTokenValid(jwt, userDetails))           │  │   │     ║
║ │  │        → signature valid? expiration ok?                     │  │   │     ║
║ │  │                                                              │  │   │     ║
║ │  │ 7. Create Authentication object                             │  │   │     ║
║ │  │    authToken = new UsernamePasswordAuthenticationToken(     │  │   │     ║
║ │  │        userDetails, null, userDetails.getAuthorities()      │  │   │     ║
║ │  │    )                                                        │  │   │     ║
║ │  │                                                              │  │   │     ║
║ │  │ 8. Set SecurityContext                                      │  │   │     ║
║ │  │    SecurityContextHolder.getContext().setAuthentication()   │  │   │     ║
║ │  └─────────────────────────────────────────────────────────────┘  │   │     ║
║ └───────────────────────────────────────────────────────────────────┘   │     ║
║                                     │                                   │     ║
║                                     ▼                                   │     ║
║ ┌───────────────────────────────────────────────────────────────────┐   │     ║
║ │                    AuthorizationFilter                             │   │     ║
║ │  ┌─────────────────────────────────────────────────────────────┐  │   │     ║
║ │  │ Check if user has required permissions                       │  │   │     ║
║ │  │                                                              │  │   │     ║
║ │  │ Path: /api/users/**                                         │  │   │     ║
║ │  │ Required: authenticated()                                   │  │   │     ║
║ │  │ User has: ROLE_USER                                         │  │   │     ║
║ │  │ Result: ✓ ALLOWED                                           │  │   │     ║
║ │  └─────────────────────────────────────────────────────────────┘  │   │     ║
║ └───────────────────────────────────────────────────────────────────┘   │     ║
║                                     │                                   │     ║
║                                     ▼                                   │     ║
║ ┌───────────────────────────────────────────────────────────────────┐   │     ║
║ │                       Controller                                   │   │     ║
║ │  ┌─────────────────────────────────────────────────────────────┐  │   │     ║
║ │  │ @GetMapping("/me")                                          │  │   │     ║
║ │  │ public UserResponse getCurrentUser(                         │  │   │     ║
║ │  │     @AuthenticationPrincipal User user  ◄── Injected from  │  │   │     ║
║ │  │ ) {                                          SecurityContext│  │   │     ║
║ │  │     return UserResponse.fromEntity(user);                   │  │   │     ║
║ │  │ }                                                           │  │   │     ║
║ │  └─────────────────────────────────────────────────────────────┘  │   │     ║
║ └───────────────────────────────────────────────────────────────────┘   │     ║
║                                     │                                   │     ║
║                                     ▼                                   │     ║
║   Response: { "id": 1, "name": "User", "email": "user@example.com" }────┘     ║
║                                                                               ║
╚═══════════════════════════════════════════════════════════════════════════════╝
```

---

### Authentication vs Authorization

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                     AUTHENTICATION vs AUTHORIZATION                            ║
╠═══════════════════════════════════════════════════════════════════════════════╣
║                                                                               ║
║   ┌─────────────────────────────────┬─────────────────────────────────────┐   ║
║   │         AUTHENTICATION          │           AUTHORIZATION             │   ║
║   │         (WHO are you?)          │         (WHAT can you do?)          │   ║
║   ├─────────────────────────────────┼─────────────────────────────────────┤   ║
║   │                                 │                                     │   ║
║   │  "I am user@example.com"        │  "Can I access /api/admin?"         │   ║
║   │                                 │                                     │   ║
║   │  Verified by:                   │  Checked by:                        │   ║
║   │  • Valid JWT token              │  • User's roles/permissions         │   ║
║   │  • Token not expired            │  • Endpoint security rules          │   ║
║   │  • Signature matches            │  • @PreAuthorize annotations        │   ║
║   │                                 │                                     │   ║
║   │  Happens in:                    │  Happens in:                        │   ║
║   │  JwtAuthenticationFilter        │  AuthorizationFilter                │   ║
║   │                                 │  @PreAuthorize checks               │   ║
║   │                                 │                                     │   ║
║   └─────────────────────────────────┴─────────────────────────────────────┘   ║
║                                                                               ║
║   Example Flow:                                                               ║
║   ┌─────────────────────────────────────────────────────────────────────────┐ ║
║   │                                                                         │ ║
║   │  Request: GET /api/admin/dashboard                                      │ ║
║   │  Token belongs to: user@example.com (ROLE_USER)                         │ ║
║   │                                                                         │ ║
║   │  Step 1 - Authentication: ✓ PASS                                        │ ║
║   │           Token is valid, user exists                                   │ ║
║   │                                                                         │ ║
║   │  Step 2 - Authorization: ✗ FAIL                                        │ ║
║   │           /api/admin/** requires ROLE_ADMIN                             │ ║
║   │           User only has ROLE_USER                                       │ ║
║   │                                                                         │ ║
║   │  Result: 403 Forbidden                                                  │ ║
║   │                                                                         │ ║
║   └─────────────────────────────────────────────────────────────────────────┘ ║
║                                                                               ║
╚═══════════════════════════════════════════════════════════════════════════════╝
```

---

### Security Decision Tree

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                         SECURITY DECISION TREE                                 ║
╠═══════════════════════════════════════════════════════════════════════════════╣
║                                                                               ║
║                           Incoming Request                                     ║
║                                  │                                            ║
║                                  ▼                                            ║
║                    ┌─────────────────────────┐                                ║
║                    │ Is path /api/auth/** or │                                ║
║                    │ /api/public/** ?        │                                ║
║                    └───────────┬─────────────┘                                ║
║                          │           │                                        ║
║                         YES          NO                                       ║
║                          │           │                                        ║
║                          ▼           ▼                                        ║
║                    ┌─────────┐ ┌─────────────────────────┐                    ║
║                    │ ALLOW   │ │ Has Authorization       │                    ║
║                    │ (Public)│ │ header with Bearer?     │                    ║
║                    └─────────┘ └───────────┬─────────────┘                    ║
║                                      │           │                            ║
║                                     YES          NO                           ║
║                                      │           │                            ║
║                                      ▼           ▼                            ║
║                    ┌─────────────────────┐ ┌─────────────┐                    ║
║                    │ Is token signature  │ │ 401         │                    ║
║                    │ valid?              │ │ Unauthorized│                    ║
║                    └─────────┬───────────┘ └─────────────┘                    ║
║                        │           │                                          ║
║                       YES          NO                                         ║
║                        │           │                                          ║
║                        ▼           ▼                                          ║
║              ┌─────────────────┐ ┌─────────────┐                              ║
║              │ Is token        │ │ 401         │                              ║
║              │ expired?        │ │ Unauthorized│                              ║
║              └────────┬────────┘ └─────────────┘                              ║
║                 │           │                                                 ║
║                 NO         YES                                                ║
║                 │           │                                                 ║
║                 ▼           ▼                                                 ║
║     ┌─────────────────┐ ┌─────────────┐                                       ║
║     │ Does user have  │ │ 401         │                                       ║
║     │ required role?  │ │ Unauthorized│                                       ║
║     └────────┬────────┘ └─────────────┘                                       ║
║         │         │                                                           ║
║        YES        NO                                                          ║
║         │         │                                                           ║
║         ▼         ▼                                                           ║
║   ┌─────────┐ ┌─────────┐                                                     ║
║   │ ALLOW   │ │ 403     │                                                     ║
║   │ Request │ │Forbidden│                                                     ║
║   └─────────┘ └─────────┘                                                     ║
║                                                                               ║
╚═══════════════════════════════════════════════════════════════════════════════╝
```

---

### Password Storage (BCrypt)

```
╔═══════════════════════════════════════════════════════════════════════════════╗
║                         BCRYPT PASSWORD HASHING                                ║
╠═══════════════════════════════════════════════════════════════════════════════╣
║                                                                               ║
║   Registration:                                                               ║
║   ┌─────────────────────────────────────────────────────────────────────────┐ ║
║   │                                                                         │ ║
║   │   Plain Password: "Password123"                                         │ ║
║   │                        │                                                │ ║
║   │                        ▼                                                │ ║
║   │              ┌─────────────────────┐                                    │ ║
║   │              │  BCrypt Encoder     │                                    │ ║
║   │              │  + Random Salt      │                                    │ ║
║   │              └──────────┬──────────┘                                    │ ║
║   │                        │                                                │ ║
║   │                        ▼                                                │ ║
║   │   Stored Hash: "$2a$10$N9qo8uLOickgx2ZMRZoMy..."                        │ ║
║   │                 ├──┤├─┤├──────────────────────────┤                     │ ║
║   │                 │   │  └── Hashed password + salt                       │ ║
║   │                 │   └───── Cost factor (10 = 2^10 iterations)           │ ║
║   │                 └──────── Algorithm identifier                          │ ║
║   │                                                                         │ ║
║   └─────────────────────────────────────────────────────────────────────────┘ ║
║                                                                               ║
║   Login Verification:                                                         ║
║   ┌─────────────────────────────────────────────────────────────────────────┐ ║
║   │                                                                         │ ║
║   │   Input: "Password123"    Stored: "$2a$10$N9qo8uLO..."                  │ ║
║   │              │                           │                              │ ║
║   │              └─────────┬─────────────────┘                              │ ║
║   │                        │                                                │ ║
║   │                        ▼                                                │ ║
║   │              ┌─────────────────────┐                                    │ ║
║   │              │  BCrypt.matches()   │                                    │ ║
║   │              │  - Extract salt     │                                    │ ║
║   │              │  - Hash input       │                                    │ ║
║   │              │  - Compare hashes   │                                    │ ║
║   │              └──────────┬──────────┘                                    │ ║
║   │                        │                                                │ ║
║   │                        ▼                                                │ ║
║   │                  ✓ Match = Login Success                                │ ║
║   │                  ✗ No Match = 401 Unauthorized                          │ ║
║   │                                                                         │ ║
║   └─────────────────────────────────────────────────────────────────────────┘ ║
║                                                                               ║
║   Why BCrypt?                                                                 ║
║   • Automatic salting (prevents rainbow table attacks)                        ║
║   • Configurable cost factor (slow = harder to brute force)                   ║
║   • One-way function (cannot reverse hash to get password)                    ║
║                                                                               ║
╚═══════════════════════════════════════════════════════════════════════════════╝
```

---

## Architecture

## Project Structure

```
src/main/java/com/example/security/
├── SecurityJwtDemoApplication.java     # Main application
├── config/
│   ├── SecurityConfig.java             # Security configuration
│   ├── OpenApiConfig.java              # Swagger configuration
│   ├── GlobalExceptionHandler.java     # Error handling
│   └── DataInitializer.java            # Default users
├── controller/
│   ├── AuthController.java             # Login, register, refresh
│   ├── UserController.java             # User operations
│   ├── AdminController.java            # Admin operations
│   └── PublicController.java           # Public endpoints
├── dto/
│   ├── AuthRequest.java                # Login request
│   ├── RegisterRequest.java            # Registration request
│   ├── AuthResponse.java               # Auth response with token
│   ├── UserResponse.java               # User data response
│   └── ErrorResponse.java              # Error response
├── filter/
│   └── JwtAuthenticationFilter.java    # JWT validation filter
├── model/
│   ├── User.java                       # User entity
│   └── Role.java                       # Role enum
├── repository/
│   └── UserRepository.java             # User data access
└── service/
    ├── JwtService.java                 # JWT operations
    ├── AuthService.java                # Authentication logic
    └── UserService.java                # User operations
```

## Running the Application

```bash
# Navigate to project directory
cd spring-security-jwt-demo

# Run with Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/spring-security-jwt-demo-1.0.0.jar
```

**Application starts at:** http://localhost:8080

## Default Users

The application creates default users on startup:

| Email | Password | Role |
|-------|----------|------|
| admin@example.com | Admin123! | ADMIN |
| user@example.com | User1234 | USER |

## API Endpoints

### Public Endpoints (No Auth Required)

```bash
# Health check
GET /api/public/health

# API info
GET /api/public/info
```

### Authentication Endpoints

```bash
# Register new user
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "Password123"
}

# Login
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "Password123"
}

# Refresh token
POST /api/auth/refresh
Authorization: Bearer <current_token>
```

### User Endpoints (Auth Required)

```bash
# Get current user profile
GET /api/users/me
Authorization: Bearer <token>

# Get user by ID (own profile or ADMIN)
GET /api/users/{id}
Authorization: Bearer <token>

# Get all users (ADMIN only)
GET /api/users
Authorization: Bearer <token>

# Delete user (ADMIN only)
DELETE /api/users/{id}
Authorization: Bearer <token>
```

### Admin Endpoints (ADMIN Role Required)

```bash
# Promote user to admin
PATCH /api/admin/users/{id}/promote
Authorization: Bearer <token>

# Admin dashboard
GET /api/admin/dashboard
Authorization: Bearer <token>
```

## Testing the API

### Using cURL

```bash
# 1. Register a new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","password":"Test1234"}'

# 2. Login to get token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"Test1234"}'

# Response:
# {
#   "token": "eyJhbGciOiJIUzI1...",
#   "type": "Bearer",
#   "expiresIn": 86400000,
#   "user": {...}
# }

# 3. Access protected endpoint
curl http://localhost:8080/api/users/me \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1..."

# 4. Try admin endpoint (should fail with USER role)
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1..."

# 5. Login as admin
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"Admin123!"}'

# 6. Access admin endpoint (should work)
curl http://localhost:8080/api/admin/dashboard \
  -H "Authorization: Bearer <admin_token>"
```

### Using Swagger UI

1. Open http://localhost:8080/swagger-ui.html
2. Click "Authorize" button
3. Enter your JWT token (without "Bearer " prefix)
4. Try the endpoints

## Key Concepts

### Security Configuration

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())  // Disable for REST API
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()      // Public
            .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin only
            .anyRequest().authenticated()                      // Others need auth
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
```

### JWT Token Flow

1. User logs in with email/password
2. Server validates credentials
3. Server generates JWT with user info and expiration
4. Server returns JWT to client
5. Client stores JWT (localStorage, cookie, etc.)
6. Client sends JWT in `Authorization: Bearer <token>` header
7. Server validates JWT on each request
8. Server extracts user info from JWT
9. Server authorizes based on user roles

### Method-Level Security

```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping
public List<UserResponse> getAllUsers() { ... }

@PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
@GetMapping("/{id}")
public UserResponse getUserById(@PathVariable Long id) { ... }
```

## Configuration

```yaml
# application.yml

jwt:
  secret: <base64-encoded-secret-key>  # At least 256 bits
  expiration: 86400000                  # 24 hours in milliseconds

spring:
  datasource:
    url: jdbc:h2:mem:securitydb
```

## Security Best Practices Demonstrated

1. **Password Encoding**: BCrypt with automatic salting
2. **Stateless Sessions**: No server-side session storage
3. **Token Expiration**: Configurable expiration time
4. **Role-Based Access**: Endpoint and method-level authorization
5. **Input Validation**: Bean Validation on DTOs
6. **Error Handling**: Consistent error responses
7. **CORS Configuration**: Controlled cross-origin access

## Dependencies

- Spring Boot 3.2.0
- Spring Security
- JJWT (io.jsonwebtoken) 0.12.3
- H2 Database
- SpringDoc OpenAPI 2.3.0
