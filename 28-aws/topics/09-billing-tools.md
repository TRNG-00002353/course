# AWS Billing Tools

## Why Monitor AWS Costs?

Without monitoring, cloud costs can quickly spiral out of control. AWS provides several tools to help you track, analyze, and optimize spending.

```
Without Monitoring              With Monitoring
┌─────────────────────┐        ┌─────────────────────┐
│ "Why is my bill     │        │ Daily cost tracking │
│  $500 this month?!" │   →    │ Alerts before issues│
│                     │        │ Budget controls     │
│ Surprise charges    │        │ Predictable costs   │
└─────────────────────┘        └─────────────────────┘
```

---

## AWS Billing Dashboard

Your central hub for cost information.

### Accessing the Dashboard

```
AWS Console → Search "Billing" → Billing Dashboard
```

### What You'll See

```
Billing Dashboard Overview
┌─────────────────────────────────────────────────────┐
│  Month-to-Date Costs                                │
│  ┌─────────────────────────────────────┐            │
│  │ Total: $12.45                       │            │
│  │ ├─ EC2: $8.00                       │            │
│  │ ├─ RDS: $3.50                       │            │
│  │ └─ S3: $0.95                        │            │
│  └─────────────────────────────────────┘            │
│                                                     │
│  [View Bills]  [Cost Explorer]  [Budgets]           │
└─────────────────────────────────────────────────────┘
```

---

## AWS Cost Explorer

Visualize and analyze your spending patterns.

### Key Features

| Feature | Description |
|---------|-------------|
| **Daily/Monthly View** | See costs over time |
| **Service Breakdown** | Which services cost most |
| **Filtering** | By service, region, tag |
| **Forecasting** | Predict future costs |

### Accessing Cost Explorer

```
Billing Dashboard → Cost Explorer → Launch Cost Explorer
```

### Example Analysis

```
Cost by Service (Last 30 Days)
┌─────────────────────────────────────────────────────┐
│ EC2          ████████████████████░░░░░░  $45.00     │
│ RDS          ██████████████░░░░░░░░░░░░  $30.00     │
│ S3           ████░░░░░░░░░░░░░░░░░░░░░░   $8.00     │
│ Data Transfer██░░░░░░░░░░░░░░░░░░░░░░░░   $5.00     │
│ Other        █░░░░░░░░░░░░░░░░░░░░░░░░░   $2.00     │
└─────────────────────────────────────────────────────┘
```

---

## AWS Budgets

Set spending limits and get alerts before you exceed them.

### Creating a Budget

```
Billing → Budgets → Create a budget
```

### Budget Types

| Type | Purpose |
|------|---------|
| **Cost Budget** | Track overall spending |
| **Usage Budget** | Track resource usage (hours, GB) |
| **Reservation Budget** | Track RI utilization |
| **Savings Plans Budget** | Track Savings Plans coverage |

### Setting Up a Cost Budget

**Step-by-step:**

1. Choose "Cost budget"
2. Set budget name (e.g., "Monthly AWS Budget")
3. Set budget amount (e.g., $10)
4. Configure alerts:
   - Alert at 50% ($5)
   - Alert at 80% ($8)
   - Alert at 100% ($10)
5. Add email recipients

```
Budget Alert Configuration
┌─────────────────────────────────────────────────────┐
│ Budget: $10/month                                   │
│                                                     │
│ Alerts:                                             │
│ ├─ 50% threshold → Email when $5 spent             │
│ ├─ 80% threshold → Email when $8 spent             │
│ └─ 100% threshold → Email when $10 spent           │
│                                                     │
│ Actions (optional):                                 │
│ └─ Stop EC2 instances when budget exceeded         │
└─────────────────────────────────────────────────────┘
```

---

## Free Tier Usage Alerts

Track your free tier consumption to avoid charges.

### Enable Free Tier Alerts

```
Billing → Billing Preferences
  ✓ Receive Free Tier Usage Alerts
  Enter email address
  Save preferences
```

### What You'll Receive

Email alerts when approaching free tier limits:

```
Free Tier Alert Email
┌─────────────────────────────────────────────────────┐
│ Subject: AWS Free Tier Usage Alert                  │
│                                                     │
│ Your EC2 usage is approaching free tier limits:    │
│                                                     │
│ Service: Amazon EC2                                 │
│ Usage: 650 hours (86% of 750 hour limit)           │
│ Forecast: Will exceed limit in 5 days              │
│                                                     │
│ Action: Consider stopping unused instances          │
└─────────────────────────────────────────────────────┘
```

