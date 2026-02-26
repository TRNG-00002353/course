# Networking and kubectl

## Completing Our Scenario

We now have:
- Frontend deployment with 3 replicas
- Backend deployment with 2 replicas
- Database with persistent storage
- Services connecting them internally

```
┌─────────────────────────────────────────────────────────────────┐
│                    Our Application So Far                        │
│                                                                  │
│                          ??? ◄─── How do external users get in? │
│                           │                                      │
│                           ▼                                      │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │ Frontend    │───►│ Backend     │───►│ Database    │         │
│  │ Service     │    │ Service     │    │ Service     │         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│        │                  │                  │                   │
│        ▼                  ▼                  ▼                   │
│  ┌───────────┐      ┌───────────┐      ┌───────────┐           │
│  │  Pods     │      │  Pods     │      │  Pod+PVC  │           │
│  └───────────┘      └───────────┘      └───────────┘           │
└─────────────────────────────────────────────────────────────────┘
```

**Remaining questions:**
1. How do external users access our application?
2. How do we route different URLs to different services?
3. How do we secure communication between pods?
4. How do we manage all of this?

---

## Service Discovery: How Pods Find Each Other

Before we expose our app externally, let's understand internal communication.

### The Problem

Your frontend pod needs to call the backend. But backend pods can be anywhere in the cluster, and their IPs change constantly.

### The Solution: DNS-Based Discovery

Kubernetes automatically creates DNS records for services:

```
<service-name>.<namespace>.svc.cluster.local
```

### CoreDNS: The Cluster DNS Server

**CoreDNS** is the DNS server running in your cluster that makes service discovery work:

```
┌─────────────────────────────────────────────────────────────┐
│                    HOW DNS RESOLUTION WORKS                  │
│                                                              │
│  1. Pod wants to reach "backend-service"                     │
│                    │                                         │
│                    ▼                                         │
│  2. Pod's /etc/resolv.conf points to CoreDNS                │
│     nameserver 10.96.0.10  (CoreDNS ClusterIP)              │
│                    │                                         │
│                    ▼                                         │
│  3. CoreDNS looks up service in its records                  │
│     "backend-service.default.svc.cluster.local"              │
│                    │                                         │
│                    ▼                                         │
│  4. Returns ClusterIP of the service                         │
│     10.96.45.123                                             │
│                    │                                         │
│                    ▼                                         │
│  5. Pod connects to 10.96.45.123                            │
│     kube-proxy routes to actual pod                          │
└─────────────────────────────────────────────────────────────┘
```

### DNS Name Formats

| Format | When to Use | Example |
|--------|-------------|---------|
| `service-name` | Same namespace | `backend-service` |
| `service-name.namespace` | Different namespace | `backend-service.production` |
| `service-name.namespace.svc.cluster.local` | Full FQDN (always works) | `backend-service.production.svc.cluster.local` |

### How It Works

```yaml
# Backend Service in 'production' namespace
apiVersion: v1
kind: Service
metadata:
  name: backend-service
  namespace: production
```

Your frontend can now reach it using:

```bash
# From same namespace - just use service name
curl http://backend-service:8080

# From different namespace - include namespace
curl http://backend-service.production:8080

# Full DNS name (always works)
curl http://backend-service.production.svc.cluster.local:8080
```

### Real-World Example

```yaml
# Frontend deployment that calls backend
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
spec:
  template:
    spec:
      containers:
      - name: webapp
        image: myapp/frontend:1.0
        env:
        - name: BACKEND_URL
          value: "http://backend-service:8080"    # DNS name!
```

**Key insight:** Always use service DNS names, never pod IPs.

---

## Ingress: External Access with Smart Routing

### The Problem with LoadBalancer Services

Each LoadBalancer service creates a new cloud load balancer:

```
┌─────────────────────────────────────────────────────────────┐
│  Expensive! Each service = One load balancer                 │
│                                                              │
│  api.example.com  ──►  LoadBalancer ($$$)  ──►  API Service │
│  www.example.com  ──►  LoadBalancer ($$$)  ──►  Web Service │
│  admin.example.com ──► LoadBalancer ($$$)  ──►  Admin Service│
└─────────────────────────────────────────────────────────────┘
```

