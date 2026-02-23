# Kubernetes Fundamentals

## The Problem: Running Containers in Production

Imagine you've built a web application and containerized it with Docker. It works great on your laptop. But now you need to run it in production where:

- **Thousands of users** might access it simultaneously
- It needs to be **available 24/7** (what if the container crashes?)
- You need to **update** the application without downtime
- Traffic varies - more users during day, fewer at night (**scaling**)

Running `docker run myapp` on a single server won't cut it. You need:
- Multiple copies of your container for reliability
- Automatic restart if a container fails
- Load balancing across containers
- Zero-downtime deployments

**This is container orchestration** - and Kubernetes is the industry standard solution.

---

## What Kubernetes Does

Kubernetes (K8s) manages your containerized applications across a cluster of machines:

```
Without Kubernetes:                    With Kubernetes:
┌─────────────────┐                   ┌──────────────────────────────┐
│   You manually  │                   │     Kubernetes handles:      │
│   - Start containers               │   - Start/stop containers    │
│   - Restart failures │              │   - Auto-restart failures    │
│   - Scale up/down   │               │   - Auto-scale based on load │
│   - Update apps     │               │   - Rolling updates          │
│   - Balance load    │               │   - Load balancing           │
└─────────────────┘                   └──────────────────────────────┘
```

**You declare what you want, Kubernetes makes it happen.**

---

## Kubernetes Architecture

### The Cluster

A Kubernetes cluster has two types of machines:

```
┌─────────────────── Control Plane (Brain) ──────────────────┐
│  Decides WHERE to run containers, monitors health,         │
│  responds to failures, handles scaling                     │
└──────────────────────────┬─────────────────────────────────┘
                           │ manages
        ┌──────────────────┼──────────────────┐
        ▼                  ▼                  ▼
┌──────────────┐   ┌──────────────┐   ┌──────────────┐
│ Worker Node  │   │ Worker Node  │   │ Worker Node  │
│ (runs your   │   │ (runs your   │   │ (runs your   │
│  containers) │   │  containers) │   │  containers) │
└──────────────┘   └──────────────┘   └──────────────┘
```

### Control Plane Components

| Component | What It Does |
|-----------|--------------|
| **API Server** | Front door - all commands go through here |
| **Scheduler** | Decides which node should run each container |
| **Controller Manager** | Ensures desired state (e.g., "keep 3 replicas running") |
| **etcd** | Database storing all cluster state |

### Worker Node Components

| Component | What It Does |
|-----------|--------------|
| **kubelet** | Agent that runs containers on this node |
| **kube-proxy** | Handles networking for containers |
| **Container Runtime** | Actually runs containers (containerd, Docker) |

---

## Our Scenario: Deploying a Web Application

Throughout these topics, we'll deploy a web application step by step:

```
┌─────────────────────────────────────────────────────────┐
│                    Our Application                       │
│  ┌─────────┐      ┌─────────┐      ┌─────────┐         │
│  │ Frontend│ ───► │ Backend │ ───► │Database │         │
│  │ (nginx) │      │  (API)  │      │(postgres)│         │
│  └─────────┘      └─────────┘      └─────────┘         │
└─────────────────────────────────────────────────────────┘
```

Let's start with the building blocks.

---

## Pods: The Smallest Unit

### Why Pods?

You can't run a container directly in Kubernetes. Containers run inside **Pods**.

**A Pod is a wrapper around one or more containers** that:
- Share the same network (can talk via `localhost`)
- Share the same storage
- Are scheduled together on the same node

### Most Common: One Container Per Pod

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: frontend
  labels:
    app: frontend
spec:
  containers:
  - name: nginx
    image: nginx:1.19
    ports:
    - containerPort: 80
```

### Running Your First Pod

```bash
# Create pod from YAML
kubectl apply -f frontend-pod.yaml

# Or create directly
kubectl run frontend --image=nginx:1.19

# Check if it's running
kubectl get pods

