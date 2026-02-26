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

## Master Node vs Worker Node: Deep Dive

Understanding the difference between master and worker nodes is fundamental to Kubernetes.

### Master Node (Control Plane)

The **Master Node** is the brain of the cluster. It makes all the decisions but **never runs your application containers**.

```
┌─────────────────────── MASTER NODE ───────────────────────┐
│                                                            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │  API Server  │  │  Scheduler   │  │  Controller  │    │
│  │              │  │              │  │  Manager     │    │
│  │ - REST API   │  │ - Watches    │  │              │    │
│  │ - Auth/AuthZ │  │   unscheduled│  │ - Node ctrl  │    │
│  │ - Validation │  │   pods       │  │ - Repl ctrl  │    │
│  │ - Entry point│  │ - Assigns to │  │ - Endpoint   │    │
│  │   for all    │  │   best node  │  │   ctrl       │    │
│  │   commands   │  │              │  │ - Service    │    │
│  └──────────────┘  └──────────────┘  │   account    │    │
│                                       └──────────────┘    │
│  ┌────────────────────────────────────────────────────┐  │
│  │                       etcd                          │  │
│  │  - Distributed key-value store                      │  │
│  │  - Stores ALL cluster data (the "source of truth") │  │
│  │  - Highly available (usually 3 or 5 replicas)      │  │
│  └────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────┘
```

#### Master Node Components Explained

| Component | Role | What Happens If It Fails |
|-----------|------|--------------------------|
| **API Server** | Single entry point for all cluster operations. Every kubectl command goes here first. | No new operations possible, but existing pods keep running |
| **Scheduler** | Watches for newly created pods with no assigned node and selects a node for them to run on | New pods stay in "Pending" state |
| **Controller Manager** | Runs controller loops that watch cluster state and make changes to move toward desired state | Automatic recovery stops (no new pods if one dies) |
| **etcd** | Stores all cluster data: configs, secrets, current state | Cluster is completely broken - this is why etcd backup is critical |

#### How the Scheduler Decides Where to Run Pods

```
New Pod Created
      │
      ▼
┌─────────────────────────────────────────────────────────┐
│                    SCHEDULER CHECKS:                     │
│                                                          │
│  1. Resource Requirements                                │
│     Does the node have enough CPU/memory?                │
│                                                          │
│  2. Node Selectors / Affinity                           │
│     Does pod require specific node labels?               │
│                                                          │
│  3. Taints and Tolerations                              │
│     Is the node tainted? Does pod tolerate it?          │
│                                                          │
│  4. Resource Availability                                │
│     Which node has the most available resources?         │
│                                                          │
│  5. Pod Distribution                                     │
│     Spread pods across nodes for high availability       │
└─────────────────────────────────────────────────────────┘
      │
      ▼
  Best Node Selected → Pod Scheduled
```

### Worker Node

The **Worker Node** is where your applications actually run. Each worker node can run many pods.

```
┌─────────────────────── WORKER NODE ───────────────────────┐
│                                                            │
│  ┌──────────────────────────────────────────────────────┐│
│  │                      kubelet                          ││
│  │  - Registers node with the cluster                    ││
│  │  - Receives pod specs from API server                 ││
│  │  - Ensures containers are running and healthy         ││
│  │  - Reports node and pod status back to master         ││
│  │  - Executes liveness/readiness probes                 ││
│  └──────────────────────────────────────────────────────┘│
│                                                            │
│  ┌──────────────────────────────────────────────────────┐│
│  │                    kube-proxy                         ││
│  │  - Maintains network rules on the node                ││
│  │  - Enables Service abstraction (load balancing)       ││
│  │  - Uses iptables or IPVS for routing                  ││
│  └──────────────────────────────────────────────────────┘│
│                                                            │
│  ┌──────────────────────────────────────────────────────┐│
│  │               Container Runtime                        ││
│  │  - Actually pulls images and runs containers          ││
│  │  - containerd (most common), CRI-O, Docker            ││
│  │  - Implements Container Runtime Interface (CRI)       ││
│  └──────────────────────────────────────────────────────┘│
│                                                            │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐     │
│  │  Pod A  │  │  Pod B  │  │  Pod C  │  │  Pod D  │     │
│  │(your app)│  │(your app)│  │(your app)│  │(your app)│    │
│  └─────────┘  └─────────┘  └─────────┘  └─────────┘     │
└────────────────────────────────────────────────────────────┘
```

### Key Differences Summary

| Aspect | Master Node | Worker Node |
|--------|-------------|-------------|
| **Purpose** | Cluster management and orchestration | Run application workloads |
| **Runs your containers?** | No (only system components) | Yes |
| **Number in cluster** | 1 (dev), 3+ (production for HA) | Many (scale based on workload) |
| **If it fails** | Can't make changes, but apps keep running | Pods rescheduled to other workers |
| **Resources needed** | Less (management only) | More (runs actual workloads) |
| **Direct user access** | Usually restricted | Never direct access |

### High Availability Setup

In production, you run multiple master nodes:

```
                    Load Balancer
                         │
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
   ┌──────────┐   ┌──────────┐   ┌──────────┐
   │ Master 1 │   │ Master 2 │   │ Master 3 │
   │          │   │          │   │          │
   │ API, Sch │   │ API, Sch │   │ API, Sch │
   │ Ctrl Mgr │   │ Ctrl Mgr │   │ Ctrl Mgr │
   └────┬─────┘   └────┬─────┘   └────┬─────┘
        │              │              │
        └──────────────┼──────────────┘
                       │
              ┌────────┴────────┐
              │   etcd cluster  │
              │   (3 or 5 nodes)│
              └─────────────────┘
                       │
         ┌─────────────┼─────────────┐
         │             │             │
         ▼             ▼             ▼
   ┌──────────┐  ┌──────────┐  ┌──────────┐
   │ Worker 1 │  │ Worker 2 │  │ Worker N │
   └──────────┘  └──────────┘  └──────────┘
```

