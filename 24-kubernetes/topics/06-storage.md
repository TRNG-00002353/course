# Kubernetes Storage

## Overview

Kubernetes storage allows containers to persist data beyond the lifecycle of a pod. Understanding storage is essential because containers are ephemeral - when a container restarts, all data inside it is lost.

---

## Why Storage Matters

### The Problem

```
WITHOUT PERSISTENT STORAGE:

┌─────────────────────────────────────────────────────────────┐
│                                                              │
│  Pod Running             Pod Crashes              Pod Restarts
│  ┌─────────────┐        ┌─────────────┐        ┌─────────────┐
│  │ Container   │        │ Container   │        │ Container   │
│  │             │        │             │        │             │
│  │ Data: ABC   │   →    │   CRASH!    │   →    │ Data: ???   │
│  │             │        │   💥        │        │ (LOST!)     │
│  └─────────────┘        └─────────────┘        └─────────────┘
│                                                              │
│  All data stored inside the container is LOST on restart    │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### The Solution

```
WITH PERSISTENT STORAGE:

┌─────────────────────────────────────────────────────────────┐
│                                                              │
│  Pod Running             Pod Crashes              Pod Restarts
│  ┌─────────────┐        ┌─────────────┐        ┌─────────────┐
│  │ Container   │        │ Container   │        │ Container   │
│  │      │      │        │             │        │      │      │
│  │      ▼      │   →    │   CRASH!    │   →    │      ▼      │
│  │ ┌────────┐  │        │   💥        │        │ ┌────────┐  │
│  │ │ Volume │  │        │             │        │ │ Volume │  │
│  └─┴────────┴──┘        └─────────────┘        └─┴────────┴──┘
│        │                                              │
│        └──────────────── Data: ABC ──────────────────┘
│                                                              │
│  Data persists in the volume even when pod restarts         │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

---

## Storage Concepts

### Kubernetes Storage Hierarchy

```
┌─────────────────────────────────────────────────────────────┐
│                    STORAGE HIERARCHY                         │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│   STORAGE CLASS (StorageClass)                              │
│   └── Defines "type" of storage (fast SSD, slow HDD, etc.) │
│                                                              │
│   PERSISTENT VOLUME (PV)                                    │
│   └── Actual storage resource in the cluster                │
│                                                              │
│   PERSISTENT VOLUME CLAIM (PVC)                             │
│   └── Request for storage by a pod                          │
│                                                              │
│   VOLUME                                                     │
│   └── Mounted into pod/container                            │
│                                                              │
│                                                              │
│   Flow:  StorageClass → PV → PVC → Pod                      │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### Key Terms

| Term | Description |
|------|-------------|
| **Volume** | Directory accessible to containers in a pod |
| **Persistent Volume (PV)** | Cluster-wide storage resource |
| **Persistent Volume Claim (PVC)** | Request for storage by user |
| **Storage Class** | Describes "classes" of storage available |
| **Access Modes** | How the volume can be mounted (read/write) |

---

## Volumes

### What is a Volume?

A **Volume** is a directory that is accessible to containers in a pod. Unlike container storage, volumes can outlive container restarts (depending on volume type).

### Volume Types

| Type | Description | Data Persistence |
|------|-------------|------------------|
| **emptyDir** | Temporary storage, created with pod | Pod lifetime only |
| **hostPath** | Mounts directory from host node | Node lifetime |
| **configMap** | Mounts ConfigMap as files | ConfigMap lifetime |
| **secret** | Mounts Secret as files | Secret lifetime |
| **persistentVolumeClaim** | Claims persistent storage | Beyond pod lifetime |
| **nfs** | Network File System mount | Permanent |
| **cloud volumes** | AWS EBS, Azure Disk, GCP PD | Permanent |

---

## emptyDir Volume

### What is emptyDir?

An **emptyDir** volume is created when a pod is assigned to a node and exists as long as the pod runs on that node. It starts empty and is useful for temporary storage or sharing data between containers in the same pod.

**Key Points**:
- Created when pod starts
- Deleted when pod is removed from node
- Shared between containers in the same pod
- Stored on node's disk or memory (tmpfs)

### emptyDir Example

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: emptydir-pod
spec:
  containers:
  # Container 1: Writes data
  - name: writer
    image: busybox
    command: ['sh', '-c', 'echo "Hello from writer" > /data/message.txt && sleep 3600']
    volumeMounts:
    - name: shared-data
      mountPath: /data

  # Container 2: Reads data
  - name: reader
    image: busybox
    command: ['sh', '-c', 'sleep 10 && cat /data/message.txt && sleep 3600']
    volumeMounts:
    - name: shared-data
      mountPath: /data

  volumes:
  - name: shared-data
    emptyDir: {}
```

