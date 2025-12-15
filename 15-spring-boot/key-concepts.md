# Spring Boot Key Concepts for Application Developers

## Overview

This document covers essential Spring Boot concepts that every Java application developer must master. Spring Boot simplifies Spring application development with auto-configuration, embedded servers, and production-ready features. Understanding Spring Boot is critical for modern Java microservices and cloud-native application development.

---

## 1. Spring Boot Fundamentals

### Why It Matters
- Industry standard for modern Java development
- Dramatically reduces configuration and boilerplate
- Enables rapid application development
- Production-ready features out of the box
- Foundation for microservices architecture

### Key Concepts

| Concept | Description | Developer Use Case |
|---------|-------------|-------------------|
| Auto-configuration | Automatic bean setup | Zero/minimal configuration |
| Starter dependencies | Pre-packaged dependencies | Quick project setup |
| Embedded server | Built-in Tomcat/Jetty | No external server needed |
| Opinionated defaults | Sensible conventions | Less decision making |
| Production-ready | Metrics, health checks | Deploy-ready apps |

### Spring Boot vs Spring Framework

```
Spring Framework:
- Requires manual configuration
- External application server
- More boilerplate code
- Flexible but verbose

Spring Boot:
- Auto-configuration
- Embedded server
- Minimal boilerplate
- Opinionated but customizable
- Production-ready features
```

| Aspect | Spring Framework | Spring Boot |
|--------|------------------|-------------|
| Configuration | Manual XML/Java config | Auto-configuration |
| Deployment | WAR to server | Standalone JAR |
| Server | External Tomcat/JBoss | Embedded Tomcat/Jetty |
| Setup time | Hours | Minutes |
| Dependencies | Manual management | Starter POMs |
| Production features | Add manually | Built-in |

### Creating Spring Boot Application

```java
// Main application class
@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// That's it! Spring Boot handles:
// - Component scanning
// - Auto-configuration
// - Embedded server
// - Application context setup
```

### Maven Project Structure

```xml
<!-- pom.xml -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <!-- Web application starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Database starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

---

## 2. @SpringBootApplication Annotation

### Why It Matters
- Single annotation combines three key annotations
- Bootstraps entire Spring Boot application
- Enables auto-configuration and component scanning
- Entry point for Spring Boot apps

### Key Concepts

```java
@SpringBootApplication
=
@SpringBootConfiguration    // Marks as configuration class
+
@EnableAutoConfiguration    // Enable auto-configuration
+
@ComponentScan             // Scan for components
```

### Detailed Breakdown

```java
// Basic usage
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// With custom configuration
@SpringBootApplication(
    scanBasePackages = {"com.example.myapp", "com.example.shared"},
    exclude = {DataSourceAutoConfiguration.class}
)
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// Equivalent to:
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.example.myapp", "com.example.shared"})
public class MyApplication {
    // ...
}
```

### Component Scanning

```
Project Structure:
com.example.myapp
    ├── MyApplication.java (@SpringBootApplication)
    ├── controller
    │   └── UserController.java (@RestController)
    ├── service
    │   └── UserService.java (@Service)
    └── repository
        └── UserRepository.java (@Repository)

Component scanning starts from MyApplication package
and scans all sub-packages automatically.
```

---

## 3. Auto-Configuration

### Why It Matters
- Eliminates boilerplate configuration
- Intelligently configures beans based on classpath
- Customizable when needed
- Major productivity boost

### Key Concepts

```
Auto-configuration process:
1. Spring Boot detects libraries on classpath
2. Automatically configures beans based on conditions
3. Uses sensible defaults
4. Allows overriding with custom configuration
```

| Starter | Auto-Configured Components |
|---------|---------------------------|
| spring-boot-starter-web | Tomcat, Spring MVC, Jackson |
| spring-boot-starter-data-jpa | DataSource, EntityManager, Transactions |
| spring-boot-starter-security | Spring Security filters and configuration |
| spring-boot-starter-actuator | Health endpoints, metrics |

### How Auto-Configuration Works

```java
// Example: DataSource auto-configuration

