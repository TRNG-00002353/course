# Week 06 - MCQ Answer Key

This document contains answers and explanations for all 80 questions in `mcq.md`.

---

## Answer Distribution

| Option | Count | Percentage |
|--------|-------|------------|
| A | 20 | 25% |
| B | 20 | 25% |
| C | 20 | 25% |
| D | 20 | 25% |

---

## Spring Framework (Questions 1-30)

### Question 1
**Answer: B**

**IoC** stands for **Inversion of Control**. It's a design principle where the control of object creation and lifecycle is transferred from the application to a container or framework.

---

### Question 2
**Answer: C**

Dependency Injection implements the **Inversion of Control** principle. Instead of objects creating their dependencies, the container injects them.

---

### Question 3
**Answer: C**

The primary benefit of Dependency Injection is **loose coupling between components**. Classes depend on abstractions (interfaces) rather than concrete implementations, making them easier to test and maintain.

---

### Question 4
**Answer: B**

`@Component` marks a class as a Spring-managed bean. `@Bean` is used on methods within `@Configuration` classes, not on class declarations.

---

### Question 5
**Answer: D**

The default scope of a Spring bean is **singleton** - only one instance is created per Spring container and shared across all requests.

---

### Question 6
**Answer: D**

**Constructor Injection** is recommended by Spring because it enables immutability (final fields), makes dependencies explicit, and ensures the object is fully initialized when created.

---

### Question 7
**Answer: B**

`@Autowired` tells Spring to **automatically inject dependencies** into the annotated field, constructor, or setter method.

---

### Question 8
**Answer: C**

`@Service` is the stereotype annotation used for the service/business logic layer. `@Repository` is for data access, `@Controller` is for web controllers.

---

### Question 9
**Answer: A**

`@Qualifier` is used to **specify which bean to inject when multiple candidates exist** of the same type. It works alongside `@Autowired`.

---

### Question 10
**Answer: C**

`ApplicationContext` represents the Spring IoC container. It's the central interface for providing configuration to an application.

---

### Question 11
**Answer: A**

`@Primary` marks a bean as the **default when multiple candidates exist**. When no `@Qualifier` is specified, the `@Primary` bean is chosen.

---

### Question 12
**Answer: D**

`@PostConstruct` is called **after dependency injection** is complete. It's used for initialization logic that depends on injected dependencies.

---

### Question 13
**Answer: B**

`@PreDestroy` marks a method to run **before bean destruction**. It's used for cleanup operations like closing connections.

---

### Question 14
**Answer: C**

The **request** scope creates a new bean instance for each HTTP request. It's only available in web-aware ApplicationContext.

---

### Question 15
**Answer: C**

Prototype scope means a **new instance is created every time the bean is requested** from the container, unlike singleton which reuses the same instance.

---

### Question 16
**Answer: B**

AOP stands for **Aspect Oriented Programming**. It's a programming paradigm for modularizing cross-cutting concerns like logging and security.

---

### Question 17
**Answer: C**

**Business logic** is NOT a cross-cutting concern. Cross-cutting concerns are functionalities that span multiple layers like logging, security, and transactions.

---

### Question 18
**Answer: B**

An **Aspect** is a module that **encapsulates cross-cutting concerns**. It contains pointcuts (where) and advice (what to do).

---

### Question 19
**Answer: D**

A **Join Point** is a **point in program execution where an aspect can be applied**, such as method calls, field access, or object instantiation.

---

### Question 20
**Answer: A**

A **Pointcut** is an **expression that selects join points**. It defines WHERE the advice should be applied.

---

### Question 21
**Answer: C**

`@Before` advice runs **before the target method** executes. It cannot prevent the method from executing (unlike `@Around`).

---

### Question 22
**Answer: C**

`@Around` advice can **prevent the target method from executing** by not calling `joinPoint.proceed()`. It wraps the entire method execution.

---

### Question 23
**Answer: B**

`@Aspect` marks a class as an Aspect. The class must also be a Spring bean (typically via `@Component`).

---

### Question 24
**Answer: D**

`@AfterThrowing` advice **runs after the method throws an exception**. It can access the thrown exception but cannot suppress it.

---

### Question 25
**Answer: A**

`execution(* com.example.service.*.*(..))` matches all methods in classes within the service package. The pattern is: `execution(return-type package.class.method(params))`.

