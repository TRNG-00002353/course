# HTTP Headers

## Overview

HTTP headers allow clients and servers to pass additional information with requests and responses. They define the operating parameters of an HTTP transaction and are crucial for content negotiation, authentication, caching, and security.

---

## Header Structure

Headers follow a simple `Name: Value` format:

```http
Header-Name: header-value
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

### Rules

- Header names are **case-insensitive** (Content-Type = content-type)
- Values are **case-sensitive** (application/json ≠ Application/JSON)
- Multiple values can be comma-separated: `Accept: text/html, application/json`
- Custom headers should use `X-` prefix (deprecated) or company prefix

---

## Header Categories

```
┌─────────────────────────────────────────────────────────────┐
│                     HTTP Headers                             │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  Request Headers          Response Headers                   │
│  ────────────────        ─────────────────                  │
│  Accept                   Content-Type                       │
│  Authorization            Set-Cookie                         │
│  Content-Type             Cache-Control                      │
│  User-Agent               ETag                               │
│  Cookie                   Location                           │
│  Origin                   WWW-Authenticate                   │
│                                                              │
│  Entity Headers (Both)    General Headers (Both)             │
│  ────────────────────    ──────────────────────             │
│  Content-Type             Date                               │
│  Content-Length           Connection                         │
│  Content-Encoding         Transfer-Encoding                  │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

---

## Common Request Headers

### Content Negotiation Headers

```http
# Tell server what formats client accepts
Accept: application/json, text/html;q=0.9, */*;q=0.8
Accept-Language: en-US, en;q=0.9, fr;q=0.8
Accept-Encoding: gzip, deflate, br
Accept-Charset: utf-8, iso-8859-1;q=0.5
```

#### Quality Values (q-values)

```http
Accept: application/json;q=1.0, application/xml;q=0.9, text/html;q=0.8

# Interpretation:
# 1. Prefer JSON (q=1.0, default if not specified)
# 2. Then XML (q=0.9)
# 3. Then HTML (q=0.8)
```

### Authentication Headers

```http
# Basic Authentication (base64 encoded username:password)
Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=

# Bearer Token (JWT or API key)
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# API Key (custom header)
X-API-Key: your-api-key-here
```

### Host Header

```http
# Required in HTTP/1.1 - identifies the target host
Host: api.example.com
Host: api.example.com:8080  # With non-default port
```

### User-Agent Header

```http
# Identifies the client software
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/91.0

# Custom user agent
User-Agent: MyApp/1.0.0 (contact@example.com)
```

### Content Headers (for POST/PUT/PATCH)

```http
# Body format
Content-Type: application/json
Content-Type: application/x-www-form-urlencoded
Content-Type: multipart/form-data; boundary=----FormBoundary

# Body size
Content-Length: 1234

# Body encoding
Content-Encoding: gzip
```

### Conditional Request Headers

```http
# Only fetch if changed (caching)
If-None-Match: "etag-value"
If-Modified-Since: Wed, 21 Oct 2024 07:28:00 GMT

# Only update if not changed (optimistic locking)
If-Match: "etag-value"
If-Unmodified-Since: Wed, 21 Oct 2024 07:28:00 GMT
```

### Cookie Header

```http
# Send stored cookies to server
Cookie: session=abc123; user=john; preferences=dark
```

### Complete Request Example

```http
POST /api/users HTTP/1.1
Host: api.example.com
Content-Type: application/json
Content-Length: 67
Accept: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
User-Agent: MyApp/1.0
Cookie: session=xyz789
Cache-Control: no-cache
Origin: https://myapp.com

{"name": "John Doe", "email": "john@example.com"}
```

---

## Common Response Headers

### Content Headers

```http
# Response body format
Content-Type: application/json; charset=utf-8

# Response body size
Content-Length: 1234

# Compression used
Content-Encoding: gzip

# Language of content
Content-Language: en-US

# For file downloads
Content-Disposition: attachment; filename="report.pdf"
```

