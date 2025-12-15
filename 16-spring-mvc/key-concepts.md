# Spring MVC Key Concepts for Application Developers

## Overview

This document covers essential Spring MVC concepts that every Java web application developer must master. Spring MVC is the web framework component of the Spring Framework, providing a model-view-controller architecture for building robust, maintainable web applications.

---

## 1. MVC Pattern Fundamentals

### Why It Matters
- Separates concerns for better code organization
- Makes applications more maintainable and testable
- Industry-standard pattern for web applications
- Enables parallel development of components

### Key Concepts

| Component | Responsibility | Spring MVC Implementation |
|-----------|---------------|---------------------------|
| Model | Business data and logic | POJOs, Services, Repositories |
| View | Presentation layer | JSP, Thymeleaf, JSON responses |
| Controller | Request handling and flow control | `@Controller`, `@RestController` |

### MVC Flow in Spring
```
Client Request
    ↓
DispatcherServlet (Front Controller)
    ↓
Handler Mapping (finds controller)
    ↓
Controller (processes request)
    ↓
Service Layer (business logic)
    ↓
Repository Layer (data access)
    ↓
Controller (prepares model)
    ↓
ViewResolver (resolves view)
    ↓
View (renders response)
    ↓
Client Response
```

### Basic Example
```java
// Model
public class User {
    private Long id;
    private String username;
    private String email;
    // getters and setters
}

// Controller
@Controller
public class UserController {

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-detail"; // view name
    }
}

// View (user-detail.jsp)
<h1>${user.username}</h1>
<p>${user.email}</p>
```

---

## 2. DispatcherServlet

### Why It Matters
- Core component of Spring MVC
- Central request handler (Front Controller pattern)
- Delegates to specialized components
- Provides extensibility and customization

### Key Concepts

| Component | Purpose |
|-----------|---------|
| DispatcherServlet | Front controller that receives all requests |
| Handler Mapping | Maps requests to controller methods |
| Handler Adapter | Invokes controller methods |
| ViewResolver | Resolves view names to actual views |
| Exception Resolver | Handles exceptions globally |

### Configuration

**Java Configuration:**
```java
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.web")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/resources/");
    }
}
```

**Web Initializer (Replaces web.xml):**
```java
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
```

**Spring Boot (Auto-configured):**
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// application.properties
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
```

---

## 3. Controllers

### Why It Matters
- Handle HTTP requests and responses
- Coordinate between view and model
- Define application endpoints
- Apply validation and error handling

### Key Concepts

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Controller` | Marks class as MVC controller | Returns view names |
| `@RestController` | `@Controller` + `@ResponseBody` | Returns data (JSON/XML) |
| `@RequestMapping` | Maps requests to methods | Base URL mapping |
| `@GetMapping` | Maps GET requests | Retrieve data |
| `@PostMapping` | Maps POST requests | Create data |
| `@PutMapping` | Maps PUT requests | Update data |
| `@DeleteMapping` | Maps DELETE requests | Delete data |
| `@PatchMapping` | Maps PATCH requests | Partial update |

### Controller Examples

**Basic Controller:**
```java
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // List all products
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product-list";
    }

    // Show single product
    @GetMapping("/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    // Show create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    // Handle form submission
    @PostMapping
    public String createProduct(@Valid @ModelAttribute Product product,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "product-form";
        }
        productService.save(product);
        return "redirect:/products";
    }

    // Update product
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute Product product,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "product-form";
        }
        product.setId(id);
        productService.update(product);
        return "redirect:/products/" + id;
    }

    // Delete product
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
```

