# Networking and kubectl

## Service Discovery

Kubernetes provides built-in DNS for service discovery using CoreDNS.

### DNS Format

```
<service-name>.<namespace>.svc.cluster.local
```

### Accessing Services

```bash
# Within same namespace (short name)
curl http://backend-service

# Cross-namespace (full DNS name)
curl http://backend-service.production.svc.cluster.local

# From any namespace
curl http://api-service.staging.svc.cluster.local:8080
```

### Service Discovery Example

```yaml
# Backend Service
apiVersion: v1
kind: Service
metadata:
  name: backend-api
  namespace: production
spec:
  selector:
    app: backend
  ports:
  - port: 8080
---
# Frontend Pod uses backend via DNS
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
spec:
  replicas: 2
  selector:
    matchLabels:
      app: frontend
  template:
    metadata:
      labels:
        app: frontend
    spec:
      containers:
      - name: webapp
        image: myapp/frontend:1.0
        env:
        - name: BACKEND_URL
          value: "http://backend-api:8080"
```

---

## Ingress Controllers

**Ingress** exposes HTTP/HTTPS routes from outside the cluster to internal services.

### Ingress vs Service

| Feature | Service (LoadBalancer) | Ingress |
|---------|----------------------|---------|
| **Layer** | L4 (TCP/UDP) | L7 (HTTP/HTTPS) |
| **Cost** | One LB per service | One LB for all services |
| **Routing** | Port-based | Path/host-based |

### Basic Ingress

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: basic-ingress
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
            name: webapp-service
            port:
              number: 80
```

### Path-Based Routing

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: path-ingress
spec:
  ingressClassName: nginx
  rules:
  - host: api.example.com
    http:
      paths:
      - path: /api
        pathType: Prefix
        backend:
          service:
            name: api-service
            port:
              number: 8080
      - path: /admin
        pathType: Prefix
        backend:
          service:
            name: admin-service
            port:
              number: 8080
```

### Host-Based Routing

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: host-ingress
spec:
  ingressClassName: nginx
  rules:
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
```

### TLS/SSL Termination

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: tls-ingress
spec:
  ingressClassName: nginx
  tls:
  - hosts:
    - myapp.example.com
    secretName: tls-secret
  rules:
  - host: myapp.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: webapp-service
            port:
              number: 80
```

### Ingress Commands

```bash
kubectl get ingress
kubectl describe ingress basic-ingress
kubectl delete ingress basic-ingress
```

---

## Network Policies

**Network Policies** are firewall rules for pod-to-pod communication.

### Default Deny All

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: default-deny-all
  namespace: production
spec:
  podSelector: {}    # Applies to all pods
  policyTypes:
  - Ingress
  - Egress
```

### Allow Specific Ingress

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-frontend-to-backend
spec:
  podSelector:
    matchLabels:
      app: backend
  policyTypes:
  - Ingress
  ingress:
  - from:
    - podSelector:
        matchLabels:
          app: frontend
    ports:
    - protocol: TCP
      port: 8080
```

### Allow Specific Egress

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-backend-to-database
spec:
  podSelector:
    matchLabels:
      app: backend
  policyTypes:
  - Egress
  egress:
  - to:
    - podSelector:
        matchLabels:
          app: database
    ports:
    - protocol: TCP
      port: 5432
```

### Network Policy Commands

```bash
kubectl get networkpolicies
kubectl describe networkpolicy allow-frontend-to-backend
kubectl delete networkpolicy allow-frontend-to-backend
```

---

## kubectl Essentials

### Command Structure

```bash
kubectl [command] [TYPE] [NAME] [flags]
```

### Creating Resources

```bash
# Imperative
kubectl create deployment nginx --image=nginx:1.19
kubectl run nginx --image=nginx
kubectl expose deployment nginx --port=80 --type=LoadBalancer

# Declarative (recommended)
kubectl apply -f deployment.yaml
kubectl apply -f ./manifests/
```

### Viewing Resources

```bash
kubectl get pods                    # List pods
kubectl get pods -o wide            # More details
kubectl get pods -A                 # All namespaces
kubectl get all                     # All resources
kubectl describe pod nginx-pod      # Detailed info
kubectl get pods -w                 # Watch for changes
kubectl get pods -l app=nginx       # Filter by label
```

### Updating Resources

```bash
kubectl edit deployment nginx
kubectl set image deployment/nginx nginx=nginx:1.20
kubectl scale deployment nginx --replicas=5
```

### Deleting Resources

```bash
kubectl delete pod nginx-pod
kubectl delete -f deployment.yaml
kubectl delete pods -l app=nginx
```

### Debugging

```bash
# Logs
kubectl logs nginx-pod
kubectl logs -f nginx-pod           # Follow logs
kubectl logs nginx-pod --previous   # Previous container

# Execute commands
kubectl exec nginx-pod -- ls /var
kubectl exec -it nginx-pod -- /bin/bash

# Port forwarding
kubectl port-forward pod/nginx-pod 8080:80
kubectl port-forward service/nginx-service 8080:80
```

### Deployment Rollouts

```bash
kubectl rollout status deployment/nginx
kubectl rollout history deployment/nginx
kubectl rollout undo deployment/nginx
kubectl rollout restart deployment/nginx
```

### Context and Namespace

```bash
kubectl config get-contexts
kubectl config use-context production
kubectl config set-context --current --namespace=dev
kubectl get pods -n production
```

### Cluster Info

```bash
kubectl cluster-info
kubectl get nodes
kubectl get events
kubectl top pods
kubectl top nodes
```

---

## Quick Reference

### Resource Short Names

| Resource | Short |
|----------|-------|
| pods | po |
| services | svc |
| deployments | deploy |
| namespaces | ns |
| configmaps | cm |
| secrets | secret |
| ingresses | ing |
| persistentvolumeclaims | pvc |
| networkpolicies | netpol |

### Common Troubleshooting

```bash
# Pod not starting
kubectl describe pod problematic-pod
kubectl get events --field-selector involvedObject.name=problematic-pod

# Check logs
kubectl logs problematic-pod
kubectl logs problematic-pod --previous

# Test DNS
kubectl run test --image=busybox --rm -it -- nslookup kubernetes.default

# Generate YAML (dry-run)
kubectl create deployment nginx --image=nginx --dry-run=client -o yaml
```

---

## Summary

| Component | Purpose | Command |
|-----------|---------|---------|
| **Service Discovery** | DNS-based service lookup | `curl http://service-name` |
| **Ingress** | HTTP/HTTPS routing | `kubectl get ing` |
| **Network Policy** | Pod firewall rules | `kubectl get netpol` |
| **kubectl** | Cluster management CLI | `kubectl get all` |

### Key Points

1. **Service Discovery** - Use DNS names, not pod IPs
2. **Ingress** - Single entry point for HTTP traffic with routing
3. **Network Policies** - Start with deny-all, then allow specific traffic
4. **kubectl** - Prefer declarative (`apply -f`) over imperative commands
