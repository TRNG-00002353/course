# Stage 9: Microservices - Service Decomposition

## Goal
Split the monolith into independent microservices following 12-Factor App principles.

## The Split

```
BEFORE (Monolith - Stage 8):
┌─────────────────────────────────┐
│       Task Manager API          │
│   ┌─────────────────────────┐   │
│   │  Tasks + (implied Users) │   │
│   └─────────────────────────┘   │
│         Single Database         │
└─────────────────────────────────┘

AFTER (Microservices - Stage 9):
┌─────────────────┐     ┌─────────────────┐
│  Task Service   │     │  User Service   │
│    Port 8081    │     │    Port 8082    │
│   ┌─────────┐   │     │   ┌─────────┐   │
│   │ Tasks   │   │     │   │ Users   │   │
│   │   DB    │   │     │   │   DB    │   │
│   └─────────┘   │     │   └─────────┘   │
└─────────────────┘     └─────────────────┘
```

## 12-Factor App Principles Applied

| Factor | Implementation |
|--------|----------------|
| **1. Codebase** | Each service has its own codebase |
| **2. Dependencies** | Explicitly declared in pom.xml |
| **3. Config** | Externalized in application.properties |
| **4. Backing Services** | Database as attached resource |
| **5. Build/Release/Run** | Maven build → JAR → Run |
| **6. Processes** | Stateless services |
| **7. Port Binding** | Self-contained with embedded Tomcat |
| **8. Concurrency** | Scale by running multiple instances |
| **9. Disposability** | Fast startup/shutdown |
| **10. Dev/Prod Parity** | Same setup via profiles |
| **11. Logs** | Stream to stdout (SLF4J) |
| **12. Admin Processes** | Run as one-off commands |

## Project Structure

```
stage-9-microservices/
├── task-service/
│   ├── pom.xml
│   ├── src/main/java/com/example/taskservice/
│   │   ├── TaskServiceApplication.java
│   │   ├── model/Task.java
│   │   ├── repository/TaskRepository.java
│   │   ├── service/TaskService.java
│   │   ├── controller/TaskController.java
│   │   └── dto/...
│   └── src/main/resources/
│       └── application.properties
│
├── user-service/
│   ├── pom.xml
│   ├── src/main/java/com/example/userservice/
│   │   ├── UserServiceApplication.java
│   │   ├── model/User.java
│   │   ├── repository/UserRepository.java
│   │   ├── service/UserService.java
│   │   ├── controller/UserController.java
│   │   └── dto/...
│   └── src/main/resources/
│       └── application.properties
│
└── README.md
```

## Service Details

### Task Service (Port 8081)
- Manages task CRUD operations
- Has `assigneeId` field (references User Service)
- Endpoints:
  - `GET /api/tasks`
  - `GET /api/tasks/{id}`
  - `POST /api/tasks`
  - `PUT /api/tasks/{id}`
  - `DELETE /api/tasks/{id}`

### User Service (Port 8082)
- Manages user information
- Endpoints:
  - `GET /api/users`
  - `GET /api/users/{id}`
  - `POST /api/users`
  - `PUT /api/users/{id}`
  - `DELETE /api/users/{id}`

## How to Run

```bash
# Terminal 1 - Start Task Service
cd task-service
mvn spring-boot:run

# Terminal 2 - Start User Service
cd user-service
mvn spring-boot:run
```

## Test the Services

```bash
# User Service (port 8082)
curl http://localhost:8082/api/users
curl -X POST http://localhost:8082/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'

# Task Service (port 8081)
curl http://localhost:8081/api/tasks
curl -X POST http://localhost:8081/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Microservices","description":"Study service decomposition","assigneeId":1}'
```

## Key Microservices Concepts

### 1. Single Responsibility
Each service owns one business domain:
- Task Service → Task management
- User Service → User management

### 2. Database per Service
Each service has its own database:
```properties
# task-service
spring.datasource.url=jdbc:h2:mem:taskdb

# user-service
spring.datasource.url=jdbc:h2:mem:userdb
```

### 3. Independent Deployment
Services can be deployed independently:
```bash
# Deploy only task-service
cd task-service && mvn package
java -jar target/task-service.jar
```

### 4. Loose Coupling
Services communicate via REST APIs, not shared code.

## Current Limitation
Task Service stores `assigneeId` but can't verify if user exists.
Stage 10 will add service-to-service communication with Feign.

## What's Next?
Stage 10 adds Eureka for service discovery and Feign for inter-service communication.
