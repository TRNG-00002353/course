# Projections

## Overview

Projections allow you to retrieve only the data you need from the database, rather than fetching entire entities. This improves performance and creates cleaner APIs.

---

## Why Use Projections?

### The Problem with Fetching Entities

```java
// Entity with many fields
@Entity
public class Employee {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private BigDecimal salary;        // Sensitive!
    private String ssn;               // Sensitive!
    private LocalDate hireDate;

    @ManyToOne
    private Department department;

    @OneToMany
    private List<Project> projects;   // Could trigger N+1
}
```

**Problems with returning full entities:**
1. **Over-fetching**: Loading data you don't need
2. **Security risk**: Exposing sensitive fields (salary, SSN)
3. **Performance**: Triggering lazy-loaded relationships
4. **Coupling**: API tied directly to database schema

### The Solution: Projections

Fetch only the fields you need:

```sql
-- Instead of: SELECT * FROM employee
SELECT first_name, last_name, email FROM employee
```

---

## Types of Projections

| Type | Description | Use Case |
|------|-------------|----------|
| **Interface-based (Closed)** | Interface with getter methods | Simple, read-only views |
| **Interface-based (Open)** | Getters with @Value expressions | Computed/combined fields |
| **Class-based (DTO)** | Constructor projection | When you need a real class |
| **Dynamic** | Generic method with Class parameter | Multiple views of same query |

---

## Interface-Based Projections (Closed)

The simplest and most common type. Define an interface with getters for the fields you want.

### Step 1: Create the Projection Interface

```java
// Only these fields will be fetched from database
public interface EmployeeSummary {
    String getFirstName();
    String getLastName();
    String getEmail();
}
```

### Step 2: Use in Repository

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Returns projection instead of entity
    List<EmployeeSummary> findByDepartmentName(String departmentName);

    // Can combine with other query features
    List<EmployeeSummary> findTop5ByOrderByHireDateDesc();

    // Works with @Query too
    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    List<EmployeeSummary> findHighEarners(@Param("minSalary") BigDecimal minSalary);
}
```

### Step 3: Use in Service/Controller

```java
@GetMapping("/employees/summary")
public List<EmployeeSummary> getEmployeeSummaries() {
    return employeeRepository.findByDepartmentName("Engineering");
}
```

### Generated SQL

Spring Data JPA generates optimized SQL:

```sql
-- Only selects the projected columns
SELECT e.first_name, e.last_name, e.email
FROM employee e
JOIN department d ON e.department_id = d.id
WHERE d.name = ?
```

---

## Nested Projections

Access related entity fields through nested interfaces.

### Example: Employee with Department Name

```java
public interface EmployeeWithDepartment {
    String getFirstName();
    String getLastName();

    // Nested projection for related entity
    DepartmentInfo getDepartment();

    interface DepartmentInfo {
        String getName();
        String getLocation();
    }
}
```

### Repository Method

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<EmployeeWithDepartment> findByLastName(String lastName);
}
```

### Result Structure

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "department": {
    "name": "Engineering",
    "location": "Building A"
  }
}
```

> **Note**: Nested projections work best with `@ManyToOne` relationships. For collections (`@OneToMany`), consider using DTOs or explicit queries.

---

## Open Projections

Use `@Value` with SpEL expressions for computed or combined fields.

### Example: Full Name Computation

```java
public interface EmployeeNameView {

    // Computed field using SpEL
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    String getEmail();

    // Access nested property
    @Value("#{target.department.name}")
    String getDepartmentName();
}
```

### How It Works

- `target` refers to the entity instance
- SpEL expression is evaluated after fetching data
- The full entity is loaded (less efficient than closed projections)

> **Warning**: Open projections load the entire entity, then compute values. Use closed projections when possible for better performance.

---

## Class-Based Projections (DTO Projections)

Use a class with a constructor instead of an interface.

### Step 1: Create the DTO Class

```java
public class EmployeeDTO {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String departmentName;

    // Constructor parameter names must match entity properties
    // Or use @Query with constructor expression
    public EmployeeDTO(String firstName, String lastName,
                       String email, String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.departmentName = departmentName;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getDepartmentName() { return departmentName; }

    // Can add methods - unlike interfaces
    public String getDisplayName() {
        return firstName + " " + lastName + " (" + departmentName + ")";
    }
}
```

### Step 2: Use with @Query Constructor Expression

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
        SELECT new com.example.dto.EmployeeDTO(
            e.firstName,
            e.lastName,
            e.email,
            e.department.name
        )
        FROM Employee e
        WHERE e.department.id = :deptId
        """)
    List<EmployeeDTO> findEmployeesByDepartment(@Param("deptId") Long departmentId);
}
```

### When to Use Class-Based Projections

- Need methods beyond getters
- Want immutable objects with validation
- Need to use the same DTO for requests and responses
- Constructor logic required

---

## Dynamic Projections

Return different projections from the same query using a generic type parameter.

### Define Multiple Projection Interfaces

```java
// Minimal view
public interface EmployeeIdName {
    Long getId();
    String getFirstName();
    String getLastName();
}

// Contact info view
public interface EmployeeContact {
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhone();
}

// Full summary view
public interface EmployeeSummary {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    DepartmentInfo getDepartment();

    interface DepartmentInfo {
        String getName();
    }
}
```

### Repository with Dynamic Projection

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Generic type parameter allows any projection
    <T> List<T> findByDepartmentId(Long departmentId, Class<T> type);

