# Reverse Engineering for Full Stack Web Development

## What is Reverse Engineering?

Reverse engineering is the process of analyzing a system to understand how it works without having access to its original source code or documentation. In software development, it involves examining compiled code, network traffic, and application behavior to uncover the underlying design, architecture, and functionality.

```
Forward Engineering:           Reverse Engineering:
Requirements → Design → Code   Application → Analysis → Understanding
     ↓            ↓       ↓         ↓            ↓            ↓
   (Known)     (Known)  (Known)  (Known)     (Process)    (Discovered)
```

### Definition in Web Development Context

In full stack web development, reverse engineering means:
- **Analyzing how web applications communicate** between frontend and backend
- **Understanding API structures** by observing network traffic
- **Examining compiled/minified code** to understand application logic
- **Tracing data flow** from user interface through server to database

---

## Purpose of Reverse Engineering

### Why Learn Reverse Engineering?

| Purpose | Description |
|---------|-------------|
| **Learning** | Study how production applications implement features, patterns, and best practices |
| **Debugging** | Trace issues in complex applications where documentation is lacking |
| **Integration** | Understand third-party APIs or services for integration purposes |
| **Migration** | Analyze legacy systems before modernization or rewriting |
| **Security Testing** | Identify vulnerabilities in your own applications |
| **Troubleshooting** | Diagnose issues when source code isn't immediately available |

### Real-World Scenarios

**1. Learning from Existing Applications**
```
Scenario: You want to understand how a popular website implements infinite scrolling
Process:  Open DevTools → Network tab → Scroll → Observe API calls → Analyze pagination
Outcome:  Learn the pattern to implement in your own project
```

**2. Debugging Third-Party Integrations**
```
Scenario: Payment gateway integration is failing silently
Process:  Capture network requests → Analyze request/response → Identify missing headers
Outcome:  Fix the integration issue
```

**3. Understanding Legacy Code**
```
Scenario: Inherited a project with no documentation
Process:  Trace API endpoints → Map data flow → Document architecture
Outcome:  Create documentation for future maintenance
```

**4. API Discovery**
```
Scenario: Need to integrate with a service that has poor documentation
Process:  Use the service manually → Capture API calls → Document endpoints
Outcome:  Build integration based on discovered API structure
```

### Skills Developed

| Skill | Benefit |
|-------|---------|
| **Analytical Thinking** | Breaking down complex systems into understandable parts |
| **Debugging Proficiency** | Finding root causes faster |
| **Tool Mastery** | Proficiency with DevTools, network analyzers, decompilers |
| **Security Awareness** | Understanding how applications can be analyzed |
| **Documentation** | Creating technical documentation from analysis |
| **Architecture Understanding** | Recognizing patterns across different applications |

### How It Helps Full Stack Developers

