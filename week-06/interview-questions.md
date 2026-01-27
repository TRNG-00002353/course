# Week 06 - Interview Questions (38 Students)

This document contains **38 unique question sets** - one for each student. Each set has **5 questions** progressing from simple to complex, covering Spring Framework, Spring Boot, and Spring MVC.

---

## How to Use

1. Assign each student a number (1-38)
2. Ask the 5 questions from their assigned set
3. Questions progress: Q1 (easy) → Q5 (challenging)

---

## Scoring Guide

| Level | Points | Criteria |
|-------|--------|----------|
| Q1 | 10 | Basic recall |
| Q2 | 15 | Understanding |
| Q3 | 20 | Application |
| Q4 | 25 | Analysis |
| Q5 | 30 | Problem-solving |
| **Total** | **100** | |

---

# Student Question Sets

---

## Student 1

**Q1.** What does IoC stand for in Spring?
> *Answer: Inversion of Control*

**Q2.** What is the default scope of a Spring bean?
> *Answer: Singleton - one instance per Spring container*

**Q3.** Write the annotation to inject a dependency automatically.
> *Answer: @Autowired*

**Q4.** Why is Constructor Injection preferred over Field Injection?
> *Answer: Enables immutability (final fields), makes dependencies explicit, easier to test without Spring context, fails fast if dependencies missing*

**Q5.** How would you resolve a circular dependency between two beans using constructor injection?
> *Answer: Refactor to eliminate cycle, use @Lazy on one dependency, use setter injection for one, extract common code to third bean*

---

## Student 2

**Q1.** What annotation marks a class as a Spring Boot application?
> *Answer: @SpringBootApplication*

**Q2.** What three annotations does @SpringBootApplication combine?
> *Answer: @Configuration, @EnableAutoConfiguration, @ComponentScan*

**Q3.** How do you change the server port in Spring Boot?
> *Answer: server.port=8081 in application.properties*

**Q4.** Explain the difference between @Component, @Service, and @Repository.
> *Answer: All are stereotype annotations. @Component is generic, @Service for business logic (semantic only), @Repository for data access (provides exception translation)*

**Q5.** Design a configuration that uses different databases for dev and prod profiles.
> *Answer: Create @Configuration classes with @Profile("dev") and @Profile("prod"), each defining their own DataSource bean*

---

## Student 3

**Q1.** What does @RestController do?
> *Answer: Marks a class as a REST API controller that returns data (JSON) directly*

**Q2.** What is the difference between @Controller and @RestController?
> *Answer: @RestController includes @ResponseBody automatically, returns data; @Controller returns view names*

**Q3.** Write a GET endpoint that returns a user by ID from the path `/users/123`.
> *Answer: @GetMapping("/users/{id}") public User getUser(@PathVariable Long id)*

**Q4.** What is ResponseEntity and when should you use it?
> *Answer: Represents entire HTTP response (status, headers, body). Use when you need control over status codes or headers*

**Q5.** Implement a complete CRUD controller for a Product resource with proper status codes.
> *Answer: GET(200), GET/{id}(200/404), POST(201 with location), PUT(200/404), DELETE(204/404)*

---

## Student 4

**Q1.** What is Dependency Injection?
> *Answer: A technique where objects receive their dependencies from external source rather than creating them*

**Q2.** Name the three types of Dependency Injection in Spring.
> *Answer: Constructor Injection, Setter Injection, Field Injection*

**Q3.** How do you make a dependency optional in Spring?
> *Answer: @Autowired(required = false) or use Optional<T> as parameter type*

**Q4.** When would you use @Qualifier annotation?
> *Answer: When multiple beans of same type exist and you need to specify which one to inject*

**Q5.** A service class has 8 dependencies injected via constructor. What does this indicate and how would you refactor?
> *Answer: Indicates class has too many responsibilities (violates SRP). Refactor by splitting into smaller classes, grouping related dependencies*

---

## Student 5

**Q1.** What is a Spring Boot Starter?
> *Answer: A pre-packaged set of dependencies for specific functionality*

**Q2.** What starter is needed for building REST APIs?
> *Answer: spring-boot-starter-web*

**Q3.** What dependencies does spring-boot-starter-web include?
> *Answer: Spring MVC, embedded Tomcat, Jackson (JSON), Hibernate Validator*

