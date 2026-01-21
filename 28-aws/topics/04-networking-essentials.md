# Networking Essentials

## Why Networking Matters

Understanding AWS networking helps you:
- Control who can access your resources
- Secure communication between EC2 and RDS
- Troubleshoot connectivity issues

---

## VPC (Virtual Private Cloud)

A VPC is your private network in AWS. Think of it as your own data center in the cloud.

```
AWS Cloud
┌──────────────────────────────────────────────┐
│  Your VPC (10.0.0.0/16)                      │
│  ┌─────────────────┐  ┌─────────────────┐   │
│  │  Public Subnet   │  │ Private Subnet  │   │
│  │  (10.0.1.0/24)  │  │ (10.0.2.0/24)   │   │
│  │                  │  │                  │   │
│  │  ┌──────────┐   │  │  ┌──────────┐   │   │
│  │  │   EC2    │   │  │  │   RDS    │   │   │
│  │  └──────────┘   │  │  └──────────┘   │   │
│  └─────────────────┘  └─────────────────┘   │
└──────────────────────────────────────────────┘
        ↑
    Internet Gateway
        ↑
     Internet
```

### Default VPC

AWS creates a default VPC in each region. For learning, the default VPC works fine.

```
VPC → Your VPCs → Default VPC
  CIDR: 172.31.0.0/16
  Subnets: One per Availability Zone
```

---

## Subnets

Subnets divide your VPC into smaller networks.

### Public vs Private Subnets

| Type | Internet Access | Use Case |
|------|-----------------|----------|
| **Public** | Yes (via Internet Gateway) | Web servers, bastion hosts |
| **Private** | No direct access | Databases, internal services |

### Default Subnets

Default VPC has public subnets in each AZ:
```
172.31.0.0/20   (us-east-1a)
172.31.16.0/20  (us-east-1b)
172.31.32.0/20  (us-east-1c)
```

All default subnets are public (have route to Internet Gateway).

---

## Security Groups

Security groups are stateful firewalls that control inbound/outbound traffic.

### Key Characteristics

- **Stateful**: If inbound is allowed, outbound response is automatic
- **Allow rules only**: No explicit deny (implicit deny all)
- **Instance level**: Applied to individual instances

### Security Group Architecture

```
Internet
    │
    ▼
┌─────────────────────────────────┐
│  EC2 Security Group (my-app-sg) │
│  ─────────────────────────────  │
│  Inbound:                       │
│    SSH (22) ← My IP             │
│    HTTP (80) ← 0.0.0.0/0        │
│    8080 ← 0.0.0.0/0             │
└─────────────────────────────────┘
    │
    │ Internal (VPC)
    ▼
┌─────────────────────────────────┐
│  RDS Security Group (my-db-sg)  │
│  ─────────────────────────────  │
│  Inbound:                       │
│    MySQL (3306) ← my-app-sg     │
└─────────────────────────────────┘
```

### Creating Security Groups

#### EC2 Security Group

```
EC2 → Security Groups → Create security group

Name: my-app-sg
Description: Security group for application server
VPC: Default VPC

Inbound rules:
┌──────────────┬──────┬─────────────────┬─────────────────────┐
│ Type         │ Port │ Source          │ Description         │
├──────────────┼──────┼─────────────────┼─────────────────────┤
│ SSH          │ 22   │ My IP           │ Admin SSH access    │
│ HTTP         │ 80   │ 0.0.0.0/0       │ Web traffic         │
│ Custom TCP   │ 8080 │ 0.0.0.0/0       │ Spring Boot         │
└──────────────┴──────┴─────────────────┴─────────────────────┘
```

#### RDS Security Group

```
Name: my-db-sg
Description: Security group for database

Inbound rules:
┌──────────────┬──────┬─────────────────┬─────────────────────┐
│ Type         │ Port │ Source          │ Description         │
├──────────────┼──────┼─────────────────┼─────────────────────┤
│ MySQL/Aurora │ 3306 │ sg-xxxxx        │ From EC2 instances  │
│              │      │ (my-app-sg)     │                     │
└──────────────┴──────┴─────────────────┴─────────────────────┘
```

**Key:** RDS security group references EC2 security group as source—not an IP address.

### Security Group Reference

Instead of using IP addresses, reference other security groups:

```
Source: sg-0abc123def456  (my-app-sg)
```

