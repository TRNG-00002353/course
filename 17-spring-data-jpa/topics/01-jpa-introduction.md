# JPA Introduction

## What is JPA?

Java Persistence API (JPA) is a specification for object-relational mapping (ORM) in Java. It allows you to work with database records as Java objects instead of writing SQL queries directly.

```
┌─────────────────────────────────────────┐
│       Application (Java Objects)        │
├─────────────────────────────────────────┤
│              JPA / Hibernate            │
├─────────────────────────────────────────┤
│                  JDBC                   │
├─────────────────────────────────────────┤
│           Database (Tables)             │
└─────────────────────────────────────────┘
```

**Key Points:**
- JPA is a specification; Hibernate is the most common implementation
- Maps Java classes to database tables
- Manages entity lifecycle (create, read, update, delete)
- Database-agnostic (switch databases with minimal changes)

---

## Why Spring Data JPA?

Spring Data JPA builds on JPA to eliminate boilerplate code.

**Without Spring Data JPA:**
```java
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
            .getResultList();
    }

    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        }
        return entityManager.merge(user);
    }
}
```

**With Spring Data JPA:**
```java
public interface UserRepository extends JpaRepository<User, Long> {
    // That's it! CRUD operations are automatic
}
```

### Key Benefits

| Benefit | Description |
|---------|-------------|
| **No boilerplate** | Repository interfaces auto-implemented |
| **Query derivation** | `findByEmail()` generates SQL automatically |
| **Pagination** | Built-in support for paginated results |
| **@Query support** | Custom JPQL when needed |

---

## Setup

### Dependencies

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Database Driver (choose one) -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Configuration

```properties
# application.properties

# H2 Database (development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# JPA Properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console (optional)
spring.h2.console.enabled=true
```

### DDL Auto Options

| Value | Description | Use Case |
|-------|-------------|----------|
| `create-drop` | Create on startup, drop on shutdown | Testing |
| `create` | Drop and recreate on startup | Development |
| `update` | Update schema if needed | Development |
| `validate` | Validate only, no changes | Production |
| `none` | No action | Production |

---

## Project Structure

```
src/main/java/com/example/
├── Application.java           # @SpringBootApplication
├── entity/
│   └── User.java              # @Entity classes
├── repository/
│   └── UserRepository.java    # Repository interfaces
├── service/
│   └── UserService.java       # Business logic
└── controller/
    └── UserController.java    # REST endpoints
```

---

## Quick Start Example

### Entity

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    // Constructors, getters, setters
}
```

### Repository

```java
public interface UserRepository extends JpaRepository<User, Long> {
    // Inherited: save, findById, findAll, delete, count, existsById
}
```

### Service

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
```

---

## Summary

| Concept | Description |
|---------|-------------|
| **JPA** | ORM specification for Java |
| **Hibernate** | JPA implementation (default in Spring Boot) |
| **Spring Data JPA** | Reduces boilerplate, auto-implements repositories |
| **JpaRepository** | Interface with built-in CRUD methods |

## Next Topic

Continue to [Entity Mapping](./02-entity-mapping.md) to learn how to map Java classes to database tables.
