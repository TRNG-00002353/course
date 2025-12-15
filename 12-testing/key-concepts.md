# Testing Key Concepts for Application Developers

## Overview

This document covers essential testing concepts using JUnit 5 and Mockito that every Java application developer must master. Testing is fundamental to building reliable, maintainable software. Understanding unit testing, test-driven development, and mocking frameworks is critical for professional software development and modern CI/CD workflows.

---

## 1. Unit Testing Fundamentals

### Why It Matters
- Catches bugs early in development cycle
- Enables safe refactoring and code changes
- Documents expected behavior
- Required for CI/CD pipelines
- Improves code design and modularity

### Key Concepts

| Concept | Description | Developer Use Case |
|---------|-------------|-------------------|
| Unit Test | Tests single method/class | Verify individual components |
| Test Case | Single test scenario | Specific behavior validation |
| Test Suite | Collection of test cases | Group related tests |
| Test Fixture | Test setup/teardown | Prepare test environment |
| Assertion | Expected vs actual check | Verify outcomes |
| Test Coverage | Code executed by tests | Measure test completeness |

### Testing Pyramid

```
         /\
        /  \  E2E Tests (Few)
       /----\
      /      \  Integration Tests (Some)
     /--------\
    /          \  Unit Tests (Many)
   /____________\
```

### Test Types Comparison

| Type | Scope | Speed | Cost | Purpose |
|------|-------|-------|------|---------|
| Unit | Single class/method | Fast | Low | Component logic |
| Integration | Multiple classes | Medium | Medium | Component interaction |
| System/E2E | Entire application | Slow | High | User workflows |

### JUnit 5 Architecture

```
JUnit 5 = Platform + Jupiter + Vintage

Platform: Test engine foundation
Jupiter:  JUnit 5 test API
Vintage:  JUnit 3/4 backward compatibility
```

---

## 2. JUnit 5 Basics

### Why It Matters
- Industry standard for Java testing
- Required knowledge for Java development jobs
- Foundation for TDD and test automation
- Integrates with all major build tools and IDEs

### Key Concepts

| Annotation | Purpose | Usage |
|-----------|---------|-------|
| `@Test` | Mark test method | Define test cases |
| `@BeforeEach` | Run before each test | Setup per test |
| `@AfterEach` | Run after each test | Cleanup per test |
| `@BeforeAll` | Run once before all tests | Expensive setup |
| `@AfterAll` | Run once after all tests | Final cleanup |
| `@DisplayName` | Readable test name | Descriptive labels |
| `@Disabled` | Skip test | Temporarily ignore |

### Maven Setup

```xml
<dependencies>
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0</version>
        </plugin>
    </plugins>
</build>
```

### Basic Test Class

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Tests")
class CalculatorTest {

    private Calculator calculator;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting calculator tests...");
    }

    @BeforeEach
    void init() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Addition should return sum of two numbers")
    void testAddition() {
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }

    @Test
    void testSubtraction() {
        int result = calculator.subtract(10, 4);
        assertEquals(6, result);
    }

    @AfterEach
    void tearDown() {
        calculator = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests completed.");
    }
}
```

### Test Lifecycle

```java
@BeforeAll      // Once before all tests (static)
    ↓
@BeforeEach     // Before each test
    ↓
@Test           // Test method 1
    ↓
@AfterEach      // After each test
    ↓
@BeforeEach     // Before next test
    ↓
@Test           // Test method 2
    ↓
@AfterEach      // After each test
    ↓
@AfterAll       // Once after all tests (static)
```

---

## 3. Arrange-Act-Assert Pattern

### Why It Matters
- Provides clear, consistent test structure
- Improves test readability and maintainability
- Makes test intent obvious
- Industry-standard best practice

### Key Concepts

```java
// AAA Pattern
// Arrange: Set up test data and conditions
// Act:     Execute the behavior being tested
// Assert:  Verify the expected outcome
```

### AAA Pattern Examples

```java
class UserServiceTest {

