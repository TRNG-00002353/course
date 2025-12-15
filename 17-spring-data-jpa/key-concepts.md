# Spring Data JPA Key Concepts for Application Developers

## Overview

This document covers essential Spring Data JPA concepts that every application developer must master. Spring Data JPA simplifies database access by reducing boilerplate code and providing powerful abstractions over JPA (Java Persistence API), enabling developers to build data-driven applications efficiently.

---

## 1. JPA Fundamentals

### Why It Matters
- Object-Relational Mapping (ORM) eliminates manual SQL
- Standardized persistence API across databases
- Reduces boilerplate JDBC code
- Type-safe database operations

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Entity | Java object mapped to database table | Represents data model |
| EntityManager | Manages entity lifecycle | Persistence operations |
| Persistence Context | Cache of managed entities | First-level cache |
| Transaction | Unit of work | Data consistency |
| JPQL | Java Persistence Query Language | Object-oriented queries |

### JPA Architecture
```
Application Layer
    ↓
Spring Data JPA Repository
    ↓
JPA Provider (Hibernate)
    ↓
JDBC
    ↓
Database
```

### Basic Configuration

**Dependencies (Maven):**
```xml
<dependencies>
    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Database Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>

    <!-- H2 for testing -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

**Application Properties:**
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Connection Pool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

**Java Configuration:**
```java
@Configuration
@EnableJpaRepositories(basePackages = "com.example.repository")
@EnableTransactionManagement
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em =
            new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.entity");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
```

---

## 2. Entity Mapping

### Why It Matters
- Maps Java objects to database tables
- Defines relationships between entities
- Controls database schema generation
- Ensures data integrity

### Key Concepts

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Entity` | Mark class as JPA entity | Table mapping |
| `@Table` | Customize table details | Name, schema, indexes |
| `@Id` | Primary key field | Unique identifier |
| `@GeneratedValue` | Auto-generate ID values | Auto-increment |
| `@Column` | Customize column mapping | Name, nullable, unique |
| `@Transient` | Exclude field from persistence | Calculated fields |
| `@Temporal` | Date/time field mapping | Date precision |
| `@Enumerated` | Enum mapping | String or ordinal |
| `@Lob` | Large object | BLOB, CLOB |

### Basic Entity Mapping

**Simple Entity:**
```java
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole role;

    @Transient
    private String fullName; // Not persisted

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

enum UserRole {
    USER, ADMIN, MODERATOR
}
```

**Generation Strategies:**
```java
// Auto (database decides)
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

// Identity (auto-increment)
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

// Sequence (database sequence)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
@SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
private Long id;

// Table (separate table for IDs)
@GeneratedValue(strategy = GenerationType.TABLE)
private Long id;

// UUID
@Id
@GeneratedValue(generator = "UUID")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
@Column(updatable = false, nullable = false)
private UUID id;
```

**Composite Primary Key:**
```java
// Embeddable ID class
@Embeddable
public class OrderItemId implements Serializable {
    private Long orderId;
    private Long productId;

    // equals, hashCode, constructors
}

// Entity with composite key
@Entity
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private Integer quantity;
    private BigDecimal price;
}
```

---

## 3. Entity Relationships

### Why It Matters
- Model real-world data relationships
- Maintain referential integrity
- Enable efficient data retrieval
- Support complex business logic

### Key Concepts

| Relationship | Annotation | Cardinality |
|--------------|------------|-------------|
| One-to-One | `@OneToOne` | 1:1 |
| One-to-Many | `@OneToMany` | 1:N |
| Many-to-One | `@ManyToOne` | N:1 |
| Many-to-Many | `@ManyToMany` | N:M |

### Relationship Mappings

**One-to-One (Bidirectional):**
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    public void setProfile(UserProfile profile) {
        this.profile = profile;
        profile.setUser(this);
    }
}

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;
    private String avatarUrl;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
