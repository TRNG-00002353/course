# Week 06 - Multiple Choice Questions

This document contains 80 multiple choice questions covering the key concepts from Week 06 topics: Spring Framework, Spring Boot, and Spring MVC.

**Topic Distribution:**
- Spring Framework (IoC, DI, Beans, AOP): 30 questions
- Spring Boot (Auto-configuration, Starters, Properties): 25 questions
- Spring MVC (Controllers, REST APIs, Exception Handling): 25 questions

---

**Note:** Answers and explanations are in `mcq-answers.md`

---

## Spring Framework

### Question 1
**[Spring Core]**

What does IoC stand for in Spring Framework?

- A) Input/Output Controller
- B) Inversion of Control
- C) Internal Object Creation
- D) Interface Oriented Configuration

---

### Question 2
**[Spring Core]**

Which principle does Spring's Dependency Injection implement?

- A) Single Responsibility Principle
- B) Open/Closed Principle
- C) Inversion of Control
- D) Liskov Substitution Principle

---

### Question 3
**[Spring Core]**

What is the primary benefit of using Dependency Injection?

- A) Faster application startup
- B) Smaller application size
- C) Loose coupling between components
- D) Automatic database connection

---

### Question 4
**[Spring Core]**

Which annotation marks a class as a Spring-managed bean?

- A) @Bean
- B) @Component
- C) @Inject
- D) @Managed

---

### Question 5
**[Spring Core]**

What is the default scope of a Spring bean?

- A) Prototype
- B) Request
- C) Session
- D) Singleton

---

### Question 6
**[Spring Core]**

Which type of Dependency Injection is recommended by Spring?

- A) Field Injection
- B) Setter Injection
- C) Interface Injection
- D) Constructor Injection

---

### Question 7
**[Spring Core]**

What does the @Autowired annotation do?

- A) Creates a new bean
- B) Automatically injects dependencies
- C) Marks a method as a web endpoint
- D) Enables component scanning

---

### Question 8
**[Spring Core]**

Which stereotype annotation is used for the service layer?

- A) @Repository
- B) @Controller
- C) @Service
- D) @Component

---

### Question 9
**[Spring Core]**

What is the purpose of @Qualifier annotation?

- A) To mark a bean as primary
- B) To specify which bean to inject when multiple exist
- C) To define bean scope
- D) To enable lazy initialization

---

### Question 10
**[Spring Core]**

Which interface represents the Spring IoC container?

- A) BeanContext
- B) SpringContainer
- C) ApplicationContext
- D) ObjectFactory

---

### Question 11
**[Spring Core]**

What does @Primary annotation do?

- A) Marks a bean as the default when multiple candidates exist
- B) Indicates the bean should be created first
- C) Makes the bean immutable
- D) Enables primary key generation

---

### Question 12
**[Spring Core]**

When is @PostConstruct method called?

- A) Before dependency injection
- B) After dependency injection
- C) During application shutdown
- D) When the bean is accessed first time

---

### Question 13
**[Spring Core]**

What is the purpose of @PreDestroy annotation?

- A) Mark method to run before bean creation
- B) Mark method to run before bean destruction
- C) Prevent bean from being destroyed
- D) Mark bean for garbage collection

---

### Question 14
**[Spring Core]**

Which scope creates a new bean instance for each HTTP request?

- A) Singleton
- B) Prototype
- C) Request
- D) Session

---

### Question 15
**[Spring Core]**

What is the prototype scope in Spring?

- A) One instance per container
- B) One instance per HTTP session
- C) New instance every time bean is requested
- D) One instance per HTTP request

---

### Question 16
**[Spring AOP]**

What does AOP stand for?

- A) Application Oriented Programming
- B) Aspect Oriented Programming
- C) Abstract Object Protocol
- D) Advanced Operation Processing

---

### Question 17
**[Spring AOP]**

Which is NOT a cross-cutting concern?

- A) Logging
- B) Security
- C) Business logic
- D) Transaction management

---

### Question 18
**[Spring AOP]**

What is an Aspect in Spring AOP?

- A) A point in program execution
- B) A module that encapsulates cross-cutting concerns
- C) A method parameter
- D) A type of bean scope

---

### Question 19
**[Spring AOP]**

What is a Join Point in AOP?