Benefits:
- Works even when EC2 IP changes
- Any instance in my-app-sg can access database
- More maintainable than IP lists

---

## EC2 ↔ RDS Communication

### Proper Setup

```
┌─────────────────────────────────────────────────┐
│                     VPC                          │
│  ┌──────────────┐        ┌──────────────┐       │
│  │     EC2      │        │     RDS      │       │
│  │  (my-app-sg) │───────▶│  (my-db-sg)  │       │
│  │              │  3306  │              │       │
│  └──────────────┘        └──────────────┘       │
│                                                  │
│  my-db-sg allows inbound 3306 from my-app-sg    │
└─────────────────────────────────────────────────┘
```

### Configuration Steps

1. **EC2 Security Group** (my-app-sg):
   - Inbound: SSH, HTTP, 8080
   - Outbound: All traffic (default)

2. **RDS Security Group** (my-db-sg):
   - Inbound: MySQL (3306) from my-app-sg
   - Outbound: All traffic (default)

3. **RDS Public Access**: Set to "No" (EC2 connects internally)

### Verify Connectivity

From EC2 instance:
```bash
# Test MySQL port
nc -zv my-app-db.xxx.us-east-1.rds.amazonaws.com 3306

# Expected output:
Connection to my-app-db.xxx.us-east-1.rds.amazonaws.com 3306 port [tcp/mysql] succeeded!
```

---

## Common Networking Issues

### Issue: Cannot SSH to EC2

| Check | Solution |
|-------|----------|
| Security group | Add SSH (22) from My IP |
| Public IP | Ensure instance has public IP |
| Key pair | Using correct .pem file |
| Instance state | Must be "running" |

### Issue: EC2 Cannot Connect to RDS

| Check | Solution |
|-------|----------|
| RDS security group | Add inbound rule for EC2 SG |
| Same VPC | EC2 and RDS in same VPC |
| RDS endpoint | Using correct hostname |
| RDS status | Must be "Available" |

### Issue: Cannot Access App from Browser

| Check | Solution |
|-------|----------|
| Security group | Open port 80 or 8080 |
| App running | Verify app is started |
| Correct port | Using public IP:port |
| Firewall on EC2 | Check iptables (usually not an issue on Amazon Linux) |

---

## Network Architecture Diagram

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
         │      (172.31.0.0/16)      │
         │                           │
         │  ┌─────────────────────┐  │
         │  │   Public Subnet     │  │
         │  │  ┌───────────────┐  │  │
         │  │  │     EC2       │  │  │
         │  │  │  (my-app-sg)  │  │  │
         │  │  │  Public IP    │  │  │
         │  │  └───────┬───────┘  │  │
         │  └──────────┼──────────┘  │
         │             │             │
         │             │ port 3306   │
         │             ▼             │
         │  ┌─────────────────────┐  │
         │  │   Private Subnet    │  │
         │  │  ┌───────────────┐  │  │
         │  │  │     RDS       │  │  │
         │  │  │  (my-db-sg)   │  │  │
         │  │  │  No Public IP │  │  │
         │  │  └───────────────┘  │  │
         │  └─────────────────────┘  │
         └───────────────────────────┘

Security Groups:
  my-app-sg: SSH(22), HTTP(80), 8080 from internet
  my-db-sg: MySQL(3306) from my-app-sg only
```

---

## Summary

| Concept | Description |
|---------|-------------|
| **VPC** | Private network in AWS |
| **Subnet** | Subdivision of VPC |
| **Security Group** | Instance-level firewall |
| **Inbound Rule** | Controls incoming traffic |
| **Source** | IP range or security group reference |

### Security Group Quick Reference

| Resource | Inbound Rules |
|----------|---------------|
| **EC2** | SSH (My IP), HTTP/8080 (Anywhere) |
| **RDS** | MySQL/PostgreSQL (EC2 Security Group) |

### Checklist

- [ ] Understand VPC and subnet basics
- [ ] Create EC2 security group with SSH, HTTP, 8080
- [ ] Create RDS security group allowing EC2 security group
- [ ] Verify EC2 can connect to RDS on port 3306
- [ ] Disable RDS public access for security

## Next Topic

Continue to [Deploying Spring Boot](./05-deploying-spring-boot.md) to deploy your application to EC2 with RDS.