### The Solution: Ingress

**Ingress** is a single entry point that routes traffic based on URL paths or hostnames:

```
┌─────────────────────────────────────────────────────────────┐
│  One load balancer for all services!                         │
│                                                              │
│  All traffic ──► LoadBalancer ──► Ingress Controller        │
│                                          │                   │
│                    ┌─────────────────────┼───────────────┐  │
│                    │                     │               │  │
│                    ▼                     ▼               ▼  │
│               api.example.com    www.example.com   /admin   │
│                    │                     │               │  │
│                    ▼                     ▼               ▼  │
│              API Service           Web Service    Admin Svc │
└─────────────────────────────────────────────────────────────┘
```

### How Ingress Works

1. **Ingress Controller** - A pod that runs nginx/traefik (you install this once)
2. **Ingress Resource** - Your routing rules (you create per application)

### Popular Ingress Controllers

| Controller | Best For | Notes |
|------------|----------|-------|
| **NGINX Ingress** | Most common, general purpose | Default choice for most |
| **Traefik** | Auto-discovery, Let's Encrypt | Great for dynamic environments |
| **AWS ALB Ingress** | AWS environments | Native AWS integration |
| **Istio Gateway** | Service mesh users | Advanced traffic management |

**Important:** Ingress resources do nothing without an Ingress Controller installed!

```bash
# Check if ingress controller is installed
kubectl get pods -n ingress-nginx

# Install NGINX Ingress Controller (example)
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml
```

### Basic Ingress Example

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: app-ingress
spec:
  ingressClassName: nginx
  rules:
  - host: myapp.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: frontend-service
            port:
              number: 80
```

Now `http://myapp.example.com` routes to your frontend service.

### Path-Based Routing

Route different URL paths to different services:

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: app-ingress
spec:
  ingressClassName: nginx
  rules:
  - host: myapp.example.com
    http:
      paths:
      - path: /api                     # myapp.example.com/api/*
        pathType: Prefix
        backend:
          service:
            name: backend-service
            port:
              number: 8080
      - path: /                        # myapp.example.com/*
        pathType: Prefix
        backend:
          service:
            name: frontend-service
            port:
              number: 80
```

```
https://myapp.example.com/           →  frontend-service
https://myapp.example.com/api/users  →  backend-service
https://myapp.example.com/api/orders →  backend-service
```

### Host-Based Routing

Route different domains to different services:

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: multi-host-ingress
spec:
  ingressClassName: nginx
  rules:
  - host: www.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: web-service
            port:
              number: 80
  - host: api.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: api-service
            port:
              number: 8080
```

### TLS/HTTPS

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: tls-ingress
spec:
  ingressClassName: nginx
  tls:                              # Enable HTTPS
  - hosts:
    - myapp.example.com
    secretName: tls-secret          # Certificate stored in Secret
  rules:
  - host: myapp.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: frontend-service
            port:
              number: 80
```

### Ingress Commands

```bash
kubectl get ingress
kubectl describe ingress app-ingress
kubectl delete ingress app-ingress
```

---

## Network Policies: Securing Pod Communication

### The Problem

By default, any pod can talk to any other pod in the cluster:

```
┌─────────────────────────────────────────────────────────────┐
│  Default: Everything can talk to everything!                 │
│                                                              │
│  Frontend ──────► Backend ──────► Database                  │
│      │               │               ▲                       │
│      │               │               │                       │
│      └───────────────┴───────────────┘  ← This is bad!      │
│         Frontend shouldn't access DB directly                │
└─────────────────────────────────────────────────────────────┘
```

### The Solution: Network Policies

**Network Policies** are firewall rules for pods. They define:
- **Ingress**: Who can send traffic TO this pod
- **Egress**: Where can this pod send traffic TO

### Start with Default Deny

Best practice: Block everything, then allow specific traffic.

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: default-deny-all
  namespace: production
spec:
  podSelector: {}                  # Apply to ALL pods
  policyTypes:
  - Ingress
  - Egress
```