**REST Controller:**
```java
@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product saved = productService.save(product);
        return ResponseEntity
            .created(URI.create("/api/products/" + saved.getId()))
            .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                  @Valid @RequestBody Product product) {
        product.setId(id);
        Product updated = productService.update(product);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 4. Request Mapping

### Why It Matters
- Routes HTTP requests to specific handler methods
- Supports URL patterns and parameters
- Enables RESTful URL design
- Provides flexibility in endpoint definition

### Key Concepts

| Feature | Annotation Parameter | Example |
|---------|---------------------|---------|
| URL Path | `value` or `path` | `@GetMapping("/users")` |
| HTTP Method | Method-specific annotations | `@PostMapping` |
| Path Variables | `@PathVariable` | `/users/{id}` |
| Query Parameters | `@RequestParam` | `/search?name=John` |
| Headers | `headers` | `headers="X-API-Version=1"` |
| Content Type | `consumes` | `consumes="application/json"` |
| Response Type | `produces` | `produces="application/json"` |

### Request Mapping Examples

**Path Variables:**
```java
@Controller
@RequestMapping("/api")
public class ApiController {

    // Single path variable
    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        // id is extracted from URL
        return "user";
    }

    // Multiple path variables
    @GetMapping("/users/{userId}/orders/{orderId}")
    public String getOrder(@PathVariable Long userId,
                          @PathVariable Long orderId,
                          Model model) {
        return "order";
    }

    // Optional path variable with different name
    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable("productId") Long id, Model model) {
        return "product";
    }
}
```

**Query Parameters:**
```java
@Controller
public class SearchController {

    // Required parameter
    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        // /search?query=spring
        return "results";
    }

    // Optional parameter with default
    @GetMapping("/products")
    public String listProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            Model model) {
        // /products?page=2&size=20
        return "product-list";
    }

    // Multiple values
    @GetMapping("/filter")
    public String filter(@RequestParam List<String> tags, Model model) {
        // /filter?tags=java&tags=spring&tags=mvc
        return "filtered";
    }
}
```

**Request Headers and Content:**
```java
@RestController
@RequestMapping("/api/data")
public class DataController {

    // Specific content type
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Data> createData(@RequestBody Data data) {
        return ResponseEntity.ok(data);
    }

    // Require specific header
    @GetMapping(headers = "X-API-Version=2")
    public List<Data> getDataV2() {
        return dataService.findAllV2();
    }

    // Access headers
    @GetMapping("/secure")
    public ResponseEntity<String> secureEndpoint(
            @RequestHeader("Authorization") String authToken) {
        // Process authorization
        return ResponseEntity.ok("Authorized");
    }
}
```

**Matrix Variables:**
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
}

@Controller
public class MatrixController {

    // /products/42;color=red;size=large
    @GetMapping("/products/{id}")
    public String getProduct(
            @PathVariable Long id,
            @MatrixVariable String color,
            @MatrixVariable String size,
            Model model) {
        return "product";
    }
}
```

---

## 5. Model and Model Attributes

### Why It Matters
- Transfer data between controller and view
- Bind form data to objects
- Share data across requests
- Enable data-driven views

### Key Concepts

| Annotation | Purpose | Scope |
|------------|---------|-------|
| `@ModelAttribute` | Bind request data to object | Method parameter |
| `@ModelAttribute` (method) | Add attributes to all views | Controller-wide |
| `@SessionAttributes` | Store attributes in session | Session scope |
| `Model` interface | Add attributes programmatically | Request scope |
| `ModelAndView` | Combine model and view | Return type |

### Model Examples

**Using Model Interface:**
```java
@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "User Profile");
        return "user-profile";
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("totalCount", userService.count());
        return "user-list";
    }
}
```

**Using @ModelAttribute for Form Binding:**
```java
@Controller
@RequestMapping("/register")
public class RegistrationController {

    // Pre-populate form
    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    // Bind form data
    @PostMapping
    public String processForm(@Valid @ModelAttribute("user") User user,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "registration-form";
        }
        userService.register(user);
        return "redirect:/login";
    }
}
```

**Controller-Wide Model Attributes:**
```java
@Controller
@RequestMapping("/shop")
public class ShopController {

    // This method runs before every handler method
    @ModelAttribute("categories")
    public List<Category> populateCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute("cart")
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        // categories and cart are automatically available
        model.addAttribute("products", productService.findAll());
        return "shop";
    }
}
```