// 1. Add dependency
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>

// 2. Add properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=dbuser
spring.datasource.password=dbpass

// 3. Spring Boot automatically configures:
// - DataSource bean
// - JPA EntityManagerFactory
// - TransactionManager
// - Database initialization

// 4. Just use it!
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Auto-configured and ready to use!
}
```

### Conditional Auto-Configuration

```java
// Spring Boot uses @Conditional annotations

@Configuration
@ConditionalOnClass(DataSource.class)  // Only if class exists
@ConditionalOnMissingBean(DataSource.class)  // Only if bean not defined
public class DataSourceAutoConfiguration {

    @Bean
    public DataSource dataSource() {
        // Auto-configure DataSource
        return new HikariDataSource();
    }
}
```

### Customizing Auto-Configuration

```java
// Option 1: Override with custom bean
@Configuration
public class CustomDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        // Custom DataSource configuration
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(20);
        return dataSource;
    }
}

// Option 2: Disable specific auto-configuration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyApplication {
    // DataSource won't be auto-configured
}

// Option 3: Use properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
```

### Debugging Auto-Configuration

```properties
# application.properties
# Show auto-configuration report
debug=true

# Or run with:
java -jar myapp.jar --debug
```

```
Auto-configuration Report:

Positive matches:
-----------------
DataSourceAutoConfiguration matched:
  - @ConditionalOnClass found required class 'javax.sql.DataSource'

Negative matches:
-----------------
MongoAutoConfiguration:
  - @ConditionalOnClass did not find required class 'com.mongodb.client.MongoClient'
```

---

## 4. Starter Dependencies

### Why It Matters
- Pre-configured dependency bundles
- Version compatibility guaranteed
- Eliminate dependency conflicts
- Rapid project setup

### Key Concepts

```
Starter = Transitive dependencies + Auto-configuration

spring-boot-starter-web includes:
  - spring-web
  - spring-webmvc
  - jackson-databind
  - tomcat-embed-core
  - hibernate-validator
  - and more...
```

### Common Starters

| Starter | Purpose | Key Dependencies |
|---------|---------|------------------|
| spring-boot-starter | Core starter | Core, Logging, Auto-config |
| spring-boot-starter-web | Web applications | Spring MVC, Tomcat, Jackson |
| spring-boot-starter-data-jpa | JPA/Hibernate | Spring Data JPA, Hibernate |
| spring-boot-starter-security | Security | Spring Security |
| spring-boot-starter-test | Testing | JUnit, Mockito, AssertJ |
| spring-boot-starter-actuator | Production monitoring | Metrics, Health checks |
| spring-boot-starter-validation | Validation | Hibernate Validator |
| spring-boot-starter-thymeleaf | Template engine | Thymeleaf |
| spring-boot-starter-mail | Email | JavaMail |

### Using Starters

```xml
<!-- Web application -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Database access -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

### REST API Starter Template

```xml
<!-- Complete REST API with database -->
<dependencies>
    <!-- Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Database -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- Development tools -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 5. Application Properties and Configuration

### Why It Matters
- Externalize configuration from code
- Environment-specific settings
- Override defaults easily
- 12-factor app compliance

### Key Concepts

```
Configuration file precedence (highest to lowest):
1. Command line arguments
2. SPRING_APPLICATION_JSON
3. OS environment variables
4. application-{profile}.properties
5. application.properties
6. @PropertySource
7. Default properties
```

### Application Properties

```properties
# application.properties

# Server configuration
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=dbuser
spring.datasource.password=dbpass
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Logging
logging.level.root=INFO
logging.level.com.example.myapp=DEBUG
logging.file.name=logs/application.log

# Jackson JSON
spring.jackson.serialization.indent-output=true
spring.jackson.default-property-inclusion=non_null

# Application properties
app.name=My Application
app.version=1.0.0
app.max-upload-size=10MB
```

### YAML Configuration (Alternative)

```yaml
# application.yml

server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: dbuser
    password: dbpass
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update

logging:
  level:
    root: INFO
    com.example.myapp: DEBUG

