# Spring Framework Key Concepts for Application Developers

## Overview

This document covers essential Spring Framework concepts that every Java application developer must master. Spring is the most popular enterprise Java framework, providing comprehensive infrastructure support for developing Java applications. Understanding Inversion of Control (IoC), Dependency Injection (DI), and Aspect-Oriented Programming (AOP) is fundamental to modern Java enterprise development.

---

## 1. Spring Framework Overview

### Why It Matters
- Industry-standard framework for enterprise Java
- Required skill for most Java development positions
- Foundation for Spring Boot, Spring Data, Spring Security
- Simplifies enterprise application development
- Promotes best practices and clean architecture

### Key Concepts

| Component | Purpose | Developer Use Case |
|-----------|---------|-------------------|
| Spring Core | IoC and DI | Manage object dependencies |
| Spring MVC | Web applications | Build REST APIs |
| Spring Data | Database access | Simplify data operations |
| Spring Security | Authentication/authorization | Secure applications |
| Spring AOP | Cross-cutting concerns | Logging, transactions |
| Spring Boot | Auto-configuration | Rapid development |

### Spring Ecosystem

```
Spring Framework (Core)
    ├── Spring Core (IoC, DI)
    ├── Spring MVC (Web)
    ├── Spring Data (Database)
    ├── Spring Security (Auth)
    └── Spring AOP (Aspects)

Spring Boot
    └── Auto-configuration on top of Spring Framework

Spring Cloud
    └── Microservices patterns
```

### Spring vs Java EE

| Aspect | Spring | Java EE |
|--------|--------|---------|
| Container | Lightweight | Application server |
| Configuration | Flexible (XML, annotations, Java) | Mainly annotations |
| Testing | Easy unit/integration testing | Harder to test |
| Learning curve | Moderate | Steeper |
| Adoption | Very high | Lower |

---

## 2. Inversion of Control (IoC)

### Why It Matters
- Fundamental principle of Spring Framework
- Decouples component creation from usage
- Enables testability and flexibility
- Foundation for dependency injection
- Essential for understanding Spring architecture

### Key Concepts

```
Traditional Control Flow:
    Class creates its own dependencies
    Class controls lifecycle

Inversion of Control:
    Container creates dependencies
    Container controls lifecycle
    Class receives dependencies
```

| Concept | Description | Benefit |
|---------|-------------|---------|
| IoC Container | Manages object lifecycle | Centralized control |
| Bean | Spring-managed object | Automatic lifecycle |
| Configuration | How beans are defined | Flexibility |
| ApplicationContext | Advanced container | Full Spring features |

### Traditional vs IoC Approach

```java
// Traditional approach (tight coupling)
public class UserService {
    private UserRepository userRepository;

    public UserService() {
        // Service creates its own dependency
        this.userRepository = new UserRepositoryImpl();
    }

    public User getUser(int id) {
        return userRepository.findById(id);
    }
}

// Problems:
// - Hard to test (can't mock UserRepository)
// - Tight coupling to UserRepositoryImpl
// - Hard to change implementation
```

```java
// IoC approach (loose coupling)
public class UserService {
    private UserRepository userRepository;

    // Dependency is injected by container
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(int id) {
        return userRepository.findById(id);
    }
}

// Benefits:
// - Easy to test (can inject mock)
// - Loose coupling (depends on interface)
// - Easy to change implementation
// - Spring container manages lifecycle
```

### Spring IoC Container Types

```java
// BeanFactory - Basic container
BeanFactory factory = new XmlBeanFactory(new FileSystemResource("beans.xml"));
UserService service = (UserService) factory.getBean("userService");

// ApplicationContext - Full-featured container (recommended)
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
UserService service = context.getBean(UserService.class);

// Annotation-based context
ApplicationContext context =
    new AnnotationConfigApplicationContext(AppConfig.class);
UserService service = context.getBean(UserService.class);
```

### ApplicationContext Types