Now no pod can communicate. Let's allow what we need.

### Allow Frontend to Backend

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: backend-allow-frontend
  namespace: production
spec:
  podSelector:
    matchLabels:
      app: backend                 # Apply to backend pods
  policyTypes:
  - Ingress
  ingress:
  - from:
    - podSelector:
        matchLabels:
          app: frontend            # Allow from frontend pods
    ports:
    - protocol: TCP
      port: 8080
```

### Allow Backend to Database

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: database-allow-backend
  namespace: production
spec:
  podSelector:
    matchLabels:
      app: database
  policyTypes:
  - Ingress
  ingress:
  - from:
    - podSelector:
        matchLabels:
          app: backend             # Only backend can access DB
    ports:
    - protocol: TCP
      port: 5432
```

### The Result

```
┌─────────────────────────────────────────────────────────────┐
│  With Network Policies: Controlled communication             │
│                                                              │
│  Frontend ──────► Backend ──────► Database                  │
│      │               │                                       │
│      X               X           ← Frontend can't reach DB   │
│      └───────────────┘             directly anymore!         │
└─────────────────────────────────────────────────────────────┘
```

### Network Policy Commands

```bash
kubectl get networkpolicies
kubectl get netpol
kubectl describe networkpolicy backend-allow-frontend
```

---

## kubectl: Managing Everything

Now that we understand the components, let's master the tool that manages them.

### The Essential Commands

#### Creating Resources

```bash
# Declarative (recommended) - from YAML file
kubectl apply -f deployment.yaml
kubectl apply -f ./manifests/           # All files in directory

# Imperative - quick commands
kubectl create deployment nginx --image=nginx
kubectl run test-pod --image=busybox
kubectl expose deployment nginx --port=80
```

#### Viewing Resources

```bash
# List resources
kubectl get pods
kubectl get pods -o wide                 # More details
kubectl get pods -A                      # All namespaces
kubectl get all                          # All resource types

# Detailed information
kubectl describe pod nginx-pod
kubectl describe deployment frontend

# Watch for changes (real-time)
kubectl get pods -w
```

#### Debugging

```bash
# View logs
kubectl logs nginx-pod
kubectl logs nginx-pod -f                # Follow (stream)
kubectl logs nginx-pod --previous        # Previous container

# Execute commands in pod
kubectl exec nginx-pod -- ls /var
kubectl exec -it nginx-pod -- /bin/bash  # Interactive shell

# Port forwarding (access pod locally)
kubectl port-forward pod/nginx-pod 8080:80
kubectl port-forward service/nginx-svc 8080:80
```

#### Managing Deployments

```bash
# Scale
kubectl scale deployment frontend --replicas=5

# Update image (triggers rolling update)
kubectl set image deployment/frontend nginx=nginx:1.20

# Check rollout status
kubectl rollout status deployment/frontend

# View rollout history
kubectl rollout history deployment/frontend

# Rollback
kubectl rollout undo deployment/frontend
```

#### Deleting Resources

```bash
kubectl delete pod nginx-pod
kubectl delete -f deployment.yaml
kubectl delete deployment frontend
```

### Working with Namespaces

```bash
# List namespaces
kubectl get namespaces

# Work in specific namespace
kubectl get pods -n production
kubectl apply -f app.yaml -n production

# Set default namespace
kubectl config set-context --current --namespace=production
```

### Troubleshooting Checklist

When something isn't working:

```bash
# 1. Check pod status
kubectl get pods
# Look for: Running, Pending, CrashLoopBackOff, ImagePullBackOff

# 2. Get detailed info
kubectl describe pod <pod-name>
# Look at: Events section at the bottom

# 3. Check logs
kubectl logs <pod-name>
kubectl logs <pod-name> --previous      # If crashed

# 4. Check events
kubectl get events --sort-by='.lastTimestamp'

# 5. Test connectivity
kubectl exec -it <pod-name> -- curl http://service-name:port
```

---

## Common Errors and Solutions

### Pod Stuck in Pending