---

## What Happens When You Create a Pod

Understanding this flow is essential for troubleshooting:

```
1. kubectl apply -f pod.yaml
         │
         ▼
2. API Server receives request
   - Authenticates user
   - Validates YAML
   - Stores pod spec in etcd (status: Pending)
         │
         ▼
3. Scheduler notices new unscheduled pod
   - Evaluates all worker nodes
   - Selects best node
   - Updates pod in etcd with node assignment
         │
         ▼
4. Kubelet on selected node notices new pod assigned to it
   - Pulls container image
   - Creates and starts container
   - Reports status back to API server
         │
         ▼
5. Pod is Running!
   - kubelet continues monitoring
   - Reports health status periodically
```

### Common States and What They Mean

| State | Meaning | Common Cause |
|-------|---------|--------------|
| **Pending** | Pod accepted but not running | Waiting for scheduling or image pull |
| **Running** | At least one container is running | Normal operation |
| **Succeeded** | All containers completed successfully | Job/batch pods |
| **Failed** | All containers terminated, at least one failed | Application error |
| **CrashLoopBackOff** | Container keeps crashing and restarting | Application bug, missing config |
| **ImagePullBackOff** | Can't pull container image | Wrong image name, no registry access |

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

## Labels and Selectors: The Glue of Kubernetes

Before diving into pods, understand this crucial concept - **labels and selectors** are how Kubernetes components find and manage each other.

### What Are Labels?

Labels are key-value pairs attached to objects (pods, services, deployments). They're like tags that help organize and select resources.

```yaml
metadata:
  labels:
    app: frontend          # What application is this?
    environment: production # What environment?
    team: web-team         # Who owns this?
    version: v1.2.0        # What version?
```

### How Selectors Work

Selectors find objects based on their labels:

```
┌─────────────────────────────────────────────────────────────┐
│                      Service                                 │
│   selector:                                                  │
│     app: frontend  ─────────────┐                           │
│                                 │ "Find all pods with       │
│                                 │  app=frontend label"      │
└─────────────────────────────────┼───────────────────────────┘
                                  │
        ┌─────────────────────────┼─────────────────────────┐
        │                         │                         │
        ▼                         ▼                         ▼
  ┌───────────┐            ┌───────────┐            ┌───────────┐
  │   Pod 1   │            │   Pod 2   │            │   Pod 3   │
  │app:frontend│ ✓ Match!  │app:frontend│ ✓ Match!  │app:backend│ ✗ No match
  └───────────┘            └───────────┘            └───────────┘
```

### Why This Matters

| Component | Uses Labels/Selectors To |
|-----------|-------------------------|
| **Service** | Find which pods to send traffic to |
| **Deployment** | Know which pods it manages |
| **ReplicaSet** | Track pods it should maintain |
| **Network Policy** | Identify which pods rules apply to |

**Key insight:** If your labels don't match, things break silently. A service with wrong selector = no traffic to pods.

---

## Pods: The Smallest Unit

### Why Pods?

You can't run a container directly in Kubernetes. Containers run inside **Pods**.

**A Pod is a wrapper around one or more containers** that:
- Share the same network (can talk via `localhost`)
- Share the same storage
- Are scheduled together on the same node
- Have a single IP address (containers inside share it)

### Pod Lifecycle

```
┌─────────────────────────────────────────────────────────────┐
│                      POD LIFECYCLE                           │
│                                                              │
│  Created ──► Pending ──► Running ──► Succeeded/Failed       │
│                │            │                                │
│                │            │                                │
│         (Scheduling)  (Containers run)                       │
│                                                              │
│  Common issues:                                              │
│  - Stuck in Pending = scheduler can't place it              │
│  - CrashLoopBackOff = container keeps crashing              │
│  - ImagePullBackOff = can't download container image        │
└─────────────────────────────────────────────────────────────┘
```

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

### Resource Requests vs Limits: Essential Knowledge

This is critical for production deployments:

```
┌─────────────────────────────────────────────────────────────┐
│                  REQUESTS vs LIMITS                          │
│                                                              │
│  requests:                   limits:                         │
│    cpu: 100m      ◄─────►      cpu: 250m                    │
│    memory: 128Mi             memory: 256Mi                   │
│                                                              │
│  "Guaranteed minimum"       "Maximum allowed"                │
│  Scheduler uses this        Container killed if exceeded     │
│  to place pod               (for memory - OOMKilled)         │
└─────────────────────────────────────────────────────────────┘
```

| Concept | What It Means | What Happens |
|---------|---------------|--------------|
| **Request** | Minimum resources guaranteed | Scheduler only places pod on node with this much available |
| **Limit** | Maximum resources allowed | CPU: throttled if exceeded. Memory: pod killed (OOMKilled) |
| **No limits set** | Pod can use unlimited resources | Can starve other pods, cause node instability |

**CPU Units:**
- `1` = 1 CPU core
- `100m` = 100 millicores = 0.1 CPU core
- `500m` = 0.5 CPU core

**Memory Units:**
- `128Mi` = 128 Mebibytes (≈134 MB)
- `1Gi` = 1 Gibibyte (≈1.07 GB)

### Best Practice

```yaml
resources:
  requests:
    cpu: 100m       # Start low, increase based on monitoring
    memory: 128Mi
  limits:
    cpu: 500m       # Usually 2-5x the request
    memory: 512Mi   # Should be close to what app actually needs
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