### emptyDir in Memory (tmpfs)

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: emptydir-memory-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: cache
      mountPath: /cache
  volumes:
  - name: cache
    emptyDir:
      medium: Memory        # Store in RAM (tmpfs)
      sizeLimit: 100Mi      # Limit size
```

### emptyDir Use Cases

| Use Case | Description |
|----------|-------------|
| **Scratch space** | Temporary calculations, sorting |
| **Checkpointing** | Crash recovery data |
| **Cache** | Temporary cache for web server |
| **Shared data** | Share files between containers in pod |
| **Build artifacts** | Temporary build outputs |

---

## hostPath Volume

### What is hostPath?

A **hostPath** volume mounts a file or directory from the host node's filesystem into the pod. The data persists even after the pod is deleted.

**Warning**: hostPath can pose security risks and is generally not recommended for production. Use PersistentVolumes instead.

### hostPath Example

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: hostpath-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: host-data
      mountPath: /usr/share/nginx/html
  volumes:
  - name: host-data
    hostPath:
      path: /var/www/html        # Path on the host
      type: DirectoryOrCreate    # Create if doesn't exist
```

### hostPath Types

| Type | Description |
|------|-------------|
| **""** (empty) | No checks, mount whatever exists |
| **DirectoryOrCreate** | Create directory if it doesn't exist |
| **Directory** | Directory must exist |
| **FileOrCreate** | Create file if it doesn't exist |
| **File** | File must exist |
| **Socket** | Unix socket must exist |
| **CharDevice** | Character device must exist |
| **BlockDevice** | Block device must exist |

### hostPath Caution

```
⚠️ WARNING: hostPath has limitations and risks:

1. Pod scheduled to different node = different data
2. Security risk - access to host filesystem
3. Not portable across nodes
4. Use PersistentVolumes for production!

GOOD FOR:
✓ Development/testing
✓ Single-node clusters (minikube)
✓ Accessing node-level files (logs, docker socket)

BAD FOR:
✗ Production multi-node clusters
✗ Data that needs to follow the pod
✗ Sensitive applications
```

---

## ConfigMap and Secret Volumes

### ConfigMap as Volume

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  app.properties: |
    database.host=localhost
    database.port=5432
  nginx.conf: |
    server {
      listen 80;
      location / {
        root /var/www;
      }
    }
---
apiVersion: v1
kind: Pod
metadata:
  name: configmap-volume-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: config-volume
      mountPath: /etc/config
      readOnly: true
  volumes:
  - name: config-volume
    configMap:
      name: app-config
```

**Result**: Files created in `/etc/config/`:
- `/etc/config/app.properties`
- `/etc/config/nginx.conf`

### Secret as Volume

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: db-credentials
type: Opaque
stringData:
  username: admin
  password: secretpassword
---
apiVersion: v1
kind: Pod
metadata:
  name: secret-volume-pod
spec:
  containers:
  - name: app
    image: myapp
    volumeMounts:
    - name: secret-volume
      mountPath: /etc/secrets
      readOnly: true
  volumes:
  - name: secret-volume
    secret:
      secretName: db-credentials
      defaultMode: 0400    # Read-only for owner
```

---

## Persistent Volumes (PV)

### What is a Persistent Volume?

A **Persistent Volume (PV)** is a piece of storage in the cluster that has been provisioned by an administrator or dynamically provisioned using Storage Classes. It's a cluster resource that exists independently of any individual pod.

### PV vs Regular Volume