```

**One-to-Many / Many-to-One (Bidirectional):**
```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    // Helper methods
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
```

**Many-to-Many (Bidirectional):**
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    public void enrollInCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this);
    }

    public void unenrollFromCourse(Course course) {
        courses.remove(course);
        course.getStudents().remove(this);
    }
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

**Many-to-Many with Extra Attributes:**
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();
}

@Entity
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id = new EnrollmentId();

    @ManyToOne
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    private Course course;

    private LocalDate enrollmentDate;
    private Integer grade;

    // Constructors, getters, setters
}

@Embeddable
class EnrollmentId implements Serializable {
    private Long studentId;
    private Long courseId;

    // equals, hashCode
}
```

**Cascade Types:**
```java
public enum CascadeType {
    PERSIST,    // Save child when parent is saved
    MERGE,      // Update child when parent is updated
    REMOVE,     // Delete child when parent is deleted
    REFRESH,    // Reload child when parent is refreshed
    DETACH,     // Detach child when parent is detached
    ALL         // All of the above
}

// Example
@OneToMany(
    mappedBy = "order",
    cascade = CascadeType.ALL,
    orphanRemoval = true // Delete orphaned children
)
private List<OrderItem> items;
```

**Fetch Types:**
```java
// LAZY - Load when accessed (default for collections)
@OneToMany(fetch = FetchType.LAZY)
private List<Order> orders;

// EAGER - Load immediately (default for single entities)
@ManyToOne(fetch = FetchType.EAGER)
private User user;
```

---

## 4. Spring Data Repositories

### Why It Matters
- Eliminate boilerplate data access code
- Provide CRUD operations out-of-the-box
- Support custom query methods
- Enable pagination and sorting

### Key Concepts

| Interface | Extends | Methods | Use Case |
|-----------|---------|---------|----------|
| `Repository` | - | None (marker) | Custom implementation |
| `CrudRepository` | Repository | Basic CRUD | Simple operations |
| `PagingAndSortingRepository` | CrudRepository | Pagination | Large datasets |
| `JpaRepository` | PagingAndSorting | JPA-specific | Full-featured |

### Repository Interface

**Basic Repository:**
```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Inherits methods:
    // save(entity)
    // saveAll(entities)
    // findById(id)
    // findAll()
    // findAllById(ids)
    // count()
    // deleteById(id)
    // delete(entity)
    // deleteAll()
    // existsById(id)
}
```

**Usage in Service:**
```java
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Read
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    // Delete
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    // Batch operations
    public List<User> createUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

    public long countUsers() {
        return userRepository.count();
    }
}
```

---

## 5. Query Methods

### Why It Matters
- Define queries using method naming conventions
- No SQL/JPQL required for simple queries
- Type-safe and refactoring-friendly
- Supports complex query conditions

### Key Concepts

| Keyword | Example | JPQL Equivalent |
|---------|---------|----------------|
| `findBy` | `findByUsername` | `WHERE x.username = ?1` |
| `And` | `findByFirstNameAndLastName` | `WHERE x.firstName = ?1 AND x.lastName = ?2` |
| `Or` | `findByEmailOrUsername` | `WHERE x.email = ?1 OR x.username = ?2` |
| `Between` | `findByAgeBetween` | `WHERE x.age BETWEEN ?1 AND ?2` |
| `LessThan` | `findByAgeLessThan` | `WHERE x.age < ?1` |
| `GreaterThan` | `findByAgeGreaterThan` | `WHERE x.age > ?1` |
| `Like` | `findByNameLike` | `WHERE x.name LIKE ?1` |
| `StartingWith` | `findByNameStartingWith` | `WHERE x.name LIKE ?1%` |
| `EndingWith` | `findByNameEndingWith` | `WHERE x.name LIKE %?1` |
| `Containing` | `findByNameContaining` | `WHERE x.name LIKE %?1%` |
| `OrderBy` | `findByAgeOrderByNameAsc` | `WHERE x.age = ?1 ORDER BY x.name ASC` |
| `Not` | `findByActiveNot` | `WHERE x.active <> ?1` |
| `In` | `findByIdIn` | `WHERE x.id IN ?1` |
| `IsNull` | `findByDeletedAtIsNull` | `WHERE x.deletedAt IS NULL` |
| `IsNotNull` | `findByDeletedAtIsNotNull` | `WHERE x.deletedAt IS NOT NULL` |
| `True` | `findByActiveTrue` | `WHERE x.active = true` |
| `False` | `findByActiveFalse` | `WHERE x.active = false` |

