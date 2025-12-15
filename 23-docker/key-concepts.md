# Docker Key Concepts for Application Developers

## Overview

This document covers essential Docker concepts for building, deploying, and managing containerized applications. Docker enables developers to package applications with all dependencies into standardized units called containers, ensuring consistency across development, testing, and production environments.

---

## 1. Containers vs Virtual Machines

### Why It Matters
- Understanding the difference informs architecture decisions
- Containers are faster and more resource-efficient
- Essential for modern DevOps practices
- Enables consistent deployment across environments

### Key Concepts

| Aspect | Container | Virtual Machine |
|--------|-----------|-----------------|
| Virtualization | OS-level | Hardware-level |
| Size | Megabytes | Gigabytes |
| Startup Time | Seconds | Minutes |
| Resource Usage | Shared kernel, lightweight | Full OS, heavy |
| Isolation | Process-level | Complete isolation |
| Portability | High | Moderate |

### Architecture Comparison
```
Virtual Machines:
┌─────────────────────────────────────┐
│  App A  │  App B  │  App C          │
│  Bins   │  Bins   │  Bins           │
│  Libs   │  Libs   │  Libs           │
├─────────┼─────────┼─────────────────┤
│ Guest OS│ Guest OS│ Guest OS        │
├─────────────────────────────────────┤
│         Hypervisor                  │
├─────────────────────────────────────┤
│         Host OS                     │
├─────────────────────────────────────┤
│         Infrastructure              │
└─────────────────────────────────────┘

Containers:
┌─────────────────────────────────────┐
│  App A  │  App B  │  App C          │
│  Bins   │  Bins   │  Bins           │
│  Libs   │  Libs   │  Libs           │
├─────────────────────────────────────┤
│         Docker Engine               │
├─────────────────────────────────────┤
│         Host OS                     │
├─────────────────────────────────────┤
│         Infrastructure              │
└─────────────────────────────────────┘
```

---

## 2. Docker Images - Application Templates

### Why It Matters
- Images are blueprints for containers
- Layer-based architecture enables efficient storage
- Version control for application environments
- Foundation for consistent deployments

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| Image | Read-only template | `node:18-alpine` |
| Layer | Incremental change | `RUN npm install` |
| Base Image | Starting point | `FROM ubuntu:22.04` |
| Tag | Version identifier | `myapp:1.0.0` |
| Registry | Image storage | Docker Hub, ECR |

### Image Layers
```
Image: myapp:latest
┌────────────────────────────┐
│ App Code (10 MB)           │  ← Layer 5
├────────────────────────────┤
│ Dependencies (150 MB)      │  ← Layer 4
├────────────────────────────┤
│ Node.js (50 MB)            │  ← Layer 3
├────────────────────────────┤
│ Base OS (5 MB)             │  ← Layer 2
├────────────────────────────┤
│ Scratch                    │  ← Layer 1
└────────────────────────────┘
```

### Working with Images
```bash
# List local images
docker images
docker image ls

# Pull image from registry
docker pull node:18-alpine
docker pull nginx:latest

# Pull specific version
docker pull postgres:15.2

# Search Docker Hub
docker search nodejs

# Inspect image details
docker image inspect node:18

# View image history (layers)
docker history node:18

# Remove image
docker rmi node:18
docker image rm nginx:latest

# Remove unused images
docker image prune
docker image prune -a  # Remove all unused

# Tag image
docker tag myapp:latest myapp:1.0.0
docker tag myapp:latest username/myapp:latest

# Save/Load images
docker save -o myapp.tar myapp:latest
docker load -i myapp.tar
```

---

## 3. Dockerfile - Building Images

### Why It Matters
- Define reproducible build process
- Automate image creation
- Version control your infrastructure
- Optimize image size and build time

### Key Instructions

