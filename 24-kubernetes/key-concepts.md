# Kubernetes Key Concepts for Application Developers

## Overview

This document covers essential Kubernetes (K8s) concepts for deploying and managing containerized applications at scale. Kubernetes is an open-source container orchestration platform that automates deployment, scaling, and management of containerized applications across clusters of hosts.

---

## 1. Kubernetes Architecture

### Why It Matters
- Understanding architecture helps troubleshoot issues
- Know where workloads run and how they're managed
- Foundation for designing scalable applications
- Essential for deployment strategies

### Key Components

| Component | Type | Purpose |
|-----------|------|---------|
| Control Plane | Master | Manage cluster state |
| kubelet | Node | Run containers |
| kube-proxy | Node | Network routing |
| etcd | Data store | Cluster state storage |
| API Server | Control plane | Central management |
| Scheduler | Control plane | Pod placement |
| Controller Manager | Control plane | Maintain desired state |

### Architecture Diagram
```
┌─────────────── Control Plane (Master) ───────────────┐
│                                                       │
│  ┌──────────────┐  ┌────────────┐  ┌──────────────┐ │
│  │  API Server  │  │  Scheduler │  │  Controller  │ │
│  └──────┬───────┘  └─────┬──────┘  └──────┬───────┘ │
│         │                │                 │         │
│         └────────────────┴─────────────────┘         │
│                          │                           │
│                    ┌─────▼─────┐                     │
│                    │   etcd    │                     │
│                    └───────────┘                     │
└───────────────────────────────────────────────────────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
┌───────▼────────┐ ┌──────▼──────┐ ┌───────▼────────┐
│   Worker Node  │ │ Worker Node │ │  Worker Node   │
│                │ │             │ │                │
│ ┌────────────┐ │ │             │ │                │
│ │  kubelet   │ │ │             │ │                │
│ ├────────────┤ │ │             │ │                │
│ │kube-proxy  │ │ │             │ │                │
│ ├────────────┤ │ │             │ │                │
│ │ Container  │ │ │             │ │                │
│ │  Runtime   │ │ │             │ │                │
│ │  (Docker)  │ │ │             │ │                │
│ └────────────┘ │ │             │ │                │
│                │ │             │ │                │
│  ┌───┐  ┌───┐ │ │  ┌───┐      │ │  ┌───┐  ┌───┐ │
│  │Pod│  │Pod│ │ │  │Pod│      │ │  │Pod│  │Pod│ │
│  └───┘  └───┘ │ │  └───┘      │ │  └───┘  └───┘ │
└────────────────┘ └─────────────┘ └────────────────┘
```

---

## 2. Pods - Smallest Deployable Units

### Why It Matters
- Pods are the basic execution unit in Kubernetes
- One or more containers sharing network and storage
- Understanding pods is essential for deployment
- Foundation for all higher-level abstractions

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| Pod | Group of containers | App + sidecar |
| Container | Application process | Node.js app |
| Init Container | Runs before main | Database migration |
| Namespace | Logical cluster partition | dev, staging, prod |
| Label | Key-value metadata | app=frontend |
| Annotation | Non-identifying metadata | build-date=2024 |

### Simple Pod YAML
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx-pod
  labels:
    app: nginx
    environment: dev
spec:
  containers:
  - name: nginx
    image: nginx:1.24
    ports:
    - containerPort: 80
    resources:
      requests:
        memory: "64Mi"
        cpu: "250m"
      limits:
        memory: "128Mi"
        cpu: "500m"
```

### Pod with Multiple Containers
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: app-with-sidecar
spec:
  containers:
  # Main application
  - name: app
    image: myapp:1.0
    ports:
    - containerPort: 8080
    volumeMounts:
    - name: shared-data
      mountPath: /app/data

  # Sidecar for logging
  - name: log-collector
    image: fluentd:latest
    volumeMounts:
    - name: shared-data
      mountPath: /data

  volumes:
  - name: shared-data
    emptyDir: {}
```

### Pod with Init Container
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: app-with-init
spec:
  initContainers:
  # Run database migrations before app starts
  - name: db-migrate
    image: myapp:1.0
    command: ['npm', 'run', 'migrate']
    env:
    - name: DATABASE_URL
      value: postgres://db:5432/mydb

  containers:
  - name: app
    image: myapp:1.0
    ports:
    - containerPort: 3000