### Cache Control Headers

```http
# Caching directives
Cache-Control: public, max-age=3600
Cache-Control: private, no-cache
Cache-Control: no-store

# Resource version
ETag: "abc123"
ETag: W/"weak-etag"  # Weak validator

# Last modification time
Last-Modified: Wed, 21 Oct 2024 07:28:00 GMT

# Expiration (deprecated, use Cache-Control)
Expires: Wed, 21 Oct 2024 08:28:00 GMT
```

#### Cache-Control Directives

| Directive | Description |
|-----------|-------------|
| `public` | Cacheable by any cache |
| `private` | Only cacheable by browser |
| `no-cache` | Must revalidate before use |
| `no-store` | Never cache |
| `max-age=N` | Cache for N seconds |
| `s-maxage=N` | Shared cache max age |
| `must-revalidate` | Must check if stale |
| `immutable` | Never changes |

### Cookie Headers

```http
# Set a cookie
Set-Cookie: session=abc123; Path=/; HttpOnly; Secure; SameSite=Strict
Set-Cookie: preferences=dark; Max-Age=31536000

# Multiple cookies require multiple headers
Set-Cookie: cookie1=value1
Set-Cookie: cookie2=value2
```

### Redirect Headers

```http
# New location for redirects (3xx responses)
Location: https://example.com/new-page
Location: /api/users/123  # Relative URL
```

### Authentication Challenge

```http
# Request authentication from client (401 response)
WWW-Authenticate: Bearer realm="api", error="invalid_token"
WWW-Authenticate: Basic realm="Admin Area"
```

### Complete Response Example

```http
HTTP/1.1 200 OK
Date: Wed, 21 Oct 2024 07:28:00 GMT
Server: nginx/1.18.0
Content-Type: application/json; charset=utf-8
Content-Length: 245
Content-Encoding: gzip
Cache-Control: private, max-age=300
ETag: "abc123"
Set-Cookie: session=xyz789; Path=/; HttpOnly; Secure
X-Request-Id: req-12345
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95

{"users": [...]}
```

---

## CORS Headers

Cross-Origin Resource Sharing (CORS) headers control cross-domain access.

### Preflight Request (OPTIONS)

```http
OPTIONS /api/users HTTP/1.1
Host: api.example.com
Origin: https://frontend.com
Access-Control-Request-Method: POST
Access-Control-Request-Headers: Content-Type, Authorization
```

### Preflight Response

```http
HTTP/1.1 204 No Content
Access-Control-Allow-Origin: https://frontend.com
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: Content-Type, Authorization
Access-Control-Max-Age: 86400
Access-Control-Allow-Credentials: true
```

### CORS Headers Explained

| Header | Description |
|--------|-------------|
| `Access-Control-Allow-Origin` | Allowed origin(s) |
| `Access-Control-Allow-Methods` | Allowed HTTP methods |
| `Access-Control-Allow-Headers` | Allowed request headers |
| `Access-Control-Expose-Headers` | Headers client can read |
| `Access-Control-Max-Age` | Preflight cache duration |
| `Access-Control-Allow-Credentials` | Allow cookies/auth |

### CORS Flow

```
┌─────────────────────────────────────────────────────────────┐
│                    CORS Request Flow                         │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  Simple Request (GET, HEAD, POST with simple headers)        │
│  ─────────────────────────────────────────────────          │
│  Browser ──── Request + Origin header ────> Server           │
│  Browser <─── Response + CORS headers ───── Server           │
│                                                              │
│  Preflighted Request (PUT, DELETE, custom headers)           │
│  ─────────────────────────────────────────────────          │
│  Browser ──── OPTIONS (preflight) ────────> Server           │
│  Browser <─── 204 + CORS headers ─────────  Server           │
│  Browser ──── Actual Request ─────────────> Server           │
│  Browser <─── Response ───────────────────  Server           │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

---

## Security Headers

### Content Security Policy (CSP)

```http
# Prevent XSS attacks
Content-Security-Policy: default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' https://fonts.googleapis.com