```java
// XML-based
ApplicationContext context =
    new ClassPathXmlApplicationContext("applicationContext.xml");

// Annotation-based
ApplicationContext context =
    new AnnotationConfigApplicationContext(AppConfig.class);

// Web application
ApplicationContext context =
    new AnnotationConfigWebApplicationContext();

// Spring Boot
// ApplicationContext automatically created
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

---

## 3. Dependency Injection (DI)

### Why It Matters
- Core pattern for decoupling components
- Makes code testable and maintainable
- Enables swapping implementations easily
- Foundation of Spring Framework
- Industry best practice for enterprise applications

### Key Concepts

| DI Type | Method | When to Use |
|---------|--------|-------------|
| Constructor Injection | Via constructor | Required dependencies (recommended) |
| Setter Injection | Via setter methods | Optional dependencies |
| Field Injection | Direct field access | Not recommended (testing issues) |

### Constructor Injection (Recommended)

```java
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    // Constructor injection (recommended)
    @Autowired  // Optional in Spring 4.3+ with single constructor
    public UserService(UserRepository userRepository,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User createUser(String username, String email) {
        User user = new User(username, email);
        userRepository.save(user);
        emailService.sendWelcomeEmail(user);
        return user;
    }
}

// Benefits:
// - Immutable (final fields)
// - Required dependencies clear
// - Easy to test (no reflection needed)
// - NPE impossible if dependencies missing
```

### Setter Injection

```java
@Service
public class ReportService {
    private EmailService emailService;
    private SmsService smsService;

    // Optional dependency via setter
    @Autowired(required = false)
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired(required = false)
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    public void sendReport(Report report) {
        if (emailService != null) {
            emailService.send(report);
        }
        if (smsService != null) {
            smsService.send(report);
        }
    }
}
```

### Field Injection (Not Recommended)

```java
@Service
public class OrderService {
    // Field injection (avoid in production code)
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentService paymentService;

    // Problems:
    // - Can't make fields final
    // - Harder to test (need reflection)
    // - Hides dependencies
    // - Can't enforce required dependencies
}
```

### Handling Multiple Implementations

```java
// Multiple implementations of same interface
public interface NotificationService {
    void sendNotification(String message);
}

@Component
public class EmailNotificationService implements NotificationService {
    public void sendNotification(String message) {
        // Send email
    }
}

@Component
public class SmsNotificationService implements NotificationService {
    public void sendNotification(String message) {
        // Send SMS
    }
}

// Option 1: @Qualifier
@Service
public class UserService {
    private final NotificationService notificationService;

    public UserService(@Qualifier("emailNotificationService")
                       NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}

// Option 2: @Primary
@Component
@Primary
public class EmailNotificationService implements NotificationService {
    // This will be injected by default
}

@Service
public class UserService {
    private final NotificationService notificationService;

    public UserService(NotificationService notificationService) {
        // Receives EmailNotificationService (marked @Primary)
        this.notificationService = notificationService;
    }
}

// Option 3: Inject all implementations
@Service
public class NotificationService {
    private final List<NotificationService> notificationServices;

    public NotificationService(List<NotificationService> notificationServices) {
        this.notificationServices = notificationServices;
    }

    public void sendAll(String message) {
        notificationServices.forEach(service ->
            service.sendNotification(message));
    }
}
```

---

## 4. Bean Configuration

### Why It Matters
- Defines how Spring creates and manages beans
- Multiple configuration styles for flexibility
- Understanding configuration is key to Spring mastery
- Different approaches suit different scenarios

### Key Concepts

| Configuration Type | Approach | Use Case |
|-------------------|----------|----------|
| XML-based | XML files | Legacy applications |
| Annotation-based | @Component, @Service | Most common |
| Java-based | @Configuration, @Bean | Complex beans |
| Mixed | Combination | Gradual migration |

### Annotation-Based Configuration (Most Common)

```java
// Component scanning configuration
@Configuration
@ComponentScan(basePackages = "com.example.myapp")
public class AppConfig {
    // Configuration class
}

// Stereotypes - Mark classes as Spring beans
@Component  // Generic component
public class MyComponent {
}

@Service    // Business logic layer
public class UserService {
}

@Repository // Data access layer
public class UserRepository {
}

@Controller // Web presentation layer
public class UserController {
}
```

### Java-Based Configuration

```java
@Configuration
public class AppConfig {

    // Define bean with @Bean
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    // Bean with dependencies
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    // Bean with custom initialization
    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/mydb");
        dataSource.setUsername("dbuser");
        dataSource.setPassword("dbpass");
        return dataSource;
    }

