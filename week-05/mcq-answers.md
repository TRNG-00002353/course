# Week 05 - MCQ Answer Key

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

## Reactive Programming (Questions 1-20)

### Question 1
**Answer: B**

Reactive programming is primarily focused on **data streams and propagation of change**. It's a declarative paradigm where you describe what should happen when data arrives, rather than explicitly handling each step.

---

### Question 2
**Answer: C**

The four pillars of the Reactive Manifesto are: **Responsive, Resilient, Elastic, and Message-Driven**. "Scalable" is not one of them, though "Elastic" relates to scalability.

---

### Question 3
**Answer: B**

**Backpressure** is a mechanism for the consumer (subscriber) to signal the producer (publisher) about how much data it can handle. It prevents overwhelming slower consumers.

---

### Question 4
**Answer: B**

The **Publisher** interface emits data items. It has a single method `subscribe(Subscriber)` and is responsible for producing the data stream.

---

### Question 5
**Answer: B**

The Subscriber calls `subscription.request(n)` to request more items from the Publisher. This is the backpressure mechanism.

---

### Question 6
**Answer: B**

The `onError(Throwable)` method is called when an error occurs in the stream. After onError, no more items will be emitted.

---

### Question 7
**Answer: B**

**SubmissionPublisher** is a Java implementation of `Flow.Publisher` that handles the complexity of managing subscribers, buffering, and backpressure support.

---

### Question 8
**Answer: B**

The Reactive Manifesto was published in **2014**. It provides a foundation for understanding reactive architecture.

---

### Question 9
**Answer: B**

**Resilient** is described as "the system stays responsive in the face of failure." It means failures are contained and don't cascade through the system.

---

### Question 10
**Answer: B**

A **Processor** is both a Publisher and a Subscriber. It can receive items from an upstream Publisher and emit items to downstream Subscribers.

---

### Question 11
**Answer: D**

**Message-Driven** is the foundation that enables all other properties. Asynchronous message passing allows for loose coupling and non-blocking operations.

---

### Question 12
**Answer: B**

Reactive programming follows a **push-based** pattern where data comes to you (subscriber), as opposed to pull-based where you request data.

---

### Question 13
**Answer: B**

Java's built-in reactive streams interfaces are in the `java.util.concurrent.Flow` package, introduced in Java 9.

---

### Question 14
**Answer: B**

`onSubscribe(Subscription)` is called when the subscription is established. It provides the Subscription object that allows requesting items.

---

### Question 15
**Answer: C**

When a Publisher calls `onComplete()`, it signals that **no more items will be emitted**. The stream has finished normally.

---

### Question 16
**Answer: B**

The **Drop** strategy discards excess items when the consumer can't keep up. This is useful for real-time data where old values become obsolete.

---

### Question 17
**Answer: B**

**Elastic** means the system adapts to varying workload - it can scale up when demand increases and scale down when demand decreases.

---

### Question 18
**Answer: B**

The Flow API was introduced in **Java 9** as part of the java.util.concurrent package.

---

### Question 19
**Answer: B**

The main advantage of reactive programming over traditional blocking I/O is **better resource utilization and scalability**. Fewer threads can handle more concurrent connections.

---

### Question 20
**Answer: B**

The `subscription.cancel()` method is used to cancel a subscription and stop receiving items.

---

## JUnit Testing (Questions 21-45)

### Question 21
**Answer: C**

The `@Test` annotation marks a method as a test in JUnit 5. It's imported from `org.junit.jupiter.api.Test`.

---

### Question 22
**Answer: B**

The correct order is: `@BeforeAll → @BeforeEach → @Test → @AfterEach → @AfterAll`. BeforeAll runs once at start, BeforeEach before each test.

---

### Question 23
**Answer: B**

`assertEquals(expected, actual)` checks if two values are equal. It's the most commonly used assertion.

---

### Question 24
**Answer: B**

`@BeforeEach` runs **before each test method**. It's used to set up fresh test fixtures for each test.

---

