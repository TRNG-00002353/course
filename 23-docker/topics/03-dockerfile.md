# Dockerfile

## What is a Dockerfile?

A Dockerfile is a **text file containing instructions** that tell Docker how to build an image. Think of it as a recipe that Docker follows step-by-step.

**Simple Example**:
```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY . .
RUN npm install
CMD ["node", "index.js"]
```

This tells Docker:
1. Start with Node.js 18 (Alpine Linux version)
2. Create and use `/app` as the working directory
3. Copy all files from your project into the container
4. Install npm dependencies
5. Run `node index.js` when the container starts

---

## Basic Dockerfile Structure

```dockerfile
# Comments start with #
INSTRUCTION arguments
```

Each line is an instruction that creates a **layer** in the image.

---

## Essential Instructions

### FROM - Starting Point

Every Dockerfile **must start with FROM**. It specifies the base image to build upon.

```dockerfile
# Use official Node.js image
FROM node:18-alpine

# Use official Python image
FROM python:3.11-slim

# Use minimal Linux image
FROM alpine:latest
```

**Tip**: Use `-alpine` or `-slim` variants for smaller images.

---

### WORKDIR - Set Working Directory

Sets the directory where commands will run inside the container.

```dockerfile
WORKDIR /app
```

**Why use it?**
- Keeps your container organized
- Avoids typing full paths repeatedly
- Creates the directory if it doesn't exist

---

### COPY - Copy Files

Copies files from your computer into the image.

```dockerfile
# Copy single file
COPY package.json .

# Copy multiple files
COPY package.json package-lock.json ./

# Copy entire directory
COPY . .
```

The `.` at the end means "current directory" (which is WORKDIR).

---

### RUN - Execute Commands

Runs commands during the **build process** (not when container runs).

```dockerfile
# Install dependencies
RUN npm install

# Install system packages
RUN apt-get update && apt-get install -y curl

# Create a directory
RUN mkdir -p /app/logs
```

**Best Practice**: Combine related commands with `&&` to reduce layers:
```dockerfile
# Good: One layer
RUN apt-get update && apt-get install -y curl && apt-get clean

# Avoid: Three layers
RUN apt-get update
RUN apt-get install -y curl
RUN apt-get clean
```

---

### CMD - Default Command

Specifies the command to run when the container **starts**.

```dockerfile
# Run node application
CMD ["node", "index.js"]

# Run Python script
CMD ["python", "app.py"]

# Run shell command
CMD ["npm", "start"]
```

**Important**:
- There can only be ONE CMD instruction (last one wins)
- Can be overridden when running the container

---

### EXPOSE - Document Ports

Tells Docker which port the application uses. This is **documentation only** - it doesn't actually publish the port.

```dockerfile
# Web application on port 3000
EXPOSE 3000

# Multiple ports
EXPOSE 80 443
```

You still need `-p` when running: `docker run -p 8080:3000 myapp`

---

### ENV - Environment Variables

Sets environment variables available inside the container.

```dockerfile
# Set single variable
ENV NODE_ENV=production

# Set multiple variables
ENV NODE_ENV=production \
    PORT=3000 \
    LOG_LEVEL=info
```

---

## Complete Examples

### Node.js Application

```dockerfile
# Start with Node.js base image
FROM node:18-alpine

# Set working directory
WORKDIR /app

# Copy package files first (for better caching)
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy application code
COPY . .

# Document the port
EXPOSE 3000

# Start the application
CMD ["node", "index.js"]
```

### Python Application

```dockerfile
# Start with Python base image
FROM python:3.11-slim

# Set working directory
WORKDIR /app

# Copy requirements first (for better caching)
COPY requirements.txt .

# Install dependencies
RUN pip install -r requirements.txt

# Copy application code
COPY . .

# Document the port
EXPOSE 8000

# Start the application
CMD ["python", "app.py"]
```

### Static Website with Nginx

```dockerfile
# Use Nginx to serve static files
FROM nginx:alpine

# Copy website files to Nginx's default directory
COPY ./html /usr/share/nginx/html

# Nginx already exposes port 80 and has a CMD
```

---

## Building and Running

### Build the Image

```bash
# Build from Dockerfile in current directory
docker build -t myapp:v1 .

# The -t flag sets the name:tag
# The . is the build context (current directory)
```

### Run a Container

```bash
# Run the container
docker run -p 3000:3000 myapp:v1

# Run in background
docker run -d -p 3000:3000 myapp:v1
```

---

## The .dockerignore File

Like `.gitignore`, this file tells Docker which files to **exclude** from the build.

```
# .dockerignore
node_modules
npm-debug.log
.git
.env
*.md
```

**Why use it?**
- Faster builds (less files to copy)
- Smaller images
- Prevents copying sensitive files (like `.env`)

---

## Best Practices for Beginners

### 1. Order Matters for Caching

Put things that **change less often at the top**:

```dockerfile
FROM node:18-alpine
WORKDIR /app

# Dependencies change occasionally
COPY package*.json ./
RUN npm install

# Code changes frequently - put last
COPY . .

CMD ["node", "index.js"]
```

If you only change your code, Docker reuses the cached npm install layer.

### 2. Use Specific Base Image Versions

```dockerfile
# Good: Predictable
FROM node:18.17.0-alpine

# Risky: Could change unexpectedly
FROM node:latest
```

### 3. Don't Run as Root (Security)

```dockerfile
FROM node:18-alpine
WORKDIR /app

# Create a non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY . .
RUN npm install

# Switch to non-root user
USER appuser

CMD ["node", "index.js"]
```

### 4. Keep Images Small

- Use alpine/slim base images
- Remove unnecessary files
- Combine RUN commands

---

## Common Dockerfile Instructions Summary

| Instruction | Purpose | Example |
|-------------|---------|---------|
| `FROM` | Base image | `FROM node:18-alpine` |
| `WORKDIR` | Set working directory | `WORKDIR /app` |
| `COPY` | Copy files into image | `COPY . .` |
| `RUN` | Execute build commands | `RUN npm install` |
| `CMD` | Default run command | `CMD ["node", "app.js"]` |
| `EXPOSE` | Document port | `EXPOSE 3000` |
| `ENV` | Set environment variable | `ENV NODE_ENV=production` |
| `USER` | Set user to run as | `USER appuser` |

---

## Advanced: Multi-Stage Builds

Multi-stage builds help create smaller production images by separating the build and runtime environments.

```dockerfile
# Stage 1: Build
FROM node:18 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Production (smaller image)
FROM node:18-alpine
WORKDIR /app
COPY --from=builder /app/dist ./dist
COPY --from=builder /app/node_modules ./node_modules
CMD ["node", "dist/index.js"]
```

**Why use multi-stage?**
- Build tools stay in the builder stage
- Production image only contains what's needed to run
- Results in much smaller images

---

## Summary

| Concept | Key Points |
|---------|------------|
| **Dockerfile** | Text file with instructions to build an image |
| **FROM** | Required first instruction, sets base image |
| **COPY/RUN** | Copy files and execute commands during build |
| **CMD** | Command to run when container starts |
| **Layers** | Each instruction creates a cached layer |
| **Best Practice** | Order by change frequency, use specific versions |

## Next Topic

Continue to [Docker Containers](./04-docker-containers.md) to learn how to run and manage containers.
