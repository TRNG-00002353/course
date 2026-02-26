# Services and Configuration

## Continuing Our Scenario

We deployed our frontend application with 3 replicas. But there's a problem:

```
┌─────────────────────────────────────────────────────────┐
│  Frontend Deployment (3 pods)                            │
│                                                          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐      │
│  │ Pod 1       │  │ Pod 2       │  │ Pod 3       │      │
│  │ IP: 10.1.1.5│  │ IP: 10.1.2.8│  │ IP: 10.1.3.2│      │
│  └─────────────┘  └─────────────┘  └─────────────┘      │
│         ▲                                                │
│         │                                                │
│    How do users                                          │
│    connect here?    Pod IPs change when pods restart!    │
└─────────────────────────────────────────────────────────┘
```

**Problems:**
1. Pod IPs are internal and change frequently
2. Which pod should handle a request? (load balancing)
3. How does the backend find the database?

**Solution: Services**

---

## Services: Stable Networking for Pods

A **Service** provides a stable IP address and DNS name that routes traffic to pods.

```
                         ┌─────────────────┐
    Users ─────────────► │    Service      │
                         │  IP: 10.96.1.50 │
                         │  (never changes)│
                         └────────┬────────┘
                                  │ load balances
                    ┌─────────────┼─────────────┐
                    ▼             ▼             ▼
              ┌─────────┐   ┌─────────┐   ┌─────────┐
              │  Pod 1  │   │  Pod 2  │   │  Pod 3  │
              │10.1.1.5 │   │10.1.2.8 │   │10.1.3.2 │
              └─────────┘   └─────────┘   └─────────┘
```

**Key insight:** Services find pods using **labels**, not IP addresses.

---

## How Services Find Pods

Services use **label selectors** to find pods. This is crucial to understand:

```
┌─────────────────────────────────────────────────────────────┐
│                  SERVICE SELECTION PROCESS                   │
│                                                              │
│  Service YAML:                    Pod YAML:                  │
│  ┌─────────────────────┐         ┌─────────────────────┐    │
│  │ selector:           │         │ labels:             │    │
│  │   app: backend      │────────►│   app: backend      │ ✓  │
│  │   tier: api         │         │   tier: api         │    │
│  └─────────────────────┘         └─────────────────────┘    │
│           │                                                  │
│           │                      ┌─────────────────────┐    │
│           └─────────────────────►│ labels:             │    │
│                                  │   app: backend      │ ✗  │
│                                  │   tier: database    │    │
│                                  └─────────────────────┘    │
│                                                              │
│  ALL selector labels must match for a pod to receive traffic│
└─────────────────────────────────────────────────────────────┘
```

**Common mistake:** Service not routing traffic? Check if labels match exactly!

---

## Service Types

| Type | Access From | Use Case |
|------|-------------|----------|
| **ClusterIP** | Inside cluster only | Backend services, databases |
| **NodePort** | Outside via node IP:port | Development, testing |
| **LoadBalancer** | External load balancer | Production external access |

### Choosing the Right Service Type

```
┌─────────────────────────────────────────────────────────────┐
│              WHICH SERVICE TYPE DO I NEED?                   │
│                                                              │
│  ┌─────────────────────────────────────────────────────────┐│
│  │ Does it need to be accessed from outside the cluster?   ││
│  └─────────────────────────────┬───────────────────────────┘│
│                                │                             │
│              NO ◄──────────────┴──────────────► YES          │
│               │                                   │          │
│               ▼                                   ▼          │
│        ┌────────────┐               ┌─────────────────────┐ │
│        │ ClusterIP  │               │ Cloud environment?  │ │
│        │            │               └──────────┬──────────┘ │
│        │ - Databases│                          │            │
│        │ - Internal │            YES ◄─────────┴────► NO    │
│        │   APIs     │             │                    │    │
│        └────────────┘             ▼                    ▼    │
│                            ┌────────────┐      ┌──────────┐ │
│                            │LoadBalancer│      │ NodePort │ │
│                            │            │      │          │ │
│                            │ Production │      │ Dev/Test │ │
│                            │ websites   │      │ Local    │ │
│                            └────────────┘      └──────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### ClusterIP (Default) - Internal Communication

Use when pods inside the cluster need to reach this service.

```yaml
apiVersion: v1
kind: Service
metadata:
  name: backend-service
