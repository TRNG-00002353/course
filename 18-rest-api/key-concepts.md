# REST API Key Concepts for Application Developers

## Overview

This document covers essential REST API concepts that every application developer must master. REST (Representational State Transfer) is an architectural style for building web services that are scalable, maintainable, and platform-independent. Understanding REST principles is critical for modern application development.

---

## 1. REST Principles and Architecture

### Why It Matters
- Industry standard for web APIs
- Platform and language independent
- Scalable and stateless design
- Enables microservices architecture
- Supports mobile and web clients

### Key Concepts

| Principle | Description | Benefit |
|-----------|-------------|---------|
| Client-Server | Separation of concerns | Independent evolution |
| Stateless | No client context on server | Scalability |
| Cacheable | Responses marked as cacheable | Performance |
| Uniform Interface | Consistent API design | Simplicity |
| Layered System | Hierarchical architecture | Flexibility |
| Code on Demand (optional) | Server can extend client | Extensibility |

### REST Constraints

**1. Client-Server Architecture:**
```
┌─────────┐        HTTP        ┌─────────┐
│ Client  │ ◄─────────────────► │ Server  │
│ (UI)    │   Request/Response │ (API)   │
└─────────┘                    └─────────┘
```

**2. Stateless:**
```java
// BAD: Server maintains session state
@RestController
public class BadController {
    private Map<String, User> sessions = new HashMap<>(); // Don't do this!

    @GetMapping("/users/current")
    public User getCurrentUser(HttpSession session) {
        return sessions.get(session.getId()); // Server-side state
    }
}

// GOOD: Client sends all necessary information
@RestController
public class GoodController {
    @GetMapping("/users/current")
    public User getCurrentUser(@RequestHeader("Authorization") String token) {
        // Extract user from token (JWT)
        return userService.getUserFromToken(token);
    }
}
```

**3. Cacheable:**
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
            .eTag(String.valueOf(product.getVersion()))
            .body(product);
    }
}
```

**4. Uniform Interface:**
```
Resource Identification:    /api/users/123
Standard Methods:           GET, POST, PUT, DELETE, PATCH
Self-Descriptive Messages:  Content-Type, Status Codes
HATEOAS:                    Links to related resources
```

### Resource-Oriented Design

**Resources vs Actions:**
```
❌ BAD (RPC-style):
POST /api/createUser
POST /api/deleteUser/123
GET /api/getUserById/123

✅ GOOD (REST):
POST /api/users          (create)
DELETE /api/users/123    (delete)
GET /api/users/123       (retrieve)
```

**Resource Naming Conventions:**
```
✅ Use nouns, not verbs:
   /users, /products, /orders

✅ Use plural forms:
   /users (not /user)

✅ Use hierarchical structure:
   /users/123/orders
   /orders/456/items

✅ Use lowercase and hyphens:
   /order-items (not /orderItems or /order_items)

✅ Filter with query parameters:
   /products?category=electronics&maxPrice=1000

❌ Avoid file extensions:
   /users/123 (not /users/123.json)

❌ Avoid verbs in URLs:
   /users (not /getUsers or /createUser)
```

---

## 2. HTTP Methods

### Why It Matters
- Define the action to perform
- Enable CRUD operations
- Support idempotency
- Follow semantic conventions

### Key Concepts

| Method | Purpose | Idempotent | Safe | Request Body | Response Body |
|--------|---------|------------|------|--------------|---------------|
| GET | Retrieve resource | Yes | Yes | No | Yes |
| POST | Create resource | No | No | Yes | Yes |
| PUT | Update/Replace resource | Yes | No | Yes | Yes |
| PATCH | Partial update | No | No | Yes | Yes |
| DELETE | Remove resource | Yes | No | No | Optional |
| HEAD | Get headers only | Yes | Yes | No | No |
| OPTIONS | Get allowed methods | Yes | Yes | No | Yes |

### Method Characteristics

**Safe Methods:**
- Do not modify resources
- Can be cached
- Examples: GET, HEAD, OPTIONS

**Idempotent Methods:**
- Same result when called multiple times
- Examples: GET, PUT, DELETE, HEAD, OPTIONS
- Not idempotent: POST, PATCH

### HTTP Method Examples

**GET - Retrieve Resources:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<User> users = userService.findAll(role, PageRequest.of(page, size));
        return ResponseEntity.ok(users.getContent());
    }

    // Get single user
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    // Get nested resource
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}
```

