# HTTP Lifecycle

## Overview

Understanding the complete lifecycle of an HTTP request helps developers debug network issues, optimize performance, and implement proper error handling. This topic covers every step from when a user clicks a link to when the response is displayed.

---

## Complete HTTP Request Lifecycle

```
User Action
    │
    ▼
┌───────────────────┐
│ 1. URL Parsing    │  Parse the URL into components
└─────────┬─────────┘
          │
          ▼
┌───────────────────┐
│ 2. DNS Resolution │  Convert domain to IP address
└─────────┬─────────┘
          │
          ▼
┌───────────────────┐
│ 3. TCP Connection │  Establish connection (3-way handshake)
└─────────┬─────────┘
          │
          ▼
┌───────────────────┐
│ 4. TLS Handshake  │  Secure the connection (HTTPS only)
└─────────┬─────────┘
          │
          ▼
┌───────────────────┐
│ 5. HTTP Request   │  Send request to server
└─────────┬─────────┘
          │
          ▼
┌───────────────────┐
│ 6. Server Process │  Server handles the request
└─────────┬─────────┘
          │
          ▼
┌───────────────────┐
│ 7. HTTP Response  │  Receive response from server
└─────────┬─────────┘
          │
          ▼
┌───────────────────┐
│ 8. Connection     │  Close or keep-alive
└─────────┬─────────┘
          │
          ▼
    Render/Process
```

---

## 1. URL Parsing

When you enter `https://api.example.com:443/users?page=1#section` in a browser:

```javascript
URL Components:
┌─────────────────────────────────────────────────────────────┐
│ https://api.example.com:443/users?page=1#section           │
│ └──┬──┘  └──────┬──────┘└┬┘ └─┬─┘ └──┬──┘ └──┬──┘          │
│  scheme       host     port path   query  fragment         │
└─────────────────────────────────────────────────────────────┘

Parsed Result:
{
    "scheme": "https",
    "host": "api.example.com",
    "port": 443,
    "path": "/users",
    "query": "page=1",
    "fragment": "section"
}
```

### Browser Actions

1. Check if URL is valid
2. Check browser cache
3. Check HSTS (HTTP Strict Transport Security) preload list
4. Determine if request needs proxy

---

## 2. DNS Resolution

DNS (Domain Name System) converts human-readable domain names to IP addresses.

```
Client                 DNS System
  │
  │   "What is the IP for api.example.com?"
  │  ──────────────────────────────────────>
  │
  │                    ┌─────────────────┐
  │                    │ Browser Cache   │ Miss
  │                    └────────┬────────┘
  │                             ▼
  │                    ┌─────────────────┐
  │                    │ OS Cache        │ Miss
  │                    └────────┬────────┘
  │                             ▼
  │                    ┌─────────────────┐
  │                    │ Router Cache    │ Miss
  │                    └────────┬────────┘
  │                             ▼
  │                    ┌─────────────────┐
  │                    │ ISP DNS Server  │
  │                    └────────┬────────┘
  │                             │
  │                   Recursive Lookup
  │                             ▼
  │            ┌────────────────────────────┐
  │            │ Root → TLD → Authoritative │
  │            └────────────────────────────┘
  │
  │   "The IP is 93.184.216.34"
  │  <──────────────────────────────────────
```

### DNS Lookup Steps

```
1. Browser Cache
   └── Recently resolved domains (cached for TTL)

2. Operating System Cache
   └── /etc/hosts file
   └── OS DNS cache

3. Router Cache
   └── Home/office router cache

4. ISP DNS Server (Recursive Resolver)
   └── If not cached, performs recursive lookup:

5. Root Name Server
   └── "Who handles .com?"
   └── Returns: TLD server for .com

6. TLD Name Server (.com)
   └── "Who handles example.com?"
   └── Returns: Authoritative server for example.com

7. Authoritative Name Server
   └── "What is api.example.com?"
   └── Returns: 93.184.216.34
```

### DNS Record Types

| Type | Purpose | Example |
|------|---------|---------|
| A | IPv4 address | 93.184.216.34 |
| AAAA | IPv6 address | 2001:0db8:85a3::8a2e:0370:7334 |
| CNAME | Alias to another domain | www → example.com |
| MX | Mail server | mail.example.com |
| TXT | Text records | SPF, DKIM records |
| NS | Name servers | ns1.example.com |

---

## 3. TCP Connection

TCP (Transmission Control Protocol) establishes a reliable connection using a three-way handshake.

```
Client                                           Server
  │                                                 │
  │  ─────────── SYN (seq=x) ────────────────>     │
  │  "I want to connect"                            │
  │                                                 │
  │  <────────── SYN-ACK (seq=y, ack=x+1) ─────    │
  │  "OK, I acknowledge and want to connect too"   │
  │                                                 │
  │  ─────────── ACK (ack=y+1) ─────────────>     │
  │  "Connection established!"                      │
  │                                                 │
  │  ═══════════ Connection Ready ═════════════    │
```

