# Logging Key Concepts for Application Developers

## Overview

This document covers essential logging concepts using Log4j 2 and SLF4J that every Java application developer must master. Logging is critical for debugging, monitoring, troubleshooting, and maintaining applications in production. Understanding proper logging practices is essential for building observable, maintainable enterprise applications.

---

## 1. Logging Fundamentals

### Why It Matters
- Debugging production issues without debuggers
- Monitoring application health and performance
- Security auditing and compliance
- Understanding user behavior and errors
- Essential for DevOps and observability

### Key Concepts

| Concept | Description | Developer Use Case |
|---------|-------------|-------------------|
| Logger | Entry point for logging | Create per class |
| Log Level | Message severity | Filter log output |
| Appender | Output destination | Console, file, database |
| Layout | Message format | Structure log entries |
| Configuration | Logging setup | Control behavior |
| MDC | Thread context | Track requests |

### Why Use Logging Frameworks

```java
// DON'T: System.out.println
System.out.println("User logged in: " + username);
// Problems: No levels, no control, no timestamps, lost in production

// DO: Use logging framework
logger.info("User logged in: {}", username);
// Benefits: Levels, timestamps, context, configurable, searchable
```

### Benefits of Proper Logging

| Benefit | Impact |
|---------|--------|
| Debugging | Find issues quickly |
| Monitoring | Track application health |
| Auditing | Compliance and security |
| Performance | Identify bottlenecks |
| Analytics | Understand usage patterns |

---

## 2. Log Levels

### Why It Matters
- Filter noise from important messages
- Different levels for different environments
- Control verbosity without code changes
- Essential for production troubleshooting

### Key Concepts

| Level | Purpose | When to Use | Example |
|-------|---------|-------------|---------|
| TRACE | Very detailed | Deep debugging | Method entry/exit |
| DEBUG | Detailed info | Development | Variable values |
| INFO | General info | Normal operation | User logged in |
| WARN | Potential issues | Recoverable errors | Deprecated API used |
| ERROR | Errors | Exceptions | Database connection failed |
| FATAL | Critical errors | System failure | Out of memory |

### Log Level Hierarchy

```
TRACE < DEBUG < INFO < WARN < ERROR < FATAL

If level set to INFO:
✓ INFO, WARN, ERROR, FATAL shown
✗ DEBUG, TRACE hidden
```

### Choosing the Right Level

```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    public User createUser(String username, String email) {
        // TRACE - Detailed flow (rarely needed)
        logger.trace("Entering createUser method with username: {}", username);

        // DEBUG - Diagnostic information
        logger.debug("Validating user input: username={}, email={}", username, email);

        // INFO - Significant business events
        logger.info("Creating new user: {}", username);

        try {
            User user = userRepository.save(new User(username, email));

            // INFO - Success
            logger.info("User created successfully: id={}, username={}",
                user.getId(), user.getUsername());

            return user;

        } catch (DuplicateUsernameException e) {
            // WARN - Expected exception, recoverable
            logger.warn("Duplicate username attempted: {}", username);
            throw e;

        } catch (DatabaseException e) {
            // ERROR - Unexpected exception, operation failed
            logger.error("Failed to create user: {}", username, e);
            throw e;

        } finally {
            // TRACE - Method exit
            logger.trace("Exiting createUser method");
        }
    }

    public void handleCriticalSystemError() {
        try {
            initializeCriticalSystem();
        } catch (OutOfMemoryError e) {
            // FATAL - System cannot continue
            logger.fatal("Critical system failure: out of memory", e);
            System.exit(1);
        }
    }
}
```

### Environment-Specific Levels

```xml
<!-- Development: See everything -->
<Root level="DEBUG">
    <AppenderRef ref="Console"/>
</Root>

<!-- Production: Only important messages -->
<Root level="INFO">
    <AppenderRef ref="RollingFile"/>
</Root>

<!-- Troubleshooting: Temporary verbose logging -->
<Root level="TRACE">
    <AppenderRef ref="Console"/>
</Root>
```

---

## 3. Log4j 2 Setup and Configuration

### Why It Matters
- Industry-standard logging framework
- High performance and flexibility
- Rich configuration options
- Extensive ecosystem support

### Key Concepts

```xml
Configuration File Locations (checked in order):
1. log4j2-test.xml (test classpath)
2. log4j2.xml (classpath)
3. log4j2.json
4. log4j2.yaml
5. Default configuration (console only)
```

### Maven Dependencies