- A) A configuration file
- B) A type of bean
- C) A point in program execution where aspect can be applied
- D) A database connection point

---

### Question 20
**[Spring AOP]**

What is a Pointcut in AOP?

- A) The action to be taken
- B) An expression that selects join points
- C) A method return value
- D) A bean lifecycle event

---

### Question 21
**[Spring AOP]**

Which advice type runs before the target method?

- A) @After
- B) @Around
- C) @Before
- D) @AfterReturning

---

### Question 22
**[Spring AOP]**

Which advice type can prevent the target method from executing?

- A) @Before
- B) @After
- C) @Around
- D) @AfterReturning

---

### Question 23
**[Spring AOP]**

What annotation marks a class as an Aspect?

- A) @Component
- B) @Aspect
- C) @CrossCut
- D) @Advice

---

### Question 24
**[Spring AOP]**

What does @AfterThrowing advice do?

- A) Throws an exception
- B) Runs after method throws an exception
- C) Prevents exceptions
- D) Converts exceptions

---

### Question 25
**[Spring AOP]**

Which pointcut expression matches all methods in a service package?

- A) execution(* com.example.service.*.*(..))
- B) within(com.example.service)
- C) call(* service.*(..))
- D) method(com.example.service.*)

---

### Question 26
**[Spring Core]**

What is the purpose of @ComponentScan?

- A) To scan for security vulnerabilities
- B) To automatically discover Spring components
- C) To compile component classes
- D) To validate bean configurations

---

### Question 27
**[Spring Core]**

Which annotation is used for the data access layer?

- A) @Service
- B) @Controller
- C) @Repository
- D) @DataAccess

---

### Question 28
**[Spring Core]**

What additional feature does @Repository provide?

- A) Auto-increment support
- B) Exception translation
- C) Connection pooling
- D) Query optimization

---

### Question 29
**[Spring Core]**

How do you inject a value from properties file?

- A) @Property("key")
- B) @Value("${key}")
- C) @Inject("key")
- D) @Config("key")

---

### Question 30
**[Spring Core]**

What is the difference between BeanFactory and ApplicationContext?

- A) No difference
- B) ApplicationContext provides more enterprise features
- C) BeanFactory is newer
- D) ApplicationContext is for web apps only

---

## Spring Boot

### Question 31
**[Spring Boot]**

What annotation is used to create a Spring Boot application?

- A) @SpringApplication
- B) @BootApplication
- C) @SpringBootApplication
- D) @EnableSpringBoot

---

### Question 32
**[Spring Boot]**

What three annotations does @SpringBootApplication combine?

- A) @Component, @Service, @Repository
- B) @Configuration, @EnableAutoConfiguration, @ComponentScan
- C) @Bean, @Autowired, @Qualifier
- D) @Controller, @RequestMapping, @ResponseBody

---

### Question 33
**[Spring Boot]**

What is the main purpose of Spring Boot auto-configuration?

- A) To write code automatically
- B) To configure beans automatically based on classpath
- C) To generate documentation
- D) To compress application size

---

### Question 34
**[Spring Boot]**

Which embedded server is included by default with spring-boot-starter-web?

- A) Jetty
- B) Undertow
- C) Tomcat
- D) Netty

---

### Question 35
**[Spring Boot]**

What is a Spring Boot Starter?

- A) A bootstrap template
- B) A pre-packaged set of dependencies
- C) A code generator
- D) An IDE plugin

---

### Question 36
**[Spring Boot]**

What starter is needed for building REST APIs?

- A) spring-boot-starter-rest
- B) spring-boot-starter-api
- C) spring-boot-starter-web
- D) spring-boot-starter-http

---

### Question 37
**[Spring Boot]**

What is the default application properties file name?

- A) spring.properties
- B) config.properties
- C) application.properties
- D) boot.properties

---

### Question 38
**[Spring Boot]**

How do you change the server port in Spring Boot?

- A) port=8081
- B) server.port=8081
- C) spring.port=8081
- D) http.port=8081

---

### Question 39
**[Spring Boot]**

What annotation enables type-safe configuration properties?

- A) @PropertySource
- B) @ConfigurationProperties
- C) @TypeSafeConfig
- D) @Properties

---

### Question 40
**[Spring Boot]**