### TCP Handshake Details

| Step | Packet | Description |
|------|--------|-------------|
| 1 | SYN | Client requests connection with sequence number |
| 2 | SYN-ACK | Server acknowledges and sends its sequence number |
| 3 | ACK | Client acknowledges server's sequence number |

### Connection Parameters

```
After handshake, both sides agree on:
├── Initial sequence numbers
├── Window size (flow control)
├── Maximum segment size (MSS)
└── TCP options (timestamps, SACK, etc.)
```

---

## 4. TLS Handshake (HTTPS)

For HTTPS connections, TLS (Transport Layer Security) establishes an encrypted channel.

```
Client                                           Server
  │                                                 │
  │  ─── ClientHello ────────────────────────>     │
  │      Supported TLS versions                     │
  │      Cipher suites                              │
  │      Random number                              │
  │                                                 │
  │  <── ServerHello ─────────────────────────     │
  │      Selected TLS version                       │
  │      Selected cipher suite                      │
  │      Random number                              │
  │                                                 │
  │  <── Certificate ─────────────────────────     │
  │      Server's SSL certificate                   │
  │      (Contains public key)                      │
  │                                                 │
  │  <── ServerHelloDone ─────────────────────     │
  │                                                 │
  │  [Client verifies certificate]                  │
  │                                                 │
  │  ─── ClientKeyExchange ──────────────────>     │
  │      Pre-master secret                          │
  │      (encrypted with server's public key)       │
  │                                                 │
  │  ─── ChangeCipherSpec ───────────────────>     │
  │  ─── Finished ───────────────────────────>     │
  │                                                 │
  │  <── ChangeCipherSpec ────────────────────     │
  │  <── Finished ────────────────────────────     │
  │                                                 │
  │  ═══════════ Encrypted Connection ═════════    │
```

### TLS 1.3 (Faster)

```
TLS 1.2: 2 round trips (2-RTT)
TLS 1.3: 1 round trip (1-RTT)
        0 round trip for resumed connections (0-RTT)
```

### Certificate Validation

```
Browser checks:
├── Certificate is not expired
├── Certificate is for the correct domain
├── Certificate is signed by trusted CA
├── Certificate is not revoked (OCSP/CRL)
└── Certificate chain is valid
```

---

## 5. HTTP Request

Once the connection is established, the client sends the HTTP request.

```http
GET /api/users?page=1 HTTP/1.1
Host: api.example.com
Accept: application/json
Accept-Language: en-US,en;q=0.9
Accept-Encoding: gzip, deflate, br
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)
Connection: keep-alive
Cookie: session=abc123
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...

[Empty line - end of headers]
[Request body - if POST/PUT/PATCH]
```

### Request Components

```
Request Line:
├── Method: GET
├── Path: /api/users?page=1
└── Version: HTTP/1.1

Headers:
├── Host: Required in HTTP/1.1
├── Accept: Expected response format
├── Authorization: Authentication
├── Content-Type: Body format (POST/PUT)
└── Cookie: Client cookies

Body (optional):
└── JSON, form data, file upload, etc.
```

---

## 6. Server Processing

The server receives and processes the request.

```
                        Server
                          │
┌─────────────────────────┴─────────────────────────┐
│                                                    │
│  ┌──────────────┐                                 │
│  │ Web Server   │  Nginx, Apache, IIS             │
│  │ (Reverse     │                                 │
│  │  Proxy)      │                                 │
│  └──────┬───────┘                                 │
│         │                                         │
│         ▼                                         │
│  ┌──────────────┐                                 │
│  │ Application  │  Node.js, Spring, Django        │
│  │ Server       │                                 │
│  └──────┬───────┘                                 │
│         │                                         │
│         ├─────────┬─────────┬─────────┐          │
│         ▼         ▼         ▼         ▼          │
│  ┌──────────┐ ┌──────┐ ┌──────┐ ┌──────────┐    │
│  │ Routing  │ │ Auth │ │ Logic│ │ Database │    │
│  └──────────┘ └──────┘ └──────┘ └──────────┘    │
│                                                    │
└────────────────────────────────────────────────────┘
```

### Processing Steps

```
1. Receive Request
   └── Parse HTTP request

2. Route Request
   └── Match URL to handler

3. Middleware Pipeline
   ├── Authentication
   ├── Authorization
   ├── Logging
   ├── Rate limiting
   └── CORS handling

4. Controller/Handler
   └── Business logic execution

5. Data Layer
   ├── Database queries
   ├── Cache lookup
   └── External API calls

6. Response Building
   ├── Serialize data (JSON)
   ├── Set headers
   └── Set status code
```