```

### Pod Management Commands
```bash
# Create pod from YAML
kubectl apply -f pod.yaml

# Get pods
kubectl get pods
kubectl get pods -o wide
kubectl get pods -n namespace
kubectl get pods --all-namespaces

# Describe pod (detailed info)
kubectl describe pod pod-name

# View logs
kubectl logs pod-name
kubectl logs pod-name -c container-name  # Specific container
kubectl logs -f pod-name  # Follow logs
kubectl logs --tail=100 pod-name  # Last 100 lines

# Execute command in pod
kubectl exec pod-name -- ls /app
kubectl exec -it pod-name -- bash
kubectl exec -it pod-name -c container-name -- sh

# Delete pod
kubectl delete pod pod-name
kubectl delete -f pod.yaml

# Port forwarding
kubectl port-forward pod-name 8080:80
```

---

## 3. Deployments - Declarative Updates

### Why It Matters
- Manage pod replicas and updates
- Rolling updates with zero downtime
- Rollback capabilities
- Self-healing and scaling

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Deployment | Manages ReplicaSets | Deploy and update apps |
| ReplicaSet | Maintains pod replicas | High availability |
| Replicas | Number of pod copies | Scaling |
| Rolling Update | Gradual replacement | Zero downtime |
| Rollback | Revert to previous version | Recovery |

### Basic Deployment
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  labels:
    app: nginx
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
        image: nginx:1.24
        ports:
        - containerPort: 80
```

### Production-Ready Deployment
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-deployment
  namespace: production
  labels:
    app: myapp
    version: v1.0.0
spec:
  replicas: 3
  revisionHistoryLimit: 10

  selector:
    matchLabels:
      app: myapp

  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1        # How many pods above replicas
      maxUnavailable: 1  # How many pods can be unavailable

  template:
    metadata:
      labels:
        app: myapp
        version: v1.0.0
    spec:
      containers:
      - name: myapp
        image: myapp:1.0.0
        imagePullPolicy: Always

        ports:
        - containerPort: 3000
          name: http

        env:
        - name: NODE_ENV
          value: "production"
        - name: DATABASE_URL
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: url

        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"

        livenessProbe:
          httpGet:
            path: /health
            port: 3000
          initialDelaySeconds: 30
          periodSeconds: 10
          timeoutSeconds: 5
          failureThreshold: 3

        readinessProbe:
          httpGet:
            path: /ready
            port: 3000
          initialDelaySeconds: 10
          periodSeconds: 5
          timeoutSeconds: 3
          failureThreshold: 3

        volumeMounts:
        - name: config
          mountPath: /app/config
          readOnly: true

      volumes:
      - name: config
        configMap:
          name: app-config
```

### Deployment Commands
```bash
# Create deployment
kubectl apply -f deployment.yaml
kubectl create deployment nginx --image=nginx:1.24 --replicas=3

# Get deployments
kubectl get deployments
kubectl get deploy -o wide

# Describe deployment
kubectl describe deployment nginx-deployment

# Scale deployment
kubectl scale deployment nginx-deployment --replicas=5

# Update image (rolling update)
kubectl set image deployment/nginx-deployment nginx=nginx:1.25

# Check rollout status
kubectl rollout status deployment/nginx-deployment

# View rollout history
kubectl rollout history deployment/nginx-deployment

# Rollback to previous version
kubectl rollout undo deployment/nginx-deployment

# Rollback to specific revision
kubectl rollout undo deployment/nginx-deployment --to-revision=2

# Pause rollout
kubectl rollout pause deployment/nginx-deployment

# Resume rollout
kubectl rollout resume deployment/nginx-deployment

# Delete deployment
kubectl delete deployment nginx-deployment
```

---

## 4. Services - Network Access to Pods

### Why It Matters
- Pods are ephemeral with changing IP addresses
- Services provide stable networking
- Load balancing across pod replicas
- Service discovery within cluster

### Service Types

| Type | Description | Use Case |
|------|-------------|----------|
| ClusterIP | Internal cluster IP (default) | Inter-service communication |
| NodePort | Expose on each node's IP | Development/testing |
| LoadBalancer | External load balancer | Production external access |
| ExternalName | DNS CNAME record | External service proxy |

### ClusterIP Service (Internal)
```yaml
apiVersion: v1
kind: Service
metadata:
  name: backend-service
