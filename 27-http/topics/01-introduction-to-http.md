# Introduction to HTTP

## Overview

HTTP (Hypertext Transfer Protocol) is the foundation of data communication on the World Wide Web. It defines how messages are formatted and transmitted, and how web servers and browsers should respond to various commands.

---

## What is HTTP?

HTTP is an **application-layer protocol** that enables communication between clients (typically web browsers) and servers. It follows a **request-response model** where a client sends a request and the server returns a response.

### Key Characteristics

| Characteristic | Description |
|----------------|-------------|
| **Stateless** | Each request is independent; server doesn't remember previous requests |
| **Text-based** | HTTP messages are human-readable (until HTTP/2) |
| **Extensible** | Headers allow adding new functionality |
| **Request-Response** | Communication always starts with client request |

```
Client (Browser)              Server (Web Server)
      │                              │
      │   ── HTTP Request ───>       │
      │   GET /index.html            │
      │                              │
      │   <── HTTP Response ──       │
      │   200 OK + HTML content      │
      │                              │
```

---

## History and Evolution

### HTTP Timeline

```
1989 ─ Tim Berners-Lee proposes the World Wide Web
   │
1991 ─ HTTP/0.9 - Simple GET requests
   │   • Only HTML documents
   │   • No headers, no status codes
   │
1996 ─ HTTP/1.0 - First standardized version
   │   • Headers introduced
   │   • Status codes added
   │   • POST method added
   │   • Content types (images, etc.)
   │
1997 ─ HTTP/1.1 - Major improvements
   │   • Persistent connections (Keep-Alive)
   │   • Pipelining
   │   • Chunked transfer encoding
   │   • Host header (virtual hosting)
   │   • Cache control
   │
2015 ─ HTTP/2 - Performance focused
   │   • Binary protocol
   │   • Multiplexing
   │   • Header compression
   │   • Server push
   │   • Stream prioritization
   │
2022 ─ HTTP/3 - Latest version
       • QUIC protocol (UDP-based)
       • Faster connections
       • Better mobile performance
       • Built-in encryption
```

### Version Comparison

| Feature | HTTP/1.0 | HTTP/1.1 | HTTP/2 | HTTP/3 |
|---------|----------|----------|--------|--------|
| Connection | New per request | Persistent | Multiplexed | QUIC |
| Head-of-line blocking | Per connection | Per connection | Solved | Solved |
| Header compression | No | No | HPACK | QPACK |
| Protocol | Text | Text | Binary | Binary |
| Transport | TCP | TCP | TCP | UDP/QUIC |
| Encryption | Optional | Optional | Practical TLS | Always |

---

## HTTP vs HTTPS

HTTPS (HTTP Secure) is HTTP with encryption using TLS (Transport Layer Security).

```
HTTP Request Flow:
┌────────┐   Plain Text    ┌────────┐
│ Client │ ───────────────>│ Server │
└────────┘                  └────────┘
          Anyone can read!

HTTPS Request Flow:
┌────────┐   Encrypted     ┌────────┐
│ Client │ ═══════════════>│ Server │
└────────┘   (TLS/SSL)     └────────┘
          Only endpoints can read
```

### Key Differences

| Aspect | HTTP | HTTPS |
|--------|------|-------|
| **Default Port** | 80 | 443 |
| **URL Prefix** | http:// | https:// |
| **Data Security** | Plain text | Encrypted |
| **Certificate** | Not required | SSL/TLS certificate required |
| **Performance** | Slightly faster | Minor overhead |
| **SEO Impact** | Lower ranking | Higher ranking |
| **Use Case** | Development/testing | Production |

### HTTPS Benefits

1. **Confidentiality**: Data encrypted during transmission
2. **Integrity**: Data cannot be modified in transit
3. **Authentication**: Server identity verified via certificate
4. **Trust**: Browser shows padlock icon
5. **SEO**: Google prefers HTTPS sites

---

## Client-Server Architecture

HTTP follows a client-server architecture where the client initiates requests and the server responds.