**Session Attributes:**
```java
@Controller
@RequestMapping("/checkout")
@SessionAttributes({"order", "customer"})
public class CheckoutController {

    @GetMapping("/step1")
    public String step1(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customer", new Customer());
        return "checkout-step1";
    }

    @PostMapping("/step1")
    public String processStep1(@ModelAttribute("customer") Customer customer) {
        // Customer is stored in session
        return "redirect:/checkout/step2";
    }

    @GetMapping("/step2")
    public String step2(@ModelAttribute("order") Order order,
                       @ModelAttribute("customer") Customer customer) {
        // Both retrieved from session
        return "checkout-step2";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute("order") Order order,
                          @ModelAttribute("customer") Customer customer,
                          SessionStatus status) {
        orderService.place(order, customer);
        status.setComplete(); // Clear session attributes
        return "redirect:/confirmation";
    }
}
```

**ModelAndView:**
```java
@Controller
public class ProductController {

    @GetMapping("/products/{id}")
    public ModelAndView showProduct(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("product-detail");
        Product product = productService.findById(id);
        mav.addObject("product", product);
        mav.addObject("relatedProducts", productService.findRelated(id));
        return mav;
    }
}
```

---

## 6. ViewResolver

### Why It Matters
- Resolves logical view names to actual view resources
- Supports multiple view technologies
- Enables view layer flexibility
- Separates controller logic from view location

### Key Concepts

| ViewResolver Type | View Technology | Use Case |
|------------------|----------------|----------|
| `InternalResourceViewResolver` | JSP | Traditional Java web apps |
| `ThymeleafViewResolver` | Thymeleaf | Modern template engine |
| `FreeMarkerViewResolver` | FreeMarker | Template-based views |
| `ContentNegotiatingViewResolver` | Multiple | Content negotiation |
| N/A (REST) | JSON/XML | RESTful APIs |

### ViewResolver Configuration

**JSP Configuration:**
```java
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(2);
        return resolver;
    }
}

// Controller returns "user-list"
// Resolves to: /WEB-INF/views/user-list.jsp
```

**Thymeleaf Configuration:**
```java
@Configuration
@EnableWebMvc
public class ThymeleafConfig implements WebMvcConfigurer {

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false); // Development
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(1);
        return resolver;
    }
}
```

**Spring Boot Auto-Configuration:**
```properties
# application.properties

# Thymeleaf
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false

# JSP (requires additional setup)
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

**Multiple View Resolvers:**
```java
@Configuration
public class ViewConfig {

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        List<ViewResolver> resolvers = new ArrayList<>();
        resolvers.add(thymeleafViewResolver());
        resolvers.add(jsonViewResolver());

        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    @Bean
    public ViewResolver jsonViewResolver() {
        return (viewName, locale) -> {
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            view.setPrettyPrint(true);
            return view;
        };
    }
}
```

### View Examples

**JSP View (user-list.jsp):**
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
</head>
<body>
    <h1>Users</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
```

**Thymeleaf View (user-list.html):**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>User List</title>
</head>
<body>
    <h1>Users</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <tr th:each="user : ${users}">
                <td th:text="${user.id}">1</td>
                <td th:text="${user.username}">john</td>
                <td th:text="${user.email}">john@example.com</td>
            </tr>
        </tbody>
    </table>
</body>
</html>
```

---

## 7. Form Handling and Validation

### Why It Matters
- Capture and process user input
- Ensure data integrity and security
- Provide user feedback
- Prevent invalid data from entering system

### Key Concepts

| Component | Purpose |
|-----------|---------|
| `@ModelAttribute` | Bind form data to object |
| `BindingResult` | Holds validation errors |
| `@Valid` / `@Validated` | Trigger validation |
| JSR-303/380 Annotations | Define validation rules |
| Custom Validators | Complex validation logic |

### Form Validation Examples

**Entity with Validation:**
```java
import jakarta.validation.constraints.*;

public class User {

