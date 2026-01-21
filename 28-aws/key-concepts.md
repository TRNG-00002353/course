# AWS Key Concepts

## Overview

Essential AWS concepts for deploying a monolithic Spring Boot application to EC2 with RDS.

---

## 1. AWS Fundamentals

### Cloud Computing Model

```
On-Premises              Cloud (AWS)
─────────────            ───────────
Buy hardware    →        Rent resources
Setup months    →        Launch minutes
CapEx           →        OpEx (pay-as-you-go)
You maintain    →        AWS maintains
```

### Free Tier (12 Months)

| Service | Free Allowance |
|---------|----------------|
| EC2 | 750 hrs/month t2.micro |
| RDS | 750 hrs/month db.t3.micro |
| EBS | 30 GB storage |

### IAM Best Practices

```
Root Account → Create IAM Admin → Use IAM Admin Daily
                    ↓
              Enable MFA on both
```

---

## 2. EC2 (Elastic Compute Cloud)

### Key Components

| Component | Description |
|-----------|-------------|
| **Instance** | Virtual server |
| **AMI** | OS template (Amazon Linux, Ubuntu) |
| **Instance Type** | Hardware (t2.micro = 1 vCPU, 1GB RAM) |
| **Key Pair** | SSH authentication |
| **Security Group** | Firewall rules |

### Launch Instance (Quick Reference)

```
AMI: Amazon Linux 2023
Instance Type: t2.micro (free tier)
Key Pair: Create new → Download .pem
Security Group: SSH (22), HTTP (80), 8080
Public IP: Enable
```

### SSH Connection

```bash
chmod 400 my-key.pem
ssh -i my-key.pem ec2-user@<PUBLIC-IP>
```

---

## 3. RDS (Relational Database Service)

### Why RDS?

| Self-Managed | RDS |
|--------------|-----|
| Install database | Pre-configured |
| Manual backups | Automatic backups |
| Apply patches | Automatic patching |
| Scale manually | Easy scaling |

### Create Instance (Quick Reference)

```
Engine: MySQL 8.0
Template: Free tier
Instance: db.t3.micro
Storage: 20 GB (no autoscaling)
Public Access: Yes (for setup) → No (for production)
```

### Connection String

```
jdbc:mysql://<ENDPOINT>:3306/<DATABASE>

Example:
jdbc:mysql://mydb.abc123.us-east-1.rds.amazonaws.com:3306/myappdb
```

---

## 4. Security Groups

### EC2 Security Group

| Type | Port | Source | Purpose |
|------|------|--------|---------|
| SSH | 22 | My IP | Admin access |
| HTTP | 80 | 0.0.0.0/0 | Web traffic |
| Custom | 8080 | 0.0.0.0/0 | Spring Boot |

### RDS Security Group

| Type | Port | Source | Purpose |
|------|------|--------|---------|
| MySQL | 3306 | EC2-SG-ID | From EC2 only |

### Key Rule: Reference Security Groups

```
RDS inbound: Allow MySQL from sg-xxxxx (EC2 security group)
NOT from IP address
```

---

## 5. Deployment

### Build and Transfer

```bash
# Build
./mvnw clean package -DskipTests

# Transfer
scp -i key.pem target/app.jar ec2-user@IP:/opt/myapp/
```

### Environment Variables

```bash
# /opt/myapp/.env
DB_HOST=mydb.abc123.us-east-1.rds.amazonaws.com
DB_NAME=myappdb
DB_USERNAME=admin
DB_PASSWORD=secret
```

### Systemd Service

```ini
# /etc/systemd/system/myapp.service
[Unit]
Description=Spring Boot App
After=network.target

[Service]
Type=simple
User=ec2-user
EnvironmentFile=/opt/myapp/.env
ExecStart=/usr/bin/java -jar /opt/myapp/app.jar
Restart=on-failure

[Install]
WantedBy=multi-user.target
```

### Service Commands

```bash
sudo systemctl start myapp     # Start
sudo systemctl stop myapp      # Stop
sudo systemctl restart myapp   # Restart
sudo systemctl status myapp    # Status
journalctl -u myapp -f         # Logs
```

---

## Quick Reference

### AWS CLI Setup

```bash
aws configure
# Access Key: AKIA...
# Secret Key: ...
# Region: us-east-1
# Output: json
```

### Common Commands

```bash
# EC2
aws ec2 describe-instances
aws ec2 start-instances --instance-ids i-xxx
aws ec2 stop-instances --instance-ids i-xxx

# RDS
aws rds describe-db-instances
```

### Deployment Script

```bash
#!/bin/bash
# deploy.sh
./mvnw clean package -DskipTests
scp -i key.pem target/*.jar ec2-user@$IP:/opt/myapp/app.jar
ssh -i key.pem ec2-user@$IP "sudo systemctl restart myapp"
```

---

## Architecture Diagram

```
┌────────────────────────────────────────────────┐
│                    AWS VPC                      │
│                                                 │
│   ┌─────────────────┐    ┌─────────────────┐  │
│   │      EC2        │    │      RDS        │  │
│   │  ┌───────────┐  │    │  ┌───────────┐  │  │
│   │  │Spring Boot│  │───▶│  │   MySQL   │  │  │
│   │  │   :8080   │  │3306│  │   :3306   │  │  │
│   │  └───────────┘  │    │  └───────────┘  │  │
│   │   my-app-sg     │    │   my-db-sg      │  │
│   └────────┬────────┘    └─────────────────┘  │
│            │                                   │
└────────────┼───────────────────────────────────┘
             │
        Internet (HTTP :8080)
```

---

## Checklist

### Initial Setup
- [ ] Create AWS account
- [ ] Set billing alerts
- [ ] Create IAM admin user
- [ ] Enable MFA
- [ ] Install AWS CLI

### EC2 Setup
- [ ] Launch t2.micro instance
- [ ] Create key pair
- [ ] Configure security group (22, 80, 8080)
- [ ] Connect via SSH
- [ ] Install Java 17

### RDS Setup
- [ ] Create db.t3.micro instance
- [ ] Note endpoint and credentials
- [ ] Configure security group (allow EC2)
- [ ] Test connection from EC2

### Deployment
- [ ] Build JAR locally
- [ ] Transfer to EC2
- [ ] Create environment file
- [ ] Create systemd service
- [ ] Start and verify

---

## Troubleshooting

| Issue | Check |
|-------|-------|
| Can't SSH | Security group allows port 22 from your IP |
| Can't connect to RDS | RDS security group allows EC2 security group |
| App not accessible | Security group allows port 8080 |
| App crashes | Check logs: `journalctl -u myapp` |
| Wrong Java version | Install Java 17: `sudo yum install java-17-amazon-corretto` |
