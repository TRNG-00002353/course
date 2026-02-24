# Cloud Providers Overview

## The Big Three

The cloud market is dominated by three major providers:

| Provider | Market Share | Started | Known For |
|----------|--------------|---------|-----------|
| **AWS** (Amazon) | ~32% | 2006 | Pioneer, broadest services |
| **Azure** (Microsoft) | ~23% | 2010 | Enterprise integration |
| **GCP** (Google) | ~10% | 2008 | Data analytics, AI/ML |

---

## Quick Comparison

| Aspect | AWS | Azure | GCP |
|--------|-----|-------|-----|
| **Best For** | General purpose, startups | Microsoft shops, enterprise | Data/ML, Kubernetes |
| **Strengths** | Most services, mature | Office 365, .NET support | BigQuery, clean UX |
| **Considerations** | Complex pricing | Some services less mature | Smaller ecosystem |

---

## Service Names Across Providers

The same concepts exist across all providers - only the names differ:

### Compute

| What It Does | AWS | Azure | GCP |
|--------------|-----|-------|-----|
| Virtual Machines | EC2 | Virtual Machines | Compute Engine |
| Serverless Functions | Lambda | Functions | Cloud Functions |
| Kubernetes | EKS | AKS | GKE |

### Storage

| What It Does | AWS | Azure | GCP |
|--------------|-----|-------|-----|
| Object Storage | S3 | Blob Storage | Cloud Storage |
| Block Storage | EBS | Managed Disks | Persistent Disk |

### Database

| What It Does | AWS | Azure | GCP |
|--------------|-----|-------|-----|
| Relational DB | RDS | SQL Database | Cloud SQL |
| NoSQL | DynamoDB | Cosmos DB | Firestore |

---

## Choosing a Provider

### Simple Decision Guide

| If you... | Consider... |
|-----------|-------------|
| Are a startup or have no preference | **AWS** - largest ecosystem |
| Use Microsoft products (.NET, Office) | **Azure** - seamless integration |
| Focus on data analytics or ML | **GCP** - BigQuery, Vertex AI |
| Want Kubernetes-native development | **GCP** - Google created K8s |

### Key Factors

- **Existing stack** - What tools does your team already use?
- **Use case** - What are you building?
- **Team skills** - What does your team know?
- **Geography** - Where are your users?

---

## Free Tiers for Learning

| Provider | What You Get |
|----------|--------------|
| **AWS** | 12 months free tier + always-free services |
| **Azure** | 12 months free + $200 credit |
| **GCP** | 90 days + $300 credit + always-free tier |

---

## Summary

| Concept | Key Point |
|---------|-----------|
| **Concepts are universal** | Learn concepts, not just one provider |
| **Names differ, functions don't** | EC2 = Virtual Machines = Compute Engine |
| **Choose based on needs** | Existing stack, use case, team skills |
| **Start with one** | You can expand to others later |

The provider you choose matters less than understanding cloud concepts. Once you understand one cloud well, moving to others becomes easier because the concepts are the same.
