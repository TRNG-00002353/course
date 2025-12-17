# Exercise 01: Building an HTTP Server with Node.js

## Overview

In this exercise, you'll build a simple HTTP server using Node.js that demonstrates all HTTP methods, headers, cookies, and proper status codes. This hands-on practice will reinforce your understanding of HTTP concepts.

---

## Objectives

By completing this exercise, you will:
- Create an HTTP server from scratch using Node.js
- Handle all HTTP methods (GET, POST, PUT, PATCH, DELETE)
- Work with request and response headers
- Implement cookie management
- Return appropriate status codes
- Parse request bodies (JSON and URL-encoded)

---

## Prerequisites

- Node.js installed (v14+ recommended)
- Basic JavaScript knowledge
- Understanding of HTTP concepts from the module topics

---

## Setup

### 1. Create Project Directory

```bash
mkdir http-server-exercise
cd http-server-exercise
npm init -y
```

### 2. Create the Server File

Create a file named `server.js`.

---

## Part 1: Basic HTTP Server

### Task 1.1: Create a Simple Server

Create a basic HTTP server that responds to requests.

```javascript
// server.js
const http = require('http');

const PORT = 3000;

const server = http.createServer((req, res) => {
    console.log(`${req.method} ${req.url}`);

    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.end('Hello, HTTP!');
});

server.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});
```

### Test It

```bash
node server.js

# In another terminal:
curl http://localhost:3000
```

---

## Part 2: Handling HTTP Methods

### Task 2.1: Implement a REST-like API

Create an in-memory "database" and handle different HTTP methods.

```javascript
// server.js
const http = require('http');
const url = require('url');

const PORT = 3000;

// In-memory "database"
let users = [
    { id: 1, name: 'John Doe', email: 'john@example.com' },
    { id: 2, name: 'Jane Smith', email: 'jane@example.com' }
];
let nextId = 3;

// Helper: Parse JSON body
function parseBody(req) {
    return new Promise((resolve, reject) => {
        let body = '';
        req.on('data', chunk => body += chunk);
        req.on('end', () => {
            try {
                resolve(body ? JSON.parse(body) : {});
            } catch (e) {
                reject(e);
            }
        });
        req.on('error', reject);
    });
}

// Helper: Send JSON response
function sendJSON(res, statusCode, data) {
    res.writeHead(statusCode, {
        'Content-Type': 'application/json',
        'X-Powered-By': 'Node.js HTTP Exercise'
    });
    res.end(JSON.stringify(data, null, 2));
}

// Helper: Send error response
function sendError(res, statusCode, message) {
    sendJSON(res, statusCode, { error: message });
}

const server = http.createServer(async (req, res) => {
    const parsedUrl = url.parse(req.url, true);
    const path = parsedUrl.pathname;
    const query = parsedUrl.query;
    const method = req.method;

    console.log(`${method} ${path}`);

    // CORS headers (for browser testing)
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, PATCH, DELETE, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');

    // Handle preflight
    if (method === 'OPTIONS') {
        res.writeHead(204);
        return res.end();
    }

    try {
        // Route: GET /
        if (path === '/' && method === 'GET') {
            return sendJSON(res, 200, {
                message: 'Welcome to the HTTP Exercise Server!',
                endpoints: {
                    'GET /users': 'List all users',
                    'GET /users/:id': 'Get user by ID',
                    'POST /users': 'Create new user',
                    'PUT /users/:id': 'Replace user',
                    'PATCH /users/:id': 'Update user',
                    'DELETE /users/:id': 'Delete user'
                }
            });
        }

        // Route: GET /users
        if (path === '/users' && method === 'GET') {
            // TODO: Implement pagination using query params
            return sendJSON(res, 200, { users, count: users.length });
        }

        // Route: GET /users/:id
        const userMatch = path.match(/^\/users\/(\d+)$/);
        if (userMatch && method === 'GET') {
            const id = parseInt(userMatch[1]);
            const user = users.find(u => u.id === id);

            if (!user) {
                return sendError(res, 404, `User with ID ${id} not found`);
            }
            return sendJSON(res, 200, user);
        }

        // Route: POST /users
        if (path === '/users' && method === 'POST') {
            const body = await parseBody(req);

            // Validation
            if (!body.name || !body.email) {
                return sendError(res, 400, 'Name and email are required');
            }

            // Check duplicate email
            if (users.some(u => u.email === body.email)) {
                return sendError(res, 409, 'Email already exists');
            }

            const newUser = {
                id: nextId++,
                name: body.name,
                email: body.email
            };
            users.push(newUser);

            res.setHeader('Location', `/users/${newUser.id}`);
            return sendJSON(res, 201, newUser);
        }

        // Route: PUT /users/:id
        if (userMatch && method === 'PUT') {
            const id = parseInt(userMatch[1]);
            const body = await parseBody(req);

            // Validation
            if (!body.name || !body.email) {
                return sendError(res, 400, 'Name and email are required for PUT');
            }

            const index = users.findIndex(u => u.id === id);
            if (index === -1) {
                return sendError(res, 404, `User with ID ${id} not found`);
            }

            users[index] = { id, name: body.name, email: body.email };
            return sendJSON(res, 200, users[index]);
        }

        // Route: PATCH /users/:id
        if (userMatch && method === 'PATCH') {
            const id = parseInt(userMatch[1]);
            const body = await parseBody(req);

            const index = users.findIndex(u => u.id === id);
            if (index === -1) {
                return sendError(res, 404, `User with ID ${id} not found`);
            }

            // Partial update
            if (body.name) users[index].name = body.name;
            if (body.email) users[index].email = body.email;

            return sendJSON(res, 200, users[index]);
        }

        // Route: DELETE /users/:id
        if (userMatch && method === 'DELETE') {
            const id = parseInt(userMatch[1]);
            const index = users.findIndex(u => u.id === id);

            if (index === -1) {
                return sendError(res, 404, `User with ID ${id} not found`);
            }

            users.splice(index, 1);
            res.writeHead(204);
            return res.end();
        }

        // 404 for unknown routes
        sendError(res, 404, 'Not Found');

    } catch (error) {
        console.error('Server Error:', error);
        sendError(res, 500, 'Internal Server Error');
    }
});

server.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});
```

