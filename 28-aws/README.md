# AWS Fundamentals

## Overview

This module covers AWS essentials for deploying a monolithic Spring Boot application to EC2 with RDS. Focus is on free tier services and practical deployment skills.

## Learning Objectives

By the end of this module, you will be able to:
- Set up an AWS account and configure IAM users
- Launch and manage EC2 instances
- Create and configure RDS databases
- Understand VPC networking and security groups
- Deploy a Spring Boot application to AWS

---

## Topics Covered

### 1. [AWS Introduction](./topics/01-aws-introduction.md)
Get started with AWS and understand core concepts.

- What is AWS and cloud computing
- Free tier overview and limits
- Account setup and billing alerts
- IAM users, roles, and security
- AWS CLI installation and configuration

### 2. [EC2 Basics](./topics/02-ec2-basics.md)
Launch and manage virtual servers.

- EC2 concepts (AMI, instance types, key pairs)
- Launching an EC2 instance (t2.micro)
- Connecting via SSH
- Security groups for EC2
- Installing Java and dependencies

### 3. [RDS Setup](./topics/03-rds-setup.md)
Create a managed MySQL/PostgreSQL database.

- RDS overview and benefits
- Creating an RDS instance (db.t3.micro)
- Connection configuration
- Security groups for database access
- Spring Boot database configuration

### 4. [Networking Essentials](./topics/04-networking-essentials.md)
Understand VPC and secure your resources.

- VPC and subnet basics
- Security groups in depth
- EC2 ↔ RDS communication
- Troubleshooting connectivity

### 5. [Deploying Spring Boot](./topics/05-deploying-spring-boot.md)
Deploy your application to production.

- Building and packaging JAR
- Transferring files to EC2
- Environment variables for configuration
- Running as a systemd service
- Updating and monitoring

---

## Topic Flow

```
┌─────────────────────┐
│ 1. AWS Introduction │  Account, IAM, CLI
└──────────┬──────────┘
           ▼
┌─────────────────────┐
│ 2. EC2 Basics       │  Launch virtual server
└──────────┬──────────┘
           ▼
┌─────────────────────┐
│ 3. RDS Setup        │  Create database
└──────────┬──────────┘
           ▼
┌─────────────────────┐
│ 4. Networking       │  Security groups
└──────────┬──────────┘
           ▼
┌─────────────────────┐
│ 5. Deploy App       │  Spring Boot on EC2
└─────────────────────┘
```

---

## Architecture

```
                    Internet
                        │
                        ▼
              ┌─────────────────┐
              │ Internet Gateway│
              └────────┬────────┘
                       │
         ┌─────────────┴─────────────┐
         │           VPC             │
         │                           │
         │  ┌─────────────────────┐  │
         │  │     EC2 (t2.micro)  │  │
         │  │   Spring Boot :8080 │  │
         │  └──────────┬──────────┘  │
         │             │ port 3306   │
         │             ▼             │
         │  ┌─────────────────────┐  │
         │  │   RDS (db.t3.micro) │  │
         │  │       MySQL         │  │
         │  └─────────────────────┘  │
         └───────────────────────────┘
```

---

## Key Concepts Summary

| Concept | Description |
|---------|-------------|
| **EC2** | Virtual servers (Elastic Compute Cloud) |
| **RDS** | Managed relational database service |
| **VPC** | Virtual Private Cloud (your network) |
| **Security Group** | Firewall rules for instances |
| **IAM** | Identity and Access Management |
| **AMI** | Amazon Machine Image (OS template) |

---

## Free Tier Limits

| Service | Free Allowance | Duration |
|---------|----------------|----------|
| EC2 | 750 hrs/month t2.micro | 12 months |
| RDS | 750 hrs/month db.t3.micro | 12 months |
| EBS | 30 GB storage | 12 months |

**Set billing alerts to avoid unexpected charges!**

---

## Prerequisites

- AWS account (free tier)
- Spring Boot application to deploy
- Basic Linux command line knowledge
- SSH client installed

## Additional Resources

- [AWS Free Tier](https://aws.amazon.com/free/)
- [EC2 Documentation](https://docs.aws.amazon.com/ec2/)
- [RDS Documentation](https://docs.aws.amazon.com/rds/)
- [AWS CLI Reference](https://docs.aws.amazon.com/cli/)

---

## Next Steps

After completing this module, consider learning:
- Elastic Load Balancing for high availability
- S3 for file storage
- CI/CD with CodePipeline
- Docker deployment on ECS

---

**Duration:** 3 days | **Difficulty:** Intermediate | **Prerequisites:** Spring Boot, Linux basics