```
┌─────────────────────────────────────────────────────────────────┐
│                    FULL STACK DEVELOPER                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  Frontend Skills              Backend Skills                     │
│  ┌─────────────────┐         ┌─────────────────┐                │
│  │ • Debug UI      │         │ • Trace API     │                │
│  │ • Analyze JS    │         │ • Read bytecode │                │
│  │ • Trace state   │         │ • Debug security│                │
│  └────────┬────────┘         └────────┬────────┘                │
│           │                           │                          │
│           └───────────┬───────────────┘                          │
│                       │                                          │
│                       ▼                                          │
│           ┌─────────────────────┐                                │
│           │ REVERSE ENGINEERING │                                │
│           │   • Understand APIs │                                │
│           │   • Debug full flow │                                │
│           │   • Learn patterns  │                                │
│           └─────────────────────┘                                │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

---

## Table of Contents

### Introduction
- [What is Reverse Engineering?](#what-is-reverse-engineering)
- [Purpose of Reverse Engineering](#purpose-of-reverse-engineering)

### Part 1: Core Concepts (Prerequisites)
- [Goal](#goal)
- [Learning Objectives](#learning-objectives)
- [Browser Developer Tools](#1-browser-developer-tools)
- [HTTP Fundamentals](#2-http-fundamentals)
- [API Discovery & Analysis](#3-api-discovery--analysis)
- [Authentication Concepts](#4-authentication-concepts)
- [API Testing with Postman/curl](#5-api-testing-with-postmancurl)
- [Reading Minified JavaScript](#6-reading-minified-javascript)
- [Tools Summary (Part 1)](#7-tools-summary-part-1)
- [Hands-On Exercises (Part 1)](#8-hands-on-exercises-part-1)

### Part 2: Angular & Spring Specific (After Framework Training)
- [Angular Application Analysis](#1-angular-application-analysis)
- [Spring Boot Application Analysis](#2-spring-boot-application-analysis)
- [Java Decompilation](#3-java-decompilation)
- [Full Stack Data Flow](#4-full-stack-data-flow)
- [Spring Security Analysis](#5-spring-security-analysis)
- [Spring Actuator Endpoints](#6-spring-actuator-endpoints)
- [Tools Summary (Part 2)](#7-tools-summary-part-2)
- [Hands-On Exercises (Part 2)](#8-hands-on-exercises-part-2)

---

# Part 1: Core Concepts (Prerequisites)

*Learn these fundamentals before diving into Angular and Spring*

---

## Goal

Master browser developer tools, HTTP analysis, and API exploration techniques that apply to any web application.

## Learning Objectives

| Objective | Description |
|-----------|-------------|
| Master DevTools | Navigate and use browser developer tools effectively |
| Analyze HTTP traffic | Understand request/response lifecycle |
| Discover APIs | Find and document API endpoints |
| Understand authentication | Learn JWT, sessions, cookies concepts |
| Test APIs | Use tools like Postman to interact with APIs |

---

## 1. Browser Developer Tools

### Opening DevTools

- Chrome/Edge: `F12` or `Ctrl+Shift+I` (Windows) / `Cmd+Option+I` (Mac)
- Firefox: `F12` or `Ctrl+Shift+I`

### Key Panels

| Panel | Purpose | What to Analyze |
|-------|---------|-----------------|
| Elements | DOM & CSS inspection | HTML structure, styling |
| Console | JavaScript execution & logs | Errors, debug output |
| Network | HTTP traffic monitoring | API calls, resources |
| Sources | JavaScript debugging | Breakpoints, code flow |
| Application | Storage inspection | Cookies, localStorage, sessionStorage |

### Network Panel Deep Dive

```
┌─────────────────────────────────────────────────────────────┐
│ Network Panel                                                │
├──────────┬──────────┬────────┬─────────┬───────────────────┤
│ Name     │ Status   │ Type   │ Size    │ Time              │
├──────────┼──────────┼────────┼─────────┼───────────────────┤
│ users    │ 200      │ xhr    │ 1.2 KB  │ 45 ms             │
│ login    │ 200      │ xhr    │ 256 B   │ 120 ms            │
│ products │ 404      │ xhr    │ 89 B    │ 23 ms             │
└──────────┴──────────┴────────┴─────────┴───────────────────┘

Click on any request to see:
- Headers (request & response)
- Payload (request body)
- Preview (formatted response)
- Response (raw response)
- Timing (performance breakdown)
```

### Filtering Network Requests

| Filter | Shows |
|--------|-------|
| `XHR` | AJAX/API calls only |
| `JS` | JavaScript files |
| `CSS` | Stylesheets |
| `Img` | Images |
| `WS` | WebSocket connections |
| `/api` | URLs containing "/api" |

---

## 2. HTTP Fundamentals

### Request Structure

```http
POST /api/users HTTP/1.1
Host: example.com
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "name": "John Doe",
  "email": "john@example.com"
}
```

### Response Structure

```http
HTTP/1.1 201 Created
Content-Type: application/json
Set-Cookie: sessionId=abc123; HttpOnly

{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com"
}
```

### HTTP Methods

| Method | Purpose | Has Body |
|--------|---------|----------|
| GET | Retrieve data | No |
| POST | Create resource | Yes |
| PUT | Replace resource | Yes |
| PATCH | Partial update | Yes |
| DELETE | Remove resource | Optional |

### Common Status Codes

| Code | Meaning | Common Cause |
|------|---------|--------------|
| 200 | OK | Successful request |
| 201 | Created | Resource created |
| 400 | Bad Request | Invalid input |
| 401 | Unauthorized | Missing/invalid auth |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Backend exception |

---

## 3. API Discovery & Analysis

### Steps to Discover APIs

```
1. Open DevTools → Network tab
        ↓
2. Check "Preserve log" (keeps logs across page loads)
        ↓
3. Filter by "XHR" or "Fetch"
        ↓