| Instruction | Purpose | Example |
|------------|---------|---------|
| `FROM` | Base image | `FROM node:18-alpine` |
| `WORKDIR` | Set working directory | `WORKDIR /app` |
| `COPY` | Copy files | `COPY . .` |
| `RUN` | Execute commands | `RUN npm install` |
| `ENV` | Set environment variables | `ENV NODE_ENV=production` |
| `EXPOSE` | Document ports | `EXPOSE 3000` |
| `CMD` | Default command | `CMD ["npm", "start"]` |
| `ENTRYPOINT` | Fixed command | `ENTRYPOINT ["node"]` |

### Basic Dockerfile Examples

**Node.js Application:**
```dockerfile
# Base image
FROM node:18-alpine

# Set working directory
WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy application code
COPY . .

# Expose port
EXPOSE 3000

# Start application
CMD ["npm", "start"]
```

**Multi-stage Build (Optimized):**
```dockerfile
# Build stage
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Production stage
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install --production
COPY --from=builder /app/dist ./dist
EXPOSE 3000
CMD ["node", "dist/main.js"]
```

**Python Application:**
```dockerfile
FROM python:3.11-slim

WORKDIR /app

# Install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy application
COPY . .

# Run application
EXPOSE 8000
CMD ["python", "app.py"]
```

**Java Application:**
```dockerfile
# Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/myapp.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "myapp.jar"]
```

### Building Images
```bash
# Build image
docker build -t myapp:latest .

# Build with specific Dockerfile
docker build -f Dockerfile.prod -t myapp:prod .

# Build with build arguments
docker build --build-arg VERSION=1.0 -t myapp:1.0 .

# Build without cache
docker build --no-cache -t myapp:latest .

# Build and view progress
docker build --progress=plain -t myapp:latest .
```

### Dockerfile Best Practices
```dockerfile
# 1. Use specific base image versions
FROM node:18.16.0-alpine3.17  # Not just 'node'

# 2. Use .dockerignore file
# .dockerignore content:
# node_modules
# npm-debug.log
# .git
# .env

# 3. Minimize layers - combine RUN commands
RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 4. Order instructions by change frequency
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./  # Changes less frequently
RUN npm install
COPY . .               # Changes more frequently

# 5. Use multi-stage builds
FROM node:18 AS builder
# Build steps...
FROM node:18-alpine
COPY --from=builder /app/dist ./dist

# 6. Don't run as root
RUN addgroup -g 1001 -S nodejs && \
    adduser -S nodejs -u 1001
USER nodejs

# 7. Use COPY instead of ADD
COPY package.json .  # Preferred
# ADD should only be used for tar extraction

# 8. Set environment variables
ENV NODE_ENV=production \
    PORT=3000

# 9. Use EXPOSE for documentation
EXPOSE 3000

# 10. Prefer CMD over RUN for execution
CMD ["node", "server.js"]  # Proper
# Not: RUN node server.js
```

---

## 4. Containers - Running Instances

### Why It Matters
- Containers are running instances of images
- Isolated execution environment
- Ephemeral by nature
- Foundation of microservices architecture

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Container | Running instance | Execute application |
| Container ID | Unique identifier | Reference container |
| Container Name | Human-readable name | Easier management |
| State | Running/Stopped/Paused | Lifecycle status |
| Port Mapping | Host to container | External access |

### Running Containers
```bash
# Run container
docker run nginx

# Run in detached mode (background)
docker run -d nginx

# Run with name
docker run -d --name my-nginx nginx

# Run with port mapping
docker run -d -p 8080:80 nginx

# Run with environment variables
docker run -d -e NODE_ENV=production -e PORT=3000 myapp

# Run with volume mount
docker run -d -v $(pwd)/data:/app/data myapp

# Run with restart policy
docker run -d --restart unless-stopped nginx

# Run interactive terminal
docker run -it ubuntu bash

# Run and remove after exit
docker run --rm -it ubuntu bash

# Run with resource limits
docker run -d --memory="512m" --cpus="1.0" myapp

# Run with network
docker run -d --network my-network myapp
```

