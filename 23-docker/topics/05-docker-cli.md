# Docker CLI Quick Reference

This is a concise reference guide for the most commonly used Docker commands.

---

## Getting Help

```bash
docker --help              # General help
docker COMMAND --help      # Help for specific command
docker version             # Show Docker version
docker info                # System-wide information
```

---

## Image Commands

| Command | Description |
|---------|-------------|
| `docker pull IMAGE` | Download an image |
| `docker images` | List downloaded images |
| `docker build -t NAME .` | Build image from Dockerfile |
| `docker rmi IMAGE` | Remove an image |
| `docker tag SOURCE TARGET` | Create a tag |
| `docker push IMAGE` | Upload to registry |
| `docker image prune` | Remove unused images |

### Examples

```bash
# Pull an image
docker pull nginx:alpine

# Build an image
docker build -t myapp:v1 .

# List images
docker images

# Remove an image
docker rmi nginx:alpine
```

---

## Container Commands

| Command | Description |
|---------|-------------|
| `docker run IMAGE` | Create and start container |
| `docker ps` | List running containers |
| `docker ps -a` | List all containers |
| `docker stop NAME` | Stop a container |
| `docker start NAME` | Start stopped container |
| `docker restart NAME` | Restart a container |
| `docker rm NAME` | Remove a container |
| `docker logs NAME` | View container logs |
| `docker exec NAME CMD` | Execute command in container |

### Examples

```bash
# Run container in background with port mapping
docker run -d -p 8080:80 --name web nginx

# Run with volume
docker run -d -v mydata:/app/data myapp

# View logs
docker logs -f web

# Execute shell in container
docker exec -it web sh

# Stop and remove
docker stop web && docker rm web
```

---

## Common Run Options

```bash
docker run [OPTIONS] IMAGE [COMMAND]
```

| Option | Description | Example |
|--------|-------------|---------|
| `-d` | Run in background | `-d` |
| `--name` | Assign a name | `--name web` |
| `-p` | Map ports | `-p 8080:80` |
| `-e` | Environment variable | `-e NODE_ENV=prod` |
| `-v` | Mount volume | `-v data:/app/data` |
| `--rm` | Remove after exit | `--rm` |
| `--network` | Connect to network | `--network mynet` |
| `-it` | Interactive terminal | `-it` |

### Complete Example

```bash
docker run -d \
  --name api \
  -p 3000:3000 \
  -e NODE_ENV=production \
  -v appdata:/app/data \
  --network app-network \
  myapp:v1
```

---

## Volume Commands

| Command | Description |
|---------|-------------|
| `docker volume create NAME` | Create a volume |
| `docker volume ls` | List volumes |
| `docker volume inspect NAME` | View volume details |
| `docker volume rm NAME` | Remove a volume |
| `docker volume prune` | Remove unused volumes |

### Examples

```bash
# Create and use a volume
docker volume create pgdata
docker run -d -v pgdata:/var/lib/postgresql/data postgres

# List volumes
docker volume ls

# Remove volume
docker volume rm pgdata
```

---

## Network Commands

| Command | Description |
|---------|-------------|
| `docker network create NAME` | Create a network |
| `docker network ls` | List networks |
| `docker network inspect NAME` | View network details |
| `docker network connect NET CONTAINER` | Connect container |
| `docker network rm NAME` | Remove a network |

### Examples

```bash
# Create network and run containers
docker network create myapp-net
docker run -d --name db --network myapp-net postgres
docker run -d --name api --network myapp-net myapi

# Containers can communicate by name
# api can reach db at: postgres://db:5432
```

---

## Cleanup Commands

| Command | Description |
|---------|-------------|
| `docker container prune` | Remove stopped containers |
| `docker image prune` | Remove unused images |
| `docker volume prune` | Remove unused volumes |
| `docker network prune` | Remove unused networks |
| `docker system prune` | Remove all unused resources |
| `docker system prune -a` | Remove everything unused |

---

## Inspection Commands

```bash
# Container information
docker inspect CONTAINER
docker logs CONTAINER
docker top CONTAINER          # Running processes
docker stats CONTAINER        # Resource usage

# Image information
docker inspect IMAGE
docker history IMAGE          # Layer history
```

---

## Common Workflows

### Development Setup

```bash
# 1. Build image
docker build -t myapp:dev .

# 2. Run with live code mounting
docker run -d \
  --name dev \
  -p 3000:3000 \
  -v $(pwd):/app \
  myapp:dev

# 3. View logs
docker logs -f dev

# 4. Enter container for debugging
docker exec -it dev sh
```

### Database Setup

```bash
# PostgreSQL with persistent data
docker run -d \
  --name postgres \
  -e POSTGRES_PASSWORD=secret \
  -v pgdata:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres:15
```

### Multi-Container Application

```bash
# Create network
docker network create app-net

# Start database
docker run -d \
  --name db \
  --network app-net \
  -v dbdata:/var/lib/postgresql/data \
  -e POSTGRES_PASSWORD=secret \
  postgres:15

# Start application
docker run -d \
  --name api \
  --network app-net \
  -e DATABASE_URL=postgres://postgres:secret@db:5432/postgres \
  -p 3000:3000 \
  myapi:latest
```

---

## Quick Reference Card

```
IMAGE OPERATIONS:
  pull      Download image
  build     Create image from Dockerfile
  images    List images
  rmi       Remove image

CONTAINER OPERATIONS:
  run       Create and start container
  ps        List containers
  stop      Stop container
  start     Start stopped container
  rm        Remove container
  logs      View logs
  exec      Run command in container

VOLUME OPERATIONS:
  volume create    Create volume
  volume ls        List volumes
  volume rm        Remove volume

NETWORK OPERATIONS:
  network create   Create network
  network ls       List networks
  network rm       Remove network

CLEANUP:
  system prune     Remove all unused resources
```

---

## Summary

The most frequently used commands:

```bash
docker run -d -p 8080:80 --name web nginx     # Run container
docker ps                                       # List running
docker logs -f web                              # View logs
docker exec -it web sh                          # Shell access
docker stop web && docker rm web                # Cleanup
```

## Next Topic

Continue to [Docker Registry](./06-docker-registry.md) to learn about sharing images.
