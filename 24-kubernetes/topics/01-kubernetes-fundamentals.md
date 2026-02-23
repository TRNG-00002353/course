# Kubernetes Fundamentals

## What is Container Orchestration?

Container orchestration automates deployment, scaling, and management of containerized applications across clusters.

**Key Benefits:**
- Automated deployment and scaling
- Self-healing (restart failed containers)
- Load balancing and service discovery
- Rolling updates and rollbacks

---

## Kubernetes Architecture

```
┌─────────────────── Control Plane (Master) ───────────────────┐
│  API Server │ Scheduler │ Controller Manager │ etcd          │
└──────────────────────────┬───────────────────────────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        ▼                  ▼                  ▼
┌──────────────┐   ┌──────────────┐   ┌──────────────┐
│ Worker Node  │   │ Worker Node  │   │ Worker Node  │
│ kubelet      │   │ kubelet      │   │ kubelet      │
│ kube-proxy   │   │ kube-proxy   │   │ kube-proxy   │
│ [Pods...]    │   │ [Pods...]    │   │ [Pods...]    │
└──────────────┘   └──────────────┘   └──────────────┘
```

### Control Plane Components

| Component | Purpose |
|-----------|---------|
| **API Server** | Front-end for Kubernetes, handles all requests |
| **etcd** | Key-value store for cluster state |
| **Scheduler** | Assigns pods to nodes |
| **Controller Manager** | Maintains desired state (replicas, nodes) |

### Worker Node Components

| Component | Purpose |
|-----------|---------|
| **kubelet** | Runs pods, reports status to control plane |
| **kube-proxy** | Handles networking and load balancing |
| **Container Runtime** | Runs containers (containerd, Docker) |

---

## Pods

A **Pod** is the smallest deployable unit - one or more containers sharing network and storage.

### Simple Pod

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx-pod
  labels:
    app: nginx
spec:
  containers:
  - name: nginx
    image: nginx:1.19
    ports:
    - containerPort: 80
```

### Pod Commands

```bash
kubectl run nginx --image=nginx           # Create pod
kubectl get pods                          # List pods
kubectl get pods -o wide                  # List with details
kubectl describe pod nginx                # Pod details
kubectl logs nginx                        # View logs
kubectl exec -it nginx -- /bin/bash       # Shell into pod
kubectl delete pod nginx                  # Delete pod
```

---

## ReplicaSets

A **ReplicaSet** ensures a specified number of pod replicas are running.

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: nginx-rs
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.19
```

> **Note:** Use Deployments instead of ReplicaSets directly - Deployments manage ReplicaSets for you.

---

## Deployments

A **Deployment** manages ReplicaSets and provides rolling updates, rollbacks, and scaling.

### Deployment YAML

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
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

### Deployment Commands

```bash
# Create
kubectl create deployment nginx --image=nginx:1.19 --replicas=3
kubectl apply -f deployment.yaml

# View
kubectl get deployments
kubectl describe deployment nginx-deployment

# Scale
kubectl scale deployment nginx-deployment --replicas=5

# Update image (rolling update)
kubectl set image deployment/nginx-deployment nginx=nginx:1.20

# Rollout management
kubectl rollout status deployment/nginx-deployment
kubectl rollout history deployment/nginx-deployment
kubectl rollout undo deployment/nginx-deployment

# Delete
kubectl delete deployment nginx-deployment
```

---

## Namespaces

**Namespaces** provide isolation for resources within a cluster.

### Default Namespaces

| Namespace | Purpose |
|-----------|---------|
| `default` | Default for resources without namespace |
| `kube-system` | Kubernetes system components |
| `kube-public` | Public resources |

### Namespace Commands

```bash
kubectl create namespace dev              # Create namespace
kubectl get namespaces                    # List namespaces
kubectl get pods -n dev                   # Pods in namespace
kubectl get pods --all-namespaces         # All namespaces
kubectl config set-context --current --namespace=dev  # Set default
kubectl delete namespace dev              # Delete (removes all resources!)
```

### Deploy to Namespace

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
  namespace: production    # Specify namespace
spec:
  replicas: 2
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: myapp
        image: myapp:1.0
```

---

## Summary

| Concept | Purpose | Command |
|---------|---------|---------|
| **Pod** | Smallest unit, runs containers | `kubectl get pods` |
| **ReplicaSet** | Maintains pod replicas | `kubectl get rs` |
| **Deployment** | Manages rollouts and scaling | `kubectl get deployments` |
| **Namespace** | Resource isolation | `kubectl get ns` |

### Key Points

1. **Pods** are ephemeral - use Deployments for reliability
2. **Deployments** handle rolling updates and rollbacks
3. **Namespaces** isolate resources between teams/environments
4. Always set **resource requests/limits** in production