### Managing Containers
```bash
# List running containers
docker ps

# List all containers (including stopped)
docker ps -a

# Stop container
docker stop container_name
docker stop container_id

# Start stopped container
docker start container_name

# Restart container
docker restart container_name

# Pause/Unpause container
docker pause container_name
docker unpause container_name

# Remove container
docker rm container_name
docker rm -f container_name  # Force remove running container

# Remove all stopped containers
docker container prune

# View container logs
docker logs container_name
docker logs -f container_name  # Follow logs
docker logs --tail 100 container_name  # Last 100 lines
docker logs --since 5m container_name  # Last 5 minutes

# Execute command in running container
docker exec container_name ls /app
docker exec -it container_name bash
docker exec -it container_name sh

# Inspect container
docker inspect container_name

# View container stats
docker stats
docker stats container_name

# View container processes
docker top container_name

# Copy files to/from container
docker cp file.txt container_name:/app/
docker cp container_name:/app/logs.txt ./
```

### Container Lifecycle
```
┌──────────┐
│  Created │
└────┬─────┘
     │ docker start
     ▼
┌──────────┐     docker pause
│  Running │◄────────────────┐
└────┬─────┘                 │
     │                       │
     │ docker stop      ┌────┴────┐
     ▼                  │  Paused │
┌──────────┐           └────┬────┘
│  Stopped │                │
└────┬─────┘           docker unpause
     │
     │ docker rm
     ▼
┌──────────┐
│  Removed │
└──────────┘
```

---

## 5. Docker Volumes - Persistent Storage

### Why It Matters
- Containers are ephemeral - data is lost when container is removed
- Volumes persist data outside container lifecycle
- Share data between containers
- Backup and migration

### Key Concepts

| Type | Description | Use Case |
|------|-------------|----------|
| Named Volume | Managed by Docker | Database data |
| Bind Mount | Host directory | Development |
| tmpfs | In-memory | Temporary data |

### Working with Volumes
```bash
# Create volume
docker volume create mydata

# List volumes
docker volume ls

# Inspect volume
docker volume inspect mydata

# Remove volume
docker volume rm mydata

# Remove unused volumes
docker volume prune

# Run container with named volume
docker run -d -v mydata:/app/data postgres

# Run with bind mount (development)
docker run -d -v $(pwd):/app -v /app/node_modules myapp

# Read-only volume
docker run -d -v mydata:/app/data:ro nginx

# Volume from another container
docker run -d --volumes-from container1 myapp
```

### Volume Examples

**Database with Persistent Storage:**
```bash
# Create volume
docker volume create postgres-data

# Run PostgreSQL with volume
docker run -d \
  --name my-postgres \
  -e POSTGRES_PASSWORD=secret \
  -v postgres-data:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres:15
```

**Development Environment:**
```bash
# Node.js development with hot reload
docker run -d \
  --name dev-app \
  -v $(pwd):/app \
  -v /app/node_modules \
  -p 3000:3000 \
  -e NODE_ENV=development \
  myapp

# Changes to local files reflect immediately in container
```

**Backup and Restore:**
```bash
# Backup volume
docker run --rm \
  -v postgres-data:/data \
  -v $(pwd):/backup \
  ubuntu \
  tar czf /backup/backup.tar.gz /data

# Restore volume
docker run --rm \
  -v postgres-data:/data \
  -v $(pwd):/backup \
  ubuntu \
  tar xzf /backup/backup.tar.gz -C /
```

---

## 6. Docker Networks - Container Communication

### Why It Matters
- Containers need to communicate
- Network isolation for security
- Service discovery between containers
- Expose services to external world

### Network Types

| Type | Description | Use Case |
|------|-------------|----------|
| bridge | Default network | Single host |
| host | Use host network | Performance |
| none | No networking | Isolation |
| overlay | Multi-host | Swarm/orchestration |
| custom bridge | User-defined | Service isolation |