### Query Method Examples

**Simple Queries:**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find by single field
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // Find with conditions
    List<User> findByActiveTrue();
    List<User> findByActiveFalse();

    // Find with comparison
    List<User> findByAgeGreaterThan(Integer age);
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);
    List<User> findByCreatedAtAfter(LocalDateTime date);

    // Find with pattern matching
    List<User> findByUsernameContaining(String keyword);
    List<User> findByEmailEndingWith(String domain);

    // Multiple conditions
    Optional<User> findByUsernameAndEmail(String username, String email);
    List<User> findByFirstNameOrLastName(String firstName, String lastName);

    // Sorting
    List<User> findByActiveTrueOrderByCreatedAtDesc();
    List<User> findByRoleOrderByLastNameAscFirstNameAsc(UserRole role);

    // Check existence
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Count
    long countByActive(Boolean active);
    long countByRole(UserRole role);

    // Delete
    void deleteByUsername(String username);
    long deleteByActivefalse();

    // Collection queries
    List<User> findByIdIn(List<Long> ids);
    List<User> findByRoleIn(List<UserRole> roles);

    // Null checks
    List<User> findByLastLoginIsNull();
    List<User> findByDeletedAtIsNotNull();

    // Ignoring case
    Optional<User> findByUsernameIgnoreCase(String username);
    List<User> findByFirstNameContainingIgnoreCase(String keyword);

    // First/Top results
    Optional<User> findFirstByOrderByCreatedAtDesc();
    List<User> findTop10ByActiveTrueOrderByCreatedAtDesc();

    // Distinct results
    List<User> findDistinctByLastName(String lastName);
}
```

**Complex Query Methods:**
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Complex conditions
    List<Product> findByNameContainingAndPriceBetweenAndActiveTrue(
        String name, BigDecimal minPrice, BigDecimal maxPrice);

    // Multiple sorting
    List<Product> findByCategoryOrderByPriceAscNameAsc(String category);

    // Date range
    List<Product> findByCreatedAtBetweenAndCategory(
        LocalDateTime start, LocalDateTime end, String category);

    // Nested properties
    List<Product> findByCategory_NameAndActiveTrue(String categoryName);

    // Collection membership
    List<Product> findByIdInAndActiveTrue(List<Long> ids);

    // Limited results with conditions
    List<Product> findTop5ByActiveTrueOrderByPriceDesc();

    // Stream results (for large datasets)
    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    Stream<Product> findByActiveTrue();
}
```

---

## 6. Custom Queries

### Why It Matters
- Handle complex queries beyond method naming
- Optimize performance with native SQL
- Support database-specific features
- Full control over query execution

### Key Concepts

| Annotation | Purpose | Query Language |
|------------|---------|----------------|
| `@Query` | Define custom JPQL/SQL | JPQL or SQL |
| `@Modifying` | Indicate update/delete query | With @Query |
| `@Param` | Named parameters | Parameter binding |
| `@EntityGraph` | Fetch optimization | Avoid N+1 |

### Custom Query Examples