```
REGULAR VOLUME (emptyDir, hostPath):
┌─────────────────────────────────────────────────────────────┐
│                                                              │
│   Pod                                                        │
│   ┌─────────────────────────────────────────┐               │
│   │ Container                                │               │
│   │    │                                     │               │
│   │    └──► Volume (tied to pod lifecycle)   │               │
│   └─────────────────────────────────────────┘               │
│                                                              │
│   Volume is PART of the Pod definition                      │
│   Volume lifecycle = Pod lifecycle                          │
│                                                              │
└─────────────────────────────────────────────────────────────┘

PERSISTENT VOLUME:
┌─────────────────────────────────────────────────────────────┐
│                                                              │
│   Cluster Storage Pool (Independent of Pods)                │
│   ┌─────────────────────────────────────────┐               │
│   │     PV-1        PV-2        PV-3        │               │
│   │   (10Gi)      (50Gi)      (100Gi)       │               │
│   └─────────────────────────────────────────┘               │
│        │                                                     │
│        │ Claim                                               │
│        ▼                                                     │
│   ┌─────────────────────────────────────────┐               │
│   │ Pod with PVC                             │               │
│   │ ┌─────────────────────────────────────┐ │               │
│   │ │ Container → PVC → PV-1              │ │               │
│   │ └─────────────────────────────────────┘ │               │
│   └─────────────────────────────────────────┘               │
│                                                              │
│   PV exists INDEPENDENTLY of Pods                           │
│   PV lifecycle managed separately                           │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### PV YAML Structure

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: my-pv
  labels:
    type: local
spec:
  storageClassName: manual          # Storage class name
  capacity:
    storage: 10Gi                   # Storage size
  accessModes:
    - ReadWriteOnce                 # Access mode
  persistentVolumeReclaimPolicy: Retain   # What happens when released
  hostPath:                         # Volume type (for local testing)
    path: /mnt/data
```

### Access Modes

| Mode | Abbreviation | Description |
|------|--------------|-------------|
| **ReadWriteOnce** | RWO | Single node can mount as read-write |
| **ReadOnlyMany** | ROX | Multiple nodes can mount as read-only |
| **ReadWriteMany** | RWX | Multiple nodes can mount as read-write |
| **ReadWriteOncePod** | RWOP | Single pod can mount as read-write |

**Note**: Not all storage types support all access modes.

### Reclaim Policies

| Policy | Description |
|--------|-------------|
| **Retain** | Keep PV and data after PVC is deleted (manual cleanup) |
| **Delete** | Delete PV and underlying storage when PVC is deleted |
| **Recycle** | (Deprecated) Basic scrub and make available again |

### PV Status/Phases

| Phase | Description |
|-------|-------------|
| **Available** | Ready to be bound to a PVC |
| **Bound** | Bound to a PVC |
| **Released** | PVC deleted, but PV not yet reclaimed |
| **Failed** | Automatic reclamation failed |

### PV Examples

#### Local Storage PV

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: local-pv
spec:
  storageClassName: local-storage
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce
  persistentVolumeReclaimPolicy: Retain
  local:
    path: /mnt/disks/ssd1
  nodeAffinity:
    required:
      nodeSelectorTerms:
      - matchExpressions:
        - key: kubernetes.io/hostname
          operator: In
          values:
          - node-1
```

#### NFS PV

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: nfs-pv
spec:
  storageClassName: nfs
  capacity:
    storage: 50Gi
  accessModes:
    - ReadWriteMany           # NFS supports multiple writers
  persistentVolumeReclaimPolicy: Retain
  nfs:
    server: nfs-server.example.com
    path: /exports/data
```

---

## Persistent Volume Claims (PVC)

### What is a PVC?

A **Persistent Volume Claim (PVC)** is a request for storage by a user. It's similar to how a pod consumes node resources - a PVC consumes PV resources.

### How PVC Works

```
┌─────────────────────────────────────────────────────────────┐
│                    PVC BINDING PROCESS                       │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  1. Admin creates PVs (or dynamic provisioning)             │
│     ┌──────────┐  ┌──────────┐  ┌──────────┐               │
│     │ PV: 5Gi  │  │ PV: 10Gi │  │ PV: 20Gi │               │
│     │ RWO      │  │ RWO      │  │ RWX      │               │
│     │ fast-ssd │  │ standard │  │ standard │               │
│     └──────────┘  └──────────┘  └──────────┘               │
│                                                              │
│  2. User creates PVC requesting storage                     │
│     ┌────────────────────────────────┐                      │
│     │ PVC: "I need 8Gi, RWO, standard" │                      │
│     └────────────────────────────────┘                      │
│                          │                                   │
│                          ▼                                   │
│  3. Kubernetes finds matching PV                            │
│     ┌──────────┐                                            │
│     │ PV: 10Gi │ ← Matches! (10Gi >= 8Gi, RWO, standard)   │
│     │ RWO      │                                            │
│     │ standard │                                            │
│     └──────────┘                                            │
│                          │                                   │
│                          ▼                                   │
│  4. PV and PVC are BOUND                                    │
│     ┌──────────────────────────────────────┐               │
│     │ PVC ←──────── BOUND ──────────► PV   │               │
│     └──────────────────────────────────────┘               │
│                                                              │
│  5. Pod uses PVC                                            │
│     ┌─────────────┐                                         │
│     │ Pod         │                                         │
│     │  └──► PVC ──┼──► PV (actual storage)                 │
│     └─────────────┘                                         │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### PVC YAML Structure

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-pvc
spec:
  storageClassName: manual       # Must match PV's storageClassName
  accessModes:
    - ReadWriteOnce              # Must be supported by PV
  resources:
    requests:
      storage: 5Gi               # Requested size
```