    @Test
    @DisplayName("Should create user with valid data")
    void testCreateUser() {
        // Arrange - Set up test data
        UserService service = new UserService();
        String username = "john_doe";
        String email = "john@example.com";

        // Act - Execute the method under test
        User user = service.createUser(username, email);

        // Assert - Verify expected outcomes
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("Should throw exception for duplicate username")
    void testCreateUserDuplicateUsername() {
        // Arrange
        UserService service = new UserService();
        service.createUser("existing_user", "user1@example.com");

        // Act & Assert (exception cases)
        assertThrows(DuplicateUsernameException.class, () -> {
            service.createUser("existing_user", "user2@example.com");
        });
    }
}
```

### Service Layer Test Example

```java
class OrderServiceTest {

    private OrderService orderService;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        // Arrange - Common setup for all tests
        orderService = new OrderService();
        testOrder = new Order(1, "Product A", 2, 50.00);
    }

    @Test
    @DisplayName("Should calculate correct total with tax")
    void testCalculateOrderTotal() {
        // Arrange
        double taxRate = 0.08;

        // Act
        double total = orderService.calculateTotal(testOrder, taxRate);

        // Assert
        assertEquals(108.00, total, 0.01);  // 100 + 8% tax
    }

    @Test
    @DisplayName("Should apply discount correctly")
    void testApplyDiscount() {
        // Arrange
        double discountPercent = 10.0;

        // Act
        orderService.applyDiscount(testOrder, discountPercent);

        // Assert
        assertEquals(90.00, testOrder.getSubtotal(), 0.01);
    }
}
```

---

## 4. Assertion Types

### Why It Matters
- Assertions verify test expectations
- Different assertion types for different scenarios
- Clear assertions make test failures easy to diagnose
- Foundation of all automated testing

### Key Concepts

| Assertion Type | Method | Use Case |
|---------------|--------|----------|
| Equality | `assertEquals` | Compare values |
| Boolean | `assertTrue/False` | Check conditions |
| Nullity | `assertNull/NotNull` | Check null state |
| Reference | `assertSame/NotSame` | Object identity |
| Exception | `assertThrows` | Verify exceptions |
| Timeout | `assertTimeout` | Performance tests |
| Group | `assertAll` | Multiple assertions |

### Basic Assertions

```java
import static org.junit.jupiter.api.Assertions.*;

class AssertionExamples {

    @Test
    void testBasicAssertions() {
        // Equality assertions
        assertEquals(4, 2 + 2);
        assertEquals("hello", "hello");
        assertEquals(3.14, 3.14, 0.01);  // Delta for doubles

        // Boolean assertions
        assertTrue(5 > 3);
        assertFalse(5 < 3);

        // Null assertions
        String nullString = null;
        assertNull(nullString);
        assertNotNull("not null");

        // Array assertions
        int[] expected = {1, 2, 3};
        int[] actual = {1, 2, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void testReferenceAssertions() {
        String str1 = new String("test");
        String str2 = new String("test");
        String str3 = str1;

        assertEquals(str1, str2);      // Same value
        assertNotSame(str1, str2);     // Different objects
        assertSame(str1, str3);        // Same object reference
    }
}
```

### Exception Assertions

```java
class ExceptionAssertionTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException for negative age")
    void testInvalidAge() {
        User user = new User();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> user.setAge(-5)
        );

        assertEquals("Age cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should not throw exception for valid input")
    void testValidInput() {
        assertDoesNotThrow(() -> {
            User user = new User();
            user.setAge(25);
        });
    }
}
```

### Grouped Assertions

```java
class GroupedAssertionTest {

    @Test
    @DisplayName("Should validate all user properties")
    void testUserProperties() {
        User user = new User("john_doe", "john@example.com", 30);

        // All assertions executed even if some fail
        assertAll("user properties",
            () -> assertEquals("john_doe", user.getUsername()),
            () -> assertEquals("john@example.com", user.getEmail()),
            () -> assertEquals(30, user.getAge()),
            () -> assertNotNull(user.getCreatedAt())
        );
    }

