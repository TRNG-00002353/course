# Common AWS Use Cases

## Understanding AWS Through Real Applications

AWS services make more sense when you see how they're used in real-world scenarios. This guide covers the most common use cases for beginners.

---

## Use Case 1: Web Application Hosting

The most common use case - hosting websites and web applications.

### Simple Architecture

```
                    Internet
                        │
                        ▼
                ┌───────────────┐
                │  EC2 Instance │
                │  (Web Server) │
                │   + App Code  │
                └───────┬───────┘
                        │
                        ▼
                ┌───────────────┐
                │      RDS      │
                │  (Database)   │
                └───────────────┘
```

### AWS Services Used

| Service | Role |
|---------|------|
| **EC2** | Hosts web server and application |
| **RDS** | Stores application data |
| **Security Groups** | Controls network access |
| **Elastic IP** | Provides static IP address |

### Real Example: Blog Platform

- EC2 runs WordPress or custom app
- RDS stores posts, users, comments
- Monthly cost: ~$20-30 (or free tier)

---

## Use Case 2: Static Website Hosting

Host websites that don't need server-side processing.

### Architecture

```
                    Internet
                        │
                        ▼
                ┌───────────────┐
                │   S3 Bucket   │
                │ (Static Site) │
                │  HTML/CSS/JS  │
                └───────────────┘
```

### AWS Services Used

| Service | Role |
|---------|------|
| **S3** | Stores and serves static files |
| **CloudFront** | CDN for faster delivery (optional) |
| **Route 53** | Custom domain name (optional) |

### Real Example: Portfolio Website

- React/Angular app built and deployed to S3
- Costs: ~$0.50/month for small sites
- No server management needed

---

## Use Case 3: Full-Stack Application

Combining frontend and backend on AWS.

### Architecture

```
                        Internet
                            │
            ┌───────────────┴───────────────┐
            │                               │
            ▼                               ▼
    ┌───────────────┐               ┌───────────────┐
    │   S3 Bucket   │               │  EC2 Instance │
    │   (Angular)   │──── API ────► │ (Spring Boot) │
    │   Frontend    │    calls      │   Backend     │
    └───────────────┘               └───────┬───────┘
                                            │
                                            ▼
                                    ┌───────────────┐
                                    │      RDS      │
                                    │  (Database)   │
                                    └───────────────┘
```

### AWS Services Used

| Service | Role |
|---------|------|
| **S3** | Hosts Angular/React frontend |
| **EC2** | Runs Spring Boot/Node.js backend |
| **RDS** | PostgreSQL/MySQL database |
| **VPC** | Network isolation |

### This Is What You're Building!

This architecture is exactly what the AWS module teaches you to deploy.

---

## Use Case 4: Serverless API

Build APIs without managing servers.

### Architecture

```
                    Internet
                        │
                        ▼
                ┌───────────────┐
                │  API Gateway  │
                └───────┬───────┘
                        │
                        ▼
                ┌───────────────┐
                │    Lambda     │
                │  (Functions)  │
                └───────┬───────┘
                        │
                        ▼
                ┌───────────────┐
                │   DynamoDB    │
                │  (Database)   │
                └───────────────┘
```

### AWS Services Used

| Service | Role |
|---------|------|
| **API Gateway** | HTTP endpoint management |
| **Lambda** | Runs code on demand |
| **DynamoDB** | NoSQL database |

### Benefits

- No servers to manage
- Pay only when code runs
- Automatic scaling

### Real Example: Contact Form API

- Lambda processes form submissions
- Stores in DynamoDB
- Costs: Nearly free for low traffic

---

## Use Case 5: File Storage and Sharing

Store and serve files at scale.

### Architecture

```
                Users
                  │
                  ▼
          ┌───────────────┐
          │   S3 Bucket   │
          │   (Storage)   │
          └───────────────┘
                  │
        ┌─────────┼─────────┐
        │         │         │
        ▼         ▼         ▼
    Images    Videos    Documents
```

### AWS Services Used

| Service | Role |
|---------|------|
| **S3** | Object storage |
| **CloudFront** | Fast content delivery |
| **IAM** | Access control |

### Real Example: Media Storage

- User uploads profile pictures
- App stores in S3
- CloudFront delivers globally
- Costs: ~$0.023/GB/month