4. Interact with the application
        ↓
5. Observe API calls appearing
        ↓
6. Click each call to inspect details
```

### Documenting Discovered APIs

| Endpoint | Method | Request Body | Response | Auth |
|----------|--------|--------------|----------|------|
| /api/users | GET | - | User[] | Bearer |
| /api/users/{id} | GET | - | User | Bearer |
| /api/users | POST | CreateUserDTO | User | Bearer |
| /api/auth/login | POST | Credentials | Token | None |

### Analyzing Request Headers

```
Common headers to look for:

Authorization: Bearer <token>     → JWT authentication
Content-Type: application/json    → JSON request body
Accept: application/json          → Expected response format
X-CSRF-Token: abc123             → CSRF protection
Cookie: sessionId=xyz            → Session-based auth
```

---

## 4. Authentication Concepts

### JWT (JSON Web Token)

```
Header.Payload.Signature

eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsInJvbGUiOiJBRE1JTiJ9.signature
└────────┬────────┘ └──────────────────┬──────────────────┘ └────┬────┘
      Header                       Payload                   Signature
```

### Decoding JWT (use jwt.io)

```json
// Header
{
  "alg": "HS256",
  "typ": "JWT"
}

// Payload
{
  "sub": "user1",
  "role": "ADMIN",
  "exp": 1699999999,
  "iat": 1699990000
}
```

### Authentication Types

| Type | Storage | Sent Via |
|------|---------|----------|
| JWT | localStorage/memory | Authorization header |
| Session | Server-side | Cookie |
| API Key | Config | Header or query param |
| OAuth | Token storage | Authorization header |

### Session vs JWT

```
Session-Based:
┌────────┐  login   ┌────────┐
│ Client │ ───────► │ Server │ → Creates session, stores in DB
└────────┘          └────────┘
     ▲                   │
     └── Cookie: sessionId=abc ──┘

JWT-Based:
┌────────┐  login   ┌────────┐
│ Client │ ───────► │ Server │ → Creates token, returns to client
└────────┘          └────────┘
     │
     └── Stores token locally, sends in header each request
```

---

## 5. API Testing with Postman/curl

### Recreating Requests in Postman

1. Right-click request in Network tab
2. Select "Copy as cURL"
3. Import into Postman (Import → Raw Text)

### curl Examples

```bash
# GET request
curl -X GET "http://localhost:8080/api/users" \
  -H "Authorization: Bearer <token>"

# POST request with JSON body
curl -X POST "http://localhost:8080/api/users" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"name": "John", "email": "john@example.com"}'

# PUT request
curl -X PUT "http://localhost:8080/api/users/1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"name": "John Updated", "email": "john.updated@example.com"}'

# DELETE request
curl -X DELETE "http://localhost:8080/api/users/1" \
  -H "Authorization: Bearer <token>"
