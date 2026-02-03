# Code Quality Fundamentals

## Overview

Code quality is a measure of how well source code meets standards for maintainability, reliability, and efficiency. High-quality code is easier to understand, modify, test, and debug.

---

## Why Code Quality Matters

### Business Impact

| Poor Quality | High Quality |
|--------------|--------------|
| Bugs in production | Fewer defects |
| Slow feature delivery | Faster development |
| High maintenance costs | Lower TCO |
| Developer frustration | Team satisfaction |
| Knowledge silos | Easy onboarding |

### The Cost of Poor Quality

```
Bug found during:
┌─────────────────┬──────────────────┐
│ Development     │ $1 to fix        │
│ Testing         │ $10 to fix       │
│ Production      │ $100+ to fix     │
└─────────────────┴──────────────────┘
```

---

## Code Quality Metrics

### 1. Cyclomatic Complexity

Measures the number of independent paths through code. Higher complexity = harder to test and maintain.

```java
// Complexity = 1 (single path)
public String getGrade(int score) {
    return score >= 60 ? "Pass" : "Fail";
}

// Complexity = 4 (multiple paths)
public String getGrade(int score) {
    if (score >= 90) return "A";      // path 1
    else if (score >= 80) return "B"; // path 2
    else if (score >= 70) return "C"; // path 3
    else return "F";                  // path 4
}
```

**Guidelines:**
| Complexity | Risk Level | Action |
|------------|------------|--------|
| 1-10 | Low | Acceptable |
| 11-20 | Moderate | Consider refactoring |
| 21-50 | High | Must refactor |
| 50+ | Very High | Untestable, rewrite |

### 2. Code Duplication

Repeated code blocks that should be extracted into reusable methods.

```java
// BAD: Duplicated validation logic
public void createUser(String email) {
    if (email == null || !email.contains("@")) {
        throw new InvalidEmailException();
    }
    // create user...
}

public void updateEmail(String email) {
    if (email == null || !email.contains("@")) {  // Duplicated!
        throw new InvalidEmailException();
    }
    // update email...
}

// GOOD: Extracted to reusable method
private void validateEmail(String email) {
    if (email == null || !email.contains("@")) {
        throw new InvalidEmailException();
    }
}

public void createUser(String email) {
    validateEmail(email);
    // create user...
}

public void updateEmail(String email) {
    validateEmail(email);
    // update email...
}
```

### 3. Code Coverage

Percentage of code executed during testing.

```
┌─────────────────────────────────────────┐
│ Code Coverage Types                     │
├─────────────────────────────────────────┤
│ Line Coverage    - Lines executed       │
│ Branch Coverage  - Decision paths taken │
│ Method Coverage  - Methods called       │
│ Class Coverage   - Classes instantiated │
└─────────────────────────────────────────┘
```

**Coverage Guidelines:**
| Coverage | Assessment |
|----------|------------|
| < 50% | Poor - major risk |
| 50-70% | Acceptable for legacy code |
| 70-80% | Good for most projects |
| 80%+ | Excellent |
| 100% | Often impractical/diminishing returns |

> **Note:** High coverage doesn't guarantee quality tests. A test with no assertions has coverage but no value.

### 4. Maintainability Index

Composite metric combining complexity, lines of code, and comments.

| Index | Maintainability |
|-------|-----------------|
| 0-9 | Low (hard to maintain) |
| 10-19 | Moderate |
| 20-100 | High (easy to maintain) |

---

## Technical Debt

### What is Technical Debt?

Technical debt is the implied cost of additional rework caused by choosing quick solutions now instead of better approaches that would take longer.

```
┌─────────────────────────────────────────────────────────────┐
│                    Technical Debt Analogy                   │
├─────────────────────────────────────────────────────────────┤
│  Financial Debt          │  Technical Debt                 │
├──────────────────────────┼──────────────────────────────────┤
│  Borrow money            │  Ship quick/dirty code          │
│  Pay interest            │  Slower future development      │
│  Principal               │  Cost to fix properly           │
│  Bankruptcy              │  System rewrite                 │
└──────────────────────────┴──────────────────────────────────┘
```

### Types of Technical Debt (Martin Fowler's Quadrant)