---

## Use Case 6: Data Processing

Process large amounts of data.

### Batch Processing Architecture

```
        Data Source
             │
             ▼
    ┌───────────────┐
    │   S3 Bucket   │
    │  (Raw Data)   │
    └───────┬───────┘
            │
            ▼
    ┌───────────────┐
    │    Lambda     │
    │  or EC2/EMR   │
    └───────┬───────┘
            │
            ▼
    ┌───────────────┐
    │   S3 Bucket   │
    │(Processed Data│
    └───────────────┘
```

### AWS Services Used

| Service | Role |
|---------|------|
| **S3** | Data lake storage |
| **Lambda** | Small data processing |
| **EMR** | Big data processing |
| **Glue** | ETL jobs |

---

## Use Case 7: Development and Testing

Create environments for development teams.

### Multi-Environment Setup

```
┌─────────────────────────────────────────────────┐
│                    AWS Account                   │
│                                                  │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐      │
│  │   Dev    │  │  Staging │  │   Prod   │      │
│  │   ENV    │  │   ENV    │  │   ENV    │      │
│  │          │  │          │  │          │      │
│  │ EC2+RDS  │  │ EC2+RDS  │  │ EC2+RDS  │      │
│  │ (small)  │  │ (medium) │  │ (large)  │      │
│  └──────────┘  └──────────┘  └──────────┘      │
│                                                  │
└─────────────────────────────────────────────────┘
```

### Benefits

- Identical environments
- Easy to spin up/tear down
- Cost control with tags

---

## Use Case 8: Backup and Disaster Recovery

Protect your data and ensure business continuity.

### Backup Architecture

```
On-Premises                          AWS
┌───────────────┐            ┌───────────────┐
│   Servers     │ ─────────► │   S3 Bucket   │
│   (Primary)   │  Backup    │   (Archive)   │
└───────────────┘            └───────────────┘
                                     │
                             S3 Glacier for
                             long-term storage
```

### AWS Services Used

| Service | Role |
|---------|------|
| **S3** | Backup storage |
| **S3 Glacier** | Long-term archive |
| **AWS Backup** | Centralized backup management |

---

## Matching Services to Use Cases

| Use Case | Primary Services |
|----------|-----------------|
| Web App | EC2, RDS, VPC |
| Static Site | S3, CloudFront |
| API Backend | Lambda, API Gateway |
| File Storage | S3 |
| Database | RDS, DynamoDB |
| Big Data | EMR, Redshift |
| Machine Learning | SageMaker |
| Containers | ECS, EKS |

---

## Choosing the Right Architecture

### Decision Flow

```
Is it a static website?
├─ Yes → S3 + CloudFront
└─ No
   │
   Does it need a database?
   ├─ No → Lambda + API Gateway
   └─ Yes
      │
      Is traffic predictable?
      ├─ Yes → EC2 + RDS
      └─ No → Consider serverless or auto-scaling
```

### Cost vs. Complexity Trade-off

```
                    High
                     │
        Complexity   │    ┌─────────────────┐
                     │    │ Kubernetes/EKS  │
                     │    └─────────────────┘
                     │         ┌─────────────────┐
                     │         │ EC2 + Auto Scale│
                     │         └─────────────────┘
                     │              ┌─────────────────┐
                     │              │  Basic EC2+RDS  │
                     │              └─────────────────┘
                     │                   ┌─────────────────┐
                     │                   │    Serverless   │
                     │                   └─────────────────┘
                     │                        ┌─────────────────┐
                     │                        │   S3 Static     │
                    Low                       └─────────────────┘
                     └────────────────────────────────────────────►
                                   Cost/Maintenance
```

---

## Summary

| Use Case | Best AWS Approach |
|----------|------------------|
| **Simple Website** | S3 static hosting |
| **Web Application** | EC2 + RDS |
| **Full-Stack App** | S3 (frontend) + EC2 (backend) + RDS |
| **Serverless API** | API Gateway + Lambda |
| **File Storage** | S3 |
| **Data Processing** | Lambda or EMR |

### Key Takeaway

Start simple (EC2 + RDS), then evolve to more sophisticated architectures as your needs grow.

## Next Topic

Continue to [Amazon EBS](./11-ebs-storage.md) to learn about block storage for EC2 instances.
