# AWS Trusted Advisor

## What is Trusted Advisor?

AWS Trusted Advisor is an automated service that inspects your AWS environment and provides recommendations to optimize costs, improve performance, enhance security, and increase fault tolerance.

```
Your AWS Account              Trusted Advisor
┌─────────────────┐          ┌─────────────────────────┐
│ EC2 Instances   │          │ ✓ Cost Optimization     │
│ S3 Buckets      │─────────►│ ✓ Performance           │
│ Security Groups │  Scans   │ ✓ Security              │
│ IAM Policies    │          │ ✓ Fault Tolerance       │
│ RDS Databases   │          │ ✓ Service Limits        │
└─────────────────┘          └─────────────────────────┘
```

---

## The Five Pillars

Trusted Advisor organizes recommendations into five categories:

### 1. Cost Optimization

Find ways to save money.

```
Cost Optimization Checks
┌─────────────────────────────────────────────────────┐
│ □ Idle EC2 instances (low CPU usage)               │
│ □ Underutilized EBS volumes                        │
│ □ Unassociated Elastic IPs (charged when unused)   │
│ □ Idle load balancers                              │
│ □ Reserved Instance optimization                    │
└─────────────────────────────────────────────────────┘
```

### 2. Performance

Improve application performance.

```
Performance Checks
┌─────────────────────────────────────────────────────┐
│ □ High utilization EC2 instances                   │
│ □ CloudFront optimization                          │
│ □ EBS throughput optimization                      │
│ □ Large number of rules in security groups         │
└─────────────────────────────────────────────────────┘
```

### 3. Security

Identify security vulnerabilities.

```
Security Checks
┌─────────────────────────────────────────────────────┐
│ □ Security groups with unrestricted access         │
│ □ IAM use (root account usage)                     │
│ □ MFA on root account                              │
│ □ Exposed access keys                              │
│ □ S3 bucket permissions                            │
│ □ CloudTrail logging                               │
└─────────────────────────────────────────────────────┘
```

### 4. Fault Tolerance

Improve reliability and availability.

```
Fault Tolerance Checks
┌─────────────────────────────────────────────────────┐
│ □ EC2 Availability Zone balance                    │
│ □ RDS backups                                      │
│ □ RDS Multi-AZ                                     │
│ □ EBS snapshots                                    │
│ □ Load balancer optimization                       │
└─────────────────────────────────────────────────────┘
```

### 5. Service Limits

Monitor usage against AWS limits.

```
Service Limits Checks
┌─────────────────────────────────────────────────────┐
│ □ VPCs per region (approaching limit?)             │
│ □ EC2 instances per region                         │
│ □ EBS volumes per region                           │
│ □ Elastic IPs per region                           │
│ □ Security groups per VPC                          │
└─────────────────────────────────────────────────────┘
```

---

## Accessing Trusted Advisor

```
AWS Console → Search "Trusted Advisor" → Trusted Advisor Dashboard
```

### Dashboard Overview

```
Trusted Advisor Dashboard
┌─────────────────────────────────────────────────────────────┐
│  Summary                                                     │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐│
│  │Cost Opt.│ │ Perform.│ │Security │ │Fault Tol│ │ Limits  ││
│  │  ⚠ 3    │ │  ✓ OK   │ │  ✗ 2    │ │  ⚠ 1    │ │  ✓ OK   ││
│  └─────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘│
│                                                              │
│  Legend:  ✓ No issues  ⚠ Warning  ✗ Action needed           │
└─────────────────────────────────────────────────────────────┘
```

---

## Free vs Paid Checks

### Free Tier (Basic Support)

Everyone gets these core checks for free:

| Category | Free Checks |
|----------|-------------|
| **Security** | S3 Bucket Permissions |
| **Security** | Security Groups - Unrestricted Access |
| **Security** | IAM Use |
| **Security** | MFA on Root Account |
| **Service Limits** | All service limit checks |
| **Cost** | (Limited checks) |

### Full Checks (Business/Enterprise Support)

With paid support plans, you get all checks:
- 50+ additional checks
- API access
- CloudWatch integration
- Weekly email notifications

---

## Key Security Checks (Free)

### 1. MFA on Root Account

```
Check: Is MFA enabled on root account?

Status Options:
┌─────────────────────────────────────────────────────┐
│ ✓ Green: MFA is enabled                            │
│ ✗ Red: MFA is NOT enabled - CRITICAL!              │
└─────────────────────────────────────────────────────┘

Action if Red:
  IAM → Dashboard → Activate MFA on your root account
```

### 2. Security Groups - Unrestricted Access

```
Check: Are security groups allowing 0.0.0.0/0 on risky ports?

Flagged Configurations:
┌─────────────────────────────────────────────────────┐
│ ⚠ Port 22 (SSH) open to 0.0.0.0/0                  │
│ ⚠ Port 3389 (RDP) open to 0.0.0.0/0                │
│ ⚠ Port 3306 (MySQL) open to 0.0.0.0/0              │
└─────────────────────────────────────────────────────┘

Action:
  Restrict to specific IP addresses or ranges
```