```xml
<dependencies>
    <!-- Log4j 2 Core -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.21.1</version>
    </dependency>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.21.1</version>
    </dependency>

    <!-- Optional: SLF4J bridge -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-slf4j2-impl</artifactId>
        <version>2.21.1</version>
    </dependency>
</dependencies>
```

### Basic Configuration (log4j2.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <!-- Console Appender -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>

    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

### Complete Production Configuration

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Properties>
        <Property name="LOG_PATTERN">
            %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n
        </Property>
        <Property name="APP_LOG_ROOT">./logs</Property>
    </Properties>

    <Appenders>
        <!-- Console Appender -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>

        <!-- File Appender -->
        <File name="File" fileName="${APP_LOG_ROOT}/app.log">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </File>

        <!-- Rolling File Appender -->
        <RollingFile name="RollingFile"
                     fileName="${APP_LOG_ROOT}/app.log"
                     filePattern="${APP_LOG_ROOT}/app-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1"/>
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>
    </Appenders>

    <Loggers>
        <!-- Application loggers -->
        <Logger name="com.example.myapp" level="debug" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="RollingFile"/>
        </Logger>

        <!-- Third-party loggers -->
        <Logger name="org.springframework" level="info" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>

        <!-- Root logger -->
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="RollingFile"/>
        </Root>
    </Loggers>
</Configuration>
```

---

## 4. Appenders

### Why It Matters
- Control where logs are written
- Multiple destinations simultaneously
- Different outputs for different environments
- Essential for log aggregation and monitoring

### Key Concepts

| Appender Type | Purpose | Use Case |
|--------------|---------|----------|
| Console | Write to stdout/stderr | Development |
| File | Write to file | Simple logging |
| RollingFile | Rotate log files | Production |
| Async | Async logging | High performance |
| JDBC | Database storage | Auditing |
| Socket | Network logging | Centralized logging |

### Console Appender

```xml
<Console name="Console" target="SYSTEM_OUT">
    <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n"/>

    <!-- Optional: Only log errors to console -->
    <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
</Console>
```

### File Appender

```xml
<File name="FileAppender" fileName="logs/application.log" append="true">
    <PatternLayout>
        <Pattern>%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n</Pattern>
    </PatternLayout>
</File>
```

### Rolling File Appender (Time-Based)

```xml
<RollingFile name="TimeBasedRolling"
             fileName="logs/app.log"
             filePattern="logs/app-%d{yyyy-MM-dd}.log.gz">
    <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level - %msg%n"/>
    <Policies>
        <!-- Roll over daily -->
        <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
    </Policies>
    <!-- Keep 30 days of logs -->
    <DefaultRolloverStrategy max="30"/>
</RollingFile>
```

### Rolling File Appender (Size-Based)

```xml
<RollingFile name="SizeBasedRolling"
             fileName="logs/app.log"
             filePattern="logs/app-%d{yyyy-MM-dd}-%i.log.gz">
    <PatternLayout pattern="%d %-5level %logger{36} - %msg%n"/>
    <Policies>
        <!-- Roll over when file reaches 10MB -->
        <SizeBasedTriggeringPolicy size="10MB"/>
    </Policies>
    <!-- Keep max 20 files -->
    <DefaultRolloverStrategy max="20"/>
</RollingFile>
```

### Async Appender (High Performance)

```xml
<Appenders>
    <!-- Regular file appender -->
    <RollingFile name="FileAppender" fileName="logs/app.log"
                 filePattern="logs/app-%d{yyyy-MM-dd}.log">
        <PatternLayout pattern="%d %-5level %logger - %msg%n"/>
        <Policies>
            <TimeBasedTriggeringPolicy/>
        </Policies>
    </RollingFile>

    <!-- Wrap with async for better performance -->
    <Async name="AsyncAppender">
        <AppenderRef ref="FileAppender"/>
    </Async>
</Appenders>

<Loggers>
    <Root level="info">
        <AppenderRef ref="AsyncAppender"/>
    </Root>
</Loggers>
```

---

## 5. Layouts and Patterns

### Why It Matters
- Control log message format
- Include relevant context information
- Make logs searchable and parseable
- Standardize across applications

### Key Concepts

| Pattern | Output | Description |
|---------|--------|-------------|
| `%d` | 2024-01-15 10:30:45 | Date/time |
| `%t` | main | Thread name |
| `%level` | INFO | Log level |
| `%-5level` | INFO  | Level, padded to 5 chars |
| `%logger` | com.example.UserService | Logger name |
| `%logger{36}` | c.e.UserService | Abbreviated logger |
| `%msg` | User created | Log message |
| `%n` | \n | Line separator |
| `%ex` | Exception stack trace | Exception |
| `%C` | UserService | Class name |
| `%M` | createUser | Method name |
| `%L` | 42 | Line number |

### Common Pattern Examples

```xml
<!-- Simple pattern -->
<PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %msg%n"/>
<!-- Output: 10:30:45.123 INFO  User logged in -->

