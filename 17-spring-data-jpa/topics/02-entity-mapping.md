# Entity Mapping

## What is Entity Mapping?

Entity mapping defines how Java classes correspond to database tables. JPA uses annotations to establish these mappings.

---

## Basic Entity

```java
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    // Default constructor required
    public User() {}

    // Getters and setters
}
```

**Requirements:**
- `@Entity` annotation marks class as JPA entity
- Must have `@Id` field (primary key)
- Must have no-argument constructor

---

## @Entity, @Table, @Column

### @Table - Customize Table Name

```java
@Entity
@Table(name = "users")  // Maps to 'users' table
public class User {
    // ...
}
```

### @Column - Customize Column Mapping

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(updatable = false)
    private LocalDateTime createdAt;
}
```

### Common @Column Attributes

| Attribute | Description | Example |
|-----------|-------------|---------|
| `name` | Column name | `@Column(name = "user_name")` |
| `nullable` | Allow nulls | `@Column(nullable = false)` |
| `unique` | Unique constraint | `@Column(unique = true)` |
| `length` | String length | `@Column(length = 100)` |
| `updatable` | Include in updates | `@Column(updatable = false)` |

---

## Primary Key Generation

### @Id and @GeneratedValue

```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ...
}
```

### Generation Strategies

| Strategy | Description | Best For |
|----------|-------------|----------|
| `IDENTITY` | Auto-increment | MySQL, PostgreSQL |
| `SEQUENCE` | Database sequence | PostgreSQL, Oracle |
| `AUTO` | Provider chooses | General use |

```java
// IDENTITY (most common)
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

// SEQUENCE
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
@SequenceGenerator(name = "user_seq", sequenceName = "user_sequence")
private Long id;
```

---

## Relationships

### @ManyToOne (Most Common)

Many entities reference one entity (e.g., many Orders belong to one Customer).

```java
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```

### @OneToMany (Inverse of ManyToOne)

```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
```

**Key Points:**
- `mappedBy` = field name in the owning entity (Order.customer)
- `@JoinColumn` goes on the owning side (@ManyToOne)

### @OneToOne

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private UserProfile profile;
}

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;

    @OneToOne(mappedBy = "profile")
    private User user;
}
```

### @ManyToMany

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
```

---

## Fetch Types

| Fetch Type | Behavior | Default For |
|------------|----------|-------------|
| `EAGER` | Load immediately | @OneToOne, @ManyToOne |
| `LAZY` | Load on demand | @OneToMany, @ManyToMany |

```java
// Always use LAZY for collections
@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
private List<Order> orders;

// Use LAZY for associations when not always needed
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id")
private Customer customer;
```

**Best Practice:** Default to LAZY, use JOIN FETCH in queries when needed.

---

## Cascade Types

| Type | Effect |
|------|--------|
| `PERSIST` | Save child when saving parent |
| `MERGE` | Update child when updating parent |
| `REMOVE` | Delete child when deleting parent |
| `ALL` | All of the above |

```java
@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Order> orders;
```

`orphanRemoval = true` deletes child when removed from collection.

---

## Complete Example

```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors, getters, setters
}
```

---

## Summary

| Annotation | Purpose |
|------------|---------|
| `@Entity` | Mark class as JPA entity |
| `@Table` | Customize table name |
| `@Id` | Mark primary key field |
| `@GeneratedValue` | Auto-generate primary key |
| `@Column` | Customize column mapping |
| `@ManyToOne` | Many-to-one relationship |
| `@OneToMany` | One-to-many relationship |
| `@JoinColumn` | Foreign key column |

## Next Topic

Continue to [Repositories](./03-repositories.md) to learn about Spring Data JPA repository interfaces.
