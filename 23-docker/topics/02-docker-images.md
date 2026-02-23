# Docker Images

## What is a Docker Image?

A Docker image is a **blueprint** or **template** for creating containers. It contains everything needed to run an application: the code, runtime, libraries, and settings.

**Think of it like this**:
- An **image** is like a class definition in programming
- A **container** is like an object instance created from that class

You can create many containers from the same image, just like you can create many objects from the same class.

```
┌─────────────────────┐
│   Docker Image      │  ← Blueprint (read-only)
│   (nginx:latest)    │
└──────────┬──────────┘
           │ docker run
           ├──────────────────┐
           ▼                  ▼
┌─────────────────┐  ┌─────────────────┐
│   Container 1   │  │   Container 2   │  ← Running instances
│   (web-server)  │  │   (web-backup)  │
└─────────────────┘  └─────────────────┘
```

---

## Key Characteristics

| Property | Description |
|----------|-------------|
| **Read-only** | Images cannot be changed once created |
| **Layered** | Built from multiple layers stacked together |
| **Reusable** | Same image can create many containers |
| **Shareable** | Can be uploaded to registries like Docker Hub |

---

## Image Layers

Docker images are built in **layers**. Each instruction in a Dockerfile creates a new layer.

```
┌─────────────────────────────┐
│  Layer 4: Your app code     │  ← Changes frequently
├─────────────────────────────┤
│  Layer 3: npm install       │  ← Dependencies
├─────────────────────────────┤
│  Layer 2: Copy package.json │
├─────────────────────────────┤
│  Layer 1: node:18 base      │  ← Base image (rarely changes)
└─────────────────────────────┘
```

**Why layers matter**:
- **Caching**: If a layer hasn't changed, Docker reuses it (faster builds)
- **Sharing**: Common layers are shared between images (saves disk space)
- **Efficiency**: Only changed layers need to be downloaded/uploaded

---

## Working with Images

### Finding Images

Most images come from **Docker Hub** (hub.docker.com), the default public registry.

```bash
# Search for images on Docker Hub
docker search nginx
```

**Types of images**:
- **Official images**: Maintained by Docker or vendors (e.g., `nginx`, `postgres`)
- **User images**: Created by community members (e.g., `username/myapp`)

### Downloading Images

```bash
# Download the latest version
docker pull nginx

# Download a specific version
docker pull nginx:1.25

# Download a lightweight Alpine version
docker pull nginx:alpine
```

### Listing Images

```bash
# Show all downloaded images
docker images
```

Output:
```
REPOSITORY    TAG       IMAGE ID       CREATED        SIZE
nginx         latest    2b7d6430f78d   2 weeks ago    187MB
node          18        74e9f9e5f3e8   1 month ago    993MB
```

### Removing Images

```bash
# Remove a specific image
docker rmi nginx:latest

# Remove all unused images
docker image prune
```

---

## Image Naming Convention

Docker images follow this naming pattern:

```
[registry/]repository[:tag]
```

| Example | Description |
|---------|-------------|
| `nginx` | Official image, latest tag (default) |
| `nginx:1.25` | Official image, specific version |
| `nginx:alpine` | Official image, Alpine Linux variant |
| `myusername/myapp:v1` | User image with version tag |
| `gcr.io/project/app:latest` | Image from Google Container Registry |

### Common Tags

| Tag | Meaning |
|-----|---------|
| `latest` | Most recent version (default, but not recommended for production) |
| `1.25`, `1.25.3` | Specific version numbers |
| `alpine` | Smaller image based on Alpine Linux |
| `slim` | Reduced size, fewer tools |

**Best Practice**: Always use specific version tags in production (not `latest`).

---

## Building Your Own Images

Images are built from a **Dockerfile** (covered in the next topic).

```bash
# Build an image from Dockerfile in current directory
docker build -t myapp:v1 .

# Build with a specific Dockerfile
docker build -f Dockerfile.prod -t myapp:prod .
```

### Tagging Images

Tags help you version and organize your images.

```bash
# Add a new tag to an existing image
docker tag myapp:v1 myapp:latest
docker tag myapp:v1 myusername/myapp:v1
```

---

## Image Best Practices

### 1. Use Official Images as Base
```dockerfile
# Good: Official, maintained image
FROM node:18-alpine

# Avoid: Unknown or unmaintained images
FROM random-user/node-custom
```

### 2. Use Specific Versions
```dockerfile
# Good: Predictable
FROM node:18.17.0-alpine

# Risky: Could change unexpectedly
FROM node:latest
```

### 3. Choose Smaller Base Images
```dockerfile
# Large (~900MB)
FROM node:18

# Medium (~170MB)
FROM node:18-alpine

# Smaller images = faster downloads, less storage, smaller attack surface
```

### 4. Keep Images Small
- Only install what you need
- Remove unnecessary files
- Use multi-stage builds for compiled languages

---

## Essential Commands Summary

| Command | Purpose |
|---------|---------|
| `docker pull IMAGE` | Download an image |
| `docker images` | List downloaded images |
| `docker rmi IMAGE` | Remove an image |
| `docker build -t NAME .` | Build image from Dockerfile |
| `docker tag SOURCE TARGET` | Add a tag to an image |
| `docker image prune` | Remove unused images |

---

## Summary

| Concept | Key Points |
|---------|------------|
| **Image** | Read-only template for creating containers |
| **Layers** | Images are built from cached, reusable layers |
| **Tags** | Version identifiers (use specific versions in production) |
| **Registry** | Storage for images (Docker Hub is default) |

## Next Topic

Continue to [Dockerfile](./03-dockerfile.md) to learn how to create your own images.