### Using PVC in a Pod

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pvc-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: data-volume
      mountPath: /usr/share/nginx/html
  volumes:
  - name: data-volume
    persistentVolumeClaim:
      claimName: my-pvc          # Reference to PVC
```

### Complete PV and PVC Example

```yaml
# 1. Create Persistent Volume
apiVersion: v1
kind: PersistentVolume
metadata:
  name: app-pv
  labels:
    app: myapp
spec:
  storageClassName: manual
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce
  persistentVolumeReclaimPolicy: Retain
  hostPath:
    path: /mnt/data/app
---
# 2. Create Persistent Volume Claim
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: app-pvc
spec:
  storageClassName: manual
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 5Gi
  selector:
    matchLabels:
      app: myapp           # Optional: select specific PV by label
---
# 3. Create Pod using PVC
apiVersion: v1
kind: Pod
metadata:
  name: app-pod
spec:
  containers:
  - name: app
    image: nginx
    ports:
    - containerPort: 80
    volumeMounts:
    - name: app-storage
      mountPath: /var/www/html
  volumes:
  - name: app-storage
    persistentVolumeClaim:
      claimName: app-pvc
```

---

## Storage Classes

### What is a Storage Class?

A **Storage Class** provides a way to describe different "classes" of storage available in a cluster. Different classes might map to quality-of-service levels, backup policies, or arbitrary policies.

### Why Storage Classes?

```
WITHOUT STORAGE CLASSES:
┌─────────────────────────────────────────────────────────────┐
│                                                              │
│  Admin must manually create PVs before users can claim them │
│                                                              │
│  Admin: Creates PVs    →    User: Creates PVC               │
│  (manual provisioning)       (waits for matching PV)        │
│                                                              │
│  Problem: Admin bottleneck, doesn't scale                   │
│                                                              │
└─────────────────────────────────────────────────────────────┘

WITH STORAGE CLASSES (Dynamic Provisioning):
┌─────────────────────────────────────────────────────────────┐
│                                                              │
│  Storage Classes defined once, PVs created automatically    │
│                                                              │
│  1. Admin creates StorageClass (once)                       │
│     ┌─────────────────────────────────────────┐            │
│     │ StorageClass: "fast-ssd"                 │            │
│     │ Provisioner: kubernetes.io/gce-pd        │            │
│     │ Type: pd-ssd                             │            │
│     └─────────────────────────────────────────┘            │
│                                                              │
│  2. User creates PVC with storageClassName                  │
│     ┌─────────────────────────────────────────┐            │
│     │ PVC: "Give me 10Gi of fast-ssd"          │            │
│     └─────────────────────────────────────────┘            │
│                          │                                   │
│                          ▼                                   │
│  3. Kubernetes AUTOMATICALLY creates PV                     │
│     ┌─────────────────────────────────────────┐            │
│     │ PV: 10Gi SSD disk created automatically  │            │
│     └─────────────────────────────────────────┘            │
│                                                              │
│  No admin intervention needed!                              │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### Storage Class YAML

```yaml
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: fast-ssd
provisioner: kubernetes.io/gce-pd    # Depends on cloud provider
parameters:
  type: pd-ssd                        # Provider-specific parameters
reclaimPolicy: Delete                 # What happens when PVC deleted
allowVolumeExpansion: true            # Allow resizing
volumeBindingMode: WaitForFirstConsumer  # When to bind
```

### Common Provisioners

| Provider | Provisioner |
|----------|-------------|
| **AWS EBS** | kubernetes.io/aws-ebs |
| **Azure Disk** | kubernetes.io/azure-disk |
| **GCP PD** | kubernetes.io/gce-pd |
| **OpenStack Cinder** | kubernetes.io/cinder |
| **NFS** | nfs-client (external) |
| **Local** | kubernetes.io/no-provisioner |