```
┌─────────────────────────────────────────────────────────────┐
│                       INTERNET                               │
│                                                              │
│  ┌──────────┐                              ┌──────────┐     │
│  │  Client  │                              │  Server  │     │
│  │ (Browser)│                              │   (Web)  │     │
│  ├──────────┤     HTTP Request             ├──────────┤     │
│  │          │  ────────────────────>       │          │     │
│  │ - HTML   │  GET /page.html              │ - Apache │     │
│  │ - CSS    │                              │ - Nginx  │     │
│  │ - JS     │     HTTP Response            │ - Node.js│     │
│  │          │  <────────────────────       │          │     │
│  │          │  200 OK + content            │          │     │
│  └──────────┘                              └──────────┘     │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### Roles

**Client:**
- Initiates connections
- Sends requests
- Processes responses
- Examples: Browsers, mobile apps, curl, Postman

**Server:**
- Listens for connections
- Processes requests
- Sends responses
- Examples: Apache, Nginx, Node.js, Spring Boot

---

## Request-Response Model

Every HTTP communication consists of a request from the client and a response from the server.

### HTTP Request Structure

```http
┌─────────────────────────────────────────────────────────────┐
│ REQUEST LINE                                                 │
│ GET /api/users?page=1 HTTP/1.1                              │
│ ↑      ↑                ↑                                    │
│ Method  Path            Version                              │
├─────────────────────────────────────────────────────────────┤
│ HEADERS                                                      │
│ Host: api.example.com                                        │
│ Accept: application/json                                     │
│ Authorization: Bearer eyJhbGciOiJIUzI1NiIs...               │
│ User-Agent: Mozilla/5.0                                      │
├─────────────────────────────────────────────────────────────┤
│ BLANK LINE                                                   │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│ BODY (optional, for POST/PUT/PATCH)                         │
│ {"name": "John", "email": "john@example.com"}               │
└─────────────────────────────────────────────────────────────┘
```

### HTTP Response Structure

```http
┌─────────────────────────────────────────────────────────────┐
│ STATUS LINE                                                  │
│ HTTP/1.1 200 OK                                             │
│ ↑        ↑   ↑                                               │
│ Version  Code Reason                                         │
├─────────────────────────────────────────────────────────────┤
│ HEADERS                                                      │
│ Content-Type: application/json                               │
│ Content-Length: 245                                          │
│ Date: Wed, 21 Oct 2024 07:28:00 GMT                         │
│ Cache-Control: max-age=3600                                  │
├─────────────────────────────────────────────────────────────┤
│ BLANK LINE                                                   │
│                                                              │
├─────────────────────────────────────────────────────────────┤
│ BODY                                                         │
│ {                                                            │
│   "users": [                                                 │
│     {"id": 1, "name": "John"},                              │
│     {"id": 2, "name": "Jane"}                               │
│   ]                                                          │
│ }                                                            │
└─────────────────────────────────────────────────────────────┘
```

---

## URLs and URIs

### URL Structure

URL (Uniform Resource Locator) is an address for locating resources on the web.

```
https://user:pass@www.example.com:443/path/to/resource?query=value#section
└─┬─┘   └──┬───┘ └──────┬───────┘└┬┘└──────┬─────────┘└────┬─────┘└──┬──┘
scheme  userinfo       host     port     path            query    fragment
        (optional)           (optional)                (optional) (optional)
```

### URL Components

| Component | Description | Example |
|-----------|-------------|---------|
| **Scheme** | Protocol to use | `https`, `http`, `ftp` |
| **Host** | Server domain/IP | `www.example.com`, `192.168.1.1` |
| **Port** | Server port | `:443`, `:8080` (optional if default) |
| **Path** | Resource location | `/api/users/123` |
| **Query** | Parameters | `?name=john&age=30` |
| **Fragment** | Page section | `#section-name` (client-side only) |

### URI vs URL vs URN

```
URI (Uniform Resource Identifier)
├── URL (Uniform Resource Locator)
│   └── Specifies location AND how to access
│       Example: https://example.com/page.html
│
└── URN (Uniform Resource Name)
    └── Specifies name only, not location
        Example: urn:isbn:0451450523
```

### URL Encoding

Special characters in URLs must be percent-encoded:

| Character | Encoded |
|-----------|---------|
| Space | `%20` or `+` |
| `?` | `%3F` |
| `&` | `%26` |
| `=` | `%3D` |
| `/` | `%2F` |
| `#` | `%23` |

```javascript
// JavaScript URL encoding
encodeURIComponent("hello world")  // "hello%20world"
encodeURIComponent("name=john&age=30")  // "name%3Djohn%26age%3D30"

decodeURIComponent("hello%20world")  // "hello world"
```

---

## Practical Examples

### Browser Request

When you type `https://example.com/page` in a browser:

```http
GET /page HTTP/1.1
Host: example.com
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate, br
Connection: keep-alive
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) Firefox/89.0
```

### API Request

```http
GET /api/users/123 HTTP/1.1
Host: api.example.com
Accept: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Viewing HTTP in Browser

Open Developer Tools (F12) → Network tab to see:
- All HTTP requests made by the page
- Request/response headers
- Response body
- Timing information
- Status codes

---

## Key Takeaways

1. **HTTP is stateless** - Each request is independent
2. **Request-Response model** - Client asks, server responds
3. **HTTPS is essential** - Always use encryption in production
4. **URLs identify resources** - Properly structured URLs improve API design
5. **Headers control behavior** - Metadata about requests/responses
6. **HTTP/2+ improves performance** - Modern protocols are more efficient

---

## Next Topic

Continue to [HTTP Methods](./02-http-methods.md) to learn about GET, POST, PUT, DELETE, and other HTTP methods.