### 3. S3 Bucket Permissions

```
Check: Are S3 buckets publicly accessible?

Flagged Configurations:
┌─────────────────────────────────────────────────────┐
│ ✗ Bucket "my-data" allows public read              │
│ ✗ Bucket "backups" allows public list              │
└─────────────────────────────────────────────────────┘

Action:
  Review bucket policies and ACLs
  Enable "Block Public Access" if not intentional
```

### 4. IAM Use

```
Check: Is there at least one IAM user?

Purpose:
  Avoid using root account for daily tasks

Status Options:
┌─────────────────────────────────────────────────────┐
│ ✓ Green: IAM users exist                           │
│ ⚠ Yellow: No IAM users (using root only)           │
└─────────────────────────────────────────────────────┘

Action if Yellow:
  Create IAM admin user with MFA
```

---

## Understanding Check Results

### Status Colors

| Color | Meaning | Action |
|-------|---------|--------|
| **Green** | No issues detected | None needed |
| **Yellow** | Investigation recommended | Review and assess |
| **Red** | Action recommended | Fix as soon as possible |
| **Gray** | Check excluded or not available | N/A |

### Example Check Detail

```
Security Groups - Specific Ports Unrestricted
┌─────────────────────────────────────────────────────┐
│ Status: ⚠ Yellow (2 warnings)                       │
│                                                     │
│ Flagged Resources:                                  │
│ ┌─────────────────────────────────────────────────┐ │
│ │ Region    │ Security Group  │ Port │ Source     │ │
│ ├───────────┼─────────────────┼──────┼────────────┤ │
│ │ us-east-1 │ sg-abc123       │ 22   │ 0.0.0.0/0 │ │
│ │ us-east-1 │ sg-def456       │ 3306 │ 0.0.0.0/0 │ │
│ └─────────────────────────────────────────────────┘ │
│                                                     │
│ Recommendation:                                     │
│ Restrict access to known IP addresses              │
└─────────────────────────────────────────────────────┘
```

---

## Taking Action on Recommendations

### Workflow

```
1. Review Check
   └── Understand the recommendation

2. Assess Impact
   └── Is this a real issue for my use case?

3. Plan Fix
   └── Determine the appropriate action

4. Implement
   └── Make the necessary changes

5. Verify
   └── Refresh Trusted Advisor to confirm fix
```

### Excluding Checks

If a recommendation doesn't apply:

```
Trusted Advisor → Check → Exclude resource

Example: Public S3 bucket for static website
  - Intentionally public
  - Exclude from check to reduce noise
```

---

## Automated Actions

With Business/Enterprise support, you can automate responses:

```
Trusted Advisor Check
        │
        ▼
CloudWatch Event Rule
        │
        ▼
Lambda Function / SNS / etc.
        │
        ▼
Automated Action (email, remediation)
```

---

## Best Practices

### Regular Review Schedule

```
Recommended Review Frequency
┌─────────────────────────────────────────────────────┐
│ Security checks:     Weekly                         │
│ Cost optimization:   Monthly                        │
│ Service limits:      Before major deployments       │
│ All categories:      Quarterly comprehensive review │
└─────────────────────────────────────────────────────┘
```

### Priority Order

Focus on fixes in this order:

1. **Security (Red)** - Immediate action
2. **Security (Yellow)** - Soon as possible
3. **Service Limits** - Before hitting limits
4. **Cost** - Regular optimization
5. **Performance** - As needed

---

## Trusted Advisor vs Other Services

| Service | Purpose |
|---------|---------|
| **Trusted Advisor** | Broad recommendations across pillars |
| **AWS Config** | Track configuration compliance |
| **Security Hub** | Aggregated security findings |
| **Cost Explorer** | Detailed cost analysis |
| **CloudWatch** | Real-time monitoring |

---

## Summary

| Pillar | What It Checks | Free? |
|--------|----------------|-------|
| **Cost Optimization** | Unused resources | Limited |
| **Performance** | Resource utilization | Paid |
| **Security** | Vulnerabilities | Core checks free |
| **Fault Tolerance** | Backup and redundancy | Paid |
| **Service Limits** | Usage vs limits | Yes |

### Key Free Checks to Monitor

- [ ] MFA on Root Account (Security)
- [ ] Security Groups - Unrestricted Access (Security)
- [ ] S3 Bucket Permissions (Security)
- [ ] IAM Use (Security)
- [ ] Service Limits (All)

### Quick Action Items

1. Enable MFA on root account if not done
2. Review security groups with open access
3. Check S3 bucket permissions
4. Set up regular review schedule

## Next Topic

Continue to [Load Balancing](./14-load-balancing.md) to learn how to distribute traffic across multiple instances.
