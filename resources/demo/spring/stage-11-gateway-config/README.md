# Stage 11: API Gateway + Config Server

## Learning Objectives
- Implement API Gateway pattern with Spring Cloud Gateway
- Centralize configuration with Spring Cloud Config Server
- Understand routing, filtering, and load balancing
- Manage configuration across multiple services

## What's New in This Stage

### API Gateway Pattern
A single entry point for all client requests that:
- Routes requests to appropriate microservices
- Provides cross-cutting concerns (auth, logging, rate limiting)
- Load balances across service instances
- Hides internal service topology from clients

### Centralized Configuration
Instead of duplicating configuration across services:
- Store all configuration in a central repository
- Services fetch their configuration at startup
- Easy to manage environment-specific configs

### Architecture
```
                         ┌─────────────────────┐
                         │    Config Server    │
                         │    (Port 8888)      │
                         └──────────┬──────────┘
                                    │ fetch config
          ┌─────────────────────────┼─────────────────────────┐
          │                         │                         │
          ▼                         ▼                         ▼
   ┌─────────────┐          ┌─────────────┐          ┌─────────────┐
   │ API Gateway │          │Task Service │          │User Service │
   │ (Port 8080) │          │ (Port 8081) │          │ (Port 8082) │
   └──────┬──────┘          └──────▲──────┘          └──────▲──────┘
          │                        │                        │
          │    ┌───────────────────┴────────────────────────┘
          │    │          Eureka Discovery
          ▼    ▼               (Port 8761)
   ┌─────────────────┐
   │     Client      │
   │  (Single URL)   │
   └─────────────────┘

Client → http://localhost:8080/api/tasks  → routes to → task-service
Client → http://localhost:8080/api/users  → routes to → user-service
```

## Project Structure
```
stage-11-gateway-config/
├── config-repo/                    # Git repo for configurations
│   ├── application.yml             # Shared config for all services
│   ├── task-service.yml            # Task service specific config
│   └── user-service.yml            # User service specific config
├── config-server/                  # Spring Cloud Config Server
│   ├── pom.xml
│   └── src/main/
│       ├── java/.../ConfigServerApplication.java
│       └── resources/application.properties
├── api-gateway/                    # Spring Cloud Gateway
│   ├── pom.xml
│   └── src/main/
│       ├── java/.../ApiGatewayApplication.java
│       └── resources/application.yml
├── task-service/                   # Updated to use Config Server
└── user-service/                   # Updated to use Config Server
```

## Key Concepts

### 1. Config Server Setup
```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

### 2. Config Client (Services)
```properties
# bootstrap.properties (loaded first, before application.properties)
spring.application.name=task-service
spring.config.import=optional:configserver:http://localhost:8888
```

### 3. API Gateway Routes
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: task-service
          uri: lb://task-service    # lb:// enables load balancing via Eureka
          predicates:
            - Path=/api/tasks/**
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**
```

### 4. Gateway Filters
```yaml
spring:
  cloud:
    gateway:
      default-filters:
        - DedupeResponseHeader=Access-Control-Allow-Origin
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "*"
            allowedMethods: "*"
```

## Running the Services

### Start Order (Important!)
```bash
# 1. Start Config Server first (services need it for configuration)
cd config-server
mvn spring-boot:run

# 2. Start Eureka Server (copy from stage-10)
cd ../discovery-server
mvn spring-boot:run

# 3. Start User Service
cd ../user-service
mvn spring-boot:run

# 4. Start Task Service
cd ../task-service
mvn spring-boot:run

# 5. Start API Gateway
cd ../api-gateway
mvn spring-boot:run
```

### Verify Setup
- Config Server: http://localhost:8888/task-service/default
- Eureka Dashboard: http://localhost:8761
- Gateway: http://localhost:8080/api/tasks

## Testing Through Gateway

All requests now go through the gateway on port 8080:

```bash
# Create user via gateway
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Doe", "email": "jane@example.com"}'

# Create task via gateway
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Learn Gateway", "description": "Study API Gateway pattern", "assigneeId": 1}'

# Get all tasks via gateway
curl http://localhost:8080/api/tasks

# Get all users via gateway
curl http://localhost:8080/api/users
```

## Benefits of This Architecture

### API Gateway Benefits
| Benefit | Description |
|---------|-------------|
| Single Entry Point | Clients only need to know one URL |
| Security | Centralized authentication/authorization |
| Load Balancing | Automatic distribution across instances |
| Protocol Translation | Can transform requests/responses |
| Rate Limiting | Protect services from overload |

### Config Server Benefits
| Benefit | Description |
|---------|-------------|
| Centralized | All configuration in one place |
| Version Control | Track configuration changes |
| Environment Specific | Easy dev/test/prod configurations |
| Dynamic Refresh | Update config without restart (with actuator) |
| Encrypted Secrets | Can encrypt sensitive values |

## Evolution from Stage 10
| Stage 10 | Stage 11 |
|----------|----------|
| Direct service access | Single gateway entry point |
| Local properties files | Centralized configuration |
| Multiple ports exposed | Only gateway port (8080) exposed |
| No cross-cutting concerns | Gateway handles common concerns |

## Configuration Hierarchy
```
config-repo/
├── application.yml          # Common config for ALL services
├── task-service.yml         # Overrides for task-service
├── task-service-dev.yml     # Dev profile for task-service
├── task-service-prod.yml    # Prod profile for task-service
└── user-service.yml         # Overrides for user-service
```

Config precedence (highest to lowest):
1. Service's local application.properties
2. {service-name}-{profile}.yml from config server
3. {service-name}.yml from config server
4. application.yml from config server

## Complete Microservices Journey Summary

```
Stage 1-5:  Monolith Foundation (Console → Spring Boot → JPA)
Stage 6-7:  REST API Development (Controllers → Production-ready)
Stage 8:    Quality & Environments (Testing → Profiles → Logging)
Stage 9:    Service Decomposition (Monolith → Microservices)
Stage 10:   Service Discovery (Eureka → Feign)
Stage 11:   Gateway & Config (API Gateway → Centralized Config)
```

You now have a complete microservices architecture!