```
                    Reckless                    Prudent
           ┌─────────────────────┬─────────────────────┐
           │ "We don't have      │ "We must ship now   │
Deliberate │  time for design"   │  and deal with      │
           │                     │  consequences"      │
           ├─────────────────────┼─────────────────────┤
           │ "What's             │ "Now we know how    │
Inadvertent│  layering?"         │  we should have     │
           │                     │  done it"           │
           └─────────────────────┴─────────────────────┘
```

### Common Sources of Technical Debt

1. **Time Pressure**: Rushing to meet deadlines
2. **Lack of Standards**: No coding guidelines
3. **Insufficient Testing**: Skipping tests to save time
4. **Poor Documentation**: Missing or outdated docs
5. **Deferred Refactoring**: "We'll fix it later"
6. **Technology Changes**: Outdated frameworks/libraries
7. **Knowledge Loss**: Team members leaving

### Managing Technical Debt

```java
// Strategy 1: Boy Scout Rule
// "Leave the code cleaner than you found it"

// Before (while fixing a bug)
public void processOrder(Order order) {
    // existing messy code
    if (order != null && order.getItems() != null && order.getItems().size() > 0) {
        // process...
    }
}

// After (small improvement during bug fix)
public void processOrder(Order order) {
    if (order == null || order.getItems().isEmpty()) {
        return;
    }
    // process...
}
```

**Debt Reduction Strategies:**

| Strategy | When to Use |
|----------|-------------|
| **Refactor as you go** | Small improvements during regular work |
| **Dedicated sprints** | Accumulated debt needs focused attention |
| **Tech debt backlog** | Track and prioritize debt items |
| **Quality gates** | Prevent new debt from entering |

---

## Code Smells

Code smells are surface indications that usually correspond to deeper problems in the system.

### Bloaters

Code that has grown too large to handle effectively.

#### Long Method

```java
// SMELL: Method doing too much
public void processOrder(Order order) {
    // 1. Validate order (20 lines)
    // 2. Calculate totals (30 lines)
    // 3. Apply discounts (25 lines)
    // 4. Process payment (40 lines)
    // 5. Update inventory (20 lines)
    // 6. Send notifications (15 lines)
    // Total: 150+ lines!
}

// REFACTORED: Extract methods
public void processOrder(Order order) {
    validateOrder(order);
    BigDecimal total = calculateTotal(order);
    total = applyDiscounts(order, total);
    processPayment(order, total);
    updateInventory(order);
    sendNotifications(order);
}
```

#### Large Class (God Class)

```java
// SMELL: Class with too many responsibilities
public class UserManager {
    public void createUser() { }
    public void deleteUser() { }
    public void sendEmail() { }        // Should be EmailService
    public void generateReport() { }   // Should be ReportService
    public void processPayment() { }   // Should be PaymentService
    public void validateAddress() { }  // Should be AddressValidator
}

// REFACTORED: Single Responsibility
public class UserService {
    private final EmailService emailService;
    private final PaymentService paymentService;

    public void createUser() { }
    public void deleteUser() { }
}
```

#### Primitive Obsession

```java
// SMELL: Using primitives instead of small objects
public void createUser(String firstName, String lastName,
                       String street, String city, String zip,
                       String phone, String email) { }

// REFACTORED: Use value objects
public void createUser(Name name, Address address, ContactInfo contact) { }

// Value objects encapsulate validation
public class Email {
    private final String value;

    public Email(String value) {
        if (!value.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = value;
    }
}
```

### Object-Orientation Abusers

#### Switch Statements (Type Checking)

```java
// SMELL: Switch on type
public double calculatePay(Employee e) {
    switch (e.getType()) {
        case ENGINEER: return e.getSalary();
        case SALESMAN: return e.getSalary() + e.getCommission();
        case MANAGER: return e.getSalary() + e.getBonus();
    }
}

// REFACTORED: Polymorphism
public abstract class Employee {
    abstract double calculatePay();
}

public class Engineer extends Employee {
    double calculatePay() { return salary; }
}

public class Salesman extends Employee {
    double calculatePay() { return salary + commission; }
}
```

### Change Preventers

Code that makes changes difficult.

#### Divergent Change

One class is commonly changed for different reasons.