app:
  name: My Application
  version: 1.0.0
```

### Using Properties in Code

```java
// Option 1: @Value annotation
@Component
public class AppInfo {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${server.port}")
    private int serverPort;

    @Value("${app.max-upload-size:5MB}")  // Default value
    private String maxUploadSize;
}

// Option 2: @ConfigurationProperties (type-safe, recommended)
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private String version;
    private String maxUploadSize;

    // Getters and setters
}

// Option 3: Environment
@Component
public class ConfigService {

    @Autowired
    private Environment env;

    public String getAppName() {
        return env.getProperty("app.name");
    }
}
```

---

## 6. Profiles

### Why It Matters
- Different configurations for different environments
- Easy switching between dev/test/prod
- Conditional bean registration
- Essential for deployment pipelines

### Key Concepts

```
Common profiles:
- dev (development)
- test (testing)
- staging (pre-production)
- prod (production)
```

### Profile-Specific Properties

```properties
# application.properties (default)
app.name=My Application
spring.jpa.show-sql=true

# application-dev.properties (development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
logging.level.com.example=DEBUG

# application-test.properties (testing)
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop

# application-prod.properties (production)
spring.datasource.url=jdbc:postgresql://prod-db:5432/mydb
spring.jpa.show-sql=false
logging.level.root=WARN
logging.level.com.example=INFO
```

### Activating Profiles

```bash
# Command line
java -jar myapp.jar --spring.profiles.active=prod

# Environment variable
export SPRING_PROFILES_ACTIVE=dev

# application.properties
spring.profiles.active=dev

# IDE (IntelliJ, Eclipse)
VM options: -Dspring.profiles.active=dev

# Maven
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Profile-Specific Beans

```java
// Development configuration
@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }

    @Bean
    public CommandLineRunner loadTestData(UserRepository userRepository) {
        return args -> {
            userRepository.save(new User("testuser", "test@example.com"));
            userRepository.save(new User("admin", "admin@example.com"));
        };
    }
}

// Production configuration
@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://prod-db:5432/mydb");
        dataSource.setMaximumPoolSize(20);
        return dataSource;
    }
}

// Component with profile
@Service
@Profile("!prod")  // Active in all profiles except prod
public class DebugService {
    // Debug utilities
}
```

### Multiple Profiles

```bash
# Activate multiple profiles
java -jar myapp.jar --spring.profiles.active=dev,debug,local

# Properties are merged, later profiles override earlier ones
```

---

## 7. Embedded Server

### Why It Matters
- No external server installation needed
- Simplified deployment (single JAR)
- Consistent across environments
- Cloud-native deployment ready

### Key Concepts

```
Spring Boot includes:
- Tomcat (default)
- Jetty (alternative)
- Undertow (alternative)
```

### Server Configuration

```properties
# Server port
server.port=8080

# Context path
server.servlet.context-path=/api

# Session timeout
server.servlet.session.timeout=30m

# Compression
server.compression.enabled=true
server.compression.min-response-size=1024

# SSL/HTTPS
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=secret
server.ssl.key-store-type=PKCS12
```

### Switching Embedded Server

```xml
<!-- Use Jetty instead of Tomcat -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

### Customizing Embedded Server

```java
@Configuration
public class ServerConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory>
            tomcatCustomizer() {
        return factory -> {
            factory.setPort(8080);
            factory.setContextPath("/api");
            factory.addConnectorCustomizers(connector -> {
                connector.setMaxPostSize(10485760); // 10MB
            });
        };
    }
}
```

---

## 8. Building and Running Spring Boot Applications

### Why It Matters
- Deploy as standalone JAR or WAR
- Easy CI/CD integration
- Container-ready packaging
- Flexible deployment options

### Key Concepts

### Building Executable JAR

```bash
# Build with Maven
mvn clean package

# Build with Gradle
./gradlew build

# Output: target/myapp-1.0.0.jar (executable JAR)
```

### Running Application

```bash
# Run JAR
java -jar target/myapp-1.0.0.jar