---

## 7. HTTP Response

Server sends back the response.

```http
HTTP/1.1 200 OK
Date: Wed, 21 Oct 2024 07:28:00 GMT
Server: nginx/1.18.0
Content-Type: application/json; charset=utf-8
Content-Length: 245
Cache-Control: max-age=3600
ETag: "abc123"
Set-Cookie: session=xyz789; Path=/; HttpOnly; Secure

{
    "users": [
        {"id": 1, "name": "John"},
        {"id": 2, "name": "Jane"}
    ],
    "page": 1,
    "total": 50
}
```

### Response Processing

```
Client receives response:
├── Parse status line
├── Parse headers
├── Decompress body (if gzip/br)
├── Parse body (JSON/HTML)
├── Store cookies
├── Update cache
└── Render/use data
```

---

## 8. Connection Close/Keep-Alive

### Connection Termination (4-way handshake)

```
Client                                           Server
  │                                                 │
  │  ─────────── FIN ─────────────────────────>    │
  │  "I'm done sending"                             │
  │                                                 │
  │  <────────── ACK ─────────────────────────     │
  │  "Acknowledged"                                 │
  │                                                 │
  │  <────────── FIN ─────────────────────────     │
  │  "I'm done sending too"                         │
  │                                                 │
  │  ─────────── ACK ─────────────────────────>    │
  │  "Acknowledged"                                 │
  │                                                 │
  │  ═══════════ Connection Closed ════════════    │
```

### Keep-Alive (HTTP/1.1 Default)

```
Without Keep-Alive:
Request 1: [TCP] [TLS] [Request] [Response] [Close]
Request 2: [TCP] [TLS] [Request] [Response] [Close]
Request 3: [TCP] [TLS] [Request] [Response] [Close]
           └────────── Overhead ──────────┘

With Keep-Alive:
Request 1: [TCP] [TLS] [Request] [Response]
Request 2:             [Request] [Response]
Request 3:             [Request] [Response]
                       └─ Reused Connection ─┘

HTTP/1.1 header:
Connection: keep-alive
Keep-Alive: timeout=5, max=100
```

---

## Timing Breakdown

### Developer Tools Network Timing

```
┌─────────────────────────────────────────────────────────────┐
│ Queued        │ Waiting in browser queue                    │
├───────────────┼─────────────────────────────────────────────┤
│ Stalled       │ Blocked (proxy, connection limit)           │
├───────────────┼─────────────────────────────────────────────┤
│ DNS Lookup    │ Time to resolve domain to IP               │
├───────────────┼─────────────────────────────────────────────┤
│ Initial       │ TCP + TLS handshake time                    │
│ Connection    │                                             │
├───────────────┼─────────────────────────────────────────────┤
│ Request Sent  │ Time to send HTTP request                  │
├───────────────┼─────────────────────────────────────────────┤
│ Waiting       │ Time To First Byte (TTFB)                  │
│ (TTFB)        │ Server processing time                      │
├───────────────┼─────────────────────────────────────────────┤
│ Content       │ Time to download response                  │
│ Download      │                                             │
└───────────────┴─────────────────────────────────────────────┘
```

### Typical Latencies

| Stage | Typical Time | Notes |
|-------|--------------|-------|
| DNS Lookup | 20-120ms | Cached: 0ms |
| TCP Connect | 20-50ms | Per location |
| TLS Handshake | 50-150ms | TLS 1.3 is faster |
| Request Send | 1-10ms | Depends on size |
| TTFB | 50-500ms | Server dependent |
| Download | Variable | Depends on size |

---

## Optimization Opportunities

```
1. DNS Optimization
   ├── DNS prefetching: <link rel="dns-prefetch" href="//api.example.com">
   └── Use CDN for lower latency

2. Connection Optimization
   ├── HTTP/2 multiplexing
   ├── Connection pooling
   └── Preconnect: <link rel="preconnect" href="https://api.example.com">

3. TLS Optimization
   ├── TLS 1.3 (1-RTT)
   ├── Session resumption
   └── OCSP stapling

4. Server Optimization
   ├── Caching (Redis, CDN)
   ├── Database optimization
   └── Async processing

5. Response Optimization
   ├── Compression (gzip, brotli)
   ├── Minification
   └── Image optimization
```

---

## Key Takeaways

1. **Multiple steps** - Each HTTP request involves many network operations
2. **DNS caching** - Reduces lookup time significantly
3. **Connection reuse** - Keep-alive reduces overhead
4. **TLS overhead** - HTTPS adds latency but is essential
5. **TTFB matters** - Server processing time is often the bottleneck
6. **HTTP/2 helps** - Multiplexing improves performance

---

## Next Topic

Continue to [HTTP Status Codes](./04-http-status-codes.md) to learn about response codes and their meanings.