    // Conditional bean
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        // H2 in-memory database for development
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        // PostgreSQL for production
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(env.getProperty("db.url"));
        return dataSource;
    }
}
```

### XML-Based Configuration (Legacy)

```xml
<!-- applicationContext.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Define beans -->
    <bean id="userRepository" class="com.example.UserRepositoryImpl"/>

    <!-- Bean with constructor injection -->
    <bean id="userService" class="com.example.UserService">
        <constructor-arg ref="userRepository"/>
    </bean>

    <!-- Bean with property injection -->
    <bean id="emailService" class="com.example.EmailService">
        <property name="host" value="smtp.gmail.com"/>
        <property name="port" value="587"/>
    </bean>
</beans>
```

---

## 5. Bean Scopes and Lifecycle

### Why It Matters
- Controls when and how many bean instances created
- Manages bean lifecycle and resource cleanup
- Critical for web applications and stateful beans
- Performance and memory management

### Key Concepts

| Scope | Instances | Lifecycle | Use Case |
|-------|-----------|-----------|----------|
| singleton | One per container | Container lifecycle | Stateless services (default) |
| prototype | New per request | Caller manages | Stateful objects |
| request | One per HTTP request | Request lifecycle | Web - request data |
| session | One per HTTP session | Session lifecycle | Web - user session |
| application | One per ServletContext | Application lifecycle | Web - global state |

### Bean Scope Examples

```java
// Singleton (default) - One instance per container
@Service
@Scope("singleton")  // Default, can omit
public class UserService {
    // Shared by all clients
    // Must be thread-safe!
}

// Prototype - New instance per request
@Component
@Scope("prototype")
public class ShoppingCart {
    private List<Item> items = new ArrayList<>();
    // Each user gets own instance
}

// Request scope (web applications)
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext {
    private String requestId;
    private String userId;
    // One per HTTP request
}

// Session scope (web applications)
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {
    private User currentUser;
    private ShoppingCart cart;
    // One per user session
}
```

### Bean Lifecycle

```
Container Startup
    ↓
Bean Instantiation
    ↓
Dependency Injection
    ↓
BeanNameAware.setBeanName()
    ↓
BeanFactoryAware.setBeanFactory()
    ↓
ApplicationContextAware.setApplicationContext()
    ↓
@PostConstruct / InitializingBean.afterPropertiesSet()
    ↓
Custom init-method
    ↓
Bean Ready for Use
    ↓
Container Shutdown
    ↓
@PreDestroy / DisposableBean.destroy()
    ↓
Custom destroy-method
```

### Lifecycle Callbacks

```java
@Service
public class DataService {
    private Connection connection;

    // 1. Constructor
    public DataService() {
        System.out.println("Constructor called");
    }

    // 2. @PostConstruct - After dependency injection
    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct - Opening connection");
        connection = openConnection();
    }

    // Business methods
    public void doWork() {
        // Use connection
    }

    // 3. @PreDestroy - Before bean destruction
    @PreDestroy
    public void cleanup() {
        System.out.println("@PreDestroy - Closing connection");
        if (connection != null) {
            connection.close();
        }
    }
}

// Alternative: Implement interfaces
@Service
public class DataService implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() {
        // Called after properties set
        System.out.println("InitializingBean.afterPropertiesSet()");
    }

    @Override
    public void destroy() {
        // Called before destruction
        System.out.println("DisposableBean.destroy()");
    }
}

// Alternative: @Bean with methods
@Configuration
public class AppConfig {

    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public DataService dataService() {
        return new DataService();
    }
}
```

---

## 6. Aspect-Oriented Programming (AOP)

### Why It Matters
- Modularize cross-cutting concerns
- Avoid code duplication
- Separate business logic from infrastructure
- Essential for enterprise applications (logging, security, transactions)
- Clean, maintainable code

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| Aspect | Module for cross-cutting concern | Logging aspect |
| Join Point | Point in execution | Method call |
| Pointcut | Where to apply advice | All service methods |
| Advice | What to do | Before, After, Around |
| Weaving | Applying aspects | Runtime (Spring AOP) |

### Cross-Cutting Concerns

```
Without AOP - Code duplication:

public void createUser() {
    logger.info("Starting createUser");
    startTransaction();
    checkSecurity();
    try {
        // Business logic
    } catch (Exception e) {
        logger.error("Error", e);
        rollbackTransaction();
    }
    commitTransaction();
    logger.info("Finished createUser");
}

With AOP - Separation of concerns:

@Transactional
@Secured("ROLE_ADMIN")
public void createUser() {
    // Only business logic
    // Logging, transactions, security handled by aspects
}
```

### AOP Terminology Visualized

```
Join Point: Any point in program execution
    ↓
Pointcut: Selected join points
    ↓
Advice: Action taken at pointcut
    ↓
Aspect: Pointcut + Advice
```

### Maven Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### Advice Types

```java
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // @Before - Execute before method
    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Calling method: {}", joinPoint.getSignature().getName());
    }

    // @After - Execute after method (always)
    @After("execution(* com.example.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method finished: {}", joinPoint.getSignature().getName());
    }

    // @AfterReturning - Execute after successful return
    @AfterReturning(
        pointcut = "execution(* com.example.service.*.*(..))",
        returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method {} returned: {}",
            joinPoint.getSignature().getName(), result);
    }

    // @AfterThrowing - Execute after exception
    @AfterThrowing(
        pointcut = "execution(* com.example.service.*.*(..))",
        throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Method {} threw exception: {}",
            joinPoint.getSignature().getName(), error.getMessage());
    }

    // @Around - Most powerful, wraps method execution
    @Around("execution(* com.example.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        logger.info("Starting method: {}", joinPoint.getSignature().getName());

        try {
            // Proceed with method execution
            Object result = joinPoint.proceed();

            long duration = System.currentTimeMillis() - startTime;
            logger.info("Method {} completed in {}ms",
                joinPoint.getSignature().getName(), duration);

            return result;

        } catch (Exception e) {
            logger.error("Method {} failed", joinPoint.getSignature().getName(), e);
            throw e;
        }
    }
}
```

### Pointcut Expressions

```java
@Aspect
@Component
public class SecurityAspect {

    // All methods in service package
    @Before("execution(* com.example.service.*.*(..))")
    public void beforeService() { }

    // Specific method signature
    @Before("execution(public User com.example.service.UserService.getUser(int))")
    public void beforeGetUser() { }

    // All methods returning User
    @Before("execution(User *.*(..))")
    public void beforeReturningUser() { }

    // All methods with any number of parameters
    @Before("execution(* com.example.service.*.save(..))")
    public void beforeSave() { }

    // Methods in classes annotated with @Service
    @Before("within(@org.springframework.stereotype.Service *)")
    public void beforeServiceClass() { }

    // Methods annotated with custom annotation
    @Before("@annotation(com.example.Audited)")
    public void beforeAudited() { }

    // Reusable pointcut
    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceMethods() { }

    @Before("serviceMethods()")
    public void beforeServiceMethod() { }

    // Combining pointcuts
    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceLayer() { }

    @Pointcut("execution(* com.example.repository.*.*(..))")
    public void repositoryLayer() { }

    @Before("serviceLayer() || repositoryLayer()")
    public void beforeServiceOrRepository() { }
}
```

### Practical AOP Examples

```java
// Performance Monitoring Aspect
@Aspect
@Component
public class PerformanceMonitoringAspect {

    @Around("execution(* com.example.service.*.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - startTime;

        if (duration > 1000) {
            logger.warn("SLOW METHOD: {} took {}ms",
                joinPoint.getSignature(), duration);
        }

        return result;
    }
}

// Exception Handling Aspect
@Aspect
@Component
public class ExceptionHandlingAspect {

    @AfterThrowing(
        pointcut = "execution(* com.example.service.*.*(..))",
        throwing = "exception")
    public void handleException(JoinPoint joinPoint, Exception exception) {
        // Log exception
        logger.error("Exception in {}: {}",
            joinPoint.getSignature(), exception.getMessage());

        // Send alert
        alertService.sendAlert("Exception occurred: " + exception.getMessage());

        // Record metrics
        metricsService.recordException(joinPoint.getSignature().toString());
    }
}