### Test the Methods

```bash
# GET all users
curl http://localhost:3000/users

# GET one user
curl http://localhost:3000/users/1

# POST - Create user
curl -X POST http://localhost:3000/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Bob Wilson", "email": "bob@example.com"}'

# PUT - Replace user
curl -X PUT http://localhost:3000/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "John Updated", "email": "john.updated@example.com"}'

# PATCH - Partial update
curl -X PATCH http://localhost:3000/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "John Patched"}'

# DELETE user
curl -X DELETE http://localhost:3000/users/1

# Test 404
curl http://localhost:3000/users/999

# Test validation error
curl -X POST http://localhost:3000/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Missing Email"}'
```

---

## Part 3: Working with Headers

### Task 3.1: Add Custom Headers

Extend the server to demonstrate various headers.

```javascript
// Add these routes to your server

// Route: GET /headers - Echo request headers
if (path === '/headers' && method === 'GET') {
    const requestHeaders = {
        'user-agent': req.headers['user-agent'],
        'accept': req.headers['accept'],
        'accept-language': req.headers['accept-language'],
        'authorization': req.headers['authorization'],
        'all-headers': req.headers
    };

    res.setHeader('X-Custom-Header', 'Hello from server!');
    res.setHeader('X-Request-Id', Date.now().toString(36));

    return sendJSON(res, 200, {
        message: 'Request headers received',
        headers: requestHeaders
    });
}

// Route: GET /protected - Requires Authorization
if (path === '/protected' && method === 'GET') {
    const auth = req.headers['authorization'];

    if (!auth) {
        res.setHeader('WWW-Authenticate', 'Bearer realm="api"');
        return sendError(res, 401, 'Authorization required');
    }

    if (!auth.startsWith('Bearer ')) {
        return sendError(res, 401, 'Invalid authorization format');
    }

    const token = auth.substring(7);
    if (token !== 'valid-token-123') {
        return sendError(res, 403, 'Invalid token');
    }

    return sendJSON(res, 200, {
        message: 'Access granted!',
        user: { name: 'Authenticated User' }
    });
}

// Route: GET /download - File download headers
if (path === '/download' && method === 'GET') {
    const data = JSON.stringify(users, null, 2);

    res.writeHead(200, {
        'Content-Type': 'application/json',
        'Content-Disposition': 'attachment; filename="users.json"',
        'Content-Length': Buffer.byteLength(data)
    });
    return res.end(data);
}

// Route: GET /cache - Demonstrate caching headers
if (path === '/cache' && method === 'GET') {
    const etag = `"${users.length}-${Date.now()}"`;

    // Check if client has cached version
    if (req.headers['if-none-match'] === etag) {
        res.writeHead(304);
        return res.end();
    }

    res.writeHead(200, {
        'Content-Type': 'application/json',
        'Cache-Control': 'public, max-age=60',
        'ETag': etag,
        'Last-Modified': new Date().toUTCString()
    });
    return res.end(JSON.stringify({ data: 'This response can be cached for 60 seconds' }));
}
```

### Test Headers

