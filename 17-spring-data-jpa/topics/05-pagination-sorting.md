# Pagination and Sorting

## Why Pagination?

Loading all records at once is inefficient for large datasets. Pagination loads data in chunks (pages), improving performance and user experience.

---

## Pageable

`Pageable` holds pagination parameters: page number, page size, and sorting.

### Creating Pageable

```java
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

// Basic: page 0, size 10
Pageable pageable = PageRequest.of(0, 10);

// With sorting
Pageable pageableSorted = PageRequest.of(0, 10, Sort.by("name").ascending());

// With multiple sorts
Pageable pageableMultiSort = PageRequest.of(0, 10,
    Sort.by("category").ascending()
        .and(Sort.by("price").descending()));
```

**Note:** Page numbers are 0-indexed (first page = 0).

---

## Sort

### Creating Sort Objects

```java
// Single field ascending
Sort sortByName = Sort.by("name").ascending();

// Single field descending
Sort sortByPrice = Sort.by("price").descending();

// Multiple fields
Sort multiSort = Sort.by("category").ascending()
    .and(Sort.by("name").ascending());

// Alternative syntax
Sort multiSort2 = Sort.by(
    Sort.Order.asc("category"),
    Sort.Order.desc("price")
);
```

---

## Using in Repository

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Pagination
    Page<Product> findAll(Pageable pageable);

    // Pagination with filter
    Page<Product> findByCategory(String category, Pageable pageable);

    // Sorting only
    List<Product> findByCategory(String category, Sort sort);

    // @Query with pagination
    @Query("SELECT p FROM Product p WHERE p.active = true")
    Page<Product> findActiveProducts(Pageable pageable);
}
```

---

## Page Object

`Page<T>` contains the results plus metadata about the entire dataset.

```java
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return productRepository.findAll(pageable);
    }
}
```

### Page Methods

```java
Page<Product> page = productRepository.findAll(PageRequest.of(0, 10));

// Content
List<Product> products = page.getContent();

// Pagination info
int currentPage = page.getNumber();        // Current page (0-indexed)
int pageSize = page.getSize();             // Items per page
long totalElements = page.getTotalElements(); // Total items in DB
int totalPages = page.getTotalPages();     // Total pages

// Navigation
boolean hasNext = page.hasNext();
boolean hasPrevious = page.hasPrevious();
boolean isFirst = page.isFirst();
boolean isLast = page.isLast();
```

---

## REST Controller with Pagination

### Manual Parameters

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> productPage = productService.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("products", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
```

### Automatic Pageable Resolution

Spring can automatically create `Pageable` from request parameters.

```java
@GetMapping
public Page<Product> getProducts(Pageable pageable) {
    return productService.findAll(pageable);
}
```

**Request:** `GET /api/products?page=0&size=20&sort=name,asc`

### With Default Values

```java
@GetMapping
public Page<Product> getProducts(
        @PageableDefault(size = 20, sort = "createdAt",
                        direction = Sort.Direction.DESC)
        Pageable pageable) {
    return productService.findAll(pageable);
}
```

---

## Complete Pagination Example

### Repository

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByPriceGreaterThan(Double price, Pageable pageable);
}
```

### Service

```java
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return productRepository.findByCategory(category, pageable);
    }
}
```

### Controller

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getAll(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/category/{category}")
    public Page<Product> getByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.findByCategory(category, page, size);
    }
}
```

### Sample API Responses

**Request:** `GET /api/products?page=0&size=2&sort=price,desc`

**Response:**
```json
{
  "content": [
    {"id": 5, "name": "Laptop", "price": 999.99},
    {"id": 3, "name": "Phone", "price": 699.99}
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 2,
    "sort": {"sorted": true}
  },
  "totalElements": 50,
  "totalPages": 25,
  "first": true,
  "last": false,
  "numberOfElements": 2
}
```

---

## Auditing (Bonus)

Track when entities are created/modified.

### Enable Auditing

```java
@SpringBootApplication
@EnableJpaAuditing
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### Add Audit Fields

```java
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

Fields are automatically populated on save/update.

---

## Summary

| Concept | Description |
|---------|-------------|
| **Pageable** | Holds page, size, sort parameters |
| **PageRequest.of()** | Create Pageable instances |
| **Sort** | Define ordering |
| **Page<T>** | Results + pagination metadata |
| **@PageableDefault** | Default pagination in controllers |
| **@EnableJpaAuditing** | Auto-populate created/modified dates |

## Module Complete

You've learned the fundamentals of Spring Data JPA:
- JPA concepts and Spring Data JPA benefits
- Entity mapping and relationships
- Repository interfaces and query methods
- CRUD operations
- Pagination and sorting

Practice with the [exercises](../exercises/) to reinforce your learning.