```
NAME        READY   STATUS    RESTARTS   AGE
my-pod      0/1     Pending   0          5m
```

**Causes and Solutions:**

| Cause | How to Identify | Solution |
|-------|-----------------|----------|
| **Insufficient resources** | `kubectl describe pod` shows "Insufficient cpu/memory" | Add more nodes or reduce resource requests |
| **No matching node** | Node selector/affinity doesn't match any node | Check node labels, adjust selectors |
| **PVC not bound** | "persistentvolumeclaim not found" or "waiting for first consumer" | Create PV or check StorageClass |

```bash
# Debug Pending pod
kubectl describe pod my-pod | grep -A 10 Events
```

### CrashLoopBackOff

```
NAME        READY   STATUS             RESTARTS   AGE
my-pod      0/1     CrashLoopBackOff   5          10m
```

**The container keeps crashing and Kubernetes keeps restarting it.**

```bash
# Check why it's crashing
kubectl logs my-pod --previous

# Common causes:
# - Application error (check logs)
# - Missing environment variables
# - Can't connect to database
# - Wrong command or entrypoint
```

### ImagePullBackOff

```
NAME        READY   STATUS             RESTARTS   AGE
my-pod      0/1     ImagePullBackOff   0          5m
```

**Kubernetes can't pull the container image.**

| Cause | Solution |
|-------|----------|
| Typo in image name | Check image: field carefully |
| Private registry | Add imagePullSecrets |
| Image doesn't exist | Verify image exists in registry |
| Network issue | Check node internet connectivity |

```bash
# Check exact error
kubectl describe pod my-pod | grep -A 5 "Failed"
```

### Service Not Routing Traffic

```
# Pod is running but service returns nothing
curl: (7) Failed to connect
```

**Troubleshooting steps:**

```bash
# 1. Check service exists
kubectl get svc my-service

# 2. Check endpoints (pods connected to service)
kubectl get endpoints my-service
# If empty = labels don't match!

# 3. Compare labels
kubectl get pods --show-labels
kubectl describe svc my-service | grep Selector

# 4. Test from inside cluster
kubectl run test --image=busybox --rm -it -- wget -qO- http://my-service:80
```

### DNS Resolution Failing

```bash
# From inside a pod
nslookup my-service
# Server: 10.96.0.10
# ** server can't find my-service: NXDOMAIN
```

**Solutions:**

```bash
# Check CoreDNS is running
kubectl get pods -n kube-system -l k8s-app=kube-dns

# Try full DNS name
nslookup my-service.default.svc.cluster.local

# Check service exists in correct namespace
kubectl get svc --all-namespaces | grep my-service
```

### Quick Reference

| Command | Description |
|---------|-------------|
| `kubectl get pods` | List pods |
| `kubectl describe pod X` | Pod details |
| `kubectl logs X` | View logs |
| `kubectl exec -it X -- bash` | Shell into pod |
| `kubectl apply -f X.yaml` | Create/update resource |
| `kubectl delete -f X.yaml` | Delete resource |
| `kubectl rollout undo deploy/X` | Rollback deployment |
| `kubectl port-forward pod/X 8080:80` | Local access |

### Essential kubectl Productivity Tips

```bash
# Set up aliases (add to ~/.bashrc or ~/.zshrc)
alias k='kubectl'
alias kgp='kubectl get pods'
alias kgs='kubectl get svc'
alias kgd='kubectl get deployments'
alias kd='kubectl describe'
alias kl='kubectl logs'
alias kaf='kubectl apply -f'

# Enable autocompletion (bash)
source <(kubectl completion bash)

# Enable autocompletion (zsh)
source <(kubectl completion zsh)
```

### Output Formats

```bash
# Wide output (more columns)
kubectl get pods -o wide

# YAML output (see full spec)
kubectl get pod my-pod -o yaml

# JSON output (for scripting)
kubectl get pod my-pod -o json

# Custom columns
kubectl get pods -o custom-columns=NAME:.metadata.name,STATUS:.status.phase

# JSONPath (extract specific fields)
kubectl get pods -o jsonpath='{.items[*].metadata.name}'
```