spec:
  type: ClusterIP  # Default, can be omitted
  selector:
    app: backend
  ports:
  - protocol: TCP
    port: 80        # Service port
    targetPort: 8080  # Container port
```

### NodePort Service (External Access)
```yaml
apiVersion: v1
kind: Service
metadata:
  name: frontend-service
spec:
  type: NodePort
  selector:
    app: frontend
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
    nodePort: 30080  # Optional, will auto-assign if not specified
```

### LoadBalancer Service (Cloud Provider)
```yaml
apiVersion: v1
kind: Service
metadata:
  name: web-service
spec:
  type: LoadBalancer
  selector:
    app: web
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
```

### Headless Service (Direct Pod Access)
```yaml
apiVersion: v1
kind: Service
metadata:
  name: database-service
spec:
  clusterIP: None  # Headless
  selector:
    app: database
  ports:
  - protocol: TCP
    port: 5432
    targetPort: 5432
```

### Service Commands
```bash
# Create service
kubectl apply -f service.yaml
kubectl expose deployment nginx --port=80 --target-port=8080

# Get services
kubectl get services
kubectl get svc

# Describe service
kubectl describe service backend-service

# Access service
kubectl port-forward service/backend-service 8080:80

# Delete service
kubectl delete service backend-service
```

### Service Discovery
```yaml
# Pods can access services by name
apiVersion: v1
kind: Pod
metadata:
  name: client
spec:
  containers:
  - name: client
    image: curlimages/curl
    command: ['sh', '-c', 'while true; do curl http://backend-service; sleep 5; done']
    # DNS resolves to: backend-service.namespace.svc.cluster.local
```

---

## 5. ConfigMaps & Secrets - Configuration Management

### Why It Matters
- Separate configuration from code
- Manage environment-specific settings
- Secure sensitive data
- Update configuration without rebuilding images

### Key Concepts

| Resource | Purpose | Use Case |
|----------|---------|----------|
| ConfigMap | Non-sensitive configuration | App settings, config files |
| Secret | Sensitive data (base64 encoded) | Passwords, API keys, certificates |

### ConfigMap Examples

**From Literal Values:**
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  DATABASE_HOST: "postgres.default.svc.cluster.local"
  DATABASE_PORT: "5432"
  LOG_LEVEL: "info"
  FEATURE_FLAG: "true"
```

**From File Content:**
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: nginx-config
data:
  nginx.conf: |
    server {
      listen 80;
      server_name example.com;

      location / {
        proxy_pass http://backend:8080;
        proxy_set_header Host $host;
      }
    }
```

**Create ConfigMap from Command:**
```bash
# From literal values
kubectl create configmap app-config \
  --from-literal=DATABASE_HOST=postgres \
  --from-literal=LOG_LEVEL=debug

# From file
kubectl create configmap nginx-config --from-file=nginx.conf

# From directory
kubectl create configmap app-configs --from-file=./configs/
```

### Using ConfigMap in Pod
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: app-pod
spec:
  containers:
  - name: app
    image: myapp:1.0

    # Method 1: Environment variables from ConfigMap
    env:
    - name: DATABASE_HOST
      valueFrom:
        configMapKeyRef:
          name: app-config
          key: DATABASE_HOST

    # Method 2: All keys as environment variables
    envFrom:
    - configMapRef:
        name: app-config

    # Method 3: Mount as volume (file)
    volumeMounts:
    - name: config-volume
      mountPath: /etc/config

  volumes:
  - name: config-volume
    configMap:
      name: nginx-config
```

### Secrets Examples

**Generic Secret:**
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: db-secret
type: Opaque
data:
  username: dXNlcm5hbWU=  # base64 encoded
  password: cGFzc3dvcmQ=  # base64 encoded
```

**Create Secret from Command:**
```bash
# From literal
kubectl create secret generic db-secret \
  --from-literal=username=myuser \
  --from-literal=password=mypassword

# From file
kubectl create secret generic ssh-key-secret \
  --from-file=ssh-privatekey=~/.ssh/id_rsa

