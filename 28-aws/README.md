# AWS Fundamentals

## Overview

This module provides comprehensive AWS training covering cloud fundamentals, core services, deployment strategies, serverless computing, and best practices. You'll learn to deploy a full-stack application (Angular frontend on S3, Spring Boot backend on EC2, MySQL database on RDS) while understanding the broader AWS ecosystem.

## Learning Objectives

By the end of this module, you will be able to:
- Understand cloud computing and major cloud providers
- Set up an AWS account with proper IAM security
- Manage costs with AWS pricing models and billing tools
- Launch and manage EC2 instances with EBS storage
- Create and configure RDS databases
- Understand VPC networking and security groups
- Deploy Spring Boot applications to EC2
- Host Angular applications on S3
- Implement load balancing and auto scaling
- Build serverless applications with Lambda
- Apply Well-Architected Framework principles

---

## Topics Covered

### AWS Foundations

#### 1. [AWS Introduction](./topics/01-aws-introduction.md)
Get started with AWS and understand core concepts.
- What is AWS and cloud computing
- Free tier overview and limits
- Account setup and billing alerts
- Regions and Availability Zones
- IAM users, roles, and security
- AWS CLI installation and configuration

#### 7. [Major Cloud Providers](./topics/07-cloud-providers.md)
Understand the cloud landscape.
- AWS, Azure, and Google Cloud comparison
- Cloud deployment models (public, private, hybrid)
- Service models (IaaS, PaaS, SaaS)
- Why learn AWS first

#### 8. [AWS Pricing Models](./topics/08-pricing-models.md)
Understand how cloud costs work.
- Pay-as-you-go pricing
- EC2 pricing options (On-Demand, Reserved, Spot)
- Storage and data transfer costs
- Cost estimation tools

#### 9. [AWS Billing Tools](./topics/09-billing-tools.md)
Monitor and manage your costs.
- Billing Dashboard
- AWS Cost Explorer
- AWS Budgets
- CloudWatch billing alarms
- Free tier usage alerts

#### 10. [Common AWS Use Cases](./topics/10-common-use-cases.md)
Understand practical applications.
- Web application hosting
- Static website hosting
- Serverless APIs
- File storage and processing
- Full-stack architectures

---

### AWS Core Services

#### 2. [EC2 Basics](./topics/02-ec2-basics.md)
Launch and manage virtual servers.
- EC2 concepts (AMI, instance types, key pairs)
- Launching an EC2 instance (t2.micro)
- Connecting via SSH
- Security groups for EC2
- Installing Java and dependencies
- Elastic IPs

#### 11. [Amazon EBS](./topics/11-ebs-storage.md)
Block storage for EC2 instances.
- EBS volume types (gp3, io2, st1, sc1)
- Creating and attaching volumes
- Snapshots and backups
- Encryption

#### 3. [RDS Setup](./topics/03-rds-setup.md)
Create a managed database.
- RDS overview and benefits
- Creating an RDS instance (db.t3.micro)
- Connection configuration
- Security groups for database access
- Spring Boot database configuration

#### 4. [Networking Essentials](./topics/04-networking-essentials.md)
Understand VPC and secure your resources.
- VPC and subnet basics
- Public vs private subnets
- Security groups in depth
- EC2 ↔ RDS communication
- Troubleshooting connectivity

---

### Deployment

#### 5. [Deploying Spring Boot](./topics/05-deploying-spring-boot.md)
Deploy your backend to EC2.
- Building and packaging JAR
- Transferring files to EC2
- Environment variables for configuration
- Running as a systemd service
- Updating and monitoring

#### 6. [Deploying Angular to S3](./topics/06-deploying-angular-s3.md)
Host your frontend on S3.
- Building Angular for production
- Creating S3 bucket with static website hosting
- Bucket policy for public access
- Handling Angular routing
- CORS configuration for API calls

---

### Monitoring and Operations

#### 12. [CloudWatch Basics](./topics/12-cloudwatch-basics.md)
Monitor your AWS resources.
- CloudWatch metrics
- CloudWatch Logs
- Creating alarms
- Dashboards
- CloudWatch Agent

#### 13. [AWS Trusted Advisor](./topics/13-trusted-advisor.md)
Get recommendations for your account.
- Five pillars of Trusted Advisor
- Security checks
- Cost optimization
- Service limits