    @Test
    @DisplayName("Should complete within timeout")
    void testPerformance() {
        assertTimeout(Duration.ofMillis(100), () -> {
            // Code that should complete quickly
            Thread.sleep(50);
        });
    }
}
```

### Custom Assertion Messages

```java
@Test
void testWithCustomMessages() {
    int expected = 10;
    int actual = calculateValue();

    assertEquals(expected, actual,
        "Expected value should be 10 but was " + actual);

    assertTrue(actual > 0,
        () -> "Value should be positive but was " + actual);
}
```

---

## 5. Parameterized Tests

### Why It Matters
- Test multiple scenarios with single test method
- Reduce code duplication
- Improve test coverage efficiently
- Test edge cases systematically

### Key Concepts

```java
@ParameterizedTest
@ValueSource          // Simple values
@CsvSource            // CSV data
@MethodSource         // Complex objects
@EnumSource           // Enum values
```

### Parameterized Test Examples

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;

class ParameterizedTestExamples {

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "level"})
    @DisplayName("Should identify palindromes")
    void testPalindromes(String word) {
        assertTrue(isPalindrome(word));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 7, 9})
    @DisplayName("Should identify odd numbers")
    void testOddNumbers(int number) {
        assertTrue(number % 2 != 0);
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1, 2",
        "2, 3, 5",
        "10, 20, 30",
        "-5, 5, 0"
    })
    @DisplayName("Should add two numbers correctly")
    void testAddition(int a, int b, int expected) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.add(a, b));
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    @DisplayName("Should validate user emails")
    void testUserEmails(User user) {
        assertTrue(user.getEmail().contains("@"));
    }

    static Stream<User> userProvider() {
        return Stream.of(
            new User("john", "john@example.com"),
            new User("jane", "jane@test.com"),
            new User("bob", "bob@company.org")
        );
    }

    @ParameterizedTest
    @EnumSource(UserRole.class)
    @DisplayName("Should handle all user roles")
    void testUserRoles(UserRole role) {
        assertNotNull(role.getPermissions());
    }
}
```

---

## 6. Test Doubles: Stubs, Mocks, and Spies

### Why It Matters
- Isolate unit under test from dependencies
- Test components independently
- Control test environment
- Simulate hard-to-test scenarios (errors, edge cases)

### Key Concepts

| Type | Purpose | Verification |
|------|---------|--------------|
| Dummy | Placeholder, never used | None |
| Stub | Returns predefined values | State-based |
| Mock | Records interactions | Behavior-based |
| Spy | Partial mock, real object | Behavior-based |
| Fake | Working implementation | State-based |

### The Five Types of Test Doubles

```java
// Dummy: Passed but never used
public void processOrder(Order order, EmailService emailService) {
    // emailService never called
}

// Stub: Returns canned responses
class StubUserRepository implements UserRepository {
    public User findById(int id) {
        return new User(id, "stub_user");  // Always returns this
    }
}

// Mock: Verifies interactions
// (Created with Mockito - see next sections)

// Spy: Partial mock of real object
// (Created with Mockito - see next sections)

// Fake: Simplified working implementation
class FakeDatabase implements Database {
    private Map<Integer, User> data = new HashMap<>();

    public void save(User user) {
        data.put(user.getId(), user);
    }

    public User findById(int id) {
        return data.get(id);
    }
}
```

---

## 7. Mockito Fundamentals

### Why It Matters
- Industry-standard mocking framework for Java
- Simplifies creating and managing test doubles
- Essential for unit testing services with dependencies
- Required skill for professional Java development

### Key Concepts

| Annotation | Purpose | Usage |
|-----------|---------|-------|
| `@Mock` | Create mock object | Mock dependencies |
| `@InjectMocks` | Inject mocks into test subject | System under test |
| `@ExtendWith` | Enable Mockito | Class-level annotation |
| `@Spy` | Partial mock | Real object with stubbing |

### Maven Setup

```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>
```

### Basic Mockito Usage

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should find user by ID")
    void testFindUser() {
        // Arrange - Stub the mock
        User expectedUser = new User(1, "john_doe");
        when(userRepository.findById(1)).thenReturn(expectedUser);

        // Act
        User actualUser = userService.getUserById(1);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository).findById(1);  // Verify interaction
    }
}
```

---

## 8. Stubbing with Mockito

### Why It Matters
- Control what mocked methods return
- Simulate different scenarios and edge cases
- Test error handling without real failures
- Create predictable test environments

### Key Concepts

```java
// Return value
when(mock.method()).thenReturn(value);

// Return different values on successive calls
when(mock.method()).thenReturn(value1, value2, value3);

// Throw exception
when(mock.method()).thenThrow(new RuntimeException());

// Argument matchers
when(mock.method(anyInt())).thenReturn(value);
when(mock.method(eq(5))).thenReturn(value);