**Q4.** How do you switch from Tomcat to Jetty as embedded server?
> *Answer: Exclude spring-boot-starter-tomcat from web starter, add spring-boot-starter-jetty*

**Q5.** Design the Maven dependencies for a production REST API with database, security, and monitoring.
> *Answer: starter-web, starter-data-jpa, starter-security, starter-actuator, starter-validation, database driver, starter-test*

---

## Student 6

**Q1.** What annotation extracts values from URL path?
> *Answer: @PathVariable*

**Q2.** What annotation extracts query parameters?
> *Answer: @RequestParam*

**Q3.** Write a method that accepts page and size query parameters with defaults.
> *Answer: @GetMapping public Page<User> list(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size)*

**Q4.** How do you accept multiple values for the same query parameter?
> *Answer: Use List<String> as parameter type: @RequestParam List<String> statuses*

**Q5.** Create a search endpoint with multiple optional filters, pagination, and sorting.
> *Answer: Multiple @RequestParam(required=false), Pageable parameter, build dynamic query based on provided filters*

---

## Student 7

**Q1.** What does @Valid annotation do?
> *Answer: Triggers validation on the annotated parameter*

**Q2.** What is the difference between @NotNull, @NotEmpty, and @NotBlank?
> *Answer: @NotNull - not null; @NotEmpty - not null/empty; @NotBlank - not null/empty/whitespace*

**Q3.** Write a DTO with validation for name (required) and email (valid format).
> *Answer: @NotBlank private String name; @Email @NotBlank private String email;*

**Q4.** What exception is thrown when @Valid validation fails? How do you handle it?
> *Answer: MethodArgumentNotValidException. Handle with @ExceptionHandler in @ControllerAdvice*

**Q5.** Implement a custom validation annotation to check if username is unique in database.
> *Answer: Create @UniqueUsername annotation with @Constraint, implement ConstraintValidator that queries repository*

---

## Student 8

**Q1.** What is AOP in Spring?
> *Answer: Aspect-Oriented Programming - a paradigm for modularizing cross-cutting concerns*

**Q2.** Name three cross-cutting concerns.
> *Answer: Logging, Security, Transaction management, Caching, Error handling*

**Q3.** What annotation marks a class as an Aspect?
> *Answer: @Aspect (along with @Component)*

**Q4.** Explain the difference between @Before, @After, and @Around advice.
> *Answer: @Before runs before method; @After runs after (always); @Around wraps method and can control execution*

**Q5.** Implement a performance monitoring aspect that logs slow methods (>1 second).
> *Answer: @Around advice that measures time with System.currentTimeMillis(), logs warning if duration > 1000ms*

---

## Student 9

**Q1.** What is the default configuration file name in Spring Boot?
> *Answer: application.properties (or application.yml)*

**Q2.** How do you access a property value in code?
> *Answer: @Value("${property.name}")*

**Q3.** How do you provide a default value in @Value?
> *Answer: @Value("${property.name:defaultValue}")*

**Q4.** What is the property precedence order in Spring Boot?
> *Answer: Command line args > Environment variables > Profile-specific properties > application.properties*

**Q5.** Design a configuration strategy for dev, staging, and production environments.
> *Answer: application.properties (common), application-dev.properties, application-staging.properties, application-prod.properties with environment-specific settings*

---

## Student 10

**Q1.** What is @ControllerAdvice used for?
> *Answer: Defines global exception handlers that apply across all controllers*

**Q2.** What annotation handles specific exceptions?
> *Answer: @ExceptionHandler*

**Q3.** Write a simple exception handler for ResourceNotFoundException returning 404.
> *Answer: @ExceptionHandler(ResourceNotFoundException.class) public ResponseEntity<?> handle(ResourceNotFoundException ex) { return ResponseEntity.notFound().build(); }*

**Q4.** What is the difference between @ControllerAdvice and @RestControllerAdvice?
> *Answer: @RestControllerAdvice = @ControllerAdvice + @ResponseBody (returns JSON instead of view)*

**Q5.** Implement a comprehensive exception handling strategy with structured error responses.
> *Answer: Create ApiError class, handle validation errors, business exceptions, and generic exceptions with proper HTTP status codes*

---

## Student 11

