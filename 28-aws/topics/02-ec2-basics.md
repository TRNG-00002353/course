# EC2 Basics

## What is EC2?

Amazon Elastic Compute Cloud (EC2) provides virtual servers in the cloud. You can launch, configure, and terminate instances on demand.

```
EC2 Instance = Virtual Server
┌─────────────────────────────────┐
│  Your Application               │
├─────────────────────────────────┤
│  Operating System (Linux/Win)   │
├─────────────────────────────────┤
│  Virtual Hardware (CPU, RAM)    │
└─────────────────────────────────┘
     Running on AWS infrastructure
```

---

## Key Concepts

| Concept | Description |
|---------|-------------|
| **Instance** | A virtual server |
| **AMI** | Amazon Machine Image (OS template) |
| **Instance Type** | Hardware configuration (CPU, RAM) |
| **Key Pair** | SSH keys for secure login |
| **Security Group** | Firewall rules |
| **EBS Volume** | Persistent storage (hard drive) |

---

## Instance Types

Instance types define the hardware: CPU, memory, storage, network.

### Naming Convention

```
t2.micro
│  │
│  └── Size (nano, micro, small, medium, large, xlarge)
└───── Family (t=general, m=balanced, c=compute, r=memory)
```

### Free Tier Instance

| Type | vCPU | Memory | Free Tier |
|------|------|--------|-----------|
| **t2.micro** | 1 | 1 GB | 750 hrs/month |
| t3.micro | 2 | 1 GB | 750 hrs/month (some regions) |

**Use `t2.micro` for learning** - sufficient for small Spring Boot apps.

---

## Launching an EC2 Instance

### Step 1: Open EC2 Console

```
AWS Console → Services → EC2 → Launch Instance
```

### Step 2: Configure Instance

**Name and Tags:**
```
Name: my-app-server
```

**Application and OS Images (AMI):**
```
Amazon Linux 2023 AMI (Free tier eligible)
Architecture: 64-bit (x86)
```

**Instance Type:**
```
t2.micro (Free tier eligible)
  1 vCPU, 1 GB Memory
```

**Key Pair (Login):**
```
Create new key pair:
  Name: my-key-pair
  Type: RSA
  Format: .pem (Linux/Mac) or .ppk (Windows/PuTTY)

→ Download and save securely!
```

**Network Settings:**
```
VPC: Default VPC
Subnet: No preference
Auto-assign public IP: Enable
Security group: Create new
  Name: my-app-sg
  Rules:
    ✓ SSH (port 22) from My IP
    ✓ HTTP (port 80) from Anywhere
    ✓ Custom TCP (port 8080) from Anywhere  [for Spring Boot]
```

**Storage:**
```
8 GB gp3 (default, sufficient for learning)
```

### Step 3: Launch

Click "Launch Instance" and wait for status: **Running**

---

## Connecting to EC2

### Find Your Instance Details

```
EC2 → Instances → Select instance
  Public IPv4 address: 54.123.45.67
  Public IPv4 DNS: ec2-54-123-45-67.compute-1.amazonaws.com
```

### SSH from Terminal (Linux/Mac)

```bash
# Set key file permissions (required)
chmod 400 my-key-pair.pem

# Connect
ssh -i my-key-pair.pem ec2-user@54.123.45.67
```

**Default usernames by AMI:**
| AMI | Username |
|-----|----------|
| Amazon Linux | ec2-user |
| Ubuntu | ubuntu |
| Debian | admin |

### SSH from Windows (PuTTY)

1. Convert .pem to .ppk using PuTTYgen
2. Open PuTTY:
   - Host: `ec2-user@54.123.45.67`
   - Connection → SSH → Auth → Private key: select .ppk file
3. Click Open

### Using EC2 Instance Connect (Browser)

```
EC2 → Instances → Select instance → Connect
  → EC2 Instance Connect → Connect
```

Opens terminal in browser (no key file needed).

---

## Security Groups

Security groups act as virtual firewalls controlling inbound/outbound traffic.

### Inbound Rules (Traffic IN to instance)

| Type | Port | Source | Purpose |
|------|------|--------|---------|
| SSH | 22 | My IP | Admin access |
| HTTP | 80 | 0.0.0.0/0 | Web traffic |
| HTTPS | 443 | 0.0.0.0/0 | Secure web |
| Custom TCP | 8080 | 0.0.0.0/0 | Spring Boot |

### Managing Security Groups

```
EC2 → Security Groups → Select group → Edit inbound rules

Add rule:
  Type: Custom TCP
  Port: 8080
  Source: Anywhere (0.0.0.0/0)
  Description: Spring Boot
```

### Best Practices

- **SSH**: Restrict to your IP only (`My IP`)
- **Application ports**: Open only what's needed
- **Database ports**: Never expose publicly (use internal access)

---

## Basic Instance Management

### Start/Stop/Terminate

```
EC2 → Instances → Select instance → Instance state
  → Stop instance    (pause, keeps data)
  → Start instance   (resume)
  → Terminate        (delete permanently)
```

**Billing:**
- **Stopped**: No compute charges, still pay for EBS storage
- **Terminated**: No charges, data deleted

### Instance States

```
pending → running → stopping → stopped
                 → shutting-down → terminated
```

---

## Installing Software on EC2

After connecting via SSH:

### Update System

```bash
# Amazon Linux
sudo yum update -y

# Ubuntu
sudo apt update && sudo apt upgrade -y
```

### Install Java (for Spring Boot)

```bash
# Amazon Linux 2023
sudo yum install java-17-amazon-corretto -y

# Verify
java -version
```

### Install Other Tools

```bash
# Git
sudo yum install git -y

# Maven (optional, if building on server)
sudo yum install maven -y
```

---

## Elastic IP (Optional)

By default, public IP changes when instance stops/starts. Elastic IP provides a static IP.

### Allocate Elastic IP

```
EC2 → Elastic IPs → Allocate Elastic IP address
  → Allocate
```

### Associate with Instance

```
Select Elastic IP → Actions → Associate Elastic IP address
  → Instance: Select your instance
  → Associate
```

**Note:** Elastic IP is free when associated with a running instance. Charged if not associated.

---

## Summary

| Concept | Description |
|---------|-------------|
| **EC2** | Virtual servers in AWS |
| **AMI** | OS template (Amazon Linux, Ubuntu) |
| **t2.micro** | Free tier instance type |
| **Key Pair** | SSH authentication |
| **Security Group** | Firewall rules |
| **Elastic IP** | Static public IP |

### Quick Launch Checklist

- [ ] Select Amazon Linux 2023 AMI
- [ ] Choose t2.micro (free tier)
- [ ] Create/select key pair
- [ ] Configure security group (SSH + app ports)
- [ ] Enable public IP
- [ ] Launch and connect via SSH

## Next Topic

Continue to [RDS Setup](./03-rds-setup.md) to create a managed database for your application.