spec:
  type: ClusterIP              # Default, can be omitted
  selector:
    app: backend               # Route to pods with this label
  ports:
  - port: 80                   # Service listens on this port
    targetPort: 8080           # Forward to this port on pods
```

```
Frontend Pod                    Backend Service              Backend Pods
┌───────────┐                  ┌──────────────┐            ┌───────────┐
│           │ ──► port 80 ───► │   ClusterIP  │ ─► 8080 ─► │ app:backend│
│           │                  │  10.96.1.50  │            └───────────┘
└───────────┘                  └──────────────┘
```

### NodePort - External Access for Development

Exposes the service on each node's IP at a static port (30000-32767).

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
  - port: 80
    targetPort: 8080
    nodePort: 30080            # Access via <NodeIP>:30080
```

```
External User                     Node                        Pod
┌───────────┐                  ┌───────────┐              ┌───────────┐
│ Browser   │ ─► :30080 ─────► │ Worker    │ ──► 8080 ──►│ frontend  │
│           │                  │ Node IP   │              │           │
└───────────┘                  └───────────┘              └───────────┘

Access: http://192.168.1.100:30080
```

### LoadBalancer - Production External Access

Creates a cloud load balancer (AWS ELB, GCP LB, Azure LB).

```yaml
apiVersion: v1
kind: Service
metadata:
  name: frontend-service
spec:
  type: LoadBalancer
  selector:
    app: frontend
  ports:
  - port: 80
    targetPort: 8080
```

```
Internet                    Cloud Load Balancer              Pods
┌───────────┐              ┌──────────────────┐          ┌───────────┐
│   Users   │ ──► :80 ───► │  External IP     │ ───────► │ frontend  │
│           │              │  (cloud assigned)│          │           │
└───────────┘              └──────────────────┘          └───────────┘

Access: http://34.120.45.67 (assigned by cloud provider)
```

### Service Commands

```bash
# Create service imperatively
kubectl expose deployment frontend --port=80 --type=ClusterIP

# List services
kubectl get services
kubectl get svc

# See service details
kubectl describe service frontend-service

# Delete service
kubectl delete service frontend-service
```

---

## Our Scenario: Connecting Frontend to Backend

Let's make our frontend talk to the backend:

```yaml
# backend-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: backend
  namespace: production
spec:
  replicas: 2
  selector:
    matchLabels:
      app: backend
  template:
    metadata:
      labels:
        app: backend
    spec:
      containers:
      - name: api
        image: myapp/backend:1.0
        ports:
        - containerPort: 8080
---
# backend-service.yaml
apiVersion: v1
kind: Service
metadata:
  name: backend-service
  namespace: production
spec:
  selector:
    app: backend
  ports:
  - port: 8080
    targetPort: 8080
```

Now frontend can reach backend at: `http://backend-service:8080`

**But how does the backend know the database password?** We need configuration management.

---

## ConfigMaps: Application Configuration

### The Problem

Hardcoding configuration in container images is bad:
- Different settings for dev/staging/prod
- Must rebuild image for every config change
- Secrets might leak into image

### The Solution: ConfigMaps

**ConfigMaps** store non-sensitive configuration as key-value pairs.

### ConfigMap vs Environment Variables vs Command Args

| Method | Best For | Pros | Cons |
|--------|----------|------|------|
| **ConfigMap** | Multiple config values, shared across pods | Reusable, easy to update | Extra object to manage |
| **env in Pod spec** | Simple, pod-specific values | Direct, no extra objects | Hardcoded in YAML |
| **Command args** | Overriding container defaults | Simple for single values | Limited flexibility |

**Rule of thumb:** Use ConfigMaps for anything you might change without redeploying.

