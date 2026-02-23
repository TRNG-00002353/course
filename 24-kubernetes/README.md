# Kubernetes

## Overview
Kubernetes is an open-source container orchestration platform for automating deployment, scaling, and management of containerized applications. Originally developed by Google and now maintained by the Cloud Native Computing Foundation (CNCF), Kubernetes has become the de facto standard for container orchestration in modern cloud-native applications.

## Learning Objectives
By the end of this module, you will be able to:
- Understand container orchestration and Kubernetes architecture
- Deploy and manage applications using Pods, ReplicaSets, and Deployments
- Configure applications using ConfigMaps and Secrets
- Manage persistent storage using Volumes, PVs, and PVCs
- Expose applications using Services and Ingress controllers
- Secure pod-to-pod communication with Network Policies
- Use kubectl CLI for cluster management and troubleshooting

## Topics Covered

### 1. [Kubernetes Fundamentals](./topics/01-kubernetes-fundamentals.md)
- What is Container Orchestration?
- Kubernetes Architecture (Control Plane, Worker Nodes)
- Pods (Smallest Deployable Unit)
- ReplicaSets (Maintaining Pod Replicas)
- Deployments (Rolling Updates, Rollbacks, Scaling)
- Namespaces (Resource Isolation)

### 2. [Services and Configuration](./topics/02-services-and-configuration.md)
- Services (ClusterIP, NodePort, LoadBalancer)
- ConfigMaps (Non-sensitive Configuration)
- Secrets (Sensitive Data Management)
- Storage (Volumes, PersistentVolumes, PersistentVolumeClaims)

### 3. [Networking and kubectl](./topics/03-networking-and-kubectl.md)
- Service Discovery (DNS-based)
- Ingress Controllers (Path-based, Host-based Routing, TLS)
- Network Policies (Ingress, Egress Rules)
- kubectl CLI (Essential Commands, Debugging, Rollouts)

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

# Storage
kubectl get pv
kubectl get pvc

# Apply Resources
kubectl apply -f <file.yaml>
kubectl delete -f <file.yaml>
kubectl get all
```

## Key Concepts

### Container Orchestration
- **Automated Deployment**: Deploy containers across a cluster
- **Auto-scaling**: Scale applications based on demand
- **Self-healing**: Automatically restart failed containers
- **Load Balancing**: Distribute traffic across instances
- **Service Discovery**: Automatically discover and connect services

### Core Kubernetes Objects
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

## Quick Start Example

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

# Check rollout
kubectl rollout status deployment/nginx
```

## Exercises
See the [exercises](./exercises/) directory for hands-on practice problems.

## Additional Resources

### Official Documentation
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [kubectl Reference](https://kubernetes.io/docs/reference/kubectl/)

### Learning Platforms
- [Kubernetes Tutorials](https://kubernetes.io/docs/tutorials/)
- [Play with Kubernetes](https://labs.play-with-k8s.com/)

### Tools
- [Minikube](https://minikube.sigs.k8s.io/) - Local Kubernetes cluster
- [k9s](https://k9scli.io/) - Terminal UI for Kubernetes
- [Lens](https://k8slens.dev/) - Kubernetes IDE

## Assessment

You should be comfortable with:
- [ ] Understanding Kubernetes architecture and components
- [ ] Creating and managing Pods, Deployments, and Services
- [ ] Configuring applications with ConfigMaps and Secrets
- [ ] Managing persistent storage with PVs, PVCs
- [ ] Implementing service discovery and ingress routing
- [ ] Applying Network Policies for security
- [ ] Using kubectl for cluster management and troubleshooting

---

**Time Estimate:** 2 days
**Difficulty:** Beginner to Intermediate
**Prerequisites:** Docker, Container Fundamentals, Linux Basics