---

### High Availability and Scaling

#### 14. [Load Balancing](./topics/14-load-balancing.md)
Distribute traffic across instances.
- Application Load Balancer (ALB)
- Network Load Balancer (NLB)
- Target groups and health checks
- HTTPS configuration

#### 15. [Auto Scaling](./topics/15-auto-scaling.md)
Automatically adjust capacity.
- Auto Scaling Groups
- Launch templates
- Scaling policies
- Target tracking vs step scaling

---

### Serverless (AWS Lambda)

#### 16. [Introduction to Serverless](./topics/16-serverless-intro.md)
Understand serverless computing.
- Serverless vs traditional
- AWS Lambda overview
- Use cases and benefits
- Limitations

#### 17. [Creating Lambda Functions](./topics/17-lambda-functions.md)
Build your first Lambda function.
- Handler function
- Event and context objects
- Configuration (memory, timeout)
- Environment variables
- IAM roles and permissions

#### 18. [Lambda Triggers and Event Sources](./topics/18-lambda-triggers.md)
Connect Lambda to other services.
- API Gateway triggers
- S3 triggers
- Scheduled events (EventBridge)
- SQS and DynamoDB triggers

#### 19. [Lambda with S3 and API Gateway](./topics/19-lambda-s3-api-gateway.md)
Build a practical serverless application.
- Image upload API
- S3 integration
- Presigned URLs
- CORS configuration

#### 20. [Monitoring Lambda](./topics/20-lambda-monitoring.md)
Monitor serverless applications.
- CloudWatch metrics for Lambda
- CloudWatch Logs
- X-Ray tracing
- Cost monitoring

---

### Best Practices

#### 21. [Well-Architected Framework](./topics/21-well-architected-framework.md)
AWS best practices for building systems.
- Operational Excellence
- Security
- Reliability
- Performance Efficiency
- Cost Optimization
- Sustainability

---

## Topic Flow

```
AWS Foundations
┌─────────────────────┐
│ 1. AWS Introduction │
└──────────┬──────────┘
           │
┌──────────┴──────────┐     ┌─────────────────────┐
│ 7. Cloud Providers  │────►│ 8. Pricing Models   │
└─────────────────────┘     └──────────┬──────────┘
                                       │
┌─────────────────────┐     ┌──────────┴──────────┐
│ 10. Use Cases       │◄────│ 9. Billing Tools    │
└──────────┬──────────┘     └─────────────────────┘
           │
           ▼
Core Services & Deployment
┌─────────────────────┐     ┌─────────────────────┐
│ 2. EC2 Basics       │────►│ 11. EBS Storage     │
└──────────┬──────────┘     └─────────────────────┘
           │
┌──────────┴──────────┐
│ 3. RDS Setup        │
└──────────┬──────────┘
           │
┌──────────┴──────────┐
│ 4. Networking       │
└──────────┬──────────┘
           │
     ┌─────┴─────┐
     ▼           ▼
┌─────────┐ ┌─────────┐
│5. Deploy│ │6. Deploy│
│ Backend │ │Frontend │
└────┬────┘ └────┬────┘
     └─────┬─────┘
           │
           ▼
Operations & Scaling
┌─────────────────────┐     ┌─────────────────────┐
│ 12. CloudWatch      │────►│ 13. Trusted Advisor │
└──────────┬──────────┘     └─────────────────────┘
           │
┌──────────┴──────────┐     ┌─────────────────────┐
│ 14. Load Balancing  │────►│ 15. Auto Scaling    │
└──────────┬──────────┘     └─────────────────────┘
           │
           ▼
Serverless
┌─────────────────────┐
│ 16. Serverless Intro│
└──────────┬──────────┘
           │
┌──────────┴──────────┐
│ 17. Lambda Functions│
└──────────┬──────────┘
           │
┌──────────┴──────────┐
│ 18. Lambda Triggers │
└──────────┬──────────┘
           │
┌──────────┴──────────┐     ┌─────────────────────┐
│ 19. Lambda + S3/API │────►│ 20. Lambda Monitor  │
└──────────┬──────────┘     └─────────────────────┘
           │
           ▼
┌─────────────────────┐
│ 21. Well-Architected│
└─────────────────────┘
```