---

## CloudWatch Billing Alarms

Get real-time alerts when costs exceed thresholds.

### Creating a Billing Alarm

**Note:** Must be in us-east-1 region for billing metrics.

```
CloudWatch → Alarms → Create Alarm

1. Select metric:
   Billing → Total Estimated Charge → USD

2. Set conditions:
   Threshold: Greater than $5

3. Configure notification:
   Create SNS topic → Enter email

4. Name alarm:
   "billing-alarm-5-dollars"
```

### Alarm States

| State | Meaning |
|-------|---------|
| **OK** | Below threshold |
| **ALARM** | Threshold exceeded |
| **INSUFFICIENT_DATA** | Not enough data yet |

---

## AWS Cost and Usage Reports

Detailed reports for deep analysis.

### Setting Up Reports

```
Billing → Cost and Usage Reports → Create report
```

### Report Contents

- Hourly/daily resource usage
- Cost allocation by tags
- Reserved Instance usage
- Savings Plans coverage

**Best for:** Organizations needing detailed cost analysis or chargeback.

---

## Cost Allocation Tags

Track costs by project, team, or environment.

### How Tags Work

```
Resources with Tags
┌─────────────────────────────────────────────────────┐
│ EC2 Instance                                        │
│ ├─ Tag: Environment = Production                   │
│ ├─ Tag: Project = WebApp                           │
│ └─ Tag: Team = Backend                             │
│                                                     │
│ Cost Explorer can then filter:                      │
│ "Show me all Production costs"                      │
│ "Show me WebApp project spending"                   │
└─────────────────────────────────────────────────────┘
```

### Enabling Cost Allocation Tags

```
Billing → Cost allocation tags → Activate tags
```

---

## Quick Setup Guide for Beginners

### Minimum Recommended Setup

Follow these steps to avoid surprise charges:

```
Step 1: Enable Free Tier Alerts
────────────────────────────────
Billing → Preferences → Free Tier Alerts ✓

Step 2: Create a Budget
────────────────────────────────
Billing → Budgets → Create $10/month budget
  - Alert at 50%, 80%, 100%

Step 3: Create Billing Alarm
────────────────────────────────
CloudWatch (us-east-1) → Create alarm
  - Metric: EstimatedCharges
  - Threshold: $5
```

---

## Cost Management Best Practices

```
Daily Habits
┌─────────────────────────────────────────────────────┐
│ □ Check Billing Dashboard                           │
│ □ Stop EC2 instances not in use                     │
│ □ Review running resources                          │
└─────────────────────────────────────────────────────┘

Weekly Habits
┌─────────────────────────────────────────────────────┐
│ □ Review Cost Explorer trends                       │
│ □ Delete unused EBS volumes                         │
│ □ Check for orphaned resources                      │
└─────────────────────────────────────────────────────┘

Monthly Habits
┌─────────────────────────────────────────────────────┐
│ □ Review detailed bill                              │
│ □ Analyze cost by service                           │
│ □ Adjust budgets if needed                          │
└─────────────────────────────────────────────────────┘
```

---

## Common Billing Issues and Solutions

| Issue | Solution |
|-------|----------|
| Unexpected EC2 charges | Check for running instances in all regions |
| EBS charges after stopping EC2 | Delete unused volumes (EC2 → Volumes) |
| Elastic IP charges | Release unattached IPs |
| Data transfer charges | Review outbound traffic patterns |
| NAT Gateway charges | Consider alternatives or remove if unused |

### Finding Hidden Resources

```bash
# List all EC2 instances across all regions
for region in $(aws ec2 describe-regions --query 'Regions[].RegionName' --output text); do
  echo "Region: $region"
  aws ec2 describe-instances --region $region --query 'Reservations[].Instances[].InstanceId'
done
```

---

## Summary

| Tool | Purpose | When to Use |
|------|---------|-------------|
| **Billing Dashboard** | Overview of costs | Daily check |
| **Cost Explorer** | Analyze spending patterns | Weekly analysis |
| **Budgets** | Set spending limits | Monthly planning |
| **Free Tier Alerts** | Avoid unexpected charges | Always enabled |
| **CloudWatch Alarms** | Real-time cost alerts | Always enabled |

### Setup Checklist

- [ ] Enable Free Tier usage alerts
- [ ] Create monthly budget with alerts
- [ ] Set up CloudWatch billing alarm
- [ ] Review Billing Dashboard weekly

## Next Topic

Continue to [Common AWS Use Cases](./10-common-use-cases.md) to understand practical applications of AWS services.
