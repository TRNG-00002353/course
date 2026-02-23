# Docker Introduction

## What is Docker?

Docker is a platform that lets you package your application along with everything it needs to run (code, libraries, settings) into a single unit called a **container**. This container can run on any computer that has Docker installed.

**Think of it like this**: Imagine you're moving to a new house. Instead of packing items in separate boxes and hoping you remember how to set everything up, you could move your entire room - furniture arranged, decorations hung, everything exactly as it was. That's what Docker does for applications.

### Why Docker Matters

**The Problem Docker Solves**:
- "It works on my machine!" - A developer's code works on their laptop but fails on the server
- Setting up development environments takes hours or days
- Different team members have different versions of tools installed

**How Docker Helps**:
- Same environment everywhere - development, testing, production
- New team members can start working in minutes, not days
- No more "dependency hell" - everything the app needs is packaged together

---

## Key Benefits

| Benefit | What It Means |
|---------|---------------|
| **Consistent** | Works the same on every machine |
| **Portable** | Run anywhere Docker is installed |
| **Lightweight** | Uses less resources than virtual machines |
| **Fast** | Containers start in seconds |
| **Isolated** | Apps don't interfere with each other |

---

## Docker vs Virtual Machines

Both Docker and VMs help run applications in isolation, but they work differently:

```
CONTAINERS                          VIRTUAL MACHINES
┌─────────────────────┐            ┌─────────────────────┐
│   App A │  App B    │            │   App A │  App B    │
├─────────┴───────────┤            ├─────────┴───────────┤
│     Docker Engine   │            │   Full Guest OS     │
├─────────────────────┤            │   (Windows/Linux)   │
│     Host OS         │            ├─────────────────────┤
├─────────────────────┤            │   Full Guest OS     │
│     Hardware        │            ├─────────────────────┤
└─────────────────────┘            │     Hypervisor      │
                                   ├─────────────────────┤
                                   │     Host OS         │
                                   └─────────────────────┘
```

| Aspect | Containers | Virtual Machines |
|--------|-----------|------------------|
| **Startup** | Seconds | Minutes |
| **Size** | Megabytes | Gigabytes |
| **Resource Usage** | Low | High |
| **Isolation** | Process-level | Complete OS-level |

**When to use Containers**: Web apps, microservices, development environments

**When to use VMs**: Running different operating systems, legacy applications, stronger security isolation

---

## Core Docker Concepts

### 1. Image
A **read-only template** that contains instructions for creating a container. Think of it as a recipe or blueprint.

### 2. Container
A **running instance** of an image. Think of it as the actual dish made from the recipe.

### 3. Dockerfile
A **text file** with instructions to build an image. Think of it as writing down your recipe.

### 4. Registry
A **storage location** for Docker images (like Docker Hub). Think of it as a cookbook library where you can share and download recipes.

```
Dockerfile  →  docker build  →  Image  →  docker run  →  Container
(recipe)                      (blueprint)               (running app)
```

---

## Docker Architecture

Docker uses a client-server architecture:

```
┌─────────────────────────────────────────┐
│         Docker Client (CLI)             │
│   You type: docker run, docker build    │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         Docker Daemon (Engine)          │
│   Builds images, runs containers        │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         Containers Running              │
│   Your applications in isolation        │
└─────────────────────────────────────────┘
```

- **Docker Client**: The command-line tool you interact with
- **Docker Daemon**: Background service that does the actual work
- **Registry**: Where images are stored (Docker Hub is the default)

---

## Common Use Cases

### 1. Development Environments
Set up a complete development environment that any team member can use instantly.

### 2. Microservices
Run each service (user service, payment service, etc.) in its own container.

### 3. Testing
Run tests in a clean, consistent environment every time.

### 4. Deployment
Ship your application as a container - it will run the same in production as it did in development.

---

## Getting Started

### Install Docker

1. **Windows/Mac**: Download [Docker Desktop](https://www.docker.com/products/docker-desktop)
2. **Linux**: Follow the [official installation guide](https://docs.docker.com/engine/install/)

### Verify Installation

```bash
# Check Docker is installed
docker --version

# Check Docker is running
docker info
```

### Run Your First Container

```bash
# Download and run a simple "Hello World" container
docker run hello-world
```

If you see a "Hello from Docker!" message, you're ready to go!

---

## Summary

| Concept | Description |
|---------|-------------|
| **Docker** | Platform for packaging and running applications in containers |
| **Container** | Lightweight, isolated environment running your application |
| **Image** | Blueprint/template for creating containers |
| **Benefits** | Consistent, portable, fast, isolated |

## Next Topic

Continue to [Docker Images](./02-docker-images.md) to learn how to work with Docker images.