<!-- Standard pattern -->
<PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
<!-- Output: 2024-01-15 10:30:45 [main] INFO  c.e.UserService - User created -->

<!-- Detailed pattern with class and method -->
<PatternLayout pattern="%d{ISO8601} [%t] %-5level %logger{36}.%M:%L - %msg%n"/>
<!-- Output: 2024-01-15T10:30:45,123 [main] INFO  c.e.UserService.createUser:42 - User created -->

<!-- Production pattern with exception -->
<PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger - %msg%n%ex"/>

<!-- JSON pattern (for log aggregation) -->
<JsonLayout complete="false" compact="true">
    <KeyValuePair key="timestamp" value="$${date:yyyy-MM-dd'T'HH:mm:ss.SSS}"/>
</JsonLayout>
```

### Color Console Output (Development)

```xml
<Console name="Console" target="SYSTEM_OUT">
    <PatternLayout>
        <Pattern>%d{HH:mm:ss.SSS} %highlight{%-5level} %style{%logger{36}}{cyan} - %msg%n</Pattern>
    </PatternLayout>
</Console>
```

### Custom Date Formats

```xml
<!-- Time only -->
%d{HH:mm:ss}          <!-- 14:30:45 -->

<!-- Date only -->
%d{yyyy-MM-dd}        <!-- 2024-01-15 -->

<!-- ISO 8601 -->
%d{ISO8601}           <!-- 2024-01-15T14:30:45,123 -->

<!-- Custom format -->
%d{dd/MMM/yyyy HH:mm:ss.SSS}  <!-- 15/Jan/2024 14:30:45.123 -->
```

---

## 6. SLF4J (Simple Logging Facade for Java)

### Why It Matters
- Abstraction over logging frameworks
- Switch implementations without code changes
- Industry standard for libraries
- Cleaner, more flexible API

### Key Concepts

```
Application Code
       ↓
    SLF4J API (abstraction)
       ↓
 SLF4J Binding (bridge)
       ↓
Logging Implementation (Log4j 2, Logback, etc.)
```

### Maven Dependencies

```xml
<!-- SLF4J API -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>

<!-- SLF4J to Log4j 2 Bridge -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>2.21.1</version>
</dependency>

<!-- Log4j 2 Implementation -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.21.1</version>
</dependency>
```

### SLF4J Usage

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    // Create logger using SLF4J
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void createUser(String username, String email) {
        // Basic logging
        logger.info("Creating user: {}", username);

        // Multiple parameters
        logger.debug("User details: username={}, email={}", username, email);

        // Exception logging
        try {
            userRepository.save(new User(username, email));
        } catch (Exception e) {
            logger.error("Failed to create user: {}", username, e);
        }
    }

    // Parameterized messages (efficient)
    public void processOrder(Order order) {
        // Only formats if INFO is enabled
        logger.info("Processing order {} for customer {}",
            order.getId(), order.getCustomerId());
    }

    // Guard clauses (legacy, usually not needed)
    public void verboseDebug(Object data) {
        if (logger.isDebugEnabled()) {
            // Expensive operation only if debug enabled
            String complexData = data.toComplexString();
            logger.debug("Complex data: {}", complexData);
        }
    }
}
```

### SLF4J vs Log4j 2 Direct

```java
// SLF4J (recommended for applications)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void doSomething() {
        logger.info("Using SLF4J");
    }
}

// Log4j 2 Direct (when you need Log4j 2 specific features)
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedService {
    private static final Logger logger = LogManager.getLogger(AdvancedService.class);

    public void doSomething() {
        logger.info("Using Log4j 2 directly");
    }
}
```

---

## 7. MDC (Mapped Diagnostic Context)

### Why It Matters
- Track requests across multiple methods/classes
- Add context to all log messages in thread
- Essential for microservices and web applications
- Correlate logs for single user request

### Key Concepts

```java
// MDC stores thread-local key-value pairs
// All logs in same thread include MDC values
MDC.put(key, value);
MDC.get(key);
MDC.remove(key);
MDC.clear();
```