```

---

## 6. Reading Minified JavaScript

### Before (minified)

```javascript
function a(b){return b.map(c=>({id:c.id,name:c.n,email:c.e}))}
```

### After (beautified)

```javascript
function a(b) {
  return b.map(c => ({
    id: c.id,
    name: c.n,
    email: c.e
  }))
}
```

### Tools for Beautifying

- Browser DevTools: Click `{}` button in Sources panel
- Online: prettier.io, beautifier.io
- VS Code: Format Document

### Tips for Reading Minified Code

1. **Look for patterns**: API URLs often remain readable (`/api/users`)
2. **Search for strings**: Use Ctrl+F to find keywords
3. **Follow the data**: Trace variable assignments
4. **Check network calls**: Match code to observed API calls

---

## 7. Tools Summary (Part 1)

| Tool | Purpose | How to Access |
|------|---------|---------------|
| Chrome DevTools | All-in-one analysis | F12 |
| Firefox DevTools | Alternative browser tools | F12 |
| Postman | API testing | Desktop app / Web |
| curl | CLI HTTP requests | Terminal |
| JWT.io | Decode JWT tokens | Web |
| Prettier | Beautify code | Web / Extension |
| Wappalyzer | Detect technologies | Browser extension |
| JSON Viewer | Format JSON responses | Browser extension |

---

## 8. Hands-On Exercises (Part 1)

| # | Exercise | Skills Practiced |
|---|----------|------------------|
| 1 | Open any website, find all API calls in Network tab | DevTools, Network analysis |
| 2 | Copy an API request as cURL and run it in terminal | curl, API recreation |
| 3 | Find where a website stores authentication (cookies/localStorage) | Application tab, Auth concepts |
| 4 | Decode a JWT token from a website using jwt.io | JWT understanding |
| 5 | Beautify minified JavaScript and identify functions | Code analysis |
| 6 | Document 5 API endpoints from a web application | API discovery |

---

# Part 2: Angular & Spring Specific (After Framework Training)

*Learn these after completing Angular and Spring Boot modules*

---

## Goal

Apply reverse engineering techniques specifically to Angular frontend and Spring Boot backend applications.

## Learning Objectives

| Objective | Description |
|-----------|-------------|
| Analyze Angular bundles | Understand compiled components, services, modules |
| Inspect Spring structure | Read controllers, services, repositories |
| Trace full stack flow | Follow data from Angular through Spring to database |
| Debug Spring Security | Understand authentication filters and configuration |
| Decompile Java code | Read JAR files and bytecode |

---

## 1. Angular Application Analysis

### Compiled Angular Structure

```
dist/your-app/
├── index.html           # Entry point
├── main.js              # Application code (components, services)
├── polyfills.js         # Browser compatibility
├── runtime.js           # Webpack runtime
├── styles.css           # Compiled styles
├── vendor.js            # Third-party libraries (if not in main)
└── assets/              # Static files
```

### Angular DevTools

- Install from Chrome Web Store
- Inspect component tree
- View component inputs/outputs
- Profile change detection
- Analyze dependency injection

### Identifying Angular Patterns in Code

```typescript
// Service pattern - look for HttpClient calls
@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = '/api/users';

  constructor(private http: HttpClient) {}

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl);
  }

  getById(id: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/${id}`);
  }

  create(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl, user);
  }

  update(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/${id}`, user);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
```

```typescript
// Component pattern - look for decorators
@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {
  users$: Observable<User[]>;

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.users$ = this.userService.getAll();
  }
}
```

### HTTP Interceptor Analysis

```typescript
// Look for interceptors to understand:
// - How auth tokens are added
// - Error handling patterns
// - Loading indicators

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.authService.logout();
        }
        return throwError(() => error);
      })
    );
  }
}
```

### Angular Routing Analysis

```typescript
// Look for route definitions
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'users', component: UserListComponent, canActivate: [AuthGuard] },
  { path: 'users/:id', component: UserDetailComponent },
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) },
  { path: '**', component: NotFoundComponent }
];
```

---

## 2. Spring Boot Application Analysis

### Spring Project Structure

```
src/main/java/com/example/
├── controller/          # REST endpoints
│   └── UserController.java
├── service/             # Business logic
│   ├── UserService.java
│   └── UserServiceImpl.java
├── repository/          # Data access
│   └── UserRepository.java
├── model/               # JPA entities
│   └── User.java
├── dto/                 # Data Transfer Objects
│   ├── UserDTO.java
│   ├── CreateUserRequest.java
│   └── UpdateUserRequest.java
├── config/              # Configuration classes
│   ├── SecurityConfig.java
│   └── CorsConfig.java
├── exception/           # Custom exceptions
│   └── ResourceNotFoundException.java
└── Application.java     # Main class
```

### Reading REST Controllers

```java
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping                              // GET /api/users
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")                     // GET /api/users/{id}
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping                             // POST /api/users
    public ResponseEntity<UserDTO> create(@RequestBody @Valid CreateUserRequest request) {
        UserDTO created = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")                     // PUT /api/users/{id}
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")                  // DELETE /api/users/{id}
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")                   // GET /api/users/search?name=John
    public ResponseEntity<List<UserDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(userService.searchByName(name));
    }
}
```

### Understanding Spring Annotations

| Annotation | Purpose |
|------------|---------|
| `@RestController` | REST API controller (combines @Controller + @ResponseBody) |
| `@RequestMapping` | Base URL path |
| `@GetMapping` | Handle GET requests |
| `@PostMapping` | Handle POST requests |
| `@PutMapping` | Handle PUT requests |
| `@DeleteMapping` | Handle DELETE requests |
| `@PathVariable` | Extract value from URL path |
| `@RequestBody` | Parse JSON request body |
| `@RequestParam` | Query parameters |
| `@Autowired` | Dependency injection |
| `@Valid` | Trigger validation |
| `@CrossOrigin` | CORS configuration |

### Analyzing Service Layer

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
            .stream()
            .map(user -> modelMapper.map(user, UserDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO create(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserDTO.class);
    }
}
```