**POST - Create Resources:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateDTO dto) {
        User user = userService.create(dto);

        // Return 201 Created with Location header
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.getId())
            .toUri();

        return ResponseEntity.created(location).body(user);
    }

    // Bulk creation
    @PostMapping("/batch")
    public ResponseEntity<List<User>> createUsers(@Valid @RequestBody List<UserCreateDTO> dtos) {
        List<User> users = userService.createAll(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(users);
    }

    // Action endpoint (exception to REST, when needed)
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        userService.activate(id);
        return ResponseEntity.ok().build();
    }
}
```

**PUT - Full Update:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Replace entire resource
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {

        User user = userService.update(id, dto);
        return ResponseEntity.ok(user);
    }

    // Idempotent - same result when called multiple times
    // If resource doesn't exist, can optionally create it (upsert)
    @PutMapping("/{id}/upsert")
    public ResponseEntity<User> upsertUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {

        User user = userService.upsert(id, dto);
        boolean created = user.getCreatedAt().equals(user.getUpdatedAt());

        return created
            ? ResponseEntity.status(HttpStatus.CREATED).body(user)
            : ResponseEntity.ok(user);
    }
}
```

**PATCH - Partial Update:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Update specific fields
    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdate(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        User user = userService.partialUpdate(id, updates);
        return ResponseEntity.ok(user);
    }

    // Update specific field
    @PatchMapping("/{id}/email")
    public ResponseEntity<User> updateEmail(
            @PathVariable Long id,
            @RequestBody @Valid EmailUpdateDTO dto) {

        User user = userService.updateEmail(id, dto.getEmail());
        return ResponseEntity.ok(user);
    }
}

// Service implementation
@Service
public class UserService {
    public User partialUpdate(Long id, Map<String, Object> updates) {
        User user = findById(id);

        if (updates.containsKey("email")) {
            user.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("firstName")) {
            user.setFirstName((String) updates.get("firstName"));
        }
        // ... other fields

        return userRepository.save(user);
    }
}
```

**DELETE - Remove Resources:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Delete resource
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Soft delete
    @DeleteMapping("/{id}/soft")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id) {
        userService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    // Delete with confirmation
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUserWithMessage(@PathVariable Long id) {
        String username = userService.delete(id);
        return ResponseEntity.ok(Map.of("message", "User " + username + " deleted"));
    }

    // Bulk delete
    @DeleteMapping
    public ResponseEntity<Void> deleteUsers(@RequestParam List<Long> ids) {
        userService.deleteAll(ids);
        return ResponseEntity.noContent().build();
    }
}
```

**HEAD - Metadata Only:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok()
            .eTag(String.valueOf(user.getVersion()))
            .lastModified(user.getUpdatedAt().toInstant())
            .body(user);
    }

    // HEAD uses same handler as GET but returns no body
    // Useful for checking if resource exists or getting metadata
}

// Client usage:
// HEAD /api/users/123
// Returns headers only (ETag, Last-Modified, etc.)
```

**OPTIONS - Discover Capabilities:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        return ResponseEntity.ok()
            .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT,
                   HttpMethod.PATCH, HttpMethod.DELETE, HttpMethod.OPTIONS)
            .build();
    }
}

// Response headers:
// Allow: GET, POST, PUT, PATCH, DELETE, OPTIONS
// Access-Control-Allow-Methods: GET, POST, PUT, PATCH, DELETE, OPTIONS
```

---

## 3. HTTP Status Codes

### Why It Matters
- Communicate operation results
- Enable proper error handling
- Follow HTTP standards
- Improve API usability

### Key Concepts

| Category | Range | Meaning |
|----------|-------|---------|
| Informational | 100-199 | Request received, continuing |
| Success | 200-299 | Request successful |
| Redirection | 300-399 | Further action needed |
| Client Error | 400-499 | Client error |
| Server Error | 500-599 | Server error |

### Common Status Codes

**Success (2xx):**
```java
// 200 OK - Request successful
@GetMapping("/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
}

// 201 Created - Resource created
@PostMapping
public ResponseEntity<User> createUser(@RequestBody User user) {
    User created = userService.create(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(location).body(created);
}

// 202 Accepted - Request accepted for processing
@PostMapping("/process")
public ResponseEntity<TaskStatus> processAsync(@RequestBody TaskRequest request) {
    TaskStatus status = taskService.submitForProcessing(request);
    return ResponseEntity.accepted().body(status);
}

// 204 No Content - Success but no response body
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
}
```