### MDC Usage Example

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public void processOrder(HttpServletRequest request, Order order) {
        try {
            // Add context at request start
            MDC.put("requestId", UUID.randomUUID().toString());
            MDC.put("userId", request.getUserId());
            MDC.put("orderId", order.getId());

            logger.info("Processing order");
            // Log includes: requestId, userId, orderId

            orderService.validateOrder(order);
            // Service logs also include MDC context

            paymentService.processPayment(order);
            // Payment logs also include MDC context

            logger.info("Order completed successfully");

        } finally {
            // Always clear MDC after request
            MDC.clear();
        }
    }
}
```

### MDC Configuration

```xml
<!-- Include MDC values in log pattern -->
<PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level [%X{requestId}] [%X{userId}] %logger{36} - %msg%n"/>

<!-- Output: -->
<!-- 2024-01-15 10:30:45 [main] INFO  [abc-123-def] [user-456] c.e.OrderController - Processing order -->
```

### Web Application MDC Filter

```java
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.MDC;

public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            // Add request ID
            MDC.put("requestId", UUID.randomUUID().toString());

            // Add user info if available
            if (httpRequest.getUserPrincipal() != null) {
                MDC.put("username", httpRequest.getUserPrincipal().getName());
            }

            // Add IP address
            MDC.put("ipAddress", httpRequest.getRemoteAddr());

            chain.doFilter(request, response);

        } finally {
            MDC.clear();
        }
    }
}
```

---

## 8. Logging Best Practices

### Why It Matters
- Avoid common pitfalls and anti-patterns
- Ensure logs are useful and performant
- Follow industry standards
- Make debugging and monitoring effective

### Key Concepts

#### Use Parameterized Messages

```java
// DON'T: String concatenation
logger.info("User " + username + " logged in with email " + email);
// Problem: Builds string even if INFO disabled

// DO: Parameterized messages
logger.info("User {} logged in with email {}", username, email);
// Benefit: Only formats if INFO enabled
```

#### Log at Appropriate Levels

```java
// DON'T: Everything at INFO
logger.info("Entering method createUser");      // Use TRACE
logger.info("Variable x = " + x);               // Use DEBUG
logger.info("Critical system failure");         // Use ERROR/FATAL

// DO: Use appropriate levels
logger.trace("Entering method createUser");
logger.debug("Processing user with id: {}", userId);
logger.info("User created successfully: {}", username);
logger.warn("Database connection pool 80% full");
logger.error("Failed to create user", exception);
```

#### Include Context and Details

```java
// DON'T: Vague messages
logger.error("Error occurred");
logger.info("Success");

// DO: Specific, actionable messages
logger.error("Failed to create user {}: database connection timeout", username);
logger.info("User {} created successfully with id {}", username, userId);
```

#### Always Log Exceptions Properly

```java
// DON'T: Lose stack trace
logger.error("Error: " + e.getMessage());

// DO: Include exception object
logger.error("Failed to process order {}", orderId, e);

// DO: Add context before exception
logger.error("Failed to create user: username={}, email={}", username, email, e);
```

#### Don't Log Sensitive Information

```java
// DON'T: Log sensitive data
logger.info("User logged in: password={}", password);         // NEVER!
logger.debug("Credit card: {}", creditCardNumber);            // NEVER!
logger.info("Processing payment for SSN: {}", ssn);           // NEVER!

// DO: Log safe identifiers
logger.info("User logged in: username={}", username);
logger.debug("Processing payment for user: {}", userId);
logger.info("Order created: orderId={}", orderId);

// DO: Mask sensitive data if needed
logger.debug("Processing card ending in: {}",
    creditCardNumber.substring(creditCardNumber.length() - 4));
```

#### Avoid Logging in Loops (Usually)

```java
// DON'T: Log every iteration
for (User user : users) {
    logger.info("Processing user: {}", user.getId());  // 1000s of log lines!
}

// DO: Log summary
logger.info("Processing {} users", users.size());
for (User user : users) {
    processUser(user);
}
logger.info("Completed processing {} users", users.size());

// DO: Log exceptions in loops
for (User user : users) {
    try {
        processUser(user);
    } catch (Exception e) {
        logger.error("Failed to process user {}", user.getId(), e);
    }
}
```

#### Use Logger Per Class

```java
// DON'T: Shared logger
public class UserService {
    private static final Logger logger = LogManager.getLogger("MyApp");
}

// DO: Logger per class
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
}

// DO: Even better with SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
}
```

---

## 9. Environment-Specific Configuration

### Why It Matters
- Different log levels for dev/staging/prod
- Different outputs per environment
- Security and performance considerations
- Separate sensitive logs

### Key Concepts

#### Development Configuration

```xml
<!-- log4j2-dev.xml -->
<Configuration status="WARN">
    <Appenders>
        <!-- Verbose console output with colors -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout>
                <Pattern>%d{HH:mm:ss.SSS} %highlight{%-5level} %logger{36} - %msg%n</Pattern>
            </PatternLayout>
        </Console>
    </Appenders>

    <Loggers>
        <!-- Debug level for application -->
        <Logger name="com.example.myapp" level="debug"/>

        <!-- Trace for specific troubleshooting -->
        <Logger name="com.example.myapp.service.UserService" level="trace"/>

        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

