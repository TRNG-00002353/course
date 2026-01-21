# AWS Introduction

## What is AWS?

Amazon Web Services (AWS) is a cloud computing platform providing on-demand computing resources. Instead of buying physical servers, you rent virtual resources and pay only for what you use.

```
Traditional Infrastructure          Cloud (AWS)
┌─────────────────────┐      ┌─────────────────────┐
│ Buy servers         │      │ Rent virtual servers│
│ Set up data center  │  →   │ Launch in minutes   │
│ Manage hardware     │      │ AWS manages hardware│
│ High upfront cost   │      │ Pay as you go       │
└─────────────────────┘      └─────────────────────┘
```

---

## AWS Free Tier

AWS offers a free tier for learning and small projects.

### Free Tier Types

| Type | Duration | Examples |
|------|----------|----------|
| **12-Month Free** | First year after signup | EC2 t2.micro, RDS db.t3.micro |
| **Always Free** | Forever | Lambda (1M requests/month) |
| **Trials** | Short-term | Some services offer trials |

### Key Free Tier Limits (12-Month)

| Service | Free Allowance |
|---------|----------------|
| **EC2** | 750 hours/month of t2.micro |
| **RDS** | 750 hours/month of db.t3.micro |
| **EBS** | 30 GB storage |
| **Data Transfer** | 15 GB outbound |

**Warning:** Exceeding limits incurs charges. Set up billing alerts!

---

## Creating an AWS Account

### Steps

1. Go to [aws.amazon.com](https://aws.amazon.com)
2. Click "Create an AWS Account"
3. Enter email and account name
4. Provide contact information
5. Add payment method (required, but won't be charged for free tier)
6. Verify phone number
7. Select "Basic Support" (free)

### Post-Setup: Enable Billing Alerts

```
AWS Console → Billing → Billing Preferences
  ✓ Receive Free Tier Usage Alerts
  ✓ Receive Billing Alerts
```

Create a billing alarm:
```
CloudWatch → Alarms → Create Alarm
  → Select metric: Billing > Total Estimated Charge
  → Set threshold: $1 (or your preferred amount)
  → Add notification email
```

---

## AWS Console Overview

The AWS Management Console is your web interface to AWS services.

### Navigation

```
┌─────────────────────────────────────────────────────────────┐
│  AWS  [Services ▼]  [Search]              [Region ▼] [User] │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Recently visited services                                  │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐                       │
│  │   EC2   │ │   RDS   │ │   VPC   │                       │
│  └─────────┘ └─────────┘ └─────────┘                       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Key Services for This Course

| Service | Purpose | Icon |
|---------|---------|------|
| **EC2** | Virtual servers | Server |
| **RDS** | Managed databases | Database |
| **VPC** | Networking | Network |
| **IAM** | Users and permissions | Key |

---

## Regions and Availability Zones

### Regions

AWS has data centers worldwide, grouped into regions.

```
US East (N. Virginia)  - us-east-1
US West (Oregon)       - us-west-2
EU (Ireland)           - eu-west-1
Asia Pacific (Mumbai)  - ap-south-1
```

**Choose a region:**
- Close to your users (lower latency)
- `us-east-1` is common for learning (most services available)

### Availability Zones (AZs)

Each region has multiple isolated data centers (AZs).

```
Region: us-east-1
├── us-east-1a (AZ 1)
├── us-east-1b (AZ 2)
├── us-east-1c (AZ 3)
└── ...
```

For our monolithic deployment, a single AZ is sufficient.

---

## IAM (Identity and Access Management)

IAM controls who can access AWS resources and what they can do.

### Key Concepts

| Concept | Description |
|---------|-------------|
| **Root User** | Account owner, full access (avoid using daily) |
| **IAM User** | Individual user with specific permissions |
| **IAM Group** | Collection of users with shared permissions |
| **IAM Role** | Permissions for AWS services (e.g., EC2 accessing RDS) |
| **Policy** | JSON document defining permissions |

### Best Practice: Create IAM User

Never use root account for daily tasks. Create an IAM admin user.

**Steps:**
1. Sign in as root user
2. Go to IAM → Users → Create User
3. Enter username (e.g., `admin`)
4. Select "Provide user access to AWS Management Console"
5. Attach policy: `AdministratorAccess`
6. Save credentials securely

```
IAM → Users → Create User
  Username: admin
  ✓ AWS Management Console access
  Permissions: AdministratorAccess
```

### Enable MFA (Multi-Factor Authentication)

Secure both root and IAM admin accounts:

```
IAM → Users → [Your User] → Security credentials
  → Assign MFA device → Authenticator app
```

---

## AWS CLI (Command Line Interface)

The AWS CLI lets you manage AWS from your terminal.

### Installation

**macOS:**
```bash
brew install awscli
```

**Windows:**
```bash
# Download installer from AWS website
# Or use: choco install awscli
```

**Linux:**
```bash
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

### Configure AWS CLI

Create access keys for your IAM user:
```
IAM → Users → [Your User] → Security credentials
  → Create access key → Command Line Interface (CLI)
```

Configure CLI:
```bash
aws configure
# AWS Access Key ID: [Your access key]
# AWS Secret Access Key: [Your secret key]
# Default region name: us-east-1
# Default output format: json
```

### Verify Configuration

```bash
aws sts get-caller-identity
```

Output:
```json
{
    "UserId": "AIDAEXAMPLE",
    "Account": "123456789012",
    "Arn": "arn:aws:iam::123456789012:user/admin"
}
```

---

## Summary

| Concept | Description |
|---------|-------------|
| **AWS** | Cloud platform for renting computing resources |
| **Free Tier** | 12-month free usage for learning |
| **Region** | Geographic location of data centers |
| **IAM** | User and permission management |
| **IAM User** | Individual account (use instead of root) |
| **AWS CLI** | Command-line tool for AWS management |

### Setup Checklist

- [ ] Create AWS account
- [ ] Enable billing alerts
- [ ] Create IAM admin user
- [ ] Enable MFA on root and admin
- [ ] Install and configure AWS CLI

## Next Topic

Continue to [EC2 Basics](./02-ec2-basics.md) to learn how to launch virtual servers.