**Client Errors (4xx):**
```java
// 400 Bad Request - Invalid request
@PostMapping
public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    // Validation errors automatically return 400
    return ResponseEntity.ok(userService.create(user));
}

// 401 Unauthorized - Authentication required
@GetMapping("/profile")
public ResponseEntity<User> getProfile(@AuthenticationPrincipal User user) {
    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(user);
}

// 403 Forbidden - Authenticated but not authorized
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
    if (!currentUser.isAdmin()) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    userService.delete(id);
    return ResponseEntity.noContent().build();
}

// 404 Not Found - Resource doesn't exist
@GetMapping("/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    return userService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}

// 409 Conflict - Request conflicts with current state
@PostMapping
public ResponseEntity<?> createUser(@RequestBody User user) {
    if (userService.existsByUsername(user.getUsername())) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("error", "Username already exists"));
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
}

// 422 Unprocessable Entity - Semantic errors
@PostMapping
public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
    if (user.getAge() < 18) {
        return ResponseEntity.unprocessableEntity()
            .body(Map.of("error", "User must be 18 or older"));
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
}

// 429 Too Many Requests - Rate limit exceeded
@GetMapping
public ResponseEntity<List<User>> getUsers(@RequestHeader("X-User-ID") String userId) {
    if (rateLimiter.isLimitExceeded(userId)) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
            .header("X-RateLimit-Retry-After", "60")
            .build();
    }
    return ResponseEntity.ok(userService.findAll());
}
```

**Server Errors (5xx):**
```java
// 500 Internal Server Error - Generic server error
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error("Internal server error", ex);
    ErrorResponse error = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "An unexpected error occurred",
        System.currentTimeMillis()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
}

// 503 Service Unavailable - Service temporarily unavailable
@GetMapping("/health")
public ResponseEntity<HealthStatus> health() {
    if (!serviceAvailabilityChecker.isAvailable()) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .header("Retry-After", "300")
            .body(new HealthStatus("DOWN"));
    }
    return ResponseEntity.ok(new HealthStatus("UP"));
}
```

**Status Code Selection Guide:**
```java
// Create operations
POST /api/users
  201 Created - Success
  400 Bad Request - Validation failed
  409 Conflict - Resource already exists

// Read operations
GET /api/users/123
  200 OK - Resource found
  404 Not Found - Resource doesn't exist

// Update operations
PUT /api/users/123
  200 OK - Updated (with body)
  204 No Content - Updated (no body)
  404 Not Found - Resource doesn't exist

// Delete operations
DELETE /api/users/123
  204 No Content - Deleted
  404 Not Found - Resource doesn't exist
  200 OK - Deleted (with confirmation message)
```

---

## 4. Data Transfer Objects (DTOs)

### Why It Matters
- Decouple API from internal models
- Control data exposure (security)
- Support versioning
- Enable validation
- Reduce payload size

### Key Concepts

| Pattern | Purpose | Example |
|---------|---------|---------|
| Request DTO | Input validation and transformation | CreateUserDTO |
| Response DTO | Control output format | UserResponseDTO |
| Projection | Partial entity representation | UserSummaryDTO |
| Nested DTO | Composite data structures | OrderWithItemsDTO |

### DTO Examples

**Request DTOs:**
```java
// Create DTO - only fields needed for creation
@Data
public class UserCreateDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}

// Update DTO - allows partial updates
@Data
public class UserUpdateDTO {

    @Email
    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    // No password field - use separate endpoint for password change
}

// Password change DTO
@Data
public class PasswordChangeDTO {

    @NotBlank
    private String currentPassword;

    @NotBlank
    @Size(min = 8)
    private String newPassword;

    @NotBlank
    private String confirmPassword;

    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordsMatching() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}
```

**Response DTOs:**
```java
// Full response DTO
@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;

    // No password, no internal fields
}

// Summary DTO - for list views
@Data
public class UserSummaryDTO {
    private Long id;
    private String username;
    private String fullName;
    private boolean active;

    public UserSummaryDTO(Long id, String username, String firstName,
                          String lastName, boolean active) {
        this.id = id;
        this.username = username;
        this.fullName = firstName + " " + lastName;
        this.active = active;
    }
}

// Nested DTO - with relationships
@Data
public class UserWithProfileDTO {
    private Long id;
    private String username;
    private String email;
    private ProfileDTO profile;
    private List<OrderSummaryDTO> recentOrders;
}

@Data
public class ProfileDTO {
    private String bio;
    private String avatarUrl;
    private String location;
}
```