**Q1.** What is the purpose of @Primary annotation?
> *Answer: Marks a bean as default when multiple beans of same type exist*

**Q2.** When would you use @Primary vs @Qualifier?
> *Answer: @Primary for global default, @Qualifier for explicit selection at injection point*

**Q3.** Write code showing two implementations with @Primary on one.
> *Answer: @Component @Primary class EmailNotification implements Notification {} @Component class SmsNotification implements Notification {}*

**Q4.** How does Spring resolve which bean to inject when multiple exist?
> *Answer: By type → @Qualifier → @Primary → bean name matching parameter name → exception if ambiguous*

**Q5.** Design a notification system where different services need different notification implementations.
> *Answer: Use @Qualifier for specific needs, @Primary for default, List<NotificationService> for broadcast*

---

## Student 12

**Q1.** What are Spring profiles used for?
> *Answer: Different configurations for different environments (dev, test, prod)*

**Q2.** How do you activate a profile?
> *Answer: spring.profiles.active=dev in properties, or --spring.profiles.active=dev command line*

**Q3.** How do you create a bean only for development profile?
> *Answer: @Configuration @Profile("dev") class DevConfig { @Bean... }*

**Q4.** What does @Profile("!prod") mean?
> *Answer: Bean is active for all profiles EXCEPT prod*

**Q5.** Design a multi-environment setup with profile-specific beans and property inheritance.
> *Answer: Base configuration active always, profile-specific configs with @Profile, sensitive values from environment variables*

---

## Student 13

**Q1.** What is @PostConstruct used for?
> *Answer: Marks a method to run after dependency injection completes*

**Q2.** What is @PreDestroy used for?
> *Answer: Marks a method to run before bean destruction*

**Q3.** List the bean lifecycle events in order.
> *Answer: Constructor → Dependency Injection → @PostConstruct → Ready → @PreDestroy → Destroyed*

**Q4.** What are alternative ways to define init/destroy methods?
> *Answer: InitializingBean/DisposableBean interfaces, @Bean(initMethod="", destroyMethod="")*

**Q5.** Implement a connection pool bean that initializes on startup and cleans up on shutdown.
> *Answer: Use @PostConstruct to create connections, @PreDestroy to close them, handle exceptions properly*

---

## Student 14

**Q1.** What is auto-configuration in Spring Boot?
> *Answer: Automatically configures beans based on dependencies on classpath*

**Q2.** How do you see what was auto-configured?
> *Answer: Set debug=true in properties or run with --debug flag*

**Q3.** How do you disable a specific auto-configuration?
> *Answer: @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})*

**Q4.** What conditional annotations does auto-configuration use?
> *Answer: @ConditionalOnClass, @ConditionalOnMissingBean, @ConditionalOnProperty*

**Q5.** Create a custom auto-configuration for a caching library.
> *Answer: @Configuration with @ConditionalOnClass, @ConditionalOnMissingBean, register in META-INF/spring.factories*

---

## Student 15

**Q1.** What HTTP method is used to create a resource?
> *Answer: POST*

**Q2.** What is the difference between PUT and PATCH?
> *Answer: PUT replaces entire resource, PATCH updates specific fields*

**Q3.** Which HTTP methods are idempotent?
> *Answer: GET, PUT, DELETE, HEAD, OPTIONS*

**Q4.** When should you use POST vs PUT for creating resources?
> *Answer: POST when server generates ID, PUT when client specifies ID*

**Q5.** Implement both PUT (full update) and PATCH (partial update) for a User resource.
> *Answer: PUT replaces all fields, PATCH accepts Map<String,Object> and updates only provided fields*

---

## Student 16

**Q1.** What is @RequestBody used for?
> *Answer: Binds HTTP request body (JSON) to a Java object*