```
┌─────────────────────────────────────────────────────────┐
│                     ConfigMap                            │
│  ┌─────────────────────────────────────────────────┐    │
│  │  DB_HOST: "postgres-service"                     │    │
│  │  LOG_LEVEL: "info"                               │    │
│  │  MAX_CONNECTIONS: "100"                          │    │
│  └─────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────┘
                           │
                           ▼ injected as
              ┌────────────────────────┐
              │         Pod            │
              │  env:                  │
              │    DB_HOST=postgres... │
              │    LOG_LEVEL=info      │
              └────────────────────────┘
```

### Creating ConfigMaps

```bash
# From command line
kubectl create configmap app-config \
  --from-literal=DB_HOST=postgres-service \
  --from-literal=LOG_LEVEL=info

# From file
kubectl create configmap app-config --from-file=app.properties
```

### ConfigMap YAML

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  DB_HOST: "postgres-service"
  DB_PORT: "5432"
  LOG_LEVEL: "info"
```

### Using ConfigMap in Pods

**Option 1: All values as environment variables**

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: backend
spec:
  containers:
  - name: api
    image: myapp/backend:1.0
    envFrom:                       # Inject ALL keys from ConfigMap
    - configMapRef:
        name: app-config
```

**Option 2: Specific values**

```yaml
spec:
  containers:
  - name: api
    image: myapp/backend:1.0
    env:
    - name: DATABASE_HOST          # Environment variable name
      valueFrom:
        configMapKeyRef:
          name: app-config         # ConfigMap name
          key: DB_HOST             # Key in ConfigMap
```

**Option 3: Mount as files**

```yaml
spec:
  containers:
  - name: api
    image: myapp/backend:1.0
    volumeMounts:
    - name: config
      mountPath: /etc/config       # Files appear here
  volumes:
  - name: config
    configMap:
      name: app-config
```

---

## Secrets: Sensitive Data

### Why Secrets?

ConfigMaps are stored in plain text. For sensitive data like passwords and API keys, use **Secrets**.

**Secrets provide:**
- Base64 encoding (not encryption, but separation)
- Can be encrypted at rest in etcd
- Access can be restricted via RBAC

### Important Security Considerations

```
┌─────────────────────────────────────────────────────────────┐
│           SECRETS ARE NOT ENCRYPTED BY DEFAULT!              │
│                                                              │
│  Base64 encoding ≠ Encryption                               │
│                                                              │
│  echo "password123" | base64  →  cGFzc3dvcmQxMjM=           │
│  echo "cGFzc3dvcmQxMjM=" | base64 -d  →  password123        │
│                                                              │
│  Anyone with cluster access can decode secrets!              │
│                                                              │
│  For true security:                                          │
│  - Enable etcd encryption at rest                            │
│  - Use RBAC to limit secret access                           │
│  - Consider external secret managers (Vault, AWS Secrets)    │
└─────────────────────────────────────────────────────────────┘
```

### Secret Types

| Type | Use Case | Example |
|------|----------|---------|
| **Opaque** | Generic secrets (default) | Passwords, API keys |
| **kubernetes.io/tls** | TLS certificates | HTTPS certs for Ingress |
| **kubernetes.io/dockerconfigjson** | Docker registry credentials | Pulling from private registry |
| **kubernetes.io/basic-auth** | Basic authentication | Username/password |

### Creating Secrets

```bash
kubectl create secret generic db-secret \
  --from-literal=username=admin \
  --from-literal=password=secret123
```

### Secret YAML

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: db-secret
type: Opaque
stringData:                        # Use stringData for plain text
  username: admin
  password: secret123
```

### Using Secrets in Pods

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: backend
spec:
  containers:
  - name: api
    image: myapp/backend:1.0
    env:
    - name: DB_USER
      valueFrom:
        secretKeyRef:
          name: db-secret
          key: username
    - name: DB_PASS
      valueFrom:
        secretKeyRef:
          name: db-secret
          key: password
```

### Secret Commands

```bash
kubectl get secrets
kubectl describe secret db-secret

# Decode a secret value
kubectl get secret db-secret -o jsonpath='{.data.password}' | base64 -d
```

---

## Storage: Persisting Data

### The Problem

Containers are ephemeral - when a pod restarts, all data inside is lost.