---

## Architecture

### Basic Full-Stack Deployment

```
                         Internet
                             │
           ┌─────────────────┼─────────────────┐
           │                 │                 │
           ▼                 │                 ▼
   ┌───────────────┐         │         ┌─────────────────┐
   │      S3       │         │         │ Internet Gateway│
   │ ┌───────────┐ │         │         └────────┬────────┘
   │ │  Angular  │ │         │                  │
   │ │  (static) │ │─────────┼─────────────────▶│
   │ └───────────┘ │  API    │    ┌─────────────┴─────────────┐
   └───────────────┘  calls  │    │           VPC             │
                             │    │                           │
                             │    │  ┌─────────────────────┐  │
                        User │    │  │     EC2 (t2.micro)  │  │
                             │    │  │   Spring Boot :8080 │  │
                             │    │  └──────────┬──────────┘  │
                             │    │             │ port 5432   │
                             │    │             ▼             │
                             │    │  ┌─────────────────────┐  │
                             │    │  │   RDS (db.t3.micro) │  │
                             │    │  │     PostgreSQL      │  │
                             │    │  └─────────────────────┘  │
                             │    └───────────────────────────┘
                             │
                         Browser
```

### Production Architecture with High Availability

```
                              Internet
                                  │
                                  ▼
                          ┌───────────────┐
                          │  CloudFront   │
                          └───────┬───────┘
                                  │
                    ┌─────────────┼─────────────┐
                    │             │             │
                    ▼             │             ▼
            ┌───────────┐        │      ┌───────────┐
            │    S3     │        │      │    ALB    │
            │ (Angular) │        │      └─────┬─────┘
            └───────────┘        │            │
                                 │   ┌────────┼────────┐
                                 │   │        │        │
                                 │   ▼        ▼        ▼
                                 │ ┌────┐  ┌────┐  ┌────┐
                                 │ │EC2 │  │EC2 │  │EC2 │
                                 │ └──┬─┘  └──┬─┘  └──┬─┘
                                 │    └───────┼───────┘
                                 │            │
                                 │    ┌───────┴───────┐
                                 │    │  RDS Multi-AZ │
                                 │    └───────────────┘
```

---

## Key Concepts Summary

| Concept | Description |
|---------|-------------|
| **S3** | Simple Storage Service (object/static file hosting) |
| **EC2** | Virtual servers (Elastic Compute Cloud) |
| **EBS** | Block storage for EC2 instances |
| **RDS** | Managed relational database service |
| **VPC** | Virtual Private Cloud (your network) |
| **Security Group** | Firewall rules for instances |
| **IAM** | Identity and Access Management |
| **CloudWatch** | Monitoring and logging service |
| **Lambda** | Serverless compute service |
| **API Gateway** | Managed API service |
| **ALB** | Application Load Balancer |
| **Auto Scaling** | Automatic capacity adjustment |

---

## Free Tier Limits

| Service | Free Allowance | Duration |
|---------|----------------|----------|
| S3 | 5 GB storage, 20K GET requests | 12 months |
| EC2 | 750 hrs/month t2.micro | 12 months |
| RDS | 750 hrs/month db.t3.micro | 12 months |
| EBS | 30 GB storage | 12 months |
| Lambda | 1M requests, 400K GB-seconds | Always free |
| CloudWatch | 10 custom metrics, 5GB logs | Always free |

**Set billing alerts to avoid unexpected charges!**

---

## Prerequisites

- AWS account (free tier)
- Spring Boot application to deploy
- Angular application to deploy
- Basic Linux command line knowledge
- SSH client installed

## Additional Resources

- [AWS Free Tier](https://aws.amazon.com/free/)
- [AWS Well-Architected](https://aws.amazon.com/architecture/well-architected/)
- [S3 Documentation](https://docs.aws.amazon.com/s3/)
- [EC2 Documentation](https://docs.aws.amazon.com/ec2/)
- [RDS Documentation](https://docs.aws.amazon.com/rds/)
- [Lambda Documentation](https://docs.aws.amazon.com/lambda/)
- [AWS CLI Reference](https://docs.aws.amazon.com/cli/)

---

**Duration:** 5 days | **Difficulty:** Beginner to Intermediate | **Prerequisites:** Spring Boot, Linux basics