```bash
# View headers
curl -v http://localhost:3000/headers

# Test with custom headers
curl http://localhost:3000/headers \
  -H "Accept: application/json" \
  -H "Accept-Language: en-US"

# Test protected route without auth
curl http://localhost:3000/protected

# Test protected route with auth
curl http://localhost:3000/protected \
  -H "Authorization: Bearer valid-token-123"

# Download file
curl -O http://localhost:3000/download

# Test caching
curl -v http://localhost:3000/cache
curl -v http://localhost:3000/cache -H 'If-None-Match: "2-xxx"'
```

---

## Part 4: Cookie Management

### Task 4.1: Implement Sessions with Cookies

Add cookie-based session management.

```javascript
// Add at the top of your file
const sessions = new Map();

// Helper: Parse cookies from request
function parseCookies(req) {
    const cookies = {};
    const cookieHeader = req.headers.cookie;

    if (cookieHeader) {
        cookieHeader.split(';').forEach(cookie => {
            const [name, value] = cookie.trim().split('=');
            cookies[name] = decodeURIComponent(value);
        });
    }
    return cookies;
}

// Helper: Generate session ID
function generateSessionId() {
    return Math.random().toString(36).substring(2) + Date.now().toString(36);
}

// Add these routes

// Route: POST /login - Create session
if (path === '/login' && method === 'POST') {
    const body = await parseBody(req);

    // Simple validation (in real app, check against database)
    if (body.username === 'admin' && body.password === 'password') {
        const sessionId = generateSessionId();
        sessions.set(sessionId, {
            username: body.username,
            createdAt: new Date(),
            data: {}
        });

        // Set session cookie
        res.setHeader('Set-Cookie', [
            `sessionId=${sessionId}; Path=/; HttpOnly; Max-Age=3600`,
            `username=${body.username}; Path=/; Max-Age=3600`
        ]);

        return sendJSON(res, 200, {
            message: 'Login successful',
            username: body.username
        });
    }

    return sendError(res, 401, 'Invalid credentials');
}

// Route: GET /session - Check session
if (path === '/session' && method === 'GET') {
    const cookies = parseCookies(req);
    const sessionId = cookies.sessionId;

    if (!sessionId || !sessions.has(sessionId)) {
        return sendError(res, 401, 'No valid session');
    }

    const session = sessions.get(sessionId);
    return sendJSON(res, 200, {
        message: 'Session active',
        username: session.username,
        createdAt: session.createdAt
    });
}

// Route: POST /logout - Destroy session
if (path === '/logout' && method === 'POST') {
    const cookies = parseCookies(req);
    const sessionId = cookies.sessionId;

    if (sessionId) {
        sessions.delete(sessionId);
    }

    // Clear cookies by setting expired date
    res.setHeader('Set-Cookie', [
        'sessionId=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT',
        'username=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT'
    ]);

    return sendJSON(res, 200, { message: 'Logged out' });
}

// Route: GET /profile - Protected by session
if (path === '/profile' && method === 'GET') {
    const cookies = parseCookies(req);
    const sessionId = cookies.sessionId;

    if (!sessionId || !sessions.has(sessionId)) {
        return sendError(res, 401, 'Please login first');
    }

    const session = sessions.get(sessionId);
    return sendJSON(res, 200, {
        username: session.username,
        sessionAge: Math.floor((Date.now() - session.createdAt.getTime()) / 1000) + ' seconds'
    });
}

// Route: POST /preferences - Store in session
if (path === '/preferences' && method === 'POST') {
    const cookies = parseCookies(req);
    const sessionId = cookies.sessionId;

    if (!sessionId || !sessions.has(sessionId)) {
        return sendError(res, 401, 'Please login first');
    }

    const body = await parseBody(req);
    const session = sessions.get(sessionId);
    session.data = { ...session.data, ...body };

    // Also set a preference cookie (readable by JavaScript)
    if (body.theme) {
        res.setHeader('Set-Cookie', `theme=${body.theme}; Path=/; Max-Age=31536000`);
    }

    return sendJSON(res, 200, {
        message: 'Preferences saved',
        preferences: session.data
    });
}
```

### Test Cookies

```bash
# Login (save cookies to file)
curl -c cookies.txt -X POST http://localhost:3000/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}'

# Check session (use saved cookies)
curl -b cookies.txt http://localhost:3000/session

# Access profile
curl -b cookies.txt http://localhost:3000/profile

# Set preferences
curl -b cookies.txt -c cookies.txt -X POST http://localhost:3000/preferences \
  -H "Content-Type: application/json" \
  -d '{"theme": "dark", "language": "en"}'

# Logout
curl -b cookies.txt -c cookies.txt -X POST http://localhost:3000/logout

# Try to access profile after logout (should fail)
curl -b cookies.txt http://localhost:3000/profile
```