// Audit Logging Aspect
@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditService auditService;

    @AfterReturning(
        pointcut = "@annotation(audited)",
        returning = "result")
    public void auditMethod(JoinPoint joinPoint, Audited audited, Object result) {
        String username = SecurityContextHolder.getContext()
            .getAuthentication().getName();

        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(audited.action());
        log.setMethod(joinPoint.getSignature().toString());
        log.setTimestamp(LocalDateTime.now());

        auditService.log(log);
    }
}

// Usage with custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Audited {
    String action();
}

@Service
public class UserService {

    @Audited(action = "CREATE_USER")
    public User createUser(User user) {
        // Business logic
        return userRepository.save(user);
    }
}
```

---

## 7. Spring Configuration and Profiles

### Why It Matters
- Different configurations for different environments
- Externalize configuration from code
- Easy deployment across environments
- DevOps and CI/CD best practices

### Key Concepts

```java
// Property sources
@PropertySource("classpath:application.properties")

// Profile-specific configuration
@Profile("dev")
@Profile("prod")

// Conditional beans
@Conditional(CustomCondition.class)
```

### Application Properties

```properties
# application.properties
app.name=MyApplication
app.version=1.0.0

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=dbuser
spring.datasource.password=dbpass

# Logging
logging.level.root=INFO
logging.level.com.example=DEBUG
```

### Using Properties in Code

```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Bean
    public ApplicationInfo applicationInfo() {
        return new ApplicationInfo(appName, appVersion);
    }
}

// Type-safe configuration properties
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private String version;
    private Database database;

    // Getters and setters

    public static class Database {
        private String url;
        private String username;
        private String password;

        // Getters and setters
    }
}
```

### Profile-Based Configuration

```java
// Development configuration
@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public DataSource dataSource() {
        // H2 in-memory database
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
}

// Production configuration
@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public DataSource dataSource() {
        // PostgreSQL database
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://prod-db:5432/mydb");
        return dataSource;
    }
}

// Activate profile
// VM argument: -Dspring.profiles.active=dev
// Environment variable: SPRING_PROFILES_ACTIVE=prod
// application.properties: spring.profiles.active=dev
```

---

## Quick Reference Card

### Common Annotations

```java
// Configuration
@Configuration           // Configuration class
@ComponentScan          // Enable component scanning
@PropertySource         // Load properties file
@Profile               // Profile-specific beans

// Bean definition
@Component             // Generic component
@Service               // Business logic
@Repository            // Data access
@Controller            // Web controller
@Bean                  // Explicit bean definition

// Dependency injection
@Autowired             // Inject dependency
@Qualifier             // Specify bean name
@Primary               // Default bean
@Value                 // Inject property value

// Bean lifecycle
@PostConstruct         // After initialization
@PreDestroy            // Before destruction
@Scope                 // Bean scope

// AOP
@Aspect                // Define aspect
@Before                // Before advice
@After                 // After advice
@Around                // Around advice
@Pointcut              // Reusable pointcut
```

### Creating Spring Application

```java
// Configuration class
@Configuration
@ComponentScan("com.example")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        // Create and configure DataSource
        return dataSource;
    }
}

// Main class
public class Application {
    public static void main(String[] args) {
        ApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

        UserService service = context.getBean(UserService.class);
        service.doSomething();

        ((ConfigurableApplicationContext) context).close();
    }
}
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand Inversion of Control principle
- [ ] Use Dependency Injection effectively
- [ ] Choose appropriate injection method (constructor vs setter)
- [ ] Configure beans with annotations
- [ ] Create Java-based configuration classes
- [ ] Understand bean scopes and lifecycle
- [ ] Use @PostConstruct and @PreDestroy
- [ ] Resolve dependency conflicts with @Qualifier
- [ ] Apply AOP for cross-cutting concerns
- [ ] Create custom aspects with different advice types
- [ ] Write pointcut expressions
- [ ] Use profiles for environment-specific configuration
- [ ] Externalize configuration with properties files
- [ ] Test Spring components effectively

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 15: Spring Boot](../15-spring-boot/) - Modern Spring development
- Practice building layered applications (Controller, Service, Repository)
- Explore Spring Data JPA for database access
- Learn Spring Security for authentication and authorization
- Study Spring MVC for building REST APIs
