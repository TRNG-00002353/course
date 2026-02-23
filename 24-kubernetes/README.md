# Kubernetes

## Overview
Kubernetes is an open-source container orchestration platform for automating deployment, scaling, and management of containerized applications. Originally developed by Google and now maintained by the Cloud Native Computing Foundation (CNCF), Kubernetes has become the de facto standard for container orchestration in modern cloud-native applications.

## Learning Objectives
By the end of this module, you will be able to:
- Understand container orchestration and Kubernetes architecture
- Deploy and manage containerized applications using Kubernetes objects
- Configure applications using ConfigMaps and Secrets
- Manage persistent storage using Volumes, PVs, and PVCs
- Implement networking, service discovery, and ingress controllers
- Secure pod-to-pod communication with Network Policies
- Use kubectl CLI for cluster management and troubleshooting

## Topics Covered

### 1. [Kubernetes Introduction](./topics/01-kubernetes-introduction.md)
- What is Container Orchestration?
- Kubernetes Architecture
- Master and Worker Nodes
- Control Plane Components (API Server, etcd, Scheduler, Controller Manager)
- Worker Node Components (kubelet, kube-proxy, Container Runtime)
- Communication Flow
- Cluster Types and Setup

### 2. [Kubernetes Objects](./topics/02-kubernetes-objects.md)
- Pods (Single and Multi-Container)
- ReplicaSets
- Deployments (Rolling Updates, Rollbacks, Scaling)
- Services (ClusterIP, NodePort, LoadBalancer, Headless)
- Namespaces (Resource Isolation, Quotas)
- Labels and Selectors

### 3. [Configuration Management](./topics/03-configuration.md)
- ConfigMaps (Environment Variables, Volume Mounts)
- Secrets (Types, Security Best Practices)
- Kubernetes Configuration Files (YAML)
- Resource Specifications
- Health Probes (Liveness, Readiness, Startup)

### 4. [Networking](./topics/04-networking.md)
- Kubernetes Networking Model
- Pod-to-Pod Communication
- Service Discovery (DNS-based, Environment Variables)
- Ingress Controllers (NGINX, Path-based, Host-based Routing)
- TLS/SSL Termination
- Network Policies (Ingress, Egress, Security)

### 5. [kubectl CLI Commands](./topics/05-kubectl-cli.md)
- Installation and Configuration
- Basic Operations (Create, Read, Update, Delete)
- Deployment Management (Rollout, Rollback, Scale)
- Debugging Commands (Logs, Exec, Describe, Events)
- Advanced Operations (Labels, Contexts, Patching)
- Productivity Tips and Aliases

### 6. [Storage](./topics/06-storage.md)
- Volumes (emptyDir, hostPath, configMap, secret)
- Persistent Volumes (PV)
- Persistent Volume Claims (PVC)
- Storage Classes and Dynamic Provisioning
- Access Modes and Reclaim Policies
- Practical Storage Examples

## Key Commands Reference

```bash
# Cluster Information
kubectl cluster-info
kubectl get nodes
kubectl get namespaces

# Pod Operations
kubectl get pods
kubectl describe pod <pod-name>
kubectl logs <pod-name>
kubectl exec -it <pod-name> -- /bin/bash

# Deployment Operations
kubectl create deployment <name> --image=<image>
kubectl get deployments
kubectl scale deployment <name> --replicas=3
kubectl rollout status deployment/<name>
kubectl rollout undo deployment/<name>

# Service Operations
kubectl expose deployment <name> --port=80
kubectl get services
kubectl describe service <service-name>

# Configuration
kubectl create configmap <name> --from-literal=key=value
kubectl create secret generic <name> --from-literal=key=value
kubectl get configmaps
kubectl get secrets

# Storage Operations
kubectl get pv
kubectl get pvc
kubectl get storageclass
kubectl describe pvc <pvc-name>

# Apply Resources
kubectl apply -f <file.yaml>
kubectl delete -f <file.yaml>
kubectl get all
kubectl get all -n <namespace>
```