// Void methods
doNothing().when(mock).voidMethod();
doThrow(exception).when(mock).voidMethod();
```

### Stubbing Examples

```java
@ExtendWith(MockitoExtension.class)
class StubbingExamples {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should return user when found")
    void testUserFound() {
        // Arrange
        User user = new User(1, "john");
        when(userRepository.findById(1)).thenReturn(user);

        // Act
        User result = userService.getUserById(1);

        // Assert
        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    @DisplayName("Should handle user not found")
    void testUserNotFound() {
        // Arrange
        when(userRepository.findById(999)).thenReturn(null);

        // Act
        User result = userService.getUserById(999);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle database exception")
    void testDatabaseException() {
        // Arrange
        when(userRepository.findById(anyInt()))
            .thenThrow(new DatabaseException("Connection failed"));

        // Act & Assert
        assertThrows(DatabaseException.class, () -> {
            userService.getUserById(1);
        });
    }

    @Test
    @DisplayName("Should send email after user creation")
    void testCreateUser() {
        // Arrange
        User newUser = new User(0, "jane");
        User savedUser = new User(1, "jane");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        doNothing().when(emailService).sendWelcomeEmail(any(User.class));

        // Act
        userService.createUser(newUser);

        // Assert
        verify(userRepository).save(newUser);
        verify(emailService).sendWelcomeEmail(savedUser);
    }
}
```

### Argument Matchers

```java
import static org.mockito.ArgumentMatchers.*;

@Test
void testArgumentMatchers() {
    // Any value
    when(userRepository.findById(anyInt())).thenReturn(user);
    when(userRepository.findByEmail(anyString())).thenReturn(user);

    // Specific value
    when(userRepository.findById(eq(1))).thenReturn(user);

    // Null or not null
    when(service.process(isNull())).thenReturn(null);
    when(service.process(notNull())).thenReturn(result);

    // Collection matchers
    when(service.getUsers(anyList())).thenReturn(users);

    // Custom matcher
    when(repository.save(argThat(u -> u.getAge() > 18)))
        .thenReturn(savedUser);
}
```

---

## 9. Verification with Mockito

### Why It Matters
- Ensure methods were called correctly
- Verify interaction between components
- Test that side effects occurred
- Behavior-driven testing approach

### Key Concepts

```java
// Verify method called
verify(mock).method();

// Verify with arguments
verify(mock).method(eq(value));

// Verify number of invocations
verify(mock, times(3)).method();
verify(mock, never()).method();
verify(mock, atLeast(1)).method();
verify(mock, atMost(5)).method();

// Verify order
InOrder inOrder = inOrder(mock1, mock2);
inOrder.verify(mock1).method1();
inOrder.verify(mock2).method2();
```

### Verification Examples

```java
@Test
@DisplayName("Should verify user creation workflow")
void testUserCreationWorkflow() {
    // Arrange
    User user = new User("john", "john@example.com");
    when(userRepository.save(any(User.class))).thenReturn(user);

    // Act
    userService.registerUser(user);

    // Assert - Verify interactions
    verify(userRepository).save(user);
    verify(emailService).sendWelcomeEmail(user);
    verify(auditService).logUserCreation(user);
}

@Test
@DisplayName("Should not send email if save fails")
void testEmailNotSentOnFailure() {
    // Arrange
    User user = new User("john", "john@example.com");
    when(userRepository.save(any(User.class)))
        .thenThrow(new DatabaseException());

    // Act
    try {
        userService.registerUser(user);
    } catch (DatabaseException e) {
        // Expected
    }

    // Assert - Verify email was never sent
    verify(userRepository).save(user);
    verify(emailService, never()).sendWelcomeEmail(any(User.class));
}

@Test
@DisplayName("Should verify method call count")
void testCallCount() {
    // Act
    userService.processUsers(Arrays.asList(user1, user2, user3));

    // Assert
    verify(emailService, times(3)).sendEmail(any(User.class));
    verify(auditService, atLeast(1)).log(anyString());
}

@Test
@DisplayName("Should verify method call order")
void testCallOrder() {
    // Act
    orderService.placeOrder(order);

    // Assert - Verify specific order
    InOrder inOrder = inOrder(inventoryService, paymentService, notificationService);
    inOrder.verify(inventoryService).reserveItems(order);
    inOrder.verify(paymentService).processPayment(order);
    inOrder.verify(notificationService).sendConfirmation(order);
}
```

---

## 10. Spies in Mockito

### Why It Matters
- Test real objects while stubbing specific methods
- Useful for testing legacy code
- Verify calls on real implementations
- Partial mocking for integration-style tests

### Key Concepts

```java
// Create spy
@Spy
private List<String> spyList = new ArrayList<>();

// Or programmatically
List<String> list = new ArrayList<>();
List<String> spy = spy(list);
```

### Spy Examples

```java
@ExtendWith(MockitoExtension.class)
class SpyExamples {

    @Spy
    private UserService userService;

    @Test
    @DisplayName("Spy calls real methods by default")
    void testSpyRealMethod() {
        // Real method is called
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);

        spyList.add("one");
        spyList.add("two");

        assertEquals(2, spyList.size());  // Real behavior
        verify(spyList).add("one");       // Can verify
    }

    @Test
    @DisplayName("Can stub specific methods on spy")
    void testSpyStubbing() {
        List<String> spyList = spy(new ArrayList<>());

        // Stub specific method
        when(spyList.size()).thenReturn(100);

        spyList.add("one");

        assertEquals(100, spyList.size());  // Stubbed
        assertTrue(spyList.contains("one")); // Real
    }

    @Test
    @DisplayName("Use doReturn for stubbing spies")
    void testDoReturn() {
        UserService spy = spy(new UserService());

        // Use doReturn instead of when for spies
        doReturn(user).when(spy).getUserById(1);

        User result = spy.getUserById(1);
        assertEquals(user, result);
    }
}
```

---

## 11. Test-Driven Development (TDD)

### Why It Matters
- Improves code design and quality
- Ensures testable code from the start
- Provides immediate feedback
- Creates comprehensive test coverage
- Industry best practice for agile teams

### Key Concepts

```
Red-Green-Refactor Cycle:

1. RED:    Write failing test
2. GREEN:  Write minimal code to pass
3. REFACTOR: Improve code quality

Repeat for each feature
```

### The Three Laws of TDD

```
1. Write no production code except to pass a failing test
2. Write only enough of a test to demonstrate a failure
3. Write only enough production code to pass the test
```

### TDD Example: String Calculator

```java
// Iteration 1: RED - Write failing test
@Test
void testEmptyStringShouldReturnZero() {
    StringCalculator calc = new StringCalculator();
    assertEquals(0, calc.add(""));
}

// GREEN - Write minimal code to pass
public class StringCalculator {
    public int add(String numbers) {
        return 0;  // Simplest implementation
    }
}

// Iteration 2: RED - New failing test
@Test
void testSingleNumberShouldReturnTheNumber() {
    StringCalculator calc = new StringCalculator();
    assertEquals(5, calc.add("5"));
}

// GREEN - Extend implementation
public int add(String numbers) {
    if (numbers.isEmpty()) {
        return 0;
    }
    return Integer.parseInt(numbers);
}

// Iteration 3: RED - Two numbers
@Test
void testTwoNumbersShouldReturnSum() {
    StringCalculator calc = new StringCalculator();
    assertEquals(8, calc.add("3,5"));
}

// GREEN - Handle two numbers
public int add(String numbers) {
    if (numbers.isEmpty()) {
        return 0;
    }
    if (!numbers.contains(",")) {
        return Integer.parseInt(numbers);
    }
    String[] parts = numbers.split(",");
    return Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
}

// REFACTOR - Improve design
public int add(String numbers) {
    if (numbers.isEmpty()) {
        return 0;
    }

    return Arrays.stream(numbers.split(","))
        .mapToInt(Integer::parseInt)
        .sum();
}
```

### TDD Workflow in Practice

```java
// Step 1: Write test for User registration
@Test
@DisplayName("Should register new user with valid data")
void testRegisterUser() {
    // Arrange
    UserService service = new UserService();
    User user = new User("john", "john@example.com");

    // Act
    User registered = service.register(user);

    // Assert
    assertNotNull(registered.getId());
    assertEquals("john", registered.getUsername());
}

// Step 2: Write minimal code to compile
public class UserService {
    public User register(User user) {
        return null;  // Fails test
    }
}

// Step 3: Make test pass
public User register(User user) {
    user.setId(generateId());
    return user;
}

// Step 4: Add more tests, repeat cycle
@Test
@DisplayName("Should throw exception for duplicate username")
void testDuplicateUsername() {
    UserService service = new UserService();
    service.register(new User("john", "john1@example.com"));

    assertThrows(DuplicateUsernameException.class, () -> {
        service.register(new User("john", "john2@example.com"));
    });
}
```

---

## 12. Code Coverage

### Why It Matters
- Measures test completeness
- Identifies untested code paths
- Required metric for many organizations
- Helps find missing tests
- Not a guarantee of quality, but useful metric

### Key Concepts

| Coverage Type | Measures | Target |
|--------------|----------|--------|
| Line Coverage | Lines executed | 80%+ |
| Branch Coverage | Decision paths tested | 70%+ |
| Method Coverage | Methods called | 90%+ |
| Class Coverage | Classes tested | 90%+ |

### JaCoCo Maven Setup

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.11</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
                <execution>
                    <id>check</id>
                    <goals>
                        <goal>check</goal>
                    </goals>
                    <configuration>
                        <rules>
                            <rule>
                                <element>PACKAGE</element>
                                <limits>
                                    <limit>
                                        <counter>LINE</counter>
                                        <value>COVEREDRATIO</value>
                                        <minimum>0.80</minimum>
                                    </limit>
                                </limits>
                            </rule>
                        </rules>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### Running Coverage

```bash
# Generate coverage report
mvn clean test jacoco:report

# View report
open target/site/jacoco/index.html

# Enforce coverage threshold
mvn clean verify
```

### Line vs Branch Coverage Example

```java
public String getGrade(int score) {
    if (score >= 90) {           // Branch 1
        return "A";
    } else if (score >= 80) {    // Branch 2
        return "B";
    } else if (score >= 70) {    // Branch 3
        return "C";
    } else {                     // Branch 4
        return "F";
    }
}

// Line coverage: All lines executed = 100%
@Test
void testGradeA() {
    assertEquals("A", getGrade(95));
}

// Branch coverage: Only 1 of 4 branches = 25%
// Need tests for all branches for 100% branch coverage

@Test
void testAllGrades() {
    assertEquals("A", getGrade(95));  // Branch 1
    assertEquals("B", getGrade(85));  // Branch 2
    assertEquals("C", getGrade(75));  // Branch 3
    assertEquals("F", getGrade(65));  // Branch 4
}
// Now: 100% line and branch coverage
```

---

## Quick Reference Card

### Essential Testing Commands

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with coverage
mvn clean test jacoco:report

# Skip tests (use sparingly!)
mvn install -DskipTests
```

### Common Annotations

```java
// JUnit 5
@Test                    // Test method
@BeforeEach              // Before each test
@AfterEach               // After each test
@DisplayName("...")      // Readable name
@Disabled                // Skip test
@ParameterizedTest       // Multiple inputs

// Mockito
@ExtendWith(MockitoExtension.class)  // Enable Mockito
@Mock                    // Create mock
@InjectMocks             // Inject mocks
@Spy                     // Partial mock
```

### Common Patterns

```java
// AAA Pattern
@Test
void testMethod() {
    // Arrange
    Object obj = new Object();

    // Act
    Result result = obj.method();

    // Assert
    assertEquals(expected, result);
}

// Mockito Pattern
@Test
void testWithMock() {
    // Stub
    when(mock.method()).thenReturn(value);

    // Execute
    service.doSomething();

    // Verify
    verify(mock).method();
}
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Write unit tests with JUnit 5
- [ ] Use test lifecycle annotations effectively
- [ ] Apply Arrange-Act-Assert pattern consistently
- [ ] Use appropriate assertion types for different scenarios
- [ ] Create parameterized tests for multiple inputs
- [ ] Understand and use test doubles (stubs, mocks, spies)
- [ ] Create and inject mocks with Mockito
- [ ] Stub method behavior for different scenarios
- [ ] Verify method interactions and call counts
- [ ] Practice Test-Driven Development (TDD)
- [ ] Measure and interpret code coverage metrics
- [ ] Write testable, maintainable code
- [ ] Debug failing tests effectively

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 13: Logging Frameworks](../13-logging/) - Learn application logging
- Practice TDD with coding katas (String Calculator, FizzBuzz)
- Explore integration testing with Spring Boot Test
- Learn mutation testing with PIT
- Study contract testing with Pact