### Working with Networks
```bash
# List networks
docker network ls

# Create network
docker network create my-network

# Inspect network
docker network inspect my-network

# Remove network
docker network rm my-network

# Remove unused networks
docker network prune

# Connect container to network
docker network connect my-network container_name

# Disconnect container from network
docker network disconnect my-network container_name

# Run container on specific network
docker run -d --network my-network --name web nginx
```

### Network Communication Examples

**Frontend and Backend:**
```bash
# Create network
docker network create app-network

# Run backend
docker run -d \
  --name api \
  --network app-network \
  -e DATABASE_URL=postgres://db:5432/myapp \
  myapi:latest

# Run database
docker run -d \
  --name db \
  --network app-network \
  -e POSTGRES_PASSWORD=secret \
  postgres:15

# Run frontend (can access backend at http://api:3000)
docker run -d \
  --name web \
  --network app-network \
  -p 80:80 \
  -e API_URL=http://api:3000 \
  myfrontend:latest
```

**Service Discovery:**
```bash
# Containers on same network can reach each other by name
docker network create my-net

docker run -d --name db --network my-net postgres
docker run -d --name api --network my-net \
  -e DB_HOST=db myapi  # 'db' resolves to database container
```

---

## 7. Docker Compose - Multi-Container Applications

### Why It Matters
- Define multi-container applications in single file
- Manage entire application stack
- Consistent development environments
- Simplify deployment process

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| docker-compose.yml | Configuration file | Define services |
| Service | Container definition | Application component |
| Network | Service communication | Isolation |
| Volume | Data persistence | Storage |
| Environment | Configuration | Settings |

### Basic docker-compose.yml
```yaml
version: '3.8'

services:
  # Web application
  web:
    build: .
    ports:
      - "3000:3000"
    environment:
      - NODE_ENV=development
      - DATABASE_URL=postgres://db:5432/myapp
    volumes:
      - .:/app
      - /app/node_modules
    depends_on:
      - db
      - redis
    networks:
      - app-network

  # Database
  db:
    image: postgres:15
    environment:
      - POSTGRES_DB=myapp
      - POSTGRES_USER=user
      - POSTGRES_PASSWORD=password
    volumes:
      - postgres-data:/var/lib/postgresql/data
    networks:
      - app-network

  # Cache
  redis:
    image: redis:7-alpine
    networks:
      - app-network

volumes:
  postgres-data:

networks:
  app-network:
    driver: bridge
```

### Complete Full-Stack Example
```yaml
version: '3.8'

services:
  # Frontend
  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    ports:
      - "80:80"
    environment:
      - API_URL=http://backend:3000
    depends_on:
      - backend
    networks:
      - app-network

  # Backend API
  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile
    ports:
      - "3000:3000"
    environment:
      - NODE_ENV=production
      - DATABASE_URL=postgres://postgres:password@db:5432/myapp
      - REDIS_URL=redis://redis:6379
      - JWT_SECRET=${JWT_SECRET}
    depends_on:
      - db
      - redis
    volumes:
      - ./backend/logs:/app/logs
    networks:
      - app-network
    restart: unless-stopped

  # PostgreSQL Database
  db:
    image: postgres:15-alpine
    environment:
      - POSTGRES_DB=myapp
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=password
    volumes:
      - postgres-data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - app-network
    restart: unless-stopped

  # Redis Cache
  redis:
    image: redis:7-alpine
    command: redis-server --appendonly yes
    volumes:
      - redis-data:/data
    networks:
      - app-network
    restart: unless-stopped

  # Nginx Reverse Proxy
  nginx:
    image: nginx:alpine
    ports:
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf:ro
      - ./certs:/etc/nginx/certs:ro
    depends_on:
      - frontend
      - backend
    networks:
      - app-network
    restart: unless-stopped

volumes:
  postgres-data:
  redis-data:

networks:
  app-network:
    driver: bridge
```