### Useful One-Liners

```bash
# Get all resources in namespace
kubectl get all -n production

# Delete all pods in namespace (careful!)
kubectl delete pods --all -n development

# Get pods sorted by restart count
kubectl get pods --sort-by='.status.containerStatuses[0].restartCount'

# Get pods on specific node
kubectl get pods --field-selector spec.nodeName=worker-1

# Watch pods in real-time
kubectl get pods -w

# Get resource usage (requires metrics-server)
kubectl top pods
kubectl top nodes
```

---

## Putting It All Together: Complete Application

Here's our complete web application with all networking configured:

```yaml
# Complete production deployment
---
# Namespace
apiVersion: v1
kind: Namespace
metadata:
  name: production
---
# Network Policy: Default deny
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: default-deny
  namespace: production
spec:
  podSelector: {}
  policyTypes: [Ingress, Egress]
---
# Frontend Deployment
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
  namespace: production
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
---
# Frontend Service
apiVersion: v1
kind: Service
metadata:
  name: frontend-service
  namespace: production
spec:
  selector:
    app: frontend
  ports:
  - port: 80
---
# Network Policy: Allow ingress to frontend
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: frontend-ingress
  namespace: production
spec:
  podSelector:
    matchLabels:
      app: frontend
  policyTypes: [Ingress]
  ingress:
  - ports:
    - port: 80
---
# Ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: app-ingress
  namespace: production
spec:
  ingressClassName: nginx
  rules:
  - host: myapp.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: frontend-service
            port:
              number: 80
```

### Deploy and Verify

```bash
# Deploy everything
kubectl apply -f complete-app.yaml

# Verify
kubectl get all -n production
kubectl get ingress -n production
kubectl get networkpolicies -n production

# Test
kubectl port-forward service/frontend-service 8080:80 -n production
# Open http://localhost:8080
```

---

## Summary: The Complete Picture

```
                            INTERNET
                                │
                                ▼
                    ┌───────────────────────┐
                    │       Ingress         │  ← Routes by URL/host
                    │   myapp.example.com   │
                    └───────────┬───────────┘
                                │
            ┌───────────────────┼───────────────────┐
            │                   │                   │
            ▼                   ▼                   ▼
    ┌───────────────┐  ┌───────────────┐  ┌───────────────┐
    │  /            │  │  /api         │  │  /admin       │
    │  frontend-svc │  │  backend-svc  │  │  admin-svc    │
    └───────┬───────┘  └───────┬───────┘  └───────┬───────┘
            │                  │                   │
            ▼                  ▼                   ▼
    ┌───────────────┐  ┌───────────────┐  ┌───────────────┐
    │  Frontend     │  │  Backend      │  │  Admin        │
    │  Pods         │  │  Pods         │  │  Pods         │
    │  (nginx)      │  │  (api)        │  │  (dashboard)  │
    └───────────────┘  └───────┬───────┘  └───────────────┘
                               │
                    Network Policy allows
                               │
                               ▼
                    ┌───────────────────┐
                    │  database-svc     │
                    │  (ClusterIP)      │
                    └───────────────────┘
                               │
                               ▼
                    ┌───────────────────┐
                    │  Database Pod     │
                    │  + PVC            │
                    └───────────────────┘
```

### Key Concepts Recap

| Component | Purpose | When to Use |
|-----------|---------|-------------|
| **Service Discovery** | Pods find each other via DNS | Always for pod-to-pod |
| **Ingress** | External HTTP/S access with routing | Production web apps |
| **Network Policy** | Firewall rules between pods | Security requirements |
| **kubectl** | Manage everything | Always |

### Key Takeaways

1. **Service Discovery** - Use DNS names (`service-name.namespace`), not IPs
2. **Ingress** - One entry point, smart routing by path/host
3. **Network Policies** - Default deny, then allow specific traffic
4. **kubectl** - Master these: `get`, `describe`, `logs`, `exec`, `apply`
5. **Troubleshooting** - Always check: pod status → describe → logs → events