# See more details
kubectl describe pod frontend

# View logs
kubectl logs frontend

# Access the container
kubectl exec -it frontend -- /bin/bash

# Delete the pod
kubectl delete pod frontend
```

### The Problem with Standalone Pods

What happens if the pod crashes or the node fails? **The pod is gone forever.**

This is why we rarely create pods directly. We need something to manage them.

---

## ReplicaSets: Ensuring Availability

### Why ReplicaSets?

A **ReplicaSet** ensures a specified number of pod copies are always running.

```
Without ReplicaSet:              With ReplicaSet (replicas: 3):

Pod dies → Gone forever          Pod dies → ReplicaSet creates new one

┌─────┐                          ┌─────┐  ┌─────┐  ┌─────┐
│ Pod │ ──── crashes ──── X      │ Pod │  │ Pod │  │ Pod │
└─────┘                          └─────┘  └─────┘  └─────┘
                                     │        │        │
                                     └────────┴────────┘
                                              │
                                    ReplicaSet monitors
                                    "Are 3 pods running?"
```

### ReplicaSet YAML

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: frontend-rs
spec:
  replicas: 3                    # Keep 3 copies running
  selector:
    matchLabels:
      app: frontend              # Manage pods with this label
  template:                      # Template for creating pods
    metadata:
      labels:
        app: frontend
    spec:
      containers:
      - name: nginx
        image: nginx:1.19
```

### The Problem with ReplicaSets

ReplicaSets handle availability, but what about **updates**?

If you change the image from `nginx:1.19` to `nginx:1.20`, the ReplicaSet won't update existing pods. You'd have to delete them manually.

This is why we use **Deployments**.

---

## Deployments: The Complete Solution

### Why Deployments?

A **Deployment** manages ReplicaSets and provides:
- **Rolling updates**: Update pods gradually without downtime
- **Rollbacks**: Revert to previous version if something breaks
- **Scaling**: Easily change the number of replicas

```
┌─────────────────────────────────────────────────┐
│                  Deployment                      │
│  "I want 3 nginx:1.19 pods"                     │
│                    │                             │
│                    ▼                             │
│              ┌───────────┐                       │
│              │ ReplicaSet│ ◄── created by       │
│              │ nginx:1.19│     Deployment       │
│              └─────┬─────┘                       │
│           ┌───────┼───────┐                     │
│           ▼       ▼       ▼                     │
│        ┌─────┐ ┌─────┐ ┌─────┐                  │
│        │ Pod │ │ Pod │ │ Pod │                  │
│        └─────┘ └─────┘ └─────┘                  │
└─────────────────────────────────────────────────┘
```

### Deployment YAML

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
spec:
  replicas: 3
  selector:
    matchLabels:
      app: frontend
  template:
    metadata:
      labels:
        app: frontend
    spec:
      containers:
      - name: nginx
        image: nginx:1.19
        ports:
        - containerPort: 80
        resources:              # Always set in production
          requests:
            cpu: 100m
            memory: 128Mi
          limits:
            cpu: 250m
            memory: 256Mi
```

### Deployment Commands

```bash
# Create deployment
kubectl create deployment frontend --image=nginx:1.19 --replicas=3
# Or from file
kubectl apply -f deployment.yaml

# Check status
kubectl get deployments
kubectl describe deployment frontend

# Scale up/down
kubectl scale deployment frontend --replicas=5

# Update image (triggers rolling update)
kubectl set image deployment/frontend nginx=nginx:1.20

# Watch the rolling update
kubectl rollout status deployment/frontend

# Something wrong? Rollback!
kubectl rollout undo deployment/frontend

# See rollout history
kubectl rollout history deployment/frontend