**Q2.** How does Spring convert JSON to Java objects?
> *Answer: Using HttpMessageConverter (Jackson's MappingJackson2HttpMessageConverter)*

**Q3.** Write a POST endpoint that accepts a User object.
> *Answer: @PostMapping public User create(@RequestBody User user) { return service.save(user); }*

**Q4.** How do you handle different JSON field names than Java field names?
> *Answer: Use @JsonProperty("json_name") on fields*

**Q5.** Implement a controller that accepts polymorphic JSON (different types based on a field).
> *Answer: Use @JsonTypeInfo and @JsonSubTypes on base class to handle inheritance*

---

## Student 17

**Q1.** What is Spring Boot Actuator?
> *Answer: Provides production-ready monitoring features*

**Q2.** Name three Actuator endpoints.
> *Answer: /actuator/health, /actuator/info, /actuator/metrics*

**Q3.** How do you expose Actuator endpoints?
> *Answer: management.endpoints.web.exposure.include=health,info,metrics*

**Q4.** How do you create a custom health indicator?
> *Answer: Implement HealthIndicator interface, return Health.up() or Health.down() with details*

**Q5.** Configure Actuator for production with security and custom health checks.
> *Answer: Separate management port, limit exposed endpoints, require authentication, custom health indicators*

---

## Student 18

**Q1.** What is @ConfigurationProperties used for?
> *Answer: Binds external configuration to Java object in type-safe way*

**Q2.** How is it different from @Value?
> *Answer: @ConfigurationProperties binds groups of properties, supports validation, relaxed binding*

**Q3.** Write a properties class for mail settings (host, port, username).
> *Answer: @ConfigurationProperties(prefix="app.mail") class MailProperties { String host; int port; String username; }*

**Q4.** How do you add validation to @ConfigurationProperties?
> *Answer: Add @Validated on class, use @NotBlank, @Min, @Max on fields*

**Q5.** Create hierarchical configuration for multiple data sources.
> *Answer: Use Map<String, DataSourceProperties> to configure multiple named datasources*

---

## Student 19

**Q1.** What is the front controller in Spring MVC?
> *Answer: DispatcherServlet*

**Q2.** Describe the request flow in Spring MVC.
> *Answer: Request → DispatcherServlet → HandlerMapping → Controller → Response*

**Q3.** What components process a REST request?
> *Answer: DispatcherServlet, HandlerMapping, HandlerAdapter, HttpMessageConverter, Controller*

**Q4.** What is HandlerMapping responsible for?
> *Answer: Mapping URLs to controller methods*

**Q5.** How would you customize the request processing pipeline?
> *Answer: Implement WebMvcConfigurer, add interceptors, custom message converters, CORS configuration*

---

## Student 20

**Q1.** What is @ComponentScan used for?
> *Answer: Automatically discovers and registers Spring components*

**Q2.** What packages does @SpringBootApplication scan?
> *Answer: The package of the main class and all sub-packages*

**Q3.** How do you scan additional packages?
> *Answer: @ComponentScan(basePackages = {"com.example.app", "com.example.common"})*

**Q4.** What stereotype annotations does @ComponentScan detect?
> *Answer: @Component, @Service, @Repository, @Controller, @Configuration*

**Q5.** Your app needs beans from multiple modules not under main package. How do you configure this?
> *Answer: Use @ComponentScan for components, @EntityScan for entities, @EnableJpaRepositories for repos*

---

## Student 21

**Q1.** What is Bean Scope?
> *Answer: Determines how many instances are created and their lifecycle*

**Q2.** Name all Spring bean scopes.
> *Answer: singleton, prototype, request, session, application*

**Q3.** How do you define a prototype-scoped bean?
> *Answer: @Scope("prototype") on the component class*

**Q4.** What happens when a singleton depends on a prototype bean?
> *Answer: Prototype is injected once; same instance used. Use ObjectFactory or @Lookup for new instances*

**Q5.** Design a shopping cart system with session-scoped cart and singleton service.
> *Answer: @Scope(SCOPE_SESSION, proxyMode=TARGET_CLASS) on cart, inject proxied cart into singleton service*

---

## Student 22

**Q1.** What is a Pointcut in AOP?
> *Answer: An expression that selects join points where advice should apply*

**Q2.** What does this match: `execution(* com.example.service.*.*(..))`?
> *Answer: All methods in all classes in com.example.service package*

**Q3.** Write a pointcut for all public methods.
> *Answer: @Pointcut("execution(public * *(..))")*

**Q4.** Explain the difference between execution() and within() pointcuts.
> *Answer: execution() matches method signatures, within() matches all methods in matching types*

**Q5.** Create a reusable pointcut library for a layered application.
> *Answer: Define pointcuts for controller/service/repository layers, combine with && and ||*

---

## Student 23

**Q1.** What does spring-boot-devtools provide?
> *Answer: Automatic restart, LiveReload, development conveniences*

**Q2.** How do you add DevTools to a project?
> *Answer: spring-boot-devtools dependency with optional=true*

**Q3.** How do you exclude files from triggering restart?
> *Answer: spring.devtools.restart.exclude=static/**,templates/**

**Q4.** Why is optional=true important for DevTools?
> *Answer: Prevents DevTools from being included in production builds*

**Q5.** Why shouldn't DevTools be used in production? How does Spring Boot prevent this?
> *Answer: Performance overhead, security risk. Prevented by: disabled in packaged JAR, optional=true excludes from deps*

---

## Student 24

**Q1.** What is ApplicationContext?
> *Answer: Central interface for Spring IoC container*

**Q2.** What is the difference between BeanFactory and ApplicationContext?
> *Answer: ApplicationContext adds events, i18n, AOP support, automatic BeanPostProcessor registration*

**Q3.** How do you get ApplicationContext in a bean?
> *Answer: Implement ApplicationContextAware or @Autowired ApplicationContext*

**Q4.** When would you use ApplicationContext.getBean()?
> *Answer: Dynamic lookup, getting prototype from singleton, plugin systems (generally avoid)*

**Q5.** Design a plugin system that discovers and loads plugins dynamically.
> *Answer: Use getBeansOfType(Plugin.class), store in map, execute by name*

---

## Student 25

**Q1.** How do you run a Spring Boot application?
> *Answer: java -jar application.jar*

**Q2.** What does spring-boot-maven-plugin do?
> *Answer: Creates executable JAR/WAR files*

**Q3.** How do you pass arguments when running the application?
> *Answer: java -jar app.jar --server.port=9000 --spring.profiles.active=prod*

**Q4.** How do you build a Docker image for Spring Boot?
> *Answer: Multi-stage Dockerfile or mvn spring-boot:build-image*

**Q5.** Configure production deployment with health checks, graceful shutdown, and resource limits.
> *Answer: Actuator probes, server.shutdown=graceful, JVM memory settings, Kubernetes probes*

---

## Student 26

**Q1.** What annotation maps HTTP GET requests?
> *Answer: @GetMapping*

**Q2.** Write shortcut annotations for all HTTP methods.
> *Answer: @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping*

**Q3.** How do you set the base URL for a controller?
> *Answer: @RequestMapping("/api/users") on the class*

**Q4.** How do you map requests by content type?
> *Answer: @PostMapping(consumes = "application/json", produces = "application/json")*

**Q5.** Design URL mappings for nested resources: users → orders → items.
> *Answer: /users/{userId}/orders, /users/{userId}/orders/{orderId}/items, also direct /orders/{orderId}*

---

## Student 27

**Q1.** What does ResponseEntity.ok() return?
> *Answer: 200 OK status*

**Q2.** What does ResponseEntity.created(uri) return?
> *Answer: 201 Created status with Location header*

**Q3.** How do you return 404 Not Found?
> *Answer: ResponseEntity.notFound().build()*

**Q4.** List common ResponseEntity factory methods.
> *Answer: ok(), created(), noContent(), badRequest(), notFound(), status()*

**Q5.** Implement a file download endpoint with proper headers.
> *Answer: Return ResponseEntity<Resource> with Content-Disposition, Content-Type, Content-Length headers*

---

## Student 28

**Q1.** What is @RequestMapping used for?
> *Answer: Maps HTTP requests to handler methods*

**Q2.** Can @RequestMapping be used on both class and method?
> *Answer: Yes, class-level for base path, method-level for specific endpoints*

**Q3.** How do you restrict @RequestMapping to specific HTTP method?
> *Answer: @RequestMapping(method = RequestMethod.GET) or use @GetMapping*

**Q4.** How do you map multiple URLs to one method?
> *Answer: @GetMapping({"/users", "/members", "/people"})*

**Q5.** Create a versioned API with both URL and header-based versioning.
> *Answer: @RequestMapping("/api/v1") for URL, @RequestMapping(headers="X-API-Version=1") for header*

---

## Student 29

**Q1.** What annotation is used for the data access layer?
> *Answer: @Repository*

**Q2.** What special feature does @Repository provide?
> *Answer: Exception translation (converts DB exceptions to DataAccessException)*

**Q3.** Why is exception translation useful?
> *Answer: Consistent exception hierarchy regardless of persistence technology*

**Q4.** What is the relationship between @Repository and Spring Data?
> *Answer: Spring Data repositories are automatically @Repository; interface extends JpaRepository*

**Q5.** Implement a custom repository with exception handling that translates to business exceptions.
> *Answer: Catch DataAccessException, translate to specific business exceptions like DuplicateEntityException*

---

## Student 30

**Q1.** What is YAML used for in Spring Boot?
> *Answer: Configuration file format alternative to .properties*

**Q2.** Convert `server.port=8080` to YAML.
> *Answer: server:\n  port: 8080*

**Q3.** What are advantages of YAML over properties?
> *Answer: Hierarchical structure, less repetition, supports lists/maps natively*

**Q4.** How do you define multiple profiles in one YAML file?
> *Answer: Separate with --- and use spring.config.activate.on-profile*

**Q5.** Write YAML with multiple profiles, lists, and nested objects.
> *Answer: Base config, --- separator, profile activation, list items with -, nested objects with indentation*

---

## Student 31

**Q1.** What does @Autowired do?
> *Answer: Tells Spring to automatically inject a matching bean*

**Q2.** Is @Autowired required on constructors?
> *Answer: No, since Spring 4.3 it's optional with single constructor*

**Q3.** What happens if no matching bean is found?
> *Answer: NoSuchBeanDefinitionException unless required=false*

**Q4.** How do you inject all beans of a type?
> *Answer: @Autowired List<MyInterface> beans*

**Q5.** You have multiple PaymentService implementations. How do you inject primary one and also all of them?
> *Answer: @Qualifier for specific, List<PaymentService> for all, @Primary on default*

---

## Student 32

**Q1.** What is @Size annotation used for?
> *Answer: Validates string length or collection size*

**Q2.** Write validation for password (min 8 characters).
> *Answer: @Size(min = 8, message = "Password must be at least 8 characters")*

**Q3.** How do you validate nested objects?
> *Answer: Add @Valid on the nested object field*

**Q4.** List 5 common validation annotations.
> *Answer: @NotBlank, @Email, @Size, @Min/@Max, @Pattern*

**Q5.** Implement cross-field validation (confirm password must match password).
> *Answer: Class-level annotation with ConstraintValidator that compares both fields*

---

## Student 33

**Q1.** What is the default embedded server in Spring Boot?
> *Answer: Tomcat*

**Q2.** What other embedded servers does Spring Boot support?
> *Answer: Jetty, Undertow, Netty (for WebFlux)*

**Q3.** How do you configure SSL/HTTPS?
> *Answer: server.ssl.key-store, server.ssl.key-store-password, server.ssl.key-store-type*

**Q4.** How do you configure connection limits?
> *Answer: server.tomcat.max-threads, server.tomcat.max-connections, server.tomcat.accept-count*

**Q5.** Configure a production-ready server with SSL, compression, and timeouts.
> *Answer: SSL config, compression enabled with mime-types, connection limits, graceful shutdown*

---

## Student 34

**Q1.** What does @Transactional do?
> *Answer: Manages transaction boundaries declaratively*

**Q2.** Where can @Transactional be placed?
> *Answer: On class (all methods) or specific methods*

**Q3.** What is the default rollback behavior?
> *Answer: Rollback on RuntimeException and Error, not on checked exceptions*

**Q4.** How do you rollback on a checked exception?
> *Answer: @Transactional(rollbackFor = MyCheckedException.class)*

**Q5.** Design transaction management for a service that calls multiple repositories and external services.
> *Answer: Service method @Transactional, handle external service failures, consider compensation patterns*

---

## Student 35

**Q1.** What is a Join Point in AOP?
> *Answer: A point in program execution where aspect can be applied*

**Q2.** What types of join points does Spring AOP support?
> *Answer: Method execution only (not field access like AspectJ)*

**Q3.** How do you access method arguments in advice?
> *Answer: JoinPoint.getArgs() or bind with args() in pointcut*

**Q4.** What is ProceedingJoinPoint?
> *Answer: Used in @Around advice to call the actual method with proceed()*

**Q5.** Implement an audit aspect that logs method calls with arguments and results.
> *Answer: @Around advice, log method signature, arguments, call proceed(), log result or exception*

---

## Student 36

**Q1.** How do you enable validation in Spring Boot?
> *Answer: Add spring-boot-starter-validation dependency*

**Q2.** What is @Valid used for?
> *Answer: Triggers validation on @RequestBody parameters*

**Q3.** How do you return validation errors to client?
> *Answer: @ExceptionHandler for MethodArgumentNotValidException, extract FieldErrors*

**Q4.** What is the difference between @Valid and @Validated?
> *Answer: @Valid is JSR-303, @Validated is Spring's (supports validation groups)*

**Q5.** Implement validation groups for create vs update operations.
> *Answer: Define marker interfaces, use @Validated(CreateGroup.class), assign groups to constraints*

---

## Student 37

**Q1.** What is the purpose of @Bean annotation?
> *Answer: Defines a bean in @Configuration class*

**Q2.** How do you specify init and destroy methods on @Bean?
> *Answer: @Bean(initMethod = "init", destroyMethod = "cleanup")*

**Q3.** How do you inject dependencies into @Bean methods?
> *Answer: Add them as method parameters*

**Q4.** What happens when you call one @Bean method from another?
> *Answer: Spring intercepts and returns existing bean (singleton behavior)*

**Q5.** Create configuration with conditional beans based on properties and profiles.
> *Answer: @ConditionalOnProperty, @Profile, @ConditionalOnMissingBean combinations*

---

## Student 38

**Q1.** What HTTP status code indicates resource created?
> *Answer: 201 Created*

**Q2.** What HTTP status code indicates validation error?
> *Answer: 400 Bad Request*

**Q3.** What is the difference between 401 and 403?
> *Answer: 401 Unauthorized (not authenticated), 403 Forbidden (authenticated but no permission)*

**Q4.** What HTTP status should a DELETE return on success?
> *Answer: 204 No Content (or 200 OK if returning deleted entity)*

**Q5.** Design an error response structure that includes status, message, timestamp, path, and field errors.
> *Answer: ApiError class with all fields, @RestControllerAdvice to build consistent responses*

---

# Quick Reference - Topics per Student

| Student | Topics Covered |
|---------|----------------|
| 1 | IoC, Bean Scope, DI, Constructor Injection |
| 2 | @SpringBootApplication, Stereotypes, Profiles |
| 3 | @RestController, @PathVariable, ResponseEntity |
| 4 | DI Types, @Qualifier, Class Design |
| 5 | Starters, Dependencies |
| 6 | @PathVariable, @RequestParam, Search APIs |
| 7 | Validation, @Valid, Custom Validators |
| 8 | AOP, Aspects, Advice Types |
| 9 | Properties, @Value, Configuration Strategy |
| 10 | Exception Handling, @ControllerAdvice |
| 11 | @Primary, @Qualifier, Bean Resolution |
| 12 | Profiles, Environment Configuration |
| 13 | Bean Lifecycle, @PostConstruct, @PreDestroy |
| 14 | Auto-Configuration, Conditionals |
| 15 | HTTP Methods, PUT vs PATCH |
| 16 | @RequestBody, JSON Handling |
| 17 | Actuator, Health Checks |
| 18 | @ConfigurationProperties |
| 19 | MVC Architecture, DispatcherServlet |
| 20 | @ComponentScan, Package Structure |
| 21 | Bean Scopes, Prototype |
| 22 | AOP Pointcuts |
| 23 | DevTools |
| 24 | ApplicationContext |
| 25 | Building, Deployment, Docker |
| 26 | Request Mapping, URL Design |
| 27 | ResponseEntity, HTTP Status |
| 28 | @RequestMapping, API Versioning |
| 29 | @Repository, Exception Translation |
| 30 | YAML Configuration |
| 31 | @Autowired, Bean Injection |
| 32 | Validation Annotations |
| 33 | Embedded Server, SSL |
| 34 | @Transactional |
| 35 | AOP Join Points |
| 36 | Validation Groups |
| 37 | @Bean, Configuration Classes |
| 38 | HTTP Status Codes, Error Responses |

---

*Total: 38 students × 5 questions = 190 unique questions*
