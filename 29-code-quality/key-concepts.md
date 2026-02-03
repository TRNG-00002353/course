# Code Quality Key Concepts

## Overview

Code quality ensures software is maintainable, readable, and free from defects. This summary covers metrics, technical debt, code smells, and SonarQube.

---

## 1. Code Quality Metrics

### Cyclomatic Complexity

Number of independent paths through code.

| Complexity | Risk Level |
|------------|------------|
| 1-10 | Low - Acceptable |
| 11-20 | Moderate - Refactor |
| 21+ | High - Must refactor |

### Code Coverage

Percentage of code executed by tests.

| Coverage | Assessment |
|----------|------------|
| < 50% | Poor |
| 50-70% | Acceptable |
| 70-80% | Good |
| 80%+ | Excellent |

### Duplications

| Duplication | Assessment |
|-------------|------------|
| 0-3% | Excellent |
| 3-5% | Good |
| 5-10% | Acceptable |
| 10%+ | Needs attention |

---

## 2. Technical Debt

### Definition

Cost of choosing quick solutions over better approaches.

### Types (Fowler's Quadrant)

```
           Reckless              Prudent
         ┌──────────────┬──────────────┐
Deliber. │ "No time for │ "Ship now,   │
         │  design"     │  fix later"  │
         ├──────────────┼──────────────┤
Inadvert.│ "What's      │ "Now we know │
         │  layering?"  │  better"     │
         └──────────────┴──────────────┘
```

### Management Strategies

| Strategy | Description |
|----------|-------------|
| Boy Scout Rule | Leave code cleaner than you found it |
| Dedicated sprints | Focus time on debt reduction |
| Tech debt backlog | Track and prioritize items |
| Quality gates | Prevent new debt |

---

## 3. Code Smells

### Common Smells and Solutions

| Smell | Problem | Solution |
|-------|---------|----------|
| **Long Method** | Too many lines | Extract Method |
| **Large Class** | Too many responsibilities | Extract Class |
| **Primitive Obsession** | Overusing primitives | Value Objects |
| **Switch Statements** | Type checking | Polymorphism |
| **Feature Envy** | Using other class's data | Move Method |
| **Duplicate Code** | Copy-paste | Extract Method |
| **Dead Code** | Unused code | Delete it |
| **Long Parameter List** | Hard to use | Parameter Object |

### Example: Long Method

```java
// SMELL
public void processOrder(Order order) {
    // 150+ lines doing everything
}

// REFACTORED
public void processOrder(Order order) {
    validateOrder(order);
    BigDecimal total = calculateTotal(order);
    processPayment(order, total);
    sendNotification(order);
}
```

### Example: Feature Envy

```java
// SMELL: Method uses another class's data
public double calculateDiscount(Customer c) {
    if (c.getYears() > 5 && c.getPurchases() > 10000) {
        return 0.15;
    }
    return 0.05;
}

// REFACTORED: Move to Customer
public class Customer {
    public double getDiscount() {
        if (years > 5 && purchases > 10000) {
            return 0.15;
        }
        return 0.05;
    }
}
```

---

## 4. SonarQube

### Issue Types

| Type | Description |
|------|-------------|
| **Bug** | Reliability issue (something wrong) |
| **Vulnerability** | Security issue (exploitable) |
| **Code Smell** | Maintainability issue |
| **Security Hotspot** | Needs security review |

### Severity Levels

| Severity | Impact |
|----------|--------|
| Blocker | Breaks application |
| Critical | Security or likely bug |
| Major | Quality impact |
| Minor | Small issue |
| Info | Informational |

### Quality Gate

Pass/fail criteria for code quality:

```
Default Gate (Sonar way):
✓ Coverage on new code ≥ 80%
✓ Duplications on new code ≤ 3%
✓ Maintainability Rating = A
✓ Reliability Rating = A
✓ Security Rating = A
```

### Rating Scale

| Rating | Bugs/Vulnerabilities | Maintainability (Debt Ratio) |
|--------|---------------------|------------------------------|
| A | 0 issues | ≤ 5% |
| B | 1+ minor | 6-10% |
| C | 1+ major | 11-20% |
| D | 1+ critical | 21-50% |
| E | 1+ blocker | > 50% |

---

## 5. Maven Integration

```xml
<properties>
    <sonar.projectKey>my-project</sonar.projectKey>
    <sonar.host.url>http://localhost:9000</sonar.host.url>
</properties>

<!-- JaCoCo for coverage -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals><goal>prepare-agent</goal></goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals><goal>report</goal></goals>
        </execution>
    </executions>
</plugin>
```

```bash
# Run analysis
mvn clean verify sonar:sonar
```

---

## 6. SonarLint

IDE plugin for real-time feedback:

- Same rules as SonarQube
- Instant feedback while coding
- Works offline
- Supports IntelliJ, Eclipse, VS Code

**Connected Mode**: Links to SonarQube server for synced rules.

---

## Quick Reference

### Code Quality Checklist

- [ ] Cyclomatic complexity < 10 per method
- [ ] Code coverage ≥ 70%
- [ ] Duplications ≤ 5%
- [ ] No blocker/critical bugs
- [ ] No security vulnerabilities
- [ ] Quality gate passed

### Refactoring Priority

1. **Security vulnerabilities** - Fix immediately
2. **Blocker bugs** - Fix same day
3. **Critical issues** - Fix this sprint
4. **Major code smells** - Plan to fix
5. **Minor issues** - Fix when convenient

### Clean Code Principles

| Principle | Description |
|-----------|-------------|
| **DRY** | Don't Repeat Yourself |
| **KISS** | Keep It Simple |
| **YAGNI** | You Aren't Gonna Need It |
| **SRP** | Single Responsibility |
| **Boy Scout** | Leave code cleaner |
