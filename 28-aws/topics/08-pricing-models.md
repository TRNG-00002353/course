# AWS Pricing Models

## How AWS Pricing Works

AWS uses a pay-as-you-go model. You only pay for what you use, with no upfront costs or long-term contracts required.

```
Traditional IT                     AWS Pricing
┌─────────────────────┐           ┌─────────────────────┐
│ Buy servers: $50K   │           │ No upfront cost     │
│ Data center: $100K  │     →     │ Pay per hour/GB     │
│ Staff: $200K/year   │           │ Scale costs with use│
│ Fixed costs         │           │ Stop = stop paying  │
└─────────────────────┘           └─────────────────────┘
```

---

## Core Pricing Concepts

### What You Pay For

| Resource | How You're Charged |
|----------|-------------------|
| **Compute (EC2)** | Per hour or per second |
| **Storage (S3, EBS)** | Per GB per month |
| **Data Transfer** | Per GB transferred out |
| **Requests** | Per API call (for some services) |

### Key Principle: Data Transfer

```
Data Transfer Costs
┌─────────────────────────────────────────────────────┐
│                                                     │
│  Internet ──► AWS        FREE (data in)            │
│                                                     │
│  AWS ──► Internet        CHARGED (data out)        │
│                                                     │
│  AWS ◄──► AWS            Usually free (same region)│
│  (same region)                                      │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## EC2 Pricing Options

### On-Demand Instances

Pay by the hour or second with no commitment.

- **Best for:** Short-term, unpredictable workloads
- **Flexibility:** Start/stop anytime
- **Cost:** Highest per-hour rate

```
Example: t2.micro On-Demand
┌─────────────────────────────────────┐
│ Rate: $0.0116/hour                  │
│ 24 hours × 30 days = 720 hours      │
│ Monthly cost: ~$8.35                │
│ (Free tier: 750 hours free!)        │
└─────────────────────────────────────┘
```

### Reserved Instances (RI)

Commit to 1 or 3 years for significant discounts.

| Term | Payment Option | Discount |
|------|---------------|----------|
| 1 Year | No Upfront | ~30% off |
| 1 Year | All Upfront | ~40% off |
| 3 Year | All Upfront | ~60% off |

- **Best for:** Steady, predictable workloads
- **Commitment:** Must pay even if not used

### Spot Instances

Bid on unused EC2 capacity at steep discounts.

- **Savings:** Up to 90% off On-Demand
- **Risk:** Can be terminated with 2-minute notice
- **Best for:** Fault-tolerant, flexible workloads

```
Spot Instance Use Cases
┌─────────────────────────────────────┐
│ ✓ Batch processing                  │
│ ✓ Data analysis                     │
│ ✓ CI/CD builds                      │
│ ✓ Testing environments              │
│                                     │
│ ✗ Production web servers            │
│ ✗ Databases                         │
└─────────────────────────────────────┘
```

### Savings Plans

Flexible commitment-based pricing (newer than RIs).

- Commit to $/hour spend for 1 or 3 years
- Applies across instance families and regions
- More flexible than Reserved Instances

---

## Pricing Comparison Summary

| Option | Discount | Commitment | Flexibility |
|--------|----------|------------|-------------|
| **On-Demand** | 0% | None | Highest |
| **Reserved** | 30-60% | 1-3 years | Low |
| **Spot** | Up to 90% | None | Medium (can be interrupted) |
| **Savings Plans** | Up to 72% | 1-3 years | Medium |

---

## Storage Pricing

### S3 (Object Storage)

| Storage Class | Use Case | Price (approx) |
|--------------|----------|----------------|
| **S3 Standard** | Frequent access | $0.023/GB/month |
| **S3 Infrequent Access** | Less frequent | $0.0125/GB/month |
| **S3 Glacier** | Archive | $0.004/GB/month |
| **S3 Glacier Deep Archive** | Long-term archive | $0.00099/GB/month |

### EBS (Block Storage)

| Volume Type | Use Case | Price (approx) |
|------------|----------|----------------|
| **gp3 (General Purpose)** | Most workloads | $0.08/GB/month |
| **io2 (Provisioned IOPS)** | High performance | $0.125/GB/month |

---

## Database Pricing (RDS)

RDS pricing depends on:

1. **Instance type** (db.t3.micro, db.m5.large, etc.)
2. **Storage** (GB provisioned)
3. **Multi-AZ** (doubles cost for high availability)
4. **Backup storage** (beyond free allocation)

```
RDS Cost Example: db.t3.micro (PostgreSQL)
┌─────────────────────────────────────┐
│ Instance: $0.017/hour × 720 = $12   │
│ Storage: 20GB × $0.115 = $2.30      │
│ Backup: (First 100% free)           │
│ ─────────────────────────────        │
│ Total: ~$14.30/month                │
│ (Free tier covers this!)            │
└─────────────────────────────────────┘
```

---

## Free Tier Summary

AWS Free Tier helps you learn without costs:

| Service | Free Allowance | Duration |
|---------|---------------|----------|
| **EC2** | 750 hours t2.micro/month | 12 months |
| **RDS** | 750 hours db.t3.micro/month | 12 months |
| **S3** | 5 GB storage | 12 months |
| **Lambda** | 1M requests/month | Always free |
| **DynamoDB** | 25 GB storage | Always free |

**Warning:** Some services (like NAT Gateway, Elastic IP when not attached) are NOT covered by free tier!

---

## Cost Estimation Tools

### AWS Pricing Calculator

Plan your costs before deployment:

1. Go to [calculator.aws](https://calculator.aws)
2. Add services you plan to use
3. Configure specifications
4. Get monthly estimate

### Example Estimate

```
Simple Web App Architecture
┌─────────────────────────────────────┐
│ EC2 t3.small (On-Demand)    $15/mo  │
│ RDS db.t3.micro             $14/mo  │
│ S3 (10GB)                    $0.23  │
│ Data Transfer (10GB out)     $0.90  │
│ ────────────────────────────────────│
│ Estimated Total:            ~$30/mo │
└─────────────────────────────────────┘
```

---

## Cost Optimization Tips

```
Cost Saving Strategies
┌─────────────────────────────────────────────────────┐
│                                                     │
│ 1. Use Free Tier                                    │
│    - Stay within limits for learning                │
│                                                     │
│ 2. Right-size Instances                             │
│    - Don't over-provision                           │
│    - Monitor and adjust                             │
│                                                     │
│ 3. Stop Unused Resources                            │
│    - Stop EC2 when not needed                       │
│    - Delete unused EBS volumes                      │
│                                                     │
│ 4. Use Appropriate Storage                          │
│    - S3 Glacier for archives                        │
│    - Delete old snapshots                           │
│                                                     │
│ 5. Reserve for Steady Workloads                     │
│    - 1-year RI for production                       │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## Common Unexpected Charges

| Surprise Cost | Why It Happens |
|--------------|----------------|
| **Elastic IP** | Charged when not attached to running instance |
| **NAT Gateway** | $0.045/hour + data processing |
| **Stopped EBS** | Storage charged even when EC2 stopped |
| **Snapshots** | Old snapshots accumulate |
| **Data Transfer** | Large outbound data transfers |

---

## Summary

| Pricing Model | Best For | Savings |
|--------------|----------|---------|
| **On-Demand** | Variable, short-term workloads | None |
| **Reserved** | Steady, predictable workloads | 30-60% |
| **Spot** | Fault-tolerant batch jobs | Up to 90% |
| **Savings Plans** | Flexible commitment | Up to 72% |

### Key Takeaways

1. **Pay only for what you use** - stop resources to stop charges
2. **Data transfer out costs money** - data in is free
3. **Free tier is generous** - great for learning
4. **Monitor costs** - set up billing alerts

## Next Topic

Continue to [AWS Billing Tools](./09-billing-tools.md) to learn how to monitor and manage your costs.
