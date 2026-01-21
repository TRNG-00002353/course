# Repositories

## Repository Interface Hierarchy

Spring Data JPA provides a hierarchy of repository interfaces:

```
Repository<T, ID>           (Marker interface)
    ↓
CrudRepository<T, ID>       (Basic CRUD)
    ↓
PagingAndSortingRepository  (+ Pagination, Sorting)
    ↓
JpaRepository<T, ID>        (+ JPA-specific methods)
```

**Use `JpaRepository`** - it includes all features from parent interfaces.

---

## JpaRepository

### Creating a Repository

```java
public interface UserRepository extends JpaRepository<User, Long> {
    // Entity type: User
    // Primary key type: Long
}
```

### Built-in Methods

```java
// Save (insert or update)
User saved = userRepository.save(user);
List<User> savedAll = userRepository.saveAll(users);

// Find
Optional<User> user = userRepository.findById(1L);
List<User> all = userRepository.findAll();
List<User> byIds = userRepository.findAllById(List.of(1L, 2L));

// Check existence
boolean exists = userRepository.existsById(1L);
long count = userRepository.count();

// Delete
userRepository.deleteById(1L);
userRepository.delete(user);
userRepository.deleteAll();
```

---

## Derived Query Methods

Spring Data automatically implements methods based on method names.

### Basic Queries

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // Find by single field
    User findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByLastName(String lastName);

    // Find by multiple fields
    User findByUsernameAndEmail(String username, String email);
    List<User> findByFirstNameOrLastName(String firstName, String lastName);
}
```

### Comparison Operators

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Greater/Less than
    List<Product> findByPriceGreaterThan(Double price);
    List<Product> findByPriceLessThanEqual(Double price);

    // Between
    List<Product> findByPriceBetween(Double min, Double max);

    // Before/After (dates)
    List<Product> findByCreatedAtAfter(LocalDateTime date);
}
```

### String Matching

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // Contains (LIKE %value%)
    List<User> findByEmailContaining(String part);

    // Starts with (LIKE value%)
    List<User> findByUsernameStartingWith(String prefix);

    // Ends with (LIKE %value)
    List<User> findByEmailEndingWith(String suffix);

    // Case insensitive
    List<User> findByUsernameIgnoreCase(String username);
}
```

### Null and Boolean

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // Null checks
    List<User> findByMiddleNameIsNull();
    List<User> findByPhoneIsNotNull();

    // Boolean
    List<User> findByActiveTrue();
    List<User> findByActiveFalse();
}
```

### Ordering and Limiting

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // Order by
    List<User> findByActiveOrderByCreatedAtDesc(boolean active);

    // First/Top
    User findFirstByOrderByCreatedAtDesc();
    List<User> findTop10ByOrderByScoreDesc();
}
```

### Count and Exists

```java
public interface UserRepository extends JpaRepository<User, Long> {

    long countByActive(boolean active);
    boolean existsByEmail(String email);
}
```

---

## @Query Annotation

For complex queries, use `@Query` with JPQL.

### Basic JPQL Queries

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // Named parameters (recommended)
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmailQuery(@Param("email") String email);

    // Multiple conditions
    @Query("SELECT u FROM User u WHERE u.firstName = :first AND u.lastName = :last")
    User findByFullName(@Param("first") String firstName,
                        @Param("last") String lastName);

    // LIKE query
    @Query("SELECT u FROM User u WHERE u.email LIKE %:domain")
    List<User> findByEmailDomain(@Param("domain") String domain);
}
```

### Joins

```java
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Join
    @Query("SELECT o FROM Order o JOIN o.customer c WHERE c.email = :email")
    List<Order> findByCustomerEmail(@Param("email") String email);

    // Join Fetch (avoids N+1 problem)
    @Query("SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = :id")
    Order findOrderWithItems(@Param("id") Long id);
}
```

### Update and Delete Queries

```java
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.active = :active WHERE u.id = :id")
    int updateActiveStatus(@Param("id") Long id, @Param("active") boolean active);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.active = false")
    int deleteInactiveUsers();
}
```

**Note:** `@Modifying` required for UPDATE/DELETE queries.

### Native SQL

```java
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    User findByEmailNative(String email);

    @Query(value = "SELECT * FROM users WHERE department = :dept",
           nativeQuery = true)
    List<User> findByDepartmentNative(@Param("dept") String department);
}
```

---

## Pagination with Queries

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived method with pagination
    Page<Product> findByCategory(String category, Pageable pageable);

    // @Query with pagination
    @Query("SELECT p FROM Product p WHERE p.active = true")
    Page<Product> findActiveProducts(Pageable pageable);
}
```

Usage:
```java
Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
Page<Product> products = productRepository.findByCategory("Electronics", pageable);
```

---

## Query Method Keywords Reference

| Keyword | Sample | JPQL Equivalent |
|---------|--------|-----------------|
| `And` | `findByFirstNameAndLastName` | `WHERE x.first = ?1 AND x.last = ?2` |
| `Or` | `findByFirstNameOrLastName` | `WHERE x.first = ?1 OR x.last = ?2` |
| `Between` | `findByPriceBetween` | `WHERE x.price BETWEEN ?1 AND ?2` |
| `LessThan` | `findByAgeLessThan` | `WHERE x.age < ?1` |
| `GreaterThan` | `findByAgeGreaterThan` | `WHERE x.age > ?1` |
| `IsNull` | `findByAgeIsNull` | `WHERE x.age IS NULL` |
| `IsNotNull` | `findByAgeIsNotNull` | `WHERE x.age IS NOT NULL` |
| `Like` | `findByNameLike` | `WHERE x.name LIKE ?1` |
| `Containing` | `findByNameContaining` | `WHERE x.name LIKE %?1%` |
| `StartingWith` | `findByNameStartingWith` | `WHERE x.name LIKE ?1%` |
| `EndingWith` | `findByNameEndingWith` | `WHERE x.name LIKE %?1` |
| `OrderBy` | `findByOrderByNameAsc` | `ORDER BY x.name ASC` |
| `True` | `findByActiveTrue` | `WHERE x.active = true` |
| `False` | `findByActiveFalse` | `WHERE x.active = false` |

---

## Summary

| Approach | Use Case |
|----------|----------|
| **Built-in methods** | Basic CRUD (save, findById, delete) |
| **Derived queries** | Simple queries by field names |
| **@Query (JPQL)** | Complex queries, joins, aggregations |
| **Native SQL** | Database-specific features |

## Next Topic

Continue to [CRUD Operations](./04-crud-operations.md) to learn practical usage patterns.