# Docker registry secret
kubectl create secret docker-registry regcred \
  --docker-server=registry.example.com \
  --docker-username=user \
  --docker-password=pass \
  --docker-email=user@example.com

# TLS secret
kubectl create secret tls tls-secret \
  --cert=tls.crt \
  --key=tls.key
```

### Using Secrets in Pod
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: app-pod
spec:
  containers:
  - name: app
    image: myapp:1.0

    # Environment variables from Secret
    env:
    - name: DB_USERNAME
      valueFrom:
        secretKeyRef:
          name: db-secret
          key: username
    - name: DB_PASSWORD
      valueFrom:
        secretKeyRef:
          name: db-secret
          key: password

    # Mount as volume
    volumeMounts:
    - name: secret-volume
      mountPath: /etc/secrets
      readOnly: true

  volumes:
  - name: secret-volume
    secret:
      secretName: db-secret

  # Use image pull secret
  imagePullSecrets:
  - name: regcred
```

---

## 6. Ingress - HTTP(S) Routing

### Why It Matters
- Single entry point for HTTP/HTTPS traffic
- Path-based and host-based routing
- TLS/SSL termination
- Replaces multiple LoadBalancer services

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Ingress | Routing rules | HTTP(S) traffic management |
| Ingress Controller | Implementation | nginx, Traefik, etc. |
| Path-based routing | /api, /admin | Route by URL path |
| Host-based routing | api.example.com | Route by domain |

### Basic Ingress
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: basic-ingress
spec:
  rules:
  - host: example.com
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

### Advanced Ingress with Multiple Routes
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: app-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    cert-manager.io/cluster-issuer: letsencrypt-prod
spec:
  ingressClassName: nginx

  tls:
  - hosts:
    - example.com
    - api.example.com
    secretName: tls-secret

  rules:
  # Frontend
  - host: example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: frontend-service
            port:
              number: 80

  # API
  - host: api.example.com
    http:
      paths:
      - path: /v1
        pathType: Prefix
        backend:
          service:
            name: api-v1-service
            port:
              number: 8080

      - path: /v2
        pathType: Prefix
        backend:
          service:
            name: api-v2-service
            port:
              number: 8080

  # Admin panel
  - host: admin.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: admin-service
            port:
              number: 3000
```

---

## 7. Namespaces - Resource Isolation

### Why It Matters
- Logical cluster partitioning
- Resource isolation between teams/environments
- Apply different policies per namespace
- Organize resources

### Common Namespace Strategy
```bash
# Default namespaces
default          # Default namespace
kube-system      # Kubernetes system components
kube-public      # Public resources
kube-node-lease  # Node heartbeats

# Custom namespaces
dev              # Development environment
staging          # Staging environment
production       # Production environment
```

### Namespace YAML
```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: production
  labels:
    environment: production
    team: platform
```

### Namespace Commands
```bash
# Create namespace
kubectl create namespace dev
kubectl apply -f namespace.yaml

# List namespaces
kubectl get namespaces
kubectl get ns

# Set default namespace
kubectl config set-context --current --namespace=dev

# Get resources in namespace
kubectl get pods -n production
kubectl get all -n staging

# Delete namespace (deletes all resources in it)
kubectl delete namespace dev
```

### Deploying to Namespace
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
  namespace: production  # Specify namespace
spec:
  replicas: 3
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

## 8. kubectl - Kubernetes CLI

### Why It Matters
- Primary tool for interacting with Kubernetes
- Manage all Kubernetes resources
- Debug and troubleshoot
- Automate deployments

### Essential Commands

**Cluster Info:**
```bash
# Cluster information
kubectl cluster-info
kubectl version
kubectl get nodes

# Context management
kubectl config get-contexts
kubectl config current-context
kubectl config use-context context-name
```

**Resource Management:**
```bash
# Get resources
kubectl get pods
kubectl get deployments
kubectl get services
kubectl get all  # All resources in namespace

# Describe resource
kubectl describe pod pod-name
kubectl describe deployment deploy-name

# Create/Apply resources
kubectl create -f resource.yaml
kubectl apply -f resource.yaml
kubectl apply -f ./manifests/  # Multiple files

