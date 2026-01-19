# Stage 10: Service Discovery with Eureka + Feign

## Learning Objectives
- Understand service discovery patterns
- Set up Netflix Eureka Server
- Register services with Eureka
- Implement inter-service communication with OpenFeign
- Handle service-to-service failures gracefully

## What's New in This Stage

### Service Discovery Pattern
In microservices, services need to find each other dynamically. Eureka provides:
- **Service Registry**: Central registry where services register themselves
- **Service Discovery**: Services can look up other services by name
- **Health Monitoring**: Automatic removal of unhealthy instances

### Architecture
```
                    ┌─────────────────┐
                    │  Eureka Server  │
                    │   (Port 8761)   │
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
              ▼              ▼              ▼
       ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
       │Task Service │ │User Service │ │   Client    │
       │ (Port 8081) │ │ (Port 8082) │ │   (API)     │
       └──────┬──────┘ └─────────────┘ └─────────────┘
              │              ▲
              │    Feign     │
              └──────────────┘
```

## Project Structure
```
stage-10-service-discovery/
├── discovery-server/          # Eureka Server
│   ├── pom.xml
│   └── src/main/
│       ├── java/.../DiscoveryServerApplication.java
│       └── resources/application.properties
├── task-service/              # Eureka Client + Feign Client
│   ├── pom.xml
│   └── src/main/
│       ├── java/.../
│       │   ├── TaskServiceApplication.java
│       │   ├── client/UserClient.java        # NEW: Feign client
│       │   ├── dto/UserDTO.java              # NEW: User data from user-service
│       │   └── ... (same as stage-9)
│       └── resources/application.properties   # Updated for Eureka
└── user-service/              # Eureka Client
    ├── pom.xml
    └── src/main/
        ├── java/... (same as stage-9)
        └── resources/application.properties   # Updated for Eureka
```

## Key Concepts

### 1. Eureka Server Setup
```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
```

### 2. Eureka Client Configuration
```properties
# Register with Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
spring.application.name=task-service
```

### 3. Feign Client for Inter-Service Communication
```java
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
```

### 4. Using Feign in Service Layer
```java
@Service
public class TaskService {
    private final UserClient userClient;

    public Task createTask(CreateTaskRequest request) {
        // Validate assignee exists in user-service
        UserDTO user = userClient.getUserById(request.assigneeId());
        // Create task...
    }
}
```

## Running the Services

### Start Order (Important!)
```bash
# 1. Start Eureka Server first
cd discovery-server
mvn spring-boot:run

# 2. Start User Service
cd ../user-service
mvn spring-boot:run

# 3. Start Task Service
cd ../task-service
mvn spring-boot:run
```

### Verify Registration
Open http://localhost:8761 to see Eureka Dashboard with registered services.

## Testing Inter-Service Communication

```bash
# Create a user first
curl -X POST http://localhost:8082/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john@example.com"}'
# Response: {"id": 1, "name": "John Doe", ...}

# Create a task assigned to that user
curl -X POST http://localhost:8081/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Learn Eureka", "description": "Study service discovery", "assigneeId": 1}'

# If user doesn't exist, you get an error
curl -X POST http://localhost:8081/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Test", "description": "Test", "assigneeId": 999}'
# Response: 404 - Assignee not found
```

## Key Dependencies

### Eureka Server
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

### Eureka Client + Feign
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

## Evolution from Stage 9
| Stage 9 | Stage 10 |
|---------|----------|
| Hardcoded URLs | Service discovery by name |
| No inter-service calls | Feign client for user validation |
| Manual service location | Dynamic service registry |

## What's Next (Stage 11)
- API Gateway for single entry point
- Centralized configuration with Config Server
- Load balancing across service instances