### Dynamic Provisioning Example

```yaml
# 1. Storage Class (admin creates once)
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: standard
provisioner: kubernetes.io/gce-pd
parameters:
  type: pd-standard
reclaimPolicy: Delete
allowVolumeExpansion: true
---
# 2. PVC with StorageClass (user creates)
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: dynamic-pvc
spec:
  storageClassName: standard      # Reference StorageClass
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 20Gi
---
# 3. Pod using PVC
apiVersion: v1
kind: Pod
metadata:
  name: dynamic-pod
spec:
  containers:
  - name: app
    image: nginx
    volumeMounts:
    - name: data
      mountPath: /data
  volumes:
  - name: data
    persistentVolumeClaim:
      claimName: dynamic-pvc
```

When the PVC is created, Kubernetes automatically:
1. Creates a 20Gi disk in the cloud
2. Creates a PV representing that disk
3. Binds the PV to the PVC

### Default Storage Class

```yaml
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: standard
  annotations:
    storageclass.kubernetes.io/is-default-class: "true"  # Make default
provisioner: kubernetes.io/gce-pd
parameters:
  type: pd-standard
```

PVCs without `storageClassName` will use the default StorageClass.

---

## Practical Examples

### Database with Persistent Storage

```yaml
# Storage Class
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: database-storage
provisioner: kubernetes.io/gce-pd
parameters:
  type: pd-ssd
reclaimPolicy: Retain              # Keep data even if PVC deleted
allowVolumeExpansion: true
---
# PVC for Database
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: postgres-pvc
spec:
  storageClassName: database-storage
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 50Gi
---
# PostgreSQL Deployment
apiVersion: apps/v1
kind: Deployment
metadata:
  name: postgres
spec:
  replicas: 1
  selector:
    matchLabels:
      app: postgres
  template:
    metadata:
      labels:
        app: postgres
    spec:
      containers:
      - name: postgres
        image: postgres:13
        ports:
        - containerPort: 5432
        env:
        - name: POSTGRES_PASSWORD
          valueFrom:
            secretKeyRef:
              name: postgres-secret
              key: password
        - name: PGDATA
          value: /var/lib/postgresql/data/pgdata
        volumeMounts:
        - name: postgres-data
          mountPath: /var/lib/postgresql/data
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"
      volumes:
      - name: postgres-data
        persistentVolumeClaim:
          claimName: postgres-pvc
---
# Service
apiVersion: v1
kind: Service
metadata:
  name: postgres-service
spec:
  selector:
    app: postgres
  ports:
  - port: 5432
    targetPort: 5432
  type: ClusterIP
```

### Application with Multiple Volumes

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: app-data-pvc
spec:
  storageClassName: standard
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 10Gi
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: app-logs-pvc
spec:
  storageClassName: standard
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 5Gi
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: webapp
spec:
  replicas: 1
  selector:
    matchLabels:
      app: webapp
  template:
    metadata:
      labels:
        app: webapp
    spec:
      containers:
      - name: app
        image: myapp:1.0
        ports:
        - containerPort: 8080
        volumeMounts:
        # Persistent data
        - name: data-volume
          mountPath: /app/data
        # Persistent logs
        - name: logs-volume
          mountPath: /app/logs
        # Temporary cache (emptyDir)
        - name: cache-volume
          mountPath: /app/cache
        # Config from ConfigMap
        - name: config-volume
          mountPath: /app/config
          readOnly: true
      volumes:
      - name: data-volume
        persistentVolumeClaim:
          claimName: app-data-pvc
      - name: logs-volume
        persistentVolumeClaim:
          claimName: app-logs-pvc
      - name: cache-volume
        emptyDir: {}
      - name: config-volume
        configMap:
          name: app-config
```

### Shared Storage (ReadWriteMany)

```yaml
# NFS-based PV for shared storage
apiVersion: v1
kind: PersistentVolume
metadata:
  name: shared-pv
spec:
  storageClassName: nfs
  capacity:
    storage: 100Gi
  accessModes:
    - ReadWriteMany           # Multiple pods can write
  nfs:
    server: nfs.example.com
    path: /shared
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: shared-pvc
spec:
  storageClassName: nfs
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 100Gi
---
# Multiple replicas sharing same storage
apiVersion: apps/v1
kind: Deployment
metadata:
  name: shared-app
