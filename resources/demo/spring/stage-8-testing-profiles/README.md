# Stage 8: Profiles, Testing, and Logging

## Goal
Add environment-specific configuration, comprehensive testing, and structured logging.

## What's New

### 1. Spring Profiles
Different configurations for different environments:

```
application.properties        → Common settings
application-dev.properties    → Development (H2, debug logging)
application-prod.properties   → Production (MySQL, info logging)
application-test.properties   → Testing (H2, minimal logging)
```

**Activate profile:**
```bash
# Command line
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Environment variable
export SPRING_PROFILES_ACTIVE=prod

# In application.properties
spring.profiles.active=dev
```

### 2. SLF4J Logging
Structured logging throughout the application:

```java
@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    public Task createTask(String title, String description) {
        log.info("Creating task with title: {}", title);
        // ... business logic
        log.debug("Task created successfully: {}", task.getId());
        return task;
    }
}
```

**Log Levels:** TRACE < DEBUG < INFO < WARN < ERROR

### 3. Testing Layers

```
┌─────────────────────────────────────────┐
│           Integration Tests             │
│         @SpringBootTest                 │
│    Full application context loaded      │
├─────────────────────────────────────────┤
│           Controller Tests              │
│           @WebMvcTest                   │
│    Only web layer, mocked service       │
├─────────────────────────────────────────┤
│          Repository Tests               │
│          @DataJpaTest                   │
│    Only JPA layer, embedded DB          │
├─────────────────────────────────────────┤
│            Unit Tests                   │
│         @ExtendWith(MockitoExtension)   │
│    No Spring context, pure unit tests   │
└─────────────────────────────────────────┘
```

## Project Structure

```
stage-8-testing-profiles/
├── src/main/java/
│   └── com/example/taskmanager/
│       ├── Application.java
│       ├── config/
│       │   └── LoggingAspect.java      # Optional AOP logging
│       ├── controller/
│       ├── dto/
│       ├── exception/
│       ├── model/
│       ├── repository/
│       └── service/
├── src/main/resources/
│   ├── application.properties           # Common config
│   ├── application-dev.properties       # Dev profile
│   ├── application-prod.properties      # Prod profile
│   └── logback-spring.xml              # Logging config
└── src/test/java/
    └── com/example/taskmanager/
        ├── controller/
        │   └── TaskControllerTest.java  # @WebMvcTest
        ├── repository/
        │   └── TaskRepositoryTest.java  # @DataJpaTest
        └── service/
            └── TaskServiceTest.java     # Unit test
```

## How to Run

```bash
# Run with dev profile (default)
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Run tests
mvn test

# Run specific test class
mvn test -Dtest=TaskServiceTest
```

## Profile Configuration

### application.properties (Common)
```properties
spring.application.name=task-manager
spring.profiles.active=dev
```

### application-dev.properties
```properties
# H2 embedded database
spring.datasource.url=jdbc:h2:mem:taskdb
spring.h2.console.enabled=true
spring.jpa.show-sql=true

# Debug logging
logging.level.com.example=DEBUG
```

### application-prod.properties
```properties
# MySQL database
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.h2.console.enabled=false
spring.jpa.show-sql=false

# Production logging
logging.level.com.example=INFO
logging.level.root=WARN
```

## Testing Examples

### Unit Test (TaskServiceTest)
```java
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ValidInput_ReturnsTask() {
        // Arrange
        when(taskRepository.save(any())).thenReturn(new Task("Test", "Desc"));

        // Act
        Task result = taskService.createTask("Test", "Desc");

        // Assert
        assertThat(result.getTitle()).isEqualTo("Test");
        verify(taskRepository).save(any());
    }
}
```

### Controller Test (TaskControllerTest)
```java
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void getAllTasks_ReturnsTaskList() throws Exception {
        mockMvc.perform(get("/api/tasks"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }
}
```

### Repository Test (TaskRepositoryTest)
```java
@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findByStatus_ReturnsMatchingTasks() {
        // Uses embedded H2 database
        taskRepository.save(new Task("Test", "Desc"));

        List<Task> results = taskRepository.findByStatus(TaskStatus.PENDING);

        assertThat(results).hasSize(1);
    }
}
```

## Logging Best Practices

1. **Use parameterized messages** (avoid string concatenation):
   ```java
   log.info("Processing task: {}", taskId);  // Good
   log.info("Processing task: " + taskId);   // Bad
   ```

2. **Log at appropriate levels:**
   - ERROR: Failures that need immediate attention
   - WARN: Unexpected but handled situations
   - INFO: Significant business events
   - DEBUG: Detailed flow information
   - TRACE: Very detailed debugging

3. **Include context:**
   ```java
   log.info("Task {} status changed from {} to {}", taskId, oldStatus, newStatus);
   ```

## What's Next?
Stage 9 introduces microservices with service decomposition and 12-factor principles.