**JPQL Queries:**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Simple JPQL query
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmailAddress(String email);

    // Named parameters (preferred)
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    // Multiple parameters
    @Query("SELECT u FROM User u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    List<User> findByFullName(@Param("firstName") String firstName,
                              @Param("lastName") String lastName);

    // Join queries
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profile WHERE u.id = :id")
    Optional<User> findByIdWithProfile(@Param("id") Long id);

    // Complex conditions
    @Query("""
        SELECT u FROM User u
        WHERE (:role IS NULL OR u.role = :role)
        AND (:active IS NULL OR u.active = :active)
        AND (:searchTerm IS NULL OR
             u.username LIKE %:searchTerm% OR
             u.email LIKE %:searchTerm%)
        """)
    List<User> searchUsers(@Param("role") UserRole role,
                          @Param("active") Boolean active,
                          @Param("searchTerm") String searchTerm);

    // Projection (DTO)
    @Query("SELECT new com.example.dto.UserDTO(u.id, u.username, u.email) " +
           "FROM User u WHERE u.active = true")
    List<UserDTO> findAllActiveUsersDTO();

    // Aggregate functions
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    long countByRole(@Param("role") UserRole role);

    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();

    // Update query
    @Modifying
    @Query("UPDATE User u SET u.active = :active WHERE u.id = :id")
    int updateUserStatus(@Param("id") Long id, @Param("active") Boolean active);

    // Delete query
    @Modifying
    @Query("DELETE FROM User u WHERE u.lastLogin < :date")
    int deleteInactiveUsers(@Param("date") LocalDateTime date);
}
```

**Native SQL Queries:**
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Simple native query
    @Query(value = "SELECT * FROM products WHERE price < ?1", nativeQuery = true)
    List<Product> findCheapProducts(BigDecimal maxPrice);

    // Named parameters
    @Query(value = "SELECT * FROM products WHERE category_id = :categoryId " +
                   "ORDER BY price DESC LIMIT :limit", nativeQuery = true)
    List<Product> findTopProductsByCategory(@Param("categoryId") Long categoryId,
                                           @Param("limit") int limit);

    // Complex native query
    @Query(value = """
        SELECT p.* FROM products p
        INNER JOIN categories c ON p.category_id = c.id
        WHERE c.name = :category
        AND p.stock_quantity > 0
        AND p.active = true
        ORDER BY p.created_at DESC
        """, nativeQuery = true)
    List<Product> findAvailableProductsByCategory(@Param("category") String category);

    // Native query with projection
    @Query(value = "SELECT p.id, p.name, p.price, c.name as category_name " +
                   "FROM products p " +
                   "JOIN categories c ON p.category_id = c.id " +
                   "WHERE p.active = true", nativeQuery = true)
    List<Object[]> findProductSummaries();

    // Database-specific features (PostgreSQL)
    @Query(value = "SELECT * FROM products WHERE name ILIKE %:search%",
           nativeQuery = true)
    List<Product> searchProductsCaseInsensitive(@Param("search") String search);

    // Bulk update
    @Modifying
    @Query(value = "UPDATE products SET discount = :discount " +
                   "WHERE category_id = :categoryId", nativeQuery = true)
    int applyDiscountToCategory(@Param("discount") BigDecimal discount,
                                @Param("categoryId") Long categoryId);
}
```

**Entity Graphs (Solve N+1 Problem):**
```java
@Entity
@NamedEntityGraph(
    name = "User.withProfile",
    attributeNodes = @NamedAttributeNode("profile")
)
@NamedEntityGraph(
    name = "User.withOrders",
    attributeNodes = @NamedAttributeNode(value = "orders", subgraph = "orders-subgraph"),
    subgraphs = @NamedSubgraph(
        name = "orders-subgraph",
        attributeNodes = @NamedAttributeNode("items")
    )
)
public class User {
    // Entity definition
}

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = "User.withProfile")
    Optional<User> findWithProfileById(Long id);

    @EntityGraph(value = "User.withOrders")
    Optional<User> findWithOrdersById(Long id);

    // Ad-hoc entity graph
    @EntityGraph(attributePaths = {"profile", "orders"})
    List<User> findByActiveTrue();
}
```

---

## 7. Pagination and Sorting

### Why It Matters
- Handle large datasets efficiently
- Improve application performance
- Enhance user experience
- Reduce memory consumption

### Key Concepts

| Interface | Purpose |
|-----------|---------|
| `Pageable` | Pagination parameters |
| `Sort` | Sorting specification |
| `Page<T>` | Page of results with metadata |
| `Slice<T>` | Page without total count |

### Pagination Examples