# Delete deployment (also deletes pods)
kubectl delete deployment frontend
```

### Rolling Update in Action

When you update the image, Kubernetes gradually replaces old pods with new ones:

```
Step 1: Start new pod          Step 2: New pod ready, terminate old
┌─────┐ ┌─────┐ ┌─────┐       ┌─────┐ ┌─────┐ ┌─────┐
│v1.19│ │v1.19│ │v1.19│   →   │v1.20│ │v1.19│ │v1.19│
└─────┘ └─────┘ └─────┘       └─────┘ └─────┘ └─────┘
        ┌─────┐
        │v1.20│ (starting)
        └─────┘

Step 3: Continue...            Step 4: Complete
┌─────┐ ┌─────┐ ┌─────┐       ┌─────┐ ┌─────┐ ┌─────┐
│v1.20│ │v1.20│ │v1.19│   →   │v1.20│ │v1.20│ │v1.20│
└─────┘ └─────┘ └─────┘       └─────┘ └─────┘ └─────┘
```

**Zero downtime** - users always have pods available.

---

## Namespaces: Organizing Your Cluster

### Why Namespaces?

As your cluster grows, you need organization:
- Separate **development** from **production**
- Give different **teams** their own space
- Apply different **resource quotas** per environment

```
┌─────────────────── Kubernetes Cluster ───────────────────┐
│                                                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐       │
│  │ default     │  │ development │  │ production  │       │
│  │ namespace   │  │ namespace   │  │ namespace   │       │
│  │             │  │             │  │             │       │
│  │ (test pods) │  │ (dev apps)  │  │ (live apps) │       │
│  └─────────────┘  └─────────────┘  └─────────────┘       │
│                                                           │
└───────────────────────────────────────────────────────────┘
```

### Default Namespaces

| Namespace | Purpose |
|-----------|---------|
| `default` | Where resources go if you don't specify |
| `kube-system` | Kubernetes system components |
| `kube-public` | Public resources |

### Namespace Commands

```bash
# Create namespace
kubectl create namespace development

# List namespaces
kubectl get namespaces

# Deploy to specific namespace
kubectl apply -f deployment.yaml -n development

# Get pods in namespace
kubectl get pods -n development

# Get pods in all namespaces
kubectl get pods --all-namespaces

# Set default namespace for your context
kubectl config set-context --current --namespace=development
```

### Deploy to Namespace via YAML

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
  namespace: production      # Specify namespace here
spec:
  replicas: 3
  # ... rest of spec
```

---

## Putting It Together: Our Frontend Deployment

Let's deploy the frontend of our web application:

```yaml
# frontend-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
  namespace: production
  labels:
    app: frontend
    tier: web
spec:
  replicas: 3
  selector:
    matchLabels:
      app: frontend
  template:
    metadata:
      labels:
        app: frontend
        tier: web
    spec:
      containers:
      - name: nginx
        image: nginx:1.19
        ports:
        - containerPort: 80
        resources:
          requests:
            cpu: 100m
            memory: 128Mi
          limits:
            cpu: 250m
            memory: 256Mi
```

```bash
# Create namespace first
kubectl create namespace production

# Deploy frontend
kubectl apply -f frontend-deployment.yaml

# Verify
kubectl get deployments -n production
kubectl get pods -n production
```

**But wait** - how do users access this application? The pods have IP addresses, but those change when pods restart.

**Next: Services** - giving your application a stable address.

---

## Summary

| Concept | Purpose | Why You Need It |
|---------|---------|-----------------|
| **Pod** | Runs containers | Basic unit, but fragile alone |
| **ReplicaSet** | Maintains pod copies | Ensures availability |
| **Deployment** | Manages ReplicaSets | Rolling updates, rollbacks |
| **Namespace** | Organizes resources | Isolation between environments |

### The Hierarchy

```
Deployment
    └── manages → ReplicaSet
                      └── manages → Pods
                                       └── runs → Containers
```

### Key Takeaways

1. **Never create standalone Pods** - use Deployments
2. **Deployments handle everything** - scaling, updates, rollbacks
3. **Use Namespaces** to organize resources
4. **Set resource limits** in production
5. **Labels** connect everything - Deployments find Pods via labels
