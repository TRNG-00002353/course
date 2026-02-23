# Docker Registry

## What is a Docker Registry?

A Docker registry is a **storage and distribution system** for Docker images. It's like GitHub, but for Docker images instead of code.

**Think of it like this**:
- A registry is like a library
- Repositories are like bookshelves (organized by project)
- Images are like books (different versions/editions)
- Tags are like edition numbers

---

## Docker Hub

**Docker Hub** (hub.docker.com) is the default public registry. It's where most Docker images come from.

### What Docker Hub Offers

| Feature | Description |
|---------|-------------|
| **Public Images** | Millions of free images from the community |
| **Official Images** | Verified images from vendors (nginx, postgres, node, etc.) |
| **Your Own Images** | Store and share your own images |
| **Free Tier** | Unlimited public repos, 1 private repo |

### Understanding Image Names

```
[registry/]username/repository:tag
```

| Example | Description |
|---------|-------------|
| `nginx` | Official image (from Docker Hub) |
| `nginx:1.25` | Official image with specific version |
| `myusername/myapp` | Your image on Docker Hub |
| `myusername/myapp:v1.0` | Your image with version tag |

---

## Getting Started with Docker Hub

### 1. Create an Account

1. Go to [hub.docker.com](https://hub.docker.com)
2. Sign up for a free account
3. Verify your email

### 2. Login from Terminal

```bash
docker login
```

Enter your Docker Hub username and password when prompted.

```
Login Succeeded
```

### 3. Logout (When Done)

```bash
docker logout
```

---

## Pushing Images to Docker Hub

To share your image with others, you need to push it to Docker Hub.

### Step-by-Step Process

```bash
# 1. Build your image (if not already built)
docker build -t myapp:v1 .

# 2. Tag it with your Docker Hub username
docker tag myapp:v1 yourusername/myapp:v1

# 3. Push to Docker Hub
docker push yourusername/myapp:v1
```

### Example Workflow

```bash
# Build a Node.js application
docker build -t my-node-app:1.0 .

# Tag for Docker Hub (replace 'johndoe' with your username)
docker tag my-node-app:1.0 johndoe/my-node-app:1.0
docker tag my-node-app:1.0 johndoe/my-node-app:latest

# Login and push
docker login
docker push johndoe/my-node-app:1.0
docker push johndoe/my-node-app:latest
```

After pushing, anyone can pull your image:
```bash
docker pull johndoe/my-node-app:1.0
```

---

## Pulling Images from Docker Hub

### Basic Pull Commands

```bash
# Pull official image (latest tag)
docker pull nginx

# Pull specific version
docker pull nginx:1.25-alpine

# Pull from user repository
docker pull username/myapp:v1
```

### Pull and Run

```bash
# Docker automatically pulls if image isn't local
docker run -d -p 80:80 nginx:alpine
```

---

## Tagging Best Practices

### Use Meaningful Tags

```bash
# Version numbers (recommended)
docker tag myapp:latest myapp:1.0.0
docker tag myapp:latest myapp:1.0
docker tag myapp:latest myapp:1

# Environment tags
docker tag myapp:latest myapp:production
docker tag myapp:latest myapp:staging
```

### Why Avoid `latest` in Production

The `latest` tag can change unexpectedly:

```bash
# Today, latest might be version 1.0
docker pull myapp:latest  # Gets v1.0

# Tomorrow, someone pushes v2.0
docker pull myapp:latest  # Gets v2.0 (breaking change!)
```

**Always use specific version tags in production:**
```bash
# Safe - always gets the same version
docker pull myapp:1.0.0
```

---

## Managing Your Repositories

### On Docker Hub Website

1. **View your images**: Go to your profile
2. **Create repository**: Click "Create Repository"
3. **Set visibility**: Public (free) or Private
4. **Add description**: Help others understand your image

### From Command Line

```bash
# Search for images
docker search nginx

# View your local images
docker images

# Remove local image
docker rmi myusername/myapp:v1
```

---

## Private Images

### Docker Hub (Limited Free)
- 1 free private repository
- Paid plans for more

### When to Use Private Images
- Proprietary code
- Internal tools
- Pre-release versions
- Images with secrets (though secrets shouldn't be in images!)

---

## Common Commands Summary

| Command | Purpose |
|---------|---------|
| `docker login` | Login to Docker Hub |
| `docker logout` | Logout from Docker Hub |
| `docker tag SOURCE TARGET` | Tag image for registry |
| `docker push IMAGE` | Upload image to registry |
| `docker pull IMAGE` | Download image from registry |
| `docker search TERM` | Search Docker Hub |

---

## Complete Example: Share Your Application

```bash
# 1. Create a simple Dockerfile
cat > Dockerfile << 'EOF'
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["node", "index.js"]
EOF

# 2. Build the image
docker build -t myapp:1.0 .

# 3. Test locally
docker run -d -p 3000:3000 myapp:1.0
curl http://localhost:3000  # Verify it works
docker stop $(docker ps -q)

# 4. Tag for Docker Hub
docker tag myapp:1.0 yourusername/myapp:1.0
docker tag myapp:1.0 yourusername/myapp:latest

# 5. Login and push
docker login
docker push yourusername/myapp:1.0
docker push yourusername/myapp:latest

# 6. Now anyone can run your app!
docker run -d -p 3000:3000 yourusername/myapp:1.0
```

---

## Advanced: Other Registries

For enterprise use, there are other registry options:

| Registry | Provider | Use Case |
|----------|----------|----------|
| **Amazon ECR** | AWS | AWS deployments |
| **Google GCR** | Google Cloud | GCP deployments |
| **Azure ACR** | Microsoft | Azure deployments |
| **GitHub GHCR** | GitHub | Open source projects |
| **Self-hosted** | You | Full control, private |

These work similarly - you just change the registry URL:
```bash
# Instead of Docker Hub
docker push yourusername/myapp:v1

# AWS ECR
docker push 123456789.dkr.ecr.us-east-1.amazonaws.com/myapp:v1

# GitHub Container Registry
docker push ghcr.io/yourusername/myapp:v1
```

---

## Summary

| Concept | Key Points |
|---------|------------|
| **Registry** | Storage for Docker images (like GitHub for code) |
| **Docker Hub** | Default public registry, free to use |
| **Push** | Upload your images to share with others |
| **Pull** | Download images to use |
| **Tags** | Version identifiers (use specific versions in production) |

---

## What You've Learned

Congratulations! You've completed the Docker fundamentals. You now know how to:

1. **Understand Docker** - What containers are and why they matter
2. **Work with Images** - Pull, build, and manage images
3. **Write Dockerfiles** - Create your own images
4. **Run Containers** - Start, stop, and manage containers
5. **Use Volumes** - Persist data beyond container lifecycle
6. **Share Images** - Push and pull from Docker Hub

### Next Steps

- Practice building and deploying your own applications
- Learn **Docker Compose** for multi-container applications
- Explore **Kubernetes** for container orchestration
- Study container security best practices