### Analyzing Repository Layer

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Derived query methods
    List<User> findByName(String name);
    List<User> findByNameContainingIgnoreCase(String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Custom JPQL query
    @Query("SELECT u FROM User u WHERE u.status = :status")
    List<User> findByStatus(@Param("status") String status);

    // Native SQL query
    @Query(value = "SELECT * FROM users WHERE created_at > :date", nativeQuery = true)
    List<User> findRecentUsers(@Param("date") LocalDate date);
}
```

---

## 3. Java Decompilation

### Extracting JAR Contents

```bash
# List JAR contents
jar -tf application.jar

# Extract JAR
jar -xf application.jar

# View specific class
jar -xf application.jar BOOT-INF/classes/com/example/controller/UserController.class
```

### Spring Boot JAR Structure

```
application.jar
├── BOOT-INF/
│   ├── classes/           # Your compiled code
│   │   └── com/example/
│   │       ├── controller/
│   │       ├── service/
│   │       └── ...
│   └── lib/               # Dependencies
│       ├── spring-boot-*.jar
│       ├── spring-web-*.jar
│       └── ...
├── META-INF/
│   └── MANIFEST.MF
└── org/springframework/boot/loader/   # Spring Boot loader
```

### Using javap (Command Line)

```bash
# Show public methods and fields
javap com.example.controller.UserController

# Show bytecode
javap -c com.example.controller.UserController

# Show private members
javap -p com.example.controller.UserController

# Show line numbers and local variables
javap -l com.example.controller.UserController
```

### Decompiler Tools

| Tool | Description | Usage |
|------|-------------|-------|
| JD-GUI | Standalone GUI decompiler | Open JAR file directly |
| IntelliJ IDEA | Built-in decompiler | Open .class file in IDE |
| CFR | Modern CLI decompiler | `java -jar cfr.jar MyClass.class` |
| Fernflower | IntelliJ's engine | Available standalone |

### Example: Decompiled Output

```java
// Original source (unavailable)
// Decompiled output from JD-GUI

package com.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/users"})
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }
}
```

---

## 4. Full Stack Data Flow

### Tracing Request: Create User

```
┌─────────────────────────────────────────────────────────────────┐
│ ANGULAR                                                          │
│                                                                  │
│  UserFormComponent              UserService                      │
│  ┌──────────────────┐          ┌─────────────────────────┐      │
│  │ onSubmit() {     │          │ create(dto) {           │      │
│  │   this.userSvc   │ ───────► │   return this.http      │      │
│  │     .create(dto) │          │     .post('/api/users', │      │
│  │     .subscribe() │          │            dto);        │      │
│  │ }                │          │ }                       │      │
│  └──────────────────┘          └────────────┬────────────┘      │
└─────────────────────────────────────────────┼────────────────────┘
                                              │
                    HTTP POST /api/users      │
                    Content-Type: application/json
                    Authorization: Bearer eyJ...
                    { "name": "John", "email": "john@example.com" }
                                              │
                                              ▼
