# CRUD Operations

## Overview

CRUD = Create, Read, Update, Delete. Spring Data JPA's `save()` method handles both create and update based on entity state.

---

## Create (Save)

### Save a New Entity

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        return userRepository.save(user);  // INSERT
    }
}
```

### Save Multiple Entities

```java
public List<User> createUsers(List<User> users) {
    return userRepository.saveAll(users);
}
```

### How save() Works

- If `id` is **null** → INSERT (new entity)
- If `id` exists → UPDATE (merge)

```java
// INSERT - id is null
User newUser = new User();
newUser.setUsername("john");
userRepository.save(newUser);  // INSERT INTO users ...

// UPDATE - id exists
User existingUser = userRepository.findById(1L).get();
existingUser.setEmail("new@email.com");
userRepository.save(existingUser);  // UPDATE users SET ...
```

---

## Read (Find)

### Find by ID

```java
public User findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found: " + id));
}

// Or with default value
public User findByIdOrDefault(Long id) {
    return userRepository.findById(id)
        .orElse(new User());
}
```

### Find All

```java
public List<User> findAll() {
    return userRepository.findAll();
}
```

### Find by Multiple IDs

```java
public List<User> findByIds(List<Long> ids) {
    return userRepository.findAllById(ids);
}
```

### Check Existence

```java
public boolean exists(Long id) {
    return userRepository.existsById(id);
}

public long count() {
    return userRepository.count();
}
```

---

## Update

### Update via save()

```java
public User updateUser(Long id, String newEmail) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    user.setEmail(newEmail);

    return userRepository.save(user);  // UPDATE
}
```

### Partial Update

```java
public User partialUpdate(Long id, UserUpdateDTO dto) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (dto.getEmail() != null) {
        user.setEmail(dto.getEmail());
    }
    if (dto.getUsername() != null) {
        user.setUsername(dto.getUsername());
    }

    return userRepository.save(user);
}
```

### Bulk Update with @Query

```java
// In repository
@Modifying
@Transactional
@Query("UPDATE User u SET u.active = :active WHERE u.id = :id")
int updateActiveStatus(@Param("id") Long id, @Param("active") boolean active);

// Usage
int rowsUpdated = userRepository.updateActiveStatus(1L, false);
```

---

## Delete

### Delete by ID

```java
public void deleteById(Long id) {
    userRepository.deleteById(id);
}

// With existence check
public void deleteByIdSafe(Long id) {
    if (!userRepository.existsById(id)) {
        throw new RuntimeException("User not found: " + id);
    }
    userRepository.deleteById(id);
}
```

### Delete Entity

```java
public void deleteUser(User user) {
    userRepository.delete(user);
}
```

### Delete All

```java
public void deleteAll() {
    userRepository.deleteAll();
}

// Delete specific entities
public void deleteUsers(List<User> users) {
    userRepository.deleteAll(users);
}
```

### Bulk Delete with @Query

```java
// In repository
@Modifying
@Transactional
@Query("DELETE FROM User u WHERE u.active = false")
int deleteInactiveUsers();

// Usage
int rowsDeleted = userRepository.deleteInactiveUsers();
```

---

## Complete Service Example

```java
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // CREATE
    public Product create(Product product) {
        return productRepository.save(product);
    }

    // READ
    public Product findById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // UPDATE
    public Product update(Long id, ProductDTO dto) {
        Product product = findById(id);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }

    // DELETE
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
```

---

## REST Controller Example

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product created = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## Summary

| Operation | Method | Notes |
|-----------|--------|-------|
| **Create** | `save(entity)` | When id is null |
| **Read** | `findById(id)`, `findAll()` | Returns Optional |
| **Update** | `save(entity)` | When id exists |
| **Delete** | `deleteById(id)`, `delete(entity)` | |
| **Bulk** | `saveAll()`, `deleteAll()` | For multiple entities |

## Next Topic

Continue to [Pagination and Sorting](./05-pagination-sorting.md) to learn how to handle large datasets.