#### Production Configuration

```xml
<!-- log4j2-prod.xml -->
<Configuration status="ERROR">
    <Properties>
        <Property name="LOG_ROOT">/var/log/myapp</Property>
    </Properties>

    <Appenders>
        <!-- Application logs -->
        <RollingFile name="AppLog"
                     fileName="${LOG_ROOT}/app.log"
                     filePattern="${LOG_ROOT}/app-%d{yyyy-MM-dd}.log.gz">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger - %msg%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1"/>
                <SizeBasedTriggeringPolicy size="100MB"/>
            </Policies>
            <DefaultRolloverStrategy max="30"/>
        </RollingFile>

        <!-- Error logs (separate file) -->
        <RollingFile name="ErrorLog"
                     fileName="${LOG_ROOT}/error.log"
                     filePattern="${LOG_ROOT}/error-%d{yyyy-MM-dd}.log.gz">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger - %msg%n%ex"/>
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1"/>
            </Policies>
        </RollingFile>

        <!-- Async wrapper for performance -->
        <Async name="AsyncAppLog">
            <AppenderRef ref="AppLog"/>
        </Async>
    </Appenders>

    <Loggers>
        <!-- INFO level for production -->
        <Logger name="com.example.myapp" level="info" additivity="false">
            <AppenderRef ref="AsyncAppLog"/>
            <AppenderRef ref="ErrorLog"/>
        </Logger>

        <!-- Reduce third-party noise -->
        <Logger name="org.springframework" level="warn"/>
        <Logger name="org.hibernate" level="warn"/>

        <Root level="warn">
            <AppenderRef ref="AsyncAppLog"/>
            <AppenderRef ref="ErrorLog"/>
        </Root>
    </Loggers>
</Configuration>
```

#### Spring Boot Profile-Based Configuration

```yaml
# application-dev.yml
logging:
  level:
    root: INFO
    com.example.myapp: DEBUG
    org.springframework.web: DEBUG
  pattern:
    console: "%d{HH:mm:ss.SSS} %highlight{%-5level} %logger{36} - %msg%n"

# application-prod.yml
logging:
  level:
    root: WARN
    com.example.myapp: INFO
  file:
    name: /var/log/myapp/application.log
  pattern:
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
```

---

## Quick Reference Card

### Logger Creation

```java
// SLF4J (recommended)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClass {
    private static final Logger logger = LoggerFactory.getLogger(MyClass.class);
}

// Log4j 2 Direct
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyClass {
    private static final Logger logger = LogManager.getLogger(MyClass.class);
}
```

### Common Logging Patterns

```java
// Basic levels
logger.trace("Entering method");
logger.debug("Variable value: {}", value);
logger.info("User {} created", username);
logger.warn("Cache is {}% full", percentage);
logger.error("Failed to process order {}", orderId, exception);

// With MDC
MDC.put("requestId", requestId);
logger.info("Processing request");
MDC.clear();

// Exception logging
try {
    riskyOperation();
} catch (Exception e) {
    logger.error("Operation failed for user {}", userId, e);
}
```

### Basic Configuration

```xml
<!-- Minimal log4j2.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n"/>
        </Console>
        <RollingFile name="File" fileName="logs/app.log"
                     filePattern="logs/app-%d{yyyy-MM-dd}.log">
            <PatternLayout pattern="%d %-5level %logger - %msg%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
            </Policies>
        </RollingFile>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand log levels and when to use each
- [ ] Set up Log4j 2 with Maven/Gradle
- [ ] Configure appenders for console and file output
- [ ] Create rolling file appenders for production
- [ ] Use pattern layouts effectively
- [ ] Write parameterized log messages
- [ ] Use SLF4J as logging facade
- [ ] Implement MDC for request tracking
- [ ] Follow logging best practices
- [ ] Avoid logging sensitive information
- [ ] Configure logging per environment
- [ ] Debug logging configuration issues
- [ ] Use async logging for performance

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 14: Spring Framework](../14-spring-framework/) - Learn dependency injection and AOP
- Explore structured logging with JSON layouts
- Learn log aggregation with ELK stack (Elasticsearch, Logstash, Kibana)
- Study distributed tracing with Zipkin or Jaeger
- Practice log analysis and monitoring in production environments