```
Pod running                    Pod crashes                  New Pod
┌───────────────┐             ┌───────────────┐           ┌───────────────┐
│ Database      │             │               │           │ Database      │
│ ┌───────────┐ │   crash!    │      X        │  restart  │ ┌───────────┐ │
│ │ user data │ │  ────────►  │               │ ────────► │ │  empty!   │ │
│ └───────────┘ │             │               │           │ └───────────┘ │
└───────────────┘             └───────────────┘           └───────────────┘
```

For databases and stateful apps, we need **persistent storage**.

### Volume Types

| Type | Lifetime | Use Case |
|------|----------|----------|
| **emptyDir** | Pod lifetime | Temp files, cache |
| **hostPath** | Node lifetime | Dev/testing only |
| **PersistentVolumeClaim** | Beyond pod | Production data |

### emptyDir: Temporary Storage

Data survives container restarts, but deleted when pod is deleted.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: cache-pod
spec:
  containers:
  - name: app
    image: myapp
    volumeMounts:
    - name: cache
      mountPath: /cache
  volumes:
  - name: cache
    emptyDir: {}
```

### Persistent Volumes (PV) and Claims (PVC)

For data that must survive pod deletion, use the PV/PVC pattern:

```
┌─────────────────────────────────────────────────────────────────┐
│                                                                  │
│   Admin creates                User creates              User's Pod
│   ┌─────────────┐             ┌─────────────┐          ┌─────────┐
│   │ Persistent  │◄── binds ──►│ Persistent  │◄─ uses ──│  Pod    │
│   │ Volume (PV) │             │ Volume Claim│          │         │
│   │ 100Gi       │             │ (PVC) 50Gi  │          │         │
│   └─────────────┘             └─────────────┘          └─────────┘
│         │                                                        │
│         ▼                                                        │
│   ┌─────────────┐                                                │
│   │ Actual      │  (AWS EBS, GCP Disk, NFS, etc.)               │
│   │ Storage     │                                                │
│   └─────────────┘                                                │
└─────────────────────────────────────────────────────────────────┘
```

**PersistentVolume (PV):** A piece of storage provisioned by admin
**PersistentVolumeClaim (PVC):** A request for storage by a user

### PV and PVC Example

```yaml
# PersistentVolume - admin creates this
apiVersion: v1
kind: PersistentVolume
metadata:
  name: postgres-pv
spec:
  storageClassName: manual
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce            # One pod can mount read-write
  hostPath:                    # For local dev only
    path: /mnt/data
---
# PersistentVolumeClaim - user creates this
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: postgres-pvc
spec:
  storageClassName: manual
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 5Gi
```

### Using PVC in a Pod

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: postgres
spec:
  containers:
  - name: postgres
    image: postgres:13
    volumeMounts:
    - name: data
      mountPath: /var/lib/postgresql/data
  volumes:
  - name: data
    persistentVolumeClaim:
      claimName: postgres-pvc
```

### Access Modes

| Mode | Description |
|------|-------------|
| **ReadWriteOnce (RWO)** | One node can mount read-write |
| **ReadOnlyMany (ROX)** | Many nodes can mount read-only |
| **ReadWriteMany (RWX)** | Many nodes can mount read-write |

### StorageClass: Dynamic Provisioning

In production, you don't manually create PVs. **StorageClass** enables automatic provisioning:

```
┌─────────────────────────────────────────────────────────────┐
│              DYNAMIC vs STATIC PROVISIONING                  │
│                                                              │
│  STATIC (Manual):                                            │
│  Admin creates PV ──► User creates PVC ──► PVC binds to PV  │
│  (tedious for many volumes)                                  │
│                                                              │
│  DYNAMIC (Automatic):                                        │
│  User creates PVC with StorageClass ──► PV auto-created!    │
│  (production standard)                                       │
└─────────────────────────────────────────────────────────────┘
```

