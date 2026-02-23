# Docker Containers

## What is a Container?

A container is a **running instance** of a Docker image. While an image is like a blueprint, a container is the actual running application.

**Think of it like this**:
- **Image** = Recipe for a cake
- **Container** = The actual cake you baked from that recipe

You can create multiple containers from the same image, and each container runs independently.

---

## Container Lifecycle

Containers go through different states:

```
┌──────────┐  docker run   ┌──────────┐
│ Created  │ ────────────→ │ Running  │
└──────────┘               └────┬─────┘
                                │
                         docker stop
                                │
                           ┌────▼─────┐
                           │ Stopped  │
                           └────┬─────┘
                                │
                          docker rm
                                │
                           ┌────▼─────┐
                           │ Removed  │
                           └──────────┘
```

---

## Running Containers

### Basic Run Command

```bash
# Run a container (downloads image if needed)
docker run nginx

# Run in background (detached mode) - most common
docker run -d nginx

# Run with a name (easier to manage)
docker run -d --name my-web-server nginx
```

### Common Run Options

| Option | Purpose | Example |
|--------|---------|---------|
| `-d` | Run in background | `docker run -d nginx` |
| `--name` | Give container a name | `docker run --name web nginx` |
| `-p` | Map ports | `docker run -p 8080:80 nginx` |
| `-e` | Set environment variable | `docker run -e NODE_ENV=prod app` |
| `-v` | Mount a volume | `docker run -v data:/app/data app` |
| `--rm` | Remove when stopped | `docker run --rm nginx` |

### Port Mapping

To access an application inside a container from your computer, you need to map ports:

```bash
# Map host port 8080 to container port 80
docker run -d -p 8080:80 nginx
```

```
Your Computer                Container
┌──────────────┐            ┌──────────────┐
│              │            │              │
│  localhost   │ ──────────→│   nginx      │
│    :8080     │   mapped   │   :80        │
│              │            │              │
└──────────────┘            └──────────────┘
```

Now visit `http://localhost:8080` to see the nginx welcome page.

---

## Managing Containers

### Listing Containers

```bash
# Show running containers
docker ps

# Show ALL containers (including stopped)
docker ps -a
```

Output:
```
CONTAINER ID   IMAGE   COMMAND   CREATED   STATUS    PORTS                  NAMES
a1b2c3d4e5f6   nginx   ...       1 min     Up 1 min  0.0.0.0:8080->80/tcp   web
```

### Starting and Stopping

```bash
# Stop a running container
docker stop my-web-server

# Start a stopped container
docker start my-web-server

# Restart a container
docker restart my-web-server
```

### Removing Containers

```bash
# Remove a stopped container
docker rm my-web-server

# Force remove a running container
docker rm -f my-web-server

# Remove all stopped containers
docker container prune
```

---

## Viewing Container Information

### Container Logs

```bash
# View logs
docker logs my-web-server

# Follow logs in real-time (like tail -f)
docker logs -f my-web-server

# Show last 50 lines
docker logs --tail 50 my-web-server
```

### Container Details

```bash
# Detailed information about a container
docker inspect my-web-server

# Check resource usage
docker stats my-web-server
```

---

## Executing Commands in Containers

### Running Commands

```bash
# Run a command in a running container
docker exec my-web-server ls -la

# Open an interactive shell
docker exec -it my-web-server bash

# Or use sh for Alpine-based images
docker exec -it my-web-server sh
```

**Tip**: The `-it` flags give you an interactive terminal.

### Copying Files

```bash
# Copy from container to host
docker cp my-web-server:/etc/nginx/nginx.conf ./nginx.conf

# Copy from host to container
docker cp ./config.json my-web-server:/app/config.json
```

---

## Docker Volumes - Persistent Data

### The Problem: Data Loss

By default, when a container is **deleted**, all data inside it is **lost forever**.

```bash
# Create a container and add some data
docker run -d --name my-db postgres
# ... container stores data ...

# Remove the container
docker rm -f my-db
# ALL DATA IS GONE! 😱
```

