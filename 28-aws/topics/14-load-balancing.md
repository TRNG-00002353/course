# Load Balancing for EC2 Instances

## What is Load Balancing?

A load balancer distributes incoming traffic across multiple EC2 instances, improving availability and fault tolerance.

```
Without Load Balancer           With Load Balancer
┌─────────────────────┐        ┌─────────────────────┐
│     Users           │        │     Users           │
│       │             │        │       │             │
│       ▼             │        │       ▼             │
│  ┌─────────┐        │        │ ┌───────────┐       │
│  │ Single  │        │        │ │   Load    │       │
│  │ EC2     │        │        │ │ Balancer  │       │
│  └─────────┘        │        │ └─────┬─────┘       │
│                     │        │   ┌───┼───┐         │
│ Single point of     │        │   ▼   ▼   ▼         │
│ failure!            │        │ ┌──┐ ┌──┐ ┌──┐     │
└─────────────────────┘        │ │EC2│ │EC2│ │EC2│    │
                               │ └──┘ └──┘ └──┘      │
                               │ High availability!  │
                               └─────────────────────┘
```

---

## Benefits of Load Balancing

| Benefit | Description |
|---------|-------------|
| **High Availability** | If one instance fails, others handle traffic |
| **Scalability** | Add/remove instances as needed |
| **Health Checks** | Automatically routes away from unhealthy instances |
| **SSL Termination** | Handle HTTPS at the load balancer |
| **Single Entry Point** | One DNS name for multiple instances |

---

## Types of AWS Load Balancers

### Elastic Load Balancing (ELB) Family