spec:
  replicas: 3                  # All 3 pods share the same volume
  selector:
    matchLabels:
      app: shared-app
  template:
    metadata:
      labels:
        app: shared-app
    spec:
      containers:
      - name: app
        image: nginx
        volumeMounts:
        - name: shared-data
          mountPath: /shared
      volumes:
      - name: shared-data
        persistentVolumeClaim:
          claimName: shared-pvc
```

---

## Storage Commands

### PersistentVolume Commands

```bash
# List PVs
kubectl get pv
kubectl get persistentvolumes

# Describe PV
kubectl describe pv my-pv

# Get PV details
kubectl get pv my-pv -o yaml

# Delete PV
kubectl delete pv my-pv
```

### PersistentVolumeClaim Commands

```bash
# List PVCs
kubectl get pvc
kubectl get persistentvolumeclaims

# List PVCs in all namespaces
kubectl get pvc -A

# Describe PVC
kubectl describe pvc my-pvc

# Get PVC details
kubectl get pvc my-pvc -o yaml

# Delete PVC
kubectl delete pvc my-pvc

# Expand PVC (if StorageClass allows)
kubectl patch pvc my-pvc -p '{"spec":{"resources":{"requests":{"storage":"20Gi"}}}}'
```

### StorageClass Commands

```bash
# List Storage Classes
kubectl get storageclass
kubectl get sc

# Describe Storage Class
kubectl describe sc standard

# Get default Storage Class
kubectl get sc -o jsonpath='{.items[?(@.metadata.annotations.storageclass\.kubernetes\.io/is-default-class=="true")].metadata.name}'

# Create Storage Class
kubectl apply -f storageclass.yaml

# Delete Storage Class
kubectl delete sc custom-storage
```

### Debugging Storage

```bash
# Check PVC status
kubectl get pvc my-pvc
# Status should be "Bound"

# If PVC is "Pending", check events
kubectl describe pvc my-pvc

# Check if PV exists for PVC
kubectl get pv

# Check pod volume mounts
kubectl describe pod my-pod | grep -A 10 Volumes

# Check actual mount inside pod
kubectl exec -it my-pod -- df -h
kubectl exec -it my-pod -- ls -la /data
```

---

## Best Practices

### General Storage

1. **Use PVCs** - Never use hostPath in production
2. **Right-size storage** - Request what you need, allow expansion
3. **Choose correct access mode** - RWO for single-pod, RWX for shared
4. **Use Storage Classes** - Enable dynamic provisioning
5. **Set reclaim policy** - Use Retain for important data

### Data Protection

1. **Backup regularly** - PVs can still fail
2. **Use Retain policy** - For databases and critical data
3. **Test recovery** - Ensure backups actually work
4. **Monitor capacity** - Alert before running out of space

### Performance

1. **Match storage type** - SSD for databases, HDD for archives
2. **Use local storage** - For latency-sensitive apps
3. **Consider IOPS limits** - Cloud storage has limits
4. **Use emptyDir** - For temporary/cache data

### Security

1. **Encrypt at rest** - Enable encryption on cloud storage
2. **Access control** - Use RBAC to limit PV access
3. **Don't share PVs** - Unless necessary (use separate PVCs)

---

## Summary

| Concept | Purpose | Lifecycle |
|---------|---------|-----------|
| **Volume** | Mount storage into pod | Pod lifetime (varies by type) |
| **emptyDir** | Temporary pod storage | Pod lifetime only |
| **hostPath** | Node filesystem access | Node lifetime |
| **PV** | Cluster storage resource | Independent of pods |
| **PVC** | Request for storage | Bound to PV |
| **StorageClass** | Dynamic provisioning | Cluster lifetime |

### Key Takeaways

1. **Containers are ephemeral** - Use volumes to persist data
2. **emptyDir** - Temporary storage, deleted with pod
3. **PV/PVC** - Persistent storage that survives pod restarts
4. **StorageClass** - Enables automatic PV provisioning
5. **Access modes** - RWO (single node), RWX (multiple nodes), ROX (read-only)
6. **Reclaim policy** - Retain (keep data) or Delete (cleanup)

---

## Next Steps

You've completed the Kubernetes storage topic. Review the [kubectl CLI Commands](./05-kubectl-cli.md) to practice managing storage resources from the command line.