**DTO Mapping:**
```java
// Manual mapping
@Service
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setActive(user.isActive());
        return dto;
    }

    public User toEntity(UserCreateDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }

    public void updateEntity(User user, UserUpdateDTO dto) {
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
    }
}

// Using ModelMapper
@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

@Service
public class UserService {
    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserResponseDTO.class);
    }
}

// Using MapStruct (compile-time generation)
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponseDTO(User user);
    User toEntity(UserCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(@MappingTarget User user, UserUpdateDTO dto);
}
```

**Controller with DTOs:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        List<UserSummaryDTO> users = userService.findAll().stream()
            .map(userMapper::toSummaryDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserCreateDTO dto) {
        User user = userMapper.toEntity(dto);
        User created = userService.create(user);
        UserResponseDTO response = userMapper.toResponseDTO(created);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {
        User user = userService.findById(id);
        userMapper.updateEntity(user, dto);
        User updated = userService.update(user);
        return ResponseEntity.ok(userMapper.toResponseDTO(updated));
    }
}
```

---

## 5. Exception Handling

### Why It Matters
- Consistent error responses
- Better client experience
- Easier debugging
- Security (avoid exposing internals)

### Key Concepts

**Standard Error Response:**
```java
@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
    private String path;
}

@Data
@AllArgsConstructor
public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors;

    public ValidationErrorResponse(int status, String message, long timestamp,
                                  String path, Map<String, String> errors) {
        super(status, message, timestamp, path);
        this.errors = errors;
    }
}
```

**Custom Exceptions:**
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
```

**Global Exception Handler:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(
            ResourceAlreadyExistsException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            ex.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        ValidationErrorResponse response = new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            System.currentTimeMillis(),
            request.getRequestURI(),
            errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            "Access denied",
            System.currentTimeMillis(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Unexpected error occurred", ex);

        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            System.currentTimeMillis(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Invalid JSON format",
            System.currentTimeMillis(),
            request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            "HTTP method not supported",
            System.currentTimeMillis(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }
}
```

**Example Error Responses:**
```json
// 404 Not Found
{
  "status": 404,
  "message": "User not found with id: 123",
  "timestamp": 1672531200000,
  "path": "/api/users/123"
}

// 400 Validation Error
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": 1672531200000,
  "path": "/api/users",
  "errors": {
    "username": "Username must be 3-20 characters",
    "email": "Invalid email format",
    "password": "Password must be at least 8 characters"
  }
}

// 409 Conflict
{
  "status": 409,
  "message": "User with username 'john' already exists",
  "timestamp": 1672531200000,
  "path": "/api/users"
}
```

---

## 6. API Versioning

### Why It Matters
- Support backward compatibility
- Enable gradual migration
- Manage breaking changes
- Support multiple client versions

### Key Concepts

| Strategy | Example | Pros | Cons |
|----------|---------|------|------|
| URI Versioning | `/api/v1/users` | Simple, visible | URL pollution |
| Header Versioning | `Accept: application/vnd.api.v1+json` | Clean URLs | Hidden version |
| Query Parameter | `/api/users?version=1` | Simple | Not RESTful |
| Content Negotiation | `Accept: application/vnd.api+json;version=1` | RESTful | Complex |

### Versioning Strategies

**URI Versioning (Most Common):**
```java
// Version 1
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    @GetMapping("/{id}")
    public ResponseEntity<UserV1DTO> getUser(@PathVariable Long id) {
        // V1 implementation
        return ResponseEntity.ok(userService.getUserV1(id));
    }
}

// Version 2 - with breaking changes
@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 {

    @GetMapping("/{id}")
    public ResponseEntity<UserV2DTO> getUser(@PathVariable Long id) {
        // V2 implementation with new fields/structure
        return ResponseEntity.ok(userService.getUserV2(id));
    }
}
```

**Header Versioning:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping(value = "/{id}", headers = "X-API-Version=1")
    public ResponseEntity<UserV1DTO> getUserV1(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserV1(id));
    }

    @GetMapping(value = "/{id}", headers = "X-API-Version=2")
    public ResponseEntity<UserV2DTO> getUserV2(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserV2(id));
    }
}

// Client request:
// GET /api/users/123
// X-API-Version: 2
```

**Content Negotiation:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping(value = "/{id}", produces = "application/vnd.api.v1+json")
    public ResponseEntity<UserV1DTO> getUserV1(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserV1(id));
    }

    @GetMapping(value = "/{id}", produces = "application/vnd.api.v2+json")
    public ResponseEntity<UserV2DTO> getUserV2(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserV2(id));
    }
}