**Repository with Pagination:**
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Basic pagination
    Page<Product> findAll(Pageable pageable);

    // Pagination with filtering
    Page<Product> findByCategory(String category, Pageable pageable);

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    // Slice (no total count - better performance)
    Slice<Product> findByNameContaining(String name, Pageable pageable);

    // Custom query with pagination
    @Query("SELECT p FROM Product p WHERE p.price > :price")
    Page<Product> findExpensiveProducts(@Param("price") BigDecimal price,
                                       Pageable pageable);
}
```

**Service Layer:**
```java
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProducts(String category, BigDecimal minPrice,
                                        BigDecimal maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
            Sort.by("createdAt").descending());

        return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }

    // Multiple sort fields
    public Page<Product> getProductsSorted(int page, int size) {
        Sort sort = Sort.by("category").ascending()
                       .and(Sort.by("price").descending())
                       .and(Sort.by("name").ascending());

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }
}
```

**Controller Usage:**
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<Product> products = productService.getProducts(page, size, sortBy, direction);
        return ResponseEntity.ok(products);
    }

    // Alternative: Using Pageable as parameter
    @GetMapping("/v2")
    public ResponseEntity<Page<Product>> getProductsV2(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {

        Page<Product> products = productRepository.findAll(pageable);
        return ResponseEntity.ok(products);
    }
}
```

**Custom DTO Response:**
```java
public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;

    public PagedResponse(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
        this.first = page.isFirst();
    }

    // Getters and setters
}

// Controller
@GetMapping
public ResponseEntity<PagedResponse<Product>> getProducts(Pageable pageable) {
    Page<Product> page = productRepository.findAll(pageable);
    return ResponseEntity.ok(new PagedResponse<>(page));
}
```

---

## 8. Specifications and Criteria API

### Why It Matters
- Build dynamic queries programmatically
- Type-safe query construction
- Reusable query logic
- Complex search functionality

### Key Concepts

**Specification Interface:**
```java
import org.springframework.data.jpa.domain.Specification;

public interface ProductRepository extends JpaRepository<Product, Long>,
                                           JpaSpecificationExecutor<Product> {
    // Query methods
}
```

**Specification Examples:**
```java
public class ProductSpecifications {

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) ->
            name == null ? null : cb.equal(root.get("name"), name);
    }

    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) ->
            category == null ? null : cb.equal(root.get("category"), category);
    }

    public static Specification<Product> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("price"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("price"), min);
            return cb.between(root.get("price"), min, max);
        };
    }

    public static Specification<Product> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    public static Specification<Product> nameContains(String keyword) {
        return (root, query, cb) ->
            keyword == null ? null :
            cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Product> createdAfter(LocalDateTime date) {
        return (root, query, cb) ->
            date == null ? null : cb.greaterThan(root.get("createdAt"), date);
    }
}
```

**Service with Specifications:**
```java
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(ProductSearchCriteria criteria, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);

        if (criteria.getName() != null) {
            spec = spec.and(ProductSpecifications.nameContains(criteria.getName()));
        }

        if (criteria.getCategory() != null) {
            spec = spec.and(ProductSpecifications.hasCategory(criteria.getCategory()));
        }

        if (criteria.getMinPrice() != null || criteria.getMaxPrice() != null) {
            spec = spec.and(ProductSpecifications.priceBetween(
                criteria.getMinPrice(), criteria.getMaxPrice()));
        }

        if (Boolean.TRUE.equals(criteria.getActiveOnly())) {
            spec = spec.and(ProductSpecifications.isActive());
        }

        return productRepository.findAll(spec, pageable);
    }
}

// Search criteria DTO
@Data
public class ProductSearchCriteria {
    private String name;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean activeOnly;
}
```

**Generic Specification Builder:**
```java
public class GenericSpecification<T> {

    public static <T> Specification<T> fieldEquals(String field, Object value) {
        return (root, query, cb) ->
            value == null ? null : cb.equal(root.get(field), value);
    }

    public static <T> Specification<T> fieldContains(String field, String value) {
        return (root, query, cb) ->
            value == null ? null :
            cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    public static <T> Specification<T> fieldBetween(String field,
                                                     Comparable min,
                                                     Comparable max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get(field), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get(field), min);
            return cb.between(root.get(field), min, max);
        };
    }
}
```

---

## 9. Transaction Management

### Why It Matters
- Ensure data consistency
- Handle concurrent access
- Enable rollback on errors
- Support ACID properties

### Key Concepts