How do you activate a Spring profile?

- A) profile.active=dev
- B) spring.profile=dev
- C) spring.profiles.active=dev
- D) active.profile=dev

---

### Question 41
**[Spring Boot]**

What does spring-boot-devtools provide?

- A) Database tools
- B) Automatic restart and LiveReload
- C) Code generation
- D) Security scanning

---

### Question 42
**[Spring Boot]**

Which property controls Hibernate's DDL mode?

- A) spring.hibernate.ddl=auto
- B) spring.jpa.hibernate.ddl-auto
- C) hibernate.ddl.mode
- D) jpa.schema.mode

---

### Question 43
**[Spring Boot]**

What does spring-boot-starter-actuator provide?

- A) Database connections
- B) Production-ready monitoring features
- C) Automatic testing
- D) Code compilation

---

### Question 44
**[Spring Boot]**

Which actuator endpoint shows application health?

- A) /actuator/status
- B) /actuator/health
- C) /actuator/check
- D) /actuator/ping

---

### Question 45
**[Spring Boot]**

How do you exclude a specific auto-configuration class?

- A) @DisableAutoConfiguration
- B) @SpringBootApplication(exclude = ...)
- C) @NoAutoConfig
- D) spring.autoconfigure.disable

---

### Question 46
**[Spring Boot]**

What is the purpose of @ConditionalOnClass?

- A) Apply configuration only if class exists on classpath
- B) Create class conditionally
- C) Check class version
- D) Validate class structure

---

### Question 47
**[Spring Boot]**

What file format can be used instead of .properties?

- A) .xml
- B) .json
- C) .yaml
- D) .config

---

### Question 48
**[Spring Boot]**

What annotation creates a bean in a @Configuration class?

- A) @Component
- B) @Bean
- C) @Service
- D) @Create

---

### Question 49
**[Spring Boot]**

What does the spring-boot-maven-plugin do?

- A) Generates Maven files
- B) Creates executable JAR/WAR files
- C) Validates POM files
- D) Updates dependencies

---

### Question 50
**[Spring Boot]**

How do you provide a default value in @Value annotation?

- A) @Value("${key:default}")
- B) @Value("${key}") default="value"
- C) @Value(key, default)
- D) @Value("${key}", "default")

---

### Question 51
**[Spring Boot]**

What property enables SQL logging in Spring Boot?

- A) spring.sql.show=true
- B) spring.jpa.show-sql=true
- C) logging.sql=true
- D) hibernate.show.sql=true

---

### Question 52
**[Spring Boot]**

What is the default context path in Spring Boot?

- A) /app
- B) /api
- C) / (root)
- D) /spring

---

### Question 53
**[Spring Boot]**

Which profile notation means "all profiles except prod"?

- A) @Profile("not-prod")
- B) @Profile("!prod")
- C) @Profile("except:prod")
- D) @Profile("-prod")

---

### Question 54
**[Spring Boot]**

What starter includes JUnit and Mockito for testing?

- A) spring-boot-starter-junit
- B) spring-boot-starter-test
- C) spring-boot-starter-mock
- D) spring-boot-starter-testing

---

### Question 55
**[Spring Boot]**

How do you run a Spring Boot application from command line?

- A) spring run app.jar
- B) java -jar app.jar
- C) boot start app.jar
- D) mvn start

---

## Spring MVC

### Question 56
**[Spring MVC]**

What is the front controller in Spring MVC?

- A) FrontController
- B) DispatcherServlet
- C) MainServlet
- D) SpringController

---

### Question 57
**[Spring MVC]**

What annotation creates a REST API controller?

- A) @Controller
- B) @RestController
- C) @ApiController
- D) @WebController

---

### Question 58
**[Spring MVC]**

What is the difference between @Controller and @RestController?

- A) No difference
- B) @RestController includes @ResponseBody
- C) @Controller is for REST only
- D) @RestController requires XML config

---

### Question 59
**[Spring MVC]**

Which annotation maps HTTP GET requests?

- A) @Get
- B) @GetRequest
- C) @GetMapping
- D) @HttpGet

---

### Question 60
**[Spring MVC]**

What annotation extracts values from URL path?

- A) @RequestParam
- B) @PathVariable
- C) @UrlParam
- D) @PathParam

