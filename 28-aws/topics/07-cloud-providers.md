# Major Cloud Providers

## What is Cloud Computing?

Cloud computing delivers computing services over the internet. Instead of owning hardware, you rent resources from cloud providers.

```
On-Premises                        Cloud Computing
┌─────────────────────┐           ┌─────────────────────┐
│ You buy hardware    │           │ Provider owns       │
│ You maintain it     │     →     │ You rent resources  │
│ Fixed capacity      │           │ Scale up/down       │
│ High upfront cost   │           │ Pay for what you use│
└─────────────────────┘           └─────────────────────┘
```

---

## The Big Three Cloud Providers

Three companies dominate the cloud market:

```
Market Share (Approximate)
┌────────────────────────────────────────────────────┐
│ AWS          ████████████████████████████░░  ~32%  │
│ Azure        ███████████████████████░░░░░░░  ~23%  │
│ Google Cloud ██████████████░░░░░░░░░░░░░░░░  ~10%  │
│ Others       ███████████████████████████████  ~35%  │
└────────────────────────────────────────────────────┘
```

### Amazon Web Services (AWS)

- **Launched:** 2006 (first major cloud provider)
- **Strengths:** Largest service catalog, most mature, extensive documentation
- **Best for:** Wide variety of use cases, enterprise applications

### Microsoft Azure

- **Launched:** 2010
- **Strengths:** Microsoft integration (Office 365, Active Directory), hybrid cloud
- **Best for:** Organizations using Microsoft tools, Windows-based workloads

### Google Cloud Platform (GCP)

- **Launched:** 2008
- **Strengths:** Data analytics, machine learning, Kubernetes
- **Best for:** Data-intensive applications, containerized workloads

---

## Quick Comparison

| Feature | AWS | Azure | GCP |
|---------|-----|-------|-----|
| **Compute** | EC2 | Virtual Machines | Compute Engine |
| **Storage** | S3 | Blob Storage | Cloud Storage |
| **Database** | RDS | SQL Database | Cloud SQL |
| **Serverless** | Lambda | Functions | Cloud Functions |
| **Container Orchestration** | EKS | AKS | GKE |

---

## Service Name Mapping

Same concepts, different names:

| Concept | AWS | Azure | GCP |
|---------|-----|-------|-----|
| Virtual Server | EC2 | VM | Compute Engine |
| Object Storage | S3 | Blob Storage | Cloud Storage |
| Relational Database | RDS | SQL Database | Cloud SQL |
| NoSQL Database | DynamoDB | CosmosDB | Firestore |
| Virtual Network | VPC | VNet | VPC |
| DNS Service | Route 53 | Azure DNS | Cloud DNS |
| CDN | CloudFront | Azure CDN | Cloud CDN |
| Serverless Functions | Lambda | Functions | Cloud Functions |

---

## Why Learn AWS First?

```
Reasons to Start with AWS
┌─────────────────────────────────────────────────────┐
│ 1. Market Leader                                    │
│    - Most job opportunities                         │
│    - Largest community and resources                │
│                                                     │
│ 2. Comprehensive Free Tier                          │
│    - 12 months of free resources                    │
│    - Great for learning                             │
│                                                     │
│ 3. Transferable Skills                              │
│    - Cloud concepts are universal                   │
│    - Easy to learn other providers later            │
│                                                     │
│ 4. Most Services                                    │
│    - 200+ services available                        │
│    - Whatever you need, AWS has it                  │
└─────────────────────────────────────────────────────┘
```

---

## Cloud Deployment Models

### Public Cloud

Resources shared among multiple customers (most common):
- AWS, Azure, GCP
- Cost-effective, scalable

### Private Cloud

Dedicated infrastructure for one organization:
- On-premises or hosted
- Maximum control and security

### Hybrid Cloud

Combination of public and private:
- Sensitive data on-premises
- Scalable workloads in public cloud

```
Hybrid Cloud Architecture
┌─────────────────┐         ┌─────────────────┐
│  Private Cloud  │◄───────►│  Public Cloud   │
│  (On-Premises)  │   VPN   │  (AWS/Azure)    │
│                 │         │                 │
│ Sensitive Data  │         │ Web Apps        │
│ Legacy Systems  │         │ Scalable Loads  │
└─────────────────┘         └─────────────────┘
```

---

## Cloud Service Models

Understanding what you manage vs. what the provider manages:

```
┌─────────────────────────────────────────────────────────────┐
│        On-Prem    IaaS       PaaS       SaaS               │
├─────────────────────────────────────────────────────────────┤
│ Apps      You      You        You      Provider            │
│ Data      You      You        You      Provider            │
│ Runtime   You      You      Provider   Provider            │
│ OS        You      You      Provider   Provider            │
│ Virtualization You Provider Provider   Provider            │
│ Servers   You    Provider  Provider   Provider            │
│ Storage   You    Provider  Provider   Provider            │
│ Network   You    Provider  Provider   Provider            │
└─────────────────────────────────────────────────────────────┘

You = You Manage    Provider = Cloud Provider Manages
```

### IaaS (Infrastructure as a Service)

- You get virtual servers, storage, networking
- You manage OS, runtime, applications
- Example: AWS EC2, Azure VMs

### PaaS (Platform as a Service)

- You get a platform to deploy applications
- Provider manages infrastructure and runtime
- Example: AWS Elastic Beanstalk, Azure App Service

### SaaS (Software as a Service)

- You use ready-made applications
- Provider manages everything
- Example: Gmail, Salesforce, Office 365

---

## Other Notable Cloud Providers

| Provider | Specialty |
|----------|-----------|
| **IBM Cloud** | Enterprise, AI (Watson) |
| **Oracle Cloud** | Database workloads |
| **Alibaba Cloud** | Asia-Pacific market |
| **DigitalOcean** | Developer-friendly, simple |
| **Linode** | Affordable VPS hosting |

---

## Summary

| Concept | Description |
|---------|-------------|
| **Cloud Computing** | Renting computing resources over the internet |
| **AWS** | Market leader, most services |
| **Azure** | Best Microsoft integration |
| **GCP** | Strong in data and ML |
| **IaaS** | Rent infrastructure, manage the rest |
| **PaaS** | Deploy apps, provider manages platform |
| **SaaS** | Use ready-made software |

### Key Takeaway

Cloud skills are transferable. Learning AWS provides a strong foundation that applies to any cloud provider.

## Next Topic

Continue to [AWS Pricing Models](./08-pricing-models.md) to understand how cloud costs work.