# Run with profile
java -jar myapp.jar --spring.profiles.active=prod

# Run with custom properties
java -jar myapp.jar --server.port=9000

# Run with Maven
mvn spring-boot:run

# Run with Gradle
./gradlew bootRun
```

### Maven Plugin Configuration

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <!-- Create executable JAR -->
                <executable>true</executable>
                <!-- Exclude dev tools from production -->
                <excludeDevtools>true</excludeDevtools>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Docker Deployment

```dockerfile
# Dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/myapp-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# Build Docker image
docker build -t myapp:1.0.0 .

# Run container
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod myapp:1.0.0
```

---

## 9. Spring Boot DevTools

### Why It Matters
- Automatic application restart on code changes
- LiveReload for browser refresh
- Development-only features
- Productivity boost during development

### Key Concepts

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

### DevTools Features

```
DevTools provides:
1. Automatic restart when classpath changes
2. LiveReload server (browser auto-refresh)
3. Property defaults for development
4. H2 console auto-configuration
5. Disabled caching for templates
```

### Configuration

```properties
# application-dev.properties

# Disable restart (if needed)
spring.devtools.restart.enabled=false

# Exclude files from restart trigger
spring.devtools.restart.exclude=static/**,public/**

# LiveReload
spring.devtools.livereload.enabled=true
```

---

## 10. Actuator - Production-Ready Features

### Why It Matters
- Monitor application health
- Expose metrics and diagnostics
- Production readiness out of the box
- Essential for DevOps and observability

### Key Concepts

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Common Endpoints

| Endpoint | Purpose | Example |
|----------|---------|---------|
| /actuator/health | Application health | UP/DOWN status |
| /actuator/info | Application info | Version, description |
| /actuator/metrics | Application metrics | JVM, HTTP, custom |
| /actuator/env | Environment properties | Configuration |
| /actuator/loggers | Logger levels | View/modify at runtime |

### Actuator Configuration

```properties
# Enable/disable actuator
management.endpoints.enabled-by-default=true

# Expose endpoints
management.endpoints.web.exposure.include=health,info,metrics

# Health endpoint details
management.endpoint.health.show-details=always

# Custom info
info.app.name=My Application
info.app.version=1.0.0
info.app.description=My awesome Spring Boot app
```

### Custom Health Indicator

```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Health health() {
        try {
            long count = userRepository.count();
            return Health.up()
                .withDetail("database", "Available")
                .withDetail("userCount", count)
                .build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("database", "Unavailable")
                .withException(e)
                .build();
        }
    }
}
```

---

## Quick Reference Card

### Essential Annotations

```java
@SpringBootApplication    // Main application
@RestController          // REST API controller
@Service                 // Business logic
@Repository              // Data access
@Configuration           // Configuration class
@ConfigurationProperties // Type-safe properties
@Value                   // Inject property
@Profile                 // Profile-specific bean
```

### Quick Start REST API

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
```

### Common Properties

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=user
spring.datasource.password=pass

# JPA
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# Logging
logging.level.root=INFO
logging.level.com.example=DEBUG

# Profile
spring.profiles.active=dev
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand Spring Boot advantages over Spring Framework
- [ ] Create Spring Boot application with @SpringBootApplication
- [ ] Use starter dependencies effectively
- [ ] Configure application with properties/YAML files
- [ ] Use profiles for environment-specific configuration
- [ ] Leverage auto-configuration
- [ ] Customize auto-configuration when needed
- [ ] Use @ConfigurationProperties for type-safe configuration
- [ ] Build and run Spring Boot applications
- [ ] Use DevTools for development productivity
- [ ] Configure and use Actuator endpoints
- [ ] Deploy Spring Boot as executable JAR
- [ ] Debug auto-configuration issues

---

## Next Steps

After mastering these concepts, proceed to:
- Build complete REST API with Spring Boot
- Learn Spring Data JPA for database operations
- Explore Spring Security for authentication
- Study Spring Boot testing strategies
- Learn microservices patterns with Spring Cloud
- Practice containerizing Spring Boot apps with Docker