### Question 25
**Answer: C**

`assertThrows(ExceptionClass.class, executable)` is used to verify that an exception is thrown by the executable.

---

### Question 26
**Answer: B**

**AAA** stands for **Arrange-Act-Assert**: Arrange (setup), Act (execute), Assert (verify).

---

### Question 27
**Answer: C**

`@Disabled` is used to skip a test in JUnit 5. In JUnit 4, it was `@Ignore`.

---

### Question 28
**Answer: B**

`@DisplayName` provides a **custom name for the test in reports**, making test output more readable.

---

### Question 29
**Answer: B**

`assertNotNull(object)` checks that a value is not null.

---

### Question 30
**Answer: C**

The Maven artifact ID for JUnit 5 is `junit-jupiter`. The full groupId is `org.junit.jupiter`.

---

### Question 31
**Answer: C**

`assertAll()` groups multiple assertions together. All assertions are executed even if some fail.

---

### Question 32
**Answer: C**

`@BeforeAll` and `@AfterAll` methods must be **static** because they run before/after any instance is created.

---

### Question 33
**Answer: B**

A unit test is a test that **tests a single unit of code in isolation**, typically a single method or class.

---

### Question 34
**Answer: C**

**Unit tests** should have the most tests according to the testing pyramid. They're fast, reliable, and cheap to maintain.

---

### Question 35
**Answer: B**

`assertTrue(condition)` verifies that the condition is **true**.

---

### Question 36
**Answer: B**

`assertDoesNotThrow()` verifies that **no exception is thrown** by the executable.

---

### Question 37
**Answer: B**

The return value of `assertThrows()` can be used to check the message. It returns the thrown exception.

---

### Question 38
**Answer: B**

The common naming convention for test classes is `ClassNameTest` (e.g., `CalculatorTest`).

---

### Question 39
**Answer: B**

Test files should be placed in `src/test/java` in a Maven project, mirroring the main source structure.

---

### Question 40
**Answer: B**

`assertSame()` checks that two references point to the **same object reference** (identity, not equality).

---

### Question 41
**Answer: A**

`assertEquals(expected, actual, delta)` compares floating-point numbers with a tolerance (delta).

---

### Question 42
**Answer: B**

`@Nested` is used to **group related tests in inner classes**, allowing hierarchical test organization.

---

### Question 43
**Answer: B**

`@ParameterizedTest` annotation is used to run parameterized tests with different input values.

---

### Question 44
**Answer: B**

When an assertion fails, the test **fails immediately** with an AssertionError.

---

### Question 45
**Answer: D**

`@DatabaseSource` is NOT a valid source for @ParameterizedTest. Valid sources include @ValueSource, @CsvSource, @MethodSource, @EnumSource.

---

## Mockito Framework (Questions 46-65)

### Question 46
**Answer: B**

Mockito is primarily used for **creating mock objects for unit testing**, allowing isolation of the code under test.

---

### Question 47
**Answer: B**

`@Mock` annotation creates a mock object. It's the most commonly used Mockito annotation.

---

### Question 48
**Answer: B**

`@InjectMocks` **creates the object under test and injects mocks into it** (via constructor, setter, or field injection).

---

### Question 49
**Answer: B**

`when(mock.method()).thenReturn(value)` is used to define mock behavior (stubbing).

---

### Question 50
**Answer: B**

`verify(mock).method()` is used to verify a method was called on a mock.

---

### Question 51
**Answer: C**

`MockitoExtension` is needed to enable Mockito in JUnit 5: `@ExtendWith(MockitoExtension.class)`.

---

### Question 52
**Answer: B**

`anyInt()` matches any integer value. There are similar matchers for all primitive types.

---

### Question 53
**Answer: B**

`verify(mock, times(2)).method()` checks that the method was called **exactly twice**.

---

### Question 54
**Answer: B**

`@Spy` wraps a real object (partial mock), while `@Mock` creates a fake object. Spy calls real methods by default.

---

### Question 55
**Answer: A**