| Annotation | Purpose | Scope |
|------------|---------|-------|
| `@Transactional` | Mark method as transactional | Method/Class |
| `@EnableTransactionManagement` | Enable transaction support | Configuration |

**Transaction Configuration:**
```java
@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PaymentService paymentService;

    // Read-only transaction (optimization)
    @Transactional(readOnly = true)
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    // Default transaction (read-write)
    public Order createOrder(Order order) {
        // Validate inventory
        inventoryService.reserveItems(order.getItems());

        // Process payment
        paymentService.processPayment(order.getPayment());

        // Save order
        Order savedOrder = orderRepository.save(order);

        return savedOrder;
        // If any exception occurs, entire transaction rolls back
    }

    // Custom transaction settings
    @Transactional(
        propagation = Propagation.REQUIRES_NEW,
        isolation = Isolation.SERIALIZABLE,
        timeout = 30,
        rollbackFor = Exception.class
    )
    public void criticalOperation() {
        // This runs in a new transaction
    }

    // No rollback for specific exceptions
    @Transactional(noRollbackFor = CustomBusinessException.class)
    public void operationWithBusinessRules() {
        // Won't rollback for CustomBusinessException
    }
}
```

**Propagation Types:**
```java
// REQUIRED (default) - Use existing or create new
@Transactional(propagation = Propagation.REQUIRED)

// REQUIRES_NEW - Always create new transaction
@Transactional(propagation = Propagation.REQUIRES_NEW)

// SUPPORTS - Use existing if available, otherwise non-transactional
@Transactional(propagation = Propagation.SUPPORTS)

// NOT_SUPPORTED - Execute non-transactionally
@Transactional(propagation = Propagation.NOT_SUPPORTED)

// MANDATORY - Must have existing transaction
@Transactional(propagation = Propagation.MANDATORY)

// NEVER - Must not have transaction
@Transactional(propagation = Propagation.NEVER)
```

---

## Quick Reference Card

### Essential Annotations
```java
// Entity Mapping
@Entity                             // Mark as JPA entity
@Table(name = "users")             // Table name
@Id                                // Primary key
@GeneratedValue(strategy = ...)    // Auto-generate ID
@Column(name = "email")            // Column mapping

// Relationships
@OneToOne                          // 1:1 relationship
@OneToMany(mappedBy = "...")       // 1:N relationship
@ManyToOne                         // N:1 relationship
@ManyToMany                        // N:M relationship
@JoinColumn(name = "...")          // Foreign key column
@JoinTable(name = "...")           // Join table

// Lifecycle
@PrePersist                        // Before save
@PostPersist                       // After save
@PreUpdate                         // Before update
@PostUpdate                        // After update
@PreRemove                         // Before delete
@PostRemove                        // After delete

// Queries
@Query("SELECT ...")               // Custom JPQL/SQL
@Modifying                         // Update/Delete query
@Param("name")                     // Named parameter
@EntityGraph                       // Fetch optimization

// Transaction
@Transactional                     // Transaction boundary
@Transactional(readOnly = true)    // Read-only optimization
```

### Common Repository Patterns
```java
// Repository interface
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByActiveTrue();
    Page<User> findByRole(UserRole role, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.lastLogin < :date")
    int deactivateInactiveUsers(@Param("date") LocalDateTime date);
}

// Service usage
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User save(User user) {
        return repository.save(user);
    }
}
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand JPA architecture and persistence context
- [ ] Map entities to database tables using annotations
- [ ] Define entity relationships (One-to-One, One-to-Many, Many-to-Many)
- [ ] Create Spring Data JPA repositories
- [ ] Use built-in CRUD operations
- [ ] Define query methods using naming conventions
- [ ] Write custom queries using @Query (JPQL and native SQL)
- [ ] Implement pagination and sorting
- [ ] Use Specifications for dynamic queries
- [ ] Manage transactions effectively
- [ ] Optimize queries and avoid N+1 problems
- [ ] Handle cascade operations and orphan removal

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 18: REST APIs](../18-rest-api/) - Build RESTful web services
- Practice building complete CRUD applications with JPA
- Explore advanced topics: caching, auditing, multi-tenancy
- Learn query optimization and performance tuning