## Key Concepts

### Container Orchestration
- **Automated Deployment**: Deploy containers across a cluster of machines
- **Auto-scaling**: Scale applications based on demand
- **Self-healing**: Automatically restart failed containers
- **Load Balancing**: Distribute traffic across container instances
- **Service Discovery**: Automatically discover and connect services

### Kubernetes Objects
- **Pods**: Smallest deployable units containing one or more containers
- **Deployments**: Declarative updates for Pods and ReplicaSets
- **Services**: Stable networking abstraction for accessing Pods
- **ConfigMaps/Secrets**: Configuration and sensitive data management
- **Namespaces**: Virtual clusters for resource isolation
- **PersistentVolumes/PVCs**: Persistent storage for stateful applications

### Networking
- **Flat Network**: All pods can communicate without NAT
- **Service Discovery**: DNS-based service discovery using CoreDNS
- **Ingress**: HTTP/HTTPS routing from outside the cluster
- **Network Policies**: Firewall rules for pod-to-pod communication

## Practical Examples

### Deploy a Simple Application

```bash
# Create deployment
kubectl create deployment nginx --image=nginx:1.19 --replicas=3

# Expose as service
kubectl expose deployment nginx --port=80 --type=LoadBalancer

# Check status
kubectl get deployments,pods,services

# Scale deployment
kubectl scale deployment nginx --replicas=5

# Update image
kubectl set image deployment/nginx nginx=nginx:1.20

# Check rollout status
kubectl rollout status deployment/nginx
```

### Deploy Multi-Tier Application

```bash
# Apply all resources from directory
kubectl apply -f ./kubernetes-manifests/

# Check all resources in namespace
kubectl get all -n production

# View logs
kubectl logs -f deployment/webapp -n production

# Access application
kubectl port-forward service/webapp 8080:80 -n production
```

## Exercises
See the [exercises](./exercises/) directory for hands-on practice problems and solutions.

## Additional Resources

### Official Documentation
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [kubectl Reference](https://kubernetes.io/docs/reference/kubectl/)
- [Kubernetes API Reference](https://kubernetes.io/docs/reference/kubernetes-api/)

### Learning Platforms
- [Kubernetes Tutorials](https://kubernetes.io/docs/tutorials/)
- [Katacoda Kubernetes Scenarios](https://www.katacoda.com/courses/kubernetes)
- [Play with Kubernetes](https://labs.play-with-k8s.com/)

### Tools and Utilities
- [Minikube](https://minikube.sigs.k8s.io/) - Local Kubernetes cluster
- [k9s](https://k9scli.io/) - Terminal UI for Kubernetes
- [Lens](https://k8slens.dev/) - Kubernetes IDE
- [Helm](https://helm.sh/) - Package manager for Kubernetes

### Cheat Sheets
- See [resources/cheat-sheets/kubernetes.md](../resources/cheat-sheets/)

## Assessment

You should be comfortable with:
- [ ] Understanding Kubernetes architecture and components
- [ ] Creating and managing Pods, Deployments, and Services
- [ ] Configuring applications with ConfigMaps and Secrets
- [ ] Managing persistent storage with PVs, PVCs, and Storage Classes
- [ ] Implementing service discovery and ingress routing
- [ ] Applying Network Policies for security
- [ ] Using kubectl for cluster management and troubleshooting
- [ ] Debugging common Kubernetes issues
- [ ] Deploying multi-tier applications

## Next Steps

Once you've completed this module and feel confident with Kubernetes, proceed to:
- Continue practicing with real-world scenarios
- Explore advanced topics like StatefulSets, DaemonSets, and Jobs
- Learn about Helm for package management
- Implement CI/CD pipelines with Kubernetes
- Study Kubernetes security best practices

---

**Time Estimate:** 3 days
**Difficulty:** Intermediate
**Prerequisites:** Docker, Container Fundamentals, Linux Basics