---

## Part 5: Complete Server Code

Here's the complete server combining all parts:

Create a file named `complete-server.js` with all the functionality combined.

---

## Challenges

### Challenge 1: Add Pagination

Modify the `GET /users` endpoint to support pagination:

```
GET /users?page=1&limit=10
```

Return pagination metadata:
```json
{
    "users": [...],
    "pagination": {
        "page": 1,
        "limit": 10,
        "total": 50,
        "totalPages": 5
    }
}
```

### Challenge 2: Add Search

Implement search functionality:

```
GET /users?search=john
```

### Challenge 3: Rate Limiting

Implement simple rate limiting:
- Track requests per IP
- Return 429 if more than 10 requests per minute
- Include `Retry-After` header

### Challenge 4: Request Logging Middleware

Add middleware that logs:
- Timestamp
- Method and URL
- Response status code
- Response time

### Challenge 5: Content Negotiation

Make the server respond with different formats:
- JSON (default)
- XML (if `Accept: application/xml`)
- HTML (if `Accept: text/html`)

---

## Testing with Browser

Create an HTML file to test the API from a browser:

```html
<!-- test.html -->
<!DOCTYPE html>
<html>
<head>
    <title>HTTP Server Test</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        button { margin: 5px; padding: 10px; }
        pre { background: #f4f4f4; padding: 10px; }
        .section { margin: 20px 0; padding: 15px; border: 1px solid #ddd; }
    </style>
</head>
<body>
    <h1>HTTP Server Test Client</h1>

    <div class="section">
        <h2>Users API</h2>
        <button onclick="getUsers()">GET Users</button>
        <button onclick="createUser()">POST User</button>
        <button onclick="updateUser()">PUT User</button>
        <button onclick="patchUser()">PATCH User</button>
        <button onclick="deleteUser()">DELETE User</button>
    </div>

    <div class="section">
        <h2>Session</h2>
        <button onclick="login()">Login</button>
        <button onclick="checkSession()">Check Session</button>
        <button onclick="getProfile()">Get Profile</button>
        <button onclick="logout()">Logout</button>
    </div>

    <div class="section">
        <h2>Response</h2>
        <pre id="response">Response will appear here...</pre>
    </div>

    <script>
        const API = 'http://localhost:3000';

        async function apiCall(method, path, body = null) {
            try {
                const options = {
                    method,
                    headers: { 'Content-Type': 'application/json' },
                    credentials: 'include'  // Include cookies
                };
                if (body) options.body = JSON.stringify(body);

                const res = await fetch(API + path, options);
                const data = await res.json();

                document.getElementById('response').textContent =
                    `Status: ${res.status}\n\n${JSON.stringify(data, null, 2)}`;
            } catch (err) {
                document.getElementById('response').textContent = 'Error: ' + err.message;
            }
        }

        // Users API
        function getUsers() { apiCall('GET', '/users'); }
        function createUser() {
            const name = prompt('Name:');
            const email = prompt('Email:');
            apiCall('POST', '/users', { name, email });
        }
        function updateUser() {
            const id = prompt('User ID:');
            const name = prompt('Name:');
            const email = prompt('Email:');
            apiCall('PUT', `/users/${id}`, { name, email });
        }
        function patchUser() {
            const id = prompt('User ID:');
            const name = prompt('Name (leave empty to skip):');
            apiCall('PATCH', `/users/${id}`, { name });
        }
        function deleteUser() {
            const id = prompt('User ID:');
            apiCall('DELETE', `/users/${id}`);
        }

        // Session
        function login() {
            apiCall('POST', '/login', { username: 'admin', password: 'password' });
        }
        function checkSession() { apiCall('GET', '/session'); }
        function getProfile() { apiCall('GET', '/profile'); }
        function logout() { apiCall('POST', '/logout'); }
    </script>
</body>
</html>
```

---

## Verification Checklist

After completing this exercise, verify you can:

- [ ] Start the server and access it via curl/browser
- [ ] Perform all CRUD operations on users
- [ ] See correct status codes (200, 201, 204, 400, 401, 404, 409)
- [ ] View and set custom headers
- [ ] Login and receive session cookie
- [ ] Access protected routes with valid session
- [ ] Logout and clear cookies
- [ ] Handle invalid requests gracefully

---

## Summary

This exercise demonstrated:
- Building an HTTP server from scratch
- Handling all HTTP methods
- Working with headers (custom, cache, auth)
- Cookie-based session management
- Proper status code usage
- Error handling

These concepts form the foundation for understanding web frameworks like Express.js and building RESTful APIs.