# Report violations
Content-Security-Policy-Report-Only: default-src 'self'; report-uri /csp-report
```

### Other Security Headers

```http
# Prevent clickjacking
X-Frame-Options: DENY
X-Frame-Options: SAMEORIGIN

# Force HTTPS
Strict-Transport-Security: max-age=31536000; includeSubDomains; preload

# Prevent MIME sniffing
X-Content-Type-Options: nosniff

# XSS protection (legacy, CSP preferred)
X-XSS-Protection: 1; mode=block

# Referrer policy
Referrer-Policy: strict-origin-when-cross-origin

# Permissions policy
Permissions-Policy: geolocation=(), microphone=(), camera=()
```

### Security Headers Checklist

```
Essential Security Headers:
├── Content-Security-Policy
├── Strict-Transport-Security
├── X-Content-Type-Options: nosniff
├── X-Frame-Options: DENY
├── Referrer-Policy
└── Permissions-Policy
```

---

## Custom Headers

### Naming Conventions

```http
# Deprecated: X- prefix
X-Custom-Header: value

# Modern: Company/app prefix
MyApp-Request-Id: abc123
GitHub-Request-Id: ABC:123

# Common custom headers
X-Request-Id: req-12345           # Request tracking
X-Correlation-Id: corr-67890      # Distributed tracing
X-RateLimit-Limit: 100            # Rate limiting info
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1634812860
X-Response-Time: 45ms             # Performance metric
```

### Rate Limiting Headers

```http
HTTP/1.1 200 OK
X-RateLimit-Limit: 100            # Max requests per window
X-RateLimit-Remaining: 95         # Requests remaining
X-RateLimit-Reset: 1634812860     # Window reset time (Unix timestamp)
Retry-After: 60                   # Seconds until retry (on 429)
```

---

## Header Best Practices

### Request Headers

```
DO:
✓ Set Accept header for expected response format
✓ Include Content-Type for request body
✓ Use Authorization for authentication
✓ Set appropriate caching headers

DON'T:
✗ Send sensitive data in headers that might be logged
✗ Use excessively long header values
✗ Invent new headers when standard ones exist
```

### Response Headers

```
DO:
✓ Set Content-Type accurately
✓ Include security headers
✓ Use proper caching headers
✓ Include CORS headers when needed

DON'T:
✗ Expose server internals in headers
✗ Forget to set charset in Content-Type
✗ Use overly permissive CORS settings
```

---

## Headers Quick Reference

### Most Used Request Headers

| Header | Purpose | Example |
|--------|---------|---------|
| Accept | Expected format | `application/json` |
| Authorization | Auth credentials | `Bearer token` |
| Content-Type | Body format | `application/json` |
| Cookie | Send cookies | `session=abc` |
| Host | Target server | `api.example.com` |
| User-Agent | Client info | `MyApp/1.0` |
| Origin | Request origin | `https://app.com` |
| If-None-Match | Conditional | `"etag"` |

### Most Used Response Headers

| Header | Purpose | Example |
|--------|---------|---------|
| Content-Type | Body format | `application/json` |
| Content-Length | Body size | `1234` |
| Set-Cookie | Set cookies | `session=xyz` |
| Cache-Control | Caching | `max-age=3600` |
| ETag | Version | `"abc123"` |
| Location | Redirect URL | `/new-location` |
| Access-Control-* | CORS | See above |

---

## Key Takeaways

1. **Headers are metadata** - They describe the request/response
2. **Content-Type is crucial** - Always set it correctly
3. **Cache headers save bandwidth** - Use them properly
4. **Security headers protect users** - Include them in responses
5. **CORS is essential** - For cross-origin requests
6. **Authorization formats vary** - Basic, Bearer, API keys
7. **Custom headers are fine** - But prefer standard ones

---

## Next Topic

Continue to [HTTP Cookies](./06-http-cookies.md) to learn about state management in HTTP.
