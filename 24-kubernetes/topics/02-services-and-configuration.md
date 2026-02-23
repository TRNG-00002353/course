# Services and Configuration

## Services

A **Service** provides stable networking for pods. Since pod IPs change, Services give a fixed endpoint.

### Service Types

| Type | Description | Use Case |
|------|-------------|----------|
| **ClusterIP** | Internal IP only (default) | Internal communication |
| **NodePort** | Exposes on node IP:port (30000-32767) | Development, testing |
| **LoadBalancer** | Cloud load balancer | Production external access |

### ClusterIP Service (Default)

```yaml
apiVersion: v1
kind: Service
metadata:
  name: backend-service
spec:
  type: ClusterIP
  selector:
    app: backend
  ports:
  - port: 80
    targetPort: 8080
```

### NodePort Service

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
    nodePort: 30080    # Access via <NodeIP>:30080
```

### LoadBalancer Service

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
  - port: 80
    targetPort: 8080
```

### Service Commands

```bash
kubectl expose deployment nginx --port=80 --type=ClusterIP
kubectl get services
kubectl describe service backend-service
kubectl delete service backend-service
```

---

## ConfigMaps

**ConfigMaps** store non-sensitive configuration data as key-value pairs.

### Create ConfigMap

```bash
# From literals
kubectl create configmap app-config \
  --from-literal=DB_HOST=mysql \
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
  DB_HOST: "mysql"
  DB_PORT: "3306"
  LOG_LEVEL: "info"
```

### Use in Pod (Environment Variables)

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp
spec:
  containers:
  - name: app
    image: myapp:1.0
    envFrom:
    - configMapRef:
        name: app-config
```

### Use in Pod (Volume Mount)

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp
spec:
  containers:
  - name: app
    image: myapp:1.0
    volumeMounts:
    - name: config
      mountPath: /etc/config
  volumes:
  - name: config
    configMap:
      name: app-config
```

---

## Secrets

**Secrets** store sensitive data (passwords, API keys). Values are base64 encoded.

### Create Secret

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
stringData:          # Use stringData (no base64 needed)
  username: admin
  password: secret123
```

### Use in Pod

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp
spec:
  containers:
  - name: app
    image: myapp:1.0
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
kubectl get secret db-secret -o jsonpath='{.data.password}' | base64 -d
```

---

## Storage

### The Problem

Containers are ephemeral - data is lost when pod restarts. **Volumes** solve this.

### Volume Types

| Type | Persistence | Use Case |
|------|-------------|----------|
| **emptyDir** | Pod lifetime | Temp files, cache |
| **hostPath** | Node lifetime | Dev/testing only |
| **PersistentVolumeClaim** | Beyond pod | Production data |

### emptyDir (Temporary)

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

### Persistent Volume (PV) and Claim (PVC)

```yaml
# PV - Storage resource (admin creates)
apiVersion: v1
kind: PersistentVolume
metadata:
  name: my-pv
spec:
  storageClassName: manual
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce       # RWO: single node read-write
  hostPath:
    path: /mnt/data
---
# PVC - Request for storage (user creates)
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-pvc
spec:
  storageClassName: manual
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 5Gi
---
# Pod using PVC
apiVersion: v1
kind: Pod
metadata:
  name: db-pod
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
      claimName: my-pvc
```

### Access Modes

| Mode | Description |
|------|-------------|
| **ReadWriteOnce (RWO)** | Single node can mount read-write |
| **ReadOnlyMany (ROX)** | Multiple nodes can mount read-only |
| **ReadWriteMany (RWX)** | Multiple nodes can mount read-write |

### Storage Commands

```bash
kubectl get pv                    # List PersistentVolumes
kubectl get pvc                   # List PersistentVolumeClaims
kubectl describe pvc my-pvc       # PVC details
kubectl get storageclass          # List StorageClasses
```

---

## Summary

| Resource | Purpose | Command |
|----------|---------|---------|
| **Service** | Stable network endpoint | `kubectl get svc` |
| **ConfigMap** | Non-sensitive config | `kubectl get cm` |
| **Secret** | Sensitive data | `kubectl get secrets` |
| **PV/PVC** | Persistent storage | `kubectl get pv,pvc` |

### Key Points

1. **Services** provide stable IPs - pods come and go
2. **ConfigMaps** for settings, **Secrets** for credentials
3. **PVCs** request storage, **PVs** provide it
4. Use **environment variables** or **volume mounts** for config