```yaml
# StorageClass for AWS EBS
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: fast-storage
provisioner: ebs.csi.aws.com    # Cloud-specific provisioner
parameters:
  type: gp3                      # SSD storage
reclaimPolicy: Delete            # Delete volume when PVC deleted
volumeBindingMode: WaitForFirstConsumer
---
# PVC using the StorageClass
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: database-pvc
spec:
  storageClassName: fast-storage  # Reference StorageClass
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 20Gi
```

### Reclaim Policies

| Policy | What Happens When PVC Deleted | Use Case |
|--------|-------------------------------|----------|
| **Delete** | PV and underlying storage deleted | Dev/test environments |
| **Retain** | PV kept, must be manually cleaned | Production data you want to keep |

### Storage Commands

```bash
kubectl get pv                    # List PersistentVolumes
kubectl get pvc                   # List PersistentVolumeClaims
kubectl describe pvc postgres-pvc # PVC details
```

---

## Putting It Together: Complete Backend Setup

Let's configure our backend with all these concepts:

```yaml
# backend-config.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: backend-config
  namespace: production
data:
  DB_HOST: "postgres-service"
  DB_PORT: "5432"
  LOG_LEVEL: "info"
---
apiVersion: v1
kind: Secret
metadata:
  name: backend-secrets
  namespace: production
type: Opaque
stringData:
  DB_USER: "appuser"
  DB_PASSWORD: "supersecret123"
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: backend
  namespace: production
spec:
  replicas: 2
  selector:
    matchLabels:
      app: backend
  template:
    metadata:
      labels:
        app: backend
    spec:
      containers:
      - name: api
        image: myapp/backend:1.0
        ports:
        - containerPort: 8080
        envFrom:
        - configMapRef:
            name: backend-config
        env:
        - name: DB_USER
          valueFrom:
            secretKeyRef:
              name: backend-secrets
              key: DB_USER
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: backend-secrets
              key: DB_PASSWORD
---
apiVersion: v1
kind: Service
metadata:
  name: backend-service
  namespace: production
spec:
  selector:
    app: backend
  ports:
  - port: 8080
    targetPort: 8080
```

```bash
kubectl apply -f backend-config.yaml
kubectl get all -n production
```

**Now we have:**
- Frontend pods → talk to Backend via Service
- Backend pods → configured via ConfigMap and Secrets
- Database → persistent storage via PVC

**Next: How do external users reach our application? → Ingress**

---

## Summary

| Concept | Purpose | When to Use |
|---------|---------|-------------|
| **Service** | Stable network endpoint | Always - to expose pods |
| **ClusterIP** | Internal access | Backend services, databases |
| **NodePort** | External via node port | Development, testing |
| **LoadBalancer** | External via cloud LB | Production |
| **ConfigMap** | Non-sensitive config | App settings, feature flags |
| **Secret** | Sensitive data | Passwords, API keys, certs |
| **PVC** | Persistent storage | Databases, file uploads |

### How It All Connects

```
External Users
      │
      ▼
┌─────────────────┐
│ LoadBalancer    │
│ Service         │
└────────┬────────┘
         │
         ▼
┌─────────────────┐    ConfigMap     ┌─────────────────┐
│ Frontend Pods   │◄───────────────► │ app-config      │
└────────┬────────┘                  └─────────────────┘
         │
         ▼
┌─────────────────┐    Secret        ┌─────────────────┐
│ ClusterIP       │                  │ db-secret       │
│ Service         │                  └────────┬────────┘
└────────┬────────┘                           │
         │                                    │
         ▼                                    ▼
┌─────────────────┐                  ┌─────────────────┐
│ Backend Pods    │◄─────────────────│                 │
└────────┬────────┘                  └─────────────────┘
         │
         ▼
┌─────────────────┐    PVC           ┌─────────────────┐
│ Database Pod    │◄───────────────► │ postgres-pvc    │
└─────────────────┘                  └─────────────────┘
```

### Key Takeaways

1. **Services provide stable networking** - pods come and go, services stay
2. **Use the right Service type** - ClusterIP for internal, LoadBalancer for external
3. **Separate config from code** - ConfigMaps and Secrets
4. **Never put secrets in ConfigMaps** - use Secrets for sensitive data
5. **Use PVCs for stateful data** - data survives pod restarts