### Docker Compose Commands
```bash
# Start all services
docker-compose up

# Start in detached mode
docker-compose up -d

# Start specific service
docker-compose up web

# Build and start
docker-compose up --build

# Stop services
docker-compose stop

# Stop and remove containers
docker-compose down

# Stop and remove containers + volumes
docker-compose down -v

# View logs
docker-compose logs
docker-compose logs -f web
docker-compose logs --tail 100

# List services
docker-compose ps

# Execute command in service
docker-compose exec web bash
docker-compose exec db psql -U postgres

# View service configuration
docker-compose config

# Scale services
docker-compose up -d --scale worker=3

# Restart service
docker-compose restart web

# Pull latest images
docker-compose pull
```

---

## 8. Docker Hub & Container Registries

### Why It Matters
- Share images across teams
- Version control for deployments
- CI/CD integration
- Public and private repositories

### Key Concepts

| Concept | Description | Use Case |
|---------|-------------|----------|
| Registry | Image storage | Docker Hub, ECR, GCR |
| Repository | Collection of images | myapp |
| Tag | Version identifier | latest, 1.0, prod |
| Push | Upload image | Deploy |
| Pull | Download image | Use image |

### Working with Docker Hub
```bash
# Login to Docker Hub
docker login
docker login -u username -p password

# Tag image for registry
docker tag myapp:latest username/myapp:latest
docker tag myapp:latest username/myapp:1.0.0

# Push to Docker Hub
docker push username/myapp:latest
docker push username/myapp:1.0.0

# Pull from Docker Hub
docker pull username/myapp:latest

# Search Docker Hub
docker search postgres

# Logout
docker logout
```

### CI/CD Integration Example
```yaml
# .github/workflows/docker.yml
name: Docker Build and Push

on:
  push:
    branches: [ main ]
    tags: [ 'v*' ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Login to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Extract metadata
        id: meta
        uses: docker/metadata-action@v4
        with:
          images: username/myapp

      - name: Build and push
        uses: docker/build-push-action@v4
        with:
          context: .
          push: true
          tags: ${{ steps.meta.outputs.tags }}
          labels: ${{ steps.meta.outputs.labels }}
```

---

## Quick Reference Card

### Essential Commands
```bash
# Images
docker pull image:tag
docker build -t name:tag .
docker images
docker rmi image:tag

# Containers
docker run -d -p 8080:80 --name web nginx
docker ps
docker ps -a
docker stop container
docker start container
docker rm container
docker logs -f container
docker exec -it container bash

# Volumes
docker volume create vol
docker run -v vol:/data image
docker volume ls
docker volume rm vol

# Networks
docker network create net
docker run --network net image
docker network ls
docker network rm net

# Compose
docker-compose up -d
docker-compose down
docker-compose logs -f
docker-compose exec service bash

# Cleanup
docker system prune
docker image prune
docker volume prune
docker network prune
```

### Common Patterns
```bash
# Development setup
docker run -d \
  -v $(pwd):/app \
  -v /app/node_modules \
  -p 3000:3000 \
  -e NODE_ENV=development \
  myapp

# Database
docker run -d \
  -v data:/var/lib/postgresql/data \
  -e POSTGRES_PASSWORD=secret \
  -p 5432:5432 \
  postgres:15

# Debugging
docker logs -f container
docker exec -it container sh
docker stats
docker inspect container
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand difference between containers and VMs
- [ ] Pull and manage Docker images
- [ ] Write Dockerfiles to build custom images
- [ ] Use multi-stage builds for optimization
- [ ] Run and manage containers
- [ ] Use volumes for persistent storage
- [ ] Configure container networks
- [ ] Write docker-compose.yml files
- [ ] Manage multi-container applications with Compose
- [ ] Push and pull images from registries
- [ ] Debug running containers
- [ ] Implement Docker best practices
- [ ] Use .dockerignore effectively
- [ ] Optimize image sizes

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 24: Kubernetes](../24-kubernetes/) - Orchestrate containers at scale
- Practice building and deploying full-stack applications
- Learn Docker security best practices
- Explore Docker Swarm for orchestration
- Study container monitoring and logging strategies
