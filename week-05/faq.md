# Week 05 - Interview FAQ

This document contains frequently asked interview questions and comprehensive answers for Week 05 topics: Reactive Programming, Testing (JUnit & Mockito), and Logging Frameworks.

---

## Table of Contents

1. [Reactive Programming](#reactive-programming)
2. [JUnit Testing](#junit-testing)
3. [Mockito Framework](#mockito-framework)
4. [Test-Driven Development](#test-driven-development)
5. [Logging Fundamentals](#logging-fundamentals)
6. [Log4J and SLF4J](#log4j-and-slf4j)

---

## Reactive Programming

### Q1: What is reactive programming? How does it differ from traditional programming?

**Answer:**

**Reactive programming** is a declarative programming paradigm focused on **data streams** and the **propagation of change**. Instead of explicitly handling each step, you describe what should happen when data arrives.

| Aspect | Traditional (Imperative) | Reactive |
|--------|-------------------------|----------|
| **Data Flow** | Pull-based (you request data) | Push-based (data comes to you) |
| **Blocking** | Waits for operations to complete | Non-blocking, async operations |
| **Resource Usage** | Thread per request | Event-driven, fewer threads |
| **Scalability** | Limited by thread count | Handles thousands of connections |

**Analogy:**
```
Traditional: Waiter checks if food is ready (polling)
Reactive: Kitchen rings bell when food is ready (push notification)
```

**Key benefits:**
- **Scalability**: Handle more concurrent users with fewer resources
- **Responsiveness**: System stays responsive under load
- **Resilience**: Built-in error handling and recovery
- **Resource efficiency**: Better CPU and memory utilization

---

### Q2: What are the four pillars of the Reactive Manifesto?

**Answer:**

The **Reactive Manifesto** defines four key principles for building reactive systems:

```
                    ┌─────────────────┐
                    │   RESPONSIVE    │  (The Goal)
                    └────────┬────────┘
              ┌──────────────┴──────────────┐
     ┌────────▼────────┐          ┌─────────▼───────┐
     │    RESILIENT    │          │     ELASTIC     │
     └────────┬────────┘          └─────────┬───────┘
              └──────────────┬──────────────┘
                    ┌────────▼────────┐
                    │  MESSAGE-DRIVEN │  (The Foundation)
                    └─────────────────┘
```

| Pillar | Description | Implementation |
|--------|-------------|----------------|
| **Responsive** | System responds in timely manner | Timeouts, fallbacks, fast-fail |
| **Resilient** | Stays responsive during failures | Isolation, replication, recovery |
| **Elastic** | Adapts to varying workload | Auto-scaling, load balancing |
| **Message-Driven** | Async message passing | Loose coupling, non-blocking I/O |

**Key point:** Message-driven architecture is the foundation that enables the other three properties.

---

### Q3: What is the Reactive Streams specification? What are its core interfaces?

**Answer:**

**Reactive Streams** is a specification for asynchronous stream processing with non-blocking backpressure. It's part of Java 9+ as `java.util.concurrent.Flow`.

**Core Interfaces:**

```java
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}

public interface Subscriber<T> {
    void onSubscribe(Subscription subscription);
    void onNext(T item);
    void onError(Throwable throwable);
    void onComplete();
}

public interface Subscription {
    void request(long n);  // Request n items (backpressure)
    void cancel();
}

public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
    // Both subscriber and publisher
}
```

**Flow of Events:**
```
1. Subscriber calls publisher.subscribe(subscriber)
2. Publisher calls subscriber.onSubscribe(subscription)
3. Subscriber calls subscription.request(n)
4. Publisher calls subscriber.onNext(item) for each item
5. Publisher calls subscriber.onComplete() or onError()
```

**Key concept - Backpressure:** The subscriber controls how many items it can handle, preventing the producer from overwhelming the consumer.

---

### Q4: What is backpressure in reactive programming?

**Answer:**

**Backpressure** is a mechanism for the consumer (subscriber) to signal the producer (publisher) about how much data it can handle. It prevents overwhelming slower consumers with data.

**Problem without backpressure:**
```
Fast Producer → → → → → → → [Buffer Overflow!] → Slow Consumer
    1000 items/sec                                   10 items/sec
```

**Solution with backpressure:**
```
Fast Producer → [Consumer requests 10] → Slow Consumer
Producer waits until consumer requests more
```

**Strategies:**

| Strategy | Description | Use Case |
|----------|-------------|----------|
| **Buffer** | Store excess items temporarily | Small bursts |
| **Drop** | Discard excess items | Real-time data where old values obsolete |
| **Latest** | Keep only most recent item | UI updates |
| **Error** | Signal error when overwhelmed | Critical data |
| **Request** | Consumer controls flow rate | Most common |

```java
// Example: Consumer requests items at its own pace
subscription.request(10);  // "I can handle 10 items"
// Process items...
subscription.request(10);  // "Ready for 10 more"
```

---

### Q5: What is SubmissionPublisher in Java? When would you use it?

**Answer:**

**SubmissionPublisher** is a Java implementation of `Flow.Publisher` that handles the complexity of managing subscribers, buffering, and backpressure.

```java
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow;

// Create publisher
SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

// Subscribe
publisher.subscribe(new Flow.Subscriber<String>() {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);  // Request first item
    }

    @Override
    public void onNext(String item) {
        System.out.println("Received: " + item);
        subscription.request(1);  // Request next item
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
});

// Publish items
publisher.submit("Hello");
publisher.submit("World");
publisher.close();
```

**Key features:**
- Thread-safe data emission
- Automatic backpressure handling
- Configurable buffer size
- Handles multiple subscribers

**Use cases:** Event systems, data streaming, async processing pipelines.

---

## JUnit Testing

### Q6: What is JUnit? Why is unit testing important?

**Answer:**

**JUnit** is the standard testing framework for Java. JUnit 5 (Jupiter) is the current version with modern features.

**Why unit testing matters:**

| Benefit | Description |
|---------|-------------|
| **Bug Detection** | Find bugs early, before production |
| **Regression Prevention** | Ensure changes don't break existing functionality |
| **Documentation** | Tests show how code should be used |
| **Design Quality** | Testable code tends to be better designed |
| **Confidence** | Refactor with confidence |
| **Cost Savings** | Early bug detection is cheaper |

**The Testing Pyramid:**
```
        /\
       /  \     E2E Tests (few, slow, expensive)
      /----\
     /      \   Integration Tests (some)
    /--------\
   /          \ Unit Tests (many, fast, cheap)
  /------------\
```

**Key principle:** Most tests should be unit tests because they're fast, reliable, and cheap to maintain.

---

### Q7: Explain the core JUnit 5 annotations and their lifecycle.

**Answer:**

**Core Annotations:**

| Annotation | Purpose | When Called |
|------------|---------|-------------|
| `@Test` | Marks a test method | Per test |
| `@BeforeEach` | Setup before each test | Before every @Test |
| `@AfterEach` | Cleanup after each test | After every @Test |
| `@BeforeAll` | Setup once before all tests | Once, at start (static) |
| `@AfterAll` | Cleanup once after all tests | Once, at end (static) |
| `@DisplayName` | Custom test name | N/A |
| `@Disabled` | Skip this test | N/A |

**Lifecycle Example:**

```java
class CalculatorTest {

    @BeforeAll
    static void setupOnce() {
        System.out.println("1. BeforeAll - runs once");
    }

    @BeforeEach
    void setupEach() {
        System.out.println("2. BeforeEach - before each test");
    }

    @Test
    @DisplayName("Should add two numbers")
    void testAddition() {
        System.out.println("3. Test running");
        assertEquals(5, 2 + 3);
    }

    @AfterEach
    void teardownEach() {
        System.out.println("4. AfterEach - after each test");
    }

    @AfterAll
    static void teardownOnce() {
        System.out.println("5. AfterAll - runs once at end");
    }
}
```

**Execution order for 2 tests:**
```
BeforeAll → BeforeEach → Test1 → AfterEach → BeforeEach → Test2 → AfterEach → AfterAll
```

---

### Q8: What is the AAA pattern in testing?

**Answer:**

**AAA** stands for **Arrange-Act-Assert**, a pattern for structuring unit tests clearly.

```java
@Test
void shouldCalculateOrderTotal() {
    // ARRANGE - Set up test data and dependencies
    Order order = new Order();
    order.addItem(new Item("Book", 29.99));
    order.addItem(new Item("Pen", 4.99));

    // ACT - Execute the method being tested
    double total = order.calculateTotal();

    // ASSERT - Verify the expected outcome
    assertEquals(34.98, total, 0.01);
}
```

| Phase | Purpose | What to Include |
|-------|---------|-----------------|
| **Arrange** | Setup | Create objects, configure mocks, prepare data |
| **Act** | Execute | Call the method under test (usually one line) |
| **Assert** | Verify | Check results match expectations |

**Best practices:**
- Keep each phase clearly separated
- One "Act" per test
- Multiple assertions are OK if testing one logical concept
- Use `assertAll()` for related assertions

```java
@Test
void shouldCreateUser() {
    // Arrange
    UserService service = new UserService();

    // Act
    User user = service.create("john", "john@email.com");

    // Assert - multiple related assertions
    assertAll(
        () -> assertNotNull(user.getId()),
        () -> assertEquals("john", user.getUsername()),
        () -> assertEquals("john@email.com", user.getEmail())
    );
}
```

---

### Q9: How do you test for exceptions in JUnit 5?

**Answer:**

Use `assertThrows()` to verify that a method throws the expected exception:

```java
@Test
void shouldThrowExceptionForInvalidInput() {
    Calculator calc = new Calculator();

    // Assert that dividing by zero throws IllegalArgumentException
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> calc.divide(10, 0)
    );

    // Optionally verify the exception message
    assertEquals("Cannot divide by zero", exception.getMessage());
}
```

**Testing exception message:**

```java
@Test
void shouldThrowExceptionWithSpecificMessage() {
    UserService service = new UserService();

    Exception exception = assertThrows(ValidationException.class, () -> {
        service.createUser(null);
    });

    assertTrue(exception.getMessage().contains("Username cannot be null"));
}
```

**Testing that NO exception is thrown:**

```java
@Test
void shouldNotThrowException() {
    assertDoesNotThrow(() -> {
        calculator.divide(10, 2);
    });
}
```

---

## Mockito Framework

### Q10: What is Mockito? Why do we need mocking in tests?

**Answer:**

**Mockito** is a mocking framework that creates test doubles automatically. It simplifies isolating the unit under test from its dependencies.

**Why mock?**

| Reason | Example |
|--------|---------|
| **Isolation** | Test service without real database |
| **Speed** | Avoid slow external calls |
| **Control** | Simulate specific scenarios (errors, edge cases) |
| **Reliability** | Tests don't depend on external services |
| **Determinism** | Same inputs always give same outputs |

**Without mocking:**
```java
// Problem: Depends on real database
class UserServiceTest {
    @Test
    void testFindUser() {
        UserService service = new UserService(new RealDatabase()); // Slow!
        User user = service.findById(1L);  // Needs actual DB data
    }
}
```

**With mocking:**
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // Fake database

    @InjectMocks
    private UserService userService;  // Real service with mock injected

    @Test
    void testFindUser() {
        // Control what the mock returns
        when(userRepository.findById(1L)).thenReturn(new User(1L, "john"));

        User user = userService.findById(1L);

        assertEquals("john", user.getUsername());
    }
}
```

---

### Q11: Explain @Mock, @InjectMocks, and @ExtendWith in Mockito.

**Answer:**

| Annotation | Purpose |
|------------|---------|
| `@ExtendWith(MockitoExtension.class)` | Enables Mockito annotations for the test class |
| `@Mock` | Creates a mock object (fake implementation) |
| `@InjectMocks` | Creates real object and injects mocks into it |
| `@Spy` | Creates partial mock (real object with some methods mocked) |
| `@Captor` | Captures arguments passed to mock methods |

**Example:**

```java
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;  // Mock dependency

    @Mock
    private PaymentService paymentService;    // Another mock

    @InjectMocks
    private OrderService orderService;        // Real service under test

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @Test
    void shouldProcessOrder() {
        // Arrange
        Order order = new Order(100.0);
        when(paymentService.process(any())).thenReturn(true);

        // Act
        orderService.processOrder(order);

        // Assert - capture and verify saved order
        verify(orderRepository).save(orderCaptor.capture());
        assertEquals(100.0, orderCaptor.getValue().getAmount());
    }
}
```

**How injection works:**
```java
// Mockito automatically does this:
OrderService orderService = new OrderService(orderRepository, paymentService);
```

---

### Q12: What is the difference between when().thenReturn() and verify()?

**Answer:**

| Method | Purpose | When Used |
|--------|---------|-----------|
| `when().thenReturn()` | **Stubbing** - Define what mock returns | BEFORE calling method under test |
| `verify()` | **Verification** - Check if mock was called | AFTER calling method under test |

**Stubbing (when/thenReturn):**
```java
@Test
void testStubbing() {
    // ARRANGE - Tell mock what to return
    when(userRepository.findById(1L)).thenReturn(new User(1L, "john"));
    when(userRepository.findById(99L)).thenReturn(null);

    // ACT
    User found = userService.getUser(1L);
    User notFound = userService.getUser(99L);

    // ASSERT
    assertEquals("john", found.getUsername());
    assertNull(notFound);
}
```

**Verification (verify):**
```java
@Test
void testVerification() {
    // ARRANGE
    User user = new User("john");

    // ACT
    userService.saveUser(user);

    // ASSERT - Verify the mock was called correctly
    verify(userRepository).save(user);           // Called exactly once
    verify(userRepository, times(1)).save(user); // Same as above
    verify(userRepository, never()).delete(any()); // Never called
}
```

**Common verification modes:**

```java
verify(mock, times(3)).method();    // Called exactly 3 times
verify(mock, atLeast(2)).method();  // Called 2 or more times
verify(mock, atMost(5)).method();   // Called at most 5 times
verify(mock, never()).method();     // Never called
verifyNoMoreInteractions(mock);     // No other methods called
```

---

### Q13: What are argument matchers in Mockito?

**Answer:**

**Argument matchers** allow flexible matching of method arguments instead of exact values.

```java
import static org.mockito.ArgumentMatchers.*;

@Test
void testArgumentMatchers() {
    // Match any Long
    when(userRepository.findById(anyLong())).thenReturn(new User(1L, "test"));

    // Match any String
    when(userRepository.findByUsername(anyString())).thenReturn(new User());

    // Match specific condition
    when(userRepository.findById(argThat(id -> id > 0))).thenReturn(new User());

    // Match null
    when(userRepository.findByUsername(isNull())).thenReturn(null);

    // Match non-null
    when(userRepository.findByUsername(notNull())).thenReturn(new User());
}
```

**Common matchers:**

| Matcher | Description |
|---------|-------------|
| `any()` | Any value including null |
| `anyInt()`, `anyLong()` | Any primitive value |
| `anyString()` | Any string |
| `anyList()`, `anyMap()` | Any collection |
| `eq(value)` | Equal to specific value |
| `isNull()` | Null value |
| `notNull()` | Non-null value |
| `argThat(lambda)` | Custom condition |

**Important rule:** If you use matchers, ALL arguments must use matchers:

```java
// WRONG - mixing matchers and values
when(service.method(anyString(), "literal")).thenReturn(result);

// CORRECT - use eq() for literal values
when(service.method(anyString(), eq("literal"))).thenReturn(result);
```

---

## Test-Driven Development

### Q14: What is Test-Driven Development (TDD)?

**Answer:**

**TDD** is a development methodology where you write tests BEFORE writing the implementation code.

**The Red-Green-Refactor Cycle:**

```
    ┌─────────────┐
    │  1. RED     │  Write failing test
    │  (Test)     │
    └──────┬──────┘
           │
           ▼
    ┌─────────────┐
    │  2. GREEN   │  Write minimum code to pass
    │  (Code)     │
    └──────┬──────┘
           │
           ▼
    ┌─────────────┐
    │  3. REFACTOR│  Improve code quality
    │  (Clean)    │
    └──────┬──────┘
           │
           └──────► Repeat
```

**Example - Building a Password Validator:**

```java
// Step 1: RED - Write failing test
@Test
void passwordMustBeAtLeast8Characters() {
    PasswordValidator validator = new PasswordValidator();
    assertFalse(validator.isValid("short"));  // Test fails - class doesn't exist!
}

// Step 2: GREEN - Minimum code to pass
public class PasswordValidator {
    public boolean isValid(String password) {
        return password.length() >= 8;
    }
}

// Step 3: REFACTOR - Clean up if needed (code is simple, nothing to refactor)

// Repeat: Add next test
@Test
void passwordMustContainNumber() {
    PasswordValidator validator = new PasswordValidator();
    assertFalse(validator.isValid("NoNumbers"));
}
// Then implement...
```

**Benefits of TDD:**
- Tests are always written
- Better design emerges
- Documentation through tests
- Confidence in code
- Prevents over-engineering

---

### Q15: What is the difference between a mock, stub, and spy?

**Answer:**

| Type | Description | Behavior | Use Case |
|------|-------------|----------|----------|
| **Stub** | Returns predefined values | Dumb - just returns data | Need controlled return values |
| **Mock** | Tracks interactions | Smart - verifies calls were made | Need to verify behavior |
| **Spy** | Wraps real object | Partial - real methods by default | Need some real, some mocked |

**Stub Example:**
```java
// Stub - just returns data
when(userRepository.findById(1L)).thenReturn(new User("john"));
// We only care about the return value, not if it was called
```

**Mock Example:**
```java
// Mock - verify interactions
userService.deleteUser(1L);
verify(userRepository).delete(1L);  // Was delete called?
verify(eventPublisher).publish(any(UserDeletedEvent.class));  // Was event sent?
```

**Spy Example:**
```java
@Spy
private ArrayList<String> list = new ArrayList<>();

@Test
void testSpy() {
    list.add("one");     // Real method called
    list.add("two");     // Real method called

    assertEquals(2, list.size());  // Real size

    // Override specific method
    doReturn(100).when(list).size();
    assertEquals(100, list.size());  // Mocked size
}
```

**When to use each:**
- **Stub**: Test relies on specific return values
- **Mock**: Test needs to verify that certain methods were called
- **Spy**: Need real behavior but want to override specific methods

---

## Logging Fundamentals

### Q16: Why use a logging framework instead of System.out.println()?

**Answer:**

| Aspect | System.out.println() | Logging Framework |
|--------|---------------------|-------------------|
| **Control** | No control over output | Configurable levels, targets |
| **Performance** | Always executes | Can disable by level |
| **Output Format** | Fixed format | Customizable layouts |
| **Output Target** | Console only | Console, files, database, email |
| **Production Use** | Hard to disable | Easy configuration changes |
| **Thread Safety** | Thread-safe but slow | Optimized for multi-threading |
| **Filtering** | No filtering | Filter by level, class, pattern |
| **Log Rotation** | No support | Built-in file rotation |

**Problem with println:**
```java
public void processOrder(Order order) {
    System.out.println("Processing order: " + order.getId());  // Always prints
    // Can't disable in production
    // No timestamp, no severity, no context
}
```

**With logging:**
```java
private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

public void processOrder(Order order) {
    logger.debug("Processing order: {}", order.getId());  // Only if DEBUG enabled
    // In production, set level to INFO and debug logs disappear
}
```

---

### Q17: What are log levels? Explain the hierarchy.

**Answer:**

Log levels indicate the severity/importance of a message. Log4J levels (most to least severe):

```
FATAL  ─┐
ERROR  ─┤
WARN   ─┼──► Setting level to WARN shows WARN, ERROR, FATAL only
INFO   ─┤
DEBUG  ─┤
TRACE  ─┘
```

| Level | When to Use | Example |
|-------|-------------|---------|
| **FATAL** | Application cannot continue | Database connection failed at startup |
| **ERROR** | Something failed but app continues | Payment processing failed |
| **WARN** | Potential problem | Deprecated API used, retry needed |
| **INFO** | Important milestones | User logged in, order completed |
| **DEBUG** | Detailed diagnostic info | Method entry/exit, variable values |
| **TRACE** | Most detailed, verbose | Every step in an algorithm |

**Code example:**
```java
logger.trace("Entering method with param: {}", param);
logger.debug("User {} attempting login", username);
logger.info("User {} logged in successfully", username);
logger.warn("Failed login attempt {} for user {}", attempts, username);
logger.error("Database connection failed", exception);
logger.fatal("Cannot start application - configuration missing");
```

**Production settings:**
- Development: DEBUG or TRACE
- Production: INFO or WARN
- Troubleshooting: Temporarily lower to DEBUG

---

### Q18: What is the difference between Log4J and SLF4J?

**Answer:**

| Aspect | Log4J | SLF4J |
|--------|-------|-------|
| **Type** | Logging implementation | Logging facade (abstraction) |
| **Purpose** | Actually writes logs | Delegates to implementation |
| **Direct use** | Yes | Yes, but needs binding |
| **Flexibility** | Tied to Log4J | Can switch implementations |

**Architecture:**

```
Your Code
    │
    ▼
┌─────────────┐
│    SLF4J    │  ◄── Facade (interface)
└──────┬──────┘
       │
       ▼  (binding)
┌─────────────┐
│   Log4J     │  ◄── Implementation
│  Logback    │      (choose one)
│  JUL        │
└─────────────┘
```

**Why use SLF4J?**
1. **Flexibility**: Switch from Log4J to Logback without code changes
2. **Library compatibility**: Libraries use SLF4J, you pick implementation
3. **Industry standard**: Most Java libraries use SLF4J

**Usage:**
```java
// SLF4J API - your code uses this
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClass {
    private static final Logger logger = LoggerFactory.getLogger(MyClass.class);

    public void doSomething() {
        logger.info("Doing something");  // SLF4J API
        // Actual logging handled by whatever binding you have (Log4J, Logback, etc.)
    }
}
```

**Recommendation:** Use SLF4J as your API with Log4J 2 or Logback as implementation.

---

## Log4J and SLF4J

### Q19: What are appenders in Log4J?

**Answer:**

**Appenders** define WHERE log messages are written. Multiple appenders can be configured simultaneously.

| Appender | Output | Use Case |
|----------|--------|----------|
| **ConsoleAppender** | Standard output (terminal) | Development, containerized apps |
| **FileAppender** | Single file | Simple file logging |
| **RollingFileAppender** | Multiple files with rotation | Production (prevents huge files) |
| **JDBCAppender** | Database table | Audit logs, searchable logs |
| **SMTPAppender** | Email | Critical error alerts |
| **SocketAppender** | Network | Centralized logging |

**Configuration example (log4j2.xml):**
```xml
<Configuration>
    <Appenders>
        <!-- Console output -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>

        <!-- Rolling file - new file daily or when size exceeds 10MB -->
        <RollingFile name="File" fileName="logs/app.log"
                     filePattern="logs/app-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout pattern="%d %p %c{1} - %m%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="10 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="30"/>
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

### Q20: What is a PatternLayout? Explain common conversion patterns.

**Answer:**

**PatternLayout** defines the FORMAT of log messages using conversion patterns.

**Example pattern:**
```
%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n

Output: 2024-01-15 14:30:45 [main] INFO  com.example.UserService - User logged in
```

**Common patterns:**

| Pattern | Description | Example Output |
|---------|-------------|----------------|
| `%d{pattern}` | Date/time | `2024-01-15 14:30:45` |
| `%t` | Thread name | `main` |
| `%level` or `%p` | Log level | `INFO` |
| `%-5level` | Level, left-padded to 5 chars | `INFO ` |
| `%logger{36}` | Logger name (max 36 chars) | `com.example.MyClass` |
| `%c{1}` | Logger short name | `MyClass` |
| `%msg` or `%m` | Log message | `User logged in` |
| `%n` | Newline | (line break) |
| `%ex` | Exception stack trace | (full stack trace) |
| `%L` | Line number | `42` |
| `%M` | Method name | `processOrder` |

**Production pattern:**
```
%d{ISO8601} [%t] %-5level %logger{36} - %msg%n%throwable
```

**Development pattern (with colors):**
```
%highlight{%-5level} %style{%d{HH:mm:ss}}{dim} %cyan{%logger{36}} - %msg%n
```

---

### Q21: What are logging best practices?

**Answer:**

**1. Use appropriate log levels:**
```java
// Good
logger.debug("Processing item {}", item.getId());
logger.info("Order {} completed successfully", orderId);
logger.error("Payment failed for order {}", orderId, exception);

// Bad - everything at same level
logger.info("Debug: entering method");
logger.info("Error: something failed");
```

**2. Use parameterized logging:**
```java
// Good - parameter substitution (efficient)
logger.debug("User {} logged in from {}", username, ipAddress);

// Bad - string concatenation (wasteful if DEBUG disabled)
logger.debug("User " + username + " logged in from " + ipAddress);
```

**3. Log actionable information:**
```java
// Good - includes context needed to debug
logger.error("Failed to process order {} for user {}: {}",
    orderId, userId, exception.getMessage(), exception);

// Bad - no context
logger.error("Something went wrong");
```

**4. Don't log sensitive data:**
```java
// Bad - logs password!
logger.debug("User login: {} / {}", username, password);

// Good - mask sensitive data
logger.debug("User login attempt: {}", username);
```

**5. Use MDC for request context:**
```java
MDC.put("requestId", requestId);
MDC.put("userId", userId);
try {
    // All logs in this block include requestId and userId
    logger.info("Processing request");
} finally {
    MDC.clear();
}
```

**6. Configure appropriately per environment:**
- **Development**: DEBUG level, console appender
- **Production**: INFO level, rolling file appender
- **Troubleshooting**: Temporarily lower level for specific packages

---

### Q22: How do you configure Log4J 2 for different environments?

**Answer:**

**Option 1: Multiple configuration files**
```
src/main/resources/
├── log4j2.xml           # Default (production)
├── log4j2-dev.xml       # Development
└── log4j2-test.xml      # Testing
```

Set via system property:
```bash
java -Dlog4j.configurationFile=log4j2-dev.xml -jar app.jar
```

**Option 2: Environment-specific sections in one file:**
```xml
<Configuration>
    <Properties>
        <Property name="logLevel">${env:LOG_LEVEL:-INFO}</Property>
    </Properties>

    <Loggers>
        <Root level="${logLevel}">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

```bash
# Production
LOG_LEVEL=WARN java -jar app.jar

# Development
LOG_LEVEL=DEBUG java -jar app.jar
```

**Option 3: Spring profiles (Spring Boot):**
```yaml
# application.yml
logging:
  level:
    root: INFO

---
spring:
  config:
    activate:
      on-profile: dev
logging:
  level:
    root: DEBUG
    com.myapp: TRACE
```

**Best practices:**
- Production: INFO level, file + optional console
- Development: DEBUG level, console only
- Test: WARN level, console only (less noise)

---

### Q23: What is the difference between synchronous and asynchronous logging?

**Answer:**

| Aspect | Synchronous | Asynchronous |
|--------|-------------|--------------|
| **Behavior** | Caller waits for log write | Caller continues immediately |
| **Performance** | Slower (I/O on caller thread) | Faster (I/O on background thread) |
| **Order** | Guaranteed order | Order mostly preserved |
| **Data Loss** | None | Possible on crash |
| **Use Case** | Simple apps, debugging | High-throughput production |

**Synchronous (default):**
```
Thread → Logger → Appender → Write to file → Return
         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         Caller waits for all this
```

**Asynchronous:**
```
Thread → Async Logger → Queue → Return immediately
                          ↓
         Background Thread → Appender → Write to file
```

**Configuring Async in Log4J 2:**

Option 1: Async Appender (wrap existing appender)
```xml
<Appenders>
    <File name="File" fileName="app.log">
        <PatternLayout pattern="%d %p %m%n"/>
    </File>
    <Async name="AsyncFile">
        <AppenderRef ref="File"/>
    </Async>
</Appenders>
```

Option 2: All Async Loggers (best performance)
```
# Add system property
-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
```

**When to use async:**
- High-throughput applications (1000+ logs/sec)
- Latency-sensitive operations
- When I/O shouldn't block business logic

**Caveat:** In case of application crash, some buffered logs may be lost.

---

### Q24: How do you integrate SLF4J with Log4J 2?

**Answer:**

**Maven dependencies:**
```xml
<!-- SLF4J API -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>

<!-- Log4J 2 implementation -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.21.0</version>
</dependency>

<!-- Bridge: SLF4J to Log4J 2 -->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>2.21.0</version>
</dependency>
```

**Code usage (always use SLF4J API):**
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyService {
    // Use SLF4J API
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    public void doWork() {
        logger.info("Starting work");
        logger.debug("Debug details: {}", details);
    }
}
```

**Configuration (log4j2.xml works as normal):**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

**Key point:** Your code uses SLF4J API, but Log4J 2 handles the actual logging. You can later switch to Logback by just changing dependencies.

---

## Summary

| Topic | Key Concepts |
|-------|--------------|
| **Reactive Programming** | Data streams, push-based, backpressure, Publisher/Subscriber |
| **Reactive Manifesto** | Responsive, Resilient, Elastic, Message-Driven |
| **JUnit 5** | @Test, @BeforeEach, @AfterEach, assertions, AAA pattern |
| **Mockito** | @Mock, @InjectMocks, when().thenReturn(), verify() |
| **TDD** | Red-Green-Refactor, tests first, minimum code |
| **Logging** | Levels (TRACE→FATAL), appenders, patterns |
| **Log4J 2** | Configuration, rolling files, async logging |
| **SLF4J** | Logging facade, implementation-agnostic |

---

*Week 05 covers the advanced Java topics that are essential for building production-ready, testable, and maintainable applications.*