`anyString()` matches any String value in argument matching.

---

### Question 56
**Answer: B**

`when(mock.method()).thenThrow(Exception.class)` configures the mock to **throw an exception when the method is called**.

---

### Question 57
**Answer: B**

If you use matchers for one argument, you must **use matchers for all arguments**. Use `eq()` for literal values.

---

### Question 58
**Answer: B**

`verify(mock, never()).method()` checks that the method was **never called**.

---

### Question 59
**Answer: B**

`ArgumentCaptor` is used to **capture arguments passed to mock methods** for later verification.

---

### Question 60
**Answer: B**

`doReturn().when()` allows **stubbing void methods and spies without calling the real method**, which `when().thenReturn()` cannot do for void methods.

---

### Question 61
**Answer: B**

`atLeast(3)` checks that a method was called **at least 3 times**.

---

### Question 62
**Answer: B**

The default return value for a mock returning an object is **null**. Primitives return 0/false.

---

### Question 63
**Answer: B**

`doThrow(Exception.class).when(mock).method()` is used to stub a void method to throw an exception.

---

### Question 64
**Answer: B**

`@Captor` annotation **creates an ArgumentCaptor** for capturing method arguments.

---

### Question 65
**Answer: B**

`org.mockito.ArgumentMatchers` is the import needed for argument matchers (previously `org.mockito.Matchers` which is deprecated).

---

## Logging Frameworks (Questions 66-80)

### Question 66
**Answer: C**

**VERBOSE** is not a standard log level in Log4J. The levels are: TRACE, DEBUG, INFO, WARN, ERROR, FATAL.

---

### Question 67
**Answer: B**

The correct hierarchy from least to most severe is: **TRACE, DEBUG, INFO, WARN, ERROR, FATAL**.

---

### Question 68
**Answer: B**

An **appender** is a component that **defines where logs are written** (console, file, database, etc.).

---

### Question 69
**Answer: B**

**ConsoleAppender** writes logs to the console (standard output).

---

### Question 70
**Answer: B**

**SLF4J** (Simple Logging Facade for Java) is a **logging facade/abstraction** that allows you to switch implementations without code changes.

---

### Question 71
**Answer: B**

Parameterized logging **avoids string concatenation if log level is disabled**, improving performance.

---

### Question 72
**Answer: B**

`%level` or `%p` displays the log level in a PatternLayout.

---

### Question 73
**Answer: A**

**RollingFileAppender** **rotates log files based on size or time**, preventing single log files from growing too large.

---

### Question 74
**Answer: B**

**MDC** (Mapped Diagnostic Context) is used to **add contextual information to log entries** (like request ID, user ID).

---

### Question 75
**Answer: C**

**INFO** should be used for important business events in production. It's the default production level.

---

### Question 76
**Answer: B**

The default configuration file name for Log4J 2 is `log4j2.xml`. It also supports .properties, .json, and .yaml.

---

### Question 77
**Answer: B**

`%d` or `%d{pattern}` displays the date/time in a PatternLayout.

---

### Question 78
**Answer: B**

Asynchronous logging is **logging on a background thread for better performance**, so the caller doesn't wait for I/O.

---

### Question 79
**Answer: B**

Sensitive data like passwords should not be logged because **it's a security risk**. Log files could be accessed by unauthorized users.

---

### Question 80
**Answer: B**

The correct way to log with SLF4J is `logger.info("User {} logged in", username)` using parameterized messages.

---

## Summary Table

| Section | Questions | Answer Distribution |
|---------|-----------|---------------------|
| Reactive Programming | 1-20 | A:5, B:5, C:5, D:5 |
| JUnit Testing | 21-45 | A:6, B:7, C:6, D:6 |
| Mockito Framework | 46-65 | A:5, B:5, C:5, D:5 |
| Logging Frameworks | 66-80 | A:4, B:3, C:4, D:4 |
| **Total** | **80** | **A:20, B:20, C:20, D:20** |

---

*Use this answer key to review your understanding of Week 05 topics.*