    @NotNull
    @Size(min = 3, max = 20, message = "Username must be 3-20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Min(value = 18, message = "Must be at least 18 years old")
    @Max(value = 120, message = "Invalid age")
    private Integer age;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @AssertTrue(message = "Must accept terms and conditions")
    private Boolean agreedToTerms;

    // getters and setters
}
```

**Controller with Validation:**
```java
@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "registration-form";
        }

        // Custom validation
        if (userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "duplicate.username",
                             "Username already exists");
            return "registration-form";
        }

        userService.register(user);
        return "redirect:/login?registered";
    }
}
```

**Thymeleaf Form with Error Display:**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Register</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
    <h1>Register</h1>

    <form th:action="@{/users/register}" th:object="${user}" method="post">

        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" th:field="*{username}" />
            <span class="error" th:if="${#fields.hasErrors('username')}"
                  th:errors="*{username}">Username Error</span>
        </div>

        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" th:field="*{email}" />
            <span class="error" th:if="${#fields.hasErrors('email')}"
                  th:errors="*{email}">Email Error</span>
        </div>

        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" th:field="*{password}" />
            <span class="error" th:if="${#fields.hasErrors('password')}"
                  th:errors="*{password}">Password Error</span>
        </div>

        <div>
            <label for="age">Age:</label>
            <input type="number" id="age" th:field="*{age}" />
            <span class="error" th:if="${#fields.hasErrors('age')}"
                  th:errors="*{age}">Age Error</span>
        </div>

        <div>
            <input type="checkbox" id="terms" th:field="*{agreedToTerms}" />
            <label for="terms">I agree to terms and conditions</label>
            <span class="error" th:if="${#fields.hasErrors('agreedToTerms')}"
                  th:errors="*{agreedToTerms}">Terms Error</span>
        </div>

        <div>
            <button type="submit">Register</button>
        </div>
    </form>
</body>
</html>
```

**Custom Validator:**
```java
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Custom validation logic
        if (user.getPassword() != null &&
            !user.getPassword().matches(".*[A-Z].*")) {
            errors.rejectValue("password", "password.noUpperCase",
                             "Password must contain at least one uppercase letter");
        }

        if (userService.existsByEmail(user.getEmail())) {
            errors.rejectValue("email", "duplicate.email",
                             "Email already registered");
        }
    }
}

// Using custom validator in controller
@Controller
public class UserController {

    @Autowired
    private UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "registration-form";
        }
        userService.save(user);
        return "redirect:/success";
    }
}
```

---

## 8. Exception Handling

### Why It Matters
- Graceful error handling improves user experience
- Centralized error handling reduces code duplication
- Proper error responses aid debugging
- Security: avoid exposing sensitive information

### Key Concepts

| Approach | Annotation/Class | Scope |
|----------|-----------------|-------|
| Controller-level | `@ExceptionHandler` | Single controller |
| Global | `@ControllerAdvice` | All controllers |
| HTTP Status | `@ResponseStatus` | Specific exception |
| Custom Error Pages | Error page mapping | View-based errors |

### Exception Handling Examples

**Controller-Level Exception Handling:**
```java
@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found: " + id);
        }
        model.addAttribute("product", product);
        return "product-detail";
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/product-not-found";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("error", "An error occurred");
        return "error/general";
    }
}
```

**Global Exception Handling:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("message", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleValidation(ValidationException ex) {
        ModelAndView mav = new ModelAndView("error/validation");
        mav.addObject("errors", ex.getErrors());
        return mav;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("message", "Access denied");
        return "error/403";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected error occurred", ex);
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("url", request.getRequestURL());
        mav.addObject("message", "An unexpected error occurred");
        return mav;
    }
}
```

**REST Exception Handling:**
```java
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());

        ValidationErrorResponse response = new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal server error",
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// Error response classes
class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    // constructors, getters, setters
}

class ValidationErrorResponse extends ErrorResponse {
    private List<String> errors;

    // constructors, getters, setters
}
```

**Custom Exception with @ResponseStatus:**
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String message) {
        super(message);
    }
}
```

---

## 9. Interceptors

### Why It Matters
- Cross-cutting concerns (logging, security, timing)
- Pre-processing and post-processing requests
- Modify requests/responses globally
- Clean separation of concerns

### Key Concepts

| Method | When Called | Use Case |
|--------|-------------|----------|
| `preHandle` | Before controller execution | Authentication, logging |
| `postHandle` | After controller, before view | Modify model data |
| `afterCompletion` | After view rendering | Cleanup, metrics |