// Client request:
// GET /api/users/123
// Accept: application/vnd.api.v2+json
```

**Version Deprecation:**
```java
@RestController
@RequestMapping("/api/v1/users")
@Deprecated
public class UserControllerV1 {

    @GetMapping("/{id}")
    public ResponseEntity<UserV1DTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserV1(id))
            .header("X-API-Deprecated", "true")
            .header("X-API-Sunset", "2024-12-31")
            .header("Link", "</api/v2/users>; rel=\"successor-version\"");
    }
}
```

---

## 7. API Documentation

### Why It Matters
- Enable API discovery
- Reduce support burden
- Facilitate integration
- Serve as contract

### Key Concepts

**OpenAPI/Swagger Configuration:**
```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>
```

```java
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("User Management API")
                .version("1.0")
                .description("API for managing users and their profiles")
                .contact(new Contact()
                    .name("API Support")
                    .email("support@example.com")
                    .url("https://example.com/support"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")))
            .externalDocs(new ExternalDocumentation()
                .description("Full Documentation")
                .url("https://docs.example.com"))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }
}
```

**API Annotations:**
```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    @Operation(
        summary = "Get user by ID",
        description = "Returns a single user by their unique identifier",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User found",
                content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(
            @Parameter(description = "User ID", required = true, example = "123")
            @PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }

    @Operation(
        summary = "Create new user",
        description = "Creates a new user account",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User creation data",
            required = true,
            content = @Content(schema = @Schema(implementation = UserCreateDTO.class))
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserCreateDTO dto) {
        User created = userService.create(userMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userMapper.toResponseDTO(created));
    }
}
```

**Schema Documentation:**
```java
@Schema(description = "User creation request")
@Data
public class UserCreateDTO {

    @Schema(description = "Unique username", example = "john_doe", required = true)
    @NotBlank
    private String username;

    @Schema(description = "User email address", example = "john@example.com", required = true)
    @Email
    @NotBlank
    private String email;

    @Schema(description = "User password", example = "SecurePass123!", required = true, minLength = 8)
    @NotBlank
    @Size(min = 8)
    private String password;

    @Schema(description = "User first name", example = "John")
    private String firstName;

    @Schema(description = "User last name", example = "Doe")
    private String lastName;
}
```

---

## Quick Reference Card

### REST Principles
```
✅ Use nouns for resources: /users, /products
✅ Use HTTP methods: GET, POST, PUT, PATCH, DELETE
✅ Return appropriate status codes
✅ Keep APIs stateless
✅ Use plural forms: /users (not /user)
✅ Use hierarchical URLs: /users/123/orders
✅ Filter with query params: /products?category=electronics
✅ Version your API: /api/v1/users
```

### HTTP Methods Quick Guide
```java
GET    /api/users          // Get all users
GET    /api/users/123      // Get user by ID
POST   /api/users          // Create new user
PUT    /api/users/123      // Update entire user
PATCH  /api/users/123      // Update specific fields
DELETE /api/users/123      // Delete user
```

### Status Codes Quick Guide
```
200 OK                  - Request successful
201 Created            - Resource created
204 No Content         - Success, no response body
400 Bad Request        - Invalid request
401 Unauthorized       - Authentication required
403 Forbidden          - Access denied
404 Not Found          - Resource doesn't exist
409 Conflict           - Resource already exists
422 Unprocessable      - Semantic validation error
500 Internal Error     - Server error
```

### Controller Template
```java
@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private ResourceService service;

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ResourceDTO> create(@Valid @RequestBody CreateDTO dto) {
        ResourceDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand REST architectural principles
- [ ] Design RESTful resource-oriented APIs
- [ ] Use HTTP methods correctly (GET, POST, PUT, PATCH, DELETE)
- [ ] Return appropriate HTTP status codes
- [ ] Implement proper exception handling
- [ ] Create and use DTOs for request/response
- [ ] Version APIs effectively
- [ ] Document APIs using OpenAPI/Swagger
- [ ] Implement pagination and filtering
- [ ] Handle CORS and security headers
- [ ] Follow REST best practices and conventions
- [ ] Test REST APIs thoroughly

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 19: Microservices](../19-microservices/) - Distributed systems architecture
- Practice building complete REST APIs
- Explore advanced topics: HATEOAS, GraphQL, gRPC
- Learn API security (OAuth2, JWT)
- Study API gateway patterns and rate limiting
