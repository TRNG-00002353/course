# Week 06 - Interview FAQ

This document contains frequently asked interview questions and comprehensive answers for Week 06 topics: Spring Framework, Spring Boot, and Spring MVC.

---

## Table of Contents

1. [Spring Framework Core](#spring-framework-core)
2. [Dependency Injection](#dependency-injection)
3. [Bean Configuration and Lifecycle](#bean-configuration-and-lifecycle)
4. [Aspect-Oriented Programming](#aspect-oriented-programming)
5. [Spring Boot Fundamentals](#spring-boot-fundamentals)
6. [Spring Boot Configuration](#spring-boot-configuration)
7. [Spring MVC and REST APIs](#spring-mvc-and-rest-apis)
8. [Exception Handling and Validation](#exception-handling-and-validation)

---

## Spring Framework Core

### Q1: What is the Spring Framework? Why is it used?

**Answer:**

**Spring Framework** is a comprehensive, open-source application framework for Java. It provides infrastructure support for building enterprise applications.

**Why Spring is used:**

| Feature | Benefit |
|---------|---------|
| **Dependency Injection** | Loose coupling, testability |
| **AOP Support** | Separation of concerns |
| **Transaction Management** | Declarative transactions |
| **MVC Framework** | Web application development |
| **Integration** | Works with many technologies |
| **Testability** | Easy unit and integration testing |

**Core Philosophy:**
```
Traditional Java:
- Objects create their own dependencies
- Tight coupling
- Hard to test

Spring:
- Container manages dependencies
- Loose coupling
- Easy to test
```

**Key point:** Spring makes enterprise Java development simpler by handling infrastructure concerns, letting developers focus on business logic.

---

### Q2: What is Inversion of Control (IoC)?

**Answer:**

**Inversion of Control** is a design principle where the control of object creation, configuration, and lifecycle is transferred from the application code to a container or framework.

**Traditional vs IoC approach:**

```java
// Traditional - Object creates its dependency
public class UserService {
    private UserRepository repository;

    public UserService() {
        // UserService controls creation
        this.repository = new UserRepositoryImpl();
    }
}

// IoC - Container provides dependency
public class UserService {
    private UserRepository repository;

    // Container injects dependency
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

**Benefits of IoC:**

| Without IoC | With IoC |
|-------------|----------|
| Hard-coded dependencies | Configurable dependencies |
| Tight coupling | Loose coupling |
| Difficult to test | Easy to mock |
| Code changes needed | Configuration changes |

**Key point:** IoC inverts the responsibility - instead of the class controlling its dependencies, an external container provides them.

---

### Q3: What is Dependency Injection? What are the types?

**Answer:**

**Dependency Injection (DI)** is a technique where objects receive their dependencies from an external source rather than creating them internally. It's a specific implementation of IoC.

**Three types of DI:**

**1. Constructor Injection (Recommended):**
```java
@Service
public class UserService {
    private final UserRepository repository;

    @Autowired  // Optional in Spring 4.3+
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

**2. Setter Injection:**
```java
@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
```

**3. Field Injection (Not Recommended):**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
}
```

**Comparison:**

| Type | Pros | Cons |
|------|------|------|
| **Constructor** | Immutable, required deps clear, easy testing | More verbose |
| **Setter** | Optional dependencies | Mutable, harder to test |
| **Field** | Concise | Requires reflection, hard to test |

**Why Constructor Injection is preferred:**
- Dependencies are explicit
- Objects are immutable (final fields)
- Easy to test without Spring
- Fails fast if dependencies missing

---

### Q4: What is the difference between @Component, @Service, @Repository, and @Controller?

**Answer:**

All are stereotype annotations that mark a class as a Spring bean, but they indicate the component's role:

```java
@Component   // Generic component
@Service     // Business logic layer
@Repository  // Data access layer
@Controller  // Web presentation layer
```

**Detailed comparison:**

| Annotation | Layer | Special Features |
|------------|-------|------------------|
| `@Component` | Any | Base annotation, generic |
| `@Service` | Service | Semantic only, no extra features |
| `@Repository` | DAO | Exception translation |
| `@Controller` | Web | Web request handling |

**Exception Translation in @Repository:**
```java
@Repository
public class UserRepository {
    // DataAccessException hierarchy
    // instead of SQLException
}
```

**Best practice - Layered architecture:**
```
@RestController (presentation)
        ↓
    @Service (business logic)
        ↓
   @Repository (data access)
```

**Key point:** Use the appropriate annotation for each layer - it improves code readability and enables layer-specific features.

---

## Dependency Injection

### Q5: What is @Autowired? How does it work?

**Answer:**

`@Autowired` tells Spring to automatically inject a matching bean into the annotated field, constructor, or setter.

**How Spring resolves dependencies:**

```
1. Look for bean by type
2. If multiple matches, look for @Qualifier or @Primary
3. If still ambiguous, try matching by name
4. If no match, throw NoSuchBeanDefinitionException
```

**Usage examples:**

```java
// Constructor (recommended)
@Service
public class OrderService {
    private final PaymentService paymentService;

    @Autowired  // Optional with single constructor
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}

// Setter
@Service
public class OrderService {
    private PaymentService paymentService;

    @Autowired(required = false)  // Optional dependency
    public void setPaymentService(PaymentService service) {
        this.paymentService = service;
    }
}
```

**Handling multiple beans:**

```java
// Option 1: @Qualifier
@Autowired
@Qualifier("creditCardPayment")
private PaymentService paymentService;

// Option 2: @Primary on bean definition
@Component
@Primary
public class CreditCardPayment implements PaymentService { }

// Option 3: Inject all implementations
@Autowired
private List<PaymentService> paymentServices;
```

---

### Q6: What is the difference between @Qualifier and @Primary?

**Answer:**

Both resolve ambiguity when multiple beans of the same type exist, but they work differently:

**@Primary - Default choice:**
```java
@Component
@Primary
public class EmailNotification implements NotificationService {
    // This is injected by default
}

@Component
public class SmsNotification implements NotificationService {
    // Not injected unless explicitly requested
}

@Service
public class AlertService {
    @Autowired
    private NotificationService notification;  // Gets EmailNotification
}
```

**@Qualifier - Explicit choice:**
```java
@Component("email")
public class EmailNotification implements NotificationService { }

@Component("sms")
public class SmsNotification implements NotificationService { }

@Service
public class AlertService {
    @Autowired
    @Qualifier("sms")  // Explicitly choose SMS
    private NotificationService notification;
}
```

**Comparison:**

| Aspect | @Primary | @Qualifier |
|--------|----------|------------|
| Where used | On bean definition | On injection point |
| Scope | Global default | Per injection |
| Override | Can be overridden by @Qualifier | Explicit choice |
| Use case | One primary implementation | Multiple explicit choices |

**Key point:** Use `@Primary` when one bean is the obvious default; use `@Qualifier` when you need explicit control at each injection point.

---

## Bean Configuration and Lifecycle

### Q7: What are Bean Scopes in Spring?

**Answer:**

Bean scope determines how many instances Spring creates and how long they live.

| Scope | Instances | Lifecycle |
|-------|-----------|-----------|
| **singleton** | One per container | Container lifecycle |
| **prototype** | New per request | Caller manages |
| **request** | One per HTTP request | Request lifecycle |
| **session** | One per HTTP session | Session lifecycle |
| **application** | One per ServletContext | Application lifecycle |

**Examples:**

```java
// Singleton (default) - shared instance
@Service
@Scope("singleton")
public class UserService { }

// Prototype - new instance each time
@Component
@Scope("prototype")
public class ShoppingCart { }

// Request scope (web only)
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
       proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext { }
```

**When to use each:**

| Scope | Use Case |
|-------|----------|
| Singleton | Stateless services, repositories |
| Prototype | Stateful objects, builders |
| Request | Request-specific data |
| Session | User session data |

**Important:** Singleton beans must be thread-safe since they're shared across all threads.

---

### Q8: What is the Bean Lifecycle? Explain @PostConstruct and @PreDestroy.

**Answer:**

**Bean Lifecycle stages:**

```
1. Container Startup
2. Bean Instantiation (constructor)
3. Dependency Injection
4. @PostConstruct method
5. Bean Ready for Use
6. Container Shutdown
7. @PreDestroy method
8. Bean Destroyed
```

**Lifecycle callbacks:**

```java
@Service
public class DatabaseService {
    private Connection connection;

    public DatabaseService() {
        System.out.println("1. Constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("2. @PostConstruct - Opening connection");
        this.connection = openConnection();
    }

    public void doWork() {
        System.out.println("3. Business method");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("4. @PreDestroy - Closing connection");
        if (connection != null) {
            connection.close();
        }
    }
}
```

**Common use cases:**

| Annotation | Use Case |
|------------|----------|
| `@PostConstruct` | Initialize resources, validate configuration, load cache |
| `@PreDestroy` | Close connections, release resources, flush data |

**Alternative approaches:**
```java
// Using InitializingBean and DisposableBean interfaces
@Service
public class MyService implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() { /* init */ }

    @Override
    public void destroy() { /* cleanup */ }
}

// Using @Bean annotation
@Bean(initMethod = "init", destroyMethod = "cleanup")
public MyService myService() {
    return new MyService();
}
```

---

## Aspect-Oriented Programming

### Q9: What is AOP? What are cross-cutting concerns?

**Answer:**

**Aspect-Oriented Programming (AOP)** is a programming paradigm that allows separating cross-cutting concerns from business logic.

**Cross-cutting concerns** are functionalities that span multiple layers:

```
Without AOP:
┌────────────────────────────────────────┐
│  logging + security + business logic   │  (tangled)
│  + transaction + logging...            │
└────────────────────────────────────────┘

With AOP:
┌─────────────┐  ┌─────────────────────┐
│   Aspects   │  │   Business Logic    │
│  - Logging  │  │   (clean, focused)  │
│  - Security │  └─────────────────────┘
│  - Txn      │
└─────────────┘
```

**Common cross-cutting concerns:**

| Concern | Without AOP | With AOP |
|---------|-------------|----------|
| Logging | Log calls in every method | Logging aspect |
| Security | Check permissions everywhere | Security aspect |
| Transactions | Try-catch in every service | `@Transactional` |
| Caching | Cache logic repeated | Caching aspect |
| Performance | Timing code duplicated | Monitoring aspect |

**Example - Logging without AOP:**
```java
public void createUser(User user) {
    logger.info("Starting createUser");
    try {
        // business logic
        logger.info("User created successfully");
    } catch (Exception e) {
        logger.error("Error creating user", e);
        throw e;
    }
}
```

**With AOP:**
```java
public void createUser(User user) {
    // Only business logic
}

// Logging handled by aspect
@Aspect
public class LoggingAspect { }
```

---

### Q10: Explain AOP terminology: Aspect, Advice, Pointcut, Join Point.

**Answer:**

| Term | Definition | Example |
|------|------------|---------|
| **Aspect** | Module containing cross-cutting logic | LoggingAspect class |
| **Advice** | Action taken at a join point | The logging code |
| **Join Point** | Point where advice can apply | Method execution |
| **Pointcut** | Expression selecting join points | "all service methods" |

**Visual representation:**

```
@Aspect                          ← Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.example.service.*.*(..))")  ← Pointcut
    public void serviceMethods() { }

    @Before("serviceMethods()")                             ← Advice
    public void logBefore(JoinPoint joinPoint) {           ← Join Point
        logger.info("Calling: {}", joinPoint.getSignature());
    }
}
```

**Advice Types:**

| Type | When | Can modify result? |
|------|------|-------------------|
| `@Before` | Before method | No |
| `@After` | After method (always) | No |
| `@AfterReturning` | After successful return | Can access result |
| `@AfterThrowing` | After exception | Can access exception |
| `@Around` | Wraps method | Yes |

**Pointcut expressions:**
```java
// All methods in service package
@Before("execution(* com.example.service.*.*(..))")

// All public methods
@Before("execution(public * *(..))")

// Methods annotated with @Transactional
@Before("@annotation(org.springframework.transaction.annotation.Transactional)")

// Methods in classes annotated with @Service
@Before("within(@org.springframework.stereotype.Service *)")
```

---

### Q11: What is the difference between @Before, @After, and @Around advice?

**Answer:**

```java
@Aspect
@Component
public class DemoAspect {

    // @Before - Runs before method
    @Before("execution(* com.example.service.*.*(..))")
    public void beforeAdvice(JoinPoint jp) {
        logger.info("Before: {}", jp.getSignature());
        // Cannot prevent method execution
        // Cannot modify return value
    }

    // @After - Runs after method (always, like finally)
    @After("execution(* com.example.service.*.*(..))")
    public void afterAdvice(JoinPoint jp) {
        logger.info("After: {}", jp.getSignature());
        // Runs whether method succeeds or throws
    }

    // @Around - Wraps entire method execution
    @Around("execution(* com.example.service.*.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Before method");

        // Can decide whether to proceed
        Object result = pjp.proceed();  // Call actual method

        // Can modify result
        logger.info("After method, result: {}", result);
        return result;
    }
}
```

**Comparison:**

| Aspect | @Before | @After | @Around |
|--------|---------|--------|---------|
| Prevent execution | No | No | Yes |
| Modify parameters | No | No | Yes |
| Modify return value | No | No | Yes |
| Handle exceptions | No | No | Yes |
| Use case | Logging, validation | Cleanup | Performance, transactions |

**When to use each:**
- **@Before**: Pre-processing, logging, security checks
- **@After**: Cleanup, logging (always runs)
- **@Around**: Performance monitoring, caching, transactions

---

## Spring Boot Fundamentals

### Q12: What is Spring Boot? How is it different from Spring Framework?

**Answer:**

**Spring Boot** is a framework built on top of Spring that simplifies application development through auto-configuration and opinionated defaults.

**Comparison:**

| Aspect | Spring Framework | Spring Boot |
|--------|------------------|-------------|
| Configuration | Manual | Auto-configuration |
| Dependencies | Manage yourself | Starter POMs |
| Server | External (Tomcat, JBoss) | Embedded |
| Setup time | Hours | Minutes |
| XML config | Often required | Not needed |
| Production features | Add manually | Built-in (Actuator) |

**Spring Boot advantages:**

```java
// Spring Framework - Manual configuration
@Configuration
@EnableWebMvc
@ComponentScan("com.example")
public class WebConfig implements WebMvcConfigurer {
    // Configure view resolvers, message converters, etc.
}

// Spring Boot - Auto-configured
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
// That's it! Everything auto-configured
```

**Key Spring Boot features:**
1. **Auto-configuration**: Configures beans based on classpath
2. **Starter dependencies**: Pre-packaged dependency bundles
3. **Embedded servers**: No external server needed
4. **Actuator**: Production-ready monitoring
5. **DevTools**: Development productivity

**Key point:** Spring Boot doesn't replace Spring Framework - it builds on it to make development faster and easier.

---

### Q13: What does @SpringBootApplication do?

**Answer:**

`@SpringBootApplication` is a convenience annotation that combines three annotations:

```java
@SpringBootApplication
=
@SpringBootConfiguration  // Marks as config class
+
@EnableAutoConfiguration  // Enable auto-config
+
@ComponentScan           // Scan for components
```

**Detailed breakdown:**

```java
// This...
@SpringBootApplication
public class MyApplication { }

// ...is equivalent to:
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.myapp")
public class MyApplication { }
```

**Package scanning:**
```
com.example.myapp
    ├── MyApplication.java  ← @SpringBootApplication here
    ├── controller/         ← Scanned
    ├── service/            ← Scanned
    └── repository/         ← Scanned

com.example.other/          ← NOT scanned by default
```

**Customization:**
```java
@SpringBootApplication(
    scanBasePackages = {"com.example.app", "com.example.common"},
    exclude = {DataSourceAutoConfiguration.class}
)
public class MyApplication { }
```

**Key point:** Place your main class in the root package so all sub-packages are scanned automatically.

---

### Q14: What is Auto-Configuration in Spring Boot?

**Answer:**

**Auto-configuration** automatically configures Spring beans based on:
1. Classes on the classpath
2. Existing bean definitions
3. Properties settings

**How it works:**

```
1. Add dependency (e.g., spring-boot-starter-data-jpa)
2. Spring Boot detects JPA classes on classpath
3. Automatically configures:
   - DataSource
   - EntityManagerFactory
   - TransactionManager
4. You just use it!
```

**Example - Auto-configured DataSource:**
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=user
spring.datasource.password=pass
```

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // DataSource, EntityManager auto-configured!
}
```

**Conditional auto-configuration:**
```java
@Configuration
@ConditionalOnClass(DataSource.class)        // Only if class exists
@ConditionalOnMissingBean(DataSource.class)  // Only if not defined
public class DataSourceAutoConfiguration {

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}
```

**Debugging auto-configuration:**
```properties
# Add to application.properties
debug=true
```

This shows what was auto-configured and why.

---

### Q15: What are Spring Boot Starters?

**Answer:**

**Starters** are pre-packaged dependency bundles that include everything needed for a specific functionality.

**Common Starters:**

| Starter | Purpose | Includes |
|---------|---------|----------|
| `spring-boot-starter-web` | REST APIs | MVC, Jackson, Tomcat |
| `spring-boot-starter-data-jpa` | Database | JPA, Hibernate, HikariCP |
| `spring-boot-starter-security` | Security | Spring Security |
| `spring-boot-starter-test` | Testing | JUnit, Mockito, AssertJ |
| `spring-boot-starter-actuator` | Monitoring | Health, Metrics |

**Without starters:**
```xml
<!-- Need to manage all dependencies manually -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>5.3.20</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.20</version>
</dependency>
<!-- ... many more -->
```

**With starters:**
```xml
<!-- One dependency includes everything -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**Benefits:**
- Compatible versions guaranteed
- Reduced dependency management
- Quick project setup
- Best practices built-in

---

## Spring Boot Configuration

### Q16: How do you externalize configuration in Spring Boot?

**Answer:**

Spring Boot supports multiple ways to externalize configuration:

**1. application.properties:**
```properties
server.port=8080
app.name=MyApp
```

**2. application.yml:**
```yaml
server:
  port: 8080
app:
  name: MyApp
```

**3. Environment variables:**
```bash
export SERVER_PORT=8080
export APP_NAME=MyApp
```

**4. Command line arguments:**
```bash
java -jar app.jar --server.port=8080 --app.name=MyApp
```

**Accessing properties in code:**

```java
// Using @Value
@Component
public class MyService {
    @Value("${app.name}")
    private String appName;

    @Value("${app.timeout:30}")  // Default value
    private int timeout;
}

// Using @ConfigurationProperties (type-safe)
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private int timeout;
    // getters and setters
}
```

**Property precedence (highest to lowest):**
1. Command line arguments
2. Environment variables
3. application-{profile}.properties
4. application.properties

---

### Q17: What are Profiles in Spring Boot?

**Answer:**

**Profiles** allow different configurations for different environments.

**Profile-specific files:**
```
src/main/resources/
├── application.properties        # Default (all profiles)
├── application-dev.properties    # Development
├── application-test.properties   # Testing
└── application-prod.properties   # Production
```

**Example configuration:**
```properties
# application.properties (default)
app.name=MyApp

# application-dev.properties
spring.datasource.url=jdbc:h2:mem:testdb
logging.level.com.example=DEBUG

# application-prod.properties
spring.datasource.url=jdbc:postgresql://prod-db:5432/mydb
logging.level.com.example=INFO
```

**Activating profiles:**
```bash
# Command line
java -jar app.jar --spring.profiles.active=prod

# Environment variable
export SPRING_PROFILES_ACTIVE=dev

# In application.properties
spring.profiles.active=dev
```

**Profile-specific beans:**
```java
@Configuration
@Profile("dev")
public class DevConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabase();  // H2 in-memory
    }
}

@Configuration
@Profile("prod")
public class ProdConfig {
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();  // PostgreSQL
    }
}

@Service
@Profile("!prod")  // All profiles except prod
public class DebugService { }
```

---

### Q18: What is Spring Boot Actuator?

**Answer:**

**Actuator** provides production-ready features for monitoring and managing your application.

**Setup:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

**Key endpoints:**

| Endpoint | Purpose |
|----------|---------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application information |
| `/actuator/metrics` | JVM and application metrics |
| `/actuator/env` | Environment properties |
| `/actuator/loggers` | View/modify log levels |
| `/actuator/beans` | All Spring beans |

**Configuration:**
```properties
# Expose endpoints
management.endpoints.web.exposure.include=health,info,metrics

# Show health details
management.endpoint.health.show-details=always

# Custom info
info.app.name=My Application
info.app.version=1.0.0
```

**Custom health indicator:**
```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (databaseIsUp()) {
            return Health.up()
                .withDetail("database", "Available")
                .build();
        }
        return Health.down()
            .withDetail("database", "Unavailable")
            .build();
    }
}
```

---

## Spring MVC and REST APIs

### Q19: What is Spring MVC? Explain the DispatcherServlet.

**Answer:**

**Spring MVC** is a web framework for building REST APIs and web applications, built on the Servlet API.

**Request flow:**

```
Client Request
     ↓
DispatcherServlet (Front Controller)
     ↓
HandlerMapping (finds controller)
     ↓
Controller (processes request)
     ↓
HttpMessageConverter (JSON conversion)
     ↓
Response
```

**DispatcherServlet** is the front controller that:
1. Receives all incoming HTTP requests
2. Delegates to appropriate handlers (controllers)
3. Returns responses to clients

**Complete REST Controller:**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saved = userService.save(user);
        URI location = URI.create("/api/users/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

### Q20: What is the difference between @Controller and @RestController?

**Answer:**

| Aspect | @Controller | @RestController |
|--------|-------------|-----------------|
| Purpose | Web pages (MVC) | REST APIs |
| Response | View name | Data (JSON/XML) |
| @ResponseBody | Required per method | Included |
| Use case | Server-side rendering | API endpoints |

**@Controller example:**
```java
@Controller
public class WebController {

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";  // Returns view name → users.html
    }

    @GetMapping("/api/users")
    @ResponseBody  // Required for JSON
    public List<User> getUsersApi() {
        return userService.findAll();
    }
}
```

**@RestController example:**
```java
@RestController  // = @Controller + @ResponseBody
@RequestMapping("/api/users")
public class UserApiController {

    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();  // Automatically JSON
    }
}
```

**Key point:** Use `@RestController` for REST APIs, `@Controller` for server-rendered web pages.

---

### Q21: Explain @PathVariable, @RequestParam, and @RequestBody.

**Answer:**

**@PathVariable** - Extract from URL path:
```java
// URL: GET /api/users/123
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userService.findById(id);
}

// Multiple path variables: GET /api/users/123/orders/456
@GetMapping("/users/{userId}/orders/{orderId}")
public Order getOrder(
        @PathVariable Long userId,
        @PathVariable Long orderId) {
    return orderService.findByUserAndId(userId, orderId);
}
```

**@RequestParam** - Extract from query string:
```java
// URL: GET /api/users?page=0&size=10&sort=name
@GetMapping("/users")
public Page<User> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String sort) {
    return userService.findAll(page, size, sort);
}
```

**@RequestBody** - Bind JSON body to object:
```java
// POST /api/users with JSON body
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    return userService.save(user);
}
```

**Comparison:**

| Annotation | Source | Example URL |
|------------|--------|-------------|
| @PathVariable | URL path | `/users/123` |
| @RequestParam | Query string | `/users?id=123` |
| @RequestBody | HTTP body | POST with JSON |

---

### Q22: What is ResponseEntity and when should you use it?

**Answer:**

**ResponseEntity** gives full control over the HTTP response: status code, headers, and body.

**Basic usage:**
```java
@GetMapping("/{id}")
public ResponseEntity<User> getUser(@PathVariable Long id) {
    return userService.findById(id)
        .map(user -> ResponseEntity.ok(user))           // 200 OK
        .orElse(ResponseEntity.notFound().build());     // 404 Not Found
}
```

**Common factory methods:**

| Method | Status Code | Use Case |
|--------|-------------|----------|
| `ok(body)` | 200 | Successful GET |
| `created(uri)` | 201 | Resource created |
| `noContent()` | 204 | Successful DELETE |
| `badRequest()` | 400 | Invalid request |
| `notFound()` | 404 | Resource not found |

**Advanced usage:**
```java
@PostMapping
public ResponseEntity<User> createUser(@RequestBody User user) {
    User saved = userService.save(user);
    URI location = URI.create("/api/users/" + saved.getId());

    return ResponseEntity
        .created(location)                  // 201 status
        .header("X-Custom-Header", "value") // Custom header
        .body(saved);                       // Response body
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.noContent().build();  // 204 No Content
}
```

**When to use:**
- Need specific status codes
- Need custom headers
- Conditional responses (found/not found)
- Following REST conventions precisely

---

## Exception Handling and Validation

### Q23: How do you handle exceptions in Spring MVC?

**Answer:**

**Global exception handling with @ControllerAdvice:**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Custom exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            message,
            request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }

    // Catch-all for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

**Error response DTO:**
```java
public class ErrorResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String message;
    private String path;

    // Constructor, getters
}
```

**Custom exception:**
```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " not found with id: " + id);
    }
}
```

---

### Q24: How do you validate request data in Spring?

**Answer:**

**Setup:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

**DTO with validation:**
```java
public class UserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be 2-50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Must be at least 18")
    @Max(value = 120, message = "Invalid age")
    private Integer age;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = ".*[A-Z].*", message = "Must contain uppercase letter")
    private String password;

    // Getters and setters
}
```

**Controller with @Valid:**
```java
@PostMapping
public ResponseEntity<User> createUser(
        @Valid @RequestBody UserRequest request) {
    // Only reached if validation passes
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.create(request));
}
```

**Common validation annotations:**

| Annotation | Purpose |
|------------|---------|
| `@NotNull` | Not null |
| `@NotEmpty` | Not null or empty |
| `@NotBlank` | Not null, not empty, not whitespace |
| `@Size(min, max)` | String/collection size |
| `@Min`, `@Max` | Numeric range |
| `@Email` | Valid email |
| `@Pattern(regexp)` | Regex match |
| `@Past`, `@Future` | Date validation |

---

## Summary

| Topic | Key Concepts |
|-------|--------------|
| **Spring Core** | IoC, DI, ApplicationContext, Beans |
| **Stereotypes** | @Component, @Service, @Repository, @Controller |
| **DI Types** | Constructor (recommended), Setter, Field |
| **Bean Scopes** | Singleton (default), Prototype, Request, Session |
| **AOP** | Aspects, Advice, Pointcuts, Join Points |
| **Spring Boot** | Auto-configuration, Starters, Embedded server |
| **Configuration** | application.properties, @Value, @ConfigurationProperties |
| **Profiles** | Environment-specific configuration |
| **Spring MVC** | DispatcherServlet, @RestController, Request mapping |
| **REST** | @GetMapping, @PostMapping, ResponseEntity |
| **Validation** | @Valid, @NotBlank, @Email, @Size |
| **Exception Handling** | @ControllerAdvice, @ExceptionHandler |

---

*Week 06 covers the Spring ecosystem fundamentals that are essential for building modern Java enterprise applications and REST APIs.*