    <T> T findById(Long id, Class<T> type);

    <T> List<T> findByLastNameContaining(String name, Class<T> type);
}
```

### Usage

```java
@Service
public class EmployeeService {

    public List<EmployeeIdName> getEmployeeNames(Long deptId) {
        // Returns only id, firstName, lastName
        return repository.findByDepartmentId(deptId, EmployeeIdName.class);
    }

    public List<EmployeeContact> getEmployeeContacts(Long deptId) {
        // Returns contact information
        return repository.findByDepartmentId(deptId, EmployeeContact.class);
    }

    public List<EmployeeSummary> getEmployeeSummaries(Long deptId) {
        // Returns full summary with department
        return repository.findByDepartmentId(deptId, EmployeeSummary.class);
    }
}
```

---

## Projections vs DTOs in Service Layer

### Pattern: Repository Returns Projection, Controller Uses Directly

```java
// Simple case - projection is the API response
@RestController
public class EmployeeController {

    @GetMapping("/employees")
    public List<EmployeeSummary> getEmployees() {
        return employeeRepository.findAllProjectedBy();
    }
}
```

### Pattern: Repository Returns Entity, Service Maps to DTO

```java
// When you need business logic or transformations
@Service
public class EmployeeService {

    public EmployeeResponse getEmployee(Long id) {
        Employee employee = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Employee not found"));

        // Manual mapping with business logic
        return new EmployeeResponse(
            employee.getFullName(),
            employee.getEmail(),
            calculateYearsOfService(employee.getHireDate())
        );
    }
}
```

### When to Use Each

| Approach | Use When |
|----------|----------|
| **Projection directly** | Simple read-only views, no transformation needed |
| **Entity → DTO mapping** | Need business logic, validation, or complex transformations |
| **Class-based projection** | Need same DTO for response but with constructor optimization |

---

## Common Patterns

### Pattern 1: List View vs Detail View

```java
// List view - minimal data for tables
public interface ProductListItem {
    Long getId();
    String getName();
    BigDecimal getPrice();
}

// Detail view - full information
public interface ProductDetails {
    Long getId();
    String getName();
    String getDescription();
    BigDecimal getPrice();
    Integer getStockQuantity();
    CategoryInfo getCategory();

    interface CategoryInfo {
        String getName();
    }
}

// Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<ProductListItem> findAllProjectedBy();          // For list page
    Optional<ProductDetails> findDetailsById(Long id);   // For detail page
}
```

### Pattern 2: Dropdown/Select Options

```java
// Just id and display text for dropdowns
public interface SelectOption {
    Long getId();
    String getName();
}

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<SelectOption> findAllProjectedByOrderByName();
}
```

### Pattern 3: Search Results

```java
public interface SearchResult {
    Long getId();
    String getTitle();

    @Value("#{target.description.substring(0, 100) + '...'}")
    String getSnippet();
}
```

---

## Summary

| Projection Type | Performance | Flexibility | Use Case |
|----------------|-------------|-------------|----------|
| **Closed Interface** | Best | Limited to getters | Most cases |
| **Open Interface** | Poor (loads entity) | SpEL expressions | Computed fields |
| **Class-based (DTO)** | Good | Full class features | Need methods/validation |
| **Dynamic** | Varies | Multiple views | Different API responses |

### Best Practices

1. **Default to closed interface projections** - simplest and most efficient
2. **Avoid open projections** unless you need computed values
3. **Use DTOs with @Query** when you need constructor expressions for complex joins
4. **Use dynamic projections** when the same query needs different response shapes
5. **Keep projection interfaces near repositories** - they're part of the data layer

---

## Quick Reference

```java
// 1. Closed interface projection
public interface UserSummary {
    String getName();
    String getEmail();
}

// 2. Nested projection
public interface OrderSummary {
    String getOrderNumber();
    CustomerInfo getCustomer();
    interface CustomerInfo {
        String getName();
    }
}

// 3. Open projection (computed)
public interface UserDisplay {
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
}

// 4. Dynamic projection
<T> List<T> findByStatus(String status, Class<T> type);

// 5. DTO projection with @Query
@Query("SELECT new com.example.UserDTO(u.name, u.email) FROM User u")
List<UserDTO> findAllAsDTO();
```