┌─────────────────────────────────────────────────────────────────┐
│ SPRING BOOT                                                      │
│                                                                  │
│  UserController           UserService           UserRepository   │
│  ┌─────────────────┐     ┌──────────────────┐  ┌─────────────┐  │
│  │ @PostMapping    │     │ create(dto) {    │  │ save(user)  │  │
│  │ create(dto) {   │────►│   User user =    │─►│             │  │
│  │   return svc    │     │     new User();  │  │  JPA /      │  │
│  │    .create(dto);│     │   return repo    │  │  Hibernate  │  │
│  │ }               │     │    .save(user);  │  │             │  │
│  └─────────────────┘     └──────────────────┘  └──────┬──────┘  │
└─────────────────────────────────────────────────────────┼────────┘
                                                          │
                                                          ▼
                                                  ┌───────────────┐
                                                  │   DATABASE    │
                                                  │ INSERT INTO   │
                                                  │ users (...)   │
                                                  └───────────────┘
```

### Complete Flow: Login Authentication

```
┌───────────────────────────────────────────────────────────────────┐
│ 1. ANGULAR: User submits login form                               │
├───────────────────────────────────────────────────────────────────┤
│ LoginComponent                    AuthService                      │
│ ┌────────────────────┐           ┌────────────────────────────┐   │
│ │ onLogin() {        │           │ login(credentials) {       │   │
│ │   this.authService │──────────►│   return this.http.post(   │   │
│ │     .login(creds)  │           │     '/api/auth/login',     │   │
│ │     .subscribe()   │           │     credentials            │   │
│ │ }                  │           │   );                       │   │
│ └────────────────────┘           └─────────────┬──────────────┘   │
└────────────────────────────────────────────────┼───────────────────┘
                                                 │
                    POST /api/auth/login         │
                    { "username": "john", "password": "secret" }
                                                 │
                                                 ▼
┌───────────────────────────────────────────────────────────────────┐
│ 2. SPRING: Validate credentials and generate JWT                  │
├───────────────────────────────────────────────────────────────────┤
│ AuthController              AuthService              JwtUtil      │
│ ┌─────────────────┐        ┌─────────────────┐     ┌───────────┐ │
│ │ @PostMapping    │        │ authenticate()  │     │ generate  │ │
│ │ login(creds) {  │───────►│ validate user   │────►│ Token()   │ │
│ │   return token  │        │ return token    │     │           │ │
│ │ }               │        │                 │     │           │ │
│ └─────────────────┘        └─────────────────┘     └───────────┘ │
└───────────────────────────────────────────────────────────────────┘
                                                 │
                    Response: { "token": "eyJhbGciOiJ..." }
                                                 │
                                                 ▼
┌───────────────────────────────────────────────────────────────────┐
│ 3. ANGULAR: Store token and redirect                              │
├───────────────────────────────────────────────────────────────────┤
│ AuthService                                                       │
│ ┌─────────────────────────────────────────────────────────────┐  │
│ │ // In subscribe callback                                     │  │
│ │ localStorage.setItem('token', response.token);               │  │
│ │ this.router.navigate(['/dashboard']);                        │  │
│ └─────────────────────────────────────────────────────────────┘  │
└───────────────────────────────────────────────────────────────────┘
                                                 │
                                                 ▼
┌───────────────────────────────────────────────────────────────────┐
│ 4. ANGULAR: Subsequent requests include token (via Interceptor)   │
├───────────────────────────────────────────────────────────────────┤
│ AuthInterceptor                                                   │
│ ┌─────────────────────────────────────────────────────────────┐  │
│ │ const token = localStorage.getItem('token');                 │  │
│ │ req = req.clone({                                            │  │
│ │   setHeaders: { Authorization: `Bearer ${token}` }           │  │
│ │ });                                                          │  │
│ └─────────────────────────────────────────────────────────────┘  │
└───────────────────────────────────────────────────────────────────┘
                                                 │
                    GET /api/users               │
                    Authorization: Bearer eyJhbGciOiJ...
                                                 │
                                                 ▼
┌───────────────────────────────────────────────────────────────────┐
│ 5. SPRING: Validate token in Security Filter                      │
├───────────────────────────────────────────────────────────────────┤
│ JwtAuthenticationFilter                                           │
│ ┌─────────────────────────────────────────────────────────────┐  │
│ │ String header = request.getHeader("Authorization");          │  │
│ │ String token = header.substring(7); // Remove "Bearer "      │  │
│ │ if (jwtUtil.validateToken(token)) {                          │  │
│ │   // Set authentication context                              │  │
│ │   SecurityContextHolder.getContext().setAuthentication(auth);│  │
│ │ }                                                            │  │
│ └─────────────────────────────────────────────────────────────┘  │
└───────────────────────────────────────────────────────────────────┘
```

---

## 5. Spring Security Analysis

### Security Filter Chain

```
HTTP Request
      │
      ▼
┌─────────────────────────────────────────────────────┐
│              Security Filter Chain                   │
├─────────────────────────────────────────────────────┤
│  ┌─────────────────┐                                │
│  │ CORS Filter     │ → Check origin, methods        │
│  └────────┬────────┘                                │
│           ▼                                          │
│  ┌─────────────────┐                                │
│  │ CSRF Filter     │ → Token validation (if enabled)│
│  └────────┬────────┘                                │
│           ▼                                          │
│  ┌─────────────────┐                                │
│  │ JWT Auth Filter │ → Validate token, set context  │
│  └────────┬────────┘                                │
│           ▼                                          │
│  ┌─────────────────┐                                │
│  │ Authorization   │ → Check roles/permissions      │
│  └────────┬────────┘                                │
└───────────┼─────────────────────────────────────────┘
            ▼
      Controller
```

### Analyzing SecurityConfig

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF (common for REST APIs)
            .csrf(csrf -> csrf.disable())

            // Enable CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Stateless session (for JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()

                // Role-based access
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")

                // All other requests require authentication
                .anyRequest().authenticated()
            )

            // Add JWT filter before UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
```

### Key Security Patterns to Identify

| Pattern | What to Look For |
|---------|------------------|
| Public endpoints | `.permitAll()` rules |
| Protected endpoints | `.authenticated()` or role-based rules |
| CORS configuration | Allowed origins, methods, headers |
| Session management | Stateless (JWT) vs Session-based |
| Custom filters | `addFilterBefore()`, `addFilterAfter()` |

---

## 6. Spring Actuator Endpoints

### If Actuator is Enabled

| Endpoint | Information |
|----------|-------------|
| `/actuator/mappings` | All REST endpoint mappings |
| `/actuator/beans` | All Spring beans |
| `/actuator/env` | Environment variables |
| `/actuator/health` | Application health status |
| `/actuator/info` | Application metadata |
| `/actuator/configprops` | Configuration properties |
| `/actuator/loggers` | Logger configurations |
| `/actuator/metrics` | Application metrics |

### Discovering All Endpoints

```bash
# Get all endpoint mappings
curl http://localhost:8080/actuator/mappings | jq '.contexts.application.mappings.dispatcherServlets'

# Get health status
curl http://localhost:8080/actuator/health

# Get all beans
curl http://localhost:8080/actuator/beans | jq '.contexts.application.beans | keys'
```

### Checking if Actuator is Enabled

```bash
# Try to access actuator
curl http://localhost:8080/actuator

# If enabled, you'll see available endpoints
{
  "_links": {
    "self": { "href": "http://localhost:8080/actuator" },
    "health": { "href": "http://localhost:8080/actuator/health" },
    "info": { "href": "http://localhost:8080/actuator/info" }
  }
}
```

---

## 7. Tools Summary (Part 2)

| Tool | Purpose | Layer |
|------|---------|-------|
| Angular DevTools | Component tree, profiling | Angular |
| Augury | Module visualization | Angular |
| Source Map Explorer | Bundle analysis | Angular |
| JD-GUI | Decompile JAR/class files | Java |
| IntelliJ IDEA | Built-in decompiler | Java |
| javap | CLI class inspection | Java |
| Swagger UI | API docs (if enabled) | Spring |
| Spring Actuator | Runtime inspection | Spring |

---

## 8. Hands-On Exercises (Part 2)

| # | Exercise | Skills Practiced |
|---|----------|------------------|
| 1 | Use Angular DevTools to inspect component hierarchy | Angular analysis |
| 2 | Find all HTTP interceptors in an Angular app | Angular patterns |
| 3 | Identify all services and their API endpoints | Angular service analysis |
| 4 | Extract and decompile a Spring Boot JAR | Java decompilation |
| 5 | Map all REST endpoints from controller classes | Spring analysis |
| 6 | Trace a complete CRUD operation from UI to database | Full stack flow |
| 7 | Analyze Spring Security config to find public endpoints | Security analysis |
| 8 | Use Actuator endpoints to discover all API mappings | Spring introspection |

---

## Ethical Considerations

- Only reverse engineer for learning, debugging, or authorized testing
- Respect Terms of Service and licensing agreements
- Don't bypass authentication or access controls
- Use knowledge to build better applications, not to exploit
- When in doubt, ask for permission

---

## Summary

### Part 1: Core Concepts
- Browser DevTools for HTTP traffic analysis
- Understanding HTTP fundamentals
- API discovery and documentation
- Authentication concepts (JWT, Sessions)
- API testing with Postman/curl

### Part 2: Angular & Spring Specific
- Angular component and service analysis
- Spring Boot project structure and patterns
- Java decompilation techniques
- Full stack data flow tracing
- Spring Security configuration analysis

---

[← Previous: HTTP Cookies](./06-http-cookies.md)