---

### Question 61
**[Spring MVC]**

What annotation extracts query parameters?

- A) @QueryParam
- B) @UrlParam
- C) @RequestParam
- D) @Parameter

---

### Question 62
**[Spring MVC]**

What annotation binds JSON request body to an object?

- A) @JsonBody
- B) @RequestBody
- C) @Body
- D) @InputBody

---

### Question 63
**[Spring MVC]**

What class provides full control over HTTP response?

- A) HttpResponse
- B) ResponseEntity
- C) RestResponse
- D) WebResponse

---

### Question 64
**[Spring MVC]**

What HTTP status does ResponseEntity.ok() return?

- A) 201 Created
- B) 204 No Content
- C) 200 OK
- D) 202 Accepted

---

### Question 65
**[Spring MVC]**

What HTTP status does ResponseEntity.created() return?

- A) 200 OK
- B) 201 Created
- C) 204 No Content
- D) 202 Accepted

---

### Question 66
**[Spring MVC]**

Which annotation triggers validation on request body?

- A) @Validate
- B) @Valid
- C) @Check
- D) @Verify

---

### Question 67
**[Spring MVC]**

What annotation validates a string is not null or empty?

- A) @Required
- B) @NotEmpty
- C) @NotBlank
- D) @Mandatory

---

### Question 68
**[Spring MVC]**

Which annotation defines a global exception handler class?

- A) @ExceptionHandler
- B) @ControllerAdvice
- C) @GlobalHandler
- D) @ErrorController

---

### Question 69
**[Spring MVC]**

What annotation handles specific exceptions in a controller advice?

- A) @CatchException
- B) @HandleError
- C) @ExceptionHandler
- D) @ErrorHandler

---

### Question 70
**[Spring MVC]**

What annotation is equivalent to @ControllerAdvice + @ResponseBody?

- A) @RestAdvice
- B) @RestControllerAdvice
- C) @ApiControllerAdvice
- D) @GlobalRestHandler

---

### Question 71
**[Spring MVC]**

What annotation sets the base URL for a controller?

- A) @BaseUrl
- B) @RequestMapping
- C) @UrlMapping
- D) @Path

---

### Question 72
**[Spring MVC]**

Which HTTP method is used to update a resource completely?

- A) POST
- B) PATCH
- C) PUT
- D) UPDATE

---

### Question 73
**[Spring MVC]**

Which HTTP method is used for partial updates?

- A) POST
- B) PUT
- C) PATCH
- D) PARTIAL

---

### Question 74
**[Spring MVC]**

What does ResponseEntity.notFound().build() return?

- A) 400 Bad Request
- B) 404 Not Found
- C) 500 Server Error
- D) 204 No Content

---

### Question 75
**[Spring MVC]**

What validation annotation checks email format?

- A) @EmailFormat
- B) @Email
- C) @ValidEmail
- D) @MailAddress

---

### Question 76
**[Spring MVC]**

What annotation sets min/max for string length?

- A) @Length
- B) @Size
- C) @Range
- D) @Limit

---

### Question 77
**[Spring MVC]**

What exception is thrown when @Valid validation fails?

- A) ValidationException
- B) ConstraintViolationException
- C) MethodArgumentNotValidException
- D) InvalidRequestException

---

### Question 78
**[Spring MVC]**

What does @RequestMapping(method = RequestMethod.DELETE) map?

- A) GET requests
- B) POST requests
- C) DELETE requests
- D) PUT requests

---

### Question 79
**[Spring MVC]**

What is the purpose of HttpMessageConverter in Spring MVC?

- A) Convert HTTP to HTTPS
- B) Convert objects to/from HTTP request/response
- C) Encrypt HTTP messages
- D) Compress HTTP data

---

### Question 80
**[Spring MVC]**

What does @ResponseStatus annotation do?

- A) Check response status
- B) Set HTTP status code for response
- C) Validate response
- D) Log response status

---

## End of Questions

**Total: 80 Questions**
- Spring Framework (IoC, DI, Beans, AOP): 30
- Spring Boot (Auto-configuration, Starters, Properties): 25
- Spring MVC (Controllers, REST APIs, Exception Handling): 25

---

*Proceed to `mcq-answers.md` for answers and explanations.*