# Delete resources
kubectl delete pod pod-name
kubectl delete -f resource.yaml
kubectl delete deployment --all
```

**Debugging:**
```bash
# Logs
kubectl logs pod-name
kubectl logs -f pod-name  # Follow
kubectl logs pod-name --previous  # Previous instance
kubectl logs deployment/app-deployment

# Execute commands
kubectl exec -it pod-name -- bash
kubectl exec pod-name -- env
kubectl exec pod-name -c container-name -- ls

# Port forwarding
kubectl port-forward pod/pod-name 8080:80
kubectl port-forward service/service-name 8080:80

# Copy files
kubectl cp pod-name:/path/to/file ./local-file
kubectl cp ./local-file pod-name:/path/to/file

# Top (resource usage)
kubectl top nodes
kubectl top pods
```

**Advanced Commands:**
```bash
# Edit resource
kubectl edit deployment app-deployment

# Patch resource
kubectl patch deployment app-deployment -p '{"spec":{"replicas":5}}'

# Label resources
kubectl label pod pod-name environment=production
kubectl label pod pod-name environment-  # Remove label

# Annotate resources
kubectl annotate pod pod-name description="My app"

# Dry run
kubectl apply -f deployment.yaml --dry-run=client
kubectl create deployment nginx --image=nginx --dry-run=client -o yaml

# Output formats
kubectl get pods -o wide
kubectl get pods -o yaml
kubectl get pods -o json
kubectl get pods -o jsonpath='{.items[0].metadata.name}'

# Watch resources
kubectl get pods -w
```

---

## Quick Reference Card

### Essential Commands
```bash
# Cluster
kubectl cluster-info
kubectl get nodes

# Pods
kubectl get pods
kubectl logs -f pod-name
kubectl exec -it pod-name -- bash
kubectl describe pod pod-name

# Deployments
kubectl apply -f deployment.yaml
kubectl get deployments
kubectl scale deployment name --replicas=5
kubectl rollout status deployment/name
kubectl rollout undo deployment/name

# Services
kubectl get services
kubectl expose deployment name --port=80 --type=LoadBalancer

# ConfigMaps & Secrets
kubectl create configmap name --from-literal=key=value
kubectl create secret generic name --from-literal=key=value
kubectl get configmaps
kubectl get secrets

# Namespaces
kubectl get ns
kubectl create namespace dev
kubectl config set-context --current --namespace=dev

# Debugging
kubectl logs pod-name
kubectl exec -it pod-name -- sh
kubectl port-forward pod-name 8080:80
kubectl describe pod pod-name
```

### Complete Application Example
```yaml
# namespace.yaml
apiVersion: v1
kind: Namespace
metadata:
  name: myapp

---
# configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
  namespace: myapp
data:
  API_URL: "http://api-service"

---
# secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: app-secret
  namespace: myapp
type: Opaque
data:
  DB_PASSWORD: cGFzc3dvcmQ=

---
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: app-deployment
  namespace: myapp
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: app
        image: myapp:1.0
        ports:
        - containerPort: 8080
        envFrom:
        - configMapRef:
            name: app-config
        - secretRef:
            name: app-secret

---
# service.yaml
apiVersion: v1
kind: Service
metadata:
  name: app-service
  namespace: myapp
spec:
  selector:
    app: myapp
  ports:
  - port: 80
    targetPort: 8080
  type: LoadBalancer
```

```bash
# Deploy complete application
kubectl apply -f .
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand Kubernetes architecture and components
- [ ] Create and manage Pods
- [ ] Deploy applications using Deployments
- [ ] Expose applications using Services
- [ ] Manage configuration with ConfigMaps
- [ ] Handle secrets securely
- [ ] Configure HTTP routing with Ingress
- [ ] Use Namespaces for resource isolation
- [ ] Perform rolling updates and rollbacks
- [ ] Scale applications horizontally
- [ ] Debug running containers
- [ ] Use kubectl effectively
- [ ] Write production-ready YAML manifests
- [ ] Implement health checks (liveness/readiness probes)

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 25: Google Cloud Platform](../25-gcp/) - Deploy Kubernetes on GKE
- Practice deploying multi-tier applications
- Learn Helm for package management
- Explore service meshes (Istio, Linkerd)
- Study StatefulSets for stateful applications
- Implement monitoring with Prometheus and Grafana