```java
// SMELL: Class changes for multiple reasons
public class Employee {
    // Changes when: HR policy changes
    public void calculatePay() { }

    // Changes when: Reporting requirements change
    public void generateReport() { }

    // Changes when: Database schema changes
    public void save() { }
}

// REFACTORED: Separate by reason for change
public class Employee { }
public class PayrollCalculator { }
public class EmployeeReporter { }
public class EmployeeRepository { }
```

#### Shotgun Surgery

One change requires modifications in many classes.

```java
// SMELL: Adding a new field requires changes everywhere
// - Employee class
// - EmployeeDTO class
// - EmployeeMapper class
// - EmployeeValidator class
// - EmployeeRepository class
// - Multiple test classes

// SOLUTION: Centralize related logic
// Use conventions, reflection, or generation to reduce duplication
```

### Dispensables

Code that could be removed without affecting functionality.

#### Dead Code

```java
// SMELL: Unused code
public class UserService {

    // Never called anywhere
    public void oldMethod() {
        // ...
    }

    // Commented out code
    // public void deprecatedFeature() {
    //     ...
    // }
}

// SOLUTION: Delete it! Version control has the history.
```

#### Duplicate Code

Already covered in metrics section - extract to reusable methods.

### Couplers

Code with excessive coupling between classes.

#### Feature Envy

Method uses another class's data more than its own.

```java
// SMELL: Method envies another class
public class Order {
    public double calculateDiscount() {
        // Uses customer data extensively
        if (customer.getMembershipYears() > 5
            && customer.getTotalPurchases() > 10000
            && customer.getMembershipLevel() == Level.GOLD) {
            return 0.15;
        }
        return 0.05;
    }
}

// REFACTORED: Move to the class with the data
public class Customer {
    public double getDiscount() {
        if (membershipYears > 5
            && totalPurchases > 10000
            && membershipLevel == Level.GOLD) {
            return 0.15;
        }
        return 0.05;
    }
}
```

---

## Code Smells Quick Reference

| Smell | Problem | Solution |
|-------|---------|----------|
| **Long Method** | Hard to understand | Extract Method |
| **Large Class** | Too many responsibilities | Extract Class |
| **Primitive Obsession** | Overuse of primitives | Replace with Value Object |
| **Long Parameter List** | Hard to call | Introduce Parameter Object |
| **Data Clumps** | Data always together | Extract Class |
| **Switch Statements** | Type checking | Replace with Polymorphism |
| **Divergent Change** | Multiple reasons to change | Extract Class |
| **Shotgun Surgery** | Change affects many classes | Move Method/Field |
| **Feature Envy** | Method uses other class's data | Move Method |
| **Dead Code** | Unused code | Delete |
| **Duplicate Code** | Copy-paste code | Extract Method |
| **Comments** | Code needs explanation | Rename/Extract to clarify |

---

## Refactoring Techniques

### Extract Method

```java
// Before
public void printReport() {
    System.out.println("=== Report ===");
    System.out.println("Date: " + LocalDate.now());
    System.out.println("==============");

    // ... report content

    System.out.println("==============");
    System.out.println("End of Report");
}

// After
public void printReport() {
    printHeader();
    // ... report content
    printFooter();
}

private void printHeader() {
    System.out.println("=== Report ===");
    System.out.println("Date: " + LocalDate.now());
    System.out.println("==============");
}
```

### Replace Conditional with Polymorphism

See Switch Statements example above.

### Introduce Parameter Object

```java
// Before
public List<Order> findOrders(LocalDate startDate, LocalDate endDate,
                              String status, Long customerId,
                              int page, int size) { }

// After
public List<Order> findOrders(OrderSearchCriteria criteria) { }

public class OrderSearchCriteria {
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long customerId;
    private int page;
    private int size;
}
```

---

## Summary

| Concept | Key Point |
|---------|-----------|
| **Code Quality** | Maintainability, reliability, efficiency |
| **Cyclomatic Complexity** | Keep methods under 10 |
| **Duplication** | DRY - Don't Repeat Yourself |
| **Coverage** | Aim for 70-80% meaningful coverage |
| **Technical Debt** | Track it, pay it down incrementally |
| **Code Smells** | Surface indicators of deeper problems |
| **Refactoring** | Improve structure without changing behavior |

---

## Next Steps

Continue to [SonarQube](./02-sonarqube.md) to learn how to automate code quality analysis.