```
ELB Types
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  Application LB (ALB)    Network LB (NLB)    Classic LB     │
│  ─────────────────────   ────────────────    ──────────     │
│  Layer 7 (HTTP/HTTPS)    Layer 4 (TCP/UDP)   Legacy         │
│  Path-based routing      Ultra-low latency   Do not use     │
│  Best for web apps       Best for gaming     for new apps   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Application Load Balancer (ALB)

Best for HTTP/HTTPS traffic (most web applications).

```
ALB Features
┌─────────────────────────────────────────────────────┐
│ ✓ HTTP/HTTPS (Layer 7)                             │
│ ✓ Path-based routing (/api/* → backend)            │
│ ✓ Host-based routing (api.example.com → backend)   │
│ ✓ WebSocket support                                 │
│ ✓ Native HTTP/2                                     │
│ ✓ Request tracing                                   │
└─────────────────────────────────────────────────────┘
```

### Network Load Balancer (NLB)

Best for TCP/UDP traffic requiring extreme performance.

```
NLB Features
┌─────────────────────────────────────────────────────┐
│ ✓ TCP/UDP (Layer 4)                                │
│ ✓ Millions of requests per second                  │
│ ✓ Ultra-low latency                                │
│ ✓ Static IP addresses                              │
│ ✓ Preserves source IP                              │
└─────────────────────────────────────────────────────┘
```

---

## When to Use Which?

| Use Case | Recommended LB |
|----------|---------------|
| Web applications | ALB |
| REST APIs | ALB |
| Microservices | ALB |
| Gaming | NLB |
| IoT | NLB |
| Static IP required | NLB |

**For beginners:** Start with ALB for web applications.

---

## Key Concepts

### Target Groups

A target group is a collection of instances that receive traffic.

```
Load Balancer
      │
      ▼
┌─────────────────────────────────────────┐
│          Target Group                    │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐   │
│  │ EC2 #1  │ │ EC2 #2  │ │ EC2 #3  │   │
│  │ Healthy │ │ Healthy │ │Unhealthy│   │
│  └─────────┘ └─────────┘ └─────────┘   │
│       ▲           ▲           ✗         │
│       └───────────┴── Traffic routed    │
└─────────────────────────────────────────┘
```

### Health Checks

The load balancer periodically checks if instances are healthy.

```
Health Check Configuration
┌─────────────────────────────────────────────────────┐
│ Protocol: HTTP                                      │
│ Path: /health or /actuator/health                   │
│ Port: 8080                                          │
│ Healthy threshold: 2 consecutive successes          │
│ Unhealthy threshold: 3 consecutive failures         │
│ Interval: 30 seconds                                │
│ Timeout: 5 seconds                                  │
└─────────────────────────────────────────────────────┘
```

### Listeners

Define how the load balancer handles incoming traffic.

```
Listener Examples
┌─────────────────────────────────────────────────────┐
│ Listener 1: Port 80 (HTTP) → Forward to target     │
│ Listener 2: Port 443 (HTTPS) → Forward to target   │
│                                                     │
│ Rules can redirect:                                 │
│   HTTP (80) → Redirect to HTTPS (443)              │
└─────────────────────────────────────────────────────┘
```

---

## Creating an Application Load Balancer

### Prerequisites

- At least 2 EC2 instances running your application
- Instances in different Availability Zones (recommended)
- Security group allowing traffic on application port

### Step 1: Create Target Group

```
EC2 → Target Groups → Create target group

1. Target type: Instances
2. Target group name: my-app-targets
3. Protocol: HTTP
4. Port: 8080 (your app port)
5. VPC: Select your VPC
6. Health check path: /health (or /actuator/health for Spring Boot)
7. Create target group
```

### Step 2: Register Targets

```
Target Groups → Select target group → Register targets

1. Select your EC2 instances
2. Click "Include as pending below"
3. Click "Register pending targets"
```

### Step 3: Create Load Balancer

```
EC2 → Load Balancers → Create Load Balancer

1. Select "Application Load Balancer"
2. Name: my-app-alb
3. Scheme: Internet-facing
4. IP address type: IPv4
5. Network mapping:
   - VPC: Select your VPC
   - Mappings: Select at least 2 AZs
6. Security groups: Create or select one allowing HTTP/HTTPS
7. Listeners:
   - HTTP:80 → Forward to my-app-targets
8. Create load balancer
```

### Step 4: Configure Security Group

```
Load Balancer Security Group
┌─────────────────────────────────────────────────────┐
│ Inbound Rules:                                      │
│ ├── HTTP (80) from 0.0.0.0/0                       │
│ └── HTTPS (443) from 0.0.0.0/0                     │
│                                                     │
│ Outbound Rules:                                     │
│ └── All traffic to VPC CIDR                        │
└─────────────────────────────────────────────────────┘

EC2 Instance Security Group
┌─────────────────────────────────────────────────────┐
│ Inbound Rules:                                      │
│ └── Custom TCP (8080) from ALB Security Group      │
│                                                     │
│ Important: Only allow traffic FROM the ALB         │
└─────────────────────────────────────────────────────┘
```

---

## Architecture with Load Balancer

```
                         Internet
                             │
                             ▼
                    ┌─────────────────┐
                    │   Route 53      │
                    │  (DNS - Optional)│
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │       ALB       │
                    │  (Load Balancer)│
                    └────────┬────────┘
                             │
            ┌────────────────┼────────────────┐
            │                │                │
            ▼                ▼                ▼
     ┌───────────┐    ┌───────────┐    ┌───────────┐
     │   EC2     │    │   EC2     │    │   EC2     │
     │   AZ-1a   │    │   AZ-1b   │    │   AZ-1c   │
     └─────┬─────┘    └─────┬─────┘    └─────┬─────┘
           │                │                │
           └────────────────┼────────────────┘
                            │
                            ▼
                    ┌─────────────────┐
                    │      RDS        │
                    │   (Database)    │
                    └─────────────────┘
```

---

## Path-Based Routing

Route different URLs to different target groups.

```
ALB with Path Routing
┌─────────────────────────────────────────────────────┐
│                      ALB                            │
│                       │                             │
│    ┌──────────────────┼──────────────────┐         │
│    │                  │                  │         │
│ /api/*            /static/*          /* (default)  │
│    │                  │                  │         │
│    ▼                  ▼                  ▼         │
│ ┌──────┐          ┌──────┐          ┌──────┐      │
│ │ API  │          │  S3  │          │ Web  │      │
│ │Target│          │Target│          │Target│      │
│ └──────┘          └──────┘          └──────┘      │
└─────────────────────────────────────────────────────┘
```

### Creating Path-Based Rules

```
Load Balancer → Listeners → View/edit rules

Add Rule:
  IF path is /api/*
  THEN forward to api-target-group

Add Rule:
  IF path is /admin/*
  THEN forward to admin-target-group
```

---

## HTTPS Setup

### Option 1: AWS Certificate Manager (ACM)

Free SSL certificates for AWS resources.

```
1. Request certificate in ACM
2. Validate domain ownership
3. Attach to ALB listener

ACM → Request certificate → Enter domain → DNS validation
```

### Option 2: Import Certificate

```
ACM → Import certificate
  - Certificate body (PEM)
  - Private key (PEM)
  - Certificate chain (optional)
```

### Creating HTTPS Listener

```
Load Balancer → Listeners → Add listener

Protocol: HTTPS
Port: 443
SSL Certificate: Select from ACM
Default action: Forward to target group
```

### HTTP to HTTPS Redirect

```
Load Balancer → Listeners → HTTP:80 → Edit rules

Change default action to:
  Redirect to HTTPS://#{host}:443/#{path}?#{query}
  Status code: 301 (Permanent)
```

---

## Health Check Configuration

### For Spring Boot Applications

```
Health Check Settings
┌─────────────────────────────────────────────────────┐
│ Path: /actuator/health                              │
│ Port: traffic-port (8080)                           │
│ Protocol: HTTP                                      │
│                                                     │
│ Advanced:                                           │
│ Healthy threshold: 2                                │
│ Unhealthy threshold: 3                              │
│ Timeout: 5 seconds                                  │
│ Interval: 30 seconds                                │
│ Success codes: 200                                  │
└─────────────────────────────────────────────────────┘
```

### Spring Boot Actuator Setup

```java
// application.properties
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=never
```

---

## Monitoring Load Balancer

### CloudWatch Metrics

| Metric | Description |
|--------|-------------|
| **RequestCount** | Number of requests |
| **TargetResponseTime** | Time for target to respond |
| **HealthyHostCount** | Number of healthy targets |
| **UnHealthyHostCount** | Number of unhealthy targets |
| **HTTPCode_ELB_5XX** | 5xx errors from ALB |

### Access Logs

Enable access logs to S3 for detailed request information.

```
Load Balancer → Attributes → Edit

Access logs: Enabled
S3 bucket: my-alb-logs-bucket
```

---

## Pricing

```
ALB Pricing Components
┌─────────────────────────────────────────────────────┐
│ 1. Hourly charge: ~$0.0225/hour (~$16/month)       │
│                                                     │
│ 2. LCU (Load Balancer Capacity Units):             │
│    - Based on connections, bandwidth, rules        │
│    - ~$0.008 per LCU-hour                          │
│                                                     │
│ Note: NOT covered by free tier                      │
└─────────────────────────────────────────────────────┘
```

---

## Best Practices

### Do's

```
✓ Use at least 2 Availability Zones
✓ Configure proper health checks
✓ Use HTTPS for production
✓ Enable access logging
✓ Set up CloudWatch alarms
✓ Use target group for Blue/Green deployments
```

### Don'ts

```
✗ Don't expose EC2 instances directly to internet
✗ Don't skip health checks
✗ Don't use single AZ (defeats purpose)
✗ Don't forget to update security groups
```

---

## Troubleshooting

| Issue | Possible Cause | Solution |
|-------|---------------|----------|
| 502 Bad Gateway | Target not responding | Check target health, security groups |
| 503 Service Unavailable | No healthy targets | Check application on instances |
| 504 Gateway Timeout | Target too slow | Increase timeout or optimize app |
| Health check failing | Wrong path/port | Verify health check configuration |

---

## Summary

| Concept | Description |
|---------|-------------|
| **ALB** | Layer 7 load balancer for HTTP/HTTPS |
| **NLB** | Layer 4 load balancer for TCP/UDP |
| **Target Group** | Collection of instances |
| **Health Check** | Monitors instance health |
| **Listener** | Handles incoming connections |

### Setup Checklist

- [ ] Create target group with health check
- [ ] Register EC2 instances
- [ ] Create ALB in multiple AZs
- [ ] Configure security groups
- [ ] (Optional) Add HTTPS with ACM certificate
- [ ] Set up CloudWatch alarms

## Next Topic

Continue to [Auto Scaling](./15-auto-scaling.md) to learn how to automatically adjust capacity based on demand.