---

### Question 26
**Answer: B**

`@ComponentScan` tells Spring to **automatically discover Spring components** (classes annotated with `@Component`, `@Service`, `@Repository`, `@Controller`).

---

### Question 27
**Answer: C**

`@Repository` is used for the **data access layer**. It marks a class as a DAO (Data Access Object) and enables exception translation.

---

### Question 28
**Answer: B**

`@Repository` provides **exception translation** - it translates database-specific exceptions into Spring's `DataAccessException` hierarchy.

---

### Question 29
**Answer: B**

`@Value("${key}")` injects a value from properties files. The `${}` syntax is for property placeholders.

---

### Question 30
**Answer: D**

`ApplicationContext` provides **more enterprise features** than `BeanFactory`, including event publication, internationalization, and AOP support.

---

## Spring Boot (Questions 31-55)

### Question 31
**Answer: C**

`@SpringBootApplication` is used to create a Spring Boot application. It's a convenience annotation that combines three other annotations.

---

### Question 32
**Answer: B**

`@SpringBootApplication` combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan` into a single annotation.

---

### Question 33
**Answer: A**

Spring Boot auto-configuration's main purpose is to **configure beans automatically based on classpath**. It detects libraries and configures them with sensible defaults.

---

### Question 34
**Answer: C**

**Tomcat** is the embedded server included by default with `spring-boot-starter-web`. You can switch to Jetty or Undertow if needed.

---

### Question 35
**Answer: B**

A Spring Boot Starter is a **pre-packaged set of dependencies** that provides all libraries needed for a specific functionality.

---

### Question 36
**Answer: D**

`spring-boot-starter-web` is needed for building REST APIs. It includes Spring MVC, Jackson, and embedded Tomcat.

---

### Question 37
**Answer: C**

The default application properties file name is `application.properties`. Spring Boot also supports `application.yml`.

---

### Question 38
**Answer: A**

`server.port=8081` changes the server port in Spring Boot. This is configured in `application.properties`.

---

### Question 39
**Answer: B**

`@ConfigurationProperties` enables **type-safe configuration properties** binding. It maps properties to a POJO automatically.

---

### Question 40
**Answer: C**

`spring.profiles.active=dev` activates a Spring profile. This can be set via properties, environment variables, or command line.

---

### Question 41
**Answer: D**

`spring-boot-devtools` provides **automatic restart and LiveReload** during development. Changes trigger automatic application restart.

---

### Question 42
**Answer: B**

`spring.jpa.hibernate.ddl-auto` controls Hibernate's DDL mode. Values include `none`, `validate`, `update`, `create`, `create-drop`.

---

### Question 43
**Answer: A**

`spring-boot-starter-actuator` provides **production-ready monitoring features** including health checks, metrics, and environment info.

---

### Question 44
**Answer: B**

`/actuator/health` is the endpoint that shows application health status (UP, DOWN, etc.).

---

### Question 45
**Answer: A**

`@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)` excludes a specific auto-configuration class.

---

### Question 46
**Answer: A**

`@ConditionalOnClass` applies configuration **only if a specific class exists on the classpath**. It's used extensively in auto-configuration.

---

### Question 47
**Answer: C**

`.yaml` (or `.yml`) can be used instead of `.properties` for Spring Boot configuration. YAML supports hierarchical configuration.

---

### Question 48
**Answer: B**

`@Bean` creates a bean in a `@Configuration` class. It's used for programmatic bean definition.

---

### Question 49
**Answer: D**

The `spring-boot-maven-plugin` **creates executable JAR/WAR files** that can be run with `java -jar`.

---

### Question 50
**Answer: A**

`@Value("${key:default}")` provides a default value using the colon syntax. If `key` is not found, `default` is used.

---

### Question 51
**Answer: C**

`spring.jpa.show-sql=true` enables SQL logging in Spring Boot, showing all SQL statements executed by Hibernate.

---

### Question 52
**Answer: C**

The default context path in Spring Boot is **/ (root)**. You can change it with `server.servlet.context-path`.

---

### Question 53
**Answer: B**

`@Profile("!prod")` means "all profiles except prod". The `!` operator negates the profile condition.

---

### Question 54
**Answer: D**

`spring-boot-starter-test` includes JUnit, Mockito, AssertJ, and other testing libraries for Spring Boot applications.

---

### Question 55
**Answer: B**

`java -jar app.jar` runs a Spring Boot application from the command line. The JAR is an executable fat JAR.

---

## Spring MVC (Questions 56-80)

### Question 56
**Answer: B**

The **DispatcherServlet** is the front controller in Spring MVC. It receives all incoming requests and dispatches them to appropriate handlers.

---

### Question 57
**Answer: B**

`@RestController` creates a REST API controller. It combines `@Controller` and `@ResponseBody`.

---

### Question 58
**Answer: D**

The difference is that `@RestController` **includes @ResponseBody**, meaning all methods return data (JSON) directly instead of view names.

---

### Question 59
**Answer: C**

`@GetMapping` maps HTTP GET requests to a handler method. It's a shortcut for `@RequestMapping(method = RequestMethod.GET)`.

---

### Question 60
**Answer: A**

`@PathVariable` extracts values from the URL path. For example, `/users/{id}` with `@PathVariable Long id`.

---

### Question 61
**Answer: C**

`@RequestParam` extracts query parameters from the URL. For example, `/users?page=1` with `@RequestParam int page`.

---

### Question 62
**Answer: B**

`@RequestBody` binds the JSON request body to an object. Spring uses Jackson to deserialize JSON to Java objects.

---

### Question 63
**Answer: A**

`ResponseEntity` provides full control over HTTP response including status code, headers, and body.

---

### Question 64
**Answer: C**

`ResponseEntity.ok()` returns **200 OK** status code, indicating the request was successful.

---

### Question 65
**Answer: B**

`ResponseEntity.created(uri)` returns **201 Created** status code, indicating a resource was successfully created.

---

### Question 66
**Answer: D**

`@Valid` triggers validation on the request body using JSR-303 Bean Validation annotations.

---

### Question 67
**Answer: C**

`@NotBlank` validates that a string is **not null, not empty, and not just whitespace**. It's more restrictive than `@NotEmpty`.

---

### Question 68
**Answer: A**

`@ControllerAdvice` defines a global exception handler class that handles exceptions across all controllers.

---

### Question 69
**Answer: C**

`@ExceptionHandler` handles specific exceptions in a controller or controller advice. It specifies which exception type to catch.

---

### Question 70
**Answer: B**

`@RestControllerAdvice` is equivalent to `@ControllerAdvice` + `@ResponseBody`. It's used for REST API exception handling.

---

### Question 71
**Answer: D**

`@RequestMapping` sets the base URL for a controller. It can be used at class level and method level.

---

### Question 72
**Answer: C**

**PUT** is used to update a resource completely. It replaces the entire resource with the new representation.

---

### Question 73
**Answer: D**

**PATCH** is used for partial updates. It modifies only the specified fields without replacing the entire resource.

---

### Question 74
**Answer: B**

`ResponseEntity.notFound().build()` returns **404 Not Found** status code, indicating the requested resource doesn't exist.

---

### Question 75
**Answer: A**

`@Email` validates that a string is a valid email format. It checks for basic email structure.

---

### Question 76
**Answer: B**

`@Size(min=x, max=y)` sets minimum and maximum constraints for string length or collection size.

---

### Question 77
**Answer: C**

`MethodArgumentNotValidException` is thrown when `@Valid` validation fails on a `@RequestBody` parameter.

---

### Question 78
**Answer: D**

`@RequestMapping(method = RequestMethod.DELETE)` maps DELETE requests. This is equivalent to `@DeleteMapping`.

---

### Question 79
**Answer: A**

`HttpMessageConverter` converts objects to/from HTTP request/response. Jackson's converter handles JSON serialization.

---

### Question 80
**Answer: B**

`@ResponseStatus` sets the HTTP status code for a response. It can be used on exception classes or handler methods.

---

## Summary

| Topic | Questions | Key Concepts |
|-------|-----------|--------------|
| Spring Framework | 1-30 | IoC, DI, Beans, Scopes, AOP |
| Spring Boot | 31-55 | Auto-config, Starters, Properties, Profiles, Actuator |
| Spring MVC | 56-80 | Controllers, REST, Validation, Exception Handling |

---

*For detailed explanations and code examples, refer to the module documentation in `14-spring-framework/`, `15-spring-boot/`, and `16-spring-mvc/`.*