**This is a problem for**:
- Databases (you'd lose all your data!)
- User uploads
- Log files
- Any data that needs to survive restarts

### The Solution: Volumes

A **volume** is storage that exists **outside the container**. Data in volumes survives even when containers are deleted.

**Think of it like this**: A container is like a disposable cup. A volume is like a reusable water bottle - you can attach it to different cups, and the contents survive even when the cup is thrown away.

```
WITHOUT Volume:                 WITH Volume:
┌─────────────┐                ┌─────────────┐
│  Container  │                │  Container  │
│  ┌───────┐  │                │      │      │
│  │ Data  │  │                │      │      │
│  └───────┘  │                │      ▼      │
└─────────────┘                └──────┼──────┘
       │                              │
  Container dies                      │
       │                       ┌──────▼──────┐
       ▼                       │   Volume    │
   Data LOST!                  │   (Data     │
                               │   survives) │
                               └─────────────┘
```

### Types of Volumes

| Type | What It Is | When to Use |
|------|------------|-------------|
| **Named Volume** | Docker-managed storage | Databases, production data |
| **Bind Mount** | Maps a host folder | Development (edit files on host) |
| **tmpfs** | Temporary memory storage | Sensitive data that shouldn't persist |

### Using Named Volumes

Named volumes are managed by Docker and are the **recommended** way to persist data.

```bash
# Create a volume
docker volume create my-data

# Run container with volume
docker run -d \
  --name my-db \
  -v my-data:/var/lib/postgresql/data \
  postgres

# The data persists even if container is removed!
docker rm -f my-db
# Volume "my-data" still exists with all your data

# Start a new container with the same volume
docker run -d \
  --name my-db-new \
  -v my-data:/var/lib/postgresql/data \
  postgres
# All your data is still there! 🎉
```

### Volume Commands

```bash
# List all volumes
docker volume ls

# Inspect a volume
docker volume inspect my-data

# Remove a volume (be careful!)
docker volume rm my-data

# Remove all unused volumes
docker volume prune
```

### Using Bind Mounts

Bind mounts link a folder on your computer directly to the container. Great for development!

```bash
# Mount current directory to /app in container
docker run -d \
  -v $(pwd):/app \
  -p 3000:3000 \
  node:18

# Changes you make to files are instantly visible in the container
```

```
Your Computer                 Container
┌─────────────────┐          ┌─────────────────┐
│ /home/user/app  │ ←──────→ │ /app            │
│ ├── index.js    │  linked  │ ├── index.js    │
│ └── package.json│          │ └── package.json│
└─────────────────┘          └─────────────────┘
```

### Practical Examples

**Database with Persistent Data**:
```bash
docker run -d \
  --name postgres \
  -e POSTGRES_PASSWORD=secret \
  -v pgdata:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres:15
```

**Development with Live Code Reloading**:
```bash
docker run -d \
  --name dev-server \
  -v $(pwd):/app \
  -p 3000:3000 \
  node:18 npm run dev
```

**Read-Only Configuration Mount**:
```bash
docker run -d \
  --name nginx \
  -v $(pwd)/nginx.conf:/etc/nginx/nginx.conf:ro \
  -p 80:80 \
  nginx
```

---

## Environment Variables

Pass configuration to containers using environment variables:

```bash
# Single variable
docker run -e NODE_ENV=production my-app

# Multiple variables
docker run \
  -e NODE_ENV=production \
  -e DB_HOST=localhost \
  -e DB_PORT=5432 \
  my-app

# From a file
docker run --env-file .env my-app
```

---

## Container Networks

### How Containers Communicate

By default, containers can communicate with each other using Docker networks.

```bash
# Create a network
docker network create my-network

# Run containers on the same network
docker run -d --name database --network my-network postgres
docker run -d --name api --network my-network my-api

# Containers can reach each other by name!
# From "api" container: postgres://database:5432/mydb
```

### Network Commands

```bash
# List networks
docker network ls

# Inspect a network
docker network inspect my-network

# Remove a network
docker network rm my-network
```

---

## Complete Example

Here's a typical development setup:

```bash
# 1. Create a network
docker network create myapp-network

# 2. Run database with volume
docker run -d \
  --name db \
  --network myapp-network \
  -e POSTGRES_PASSWORD=secret \
  -v dbdata:/var/lib/postgresql/data \
  postgres:15

# 3. Run application
docker run -d \
  --name api \
  --network myapp-network \
  -e DATABASE_URL=postgres://postgres:secret@db:5432/postgres \
  -p 3000:3000 \
  my-api

# 4. View logs
docker logs -f api

# 5. Cleanup when done
docker stop api db
docker rm api db
# Data is preserved in dbdata volume!
```

---

## Essential Commands Summary

| Command | Purpose |
|---------|---------|
| `docker run -d IMAGE` | Run container in background |
| `docker ps` | List running containers |
| `docker ps -a` | List all containers |
| `docker stop NAME` | Stop a container |
| `docker start NAME` | Start a stopped container |
| `docker rm NAME` | Remove a container |
| `docker logs NAME` | View container logs |
| `docker exec -it NAME sh` | Open shell in container |
| `docker volume ls` | List volumes |
| `docker volume create NAME` | Create a volume |
| `docker network create NAME` | Create a network |

---

## Summary

| Concept | Key Points |
|---------|------------|
| **Container** | Running instance of an image |
| **Ports** | Use `-p host:container` to access applications |
| **Volumes** | Persist data beyond container lifecycle |
| **Named Volumes** | Docker-managed, best for databases |
| **Bind Mounts** | Link host folders, best for development |
| **Networks** | Allow containers to communicate |

## Next Topic

Continue to [Docker CLI](./05-docker-cli.md) for a quick reference of all Docker commands.