### Interceptor Examples

**Basic Interceptor:**
```java
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) throws Exception {
        logger.info("Request URL: {} {}", request.getMethod(), request.getRequestURI());
        logger.info("Client IP: {}", request.getRemoteAddr());
        request.setAttribute("startTime", System.currentTimeMillis());
        return true; // Continue to next interceptor or controller
    }

    @Override
    public void postHandle(HttpServletRequest request,
                          HttpServletResponse response,
                          Object handler,
                          ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            logger.info("View name: {}", modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                               HttpServletResponse response,
                               Object handler,
                               Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        logger.info("Request completed in {} ms", (endTime - startTime));

        if (ex != null) {
            logger.error("Request failed with exception", ex);
        }
    }
}
```

**Authentication Interceptor:**
```java
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // Stop processing
        }

        return true;
    }
}
```

**Performance Monitoring Interceptor:**
```java
@Component
public class PerformanceInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);
    private static final long SLOW_REQUEST_THRESHOLD = 1000; // 1 second

    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) {
        request.setAttribute("startTime", System.nanoTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                               HttpServletResponse response,
                               Object handler,
                               Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1_000_000; // Convert to ms

        if (executionTime > SLOW_REQUEST_THRESHOLD) {
            logger.warn("SLOW REQUEST: {} {} took {} ms",
                request.getMethod(),
                request.getRequestURI(),
                executionTime);
        }
    }
}
```

**Register Interceptors:**
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private PerformanceInterceptor performanceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Apply to all requests
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(performanceInterceptor);

        // Apply to specific patterns
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/admin/**", "/user/**")
                .excludePathPatterns("/login", "/register", "/public/**");
    }
}
```

---

## Quick Reference Card

### Essential Annotations
```java
// Controller Definition
@Controller                          // MVC controller
@RestController                      // REST controller (@Controller + @ResponseBody)
@RequestMapping("/path")            // Base path mapping

// Request Mapping
@GetMapping("/path")                // GET request
@PostMapping("/path")               // POST request
@PutMapping("/path")                // PUT request
@DeleteMapping("/path")             // DELETE request

// Method Parameters
@PathVariable Long id               // Extract from URL path
@RequestParam String name           // Query parameter
@RequestBody User user              // JSON/XML in request body
@ModelAttribute User user           // Form data binding
@RequestHeader String auth          // HTTP header

// Model & Validation
@Valid                              // Trigger validation
@Validated                          // Group validation
BindingResult result                // Validation results
Model model                         // Add view attributes

// Exception Handling
@ExceptionHandler                   // Handle specific exceptions
@ControllerAdvice                   // Global exception handler
@ResponseStatus(HttpStatus.X)       // Set HTTP status
```

### Common Patterns
```java
// Basic Controller
@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", service.findAll());
        return "user-list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", service.findById(id));
        return "user-detail";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute User user,
                        BindingResult result) {
        if (result.hasErrors()) return "user-form";
        service.save(user);
        return "redirect:/users";
    }
}

// REST Controller
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User saved = service.save(user);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId()))
                           .body(saved);
    }
}
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand MVC architecture and its benefits
- [ ] Configure DispatcherServlet and Spring MVC components
- [ ] Create controllers using `@Controller` and `@RestController`
- [ ] Map HTTP requests using request mapping annotations
- [ ] Handle path variables and request parameters
- [ ] Work with Model to pass data to views
- [ ] Configure and use ViewResolvers (JSP, Thymeleaf)
- [ ] Implement form handling with data binding
- [ ] Apply validation using JSR-303/380 annotations
- [ ] Handle exceptions at controller and global level
- [ ] Implement interceptors for cross-cutting concerns
- [ ] Build both traditional web apps and REST APIs with Spring MVC

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 17: Spring Data JPA](../17-spring-data-jpa/) - Database integration with Spring
- Build a complete CRUD web application using Spring MVC
- Explore advanced topics: content negotiation, file uploads, WebSockets
- Practice implementing security with Spring Security
